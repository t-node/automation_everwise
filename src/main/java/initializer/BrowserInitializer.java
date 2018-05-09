package initializer;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.maven.model.Model;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;


import ui.BrowserDriver;
import ui.UiBase;
import ui.utils.CaptureScreenShot;
import ui.utils.ExtentTestManager;
import ui.utils.browser.Browser;

public class BrowserInitializer {
	private static final Logger logger = Logger.getLogger(BrowserInitializer.class);
	protected ITestContext testInfo;
	protected int stepNumber = 1;
	protected Model model;
	private static DateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
	private BrowserDriver driver;

	private String stepDescription;
	private HashMap<String, Object> browserCapabilities = new HashMap<String, Object>();
	private Browser browser;
	protected Class testClass;
	public UiBase base = new UiBase();
	List<Thread> threadCollection = new ArrayList<Thread>();

	public BrowserInitializer() {
		
	}



	public void initializeDriver(Class testClass) throws Exception {
		logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + "ThreadID: " + Thread.currentThread().getId()
				+ "  " + "ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
				+ "Initializing Driver: " + testClass.getName());
		this.testClass = testClass;

		driver = DriverFactory.getDriver();
		logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + "ThreadID: " + Thread.currentThread().getId()
				+ "  " + Thread.currentThread().getName() + " " + "Instantiated Driver: " + testClass.getName());
		setTimeOuts();
		base.setDriver(driver);
		driver.manage().window().maximize();
	}

	public void setDriver(BrowserDriver driver) {
		this.driver = driver;
	}

	public BrowserDriver getDriver() {
		return this.driver;
	}

	private void setTimeOuts() {
		driver.manage().timeouts().implicitlyWait(UiBase.getDefaultImplicitTimeout(), TimeUnit.SECONDS);
	}

	public void shutDownBrowser() {
		logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + "ThreadID: " + Thread.currentThread().getId()
				+ "  " + Thread.currentThread().getName() + " " + "COMPLETED RUN FOR : " + this.testClass.getName());
		driver.quit();
	}



	public void Assert(boolean result, String stepDescription) {
		this.stepDescription = stepDescription;
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[2];// maybe this number needs to be
		String status="FAIL";// corrected
		String methodName = e.getMethodName();
		try {
			String screenShot = CaptureScreenShot.captureScreen(driver.getWebDriver(),
					CaptureScreenShot.generateFileName(methodName));
			String fileName = screenShot.split("\\\\")[screenShot.split("\\\\").length - 1];
			if (!result) {
				ExtentTestManager.getTest().fail(markUp(stepDescription,screenShot));
				status="FAIL";
				Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
			} else {
				ExtentTestManager.getTest().pass(markUp(stepDescription,screenShot));
				status="PASS";
			}
			//ExtentTestManager.getScreenShotLst().add(screenShot);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static String markUp(String stepDescription,String screenShot){
    /*	return "<span ng-bind-html=\"trust(log.details)\" class=\"ng-binding\">"+stepDescription+"</span>\n" +
                "                    <!-- ngIf: log.media.length>0 --><span ng-if=\"log.media.length>0\" class=\"ng-scope\">\n" +
                "                        <!-- ngRepeat: media in log.media --><span ng-repeat=\"media in log.media\" class=\"ng-scope\">\n" +
                "<!-- ngIf: media.mediaType=='img' --><!-- end ngIf: media.mediaType=='img' -->\n" +
                "                        </span><!-- end ngRepeat: media in log.media -->\n" +
                "                    </span><!-- end ngIf: log.media.length>0 -->\n" +
                "                </td></span>\n" +
                "                    <!-- ngIf: log.media.length>0 -->";*/


		return "<span ng-bind-html=\"trust(log.details)\" class=\"ng-binding\">"+stepDescription+"</span>\n" +
				"                    <!-- ngIf: log.media.length>0 --><span ng-if=\"log.media.length>0\" class=\"ng-scope\">\n" +
				"                        <!-- ngRepeat: media in log.media --><span ng-repeat=\"media in log.media\" class=\"ng-scope\">\n" +
				"<!-- ngIf: media.mediaType=='img' --><img width=\"10%\" class=\"report-img ng-scope\"" +
				" data-featherlight" +"="+screenShot+" " +
				"src="+screenShot+" " +"ng-if=\"media.mediaType=='img'\"><!-- end ngIf: media.mediaType=='img' -->\n" +
				"                        </span><!-- end ngRepeat: media in log.media -->\n" +
				"                    </span><!-- end ngIf: log.media.length>0 -->\n" +
				"                </td></span>\n" +
				"                    <!-- ngIf: log.media.length>0 -->";


	}
	


}
