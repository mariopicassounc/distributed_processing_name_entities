import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import feed.Article;
import feed.Feed;

import parser.FeedParser;
import parser.FeedParserFactory;
import parser.SubscriptionParser;

import subscription.SingleSubscription;
import subscription.Subscription;


public class FeedReaderMain {

	public static void main(String[] args) {
	
		//Seteo spark
		SparkSession spark = SparkSession.builder()	//Init SparkSession
		.appName("NamedEntityCount")	//Setea el nombre de aplicación
		.master("local[*]")  // Usar todos los núcleos disponibles localmente
		.getOrCreate();

		JavaSparkContext sparkContext = new JavaSparkContext(spark.sparkContext());
		
		Subscription subscription = null;

		System.out.println("\n\n\n************* FeedReader version 1.0 *************");
			
		// Read the default subscription file
		String relativePath = "config/subscriptions.json";
		String absolutePath = Paths.get(relativePath).toAbsolutePath().toString();
				
		SubscriptionParser subParser = new SubscriptionParser(absolutePath);
				
		try{
			subscription = subParser.parseJSONFile();
		}
		catch(FileNotFoundException e){
			System.out.println("Error: " + e.getMessage());
		}
		
		//Lista donde se guardan los artículos de cada feed
		List<Article> articles = new ArrayList<Article>();
		
		// Para cada subscripcion requestear el feed, parsearlo y mostrarlo
		for (SingleSubscription s: subscription.getSubscriptionsList()){
			String url = s.getUrl();
			String type = s.getUrlType();
			List<String> params = s.getUlrParams();
			s.prettyPrint();
			// Para cada parametro obtener el feed
			for (String param: params){
				String feedUrl = url.replace("%s", param);

				Feed feed;
						
				FeedParser feedParser = FeedParserFactory.createParser(type);
				feed = feedParser.parseFeed(feedUrl);
				feed.prettyPrint();

				//Añado los artículos a la lista
				articles.addAll(feed.getArticleList());
			}
		}

		//Lista de artículos a String
		List<String> articleToString = new ArrayList<String>();
		for (Article a: articles){
			articleToString.add(a.getText());
			articleToString.add(a.getTitle());
		}

		// Detener Spark
		spark.stop();
	}
}
