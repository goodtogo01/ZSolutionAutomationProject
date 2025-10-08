package tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.SeleniumUtils;

public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;
    private SeleniumUtils seleniumUtils;
    private WebDriver driver;  // Local reference for easy access

    public LoginPageTest() {
        super();
    }


	@BeforeMethod(groups = "Initializations")
    public void setUp() throws MalformedURLException {
        driver = initialization(); // ✅ returns WebDriver instance from BaseTest
        loginPage = new LoginPage(driver);
        seleniumUtils = new SeleniumUtils(driver);
    }

    @Test(priority = 0)
    public void validatePageTitle() {
        test = extent.createTest("Test to Validate Page Title");

        String actualTitle = driver.getTitle();
        String expectedTitle = BaseTest.prop.getProperty("loginPageTitle");

        Assert.assertEquals(actualTitle, expectedTitle, "❌ Title doesn't match!");
        System.out.println("✅ Title Found: " + actualTitle);
        test.pass("✅ Page title validation passed successfully.");
    }

    @Test(priority = 1)
    public void loginWithValidCredentials() {
        test = extent.createTest("Test to Login With Valid Credentials");

        loginPage.setUserName(BaseTest.prop.getProperty("userName"));
        loginPage.setPassword(BaseTest.prop.getProperty("password"));
        loginPage.clickOnLoginButton();
        loginPage.clickOnLogoutButton();

        test.pass("✅ Login and logout succeeded with valid credentials.");
    }

    @Test(priority = 2)
    public void loginWithInvalidCredentials() throws IOException {
        test = extent.createTest("Test to Login With Invalid Credentials");

        loginPage.setUserName(BaseTest.prop.getProperty("userName2"));
        loginPage.setPassword(BaseTest.prop.getProperty("password2"));
        loginPage.clickOnLoginButton();

        String expectedError = BaseTest.prop.getProperty("loginError");
        String actualError = loginPage.captureInvalidLoginError();

        Assert.assertEquals(actualError, expectedError, "❌ Error message mismatch!");
        seleniumUtils.takeScreenShoot();

        test.pass("✅ Invalid login correctly prevented access.");
    }
    
    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
    	driver.close();
    }
}