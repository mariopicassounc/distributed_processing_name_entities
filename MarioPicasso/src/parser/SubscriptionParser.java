package parser;

import subscription.Subscription;
import subscription.SingleSubscription;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/*
 * Esta clase implementa el parser del  archivo de suscripcion (json)
 * Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
 */

public class SubscriptionParser{
    private String subscriptionFilePath;
    private Subscription subscription;

    public SubscriptionParser(String subscriptionFilePath) {
        this.subscriptionFilePath = subscriptionFilePath;
        this.subscription = new Subscription(subscriptionFilePath);
    }

    public Subscription parseJSONFile() throws FileNotFoundException {
        SingleSubscription singleSubscription = null;

        FileReader reader = new FileReader(this.subscriptionFilePath);
        JSONArray jsonArray = new JSONArray(new JSONTokener(reader));
        
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String url = jsonObject.getString("url");
            String urlType = jsonObject.getString("urlType");
            JSONArray jsonArrayURLParams = jsonObject.getJSONArray("urlParams");
            
            List<String> urlParams = new ArrayList<String>();

            for (int j = 0 ;j < jsonArrayURLParams.length(); j++) {
                urlParams.add( jsonArrayURLParams.getString(j));
            }
            System.out.println(urlParams.toString());
            
            singleSubscription = new SingleSubscription(url, urlParams, urlType);
            this.subscription.addSingleSubscription(singleSubscription);
        }
        return this.subscription;
    }
}
