package Prectices;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FinalTest {
	protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public String url = "file:///Users/khosruzzaman/ALL_JAVA/FrameWork/ZSolutionAutomationProject/zSolutions/index.html";
 
	Selenium_Utils su = new Selenium_Utils();
	WebDriverWait wait;
	@BeforeTest(alwaysRun = true)
	public WebDriver setup() {
	 
		WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		driver.set(new ChromeDriver(option));
		getDriver().get(url);
		getDriver().findElement(By.xpath("//*[@id='username']")).sendKeys("admin");
		getDriver().findElement(By.xpath("//*[@id='password']")).sendKeys("test123");
		getDriver().findElement(By.xpath("//button[contains(text(), 'Login')]")).click();
		
		return getDriver();
	}
	
	public static WebDriver getDriver() { return driver.get();}
	
	@Test
	public void loginPagetitle() {
		System.out.println("current page title is : "+getDriver().getTitle());
		String expectedTitle = "zSolution";
		Assert.assertEquals(getDriver().getTitle(), expectedTitle, "title did not match");
		
	}
	@Test
	public void textBoxTest() {
		//New User details
		String name = "Khosruz";
		String email = "Khosruz@zsolution.com";
		String address = "508 winfield ave, Upper darby, PA, 19082";
		
		getDriver().findElement(By.xpath("/html/body/div[1]/button[1]")).click();
		getDriver().findElement(By.id("name")).sendKeys(name);
		getDriver().findElement(By.id("email")).sendKeys(email);
		getDriver().findElement(By.id("address")).sendKeys(address);
		getDriver().findElement(By.xpath("//*[@id='text-box']/div[1]/button")).click();
		
		// check result after submit user details result-name
		WebElement resultName = getDriver().findElement(By.id("result-name"));
		WebElement resultEmail = getDriver().findElement(By.id("result-email"));
		WebElement resultAddress = getDriver().findElement(By.id("result-address"));
		
		// validate user details
		Assert.assertEquals(resultName.getText(), name, "Sorry, Name did not matched!!");
		Assert.assertEquals(resultEmail.getText(), email, "Sorry, email did not matched!!");
		Assert.assertEquals(resultAddress.getText(), address, "Sorry, Address did not matched!!");
		
		System.out.println("User name: "+name+"\nUser Email: "+email+"\nUser Address: "+address);
	}
	
	@Test
	public void dropdownTest() {
		//Input data
		String myCountry = "UK";
		String myCity = "London";
		
		//get identifires
		getDriver().findElement(By.xpath("/html/body/div[1]/button[4]")).click();
		getDriver().findElement(By.id("country")).click();
		WebElement country = getDriver().findElement(By.id("country"));
		WebElement city = getDriver().findElement(By.id("city"));
		
		//select country from Country dropdownList
		Select countrySelect = new Select(country);
		countrySelect.selectByVisibleText(myCountry);
		
		//select country from Country dropdownList
		Select citySelect = new Select(city);
		citySelect.selectByVisibleText(myCity);
		
		System.out.println("Selected value '" + myCity + "' from the country : " + myCountry.toString());
		
		// Get results 
		getDriver().findElement(By.xpath("//*[@id='dropdown']/div[1]/button")).click();
		WebElement countryResult = getDriver().findElement(By.id("selected-country"));
		WebElement cityResult = getDriver().findElement(By.id("selected-city"));
		
		//validate result 
		Assert.assertEquals(countryResult.getText(),myCountry, "Not country matched");
		Assert.assertEquals(cityResult.getText(),myCity, "Not City matched");
		
		
		
		
		
	}
	
	@Test
	public void aletsPopUpsTest() {
		// click Alert manue
		getDriver().findElement(By.xpath("/html/body/div[1]/button[5]")).click();
		
		// get alerts and popups
		WebElement showAlert = getDriver().findElement(By.xpath("//*[@id='alerts-popups']/button[1]"));
		WebElement showConfirmation = getDriver().findElement(By.xpath("//*[@id='alerts-popups']/button[2]"));
		WebElement promptAlert = getDriver().findElement(By.xpath("//*[@id='alerts-popups']/button[3]"));
		
		// basic Alert
		showAlert.click();
		
		// Alert class invoke
		Alert clk = getDriver().switchTo().alert();
		
		String alertText = clk.getText();
		Assert.assertEquals(alertText, "This is a simple alert!", "Message didnot matched!!");
		
		System.out.println("Alert Text captured as : " + alertText);
		clk.dismiss();
		
		// Confirmation alert
		showConfirmation.click();
		
		String confirmationText = clk.getText();
		Assert.assertEquals(confirmationText, "Are you sure you want to proceed?", "Message didnot matched!!");
		System.out.println("Confirmation Alert Text captured as : " + confirmationText);
		clk.accept();
		
		// prompt Alert
		promptAlert.click();
		String inputValue = "Khosruz zaman";
		clk.sendKeys(inputValue);
		clk.accept();
		String propmptText = clk.getText();
		Assert.assertEquals(propmptText, "Hello, "+inputValue, "Message didnot matched!!");
		System.out.println("Prompt Alert Text captured as : " + propmptText);
		clk.dismiss();
		
		
		
		
	}
	
	@Test
	public void brokenLinkTest() {
		/*
		 * Use the following steps to identify broken links in Selenium
			Use <a> tag to fetch all the links present on a web page
			Send HTTP request for the link
			Verify the HTTP response code for the link
			Determine if the link is valid or it is broken based on the HTTP response code
			Repeat the process for all links captured with the first step
			
		*/
		//nevigate to url
	//	getDriver().get("https://zsolution.com");
		getDriver().get("https://databook.wegov.nyc/people/pr6086495774-donna-l-robinson");
		 
		
		//find all available links in webpage
		List<WebElement> links = getDriver().findElements(By.tagName("a"));
		String[] linkText = new String[links.size()];
		int i = 0;
		
		
		//extract the link texts of each link element	
		for(WebElement e:links) {
			linkText[i]= e.getText();
			i++;
			
		}
		
//		// Iterate each link and check the response status 
//		for(WebElement link: links) {
//			String url = link.getAttribute("herf");
//			
//			su.verifyLinks(url);
//		}
		
		for(String t:linkText) {
			try {
				getDriver().findElement(By.linkText(t)).click();
				su.verifyLinks(t);
			} catch (Exception e1) {
			System.out.println(e1.getMessage());
			break;
			}
		}
		
		
		
	}
	
	@Test
	public void iFrameTest() throws InterruptedException {
		//head to IFrame window
		getDriver().findElement(By.xpath("/html/body/div[1]/button[7]")).click();
		
		// fatch child windows
		WebElement frame1 = getDriver().findElement(By.id("frame1"));
		WebElement frame2 = getDriver().findElement(By.id("frame2"));
		
		// switch to frame1
		getDriver().switchTo().frame(frame1);
		Thread.sleep(2000);
		
		// get text from Frame 1
		WebElement headers = getDriver().findElement(By.tagName("h2"));
		WebElement text = getDriver().findElement(By.tagName("p"));
		 System.out.println("Frame 1 : Title is: "+headers.getText()+"\nText : " + text.getText());
	    
	    // Back to main window
	    getDriver().switchTo().defaultContent();
		
		// switch to frame2
		getDriver().switchTo().frame(frame2);
		Thread.sleep(2000);
		
		// get text from Frame 1
		WebElement headers2 = getDriver().findElement(By.tagName("h2"));
		WebElement text2 = getDriver().findElement(By.tagName("p"));
		
	    System.out.println("Frame 2 : Title is: "+headers2.getText()+"\nText : " + text2.getText());
	    
	    
	}
	
	@Test
	public void dragNdrop() {
		
	}
	
	
	@AfterTest(alwaysRun = true)
	public void tearDown() {
		getDriver().close();	
	}

}
