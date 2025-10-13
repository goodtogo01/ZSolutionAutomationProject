package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

	public LoginPage(WebDriver driver) {
		this.driver = driver;
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

	// ✅ Proper logout with explicit wait
    public void clickOnLogoutButton() {
    	// Wait until logout button is clickable
        WebElement logoutBtn = seleniumUtils.waitForElementClickable(logoutButton);
         // Click logout
        logoutBtn.click();
    }
	// Capture Error message
	public String captureInvalidLoginError() {
		String actualMessage = errorMessage.getText();
		System.out.println("Error Message found as : " + actualMessage);
		return actualMessage;

	}
}
