package ui;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import com.google.common.base.Function;

import ui.support.Config;
import ui.utils.RegexSearch;
import ui.utils.browser.Browser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toMap;

import java.awt.AWTException;
import java.awt.Robot;


public class UiBase {

    private static final String SELENIUM_MAXTIMEOUT = "selenium.element.maxtimeout";
    private static final String SELENIUM_MINTIMEOUT = "selenium.element.mintimeout";
    private static final String SELENIUM_IMPLICITWAIT = "selenium.element.implicitwait";
    private static final String PAGELOAD_MAXTIMEOUT = "selenium.pageload.timeout";
    private static final String THREAD_MAXTIMEOUT = "thread.maxtimeout";
    private static final String CONFIG_FILE = "Config.properties";
    private boolean checkoutType;
    private static String browserType;
    private Actions actions;
    private JavascriptExecutor javascriptExecutor;
    private BrowserDriver driver;

    public BrowserDriver   getDriver() {
        return driver.setMethodInvokedOn(Thread.currentThread().getStackTrace()[2].getMethodName());
    }
	protected void takeDebugScreenShot() {
		 findByTagName("body").getText();
	}

    public void showToolTip(String selector){	
    	executeJavaScript("$(\""+selector+"\").tooltip()");
    }
    public RegexSearch regexSearch = new RegexSearch();

    public void setDriver(BrowserDriver driver) {
        this.driver = driver;
    }
    private static final Logger logger = Logger.getLogger(UiBase.class);

    public BrowserElement findById(String id) {
        return getDriver().findById(id).setMethodInvokedOn(Thread.currentThread().getStackTrace()[2].getMethodName()).
                setScreenShotLocation(getDriver().getScreenShotLocation());
    }
    public BrowserElement findByClass(String className) {
        return getDriver().findByClass(className).setMethodInvokedOn(Thread.currentThread().getStackTrace()[2].getMethodName()).setScreenShotLocation(getDriver().getScreenShotLocation());
    }
    public BrowserElement findByCssSelector(String cssSelector) {
        return getDriver().findByCssSelector(cssSelector).setMethodInvokedOn(Thread.currentThread().getStackTrace()[2].getMethodName()).setScreenShotLocation(getDriver().getScreenShotLocation());
    }
    public BrowserElement findByLinkText(String linkText) {
        return getDriver().findByLinkText(linkText).setMethodInvokedOn(Thread.currentThread().getStackTrace()[2].getMethodName()).setScreenShotLocation(getDriver().getScreenShotLocation());
    }

    public BrowserElement findByName(String name) {
        return getDriver().findByName(name).setMethodInvokedOn(Thread.currentThread().getStackTrace()[2].getMethodName()).setScreenShotLocation(getDriver().getScreenShotLocation());
    }

    public BrowserElement findByPartialLinkText(String partialLinkText) {
        return getDriver().findByPartialLinkText(partialLinkText).setMethodInvokedOn(Thread.currentThread().getStackTrace()[2].getMethodName()).setScreenShotLocation(getDriver().getScreenShotLocation());
    }

    public BrowserElement findByTagName(String tagName) {
        return getDriver().findByTagName(tagName).setMethodInvokedOn(Thread.currentThread().getStackTrace()[2].getMethodName()).setScreenShotLocation(getDriver().getScreenShotLocation());
    }

    public BrowserElement findByXpath(String xpath) {
         return getDriver().findByXpath(xpath).setMethodInvokedOn(Thread.currentThread().getStackTrace()[2].getMethodName()).setScreenShotLocation(getDriver().getScreenShotLocation());
    }


    private static PropertiesConfiguration getPropertiesConfiguration() {
        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration(loadAndGetResourceLocation(CONFIG_FILE));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return config;
    }

    public boolean isInternational() {
        return Boolean.parseBoolean(executeJavaScript("return services.isInternational()").toString());
    }
   

