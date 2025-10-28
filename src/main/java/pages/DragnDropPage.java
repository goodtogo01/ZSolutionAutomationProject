package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DragnDropPage {

	private WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;

	// ✅ Sidebar Drag & Drop Menu Button
	@FindBy(xpath = "/html/body/div[1]/button[5]")
	private WebElement dragDropMenu;

	// ✅ Drag source items
	@FindBy(css = ".draggable")
	private List<WebElement> draggableItems;

	// ✅ Drop target area
	@FindBy(css = ".drop-target")
	private WebElement dropTarget;

	// ✅ Drop confirmation text (if available)
	private By dropResultMessage = By.id("drop-result");

	// -------------------- Constructor --------------------
	public DragnDropPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.actions = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	// -------------------- Actions --------------------

	public void navigateToDragDropMenu() {
		wait.until(ExpectedConditions.elementToBeClickable(dragDropMenu)).click();
	}

	public boolean isDraggableItemsPresent() {
		wait.until(ExpectedConditions.visibilityOfAllElements(draggableItems));
		return !draggableItems.isEmpty();
	}

	public boolean isDropTargetVisible() {
		wait.until(ExpectedConditions.visibilityOf(dropTarget));
		return dropTarget.isDisplayed();
	}

	// Drag one specific item
	public void dragItemToTarget(int itemIndex) {
		WebElement source = draggableItems.get(itemIndex);
		actions.dragAndDrop(source, dropTarget).perform();
	}

	// Drag all available items
	public void dragAllItemsToTarget() {
		for (WebElement item : draggableItems) {
			actions.dragAndDrop(item, dropTarget).pause(Duration.ofMillis(300)).perform();
		}
	}

	public String getDropResultText() {
		try {
			WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(dropResultMessage));
			return result.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}
}