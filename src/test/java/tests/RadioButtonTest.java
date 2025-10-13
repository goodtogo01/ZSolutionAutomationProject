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
import pages.LoginPage;
import pages.RadioButtonPage;
import utils.SeleniumUtils;

public class RadioButtonTest extends BaseTest {

	private WebDriver driver;
	private SeleniumUtils seleniumUtils;
	private RadioButtonPage radioButtonPage;
	private LoginPage loginPage;

	// ✅ Login once before all radio button tests
	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws MalformedURLException {
		driver = initialization(); // ✅ from BaseTest
		seleniumUtils = new SeleniumUtils(driver);
		radioButtonPage = new RadioButtonPage(driver);
		loginPage = new LoginPage(driver);

		// ✅ Navigate to login page
		driver.get(BaseTest.prop.getProperty("url"));

		// ✅ Perform login
		loginPage.setUserName(BaseTest.prop.getProperty("userName"));
		loginPage.setPassword(BaseTest.prop.getProperty("password"));
		loginPage.clickOnLoginButton();

		// ✅ Validate login success
		String expectedHomeTitle = BaseTest.prop.getProperty("homePageTitle");
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedHomeTitle, "❌ Login failed or page title mismatch!");
		System.out.println("✅ Successfully logged in. Current Page Title: " + actualTitle);
	}

	// ✅ Navigate to Radio Button page before each test
	@BeforeMethod(alwaysRun = true)
	public void navigateToRadioButtonMenu() {
		radioButtonPage.clickMainManue();
		test = extent.createTest("Navigate to Radio Button Page");
		test.pass("✅ Navigated to Radio Button section successfully.");
	}

	// -------------------- TESTS -------------------- //

	@Test(priority = 1)
	public void selectMaleAge_18_25() throws IOException {
		test = extent.createTest("Test Select Male Option and Age 18-25");

		radioButtonPage.selectMaleOption();
		Assert.assertTrue(radioButtonPage.isMaleSelected(), "❌ Male radio button should be selected!");

		radioButtonPage.selectAge18_25();
		Assert.assertTrue(radioButtonPage.isParticulerAgeSelected1825(),
				"❌ Age 18-25 radio button should be selected!");

		SeleniumUtils.takeScreenShoot();
		test.pass("✅ Male option and age group 18-25 selected successfully.");
	}

	@Test(priority = 2)
	public void selectFemaleOption() throws IOException {
		test = extent.createTest("Test Select Female Option and Age 26-35");

		radioButtonPage.selectFemaleOption();
		Assert.assertTrue(radioButtonPage.isFemaleSelected(), "❌ Female radio button should be selected!");

		radioButtonPage.selectAge26_35();
		Assert.assertTrue(radioButtonPage.isParticulerAgeSelected2635(),
				"❌ Age 26-35 radio button should be selected!");

		SeleniumUtils.takeScreenShoot();
		test.pass("✅ Female option and age group 26-35 selected successfully.");
	}

	// ✅ Close browser after all tests
	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		if (driver != null) {
			driver.quit();
			System.out.println("✅ Browser closed after Radio Button tests.");
		}
	}
}