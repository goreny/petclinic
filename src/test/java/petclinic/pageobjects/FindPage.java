package petclinic.pageobjects;

import petclinic.utils.BasePageObject;
import petclinic.utils.DemoDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FindPage extends BasePageObject {

    WebDriver driver;
    public FindPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(DemoDriver.getDriver(),this);
    }
    @FindBy(id = "lastName")
    public static WebElement txt_lastName;

   // @FindBy(xpath = "//*[contains(text()='Find Owner')]")
    @FindBy(xpath = "/html/body/div/div/form/div[2]/div/button")
    public static WebElement btn_find;

    @FindBy(xpath = "//*[contains(text(),'Add Owner')]")
    public static WebElement btn_addOwner;

    public void clickAddOwner(){
        waitForElementToBeShown(driver,btn_addOwner);
        btn_addOwner.click();
    }

    public void clickFindOwner(){
        waitForElementToBeShown(driver,btn_find);
        btn_find.click();
    }

    public void setLastname(String text){
        txt_lastName.sendKeys(text);
    }

}
