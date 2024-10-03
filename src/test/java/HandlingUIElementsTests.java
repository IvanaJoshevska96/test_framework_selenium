import annotations.TestDescription;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.TestBase;

import java.time.Duration;

@Execution(ExecutionMode.SAME_THREAD)
public class HandlingUIElementsTests extends TestBase {
    static WebDriver driver;

    @BeforeEach
    public void setupOnce() {

        String BASE_URL = "https://testautomationpractice.blogspot.com/";
        System.out.println("Starting ChromeDriver");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void teardownOnce() {
        if (driver != null) {
            driver.quit();
            System.out.println("ChromeDriver session ended");
        }
    }

    @ParameterizedTest
    @Order(1)
    @ValueSource(strings = {"sunday", "saturday", "monday", "tuesday", "wednesday", "thursday", "friday"})
    @TestDescription("Verify that when the page loads,checkboxes are not checked")
    public void verifyInitialStateOfCheckboxes(String id) {
        WebElement checkBoxDay = driver.findElement(By.id(id));
        Assert.assertFalse(checkBoxDay.isSelected());
    }

    @ParameterizedTest
    @Order(2)
    @ValueSource(strings = {"sunday", "saturday", "monday", "tuesday", "wednesday", "thursday", "friday"})
    @TestDescription("Verify that the checkbox state is correctly updated after a click action")
    public void verifyStateIsCorrectlyUpdated(String id) {
        WebElement checkBoxDay = driver.findElement(By.id(id));
        checkBoxDay.click();
        Assert.assertTrue(checkBoxDay.isSelected());
    }

    @ParameterizedTest
    @Order(3)
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
    @Order(4)
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
    @Order(5)
    @ValueSource(strings = {"sunday", "saturday", "tuesday", "wednesday", "thursday", "friday"})
    @TestDescription("Verify that checking the with keyboard is not allowed")
    public void verifyCheckingWithKeyboardIsNotAllowed(String id) {
        WebElement checkBoxDay = driver.findElement(By.id(id));
        checkBoxDay.sendKeys(Keys.ENTER);
        Assert.assertFalse(checkBoxDay.isSelected());
    }

    @Test
    @Order(6)
    @TestDescription("Verify that simple alert popup is displayed after clicking on it")
    public void simpleAlert() throws InterruptedException {
        WebElement simpleAlert = driver.findElement(By.id("alertBtn"));
        simpleAlert.click();
        addWaitTime(3000L);//ms
        Alert alertWindow = driver.switchTo().alert();
        alertWindow.accept();
    }

    @Test
    @Order(7)
    @TestDescription("Verify text on alert popup is correctly displayed")
    public void simpleAlertText() throws InterruptedException {
        WebElement simpleAlert = driver.findElement(By.id("alertBtn"));
        simpleAlert.click();
        addWaitTime(3000L);//ms
        Alert alertWindow = driver.switchTo().alert();
        String alertText = alertWindow.getText();
        Assert.assertNotNull(alertText);
        Assert.assertEquals(alertText, "I am an alert box!");
    }

    @Test
    @Order(8)
    @TestDescription("Verify after clicking OK on Confirmation Alert,the corresponding text is displayed")
    public void confirmationAlertAccepting() throws InterruptedException {
        WebElement confirmationAlert = driver.findElement(By.id("confirmBtn"));
        confirmationAlert.click();
        addWaitTime(3000L);//ms
        Alert alertWindow = driver.switchTo().alert();
        alertWindow.accept();
        String confirmationMsg = driver.findElement(By.xpath("//p[@id='demo']")).getText();
        Assert.assertEquals(confirmationMsg, "You pressed OK!");
    }

    @Test
    @Order(9)
    @TestDescription("Verify after clicking OK on Confirmation Alert,the corresponding text is displayed")
    public void confirmationAlertDenying() throws InterruptedException {
        WebElement confirmationAlert = driver.findElement(By.id("confirmBtn"));
        confirmationAlert.click();
        addWaitTime(3000L);//ms
        Alert alertWindow = driver.switchTo().alert();
        alertWindow.dismiss();
        String confirmationMsg = driver.findElement(By.xpath("//p[@id='demo']")).getText();
        Assert.assertEquals(confirmationMsg, "You pressed Cancel!");
    }

    @Test
    @Order(10)
    @TestDescription("Verify that the message is correctly displayed after entering text in the input field.")
    public void promptAlert() throws InterruptedException {
        WebElement promptAlert = driver.findElement(By.id("promptBtn"));
        promptAlert.click();
        Alert prompt = driver.switchTo().alert();
        prompt.sendKeys("Harry Potter");
        prompt.accept();
        Assert.assertEquals(driver.findElement(By.id("demo")).getText(), "Hello Harry Potter! How are you today?");
    }

    @Test
    @Order(11)
    @TestDescription("Verify that the message is correctly displayed after entering empty string in the input field.")
    public void emptyStringInputPromptAlert() throws InterruptedException {
        WebElement promptAlert = driver.findElement(By.id("promptBtn"));
        promptAlert.click();
        Alert prompt = driver.switchTo().alert();
        prompt.sendKeys("   ");
        prompt.accept();
        Assert.assertEquals(driver.findElement(By.id("demo")).getText(), "Hello ! How are you today?");
    }
}
