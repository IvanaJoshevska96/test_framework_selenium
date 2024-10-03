package config;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthenticationHandler {
    private WebDriver driver;
    private ConfigReader configReader;

    public AuthenticationHandler(WebDriver driver) {
        this.driver = driver;
        this.configReader = new ConfigReader();
    }

    public void handleAuthenticationPopup() {
        // Read credentials from properties
        String username = configReader.getUsername();
        String password = configReader.getPassword();

        // Wait for the authentication popup (alert) to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Alert authPopup = wait.until(ExpectedConditions.alertIsPresent());

        // Send credentials to the alert popup
        authPopup.sendKeys(username + "\t" + password);

        // Accept the popup to submit the credentials
        authPopup.accept();
    }
}
