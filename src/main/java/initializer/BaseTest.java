package initializer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cucumber.api.testng.TestNGCucumberRunner;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.itextpdf.text.DocumentException;

import atu.testrecorder.ATUTestRecorder;
import ui.BrowserDriver;
import ui.support.Config;
import ui.utils.ExtentManager;
import ui.utils.ExtentTestManager;
import ui.utils.PDFGenerator;

public class BaseTest extends PageInitializer {

	ITestResult result;
	boolean possibleFailure = true;
	private static final Logger logger = Logger.getLogger(BaseTest.class);
	protected final String fileName = getClass().getSimpleName();
	private Map<String, String> localStorage;
	ATUTestRecorder recorder;

	public void setUpPageInitializer(BrowserDriver driver) {
		initializePages(driver, this.getClass());
		localStorage = new HashMap<>();
		logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
				+ "Initialized Pages and Setup Local Storage");

	}


	public static String getData(String key) throws Exception {
		return Data.getData(key);
	}

	public void addToStorage(String key, String value) {
		localStorage.put(key, value);
	}

	public String getFromStorage(String key) {
		return localStorage.get(key);
	}

	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext testInfo) throws Exception {
		String projectName = testInfo.getName();
		if (testInfo.getIncludedGroups().length > 0) {
			projectName = testInfo.getIncludedGroups()[0];
		}
		logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
				+ "Create Report HTML");

		if (ExtentManager.getInstance() == null) {
			ExtentManager.createInstance(
					System.getProperty("user.dir") + File.separator + "target" + File.separator + "TestReport.html",
					projectName);
			logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
					+ Thread.currentThread().getName() + " Created instance for report");
		}

		this.testInfo = testInfo;

		File VideoDirectory=new File(System.getProperty("user.dir")+"/Videos");
		if(!VideoDirectory.exists())
		{
			VideoDirectory.mkdir();
		}
		recorder = new ATUTestRecorder(VideoDirectory.toString(), this.getClass().getName().toString(), false);

		initializeDriver(this.getClass());
		recorder.start();
		getDriver().get(Config.getApplicationUrl());
		ExtentTestManager.createTest(testInfo.getSuite().getAllMethods().get(0).getMethodName(),
				testInfo.getSuite().getAllMethods().get(0).getMethodName());
		setUpPageInitializer(getDriver());
	}



	@AfterMethod(alwaysRun = true)
	public void generatePdfReport(ITestResult result) {
		this.possibleFailure = false;

		this.result = result;
		logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
				+ "FINAL RESULT STATUS" + " for " + result.getStatus());
		if (result.getStatus() == ITestResult.FAILURE) {
			if (result.getThrowable() != null) {
				StringBuilder sBuilder = new StringBuilder();
				for (StackTraceElement e : result.getThrowable().getStackTrace()) {
					sBuilder.append(e.toString()).append("<br>");
				}
				sBuilder.toString();
				ExtentTestManager.getTest().fail(MarkupHelper.createLabel(
						result.getName() + "<br>" + result.getThrowable() + "<br>" + sBuilder + "<br>Test Case Failed",
						ExtentColor.RED));
			} else {
				ExtentTestManager.getTest()
						.fail(MarkupHelper.createLabel(result.getName() + "<br>Test Case Failed", ExtentColor.RED));
			}

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentTestManager.getTest()
					.pass(MarkupHelper.createLabel(result.getName() + "Test Case Passed", ExtentColor.GREEN));
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getTest()
					.skip(MarkupHelper.createLabel(result.getName() + "Test Case Skipped", ExtentColor.YELLOW));
		}

		ExtentManager.getInstance().flush();
		logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
				+ "PDF ABOUT TO BE GENERATED" + " for " + result.getName());
		try {
			PDFGenerator.generatePDF(ExtentTestManager.getScreenShotLst(),
					System.getProperty("user.dir") + File.separator + "target" + File.separator
							+ testClass.getSimpleName() + "___" + result.getMethod().getMethodName() + ".pdf");
		} catch (IOException io) {
			logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
					+ "ERROR PDF CORRUPT");
		} catch (DocumentException de) {
			logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
					+ "ERROR PDF CORRUPT");
		}

		logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
				+ "PDF GENERATED");
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		if (result != null) {
			if (possibleFailure) {
				result.setStatus(ITestResult.FAILURE);
				logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName()
						+ " " + "RESULT IS SET FOR FAILURE EXPLICITLY BECAUSE PDF CODE NOT PARSED");
			}
		} else {
			logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
					+ "RESULT IS NULL");
		}

		/*
		 * logger.info("ThreadID: " + Thread.currentThread().getId() + "  " +
		 * Thread.currentThread().getName() + " " + "TEAR DOWN RESULT STATUS" +
		 * " for " + result.getStatus());
		 */
		recorder.stop();
		shutDownBrowser();
	}
}