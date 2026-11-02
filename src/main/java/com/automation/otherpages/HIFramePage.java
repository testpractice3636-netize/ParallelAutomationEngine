package com.automation.otherpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HIFramePage {

	private final WebDriver driver;
	private HElementActions element;
	
	public HIFramePage(WebDriver driver)
	{
		this.driver=driver;
		this.element=new HElementActions(driver);
	}
	
	private final By iframeContext = By.id("mce_0_ifr");
	private final By iframeInput = By.id("tinymce");
	private final By mainHeaderText = By.cssSelector(" div#content h3");
	
	public String verifyInputIFrame()
	{
		driver.switchTo().defaultContent();
		
		 element.getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeContext));
		
		try
		{
		element.enterText(iframeInput,"Architect Level Entry");
		}
		finally
		{
		driver.switchTo().defaultContent();
		}
		String mainText = element.waitForVisibility(mainHeaderText).getText().trim();
		return mainText;
	}
}
