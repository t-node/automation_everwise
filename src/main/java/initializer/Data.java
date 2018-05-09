package initializer;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import ui.support.Config;

public class Data {
    private static final Logger logger = Logger.getLogger(Data.class);
    private static String dataFile_everwise = "data_everwise.properties";

    private static Configuration dataConfig;

    public static String getData(String key) throws Exception {
        dataConfig = new PropertiesConfiguration(Config.loadAndGetResourceLocation(dataFile_everwise));
        return dataConfig.getString(key);
    }
}
