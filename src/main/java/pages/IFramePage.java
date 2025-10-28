package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IFramePage {
	private WebDriver driver;
	private WebDriverWait wait;

	// Sidebar iFrame menu button
	// ✅ FIX: Use a stable locator based on the button's text
	@FindBy(xpath = "//button[text()='iFrame']") 
	private WebElement iFrameMenu;

	// Locator for the main iFrame container (id="iframe")
	private By iframeContainerLocator = By.id("iframe");

	// iFrames on the page
	private By frame1Locator = By.id("frame1");
	private By frame2Locator = By.id("frame2");

	public IFramePage(WebDriver driver) {
		this.driver = driver;
		// Use driver from current thread
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
		PageFactory.initElements(driver, this);
	}

	// Navigate to the iframe menu
	public void navigateToIFrameMenu() {
		// Wait for the button to be clickable before clicking
		wait.until(ExpectedConditions.elementToBeClickable(iFrameMenu)).click();
		// Wait for the content panel to become visible after the click
		wait.until(ExpectedConditions.visibilityOfElementLocated(iframeContainerLocator));
	}

	// Check if iframe is present
	public boolean isIFramePresent(String frameId) {
		try {
			By locator = getFrameLocator(frameId);
			WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return iframe.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// Switch to specified iframe
	public void switchToIFrame(String frameId) {
		By locator = getFrameLocator(frameId);
		WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		driver.switchTo().frame(iframe);
	}

	// Get content inside the iframe
	public String getIFrameContent(String xpathInsideFrame) {
		WebElement content = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathInsideFrame)));
		return content.getText();
	}

	// Switch back to main content
	public void switchToMainContent() {
		driver.switchTo().defaultContent();
	}

	// Helper to get frame locator
	private By getFrameLocator(String frameId) {
		switch (frameId) {
		case "frame1":
			return frame1Locator;
		case "frame2":
			return frame2Locator;
		default:
			throw new IllegalArgumentException("Invalid frameId: " + frameId);
		}
	}
}