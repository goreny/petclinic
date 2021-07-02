package petclinic.pageobjects;

import petclinic.utils.DemoDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver = DemoDriver.getDriver();

    public HomePage(WebDriver driver){
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = "[title^='find owners']")
    public static WebElement txt_FindOwners;

    @FindBy(xpath = "[title^='veterinarians']")
    public static WebElement txt_Veterianarians;

    public void clickFindOwners(){
        txt_FindOwners.click();
    }

    public void clickVeterianarias(){
        txt_Veterianarians.click();
    }
}
