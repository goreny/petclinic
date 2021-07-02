package petclinic.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import petclinic.utils.BasePageObject;
import petclinic.utils.DemoDriver;
import petclinic.utils.DriverUtils;

import java.util.List;

public class ListOwnerPage extends BasePageObject {

    WebDriver driver = DemoDriver.getDriver();

    public ListOwnerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    DriverUtils du;

    @FindBy(css = "[title^='Edit Owner']")
    public static WebElement btn_EditOwner;

    @FindBy(xpath = "[title^='Add New Pet']")
    public static WebElement btn_AddNewPet;

    public void clickEdit() {
        waitForElementToBeShown(driver, btn_AddNewPet);
        btn_EditOwner.click();
    }

    public void clickAddNewPet() {
        waitForElementToBeShown(driver, btn_AddNewPet);
        btn_AddNewPet.click();
    }

    public void checkText(final List<Owner> owner){
        for(int i=1; i<5; i++){
            for (Owner a: owner) {
                du.assertText("/html/body/div/div/table[1]/tbody/tr["+i+"]/td/b",owner.get(i).toString());
            }
        }
    }
}
