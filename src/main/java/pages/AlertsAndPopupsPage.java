package pages;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

public class AlertsAndPopupsPage {
	private WebDriver driver;

	private By alertButton = By.xpath("/html/body/div[1]/button[5]");
	private By showAlert = By.xpath("//*[@id='alerts-popups']/button[1]");
	private By showConfirmation = By.xpath("//*[@id='alerts-popups']/button[2]");
	private By promptAlert = By.xpath("//*[@id='alerts-popups']/button[3]");

	public AlertsAndPopupsPage(WebDriver driver) {
		this.driver = driver;
	}

	public void clickMainManue() {
		driver.findElement(alertButton).click();

	}

	// Click on each bttons
	public void clickToAlertButton() {
		driver.findElement(showAlert).click();

	}

	public void clickToShowConfirmationButton() {
		driver.findElement(showConfirmation).click();

	}

	public void clickToShowInputAlertButton() {
		driver.findElement(promptAlert).click();

	}

	// prompt operation
	public void acceptAlert() {
		handleAlertAndGetText(true);
	}

	// prompt operation
	public void dismisAlert() {
		handleAlertAndGetText(false);
	}

	public String getAlertText() {
		return handleAlertAndGetText(false);
	}

	// Handle get alert Text
	public String handleAlertAndGetText(boolean accept) {
		try {
			Alert alert = driver.switchTo().alert();
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

	// Hangle Prompt alert
	public void hanglePromptAlert(boolean accept) {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			System.out.println("Alert Text captured as : " + alertText);

			if (accept) {
				alert.accept();
			} else {
				alert.dismiss();
			}

		} catch (NoAlertPresentException e) {
			System.out.println("No Alert is available");
		}
	}

// Handle input alert
	public void handleInputAlert(String TextToEnter, boolean accept) throws IOException {
		try {
			Alert alert = driver.switchTo().alert();
			alert.sendKeys(TextToEnter);
			// acceptAlert();

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
