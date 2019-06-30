
/* Write a working program using Selenium Webdriver that will do:
• Browse to http://slashdot.org/
• Print how many articles are on the page
• Print a list of unique (different) icons used on article titles and how many times was it used
• Vote for some random option on the daily poll
• Return the number of people that have voted for that same option
• Java or Python

/* Ecobee Challenge - Question 2 - **Nicholas Goorwah**
 * ******************************************************
 */
package com.ecobee.org;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;

public class SlashdotArticlePollMetrics {
	
	public static void main(String[] args) {
		//Set System property for launching chrome driver
		System. setProperty("webdriver.chrome.driver", "..//Ecobee//src//com//ecobee//org//chromedriver.exe");
		// Create new ChromeDriver object for controling the browser.
		WebDriver driver = new ChromeDriver();
		driver.get("http://slashdot.org/");
		//Maximize window for testing the above URL.
		driver.manage().window().maximize();
		//Get the list of webelements that are responsible for finding all unique article titles or any attribute in the img tag.
		List<WebElement> titles = driver.findElements(By.xpath("//div[@id='firehoselist']/article/header/span/a/img"));
		
		for(WebElement a : titles) {
			System.out.println(a.getAttribute("title").replaceAll("()*&^%$#@!<>", ""));
		}
		//Amount of articles that exist on the home page.
		int articleCount = titles.size();
		System.out.println("Total Articles on Homepage Today: " + articleCount);
		Hashtable<String, Integer> images = new Hashtable<String, Integer>();
		String key = "";
		for(WebElement article : titles) {
			//Get the article title since it would be the key and unique identifier.
			key = article.getAttribute("title").replaceAll("()*&^%$#@!<>", "");
			//System.out.println("KEY: " + key);
			//Ensure key is not a empty string.
			if (!key.equals("")) {
				// Make sure if the hash already contains the key just increment the
				// number of occurrences.
				if(images.containsKey(key)) {
					int value = (int)images.get(key);
					images.put(key, value + 1);
				}else {
					// No occurrences exist so put a new key
					images.put(key, 1);		
				}
			}
		}
		// Get all keys in a Set and then loop through keys in the hashtable
		Set<String> keys = images.keySet();
		for(String k : keys) {
			System.out.println("Key: " + k + " " + "Occurrences: " + images.get(key));
		}
		//Get the poll booth's label objects.
		List<WebElement> booth = driver.findElements(By.xpath("//form[@id='pollBooth']/label"));
		//Get the form'ss submission button
		WebElement submitButton = driver.findElement(By.xpath("//form[@id='pollBooth']/div"));
		Random rand = new Random();
		//Random int generator - generates a random value based on the 
		//poll selection options size.
		int pollSelection = (int) (Math.random()*booth.size());
		//Get the random selection.
		String answer = booth.get(pollSelection).getText().trim();
		System.out.println("Answer: " + answer);
		//Click the radio button and submit the form.
		booth.get(pollSelection).click();
		submitButton.submit();
		//Set up for getting the results for the poll based on the previous
		//decision.
		List<WebElement> pollBarLabels = driver.findElements(By.className("poll-bar-label"));
		List<WebElement> pollBarText = driver.findElements(By.className("poll-bar-text"));
		int i = 0;
		//Loop through each option ensuring that the answer = poll label text
		for (WebElement pollLabel : pollBarLabels) {
			if (answer.equals(pollLabel.getText())) {
				String results = pollBarText.get(i).getText();
				System.out.println("Results: " + results);
			}
			i += 1;
		}
		
		
	}
}