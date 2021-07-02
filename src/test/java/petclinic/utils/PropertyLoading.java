package petclinic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class PropertyLoading {

    private static final String PROPERTIES_CONFIG = "config/config.properties";
    private static final String PROPERTIES_LOCATOR = "locator/locator.properties";
    private static final String PROPERTIES_DYNAMIC_LOCATOR = "locator/dynamic.locator.properties";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyLoading.class);

    private static Properties properties = null;

    static {

        Properties file_properties = new Properties();
        loadFile(PROPERTIES_CONFIG, file_properties);

        properties = new Properties();

        for (Object file_name : file_properties.keySet()) {
            loadFile((String) file_name, properties);
        }

        Properties locator_properties = new Properties();
        loadFile(PROPERTIES_LOCATOR, locator_properties);

        Properties dynamic_locator_properties = new Properties();
        loadFile(PROPERTIES_DYNAMIC_LOCATOR, dynamic_locator_properties);
        
        new LocatorLoader(locator_properties, dynamic_locator_properties);
        
        locator_properties = null;
        dynamic_locator_properties = null;
        file_properties = null;
    }

    private static void loadFile(final String file, Properties property_repo) {

        InputStream input;

        try {
            input = ClassLoader.getSystemResourceAsStream(file);
            property_repo.load(input);

        } catch (Exception e) {
            LOGGER.error("Error loading properties file: {}; Error: {}", file,
                    e.getMessage());
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getValue(final String key) {
        return getProperties().getProperty(key);
    }

    public static Integer getInteger(final String key) {
        return Integer.valueOf(getProperties().getProperty(key).trim());
    }
}