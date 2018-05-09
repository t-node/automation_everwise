package ui.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * Created by vevinmoza on 10/22/17.
 */

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import ui.support.Config;

public class ExtentManager {
	private static final Logger logger = Logger.getLogger(ExtentManager.class);
	private static ExtentReports extent;

	public static synchronized ExtentReports getInstance() {
		return extent;
	}

	public static synchronized ExtentReports createInstance(String fileName, String projectName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("QA Automation Report");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);


		extent = new ExtentReports();
		if (projectName.equalsIgnoreCase("Default test")) {
			extent.attachReporter(htmlReporter);
		} else {
			extent.attachReporter(htmlReporter);
		}
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		try {
			extent.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName() + " " + e);
		}
		return extent;
	}

}
