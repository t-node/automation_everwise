package ui;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import ui.utils.CaptureScreenShot;
import ui.utils.ExtentTestManager;

/**
 * Created by vevinmoza on 9/28/17.
 */
public class BrowserElementImpl implements BrowserElement {
	private static final Logger logger = Logger.getLogger(BrowserElementImpl.class);
	private WebElement webElement;
	private WebDriver driver;
	private File loc;
	private String methodInvokedOn;

	public BrowserElementImpl(WebElement webElement, WebDriver driver) {
		this(webElement);
		this.driver = driver;
	}

	public BrowserElementImpl(WebElement webElement, WebDriver driver, String methodInvokedOn) {
		this(webElement, driver);
		this.methodInvokedOn = methodInvokedOn;
	}

	public BrowserElementImpl(WebElement webElement, WebDriver driver, String methodInvokedOn, File loc) {
		this(webElement, driver, methodInvokedOn);
		this.loc = loc;
	}

	public BrowserElementImpl(WebElement webElement) {
		this.webElement = webElement;

	}

	public void click() {
		webElementlogger();
		webElement.click();
	}

	@Override
	public void submit() {
		webElementlogger();
		webElement.submit();
	}

	@Override
	public void sendKeys(CharSequence... charSequences) {
		webElement.sendKeys(charSequences);
		webElementlogger();
	}

	@Override
	public void clear() {
		webElementlogger();
		webElement.clear();
	}

	@Override
	public String getTagName() {
		webElementlogger();
		return webElement.getTagName();
	}

	@Override
	public String getAttribute(String s) {
		webElementlogger();
		return webElement.getAttribute(s);
	}

	@Override
	public boolean isSelected() {
		webElementlogger();
		return webElement.isSelected();
	}

	@Override
	public boolean isEnabled() {
		webElementlogger();
		return webElement.isEnabled();
	}

	@Override
	public String getText() {
		if (webElement instanceof BrowserElementImpl) {
			webElement = ((BrowserElementImpl) webElement).getWrappedElement();
		}
		webElementlogger();
		return webElement.getText();
	}

	@Override
	public List<WebElement> findElements(By by) {
		return webElement.findElements(by).stream()
				.map(e -> new BrowserElementImpl(e, driver, methodInvokedOn, this.loc)).collect(Collectors.toList());
	}

	@Override
	public BrowserElementImpl findElement(By by) {

		return new BrowserElementImpl(webElement.findElement(by), driver, methodInvokedOn, this.loc);
	}

	@Override
	public BrowserElement setMethodInvokedOn(String methodInvokedOn) {
		this.methodInvokedOn = methodInvokedOn;
		return this;
	}

	@Override
	public BrowserElement setScreenShotLocation(File loc) {
		this.loc = loc;
		return this;
	}

	@Override
	public boolean isDisplayed() {
		webElementlogger();
		return webElement.isDisplayed();
	}

	@Override
	public Point getLocation() {
		webElementlogger();
		return webElement.getLocation();
	}

	@Override
	public Dimension getSize() {
		webElementlogger();
		return webElement.getSize();
	}

	@Override
	public Rectangle getRect() {
		webElementlogger();
		return webElement.getRect();
	}

	@Override
	public String getCssValue(String s) {
		webElementlogger();
		return webElement.getCssValue(s);
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
		return webElement.getScreenshotAs(outputType);
	}

	private void webElementlogger() {
		logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
				+ methodInvokedOn);
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[2];// maybe this number needs to be corrected
		String methodName = e.getMethodName();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", this.webElement,
				"color: red; border: 2px solid red;");
		if (false) {
			String screenShot = CaptureScreenShot.captureScreen(driver, CaptureScreenShot.generateFileName(methodName));
			String fileName = screenShot.split("\\\\")[screenShot.split("\\\\").length - 1];
			ExtentTestManager.getScreenShotLst().add(screenShot);
			try {
				ExtentTestManager.getTest().info(""+ExtentTestManager.getTest().addScreenCaptureFromPath(screenShot));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			 try { Thread.sleep(100); } catch (InterruptedException e1){
			  e1.printStackTrace(); }
		}
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement, "");
	}

	
    @Override
    public Coordinates getCoordinates() {
        webElementlogger();
        return ((org.openqa.selenium.interactions.internal.Locatable) webElement).getCoordinates();
    }

	@Override
	public WebElement getWrappedElement() {
		return webElement;
	}

	@Override
	public BrowserElement findById(String id) {
		/*
		 * try { logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.
		 * currentThread().getName()+" "+("Element Loc "+this.loc.getCanonicalPath()); }
		 * catch (Exception e){ e.printStackTrace(); }
		 */
		return new BrowserElementImpl(webElement.findElement(By.id(id)), driver, methodInvokedOn, this.loc);
	}

	@Override
	public BrowserElement findByName(String name) {
		return new BrowserElementImpl(webElement.findElement(By.name(name)), driver, methodInvokedOn, this.loc);
	}

	@Override
	public BrowserElement findByLinkText(String linkText) {
		return new BrowserElementImpl(webElement.findElement(By.linkText(linkText)), driver, this.methodInvokedOn,
				this.loc);
	}

	@Override
	public BrowserElement findByPartialLinkText(String partialLinkText) {
		return new BrowserElementImpl(webElement.findElement(By.partialLinkText(partialLinkText)), driver,
				this.methodInvokedOn, this.loc);
	}

	@Override
	public BrowserElement findByClass(String className) {
		return new BrowserElementImpl(webElement.findElement(By.className(className)), driver, this.methodInvokedOn,
				this.loc);
	}

	@Override
	public BrowserElement findByCssSelector(String cssSelector) {
		return new BrowserElementImpl(webElement.findElement(By.cssSelector(cssSelector)), driver, this.methodInvokedOn,
				this.loc);
	}

	@Override
	public BrowserElement findByTagName(String tagName) {
		return new BrowserElementImpl(webElement.findElement(By.tagName(tagName)), driver, this.methodInvokedOn,
				this.loc);
	}

	@Override
	public BrowserElement findByXpath(String xpath) {
		return new BrowserElementImpl(webElement.findElement(By.xpath(xpath)), driver, this.methodInvokedOn, this.loc);
	}

}