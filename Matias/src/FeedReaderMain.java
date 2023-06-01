import java.io.FileNotFoundException;
import java.io.IOException;
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
import namedEntity.NamedEntity;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import parser.FeedParser;
import parser.FeedParserFactory;
import parser.SubscriptionParser;
import scala.Tuple2;
import subscription.SingleSubscription;
import subscription.Subscription;

public class FeedReaderMain {
	private static final Pattern SPACE = Pattern.compile(" ");

	public static void main(String[] args) throws IOException {

		// set up Spark configuration
		SparkConf conf = new SparkConf()
				.setAppName("Spark Example")
				.setMaster("local[2]"); // Set the number of executor cores

		JavaSparkContext sc = new JavaSparkContext(conf);
		sc.setLogLevel("ERROR");

		Subscription subscription = null;
		List<Article> articles = new ArrayList<Article>();

		System.out.println("\n\n\n************* FeedReader version 2.0 *************");
		
		// Read the default subscription file
		String relativePath = "Matias/config/subscriptions.json";
		String absolutePath = Paths.get(relativePath).toAbsolutePath().toString();

		SubscriptionParser subParser = new SubscriptionParser(absolutePath);

		try {
			subscription = subParser.parseJSONFile();
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		}

		// For each subscription in the list, request the feed, parse it, show it, and
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

				FeedParser feedParser = FeedParserFactory.createParser(type);
				feed = feedParser.parseFeed(feedUrl);
				feed.prettyPrint();

				articles.addAll(feed.getArticleList());
			}
		}

		// Map articles to string
		List<String> stringArticles = new ArrayList<String>();
		for (Article a : articles) {
			stringArticles.add(a.getTitleAndText());
		}

		JavaRDD<String> RDDArticles = sc.parallelize(stringArticles);

		// Split all strings into word list
		JavaRDD<String> RDDWords = RDDArticles.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

		// Map each word to (word, 1) pair
		JavaPairRDD<String, Integer> ones = RDDWords.mapToPair(s -> new Tuple2<>(s,
				1));

		// Reduce by key to get the frequency of each word
		JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);

        // Sort the words by frequency
		List<Tuple2<String, Integer>> output = counts.collect();
		for (Tuple2<?, ?> tuple : output) {
			System.out.println(tuple._1() + ": " + tuple._2());
		}

		// Create a List of named entities
		List<NamedEntity> namedEntities = new ArrayList<NamedEntity>();

		// For each word in the RDD, check if it is a named entity
		Heuristic h = new QuickHeuristic();

		for (Tuple2<?, ?> tuple : output) {
			String word = (String) tuple._1();
			Integer frequency = (Integer) tuple._2();

			// If it is a named entity, add it to the list
			if (h.isEntity(word)) {
				List<String> category = h.getCategory(word);

				if (category != null) {
					NamedEntity ne = new NamedEntity(word, category, frequency, null);
					namedEntities.add(ne);

				}
				else{
					NamedEntity ne = new NamedEntity(word, null, frequency, null);
					namedEntities.add(ne);
				}
			}
		}

		// Print the named entities
		System.out.println("\n\n\n************* Named Entities *************");
		for (NamedEntity ne : namedEntities) {
			ne.prettyPrint();
		}

		sc.stop();
		sc.close();
	}
}