    private static String loadAndGetResourceLocation(String fileName) throws URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(fileName).toString();
    }

    public static int getMinTimeout() {
        PropertiesConfiguration config = getPropertiesConfiguration();
        return getTimeOutForProperty(config, SELENIUM_MINTIMEOUT);
    }


    public static int getMaxTimeout() {
        PropertiesConfiguration config = getPropertiesConfiguration();
        return getTimeOutForProperty(config, SELENIUM_MAXTIMEOUT);
    }

    public static int getDefaultImplicitTimeout() {
        PropertiesConfiguration config = getPropertiesConfiguration();

        return getTimeOutForProperty(config, SELENIUM_IMPLICITWAIT);
    }

    public static int getMaxPageLoadTimeout() {
        PropertiesConfiguration config = getPropertiesConfiguration();

        return getTimeOutForProperty(config, PAGELOAD_MAXTIMEOUT);
    }


    private static int getTimeOutForProperty(PropertiesConfiguration config, String config_property_name) {
        String timeOut = config.getString(config_property_name);
        return Integer.parseInt(timeOut);
    }


    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public static String getCssLocator(WebElement element) {
        String val = element.toString();
        return val.substring(val.lastIndexOf("selector:") + 10, val.length() - 1).trim();
    }

    public static String getXpathLocator(WebElement element) {
        String val = element.toString();
        return val.substring(val.lastIndexOf("xpath:") + 7, val.length() - 1).trim();
    }


    public void clickJs(BrowserElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver().getWebDriver();
        js.executeScript("arguments[0].click();", element.getWrappedElement());
    }


    public int numberOfElement(List<WebElement> elements) {
        int size = elements.size();
        return size;
    }


    public void sendText(BrowserElement element, String text) {
        try {
            element.sendKeys(text);
        } catch (Throwable t) {
            t.printStackTrace();

        }

    }

    public String getText(WebElement element) {
        try {
            return element.getText();
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }

    }

    public Boolean waitUntilElementDisplayed(BrowserElement element) {
        try {
            (new WebDriverWait(driver.getWebDriver(), getMaxTimeout()))
                    .until(ExpectedConditions.visibilityOf(element.getWrappedElement()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean waitUntilIframeAvailable(String frameId) {
        try {
            (new WebDriverWait(driver.getWebDriver(), getMaxTimeout()))
                    .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Boolean waitUntilElementDisplayed(BrowserElement element, int timeInSeconds) {
        try {

            (new WebDriverWait(driver.getWebDriver(), timeInSeconds))

                    .until(ExpectedConditions.visibilityOf(element.getWrappedElement()));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Boolean waitUntilElementDisplayed(String cssSelector) {
        try {

            (new WebDriverWait(driver.getWebDriver(), getMaxTimeout()))

                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public Boolean waitUntilElementDisplayed(List<WebElement> elements, int timeInSeconds) {
        boolean retVal = false;
        try {
            (new WebDriverWait(driver.getWebDriver(), timeInSeconds))

                    .until(ExpectedConditions.visibilityOfAllElements(elements));
            retVal = true;
        } catch (Exception e) {
            retVal = false;
        }
        return retVal;

    }

    public Boolean waitUntilElementDisplayed(List<WebElement> elements, String matchText, int timeInSeconds) {
        boolean retVal = false;
        for (WebElement element : elements) {
            (new WebDriverWait(driver.getWebDriver(), timeInSeconds))
                    .until(ExpectedConditions.visibilityOf(element));
            if (getText(element).equalsIgnoreCase(matchText)) {
                retVal = true;
                break;
            }

        }
        return retVal;
    }


    public boolean waitForElement(List<WebElement> elements, String matchText, int timeOut) {
        boolean retVal = false;
        for (WebElement element : elements) {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver.getWebDriver())
                    .withTimeout(timeOut, TimeUnit.SECONDS)
                    .pollingEvery(2, TimeUnit.SECONDS)
                    .ignoring(NoSuchElementException.class);

            wait.until(ExpectedConditions.visibilityOf(element));
            if (getText(element).equalsIgnoreCase(matchText)) {
                retVal = true;
                break;
            }

        }
        return retVal;
    }


    public void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void staticWait(int milliSecs) {
        try {
            Thread.sleep(milliSecs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void mouseHover(BrowserElement element) {

        waitUntilElementDisplayed(element);
        System.out.println(" ");
        actions = new Actions(getDriver().getWebDriver());
        actions.moveToElement(element.getWrappedElement()).perform();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    public void mouseHover(WebElement element) {
    	actions = new Actions(getDriver().getWebDriver());
    	actions.moveToElement(element).perform();
    	
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public Object executeJavaScript(String script) {
        javascriptExecutor = (JavascriptExecutor) getDriver().getWebDriver();
        return javascriptExecutor.executeScript(script);
    }

    public Object executeAsyncJavaScript(String script) {
        javascriptExecutor = (JavascriptExecutor) getDriver().getWebDriver();
        return javascriptExecutor.executeAsyncScript(script);
    }


    public Object executeJavaScript(String script, String location) {
        javascriptExecutor = (JavascriptExecutor) getDriver().getWebDriver();
        while (true) {
            if (javascriptExecutor.executeScript(script) == null) {
                logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+"waiting for javascript variable probable initialization: " + location);
            } else {
                break;
            }
        }
        return javascriptExecutor.executeScript(script);
    }

    public void executeJavaScript(String script, BrowserElement element) {
        javascriptExecutor = (JavascriptExecutor) getDriver().getWebDriver();
        javascriptExecutor.executeScript(script, element.getWrappedElement());
    }

    public void switchToFrame(BrowserElement element) {
        waitUntilElementDisplayed(element);
        getDriver().switchTo().frame(element);
    }

    public void switchToFrame(int frameId) {
        getDriver().switchTo().frame(frameId);
    }

    public void switchToFrame(String frameName) {
        getDriver().switchTo().frame(frameName);
    }

    public void switchToDefaultFrame() {
        getDriver().switchTo().defaultContent();
    }

    public Alert switchToAlart() {
    	   WebDriverWait wait = new WebDriverWait(driver, 2);
    	    wait.until(ExpectedConditions.alertIsPresent());
        return getDriver().switchTo().alert();
    }

    public void switchToWindows(String win) {
    	
        getDriver().switchTo().window(win);
    }

    public void switchToNewWindow() {
        for (String winHandle : getWindowHandles()) {
            getDriver().switchTo().window(winHandle);
        }
    }

    public Set<String> getWindowHandles() {
        return getDriver().getWindowHandles();
    }

    public String getWindowHandle() {
        return getDriver().getWindowHandle();
    }

    public Boolean waitForPageTitle(String title) {
        try {
            (new WebDriverWait(driver, getMaxTimeout()))
                    .until(ExpectedConditions.titleIs(title));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Boolean waitForPageTitle(String title, int timeInSeconds) {
        try {
            (new WebDriverWait(driver, timeInSeconds))
                    .until(ExpectedConditions.titleIs(title));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean waitForTextToAppear(WebElement element, String textToAppear, int secs) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, secs);
            wait.until(ExpectedConditions.textToBePresentInElement(element, textToAppear));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForTextToAppearEqual(final WebElement element, final String textToAppear, int secs) {
        boolean result = false;
        try {
            result = new WebDriverWait(driver, secs).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    String text = element.getText();
                    return text.equalsIgnoreCase(textToAppear);
                }
            });
        } catch (Exception e) {
            return false;
        }
        return result;
    }


    public boolean waitForTextToAppear(WebElement element, String textToAppear) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, getMaxTimeout());
            wait.until(ExpectedConditions.textToBePresentInElement(element, textToAppear));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getRandomNumber(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    public String getRandomString(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

    public String getCurrentDate(String dateString) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateString);
        return dateFormat.format(cal.getTime());
    }

    public String getTitle() {
        String title = driver.getTitle();
        return title;
    }

    public String getFrameName() {
        return getDriver().findElement(By.tagName("iframe")).getAttribute("name");
    }

    /*public Boolean isDisplayed(WebElement element) {
        try {
            boolean b = element.isDisplayed();
            String text = element.getText();


            return b;
        } catch (Throwable t) {
            return false;
        }

    }*/

   /* public Boolean isDisplayed(List<WebElement> element) {
        boolean b = true;
        try {
            for (int i = 0; i < element.size(); i++) {
                b = b && element.get(i).isDisplayed();
            }

            return b;
        } catch (Throwable t) {
            return false;
        }

    }*/


    public boolean isEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean isSelected(WebElement element) {
        try {
            return element.isSelected();
        } catch (Throwable t) {
            return false;
        }
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
        waitFor(1);
        refreshPage();
        logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+"deleteAllCookies");
        waitFor(1);
    }
    public void doubleClick(WebElement webElement) {
    	Actions action = new Actions(driver.getWebDriver());
    	action.moveToElement(webElement,5,5).doubleClick().perform();
    	
    }
    public String getAttributeValue(WebElement element, String attributeName) {
        return element.getAttribute(attributeName);
    }

    public boolean isAttributePresent(WebElement element, String attributeName) {
        Boolean result = false;
        try {
            String value = element.getAttribute(attributeName);
            if (value != null) {
                result = true;
            }
        } catch (Exception e) {
        }

        return result;

    }

    public void makeRequest(String request) throws Exception {
        logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+Config.getApplicationUrl() + request);
        driver.get(Config.getApplicationUrl() + request);
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    /*public boolean navigateAndVerify(WebElement currentPageElement, WebElement nextPageElement) {

        waitUntilElementDisplayed(currentPageElement);
        click(currentPageElement);
        boolean ele = waitUntilElementDisplayed(nextPageElement);
        logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+("Navigating to " + driver.getTitle() + " Page ->" + ele);
        //logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+("Navigating to "+driver.getTitle()+" Page ->"+ele);
        navigateBack();
        waitFor(1);

        return ele;
    }*/

    public void refreshPage() {
        driver.navigate().refresh();
    }


    public void waitForFrameToLoad(String frameName) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
    }

    public boolean isAlertPresent() {
        try {
            getDriver().switchTo().alert();
            return true;
        }   // try
        catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }
    public String getAlertText() {
       return driver.switchTo().alert().getText();
    }
    public void waitAndAcceptAlert(int waitTimeInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, waitTimeInSeconds);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert();

            //logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+("Alert Displayed: " + alert.getText());
            alert.accept();
            staticWait(3000);
            //logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+("Alert Accepted");

        } catch (Exception e) {
            //logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+("Alert Not Displayed.");
        }
    }

    public String getCssValue(WebElement element, String cssValueName) {
        return element.getCssValue(cssValueName);
    }

    public long scrollPosition() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return (Long) executor.executeScript("return window.scrollY;");
    }


    public void scrollDown() {
        ((JavascriptExecutor) driver.getWebDriver()).executeScript("scroll(0,document.body.scrollHeight);");
    }
    public void scrollTop() {
        ((JavascriptExecutor) driver.getWebDriver()).executeScript("scroll(0,0);");
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public Date convertStringToDate(String dateString, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formatter.parse(dateString);
            // logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+("String converted to date object " + date);
            String val = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public int getRandomElementNumberFrom2(List<WebElement> allElement) {
        int num = numberOfElement(allElement);
        Random r = new Random();
        return r.nextInt(num - 2) + 2;
    }

    public void closeCurrentWindow() {
        getDriver().close();
    }

    public String getpageSource() {

        return driver.getPageSource();
    }

    public double textToPrice(String text) {
        String priceText = text.toString();
        if (priceText.contains("$")) {
            priceText = priceText.replace("$", "");
        }
        if (priceText.contains(",")) {
            priceText = priceText.replace(",", "");
        }
        if (priceText.contains("-")) {
            priceText = priceText.replace("-", "");
        }
        if (priceText.contains(" ")) {
            priceText = priceText.replace(" ", "");
        }
        double price = new Double(priceText);
        return price;
    }

    public void waitForAjax() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        int iterationsIndex = 0;
        while (iterationsIndex <= 120) // Handle timeout somewhere
        {
            Object ajaxIsComplete = js.executeScript("return window.jQuery != undefined && !!jQuery && jQuery.active == 0");
            if ((boolean) ajaxIsComplete) {
                break;
            }
            logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+"Waiting..");
            staticWait(500);
            iterationsIndex++;
        }
    }

    public void loadjQuery() {
        ((JavascriptExecutor) driver).executeScript("javascript:(function(){function l(u,i){\n" +
                "var d=document;if(!d.getElementById(i)){var s=d.createElement('script');s.src=u;s.id=i;d.body.appendChild(s);}}l('//code.jquery.com/jquery-latest.min.js','jquery')})();");
        staticWait(1500);
    }

    public BrowserElement scrollToView(BrowserElement we) {
        executeJavaScript("arguments[0].scrollIntoView(false);", we);
        return we;
    }

    public List<String> scrapLinksCurrentPage(String tagName, String propertyName) {
        Object imp = executeJavaScript("{ window.p=''; $('" + tagName + "').each(function(){ var x = $(this).attr('" + propertyName + "');  if( x && x.startsWith('http')) { p = p+'##'+x;} }) ;}");
        String concatnatedLinks = executeJavaScript("return p").toString();
        return Arrays.asList(concatnatedLinks.split("##"));

    }

    public void waitForAjax(String location) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver().getWebDriver();
        int iterationsIndex = 0;
        while (iterationsIndex <= 30) // Handle timeout somewhere
        {
            Object ajaxIsComplete = js.executeScript("return window.jQuery != undefined && !!jQuery && jQuery.active == 0");
            if ((boolean) ajaxIsComplete) {
                break;
            }
            logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+(location));
            staticWait(500);
            iterationsIndex++;
        }

    }

    /* public static boolean waitForJQueryProcessing(WebDriver driver,
                                                   int timeOutInSeconds) {
         boolean jQcondition = false;
         try {
             new WebDriverWait(driver, timeOutInSeconds) {
             }.until(new ExpectedCondition<Boolean>() {

                 @Override
                 public Boolean apply(WebDriver driverObject) {
                     return (Boolean) ((JavascriptExecutor) driverObject)
                             .executeScript("return jQuery.active == 0");
                 }
             });
             jQcondition = (Boolean) ((JavascriptExecutor) driver)
                     .executeScript("return window.jQuery != undefined && jQuery.active === 0");
             return jQcondition;
         } catch (Exception e) {
             e.printStackTrace();
         }
         return jQcondition;
     }*/
    public void waitUntilAnimationIsDone(final String cssLocator) {
        WebDriverWait wdw = new WebDriverWait(driver, 20);
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+"animation test");
                String temp = ((JavascriptExecutor) driver)
                        .executeScript("return jQuery('" + cssLocator + "').is(':animated')").toString();
                return temp.equalsIgnoreCase("false");
            }
        };

        try {
            wdw.until(expectation);
        } catch (TimeoutException e) {
            throw new AssertionError("BrowserElement animation is not finished in time. Css locator: " + cssLocator);
        }
    }

    public void waitForTextOnPage(String text) {
        int i = 0;
        while (i < 10) {

            if (driver.getPageSource().contains(text)) {
                break;
            } else {
                logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+"waiting for json text : " + text);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
    }

    class Pair {
        private String url;
        private boolean status;

        public Pair(String url, boolean status) {
            this.status = status;
            this.url = url;
        }

        public String getURL() {
            return url;
        }

        public boolean getStatus() {
            return status;
        }
    }

    public Map<String, Boolean> statusLinks(List<String> links) {
        return links.stream().filter(e -> e.length() > 0).map(e -> {
            int responseCode = 100;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(e).openConnection();
                connection.connect();
                responseCode = connection.getResponseCode();
                //logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+(responseCode);
                connection.disconnect();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {

                return new Pair(e, responseCode == 200);
            }

        }).collect(toMap(Pair::getURL, Pair::getStatus, (s1, s2) -> {
            return s1;
        }));

    }

    public void waitUntilElementInvisible(String element) {
        WebDriverWait wait = new WebDriverWait(driver, getMaxTimeout());
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.linkText(element)));
    }

    /*public void waitForPageReadyState(String s) throws InterruptedException {
        while (true) {
            Object pageState = executeJavaScript(("return document.readyState"));
            if (pageState.toString().equalsIgnoreCase("complete")) {
                break;
            } else {
                Thread.sleep(500);
                logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+(s);
            }
        }
    }*/

}
