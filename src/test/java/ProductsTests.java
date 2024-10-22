import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.TestBase;
import utils.TestWatcherExtension;

import java.time.Duration;

@ExtendWith({TestWatcherExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductsTests extends TestBase {

    public String product;


    public void setupProduct() {
        setup(BASE_URL + "en-gb/product/" + product);
        driver.get(BASE_URL + "en-gb/product/" + product);
    }

    @AfterAll
    public static void teardownOnce() {
        tearDown();
    }

    @ParameterizedTest
    @ValueSource(strings = {"macbook", "iphone"})
    public void verifyMacBookItem(String productValue) {
        this.product = productValue;
        setupProduct();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); //wait for the robot verification to pass
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='product-info']//div[@class='row']//div[@id='content']//div[@class='col-sm']//h1"))
        );
        String actualTitle = driver.findElement(By.xpath("//div[@id='product-info']//div[@class='row']//div[@id='content']//div[@class='col-sm']//h1")).getText();
        if (productValue.equals("macbook")) {
            Assert.assertEquals(actualTitle.toLowerCase(), "macbook");
        } else if (productValue.equals("iphone")) {
            Assert.assertEquals(actualTitle.toLowerCase(), "iphone");
        }
    }
}
