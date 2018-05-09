package ui.utils;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CaptureScreenShot {

    private static final Logger logger = Logger.getLogger(CaptureScreenShot.class);
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd SSS");

    public static String captureScreen(WebDriver driver, String screenName){
        String dest="";
        try {
            TakesScreenshot screen = (TakesScreenshot) driver;
            File src = screen.getScreenshotAs(OutputType.FILE);
            dest = System.getProperty("user.dir") + File.separator+"Test-ScreenShots"+File.separator + screenName + ".png";
            File target = new File(dest);
            FileUtils.copyFile(src, target);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return dest;
    }
    
    public static String captureDesktop(WebDriver driver, String screenName){
        String dest="";
        try {
        	File src=new File(System.currentTimeMillis()+".png");
        	BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));	  
        	ImageIO.write(image, "png", src);
        	dest = System.getProperty("user.dir") + File.separator+"Test-ScreenShots"+File.separator + screenName + ".png";
            File target = new File(dest);
            FileUtils.copyFile(src, target);
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (HeadlessException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
        return dest;
    }
    

    public static String generateFileName(String methodName){
        Date date = new Date();
        String fileName = methodName+ "_" +date.getTime();
        return fileName;

    }

}