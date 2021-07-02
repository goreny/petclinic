package petclinic.utils;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import static petclinic.utils.PropertyLoading.getInteger;
import static petclinic.utils.PropertyLoading.getValue;

public class DemoDriver {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DemoDriver.class);

    public static WebDriver driver = null;

    public static WebDriver getDriver() {

        if (driver == null) {
            initialiseDriver();
            driver.manage().timeouts().implicitlyWait(
                    getInteger("driver.wait.sec.implicit"), TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
        return driver;
    }

    private static void initialiseDriver() {

        final String browserName = getBrowserName();

        final Map<String, List<String>> driverPaths = setDriverPath(
                browserName);

        boolean driverInitialised = doInitialiseDriver(browserName,
                driverPaths);

        if (!driverInitialised) {
            LOGGER.error("All routes for {} driver initialisation has failed",
                    browserName);
            new IllegalStateException(
                    "All routes for driver initialisation has failed. Please fix driver initialisation to proceed further.");
        }
    }

    private static boolean doInitialiseDriver(final String browserName,
            final Map<String, List<String>> driverPaths) {

        boolean driverInitialised = false;

        // Try to initialise chrome driver with no setting to accommodate
        // different ways the driver is configured
        driverInitialised = isDriverSuccess(browserName);

        if (!driverInitialised) {

            for (Entry<String, List<String>> driverSetting : driverPaths
                    .entrySet()) {

                if (!driverInitialised) {
                    for (String driverSettingPath : driverSetting.getValue()) {

                        final String driverKey = getValue(
                                driverSetting.getKey());

                        LOGGER.info(
                                "Initialising {} driver with setting {} = {}",
                                browserName, driverKey, driverSettingPath);

                        System.setProperty(driverKey, driverSettingPath);
                        driverInitialised = isDriverSuccess(browserName);

                        if (driverInitialised) {
                            LOGGER.info(
                                    "Driver initialisation for above setting was successful",
                                    browserName, driverKey, driverSettingPath);
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
        }

        return driverInitialised;
    }

    private static boolean isDriverSuccess(final String browserName) {

        boolean driverInitialised = true;

        try {
            switch (browserName) {

                case "firefox" :
                    driver = new FirefoxDriver();
                    break;

                case "chrome" :
                    driver = new ChromeDriver();
                    break;

                default :
                    LOGGER.error(
                            "Provided browser currently not supported - {}",
                            browserName);
                    driverInitialised = false;
                    break;
            }

        } catch (Exception e) {
            LOGGER.error("Error initialising {} driver; Error: {}", browserName,
                    e.getMessage());
            driverInitialised = false;
        }

        return driverInitialised;
    }

    private static String getBrowserName() {

        String browserName;

        final String systemSettingBrowser = System
                .getProperty("driver.browser.name");

        if (StringUtils.isEmpty(systemSettingBrowser)) {
            browserName = getValue("driver.browser.name").toLowerCase().trim();
        } else {
            browserName = systemSettingBrowser.toLowerCase().trim();
        }

        return browserName;
    }

    private static Map<String, List<String>> setDriverPath(
            final String browserName) {

        Map<String, List<String>> driverPaths = new HashMap<String, List<String>>();

        try {

            final String driverSettingValue = new StringBuilder(
                    "driver.setting.").append(browserName).toString();

            driverPaths = initialisePackagedDriver(browserName,
                    driverSettingValue);

        } catch (Exception e) {
            LOGGER.error("Error initialsing browser driver - {}; Error: {}",
                    browserName, e);
        }

        return driverPaths;
    }

    private static Map<String, List<String>> initialisePackagedDriver(
            final String browserName, final String driverSettingValue) {

        Map<String, List<String>> driverPaths = new HashMap<String, List<String>>();

        try {
            final String driverPath = getValue(
                    new StringBuilder(driverSettingValue).append(".path")
                            .toString());

            StringBuilder driverPathExe = new StringBuilder(driverSettingValue);

            if (SystemUtils.IS_OS_WINDOWS) {
                driverPathExe.append(".win.exe");

            } else if (SystemUtils.IS_OS_MAC) {
                driverPathExe.append(".mac.exe");

            } else {
                LOGGER.error("Provided OS currently not supported - {}",
                        SystemUtils.OS_NAME);
            }

            final String driverPathExeValue = getValue(
                    driverPathExe.toString());

            File[] driverFolders = new File(driverPath).listFiles(
                    (FileFilter) FileFilterUtils.directoryFileFilter());
            List<File> folderList = Arrays.asList(driverFolders);
            Collections.sort(folderList, Collections.reverseOrder());
            List<String> driverFilePaths = new ArrayList<>();

            for (File driverFolder : driverFolders) {
                driverFilePaths.add(new StringBuilder(driverFolder.getPath())
                        .append(File.separator).append(driverPathExeValue)
                        .toString());
            }

            driverPaths.put(driverSettingValue, driverFilePaths);

        } catch (Exception e) {
            LOGGER.error(
                    "Error initialsing packaged browser driver - {}; Error: {}",
                    browserName, e);
        }

        return driverPaths;
    }

    public static void closeDriver() {
        getDriver().quit();
        driver = null;
    }
}