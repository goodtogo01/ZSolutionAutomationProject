package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class DropdownPage {
    
    private WebDriver driver;

    private By dropDownMenu = By.xpath("/html/body/div[1]/button[4]");
    private By selectCountry = By.id("country");
    private By selectCity = By.id("city");

    public DropdownPage(WebDriver driver) {
        this.driver = driver;
    }

    // Navigate to dropdown menu
    public void navigateToDropdownManue() {
        driver.findElement(dropDownMenu).click();
    }

    // Select a value from dropdown
    public void selectDropdownValue(By dropdown, String value) {
        WebElement element = driver.findElement(dropdown);
        Select select = new Select(element);
        select.selectByVisibleText(value);
        System.out.println("Selected value '" + value + "' from dropdown: " + dropdown.toString());
    }

    // Get all dropdown options
    public List<String> getAllDropdownOptions(By dropdown) {
        WebElement element = driver.findElement(dropdown);
        Select select = new Select(element);
        return select.getOptions().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // Verify if specific option exists
    public boolean isOptionPresentInDropdown(By dropdown, String value) {
        List<String> options = getAllDropdownOptions(dropdown);
        return options.contains(value);
    }

    // Verify if dropdown supports multi-select
    public boolean isMultiSelect(By dropdown) {
        WebElement element = driver.findElement(dropdown);
        Select select = new Select(element);
        return select.isMultiple();
    }

    // Get the currently selected option
    public String getSelectedOption(By dropdown) {
        WebElement element = driver.findElement(dropdown);
        Select select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }
}