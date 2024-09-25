package utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

@ExtendWith(TestWatcherExtension.class)
public class TestBase {
    protected static WebDriver driver;
    private static String originalWindow;

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

    public static void switchToNewWindow() {
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                return;
            }
        }
        throw new NoSuchWindowException("No new window found to switch to.");
    }

    public void switchToOriginalWindow() {
        driver.switchTo().window(originalWindow);
    }
}

