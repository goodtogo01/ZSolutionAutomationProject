package utils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {
	private static WebDriver driver;
	private WebDriverWait wait;

	public SeleniumUtils(WebDriver driver) {
		SeleniumUtils.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Updated
	}

	// Wait for Element to Be Visible
	public WebElement waitForElementVisible(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	 // Wait until element is clickable
    public WebElement waitForElementClickable(By logoutButton) {
        return wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
    }

	// Click Element
	public void click(By locator) {
		waitForElementVisible(locator).click();
	}

	// Enter Text in TextBox
	public void enterText(By locator, String text) {
		WebElement element = waitForElementVisible(locator);
		element.clear();
		element.sendKeys(text);
	}

	// Select Radio Button
	public void selectRadioButton(By locator) {
		WebElement radioButton = waitForElementVisible(locator);
		if (!radioButton.isSelected()) {
			radioButton.click();
		}
	}

	// Check or Uncheck Checkbox
	public void setCheckbox(By locator, boolean check) {
		WebElement checkbox = waitForElementVisible(locator);
		if (checkbox.isSelected() != check) {
			checkbox.click();
		}
	}

	// Select by Visible Text in Dropdown
	public void selectByVisibleText(By locator, String visibleText) {
		WebElement dropdown = waitForElementVisible(locator);
		Select select = new Select(dropdown);
		select.selectByVisibleText(visibleText);
	}

	// Select by Index in Dropdown
	public void selectByIndex(By locator, int index) {
		WebElement dropdown = waitForElementVisible(locator);
		Select select = new Select(dropdown);
		select.selectByIndex(index);
	}

	// Select by Value in Dropdown
	public void selectByValue(By locator, String value) {
		WebElement dropdown = waitForElementVisible(locator);
		Select select = new Select(dropdown);
		select.selectByValue(value);
	}

	// Handle Alert - Accept
	public void acceptAlert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	// Handle Alert - Dismiss
	public void dismissAlert() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	// Get Alert Text
	public String getAlertText() {
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}

	// Switch to Iframe by Index
	public void switchToIframeByIndex(int index) {
		driver.switchTo().frame(index);
	}

	// Switch to Iframe by Locator
	public void switchToIframeByLocator(By locator) {
		WebElement iframe = waitForElementVisible(locator);
		driver.switchTo().frame(iframe);
	}

	// Switch Back to Default Content
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	// Get All Rows from Web Table
	public List<WebElement> getAllRows(By tableLocator) {
		WebElement table = waitForElementVisible(tableLocator);
		return table.findElements(By.tagName("tr"));
	}

	// Get Cell Value from Web Table
	public String getTableCellValue(By tableLocator, int row, int col) {
		List<WebElement> rows = getAllRows(tableLocator);
		WebElement cell = rows.get(row).findElements(By.tagName("td")).get(col);
		return cell.getText();
	}

	// Scroll to Element
	public void scrollToElement(By locator) {
		WebElement element = waitForElementVisible(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	// Highlight Element
	public void highlightElement(By locator) {
		WebElement element = waitForElementVisible(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	// Capture All Links on a Page
	public List<String> getAllLinks() {
		List<String> links = new ArrayList<>();
		List<WebElement> linkElements = driver.findElements(By.tagName("a"));
		for (WebElement link : linkElements) {
			String url = link.getAttribute("href");
			if (url != null && !url.isEmpty()) {
				links.add(url);
			}
		}
		return links;
	}

	// Validate Broken Link
	public boolean isLinkBroken(String url) {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("HEAD");
			connection.connect();
			return connection.getResponseCode() >= 400;
		} catch (Exception e) {
			return true;
		}
	}

	// Drag and Drop
	public void dragAndDrop(By sourceLocator, By targetLocator) {
		WebElement source = waitForElementVisible(sourceLocator);
		WebElement target = waitForElementVisible(targetLocator);
		Actions actions = new Actions(driver);
		actions.dragAndDrop(source, target).perform();
	}

	// Hover Over Element
	public void hoverOverElement(By locator) {
		WebElement element = waitForElementVisible(locator);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	// Take Screenshot of an Element
	public void captureElementScreenshot(By locator) {
		WebElement element = waitForElementVisible(locator);
		File screenshot = element.getScreenshotAs(OutputType.FILE);
		String curentDir = System.getProperty("user.dir");
		String filePath = curentDir + "/Screen_Shoot/" + System.currentTimeMillis() + ".png";
		try {
			Files.copy(screenshot.toPath(), new File(filePath).toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void takeScreenShoot() throws IOException {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String curentDir = System.getProperty("user.dir");
		FileUtils.copyFile(srcFile, new File(curentDir + "/Screen_Shoot/" + System.currentTimeMillis() + ".png"));

	}


    // Wait until text appears in element
    public boolean waitForTextToBePresent(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
}