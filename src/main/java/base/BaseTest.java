package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExtentReportManager;
import utils.ScreenshotUtility;

public class BaseTest {

    // Thread-safe WebDriver
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Extent Reports
    protected ExtentReports extent;
    protected ExtentTest test;

    public static Properties prop;
    public static String configPath = "/Users/khosruzzaman/ALL_JAVA/FrameWork/ZSolutionAutomationProject/src/main/resources/config.properties";

    // ------------------ CONSTRUCTOR ------------------
    public BaseTest() {
        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream(configPath);
            prop.load(fis);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // ------------------ EXTENT REPORT SETUP ------------------
    @BeforeSuite
    public void setupExtentReport() {
        if (extent == null) {
            extent = ExtentReportManager.getInstance();
        }
    }

    @BeforeClass(alwaysRun = true)
    public void initExtentForClass() {
        // Ensure extent is initialized if running single class
        if (extent == null) {
            extent = ExtentReportManager.getInstance();
        }
    }

    public ExtentReports getExtent() {
        if (extent == null) {
            extent = ExtentReportManager.getInstance();
        }
        return extent;
    }

    // ------------------ WEBDRIVER INITIALIZATION ------------------
    public WebDriver initialization() {
    	//System.setProperty("webdriver.chrome.silentOutput", "true");
    	java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.OFF);
        if (prop == null) {
            try {
                prop = new Properties();
                FileInputStream fis = new FileInputStream(configPath);
                prop.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("❌ Failed to load config.properties!");
            }
        }

        String browserName = prop.getProperty("browser").trim().toLowerCase();
        try {
            switch (browserName) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--start-maximized");
                    options.addArguments("--remote-allow-origins=*");
                    driver.set(new ChromeDriver(options));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver.set(new FirefoxDriver());
                    break;

                case "safari":
                    driver.set(new SafariDriver());
                    break;

                default:
                    throw new IllegalArgumentException("❌ Unsupported browser: " + browserName);
            }

            if (getDriver() == null) {
                Assert.fail("❌ WebDriver was not initialized properly!");
            }

            String url = prop.getProperty("url");
            if (url == null || url.isEmpty()) {
                Assert.fail("❌ URL is not defined in config.properties!");
            }

            getDriver().get(url);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("❌ WebDriver initialization failed!");
        }

        return getDriver();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    // ------------------ TEST RESULT HANDLING ------------------
    @AfterMethod(alwaysRun = true)
    public void handleTestResult(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                String screenshotPath = ScreenshotUtility.captureScreenshot(getDriver(), result.getName());
                if (test != null) {
                    test.fail(result.getThrowable(),
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                }
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                if (test != null) test.pass("✅ Test Passed");
            } else if (result.getStatus() == ITestResult.SKIP) {
                if (test != null) {
                    test.skip(result.getThrowable());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------ TEARDOWN DRIVER ------------------
 // Closes all drivers after the entire <test> in TestNG finishes
    @AfterClass(alwaysRun = true)
    public void tearDownAllDrivers() {
        try {
            WebDriver currentDriver = driver.get(); // ✅ get actual WebDriver from ThreadLocal
            if (currentDriver != null) {
                currentDriver.quit();  // ✅ closes browser correctly
                //driver.remove();       // ✅ clears ThreadLocal reference
                System.out.println("✅ All browsers closed after tests.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Warning: Driver already closed or session ended. " + e.getMessage());
        }
    }

}