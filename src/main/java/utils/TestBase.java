package utils;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Set;

@ExtendWith({TestWatcherExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestBase {
    protected static WebDriver driver;
    private static String originalWindow;
    public static String BASE_URL = "https://demo.opencart.com/";

    public static void setup(String url) {
        ChromeOptions options = new ChromeOptions();
       options.addArguments("--headless"); // Run Chrome in headless mode
       options.addArguments("--no-sandbox"); // Disable the sandbox, sometimes necessary in CI environments
       options.addArguments("--disable-dev-shm-usage"); // Avoid /dev/shm issues in Docker
        System.out.println("Starting ChromeDriver");
        driver = new ChromeDriver(options);
        driver.get(url);
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

    public static void addWaitTime(long waiter) throws InterruptedException {
        Thread.sleep(waiter);
    }

}

