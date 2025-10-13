package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	private WebDriver driver;

	@FindBy(xpath = "//*[@id='username']")
	private WebElement userName;

	// *[@id='username']

	@FindBy(xpath = "//*[@id='password']")
	private WebElement passwords;

	@FindBy(css = "body > div > button")
	private WebElement loginButton;

	@FindBy(className = "logout-btn")
	private WebElement logoutButton;

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

	// Perform LogOut
	public void clickOnLogoutButton() {
		loginButton.click();
	}

	// Capture Error message
	public String captureInvalidLoginError() {
		String actualMessage = errorMessage.getText();
		System.out.println("Error Message found as : " + actualMessage);
		return actualMessage;

	}
}
