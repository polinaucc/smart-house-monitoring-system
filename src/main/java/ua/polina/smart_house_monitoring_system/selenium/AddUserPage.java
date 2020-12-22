package ua.polina.smart_house_monitoring_system.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddUserPage {

    public WebDriver driver;

    public AddUserPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "username")
    private WebElement username_input;
    @FindBy(id = "password")
    private WebElement password_input;
    @FindBy(id = "firstname")
    private WebElement firstName_input;
    @FindBy(id = "firstnameRu")
    private WebElement firstNameRu_input;
    @FindBy(id = "middlename")
    private WebElement middleName_input;
    @FindBy(id = "middlenameRu")
    private WebElement middleNameRu_input;
    @FindBy(id = "lastname")
    private WebElement lastName_input;
    @FindBy(id = "lastnameRu")
    private WebElement lastNameRu_input;

    @FindBy(id = "passport")
    private WebElement passport_input;
    @FindBy(id = "birthday")
    private WebElement birthday_input;

    @FindBy(linkText = "EN")
    private WebElement en_language_link;

    @FindBy(linkText = "RU")
    private WebElement ru_language_link;

    @FindBy(className = "btn-primary")
    private WebElement signup_button;

    public void change_lang_to_en() {
        en_language_link.click();
    }

    public void change_lang_to_ru() {
        ru_language_link.click();
    }

    public boolean is_lang_displayed(){
        return ru_language_link.isDisplayed()&&en_language_link.isDisplayed();
    }

    public boolean is_fields_displayed(){
        return username_input.isDisplayed()&&password_input.isDisplayed();
    }

    public boolean is_form_elems_displayed(){
        return username_input.isDisplayed()&& passport_input.isDisplayed()&&
                firstName_input.isDisplayed() && firstNameRu_input.isDisplayed()&&
                middleName_input.isDisplayed()&& middleNameRu_input.isDisplayed() &&
                lastNameRu_input.isDisplayed()&& lastNameRu_input.isDisplayed()&&
                passport_input.isDisplayed()&&birthday_input.isDisplayed();
    }

    public boolean is_btn_displayed(){
        return signup_button.isDisplayed();
    }

    public void signup(String username, String password, String firstName, String middleName,
                       String lastName,String firstNameRu, String middleNameRu,
                       String lastNameRu, String passport, String birthday) {
        username_input.sendKeys(username);
        password_input.sendKeys(password);
        firstName_input.sendKeys(firstName);
        firstNameRu_input.sendKeys(firstNameRu);
        middleName_input.sendKeys(middleName);
        middleNameRu_input.sendKeys(middleNameRu);
        lastName_input.sendKeys(lastName);
        lastNameRu_input.sendKeys(lastNameRu);
        passport_input.sendKeys(passport);
        birthday_input.sendKeys(birthday);

        signup_button.click();
    }
}
