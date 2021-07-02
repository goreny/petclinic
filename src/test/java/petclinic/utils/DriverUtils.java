package petclinic.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static petclinic.utils.DemoDriver.getDriver;
import static petclinic.utils.LocatorLoader.locator;

public class DriverUtils {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DriverUtils.class);

    private Integer wait = 0;

    public static void loadPage(final String url) {
        if (!getDriver().getCurrentUrl().contentEquals(url)) {
            getDriver().get(url);
        }
    }

    public DriverUtils(final Integer wait_in_secs) {
        this.wait = wait_in_secs;
    }

    public boolean isDislayed(final String locatorVal) {

        boolean isElementDisplayed = false;

        try {
            getWebElement(locatorVal);
            isElementDisplayed = true;
        } catch (TimeoutException exp) {
            isElementDisplayed = false;
        }

        return isElementDisplayed;
    }

    public void type(final WebElement we, final String text) {

        try {
           // WebElement we = getWebElement(locatorVal);
            we.clear();
            we.sendKeys(text);

        } catch (TimeoutException | IllegalArgumentException exp) {
            LOGGER.error(
                    "Unable to type '{}' in element specified by locators: {}; Error: {}",
                    text, we, exp);
        }
    }

    public void click(final WebElement we) {

        try {
            we.click();
        } catch (TimeoutException | StaleElementReferenceException exp) {
            LOGGER.error(
                    "Unable to click on element specified by locators: {}; Error: {}",
                    we, exp);
        }
    }

    public void select(final String locatorVal, final String selectValue) {
        try {
            WebElement we = getWebElement(locatorVal);
            Select ws = new Select(we);
            ws.selectByVisibleText(selectValue);

        } catch (TimeoutException | UnexpectedTagNameException
                | NoSuchElementException exp) {

            LOGGER.error(
                    "Unable to select '{}' from the element specified by locators: {}; Error: {}",
                    selectValue, locatorVal, exp);
        }
    }

    public Boolean assertText(final String locatorVal, final String expected) {
        String actual = null;
        try {
            WebElement we = getWebElement(locatorVal);
            actual = we.getText();
        } catch (TimeoutException exp) {
            LOGGER.error("Unable to get text from element specified by locators: {}; Error: {}",locatorVal, exp);
        }
        return actual.contains(expected);
    }

    public Integer getWebElementCount(final String locatorVal) {
        Integer rowSize = 0;
        try {
            List<WebElement> rows = getWebElements(locatorVal);
            rowSize = Integer.valueOf(rows.size());

        } catch (TimeoutException exp) {
            LOGGER.error("Unable to get element count specified by locators: {}; Error: {}",locatorVal, exp);
        }
        return rowSize;
    }

    private WebElement getWebElement(final String locatorVal) {
        return new WebDriverWait(getDriver(), wait).until(ExpectedConditions.visibilityOfElementLocated(locator(locatorVal)));
    }

    private List<WebElement> getWebElements(final String locatorVal) {
        return getDriver().findElements(locator(locatorVal));
    }
}