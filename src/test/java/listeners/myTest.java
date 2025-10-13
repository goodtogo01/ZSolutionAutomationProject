package listeners;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.SeleniumUtils;

public class myTest {
	private WebDriver driver;

	// Define XPaths
	private By iFrameManue = By.xpath("/html/body/div[1]/button[7]");
	private By iframe = By.xpath("//*[@id='iframe']");
	private By iframeContent = By.xpath("/html/body/div/h1");

	// Set up WebDriver and navigate to the test page
	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
		// Replace with the actual path to chromedriver
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("file:///Users/khosruzzaman/Desktop/mytest.html");
		// Replace with the actual URL of the web page
	}

	// Test navigating to the iFrame menu
	@Test
	public void testNavigateToIFrameMenu() throws IOException {
		WebElement menuButton = driver.findElement(iFrameManue);
		menuButton.click();

		Assert.assertTrue(menuButton.isDisplayed(), "Failed to navigate to the iFrame menu!");
		SeleniumUtils s = new SeleniumUtils(driver);
		SeleniumUtils.takeScreenShoot();
	}

	// Test verifying the presence of the iFrame
	@Test
	public void testIFramePresence() throws IOException {
		WebElement menuButton = driver.findElement(iFrameManue);
		menuButton.click();
		WebElement iframeElement = driver.findElement(iframe);
		Assert.assertTrue(iframeElement.isDisplayed(), "iFrame is not present on the page!");
		SeleniumUtils s = new SeleniumUtils(driver);
		SeleniumUtils.takeScreenShoot();
	}

	// Test switching to the iFrame and validating its content
	@Test
	public void testSwitchToIFrameAndValidateContent() {
		WebElement iframeElement = driver.findElement(iframe);
		driver.switchTo().frame(iframeElement);
		WebElement content = driver.findElement(iframeContent);
		String expectedText = "Welcome to the iFrame"; // Replace with actual expected text
		AssertJUnit.assertEquals(content.getText(), expectedText, "iFrame content does not match!");
		driver.switchTo().defaultContent(); // Switch back to the main page
	}

	// Tear down WebDriver
	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	// Main method to execute the test cases
	public static void main(String[] args) {
		myTest test = new myTest();

		try {
			test.setUp();
			test.testNavigateToIFrameMenu();
			test.testIFramePresence();
			test.testSwitchToIFrameAndValidateContent();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			test.tearDown();
		}
	}
}
