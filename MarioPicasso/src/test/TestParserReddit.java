package test;

import parser.FeedParserFactory;
import parser.FeedParser;
import feed.Feed;

public class TestParserReddit {
    static public void main(String[] args) {
        String url = "https://www.reddit.com/r/Android/hot/.json?count=10";
        String type = "reddit";
        FeedParser parser = FeedParserFactory.createParser(type);
        Feed feed = parser.parseFeed(url);
        feed.prettyPrint();
    }
}
