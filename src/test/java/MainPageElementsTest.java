import annotations.TestDescription;
import org.example.TestBase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainPageElementsTest extends TestBase {

    @BeforeAll
    public static void setupOnce() {
        setup();
    }

//    @AfterAll
//    public static void teardownOnce() {
//        tearDown();
//    }

    @Test
    @Order(1)
    @TestDescription("Verify that search box allows string,spaces as an input param")
    public void validateSearchBoxAllowsStringAndSpaces() {
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys("smartphone");
        Assert.assertEquals(searchBox.getAttribute("value"), "smartphone");
        //case sensitivity
        Assert.assertNotEquals(searchBox.getAttribute("value"), "SMARTPHONE");
        //clear
        searchBox.clear();
        searchBox.sendKeys("   ");
        //store the attribute in a variable
        String emptyStringInput = searchBox.getAttribute("value");
        Assert.assertEquals(emptyStringInput, "   ");
        //type uppercase
        searchBox.clear();
        searchBox.sendKeys(Keys.SHIFT + "desktop computer");
        Assert.assertEquals(searchBox.getAttribute("value"), "DESKTOP COMPUTER");
        Assert.assertNotEquals(searchBox.getAttribute("value"), "desktop computer");

    }

    @Test
    @Order(2)
    @TestDescription("Verify that the Logo is visible on the Main Page within the viewport")
    public void validateLogo() {
        WebElement logo = driver.findElement(By.id("logo"));
        Assert.assertTrue(logo.isDisplayed());
        // Check if the element is within the viewport using JavaScript
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean isInViewport = (Boolean) js.executeScript(
                "var elem = arguments[0],                 " +
                        "    box = elem.getBoundingClientRect(),   " +
                        "    cx = box.left + box.width / 2,        " +
                        "    cy = box.top + box.height / 2,        " +
                        "    e = document.elementFromPoint(cx, cy); " +
                        "for (; e; e = e.parentElement) {          " +
                        "  if (e === elem) return true;            " +
                        "}                                         " +
                        "return false;", logo);
        // Assert that the logo is within the viewport
        Assert.assertTrue(Boolean.TRUE.equals(isInViewport), "Logo should be within the viewport");
    }

    @Test
    @Order(3)
    @TestDescription("Verify that the Footer is not visible within the viewport,user should scroll down to be able to see it")
    public void validateLogoIsNotVisibleWithinViewport() {
        WebElement footer = driver.findElement(By.tagName("footer"));
        Assert.assertTrue(footer.isDisplayed());
        // Check if the element is within the viewport using JavaScript
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean isInViewport = (Boolean) js.executeScript(
                "var elem = arguments[0],                 " +
                        "    box = elem.getBoundingClientRect(),   " +
                        "    cx = box.left + box.width / 2,        " +
                        "    cy = box.top + box.height / 2,        " +
                        "    e = document.elementFromPoint(cx, cy); " +
                        "for (; e; e = e.parentElement) {          " +
                        "  if (e === elem) return true;            " +
                        "}                                         " +
                        "return false;", footer);
        // Assert that the logo is within the viewport
        Assert.assertTrue(Boolean.FALSE.equals(isInViewport), "Footer is not visible within the viewport");
    }

    @Test
    @Order(4)
    @TestDescription("Verify that the correct Links are presented")
    public void validateLinks() {
        assertEquals("Desktops", driver.findElement(By.partialLinkText("Desktop")).getText());
        assertEquals("Laptops & Notebooks", driver.findElement(By.linkText("Laptops & Notebooks")).getText());
        assertEquals("Components", driver.findElement(By.linkText("Components")).getText());
        assertEquals("Tablets", driver.findElement(By.linkText("Tablets")).getText());
        assertEquals("Software", driver.findElement(By.linkText("Software")).getText());
        assertEquals("Phones & PDAs", driver.findElement(By.linkText("Phones & PDAs")).getText());
        assertEquals("Cameras", driver.findElement(By.linkText("Cameras")).getText());
        assertEquals("MP3 Players", driver.findElement(By.linkText("MP3 Players")).getText());
    }

    @Test
    @Order(5)
    @TestDescription("Verify that the correct number of Links is presented")
    public void validateNumberOfLinks() {
        WebElement numberOfLinks = driver.findElement(By.className("navbar-collapse"));
        List<WebElement> links = numberOfLinks.findElements(By.tagName("ul"));
        int linksCount = links.size();
        assertEquals(linksCount, 8);
    }

    @Test
    @Order(6)
    @TestDescription("Validate the correct number of items on the top of the page")
    public void validateNumberOfElementInTop() {
        WebElement itemsOnTop = driver.findElement(By.className("list-inline"));
        List<WebElement> listItems = itemsOnTop.findElements(By.tagName("li"));
        //Currency is not 'li'
        assertEquals(listItems.size(), 5);
    }

    @Test
    @Order(7)
    @TestDescription("Validate the number of Links on the Main Page")
    public void validateNumberOfLinksOnMainPage() {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        assertEquals(links.size(), 76);
    }

    @Test
    @Order(8)
    @TestDescription("Validate the number of Images on the Main Page")
    public void validateNumberOfImagesOnMainPage() {
        List<WebElement> links = driver.findElements(By.tagName("img"));
        assertEquals(links.size(), 18);
    }

    @Test
    @Order(9)
    @TestDescription("Validate Currency Form is displayed")
    public void validateCurrencyForm() {
//        WebElement currency = driver.findElement(By.cssSelector("form#form-currency"));
        WebElement currency = driver.findElement(By.cssSelector("#form-currency"));
        assertTrue(currency.isDisplayed());
        //  currency.click();
    }

    @Test
    @Order(10)
    @TestDescription("Verify Shopping Cart Button is displayed")
    public void validateShoppingCartButton() {
        WebElement shippingCart = driver.findElement(By.cssSelector("button.btn-inverse"));
        assertTrue(shippingCart.isDisplayed());
        // shippingCart.click();
    }

    @Test
    @Order(11)
    @TestDescription("Verify Shopping Cart Button is displayed")
    public void validateSliderLogos() {
        Assert.assertTrue(driver.findElement(By.cssSelector("button.dropdown-toggle[data-bs-toggle='dropdown']")).isDisplayed());
    }

    @Test
    @Disabled("Test is disabled for now")
    @Order(12)
    @TestDescription("Verify that string sent for searching is correctly sent to the URL")
    public void searchForItem() {
        WebElement placeholder = driver.findElement(By.xpath("//input[@placeholder='Search']"));
        WebElement search = driver.findElement(By.xpath("//button[@class='btn btn-light btn-lg']"));
        placeholder.sendKeys("Iphone");
        search.click();
        assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("Iphone"));
    }

    @Test
    @Order(12)
    @TestDescription("Using customized Relative XPath")
    public void dropDownMenu() throws InterruptedException {
        WebElement img = driver.findElement(By.xpath("//img[@alt='iPhone 6']"));
        assertTrue(img.isDisplayed());
        driver.manage().deleteAllCookies();
        //using two attributes for the same element
        WebElement img2 = driver.findElement(By.xpath("//img[@title='Your Store'][@alt='Your Store']"));
        assertTrue(img2.isDisplayed());
        driver.manage().deleteAllCookies();
        //OR operator
        WebElement img3 = driver.findElement(By.xpath("//img[@title='Your Store' or @alt='Your Store']"));
        assertTrue(img3.isDisplayed());
        //AND operator
        WebElement img4 = driver.findElement(By.xpath("//img[@title='Your Store' and @alt='Your Store']"));
        assertTrue(img4.isDisplayed());
        WebElement link1 = driver.findElement(By.xpath("//a[text()=\"Desktops\"]"));
        //hover the Desktop link to expand Drop Down list
        Actions actions = new Actions(driver);
        // Perform hover action to expand the dropdown
        actions.moveToElement(link1).perform();
        assertTrue(driver.findElement(By.xpath("//a[text()='PC (0)']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//a[text()='Mac (1)']")).isDisplayed());
        //Laptops and Notebooks link
        WebElement link2 = driver.findElement(By.xpath("//a[text()=\"Laptops & Notebooks\"]"));
        assertTrue(link2.isDisplayed());
        actions.moveToElement(link2).perform();
        assertTrue(driver.findElement(By.xpath("//a[text()='Macs (0)']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//a[text()='Windows (0)']")).isDisplayed());
        // driver.manage().deleteAllCookies();
        //Components
        WebElement link3 = driver.findElement(By.xpath("//a[text()='Components']"));
        assertTrue(link3.isDisplayed());
        actions.moveToElement(link3).perform();
        assertTrue(driver.findElement(By.xpath("//a[text()='Mice and Trackballs (0)']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//a[text()='Monitors (2)']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//a[text()='Printers (0)']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//a[text()=\"Scanners (0)\"]")).isDisplayed());
        //Tablets
        WebElement link4 = driver.findElement(By.xpath("//a[text()='Tablets']"));
        Assert.assertTrue(link4.isDisplayed());
        //Software
        WebElement link5 = driver.findElement(By.xpath("//a[text()='Software']"));
        Assert.assertTrue(link5.isDisplayed());
        actions.moveToElement(link5).perform();
        WebElement link6 = driver.findElement(By.xpath("//*[text()='Phones & PDAs']"));
        assertTrue(link6.isDisplayed());
        assertTrue(driver.findElement(By.xpath("//*[text()='Cameras']")).isDisplayed());
    }

    @Test
    @Order(13)
    @TestDescription("'contains','starts with','chained' XPath")
    public void products() {
        assertTrue(driver.findElement(By.xpath("//h3[contains(text(), 'Featured')]")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//img[starts-with(@title,'Your')]")).isDisplayed());
        //chained
        assertTrue(driver.findElement(By.xpath("//div[@id='logo']/a/img")).isDisplayed());
    }

    @Test
    @Order(14)
    @TestDescription("Xpath Axes")
    public void xpathAxes() {
        //child
        assertTrue(driver.findElement(By.xpath("//div[@id=\"search\"]/child::input")).isDisplayed());
        //parent
        assertTrue(driver.findElement(By.xpath("//img[@class='img-fluid']/parent::a")).isDisplayed());
        //ancestor
        assertTrue(driver.findElement(By.xpath("//input[@type=\"text\"]/ancestor::div[@class=\"col-md-5\"]")).isDisplayed());
        //descendant
        assertTrue(driver.findElement(By.xpath("//div[@class=\"col-md-5\"]/descendant::input")).isDisplayed());
    }

    @Test
    @TestDescription("View page source")
    public void getMethods() {
        String pageSource = driver.getPageSource();
        System.out.println(pageSource);
    }
}