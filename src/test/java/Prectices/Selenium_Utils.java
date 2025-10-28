package Prectices;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;

import org.checkerframework.checker.units.qual.h;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Selenium_Utils {
	
	WebDriver driver;
	
	
	public static void verifyLinks(String url) {
		try {
			URL links = new URL(url);
			HttpURLConnection httpUrlConnections = (HttpURLConnection) links.openConnection();
			httpUrlConnections.setConnectTimeout(3000); // set for 3 sec
			httpUrlConnections.connect();
			
			if(httpUrlConnections.getResponseCode() == 200) {
				System.out.println(url+" _ "+ httpUrlConnections.getResponseMessage()+" - Not broken link.");
			}else {
				System.out.println(url+ " _ "+httpUrlConnections.getResponseMessage()+" _ "+
			" is a broken links");
			}
		} catch (Exception e) {
		System.out.println(url);
		}
	}
	


}
