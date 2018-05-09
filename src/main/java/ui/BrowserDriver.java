package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;

/**
 * Created by vevinmoza on 10/18/17.
 */
public interface BrowserDriver extends WebDriver, Find {
    WebDriver getWebDriver();

    @Override
    BrowserElement findElement(By by);

    public BrowserDriver setMethodInvokedOn(String methodInvokedOn);
    public File getScreenShotLocation();

}
