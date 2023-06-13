package httprequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/* Esta clase se encarga de realizar efectivamente el pedido de feed al servidor de noticias
 * Leer sobre como hacer una http request en java
 * https://www.baeldung.com/java-http-request
 * */

public class HttpRequester {
    String result;
    String url;

    public HttpRequester(String url) {
        this.url = url;
    }

    public String getContent() {
        try {
            TimeUnit.SECONDS.sleep(1); // to avoid 429 error
            URL url = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            return content.toString();
        } catch (Exception e) {
            RuntimeException re = new RuntimeException(e);
            throw re;
        }
    }

    static public void main(String[] args) {
        // String url = "https://www.reddit.com/r/Android/hot/.json?count=10";
        String url = "https://rss.nytimes.com/services/xml/rss/nyt/Business.xml";
        HttpRequester HttpRequester = new HttpRequester(url);
        String content = HttpRequester.getContent();
        System.out.println(content);
    }

}
