package automated_tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ua.polina.smart_house_monitoring_system.selenium.AdminPage;
import ua.polina.smart_house_monitoring_system.selenium.PageElements;
import ua.polina.smart_house_monitoring_system.selenium.UserPage;

import javax.validation.Validator;
import java.util.concurrent.TimeUnit;

public class signin_test  {
    public static PageElements pageElements;
    public static WebDriver driver;
    public static UserPage userPage;
    public static AdminPage adminPage;

    private Validator validator;
    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver = new ChromeDriver();
        pageElements = new PageElements(driver);
        userPage= new UserPage(driver);
        adminPage= new AdminPage(driver);
        //driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/auth/login");
    }

    @After
    public void after() {
        driver.quit();
    }

    //Verifying elements on Registration page
    @Test
    public void verifyElementsOnPageTest()
    {
        pageElements.is_btn_displayed();
        pageElements.is_fields_displayed();
        pageElements.is_lang_displayed();

    }

    @Test
    public void loginTest()
    {
        pageElements.login("admin", "admin");

        String expectedURL = "http://localhost:8080/admin/index";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL);

        String expectedTitle = "Title";
        String actualTitle = driver.getTitle();
        System.out.println(actualTitle);
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test
    public void testit()
    {
        pageElements.login("user4", "user4");
        adminPage.goto_my_rooms();
        userPage.gotoDeviceList(2);
    }




}