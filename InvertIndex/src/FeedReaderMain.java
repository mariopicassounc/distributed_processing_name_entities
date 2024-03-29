import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import feed.Article;
import feed.Feed;
import namedEntity.FactoryNamedEntity;
import namedEntity.NamedEntity;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import parser.FeedParser;
import parser.FactoryFeedParser;
import parser.SubscriptionParser;
import scala.Tuple2;
import subscription.SingleSubscription;
import subscription.Subscription;

public class FeedReaderMain {
	private static final Pattern SPACE = Pattern.compile(" ");

	public static void main(String[] args) {

		// set up Spark configuration
		JavaSparkContext sc = initSparkConfig();
		sc.setLogLevel("ERROR");

		// Read the default subscription file
		Subscription subscription = readSubscriptionFile();

		// For each subscription, request the feed, parse it, show it, and append all articles in a list
		List<Article> articles = processAndShowFeeds(subscription);
		
		// Create RDD from list of articles and map articles to string
		JavaRDD<Article> RDDArticles = sc.parallelize(articles);
		JavaRDD<String> RDDArticlesString = RDDArticles.map(article -> article.getTitleAndText());

		printNamedEntities(RDDArticlesString);

		JavaPairRDD<String, Iterable<Tuple2<Long, Integer>>> RDDInvertIndex = getInvertedIndex(RDDArticlesString);

		// Show pront to request a word and show the list of articles where it appears

		System.out.println("\n\n\n************* Inverted Index *************");
		System.out.println("Enter a word to search in the inverted index: ");
		String word = System.console().readLine();

		// Look up in the RDD
		List<Iterable<Tuple2<Long, Integer>>> articleIterables = RDDInvertIndex.lookup(word);
		
		// if length is 0, the word is not in the RDD
		if (articleIterables.size() == 0) {
			System.out.println("The word \"" + word + "\" is not in the inverted index");
			return;
		}
		
		// Print the list of articles where the word appears
		printArticles(articleIterables, articles, word);
		


		sc.stop();
		sc.close();
	}

	public static JavaSparkContext initSparkConfig(){
		// set up Spark configuration
		SparkConf conf = new SparkConf()
				.setAppName("Spark Example")
				.setMaster("local[2]"); // Set the number of executor cores

		return new JavaSparkContext(conf);
	}

	public static Subscription readSubscriptionFile(){
		Subscription subscription = null;

		String relativePath = System.getProperty("user.dir");
		relativePath += "/InvertIndex/config/subscriptions.json";
		String absolutePath = Paths.get(relativePath).toAbsolutePath().toString();

		SubscriptionParser subParser = new SubscriptionParser(absolutePath);
		
		try {
			subscription = subParser.parseJSONFile();
			return subscription;
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	public static List<Article> processAndShowFeeds(Subscription subscription){
		List<Article> articles = new ArrayList<Article>();
		for (SingleSubscription s : subscription.getSubscriptionsList()) {
			String url = s.getUrl();
			String type = s.getUrlType();
			List<String> params = s.getUlrParams();
			s.prettyPrint();

			// For each param in the list, request the feed, parse it, show it, and add all
			// the articles to the list
			for (String param : params) {
				String feedUrl = url.replace("%s", param);

				Feed feed;

				FeedParser feedParser = FactoryFeedParser.createParser(type);
				feed = feedParser.parseFeed(feedUrl);
				feed.prettyPrint();

				articles.addAll(feed.getArticleList());
			}
		}
		return articles;
	}

	public static void printNamedEntities(JavaRDD<String> RDDArticlesString){
		JavaRDD<String> RDDWords = RDDArticlesString.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

		// Map each word to (word, 1) pair
		JavaPairRDD<String, Integer> ones = RDDWords.mapToPair(s -> new Tuple2<>(s,
				1));

		// Reduce by key to get the frequency of each word
		JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);

		List<Tuple2<String, Integer>> output = counts.collect();

		// For each word in the RDD, check if it is a named entity
		Heuristic h = new QuickHeuristic();

		// Create list of named entities with FactoryNamedEntity
		FactoryNamedEntity factoryNamedEntity = new FactoryNamedEntity();
		List<NamedEntity> namedEntities = factoryNamedEntity.createListNamedEntitys(h, output);

		// Print the list of named entities with FactoryNamedEntity
		System.out.println("\n\n\n************* Named Entities *************");
		factoryNamedEntity.preetyPrint();
	}

	public static JavaPairRDD<String, Iterable<Tuple2<Long, Integer>>> getInvertedIndex(JavaRDD<String> RDDArticlesString){
		// zip with index to get the article number
		JavaPairRDD<String, Long> RDDArticlesWithIndex = RDDArticlesString.zipWithIndex();

		// Split all strings of pairs into word list
		JavaPairRDD<String, Long> RDDWordsWithIndex = RDDArticlesWithIndex
				.flatMapToPair(pair -> {
					String text = pair._1();
					Long index = pair._2();
					
					// Splitting the text into words
					String[] words = text.split(" ");
					
					List<Tuple2<String, Long>> wordList = new ArrayList<>();
					
					for (String word : words) {
						wordList.add(new Tuple2<>(word, index));
					}
					
					return wordList.iterator();
		});
	
		// Map each pair to (word, (index, 1)) pair
		JavaPairRDD<Tuple2<String, Long>, Integer> RDDWordsWithIndexAndOnes = RDDWordsWithIndex.mapToPair(pair -> new Tuple2<>(new Tuple2<>(pair._1(), pair._2()), 1));

		// Reduce by key to get the frequency of each word
		JavaPairRDD<Tuple2<String, Long>, Integer> RDDWordsWithIndexAndCounts = RDDWordsWithIndexAndOnes.reduceByKey((i1, i2) -> i1 + i2);
		
		// Map each pair to (word, (index, count)) pair
		JavaPairRDD<String, Tuple2<Long, Integer>> RDDWordsWithIndexAndCountsMapped = RDDWordsWithIndexAndCounts.mapToPair(pair -> {
			Tuple2<String, Long> wordIndex = pair._1();
			Integer count = pair._2();
			
			return new Tuple2<>(wordIndex._1(), new Tuple2<>(wordIndex._2(), count));
		});

		// Group by key to get the list of indexes for each word
		JavaPairRDD<String, Iterable<Tuple2<Long, Integer>>> RDDWordsWithIndexAndCountsGrouped = RDDWordsWithIndexAndCountsMapped.groupByKey();

		// Map each pair to sort the iterable by frequency
		JavaPairRDD<String, Iterable<Tuple2<Long, Integer>>> RDDInvertIndex = RDDWordsWithIndexAndCountsGrouped.mapToPair(pair -> {
			String word = pair._1();
			Iterable<Tuple2<Long, Integer>> iterable = pair._2();
			
			List<Tuple2<Long, Integer>> list = new ArrayList<>();
			iterable.forEach(list::add);
			
			list.sort((pair1, pair2) -> pair2._2().compareTo(pair1._2()));
			
			return new Tuple2<>(word, list);
		});	

		return RDDInvertIndex;
	}


	public static void printArticles(List<Iterable<Tuple2<Long, Integer>>> articleIterables, List<Article> articles, String word ){
		System.out.println("\n\n\n************* Articles where the word \"" + word + "\" appears *************");
		for (Tuple2<Long, Integer> pair : articleIterables.get(0)) {
			Long index = pair._1();
			Integer count = pair._2();
			
			System.out.println("Article number: " + index + " - Frequency: " + count);
			
			articles.get(index.intValue()).prettyPrint();
		}
	}
}
