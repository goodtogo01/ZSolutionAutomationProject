package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CheckBoxPage;
import pages.DropdownPage;
import pages.LoginPage;
import utils.SeleniumUtils;

public class CheckBoxTest extends BaseTest {

    private WebDriver driver;
    private SeleniumUtils seleniumUtils;
    private DropdownPage dropdownPage;
    private CheckBoxPage checkBoxPage;
    private LoginPage loginPage;

    // All checkbox locators
    private final By[] checkBoxes = {
        By.xpath("//*[@value='Reading']"),
        By.xpath("//*[@value='Traveling']"),
        By.xpath("//*[@value='Gaming']"),
        By.xpath("//*[@value='Cooking']"),
        By.xpath("//*[@value='Newsletter']"),
        By.xpath("//*[@value='Promotions']")
    };

    /**
     * ✅ Runs once before all tests in this class.
     * Initializes driver and performs login only once.
     */
    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        driver = initialization();  // Get driver from BaseTest
        seleniumUtils = new SeleniumUtils(driver);
        dropdownPage = new DropdownPage(driver);
        checkBoxPage = new CheckBoxPage(driver);
        loginPage = new LoginPage(driver);

        // ✅ Perform login only once before all tests
        driver.get(BaseTest.prop.getProperty("url"));
        loginPage.setUserName(BaseTest.prop.getProperty("userName"));
        loginPage.setPassword(BaseTest.prop.getProperty("password"));
        loginPage.clickOnLoginButton();

        // Optional validation step
        String expectedHomeTitle = BaseTest.prop.getProperty("homePageTitle");
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedHomeTitle,
                "❌ Login failed or home page title mismatch!");
        System.out.println("✅ Successfully logged in. Current Page Title: " + actualTitle);
    }

    /**
     * ✅ Runs before each test — navigates to CheckBox section.
     */
    @BeforeMethod(alwaysRun = true)
    public void navigateToCheckBoxSection() {
        checkBoxPage.navigateToCheckBoxMenu();
    }

    @Test(priority = 0)
    public void validatePageTitle() {
        test = extent.createTest("Test to Validate Page Title");

        String actualTitle = driver.getTitle();
        String expectedTitle = BaseTest.prop.getProperty("homePageTitle");

        Assert.assertEquals(actualTitle, expectedTitle, "❌ Title doesn't match!");
        test.pass("✅ Page title validated successfully.");
    }

    @Test(priority = 1)
    public void testNavigateToCheckBoxMenu() {
        test = extent.createTest("Test Navigate To CheckBox Menu");

        Assert.assertTrue(
            driver.findElement(By.xpath("//*[@value='Reading']")).isDisplayed(),
            "❌ Checkbox menu is not displayed!"
        );
        test.pass("✅ Checkbox menu is displayed correctly.");
    }

    @Test(priority = 2)
    public void testSelectIndividualCheckBox() {
        test = extent.createTest("Test Select Individual CheckBox");

        By readingCheckBox = By.xpath("//*[@value='Reading']");
        checkBoxPage.selectCheckBox(readingCheckBox);

        Assert.assertTrue(
            checkBoxPage.isCheckBoxSelected(readingCheckBox),
            "❌ Reading checkbox should be selected!"
        );
        test.pass("✅ Individual checkbox selected successfully.");
    }

    @Test(priority = 3)
    public void testDeselectIndividualCheckBox() {
        test = extent.createTest("Test Deselect Individual CheckBox");

        By readingCheckBox = By.xpath("//*[@value='Reading']");
        checkBoxPage.selectCheckBox(readingCheckBox); // Ensure selected
        checkBoxPage.deselectCheckBox(readingCheckBox);

        Assert.assertFalse(
            checkBoxPage.isCheckBoxSelected(readingCheckBox),
            "❌ Reading checkbox should not be selected!"
        );
        test.pass("✅ Checkbox deselected successfully.");
    }

    @Test(priority = 4)
    public void testSelectEachCheckBoxes() throws InterruptedException {
        test = extent.createTest("Test Try to Select All CheckBoxes");

        checkBoxPage.selectAllCheckBoxes();

        Assert.assertFalse(checkBoxPage.areAllCheckBoxesSelected(), "❌ All checkboxes are selected!");
        test.pass("✅ All checkboxes can not be selected ata a time successfully.");
    }

    @Test(priority = 5)
    public void testDeselectAllCheckBoxes() {
        test = extent.createTest("Test Deselect All CheckBoxes");

        checkBoxPage.deselectAllCheckBoxes();

        Assert.assertEquals(checkBoxPage.getSelectedCheckBoxCount(), 0, "❌ Some checkboxes remain selected!");
        test.pass("✅ All checkboxes deselected successfully.");
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