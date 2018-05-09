package ui;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BrowserDriverImpl implements BrowserDriver {
    private static final Logger logger = Logger.getLogger(BrowserDriverImpl.class);
    private WebDriver driver;
    private String methodInvokedOn;
    private File loc;

    @Override
    public WebDriver getWebDriver() {
        return driver;
    }

    public BrowserDriverImpl(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void get(String url) {
        driver.get(url);

    }

    @Override
    public BrowserDriver setMethodInvokedOn(String methodInvokedOn) {
        this.methodInvokedOn=methodInvokedOn;
        return this;
    }

    public BrowserDriver setScreenShotLocation(File loc) {
        this.loc=loc;
        return this;
    }

    @Override
    public File getScreenShotLocation() {
        return loc;
    }

    @Override
    public String getCurrentUrl() {

        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        // TODO Auto-generated method stub

      return driver.findElements(by).stream().map(e -> new BrowserElementImpl(e,driver,this.methodInvokedOn,this.loc)).collect(Collectors.toList());
    }

    @Override
    public BrowserElement findElement(By by) {
        // TODO Auto-generated method stub
        return new BrowserElementImpl(driver.findElement(by), driver,this.methodInvokedOn,this.loc);
    }


    @Override
    public String getPageSource() {
        // TODO Auto-generated method stub
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();

    }

    @Override
    public void quit() {
        driver.quit();

    }

    @Override
    public Set<String> getWindowHandles() {
        // TODO Auto-generated method stub
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        // TODO Auto-generated method stub
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        // TODO Auto-generated method stub
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        // TODO Auto-generated method stub
        return driver.navigate();
    }

    @Override
    public Options manage() {
        // TODO Auto-generated method stub
        return driver.manage();
    }
    @Override
    public BrowserElement findById(String id) {
        /*try {
            logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+("Driver Loc "+this.loc.getCanonicalPath());
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
        return new BrowserElementImpl(driver.findElement(By.id(id)),getWebDriver(),this.methodInvokedOn,this.loc);
    }

    @Override
    public BrowserElement findByName(String name){
        return new BrowserElementImpl(driver.findElement(By.name(name)),getWebDriver(),this.methodInvokedOn,this.loc);
    }

    @Override
    public BrowserElement findByLinkText(String linkText) {
        return new BrowserElementImpl(driver.findElement(By.linkText(linkText)),getWebDriver(),this.methodInvokedOn,this.loc);
    }

    @Override
    public BrowserElement findByPartialLinkText(String partialLinkText) {
        return new BrowserElementImpl(driver.findElement(By.partialLinkText(partialLinkText)),getWebDriver(),this.methodInvokedOn,this.loc);
    }

    @Override
    public BrowserElement findByClass(String className) {
        return new BrowserElementImpl(driver.findElement(By.className(className)),getWebDriver(),this.methodInvokedOn,this.loc);
    }

    @Override
    public BrowserElement findByCssSelector(String cssSelector) {
        return new BrowserElementImpl(driver.findElement(By.cssSelector(cssSelector)),getWebDriver(),this.methodInvokedOn,this.loc);
    }

    @Override
    public BrowserElement findByTagName(String tagName) {
        return new BrowserElementImpl(driver.findElement(By.tagName(tagName)),getWebDriver(),this.methodInvokedOn,this.loc);
    }

    @Override
    public BrowserElement findByXpath(String xpath) {
        return new BrowserElementImpl(driver.findElement(By.xpath(xpath)),getWebDriver(),this.methodInvokedOn,this.loc);
    }

}
