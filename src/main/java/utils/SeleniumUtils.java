package utils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

public class SeleniumUtils {

    private WebDriver driver;
    private WebDriverWait wait;

    public SeleniumUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebDriverWait getWait(int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    // Wait for element visible
    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait for element clickable
    public WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Click element
    public void click(By locator) {
        waitForElementClickable(locator).click();
    }

    // Enter text
    public void enterText(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    // Dropdown selections
    public void selectByVisibleText(By locator, String text) {
        new Select(waitForElementVisible(locator)).selectByVisibleText(text);
    }

    public void selectByIndex(By locator, int index) {
        new Select(waitForElementVisible(locator)).selectByIndex(index);
    }

    public void selectByValue(By locator, String value) {
        new Select(waitForElementVisible(locator)).selectByValue(value);
    }

    // Alerts
    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    // iFrames
    public void switchToIframe(By locator) {
        driver.switchTo().frame(waitForElementVisible(locator));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // Highlight element
    public void highlightElement(By locator) {
        WebElement element = waitForElementVisible(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
    }

    // Scroll to element
    public void scrollToElement(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", waitForElementVisible(locator));
    }

    // Screenshot
    public void takeScreenshot(String name) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/Screen_Shoot/" + name + "_" + System.currentTimeMillis() + ".png";
        FileUtils.copyFile(src, new File(path));
    }

    // Get all links
    public List<String> getAllLinks() {
        List<String> links = new ArrayList<>();
        for (WebElement link : driver.findElements(By.tagName("a"))) {
            String href = link.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                links.add(href);
            }
        }
        return links;
    }

    // Check for broken link
    public boolean isLinkBroken(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            return connection.getResponseCode() >= 400;
        } catch (Exception e) {
            return true;
        }
    }

	public static void takeScreenShoot() {
		// TODO Auto-generated method stub
		
	}

	public void waitForTextToBePresent(By tagName, String string) {
		// TODO Auto-generated method stub
		
	}
}