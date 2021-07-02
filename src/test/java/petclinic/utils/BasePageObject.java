package petclinic.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class BasePageObject {

    public static final long DEFAULT_TIMEOUT_SECONDS = 60;

    public static final Logger LOGGER = LoggerFactory.getLogger(BasePageObject.class);

    public static WebDriver driver;

    DriverUtils du;

    public BasePageObject() {
        this.driver = getDriver();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public static void markWithRedBorder(JavascriptExecutor driver, WebElement element) {
        try {
            driver.executeScript("arguments[0].scrollIntoView(false);", element);
            driver.executeScript("arguments[0].style.border='3px solid red'", element);
        } catch (WebDriverException e) {
            if (e.getMessage().contains("arguments[0] is undefined")) {
                LOGGER.warn("Selenium failed to frame element waiting for to be visible.");
            } else {
                throw e;
            }
        }
    }

    public static StringBuilder getAssertionMessage(final String expected, final String actual, final String locator_message) {
        final StringBuilder message = new StringBuilder();
        message.append(locator_message).append("; ").append("Expected value: ")
                .append(expected).append("; Actual value: ").append(actual);
        return message;
    }

    public static void waitForElementToBeVisible(WebDriver driver, By locator, long timeoutSeconds) {
        LOGGER.info("Start verifying that element is visible: " + locator.toString());
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element = driver.findElement(locator);
            markWithRedBorder((JavascriptExecutor) driver, element);
        } catch (StaleElementReferenceException e) {
            LOGGER.info("Attempting to recover from StaleElementReferenceException");
            waitForElementToBeVisible(driver, locator);
        }
        LOGGER.info("finish verifying element is visible: " + locator.toString());
    }

    public static void waitForElementToBeShown(WebDriver driver, WebElement element, long timeoutSeconds) {
        LOGGER.info("Start verifying that element is visible: " + element.toString());
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds, 50);
            wait.until(ExpectedConditions.visibilityOfAllElements(Arrays.asList(element)));
            markWithRedBorder((JavascriptExecutor) driver, element);
        } catch (StaleElementReferenceException e) {
            LOGGER.info("Attempting to recover from StaleElementReferenceException");

        }
        LOGGER.info("finish verifying element is visible: " + element.toString());
    }

    public static void waitForElementToBeShown(WebDriver driver, WebElement element) {
        waitForElementToBeShown(driver, element, DEFAULT_TIMEOUT_SECONDS);
    }

    public static void waitForElementToBeVisible(WebDriver driver, By locator) {
        waitForElementToBeVisible(driver, locator, DEFAULT_TIMEOUT_SECONDS);
    }

    public void assertDisplayed(final String locator_value) {
        doAssertDisplay(locator_value, Boolean.TRUE);
    }

    private void doAssertDisplay(final String locator_value, final Boolean expected) {
        final Boolean actual = du.isDislayed(locator_value);
        final StringBuilder msg = BasePageObject.getAssertionMessage(expected.toString(), actual.toString(), "Element Display: " + locator_value);
        assertEquals(msg.toString(), expected, actual);
    }
}
