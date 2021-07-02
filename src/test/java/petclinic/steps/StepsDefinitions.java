package petclinic.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import petclinic.pageobjects.*;
import petclinic.pageobjects.ListOwnerPage;
import petclinic.pageobjects.Owner;
import petclinic.utils.DriverUtils;

import java.lang.reflect.Type;
import java.net.MalformedURLException;

import static petclinic.utils.DemoDriver.getDriver;
import static petclinic.utils.DriverUtils.loadPage;
import static petclinic.utils.PropertyLoading.getInteger;
import static petclinic.utils.PropertyLoading.getValue;

public class StepsDefinitions {

    HomePage homePage = new HomePage(getDriver());
    AddOwnerPage addOwnerPage = new AddOwnerPage(getDriver());
    FindPage findPage = new FindPage(getDriver());
    ListOwnerPage listOwnerPage = new ListOwnerPage(getDriver());

    private final static Integer WAIT_FOR_ELEMENTS = getInteger("driver.wait.sec.element.display");
    private final static DriverUtils du = new DriverUtils(WAIT_FOR_ELEMENTS);

    private static final String HOMEPAGE_URL = getValue("home.page.url");
    private static final String FIND_OWNER_URL = getValue("find.owner.page.url");

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    public Object transformer(Object fromValue, Type toValueType) {
        return objectMapper.convertValue(fromValue,objectMapper.constructType(toValueType));
    }

    @Given("the user is on the owner page page")
    public void theUserIsOnTheOwnerPagePage() {
        loadPage(HOMEPAGE_URL);
        homePage.clickFindOwners();
    }

    @When("^all owner details are added$")
    public void allOwnerDetailsAreAdded() {
        findPage.clickAddOwner();
    }

    @Then("^the owner is successfully added:$")
    public void theOwnerIsSuccessfullyAdded(final Owner owner) {
        addOwnerPage.addOwner(owner);
    }

    @Given("the user is on the Find Owner page page")
    public void theUserIsOnTheFindOwnerPagePage() {
        loadPage(FIND_OWNER_URL);
    }

    @When("the user searches for lastname {string}")
    public void theUserSearchesForLastname(String lastname) {
        findPage.setLastname(lastname);
        findPage.clickFindOwner();
    }

    @Then("the owner is displayed")
    public void theOwnerIsDisplayed() {
    }
}

