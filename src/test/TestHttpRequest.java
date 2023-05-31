package test;

import httprequest.HttpRequester;

public class TestHttpRequest {
    static public void main(String[] args) {
        // String url = "https://www.reddit.com/r/Android/hot/.json?count=10";
        String url = "https://rss.nytimes.com/services/xml/rss/nyt/Business.xml";
        HttpRequester HttpRequester = new HttpRequester(url);
        String content = HttpRequester.getContent();
        System.out.println(content);
    }
}
