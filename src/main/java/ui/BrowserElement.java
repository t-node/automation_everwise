package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

import java.io.File;
import java.util.List;

/**
 * Created by vevinmoza on 10/18/17.
 */

public interface BrowserElement extends WebElement,WrapsElement,Locatable,Find {
    @Override
    BrowserElement findElement(By by);

    BrowserElement setMethodInvokedOn(String methodInvokedOn);
    BrowserElement setScreenShotLocation(File loc);

}
