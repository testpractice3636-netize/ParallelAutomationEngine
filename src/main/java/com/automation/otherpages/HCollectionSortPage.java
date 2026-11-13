package com.automation.otherpages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.automation.otherpages.HElementActions;
public class HCollectionSortPage {
	
	private final WebDriver driver;
	private final HElementActions action;
	
	public HCollectionSortPage(WebDriver driver)
	{
		this.driver = driver;
		this.action = new HElementActions(driver);
	}
	
	private final By BodyLastNamesLocator = By.cssSelector(" table#table1 tbody tr td:nth-child(1)");
	private final By HeaderLastNameLocator = By.cssSelector("table#table1 thead tr th:nth-child(1)");
	
	public void clickLastNameHeader()
	{
		 action.waitForClickable(HeaderLastNameLocator).click();

	}
	public List<String> getLastNames()
	{
		
		 action.waitForVisibility(BodyLastNamesLocator);
		 List<WebElement> LastNameElements = driver.findElements(BodyLastNamesLocator);
		 List<String> LastNamesString = new ArrayList<> ();
		 
		 for(WebElement LastNameElement : LastNameElements)
		 {
			 LastNamesString.add(LastNameElement.getText().trim());
		 }
		return LastNamesString;
		
	}
}
