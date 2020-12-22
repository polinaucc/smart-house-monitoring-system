package ua.polina.smart_house_monitoring_system.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminPage {

    public WebDriver driver;

    public AdminPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(linkText = "My rooms")
    private WebElement my_rooms_link;

    @FindBy(linkText ="Log out")
    private WebElement logout_link;

    @FindBy(linkText = "Add user to the system")
    private WebElement add_user_link;

    @FindBy(linkText = "Add house")
    private WebElement add_house_link;

    @FindBy(linkText = "House list")
    private WebElement house_list_link;

    @FindBy(linkText = "Add device")
    private WebElement add_device_link;

    @FindBy(xpath = "/html/body/header/div/h1")
    private WebElement welcome_logo;

    @FindBy(linkText = "EN")
    private WebElement en_language_link;

    @FindBy(linkText = "RU")
    private WebElement ru_language_link;
    public void goto_my_rooms() {
        my_rooms_link.click();
    }
    public void goto_logout() {
        logout_link.click();
    }
    public void goto_add_user() {
        add_user_link.click();
    }
    public void goto_add_house() {
        add_house_link.click();
    }
    public void goto_house_list() {
        house_list_link.click();
    }
    public void goto_add_device() {
        add_device_link.click();
    }

    public void change_lang_to_en() {
        en_language_link.click();
    }

    public void change_lang_to_ru() {
        ru_language_link.click();
    }

    public boolean is_lang_displayed() {
        return ru_language_link.isDisplayed() && en_language_link.isDisplayed();
    }

    public boolean is_links_displayed() {
        return logout_link.isDisplayed()&&add_house_link.isDisplayed()&&add_device_link.isDisplayed()
                &&add_user_link.isDisplayed()&&house_list_link.isDisplayed();
    }
}