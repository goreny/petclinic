package petclinic.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import petclinic.utils.DemoDriver;
import petclinic.utils.DriverUtils;

import static petclinic.utils.PropertyLoading.getInteger;

public class AddOwnerPage {

    WebDriver driver = DemoDriver.getDriver();
    public AddOwnerPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="firstName")
    public static WebElement txt_firstName;

    @FindBy(id = "lastName")
    public static WebElement txt_lastName;

    @FindBy(id = "city")
    public static WebElement txt_city;

    @FindBy(id = "address")
    public static WebElement txt_address;

    @FindBy(id = "telephone")
    public static WebElement txt_telephone;

    @FindBy(xpath = "//button[text()=\"Add Owner\"]")
    public static WebElement btn_addOwner;

    private final static Integer WAIT_FOR_ELEMENTS = getInteger("driver.wait.sec.element.display");
    private final static DriverUtils du = new DriverUtils(WAIT_FOR_ELEMENTS);

    public void addOwner(final Owner owner){
        du.type(txt_firstName,owner.getFirstName());
        du.type(txt_lastName,owner.getLastName());
        du.type(txt_address,owner.getAddress());
        du.type(txt_city,owner.getCity());
        du.type(txt_telephone,owner.getTelephone());
        du.click(btn_addOwner);
    }
}
