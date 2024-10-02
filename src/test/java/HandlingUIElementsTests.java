import annotations.TestDescription;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.TestBase;

import java.time.Duration;


public class HandlingUIElementsTests extends TestBase {
    static WebDriver driver;

    @BeforeAll
    public static void setupOnce() {

        String BASE_URL = "https://testautomationpractice.blogspot.com/";
        System.out.println("Starting ChromeDriver");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();
    }

    @AfterAll
    public static void teardownOnce() {
        if (driver != null) {
            driver.quit();
            System.out.println("ChromeDriver session ended");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"sunday", "saturday", "monday", "tuesday", "wednesday", "thursday", "friday"})
    @TestDescription("Verify that when the page loads,checkboxes are not checked")
    public void verifyInitialStateOfCheckboxes(String id) {
        WebElement checkBoxDay = driver.findElement(By.id(id));
        Assert.assertFalse(checkBoxDay.isSelected());
    }

    @ParameterizedTest
    @ValueSource(strings = {"sunday", "saturday", "monday", "tuesday", "wednesday", "thursday", "friday"})
    @TestDescription("Verify that the checkbox state is correctly updated after a click action")
    public void verifyStateIsCorrectlyUpdated(String id) {
        WebElement checkBoxDay = driver.findElement(By.id(id));
        checkBoxDay.click();
        Assert.assertTrue(checkBoxDay.isSelected());
    }

    @ParameterizedTest
    @ValueSource(strings = {"sunday", "saturday", "monday", "tuesday", "wednesday", "thursday", "friday"})
    @TestDescription("Verify that the each checkbox can be independently checked and unchecked")
    public void verifyIndependentlyCheckingUnchecking(String id) {
        WebElement checkBoxDay = driver.findElement(By.id(id));
        checkBoxDay.click();
        Assert.assertTrue(checkBoxDay.isSelected());
        checkBoxDay.click();
        Assert.assertFalse(checkBoxDay.isSelected());
    }

    @ParameterizedTest
    @ValueSource(strings = {"sunday", "saturday", "tuesday", "wednesday", "thursday", "friday"})
    @TestDescription("Verify that checking the 'monday' checkbox does not affect other checkboxes")
    public void verifyCheckingOneDoesNotAffectToOthers(String id) {
        for (String day : new String[]{"sunday", "saturday", "tuesday", "wednesday", "thursday", "friday", "monday"}) {
            WebElement checkBoxDay = driver.findElement(By.id(day));
            if (checkBoxDay.isSelected()) {
                checkBoxDay.click(); //ensure that all elements are unchecked
            }
            Assertions.assertFalse(checkBoxDay.isSelected(), day + " checkbox should be unchecked");
        }
        WebElement mondayDay = driver.findElement(By.id("monday"));
        mondayDay.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeSelected(mondayDay));
        Assertions.assertTrue(mondayDay.isSelected(), "Monday checkbox should be checked");
        WebElement checkBoxDay = driver.findElement(By.id(id));
        Assertions.assertFalse(checkBoxDay.isSelected(), id + " checkbox should be unchecked");
    }

    @ParameterizedTest
    @ValueSource(strings = {"sunday", "saturday", "tuesday", "wednesday", "thursday", "friday"})
    @TestDescription("Verify that checking the with keyboard is not allowed")
    public void verifyCheckingWithKeyboardIsNotAllowed(String id) {
        WebElement checkBoxDay = driver.findElement(By.id(id));
        checkBoxDay.sendKeys(Keys.ENTER);
        Assert.assertFalse(checkBoxDay.isSelected());
    }

}
