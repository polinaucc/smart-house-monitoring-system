package automated_tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ua.polina.smart_house_monitoring_system.selenium.AddUserPage;
import ua.polina.smart_house_monitoring_system.selenium.AdminPage;
import ua.polina.smart_house_monitoring_system.selenium.PageElements;

import javax.validation.Validator;
import java.util.concurrent.TimeUnit;

public class admin {
    public static AdminPage adminPage;
    public static AddUserPage addUserPage;
    public static PageElements pageElements;
    public static WebDriver driver;

    private Validator validator;
    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver = new ChromeDriver();
        adminPage= new AdminPage(driver);
        pageElements = new PageElements(driver);
        addUserPage = new AddUserPage(driver);
        //driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/auth/login");
        pageElements.login("admin", "admin");
    }

    @After
    public void after() {
        driver.quit();
    }

    //Verifying elements on Registration page
    @Test
    public void verifyElementsOnPageTest()
    {
        adminPage.is_links_displayed();
        adminPage.is_lang_displayed();

    }

    @Test
    public void logoutRedirectionTest()
    {
        adminPage.goto_logout();
        String expectedURL = "http://localhost:8080/auth/login?logout";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL);
    }

    @Test
    public void addUserRedirectionTest()
    {
        adminPage.goto_add_user();
        String expectedURL = "http://localhost:8080/admin/sign-up";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL);
    }

    @Test
    public void addHouseRedirectionTest()
    {
        adminPage.goto_add_house();
        String expectedURL = "http://localhost:8080/admin/add-house";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL);
    }

    @Test
    public void addDeviceRedirectionTest()
    {
        adminPage.goto_add_device();
        String expectedURL = "http://localhost:8080/admin/add-device";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL);
    }

    @Test
    public void listHouseRedirectionTest()
    {
        adminPage.goto_house_list();
        String expectedURL = "http://localhost:8080/admin/houses";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL);
    }

    @Test
    public void signupTest()
    {
        adminPage.goto_add_user();
        addUserPage.signup("sfnksosfgf", "user5", "Mykola", "Lukashyn",
                "Mykolaivoych", "Микола", "Миколайович", "Миколайович",
                "ВХ439678", "09252013");

        String expectedURL = "http://localhost:8080/admin/index";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL);

        String expectedTitle = "Title";
        String actualTitle = driver.getTitle();
        System.out.println(actualTitle);
        Assert.assertEquals(actualTitle, expectedTitle);
    }


}
