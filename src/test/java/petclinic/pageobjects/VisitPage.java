package petclinic.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VisitPage {
    @FindBy(id="description")
    public static WebElement txt_description;

    @FindBy(id="date")
    public static WebElement txt_date;

    @FindBy(xpath = "/html/body/div/div/form/div[2]/div/button")
    public static WebElement btn_add;
}
