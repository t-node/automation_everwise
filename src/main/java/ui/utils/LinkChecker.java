package ui.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by vevinmoza on 9/1/17.
 */
public class LinkChecker {
    private static final Logger logger = Logger.getLogger(LinkChecker.class);
    List<String> links;

    public LinkChecker(List<String> links) {
        this.links = links;
    }

    public List<Boolean> statusLinks() {
        return links.stream().map(e -> {
            int responseCode = 100;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(e).openConnection();
                connection.connect();
                responseCode = connection.getResponseCode();
                logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+responseCode);
                connection.disconnect();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                return responseCode==200;
            }

        }).collect(toList());

    }
}
