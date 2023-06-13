package parser;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import httprequest.*;
import feed.Article;
import feed.Feed;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 * */

public class RssParser extends FeedParser {
    
    public RssParser(){
        super();
    }
    
    public Feed parseFeed(String url){
        
        try{
            DocumentBuilderFactory factory = null;
            DocumentBuilder builder = null;
            Document document = null;
            
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            
            HttpRequester HttpRequester = new HttpRequester(url);
            String xmlString = HttpRequester.getContent();
    
            ByteArrayInputStream input = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
            document = builder.parse(input);
    
            Element root = document.getDocumentElement();

            Element channel = (Element) root.getElementsByTagName("channel").item(0);
            String feedName = channel.getElementsByTagName("title").item(0).getTextContent();                       
            Feed feedResult = new Feed(feedName);
            NodeList items = channel.getElementsByTagName("item");

            for(int i=0; i<items.getLength(); i++){
                Element item = (Element) items.item(i);
                String titleA = item.getElementsByTagName("title").item(0).getTextContent();
                String linkA = item.getElementsByTagName("link").item(0).getTextContent();
                String descriptionA = item.getElementsByTagName("description").item(0).getTextContent();
                String pubDateA = item.getElementsByTagName("pubDate").item(0).getTextContent();

                Date datePub = null;
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                try {
                    datePub = sdf.parse(pubDateA);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Article article = new Article(titleA, descriptionA, datePub, linkA);
                feedResult.addArticle(article);
            }

            return feedResult;

        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            return new Feed("");
        }
    }

    static public void main(String[] args) {
        String url = "https://rss.nytimes.com/services/xml/rss/nyt/Business.xml";
        String type = "rss";
        FeedParser parser = FactoryFeedParser.createParser(type);
        Feed feed = parser.parseFeed(url);
        feed.prettyPrint();
    }
}
