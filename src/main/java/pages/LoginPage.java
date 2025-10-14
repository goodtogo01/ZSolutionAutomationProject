package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.SeleniumUtils;

public class LoginPage {

	private WebDriver driver;
	public SeleniumUtils seleniumUtils;

	@FindBy(xpath = "//*[@id='username']")
	private WebElement userName;

	// *[@id='username']

	@FindBy(xpath = "//*[@id='password']")
	private WebElement passwords;

	@FindBy(xpath = "//button[contains(text(), 'Login')]")
	private WebElement loginButton;

	By logoutButton = By.xpath("//button[normalize-space(text())='Logout']");

	@FindBy(id = "error-message")
	private WebElement errorMessage;

	   // ✅ Proper constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.seleniumUtils = new SeleniumUtils(driver); // Initialize SeleniumUtils here
        PageFactory.initElements(driver, this);
    }

	// Set User name
	public void setUserName(String user) {
		userName.sendKeys(user);
	}

	// Set Password
	public void setPassword(String pass) {
		passwords.sendKeys(pass);
	}

	// Perform Login
	public void clickOnLoginButton() {
		loginButton.click();
	}

	public void clickOnLogoutButton() {
	    // ✅ Defensive null check before using seleniumUtils
		if (seleniumUtils == null) {
            throw new IllegalStateException("❌ SeleniumUtils not initialized. Ensure LoginPage(driver) is called properly.");
        }
	    try {
	        seleniumUtils.getWait(10)
	            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Logout')]")))
	            .click();
	        System.out.println("✅ Successfully clicked on Logout button.");
	    } catch (Exception e) {
	        System.err.println("❌ Logout button not found or not clickable: " + e.getMessage());
	    }
	}

	// Capture Error message
	public String captureInvalidLoginError() {
		String actualMessage = errorMessage.getText();
		System.out.println("Error Message found as : " + actualMessage);
		return actualMessage;

	}
}
