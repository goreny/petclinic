package petclinic.utils;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LocatorLoader {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(LocatorLoader.class);

    private static Map<String, By> locatorMap = null;
    private static Map<String, String> dynamicMap = null;

    static {
        locatorMap = new HashMap<String, By>();
        dynamicMap = new HashMap<String, String>();
    }

    public LocatorLoader(Properties locator_properties,
            Properties dynamic_properties) {

        for (Object property_key : locator_properties.keySet()) {

            final String key = (String) property_key;
            final String locatorValue = (String) locator_properties
                    .get(property_key);

            By locator = getLocator(key, locatorValue);

            locatorMap.put(key, locator);
        }

        for (Object property_key : dynamic_properties.keySet()) {

            final String key = (String) property_key;
            final String locatorValue = (String) dynamic_properties
                    .get(property_key);

            dynamicMap.put(key, locatorValue);
        }

    }

    public static By getLocator(final String key, final String locatorValue) {

        By locator = null;

        String[] locatorId = locatorValue.split("::", 2);
        if (locatorId.length < 2) {
            LOGGER.error(
                    "Error initialising object locator: {}, Verify value: {}",
                    key, locatorValue);
        } else {
            switch (locatorId[0]) {
                case "id" :
                    locator = By.id(locatorId[1]);
                    break;
                case "xpath" :
                    locator = By.xpath(locatorId[1]);
                    break;
                case "css" :
                    locator = By.cssSelector(locatorId[1]);
                    break;
                default :
                    LOGGER.error(
                            "Locator type not supported; Error initialising object locator: {}, Verify value: {}",
                            key, locatorValue);
                    break;
            }
        }
        return locator;
    }

    public static By locator(final String key) {
        if (!locatorMap.containsKey(key)) {
            LOGGER.error("Locator for specified key is not defined in file: {}",
                    key);
        }
        return locatorMap.get(key);
    }

    public static String locatorDefinition(final String key) {
        if (!dynamicMap.containsKey(key)) {
            LOGGER.error("Locator for specified key is not defined in file: {}",
                    key);
        }
        return dynamicMap.get(key);
    }
}