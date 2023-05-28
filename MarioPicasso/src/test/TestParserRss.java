package test;

import parser.FactoryFeedParser;
import parser.FeedParser;
import feed.Feed;

public class TestParserRss {

    static public void main(String[] args) {
        String url = "https://rss.nytimes.com/services/xml/rss/nyt/Business.xml";
        String type = "rss";
        FeedParser parser = FactoryFeedParser.createParser(type);
        Feed feed = parser.parseFeed(url);
        feed.prettyPrint();
    }
}