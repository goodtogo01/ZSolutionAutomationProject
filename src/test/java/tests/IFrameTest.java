package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import base.BaseTest;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import pages.DropdownPage;
import pages.IFramePage;
import pages.LoginPage;
import utils.SeleniumUtils;

public class IFrameTest extends BaseTest {

    private WebDriver driver;
    private SeleniumUtils seleniumUtils;
    private DropdownPage dropdownPage;
    private IFramePage iframePage;
    private LoginPage loginPage;

    // ✅ Login once before all IFrame tests
    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws MalformedURLException {
        driver = initialization(); // ✅ from BaseTest
        seleniumUtils = new SeleniumUtils(driver);
        iframePage = new IFramePage(driver);
        dropdownPage = new DropdownPage(driver);
        loginPage = new LoginPage(driver);

        // ✅ Navigate to login page
        driver.get(BaseTest.prop.getProperty("url"));

        // ✅ Perform login
        loginPage.setUserName(BaseTest.prop.getProperty("userName"));
        loginPage.setPassword(BaseTest.prop.getProperty("password"));
        loginPage.clickOnLoginButton();

        // ✅ Validate login
        String expectedHomeTitle = BaseTest.prop.getProperty("homePageTitle");
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedHomeTitle, "❌ Login failed or page title mismatch!");
        System.out.println("✅ Successfully logged in. Current Page Title: " + actualTitle);
    }

    // ✅ Navigate to IFrame menu before each test
    @BeforeMethod(alwaysRun = true)
    public void navigateToIFrameMenu() {
        iframePage.navigateToIFrameMenu();
        test = extent.createTest("Navigate to IFrame Menu");
        test.pass("✅ Navigated to IFrame section successfully.");
    }

    // ----------------------------- TESTS ----------------------------- //

    @Test(priority = 1)
    public void testIFrame1Content() {
        test = extent.createTest("Test iFrame1 Content");

        Assert.assertTrue(iframePage.isIFramePresent("frame1"), "❌ frame1 is not present!");
        iframePage.switchToIFrame("frame1");

        String content = iframePage.getIFrameContent("//h2"); // grabs h2 inside iframe
        System.out.println("Frame1 Content: " + content);
        Assert.assertTrue(content.contains("Frame 1"), "❌ Frame1 content mismatch!");

        iframePage.switchToMainContent();
        test.pass("✅ Frame1 content verified successfully.");
    }

    @Test(priority = 2)
    public void testIFrame2Content() {
        test = extent.createTest("Test iFrame2 Content");

        Assert.assertTrue(iframePage.isIFramePresent("frame2"), "❌ frame2 is not present!");
        iframePage.switchToIFrame("frame2");

        String content = iframePage.getIFrameContent("//h2"); // grabs h2 inside iframe
        System.out.println("Frame2 Content: " + content);
        Assert.assertTrue(content.contains("Frame 2"), "❌ Frame2 content mismatch!");

        iframePage.switchToMainContent();
        test.pass("✅ Frame2 content verified successfully.");
    }

    @Test(priority = 3)
    public void testSwitchBetweenIFrames() throws InterruptedException {
        test = extent.createTest("Test Switch Between iFrames");

        // Switch to frame1
        Assert.assertTrue(iframePage.isIFramePresent("frame1"), "❌ frame1 is not present!");
        iframePage.switchToIFrame("frame1");
        Thread.sleep(1000); // optional wait
        iframePage.switchToMainContent();

        // Switch to frame2
        Assert.assertTrue(iframePage.isIFramePresent("frame2"), "❌ frame2 is not present!");
        iframePage.switchToIFrame("frame2");
        Thread.sleep(1000);
        iframePage.switchToMainContent();

        test.pass("✅ Successfully switched between frame1 and frame2 and back to main content.");
    }

    @Test(priority = 4)
    public void testVerifyIFrameContentPresence() {
        test = extent.createTest("Verify iFrame Content Presence");

        // Verify frame1 content
        iframePage.switchToIFrame("frame1");
        String frame1Text = iframePage.getIFrameContent("//p");
        Assert.assertTrue(frame1Text.contains("first embedded frame"), "❌ Frame1 paragraph content mismatch!");
        iframePage.switchToMainContent();

        // Verify frame2 content
        iframePage.switchToIFrame("frame2");
        String frame2Text = iframePage.getIFrameContent("//p");
        Assert.assertTrue(frame2Text.contains("switching between iFrames"), "❌ Frame2 paragraph content mismatch!");
        iframePage.switchToMainContent();

        test.pass("✅ iFrame paragraph contents verified successfully.");
    }

    // ✅ Close browser after all tests
    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("✅ Browser closed after IFrame tests.");
        }
    }
}