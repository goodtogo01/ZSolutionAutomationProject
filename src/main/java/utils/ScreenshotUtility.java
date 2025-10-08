package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.security.SecureRandom;

public class ScreenshotUtility {

	private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    // Generate random 6-character alpha-numeric string
    public static String getRandomAlphaNumeric() {
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = RANDOM.nextInt(ALPHANUMERIC.length());
            sb.append(ALPHANUMERIC.charAt(index));
        }
        return sb.toString();
    }

    // Capture screenshot with unique filename
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) {
            System.out.println("⚠️ WebDriver is null, skipping screenshot.");
            return null;
        }

        try {
            // ✅ Check if window still open
            driver.getTitle(); // simple no-op call that fails if browser closed

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String destPath = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
            FileUtils.copyFile(src, new File(destPath));
            return destPath;

        } catch (NoSuchWindowException e) {
            System.out.println("⚠️ Browser already closed, skipping screenshot capture.");
        } catch (WebDriverException e) {
            System.out.println("⚠️ WebDriver not reachable, screenshot skipped: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}