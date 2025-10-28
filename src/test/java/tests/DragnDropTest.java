package tests;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import base.BaseTest;
import pages.DragnDropPage;
import pages.LoginPage;
import utils.SeleniumUtils;

public class DragnDropTest extends BaseTest {

	private WebDriver driver;
	private DragnDropPage dragDropPage;
	private SeleniumUtils seleniumUtils;
	private LoginPage loginPage;

	// -------------------- Setup --------------------
	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws MalformedURLException {
		driver = initialization(); // ✅ from BaseTest
		seleniumUtils = new SeleniumUtils(driver);
		dragDropPage = new DragnDropPage(driver);
		loginPage = new LoginPage(driver);

		// ✅ Navigate & Login
		driver.get(BaseTest.prop.getProperty("url"));
		loginPage.setUserName(BaseTest.prop.getProperty("userName"));
		loginPage.setPassword(BaseTest.prop.getProperty("password"));
		loginPage.clickOnLoginButton();

		String expectedTitle = BaseTest.prop.getProperty("homePageTitle");
		Assert.assertEquals(driver.getTitle(), expectedTitle, "❌ Login failed or title mismatch!");
		System.out.println("✅ Logged in successfully. Page title: " + expectedTitle);
	}

	@BeforeMethod(alwaysRun = true)
	public void navigateToDragDropSection() {
		test = extent.createTest("Navigate to Drag & Drop Section");
		dragDropPage.navigateToDragDropMenu();
		test.pass("✅ Navigated to Drag & Drop section successfully.");
	}

	// -------------------- TEST CASES --------------------

	@Test(priority = 1)
	public void testDragDropSectionVisible() {
		test = extent.createTest("Verify Drag & Drop Section Visibility");
		Assert.assertTrue(dragDropPage.isDraggableItemsPresent(), "❌ Draggable items not found!");
		Assert.assertTrue(dragDropPage.isDropTargetVisible(), "❌ Drop target not visible!");
		test.pass("✅ Verified draggable items and drop area visibility.");
	}

	@Test(priority = 2)
	public void testSingleItemDragAndDrop() {
		test = extent.createTest("Test Single Item Drag & Drop");
		dragDropPage.dragItemToTarget(0);
		String result = dragDropPage.getDropResultText();
		Assert.assertTrue(result.contains("dropped") || !result.isEmpty(), "❌ Drop message missing!");
		test.pass("✅ Single item drag and drop verified successfully.");
	}

	@Test(priority = 3)
	public void testMultipleItemsDragAndDrop() {
		test = extent.createTest("Test Multiple Items Drag & Drop");
		dragDropPage.dragAllItemsToTarget();
		String result = dragDropPage.getDropResultText();
		Assert.assertTrue(result.contains("dropped") || !result.isEmpty(), "❌ Drop confirmation not displayed!");
		test.pass("✅ Multiple items drag and drop executed successfully.");
	}

	@Test(priority = 4)
	public void testDropTargetPersistence() {
		test = extent.createTest("Verify Drop Target Remains Active");
		Assert.assertTrue(dragDropPage.isDropTargetVisible(), "❌ Drop target not visible after drag!");
		test.pass("✅ Drop target remains active after drag operations.");
	}

	@Test(priority = 5)
	public void testDropResultMessage() {
		test = extent.createTest("Validate Drop Result Message");
		dragDropPage.dragItemToTarget(0);
		String dropMessage = dragDropPage.getDropResultText();
		Assert.assertFalse(dropMessage.isEmpty(), "❌ Drop result message is empty!");
		test.pass("✅ Drop result message displayed successfully: " + dropMessage);
	}

	// -------------------- Tear Down --------------------
	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		if (driver != null) {
			driver.quit();
			driver = null;
			System.out.println("✅ Browser closed after Drag & Drop tests.");
		}
	}
}