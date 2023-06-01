import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import feed.Article;
import feed.Feed;

import parser.FeedParser;
import parser.FeedParserFactory;
import parser.SubscriptionParser;
import scala.Tuple2;
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
			
		// Leer el archivo subscripción
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

		// Crear un RDD a partir de los artículos
		JavaRDD<String> articlesRDD = sparkContext.parallelize(articleToString);

		// Expresión regular para filtrar las NE
		String regex = "^[A-Z][a-zA-Z]+$";

		// Generar un nuevo RDD que contiene todas las palabras individuales
		JavaRDD<String> wordsRDD = articlesRDD.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
		
		// Utilizo regex en wordsRDD
		wordsRDD = wordsRDD.filter(word -> Pattern.matches(regex, word));

		// Se mapea a pares, y con reduceByKey se aplica la suma al par (palabra, 1) para sumar el conteo
		JavaRDD<String> countedEntitiesRDD = wordsRDD.mapToPair(word -> new Tuple2<>(word, 1))
		.reduceByKey(Integer::sum) // Combina los valores para cada clave utilizando la función Integer::sum
		.map(pair -> pair._1 + ": " + pair._2);  // Mapear a pares (palabra, conteo) 

		// Pasamos a lista
		List<String> countedEntitiesList = countedEntitiesRDD.collect();

		// Detener Spark
		spark.stop();
	}
}
