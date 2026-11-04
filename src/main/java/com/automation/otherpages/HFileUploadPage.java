package com.automation.otherpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HFileUploadPage {

	private final WebDriver driver;
	private final HElementActions action;
	
	public HFileUploadPage(WebDriver driver)
	{
		this.driver = driver;
		this.action=new HElementActions(driver);
	}
	
	private final By chooseFile = By.cssSelector("div#content form>input#file-upload");
	private final By uploadFile = By.cssSelector("div#content form input.button");
	private final By uploadSucessMsg = By.cssSelector("div#content div.example>h3");
	
	public  void chooseFile(String filePath)
	{
		action.enterText(chooseFile, filePath);
		
	}
	public String uploadFile()
	{
		
		action.clickElement(uploadFile);
	
		return action.waitForVisibility(uploadSucessMsg).getText().trim();
		
	}
}
