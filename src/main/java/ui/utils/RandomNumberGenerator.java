package ui.utils;

import org.apache.log4j.Logger;

import java.util.Random;

/**
 * Created by vevinmoza on 4/1/16.
 */
public class RandomNumberGenerator {
    private static final Logger logger = Logger.getLogger(RandomNumberGenerator.class);
    public static String generateNumber(){
       return new Random().nextLong()+"";
    }
}
