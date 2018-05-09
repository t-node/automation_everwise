package initializer;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import ui.BrowserDriver;
import ui.BrowserDriverImpl;
import ui.support.Config;
import ui.utils.browser.Browser;
import ui.utils.browser.ChromeDriverDownloader;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;

public class DriverFactory {
    private static final Logger logger = Logger.getLogger(DriverFactory.class);
    static File chromedriver;
    private static Browser browser;
    private static final String PLATFORM = "platform";
    private static final String BROWSER_NAME = "browserName";
    private static final String BROWSER_PARAMETERS = "browserParameters";
    private static final String BROWSER_VERSION = "version";
    private static HashMap<String, Object> browserCapabilities = new HashMap<String, Object>();

    public static BrowserDriver getDriver() throws Exception {
        setBrowserCapabilities();
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        switch (Config.getBrowser() + Config.getExecutionType()) {
            case "chromelocal":
                desiredCapabilities = DesiredCapabilities.chrome();
                setDesiredCapabilities(desiredCapabilities);
                System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());
                return new BrowserDriverImpl(new ChromeDriver(desiredCapabilities));
            case "firefoxlocal":
                desiredCapabilities = DesiredCapabilities.firefox();
                setDesiredCapabilities(desiredCapabilities);
                return new BrowserDriverImpl(new FirefoxDriver());
            case "firefoxgrid":
            case "chromegrid":
                setDesiredCapabilities(desiredCapabilities);
                RemoteWebDriver driver = new RemoteWebDriver(new URL(Config.getGridUrl()), desiredCapabilities);
                driver.setFileDetector(new LocalFileDetector());
                return new BrowserDriverImpl(driver);
            default:
                return new BrowserDriverImpl(new ChromeDriver());

        }

    }

    private static void setBrowserCapabilities() throws Exception {

        browserCapabilities.put(PLATFORM, Config.getOsPlatform());
        browserCapabilities.put(BROWSER_NAME, Config.getBrowser());
        browserCapabilities.put(BROWSER_PARAMETERS, Config.getBrowserParameters());
        browserCapabilities.put(BROWSER_VERSION, Config.getBrowserVersion());

    }

    private static Browser setDesiredCapabilities(DesiredCapabilities capabilities) throws Exception {
        browser = Browser.getBrowser(browserCapabilities.get(BROWSER_NAME).toString());
        capabilities.setBrowserName(browser.name().toLowerCase());
        setBrowserParameters(capabilities);
        if (browser.name() == "CHROME") {
            setChromeCapabilities(capabilities);
        }
        if (browser.name() == "FIREFOX") {
            setFirefoxCapabilities(capabilities);
        }
        return browser;
    }

    private static void setBrowserParameters(DesiredCapabilities capabilities) {
        if (browserCapabilities.get(BROWSER_PARAMETERS).toString().length() > 0) {
            String[] parameters = browserCapabilities.get(BROWSER_PARAMETERS).toString().split(";");
            setBrowserParameters(capabilities, parameters);
        }
    }

    private static void setBrowserParameters(DesiredCapabilities capabilities, String[] parameters) {
        for (String parameter : parameters) {
            if (!parameter.isEmpty()) {
                String[] key_value = parameter.split(":");
                capabilities.setCapability(key_value[0], key_value[1]);
            }
        }
    }

    private static void setChromeCapabilities(DesiredCapabilities capabilities) throws Exception {
        capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
        if (!Config.getExecutionType().equalsIgnoreCase("grid")) {
            chromedriver=new File(System.getProperty("user.dir")+"/lib/chromedriver");
            capabilities.setCapability(CHROME_DRIVER_EXE_PROPERTY, System.getProperty("user.dir")+"/lib/chromedriver");
        }

    }

    private static void setFirefoxCapabilities(DesiredCapabilities capabilities) throws Exception {
        if (!Config.getExecutionType().equalsIgnoreCase("grid")) {
            //firefoxdriver = new ChromeDriverDownloader().downloadAndExtract();
            // capabilities.setCapability(CHROME_DRIVER_EXE_PROPERTY, chromedriver.getAbsolutePath());
        }
    }
}
