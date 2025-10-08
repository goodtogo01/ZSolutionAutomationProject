package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class TextBoxPage {

    private WebDriver driver;

    // -------------------- Locators -------------------- //
    private By clickTextBox = By.xpath("/html/body/div[1]/button[1]");
    private By nameField = By.id("name");
    private By emailField = By.id("email");
    private By addressField = By.id("address");
    private By submitButton = By.xpath("//*[@id='text-box']/div[1]/button");

    // Results
    private By resultName = By.id("result-name");
    private By resultEmail = By.id("result-email"); // ✅ Fixed duplicate ID
    private By resultAddress = By.id("result-address");

    // -------------------- Constructor -------------------- //
    public TextBoxPage(WebDriver driver) {
        this.driver = driver;
    }

    // -------------------- Page Actions -------------------- //

    // Navigate to TextBox page
    public void clickTextButton() {
        driver.findElement(clickTextBox).click();
    }

    // Fill user details
    public void userDetails(String Name, String Email, String Address) {
        driver.findElement(nameField).clear();
        driver.findElement(nameField).sendKeys(Name);

        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(Email);

        driver.findElement(addressField).clear();
        driver.findElement(addressField).sendKeys(Address);
    }

    // Submit the form
    public void clickSubmit() {
        WebElement submit = driver.findElement(submitButton);
        submit.click();
    }

    // -------------------- Get Results -------------------- //
    public String getdOutputName() {
        return driver.findElement(resultName).getText();
    }

    public String getdOutputEmail() {
        return driver.findElement(resultEmail).getText();
    }

    public String getdOutputAddress() {
        return driver.findElement(resultAddress).getText();
    }

    // -------------------- Reusable Validations -------------------- //
    public void validateNameWithList(List<String> validNames) {
        String actualName = getdOutputName();
        boolean match = validNames.stream().anyMatch(actualName::contains);
        Assert.assertTrue(match, "❌ Name output mismatch! Expected one of " + validNames + " but found: " + actualName);
    }

    public void validateEmailWithList(List<String> validEmails) {
        String actualEmail = getdOutputEmail();
        boolean match = validEmails.stream().anyMatch(actualEmail::contains);
        Assert.assertTrue(match, "❌ Email output mismatch! Expected one of " + validEmails + " but found: " + actualEmail);
    }

    public void validateAddressWithList(List<String> validAddresses) {
        String actualAddress = getdOutputAddress();
        boolean match = validAddresses.stream().anyMatch(actualAddress::contains);
        Assert.assertTrue(match, "❌ Address output mismatch! Expected one of " + validAddresses + " but found: " + actualAddress);
    }
}