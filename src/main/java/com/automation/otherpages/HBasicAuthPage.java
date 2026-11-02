package com.automation.otherpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HBasicAuthPage {
	
	private final WebDriver driver;
	private final HElementActions element;
	
	public HBasicAuthPage(WebDriver driver)
	{
		this.driver=driver;
		this.element=new HElementActions(driver);
	}
	
	private final By authMessage =By.cssSelector("div#content p"); 
	
	public String authVerifcation()
	{
		return element.waitForVisibility(authMessage).getText().trim();
	}

}
