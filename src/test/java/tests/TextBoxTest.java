package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.TextBoxPage;
import utils.ExcelUtils;
import utils.SeleniumUtils;
import utils.TestDataUtils;

public class TextBoxTest extends BaseTest {

	private TextBoxPage textBoxPage;
	private SeleniumUtils seleniumUtils;
	private TestDataUtils testDataUtils;
	private ExcelUtils excelUtils;
	private LoginPage loginPage;

	private String sheetName = "Sheet1";

	@DataProvider(name = "excelData")
	public Object[][] textBoxDataProvider() {
		return ExcelUtils.getTestData(sheetName);
	}

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws IOException {
		// 1️⃣ Initialize driver
		BaseTest.getDriver(); // ensure ThreadLocal driver is initialized
		initialization(); // launches browser and navigates to URL

		// 2️⃣ Initialize utilities and pages using getDriver()
		seleniumUtils = new SeleniumUtils(BaseTest.getDriver());
		textBoxPage = new TextBoxPage(BaseTest.getDriver());
		testDataUtils = new TestDataUtils();
		excelUtils = new ExcelUtils();
		loginPage = new LoginPage(BaseTest.getDriver());

		// 3️⃣ Perform login
		loginPage.setUserName(prop.getProperty("userName"));
		loginPage.setPassword(prop.getProperty("password"));
		loginPage.clickOnLoginButton();

		// 4️⃣ Validate login
		String expectedHomeTitle = prop.getProperty("homePageTitle");
		String actualTitle = BaseTest.getDriver().getTitle();
		System.out.println("Page Title is : " + actualTitle);
		Assert.assertEquals(actualTitle, expectedHomeTitle, "❌ Login failed or home page title mismatch!");
		System.out.println("✅ Successfully logged in. Current Page Title: " + actualTitle);
	}

	@BeforeMethod(alwaysRun = true)
	public void navigateToTextBoxPage() {
		textBoxPage.clickTextButton();
	}

	@Test(dataProvider = "excelData", priority = 1)
	public void testTextBox(String name, String email, String address) throws IOException {
		test = extent.createTest("Test TextBox Form Submission");

		textBoxPage.userDetails(name, email, address);
		textBoxPage.clickSubmit();
		SeleniumUtils.takeScreenShoot();

		Assert.assertEquals(textBoxPage.getdOutputName(), name, "❌ Name output does not match!");
		Assert.assertEquals(textBoxPage.getdOutputEmail(), email, "❌ Email output does not match!");
		test.pass("✅ TextBox content validated successfully.");
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		if (BaseTest.getDriver() != null) {
			BaseTest.getDriver().quit();
			System.out.println("✅ Browser closed after TextBox tests.");
		}
	}
}