package tests;

import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.AlertsAndPopupsPage;
import pages.LoginPage;
import utils.SeleniumUtils;
import utils.TestDataUtils;

public class AlertsAndPopupTest extends BaseTest {

	private WebDriver driver;
	private AlertsAndPopupsPage alertsAndPopupsPage;
	private SeleniumUtils seleniumUtils;
	private TestDataUtils testDataUtils;
	private LoginPage loginPage;

	// ✅ Perform login once before all tests
	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws MalformedURLException {
		driver = initialization(); // ✅ Get driver from BaseTest
		seleniumUtils = new SeleniumUtils(driver);
		alertsAndPopupsPage = new AlertsAndPopupsPage(driver);
		loginPage = new LoginPage(driver);
		testDataUtils = new TestDataUtils();

		// ✅ Navigate to login page
		driver.get(BaseTest.prop.getProperty("url"));

		// ✅ Login only once
		loginPage.setUserName(BaseTest.prop.getProperty("userName"));
		loginPage.setPassword(BaseTest.prop.getProperty("password"));
		loginPage.clickOnLoginButton();

		// ✅ Validate login success
		String expectedHomeTitle = BaseTest.prop.getProperty("homePageTitle");
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedHomeTitle, "❌ Login failed or page title mismatch!");
		System.out.println("✅ Successfully logged in. Current Page Title: " + actualTitle);
	}

	// ✅ Navigate to main alert menu before each test
	@BeforeMethod(alwaysRun = true)
	public void navigateToAlertMenu() {
		alertsAndPopupsPage.clickMainManue();
		test = extent.createTest("Navigate to Alert Menu");
		test.pass("✅ Navigated to Alerts & Popups section successfully.");
	}

	// ---------------------------- TESTS ---------------------------- //

	@Test(priority = 1)
	public void basicAlertHandling() {
		test = extent.createTest("Test Basic Alert Handling");

		alertsAndPopupsPage.clickToAlertButton();
		String alertMessage = alertsAndPopupsPage.getAlertText();

		System.out.println("Alert Message: " + alertMessage);
		Assert.assertEquals(alertMessage, "This is a simple alert!", "❌ Message doesn't match!");
		test.pass("✅ Basic alert handled successfully.");
	}

	@Test(priority = 2)
	public void confirmationAlertHandling() {
		test = extent.createTest("Test Confirmation Alert Handling");

		alertsAndPopupsPage.clickToShowConfirmationButton();
		String alertMessage = alertsAndPopupsPage.getAlertText();

		System.out.println("Alert Message: " + alertMessage);
		Assert.assertEquals(alertMessage, "Are you sure you want to proceed?", "❌ Message doesn't match!");
		test.pass("✅ Confirmation alert handled successfully.");
	}

	@Test(priority = 3)
	public void promptAlertHandling() throws IOException, InterruptedException {
		test = extent.createTest("Test Prompt Alert Handling");

		alertsAndPopupsPage.clickToShowInputAlertButton();
		alertsAndPopupsPage.handleInputAlert("khosruz zaman", true);

		String alertMessage = alertsAndPopupsPage.getAlertText();
		System.out.println("Alert Message: " + alertMessage);

		Assert.assertEquals(alertMessage, "Hello, khosruz zaman", "❌ Message doesn't match!");
		test.pass("✅ Prompt alert handled successfully.");
	}

	// ✅ Clean up after all tests
	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		if (driver != null) {
			driver.quit();
			System.out.println("✅ Browser closed after all alert tests.");
		}
	}
}