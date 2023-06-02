package parser;

public class FactoryFeedParser {
    public static FeedParser createParser(String typeUrl){
        if("rss".equals(typeUrl)){
            return new RssParser();
        }
        else if("reddit".equals(typeUrl)){
            return new RedditParser();
        }
        else{
            throw new RuntimeException("Invalid type of url");
        }
    }
}
