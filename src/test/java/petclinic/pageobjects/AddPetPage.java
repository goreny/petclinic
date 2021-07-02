package petclinic.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddPetPage {

    @FindBy(id="name")
    public static WebElement txt_name;

    @FindBy(id = "birthDate")
    public static WebElement txt_birthDate;

    @FindBy(id="type")
    private WebElement Select;

}
