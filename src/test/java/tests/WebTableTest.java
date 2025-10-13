package tests;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseTest;
import pages.LoginPage;
import pages.WebTablePage;

public class WebTableTest extends BaseTest {

	private WebDriver driver;
	private WebTablePage webTablePage;
	private LoginPage loginPage;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		// initialization();
		driver = initialization(); // ✅ Get driver from BaseTest
		webTablePage = new WebTablePage(BaseTest.getDriver());
		loginPage = new LoginPage(BaseTest.getDriver());

		loginPage.setUserName(prop.getProperty("userName"));
		loginPage.setPassword(prop.getProperty("password"));
		loginPage.clickOnLoginButton();

		Assert.assertEquals(BaseTest.getDriver().getTitle(), prop.getProperty("homePageTitle"));
		webTablePage.clickOnTableMenu();
	}

	@Test(priority = 1)
	public void testRowCount() {
		test = extent.createTest("Row Count Presence");
		Assert.assertTrue(webTablePage.getRowCount() > 0);
		test.pass("Row count validated.");
	}

	@Test(priority = 2)
	public void testFetchAndPrintData() {
		test = extent.createTest("Fetch & Print Table Data");
		List<List<String>> data = webTablePage.getTableData();
		data.forEach(System.out::println);
		Assert.assertFalse(data.isEmpty());
		test.pass("Data fetched successfully.");
	}

	@Test(priority = 3)
	public void testSearchSpecificRecord() {
		test = extent.createTest("Search Specific Record");
		String name = "Delbert Sanford", role = "Designer", location = "UAE";

		// Ensure record exists — if not, add it first
		if (!webTablePage.isRecordPresent(name, role, location)) {
			webTablePage.addNewRecord(name, role, location);
		}

		Assert.assertTrue(webTablePage.isRecordPresent(name, role, location), "Expected record not found: " + name);
		test.pass("Specific record exists.");
	}

	@Test(priority = 4)
	public void testAddNewRecordAndSearch() {
		test = extent.createTest("Add New Record & Verify");
		String name = "KHOSRUZ ZAMAN", role = "SDET", location = "USA";
		System.out.println(name);
		String msg = webTablePage.addNewRecord(name, role, location);
		Assert.assertTrue(msg.contains("added successfully"));

		Assert.assertTrue(webTablePage.isRecordPresent(name, role, location));
		test.pass("Add + search record passed.");
	}

	@Test(priority = 5)
	public void testDeleteRecord() {
		test = extent.createTest("Delete Record & Confirm");

		String name = "Delbert Sanford";
		String role = "Designer";
		String location = "UAE";

		if (!webTablePage.isRecordPresent(name, role, location)) {
			webTablePage.addNewRecord(name, role, location);
		}

		String msg = webTablePage.deleteRecordByName(name);
		if (msg != null && !msg.isEmpty()) {
			Assert.assertTrue(msg.toLowerCase().contains("deleted") || msg.toLowerCase().contains("success"),
					"Delete message doesn't confirm deletion: " + msg);
		}

		Assert.assertFalse(webTablePage.isRecordPresent(name, role, location), "Record still present after deletion!");

		test.pass("Record deleted and confirmed visually.");
	}

	@Test(priority = 6)
	public void testFilterByLocation() {
		test = extent.createTest("Filter By Location");
		List<String> names = webTablePage.getNamesByLocation("USA");

		SoftAssert soft = new SoftAssert();
		soft.assertFalse(names.isEmpty());
		soft.assertAll();

		test.pass("Filter by location validated.");
	}

	@Test(priority = 7)
	public void testDynamicRoleValidation() {
		test = extent.createTest("Dynamic Role Validation");
		Assert.assertTrue(webTablePage.isRolePresent("Lead"));
		test.pass("Role presence validated.");
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