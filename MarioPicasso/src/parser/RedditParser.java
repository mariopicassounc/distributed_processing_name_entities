package parser;

import com.google.gson.*;
import java.util.Date;

import feed.Article;
import feed.Feed;
import httprequest.HttpRequester;

/*
 * Esta clase implementa el parser de feed de tipo reddit (json)
 * pero no es necesario su implemntacion 
 * */

public class RedditParser extends FeedParser {

    public RedditParser() {
        super();
    }

    public Feed parseFeed(String url) {
        try {

            HttpRequester httpRequester = new HttpRequester(url);
            String content = httpRequester.getContent();

            JsonElement jsonData = JsonParser.parseString(content);
            JsonObject data = jsonData.getAsJsonObject().getAsJsonObject("data");
            JsonArray children = data.getAsJsonArray("children");

            String nombreFeed = "";
            int barIndex = url.indexOf('/', 8);
            if (barIndex != -1) {
                String subcadena = url.substring(barIndex + 1);
                int barIndexNext = subcadena.indexOf('/');
                if (barIndexNext != -1) {
                    nombreFeed = subcadena.substring(0, barIndexNext);
                    nombreFeed = nombreFeed.replace('_', ' ');
                }
            }

            Feed feedResult = new Feed(nombreFeed);

            for (JsonElement child : children) {
                JsonObject publication = child.getAsJsonObject().getAsJsonObject("data");
                String titleA = publication.get("title").getAsString();
                String linkA = "https://www.reddit.com" + publication.get("permalink").getAsString();
                String descriptionA = publication.get("selftext").getAsString();
                Long pubDateA = publication.get("created_utc").getAsLong();

                Date datePub = new Date(pubDateA * 1000);

                Article article = new Article(titleA, descriptionA, datePub, linkA);
                feedResult.addArticle(article);
            }
            return feedResult;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new Feed("");
        }
    }

    static public void main(String[] args) {
        String url = "https://www.reddit.com/r/Android/hot/.json?count=10";
        String type = "reddit";
        FeedParser parser = FactoryFeedParser.createParser(type);
        Feed feed = parser.parseFeed(url);
        feed.prettyPrint();
    }
}
