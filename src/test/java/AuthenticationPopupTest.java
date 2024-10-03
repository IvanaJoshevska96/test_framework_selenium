import config.ConfigReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import utils.TestBase;

public class AuthenticationPopupTest extends TestBase {
    private WebDriver driver;
    private ConfigReader configReader;

    @BeforeEach
    public void setUp() {
        configReader = new ConfigReader();
    }

    @Test
    public void loginOnAlertPopup() {
        // Read credentials and base URL from the properties file
        String username = configReader.getUsername();
        String password = configReader.getPassword();
        String baseUrl = configReader.getBaseUrl();

        String fullUrl = "https://" + username + ":" + password + "@" + baseUrl;

        System.out.println("Starting ChromeDriver");
        System.out.println(fullUrl);
        driver = new ChromeDriver();
        driver.get(fullUrl);
        driver.manage().window().maximize();
        Assert.assertEquals(driver.findElement(By.tagName("p")).getText(), "Congratulations! You must have the proper credentials.");
        if (driver != null) {
            driver.quit();
            System.out.println("ChromeDriver session ended");
        }
    }
}
