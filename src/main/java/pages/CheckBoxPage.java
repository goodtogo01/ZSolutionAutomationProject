package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CheckBoxPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By checkBoxMenu = By.xpath("/html/body/div[1]/button[3]");

    public CheckBoxPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToCheckBoxMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(checkBoxMenu)).click();
    }

    public void selectCheckBox(By checkBox) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(checkBox));
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void deselectCheckBox(By checkBox) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(checkBox));
        if (element.isSelected()) {
            element.click();
        }
    }

    public boolean isCheckBoxSelected(By checkBox) {
        return wait.until(ExpectedConditions.elementToBeClickable(checkBox)).isSelected();
    }

    // Get all checkboxes dynamically from the page
    public List<WebElement> getAllCheckBoxElements() {
        return driver.findElements(By.xpath("//*[@type='checkbox']"));
    }

    // Select all checkboxes dynamically
    public void selectAllCheckBoxes() throws InterruptedException {
        for (WebElement cb : getAllCheckBoxElements()) {
            if (!cb.isSelected()) {
                cb.click();
                Thread.sleep(2000);
            }
        }
    }

    // Deselect all checkboxes dynamically
    public void deselectAllCheckBoxes() {
        for (WebElement cb : getAllCheckBoxElements()) {
            if (cb.isSelected()) {
                cb.click();
            }
        }
    }

    // Verify all checkboxes are selected
    public boolean areAllCheckBoxesSelected() {
        for (WebElement cb : getAllCheckBoxElements()) {
            if (!cb.isSelected()) {
            	 cb.click();
            	 return false;
            	 }
        }
        return true;
    }

    public int getSelectedCheckBoxCount() {
        int count = 0;
        for (WebElement cb : getAllCheckBoxElements()) {
            if (cb.isSelected()) {
                count++;
            }
        }
        return count;
    }
}