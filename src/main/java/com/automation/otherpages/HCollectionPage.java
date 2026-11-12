package com.automation.otherpages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HCollectionPage {

	private final WebDriver driver;
	private final HElementActions action;
	
	
	public HCollectionPage(WebDriver driver)
	{
		this.driver = driver;
		this.action = new HElementActions(driver);
		
	}
	
	//STATUSCODE
	private final By codeLocator = By.cssSelector(" div#content ul li a");

	//DISAPPEARING ELEMENTS
	private final By disappearingLocator = By.cssSelector(" div#content div.example ul li a");
	
	//DROPDOWN
	private final By dropdownLocator = By.cssSelector(" div#content select#dropdown option");
	
	//TABLES
	private final By headerLocator = By.cssSelector("table#table1 thead>tr>th");
	private final By bodyLocator = By.cssSelector("table#table1 tbody>tr");

	
	public List<String> verifyStatusCode()
	{
		List<WebElement> codeElements = driver.findElements(codeLocator);
		List<String> codeString = new ArrayList<>();
		
		for(WebElement codeElement:codeElements)
		{
			codeString.add(codeElement.getText().trim());
		}
		return codeString;
	}
	
	public Set<String> verifyDisappearingElements()
	{
		List<WebElement> disappearingElements = driver.findElements(disappearingLocator);
		Set<String> disappearingString = new HashSet<>();
		
		System.out.println("..........disappearingElements.........."+disappearingElements.size());
		for(WebElement disappearingElement:disappearingElements)
		{
			disappearingString.add(disappearingElement.getText().trim());
		}
		return disappearingString;
	}
	
	public Map<String,String> verifyDropdown()
	{
		List<WebElement> dropdownElements = driver.findElements(dropdownLocator);
		Map<String, String> dropdownString = new HashMap<>();
		
		for(WebElement dropdownElement:dropdownElements)
		{
			dropdownString.put(dropdownElement.getText().trim(),dropdownElement.getAttribute("value"));
		}
		return dropdownString;
	}
	public List<Map<String,String>> verifyTable()
	{
		List<WebElement> headerElements = driver.findElements(headerLocator);
		List<String> headerString = new ArrayList<>();
		
		List<WebElement> bodyElements = driver.findElements(bodyLocator);
		List<Map<String, String>> tableData = new ArrayList<>();
		
		for(WebElement headerElement:headerElements)
		{
			headerString.add(headerElement.getText().trim());
		}
		
		for(WebElement bodyElement:bodyElements)
		{
			Map<String,String> headerBodyMap = new HashMap<>();
			
			List<WebElement> rowData = bodyElement.findElements(By.xpath("./td"));
			
			for(int i =0; i<headerElements.size();i++)
			{
				headerBodyMap.put(headerString.get(i), rowData.get(i).getText().trim());
			}
			
			tableData.add(headerBodyMap);
			
		}
	return tableData;	
	}
}

