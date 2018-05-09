package ui.support;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


public class Config {
    private static final String BROWSER = "selenium.browser";
    public static final String ENV_OSVERSION = "env.platform.version";
    public static final String ENV_BROWSERVERSION = "env.browser.version";
    public static final String ENV_PLATFORM = "env.platform.id";
    public static final String ENV_BROWSERPARAMETERS = "env.browser.parameters";
    public static final String EXECUTION_TYPE = "env.execType";
    public static final String GRID_URL = "env.gridURL";
    public static final String BANNER= "env.banner";
    public static final String REPORTINGSERVER= "env.reportingserver";
    public static final String SCREENSHOTSERVER= "env.screenshotserver";
    public static final String INFOSCREENSHOTS= "env.infoScreenshots";
    public static final String TOOLS= "env.tools";
    public static final String APPLICATION_URL ="environment.url";
    public static final String ENVIRONMENT_PROFILE = "environment.profile";
    public static final String TFS_URL = "env.tfs";
    public static final String BROWSER_VERSION = "env.browserversion";
    public static final String TFS_PROJECT_LOCATION_PREFIX = "env.tfsProjectLocationPrefix";
    private static String configFile = "Config.properties";
    private static Configuration configuration;
    private static String REPORTS_PROPERTY_FILE="reports.properties.file";
    public static String JENKINS_BUILDUSER_FIRSTNAME ="jenkins.buildUserFirstName";
    private static Configuration configData;
    
    public static String getBanner() {
        try {
            configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return configuration.getString(BANNER);
    }

    public static String getBrowser() throws Exception {
        configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        return configuration.getString(BROWSER);
    }

    public static String getApplicationUrl() throws Exception {
        configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        return configuration.getString(APPLICATION_URL);
    }

    public static String getGridUrl() throws Exception {
        configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        return configuration.getString(GRID_URL);
    }
    public static String getToolsURL() throws Exception {
        configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        return configuration.getString(TOOLS);
    }

    public static String getEnvironmentProfile() throws Exception {
        configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        return configuration.getString(ENVIRONMENT_PROFILE);
    }


    public static String getOsPlatform() throws Exception {
        configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        return configuration.getString(ENV_PLATFORM);
    }

    public static String getOsPlatformVersion() throws Exception {
        configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        return configuration.getString(ENV_OSVERSION);
    }

    public static String getBrowserParameters() throws Exception {
        configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        String params = configuration.getString(ENV_BROWSERPARAMETERS);
        if (params.length() > 0 && params.contains(":"))
            return params;
        return "";
    }

    public static String getExecutionType()  {
        try {
            configuration = new PropertiesConfiguration((loadAndGetResourceLocation(configFile)));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(EXECUTION_TYPE);
    }


    public static String loadAndGetResourceLocation(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(fileName).toString();
    }

    public static String getDataFile(String propertyName)
    {
        try {
            configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(propertyName);
    }

    public static String getJenkinsBuildUserFirstName()  {
        try {
            configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(JENKINS_BUILDUSER_FIRSTNAME);
    }

    public static String getReportPropertiesFile()  {
        try {
            configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(REPORTS_PROPERTY_FILE);
    }


    public static String getTFSURL() {

        try {
            configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(TFS_URL);

    }
    public static String getTFS_PROJECT_LOCATION_PREFIX() {

        try {
            configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(TFS_PROJECT_LOCATION_PREFIX);

    }
    public static String getBrowserVersion() {

        try {
            configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(BROWSER_VERSION);

    }
    //
    public static String getREPORTINGSERVER() {
        //
        try {
            configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(REPORTINGSERVER);
    }
    public static String getSCREENSHOTSERVER() {
        //
        try {
            configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return configuration.getString(SCREENSHOTSERVER);
    }
    public static boolean getInfoScreenShotStatus() {
        //
        try {
            configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return new Boolean(configuration.getString(INFOSCREENSHOTS));
    }
}
