import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;

import feed.Feed;
import parser.FeedParser;
import parser.FeedParserFactory;
import parser.SubscriptionParser;
import subscription.SingleSubscription;
import subscription.Subscription;


public class FeedReaderMain {

	private static void printHelp(){
		System.out.println("Please, call this program in correct way: FeedReader [-ne]");
	}
	
	public static void main(String[] args) {
		
		if (args.length == 0){
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
				}
			}	
		}else {
			printHelp();
		}
	}
}
