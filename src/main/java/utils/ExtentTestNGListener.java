package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import base.BaseTest;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGListener implements ITestListener {

    private static ExtentReports extent = ExtentReportManager.getInstance();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, "Test Failed");
        extentTest.get().log(Status.FAIL, result.getThrowable());

        // Capture a screenshot for failed tests
        WebDriver driver = BaseTest.getDriver(); // ✅ Safe way
        String screenshotPath = ScreenshotUtility.captureScreenshot(driver, result.getMethod().getMethodName());

        try {
            extentTest.get().fail("Screenshot attached", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped");
        extentTest.get().log(Status.SKIP, result.getThrowable());
    }

    @Override
    public void onStart(ITestContext context) {
        // Do nothing
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

}
