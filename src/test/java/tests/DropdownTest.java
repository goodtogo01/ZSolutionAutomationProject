package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.DropdownPage;
import pages.LoginPage;
import pages.CheckBoxPage;
import utils.SeleniumUtils;

public class DropdownTest extends BaseTest {

    private WebDriver driver;
    private SeleniumUtils seleniumUtils;
    private DropdownPage dropdownPage;
    private CheckBoxPage checkBoxPage;
    private LoginPage loginPage;

    private final By countryDropdown = By.id("country");
    private final By cityDropdown = By.id("city");

    // ✅ LOGIN ONCE before running all dropdown tests
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

        // ✅ Validate successful login
        String expectedHomeTitle = BaseTest.prop.getProperty("homePageTitle");
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedHomeTitle,
                "❌ Login failed or home page title mismatch!");
        System.out.println("✅ Successfully logged in. Current Page Title: " + actualTitle);
    }

    // ✅ Navigate to dropdown menu before each test
    @BeforeMethod(alwaysRun = true)
    public void navigateToDropdown() {
        dropdownPage.navigateToDropdownManue();
        test = extent.createTest("Navigate to Dropdown Menu");
        test.pass("✅ Navigated to dropdown section successfully.");
    }

    @Test(priority = 1)
    public void testSelectCountry() {
        test = extent.createTest("Test Select Country Dropdown Value");

        dropdownPage.selectDropdownValue(countryDropdown, "USA");
        String selected = dropdownPage.getSelectedOption(countryDropdown);

        Assert.assertEquals(selected, "USA", "❌ Selected country is not 'USA'!");
        test.pass("✅ Dropdown value selected and validated successfully.");
    }

    @Test(priority = 2)
    public void testAllDropdownOptions() {
        test = extent.createTest("Test Validate All Dropdown Options");

        var options = dropdownPage.getAllDropdownOptions(countryDropdown);
        Assert.assertTrue(options.contains("USA"), "❌ 'USA' not found in country dropdown!");

        test.pass("✅ Dropdown options validated successfully.");
    }

    @Test(priority = 3)
    public void testIsOptionPresentInCityDropdown() {
        test = extent.createTest("Test Validate City Dropdown Option Presence");

        boolean isPresent = dropdownPage.isOptionPresentInDropdown(cityDropdown, "New York");
        Assert.assertTrue(isPresent, "❌ 'New York' not found in city dropdown!");

        test.pass("✅ City dropdown option validated successfully.");
    }

    @Test(priority = 4)
    public void testIsCountryDropdownMultiSelect() {
        test = extent.createTest("Test Validate Country Dropdown Type");

        boolean isMultiSelect = dropdownPage.isMultiSelect(countryDropdown);
        Assert.assertFalse(isMultiSelect, "❌ Country dropdown should not be multi-select!");

        test.pass("✅ Country dropdown type validated successfully.");
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ Browser closed after all dropdown tests.");
        }
    }
}