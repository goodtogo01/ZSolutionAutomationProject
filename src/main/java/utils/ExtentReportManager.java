package utils;



import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReportManager - Handles creation and configuration of ExtentReports
 * for the ZSolution automation framework.
 *
 * This class ensures a single report instance is used throughout execution,
 * applies a timestamped report file name, and loads custom CSS/JS if available.
 */
public class ExtentReportManager {

    private static ExtentReports extent;

    /**
     * Creates and returns a singleton ExtentReports instance.
     * @return ExtentReports instance
     */
    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    /**
     * Builds and configures a new ExtentReports instance with Spark reporter.
     * @return Configured ExtentReports instance
     */
    private static ExtentReports createInstance() {
        ExtentReports extent = new ExtentReports();

        // Timestamp for report file
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport/ExtentReport_" + timestamp + ".html";

        // Initialize Spark Reporter
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("ZSolution Automation Report");
        spark.config().setReportName("ZSolution Test Execution Report");

        // Load custom CSS and JS if available
        try {
            String cssPath = "src/test/resources/spark/css/spark-style.css";
            String jsPath = "src/test/resources/spark/js/spark-script.js";

            if (Files.exists(Paths.get(cssPath))) {
                spark.config().setCss(new String(Files.readAllBytes(Paths.get(cssPath))));
            }
            if (Files.exists(Paths.get(jsPath))) {
                spark.config().setJs(new String(Files.readAllBytes(Paths.get(jsPath))));
            }
        } catch (IOException e) {
            System.err.println("⚠️ Warning: Unable to load custom CSS/JS for Extent Report - " + e.getMessage());
        }

        // Attach reporter
        extent.attachReporter(spark);

        // Add system/environment info
        extent.setSystemInfo("Project", "ZSolution Automation");
        extent.setSystemInfo("Framework", "Selenium + TestNG");
        extent.setSystemInfo("Executed On", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        return extent;
    }

    /**
     * Flushes and closes the report instance.
     */
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}