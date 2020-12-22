package ua.polina.smart_house_monitoring_system.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserPage {

    public WebDriver driver;

    public UserPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(linkText = "Device list")
    private WebElement device_list_link;

    @FindBy(linkText ="Set up room parameters")
    private WebElement room_params_link;

    @FindBy(linkText = "Fire")
    private WebElement fire_link;

    @FindBy(linkText = "Flood")
    private WebElement flood_link;

    @FindBy(linkText = "Open window")
    private WebElement open_window_link;

    @FindBy(linkText = "EN")
    private WebElement en_language_link;

    @FindBy(linkText = "RU")
    private WebElement ru_language_link;

    @FindBy(className = "table")
    private WebElement roomList;


    public WebElement getCertainRoom(int roomId) {
        return driver.findElement(By.cssSelector("table tr:nth-of-type("+roomId+")"));
    }

    public void gotoDeviceList(int roomId) {
        getCertainRoom(roomId).findElement(By.linkText("Device list")).click();
        //device_list_link.click();
    }
}
