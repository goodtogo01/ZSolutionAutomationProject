package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RadioButtonPage {
	private WebDriver driver;

	@FindBy(xpath = "/html/body/div[1]/button[2]")
	WebElement clickManue;

	// private By selectMale = By.xpath("//*[@value='Male']");
	@FindBy(xpath = "//*[@value='Male']")
	WebElement selectMale;

	@FindBy(xpath = "//*[@value='Male']")
	WebElement selectFemale;

	@FindBy(xpath = "//*[@value='Other']")
	WebElement selectOther;

	@FindBy(xpath = "//*[@value='18-25']")
	WebElement selectAge18_25;

	@FindBy(xpath = "//*[@value='26-35']")
	WebElement selectAge26_35;

	@FindBy(xpath = "//*[@value='36']")
	WebElement selectAge_36;

	public RadioButtonPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickMainManue() {
		clickManue.click();
	}

	public void selectMaleOption() {
		selectMale.click();
	}

	public void selectFemaleOption() {
		selectFemale.click();
	}

	public void selectOtherOption() {
		selectOther.click();
	}

	public void selectAge18_25() {
		selectAge18_25.click();
	}

	public void selectAge26_35() {
		selectAge26_35.click();
	}

	public void selectAgeUp_36() {
		selectAge_36.click();
	}

	// Verify if Male radio button is selected
	public boolean isMaleSelected() {
		return selectMale.isSelected();
	}

	// Verify if Female radio button is selected
	public boolean isFemaleSelected() {
		return selectFemale.isSelected();
	}

	// Verify if particuler age 18-25 radio button is selected
	public boolean isParticulerAgeSelected1825() {
		return selectAge18_25.isSelected();
	}

	// Verify if particuler age 26-35 radio button is selected
	public boolean isParticulerAgeSelected2635() {
		return selectAge26_35.isSelected();
	}

}