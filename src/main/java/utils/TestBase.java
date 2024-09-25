package utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@ExtendWith(TestWatcherExtension.class)
public class TestBase {
    protected static WebDriver driver;

    public static void setup() {

        System.out.println("Starting ChromeDriver");
        driver = new ChromeDriver();
        driver.get("https://demo.opencart.com/");
        driver.manage().window().maximize();

    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("ChromeDriver session ended");
        }
    }
}

