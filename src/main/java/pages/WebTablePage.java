package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebTablePage {
	private WebDriver driver;
	private WebDriverWait wait; // ✅ Global wait instance

	@FindBy(xpath = "/html/body/div[1]/button[8]")
	WebElement tableMenu;

	@FindBy(css = "#employeeTable tbody tr")
	List<WebElement> tableRows;

	@FindBy(id = "newName")
	WebElement inputNewName;

	@FindBy(id = "newRole")
	WebElement inputNewRole;

	@FindBy(id = "newLocation")
	WebElement inputNewLocation;

	@FindBy(xpath = "//button[contains(text(), 'Add Record')]")
	WebElement btnAddRecord;

	@FindBy(id = "table-message")
	WebElement messageDiv;

	public WebTablePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ✅ initialize once
		PageFactory.initElements(driver, this);
	}

	public void clickOnTableMenu() {
		tableMenu.click();
	}

	// ===== Existing methods =====
	public int getRowCount() {
		return tableRows.size();
	}

	public List<List<String>> getTableData() {
		List<List<String>> tableData = new ArrayList<>();
		for (WebElement row : tableRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			List<String> rowData = new ArrayList<>();
			for (WebElement cell : cells) {
				rowData.add(cell.getText().trim());
			}
			tableData.add(rowData);
		}
		return tableData;
	}

	public boolean isRecordPresent(String name, String role, String location) {
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#tableBody tr")));
		List<WebElement> rows = driver.findElements(By.cssSelector("#tableBody tr"));

		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			if (cells.size() >= 4 && cells.get(1).getText().equalsIgnoreCase(name)
					&& cells.get(2).getText().equalsIgnoreCase(role)
					&& cells.get(3).getText().equalsIgnoreCase(location)) {
				return true;
			}
		}
		return false;
	}

	public List<String> getNamesByLocation(String targetLocation) {
		final int NAME_IDX = 1;
		final int LOCATION_IDX = 3;
		String locTarget = targetLocation.trim().toLowerCase();

		return tableRows.stream().map(r -> r.findElements(By.tagName("td")))
				.filter(cells -> cells.size() > LOCATION_IDX)
				.filter(cells -> cells.get(LOCATION_IDX).getText().trim().equalsIgnoreCase(locTarget))
				.map(cells -> cells.get(NAME_IDX).getText().trim()).collect(Collectors.toList());
	}

	public boolean isRolePresent(String roleToCheck) {
		String roleLc = roleToCheck.trim().toLowerCase();
		for (WebElement row : tableRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			if (cells.size() >= 5 && cells.get(2).getText().trim().toLowerCase().equals(roleLc)) {
				return true;
			}
		}
		return false;
	}

	// ===== Add New Record =====
	public String addNewRecord(String name, String role, String location) {
		inputNewName.clear();
		inputNewName.sendKeys(name);

		inputNewRole.clear();
		inputNewRole.sendKeys(role);

		inputNewLocation.clear();
		inputNewLocation.sendKeys(location);

		btnAddRecord.click();

		wait.until(ExpectedConditions.visibilityOf(messageDiv));
		return messageDiv.getText().trim();
	}

	// ===== Delete Record =====
	public String deleteRecordByName(String name) {
		try {
			List<WebElement> rows = driver.findElements(By.cssSelector("#tableBody tr"));
			for (WebElement row : rows) {
				List<WebElement> cells = row.findElements(By.tagName("td"));
				if (cells.size() > 0 && cells.get(1).getText().equalsIgnoreCase(name)) {
					WebElement deleteButton = row.findElement(By.xpath("./td[5]/button"));
					wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();

					// ✅ Wait for success message to appear
					WebElement message = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("table-message")));
					String msgText = message.getText();
					System.out.println("✅ Deletion message displayed: " + msgText);

					// ✅ Wait until record disappears from DOM (use a fresh locator, not the old row
					// object)
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//td[text()='" + name + "']/parent::tr")));

					return msgText;
				}
			}
		} catch (Exception e) {
			System.out.println("⚠️ Error deleting record: " + e.getMessage());
		}
		return null;
	}

	public String getInlineMessage() {
		try {
			return messageDiv.getText().trim();
		} catch (NoSuchElementException e) {
			return "";
		}
	}
}