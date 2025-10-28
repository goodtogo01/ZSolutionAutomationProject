package pages;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertsAndPopupsPage {
	private WebDriver driver;
	private WebDriverWait wait;
	
	// ✅ FIX 1: Main menu button locator is now more robust.
	private By alertMainMenuButton = By.xpath("//div[@class='sidebar']/button[text()='Alerts & Popups']");
	
	// ✅ FIX 2: Alert buttons locators are now relative to the #alerts-popups div.
	private By showAlert = By.xpath("//div[@id='alerts-popups']/button[1]");
	private By showConfirmation = By.xpath("//div[@id='alerts-popups']/button[2]");
	private By promptAlert = By.xpath("//div[@id='alerts-popups']/button[3]");

	public AlertsAndPopupsPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void clickMainManue() {
		// ✅ Wait for the main menu button to be clickable before clicking
		wait.until(ExpectedConditions.elementToBeClickable(alertMainMenuButton)).click();
		// ✅ Optional: Wait for the alert section to be visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("alerts-popups")));
	}

	// Click on each buttons
	public void clickToAlertButton() {
		// ✅ Wait for the button to be clickable
		wait.until(ExpectedConditions.elementToBeClickable(showAlert)).click();
	}

	public void clickToShowConfirmationButton() {
		// ✅ Wait for the button to be clickable
		wait.until(ExpectedConditions.elementToBeClickable(showConfirmation)).click();
	}

	public void clickToShowInputAlertButton() {
		// ✅ Wait for the button to be clickable
		wait.until(ExpectedConditions.elementToBeClickable(promptAlert)).click();
	}

	// ... (rest of the Alert handling methods remain the same) ...
	
	// Handle get alert Text
	public String getAlertText() {
		return handleAlertAndGetText(false);
	}

	// Handle alert, returns text
	public String handleAlertAndGetText(boolean accept) {
		try {
			// ✅ Wait for the alert to be present before switching
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			String alertText = alert.getText();
			System.out.println("Alert Text captured as : " + alertText);

			if (accept) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} catch (NoAlertPresentException e) {
			System.out.println("No Alert is available");
		}
		return null;
	}

// Handle input alert
	public void handleInputAlert(String TextToEnter, boolean accept) throws IOException {
		try {
			// ✅ Wait for the alert to be present before switching
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			alert.sendKeys(TextToEnter);
			
			if (accept) {
				alert.accept();
			} else {
				alert.dismiss();
			}

		} catch (NoAlertPresentException e) {
			System.out.println("No Alert is available");
		}
	}
}