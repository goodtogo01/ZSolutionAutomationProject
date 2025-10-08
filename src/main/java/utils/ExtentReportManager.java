package utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            // Configurations for the report
            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("Test Execution Results");
            sparkReporter.config().setTheme(Theme.DARK);
          //  sparkReporter.config().enableTimeline(true);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // Add system information
            extent.setSystemInfo("Framework", "Z Solution Automation Suit");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Khosruz zaman");
            extent.setSystemInfo("Browser", "Chrome");
        }
        return extent;
    }
}