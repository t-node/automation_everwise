package ui.utils;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vevinmoza on 3/31/16.
 */
public class RegexSearch {
    private static final Logger logger = Logger.getLogger(RegexSearch.class);

    public static int getInteger(String regex, String input) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " " + m.find());
        return Integer.parseInt(m.group(0));
    }

    public static List<String> getMultipleString(String regex, String pageSource, int index) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pageSource);
        List<String> matches = new ArrayList<String>();
        while (m.find()) {
            matches.add(m.group(index));
        }
        return matches;
    }

}
