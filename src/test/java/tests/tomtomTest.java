package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class tomtomTest {

    public static void main(String[] args) {

        ThreadLocal<WebDriver> driver = new ThreadLocal<>();
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver.set(new ChromeDriver(options));

        driver.get().get("file:///Users/khosruzzaman/ALL_JAVA/FrameWork/ZSolutionAutomationProject/Applications/login.html");

        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));

        try {
            WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            WebElement pass = driver.get().findElement(By.id("password"));
            WebElement loginBtn = driver.get().findElement(By.xpath("//button[contains(text(),'Login')]"));

            userName.sendKeys("admin");
            pass.sendKeys("test123");
            loginBtn.click();

            // ✅ Wait for logout button to appear after login
            WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Logout')]")));

            System.out.println("Page title after login: " + driver.get().getTitle());

            logoutBtn.click();
            System.out.println("Successfully logged out.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.get().quit();
            driver.remove();
        }
    }
}