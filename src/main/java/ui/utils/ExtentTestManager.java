package ui.utils;

/**
 * Created by vevinmoza on 10/22/17.
 */

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.*;
import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.log4j.Logger;

public class ExtentTestManager {
    private static final Logger logger = Logger.getLogger(ExtentTestManager.class);
    private static ThreadLocal<ExtentTest> extentTest=new ThreadLocal<>();
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<List<String>> listOfScreenshots=new ThreadLocal<>();

    public synchronized static ExtentTest getTest() {
        return extentTest.get();
    }
    public synchronized static List<String> getScreenShotLst() {
        return listOfScreenshots.get();
    }

    public synchronized static ExtentTest createTest(String name, String description, String category) {

        ExtentTest test = extent.createTest(name, description);

        List<String> screenShotsLst=new ArrayList<String>();
        listOfScreenshots.set(screenShotsLst);
        if (category != null && !category.isEmpty())
            test.assignCategory(category);

        extentTest.set(test);

        return getTest();
    }

    public synchronized static ExtentTest createTest(String name, String description) {
        return createTest(name, description, null);
    }

    public synchronized static ExtentTest setAuthorInfo(Triple authorInfo ){
        ExtentTestManager.getTest().assignAuthor(authorInfo.getMiddle().toString());
        return getTest();
    }
    public synchronized static ExtentTest createTest(String name) {
        return createTest(name, null);
    }

    public synchronized static void log(Status s1,String message) {
        getTest().log(s1,message);
    }
    public synchronized static void logWithScreenShot(Status s1,String message,String path) {
       try {
           getTest().log(s1, message).addScreenCaptureFromPath(path,"title");
       }
       catch (Exception e){
           e.printStackTrace();
       }

    }
}