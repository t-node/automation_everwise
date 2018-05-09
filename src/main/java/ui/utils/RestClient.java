package ui.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

/**
 * Created by vevinmoza on 4/15/16.
 */
public class RestClient {
    private static final Logger logger = Logger.getLogger(RestClient.class);
    public static String getRequest(String url) throws ClientProtocolException, IOException{

               HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(url);
                HttpResponse response = client.execute(request);
                BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
                String line = "";
        StringBuilder responseString= new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    responseString.append(line);
                }
       // logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+(responseString.toString());
        return responseString.toString();
    }
}
