package com.automation.otherpages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HMultiWindowPage {

	private final WebDriver driver;
	private final HElementActions element;
	
	public HMultiWindowPage(WebDriver driver)
	{
		this.driver = driver;
		this.element=new HElementActions(driver);
	}
	private final By click = By.cssSelector(" div#content a");
	private final By newTabMessage = By.cssSelector(" div.example h3");
	
	public String verifyNewWindow ()
	{
		String parentWindowId = driver.getWindowHandle();
		
		element.clickElement(click);
		
		element.getWait().until(ExpectedConditions.numberOfWindowsToBe(2));
		String newTabText ="";
		Set<String> childWindowIds = driver.getWindowHandles();
		for(String window:childWindowIds)
		{
			if(!window.equalsIgnoreCase(parentWindowId))
			{
				driver.switchTo().window(window);
				break;
			}
		}
		
		newTabText=element.waitForVisibility(newTabMessage).getText().trim();

		driver.close();
		driver.switchTo().window(parentWindowId);
		
		return newTabText;
		
	}
}
