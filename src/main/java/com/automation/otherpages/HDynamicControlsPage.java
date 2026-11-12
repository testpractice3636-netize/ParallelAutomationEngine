package com.automation.otherpages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.automation.otherpages.HElementActions;

public class HDynamicControlsPage {

	private final WebDriver driver;
	private final HElementActions element;
	
	public HDynamicControlsPage(WebDriver driver)
	{
		this.driver = driver;
		this.element = new HElementActions(driver);
	}
	
	
	private final By checkBox = By.xpath("//form[@id='checkbox-example']//div[contains(text(), 'A checkbox')]/input");
	// CSS -> above conversion is impossible because there no text handling in css
	
	//private final By removeAddButton = By.xpath("//form[@id='checkbox-example']//button[@type = 'button']");
	//CSS --> BELOW
	private final By removeAddButton =  By.cssSelector(" form#checkbox-example button[type='button']");
	
	//private final By message = By.xpath("//form[@id='checkbox-example']//p[@id='message']");
	//CSS --> BELOW
	private final By message = By.cssSelector(" form#checkbox-example p#message");
	
	//private final By textBox = By.xpath("//form[@id='input-example']/input");
	//CSS -->BELOW
	private final By textBox =  By.cssSelector(" form#input-example>input");
	
	//private final By textBox =  By enableDisableButton = By.xpath("//form[@id='input-example']/button");
	//CSS -->BELOW
	private final By enableDisableButton =  By.cssSelector(" form#input-example>button");

	//private final By message1 = By.xpath("//form[@id='input-example']//p[@id='message']");
	//CSS -->BELOW
	private final By message1 = By.cssSelector(" form#input-example p#message");
	
	
	public void selectCheckBox()
	{
		//WebElement checkBox = 
		if(!element.waitForClickable(checkBox).isSelected())
		
		element.clickElement(checkBox);
		
		else
			System.out.println("Check Already selected");
	}
	
	public String removeAddButton()
	{
		try
		{
		String message = element.waitForVisibility(removeAddButton).getText().trim();
		 element.clickElement(removeAddButton);
		 return message;
		}
		catch(TimeoutException e)
		{
			//org.testng.Assert.fail("💥 [TIMEOUT-FAILURE] Expected text message [" + message 
		      //      + "] failed to load in the DOM within " + 10 + " seconds due to backend lag.", e);
		        
		        throw e;
		}
	}
	
	public String getActionMessage() {
			return element.waitForPresence(message).getText().trim();
		
	}
	
	public String enableTextBox()
	{
		try
		{
		WebElement textBoxCheck = element.waitForPresence(textBox);
		if (!textBoxCheck.isEnabled())
			element.clickElement(enableDisableButton);
		//note the below line funcnality
		element.getWait().until(ExpectedConditions.textToBePresentInElementLocated(message1,"It's enabled!" ));
		return element.waitForPresence(message1).getText().trim();
		}
		catch(TimeoutException e)
		{
			//Assert.fail("[TIMEOUT-FAILURE] Expected text message failed to load in the DOM within " + 10 + " seconds due to backend lag.", e);
			throw e;
		}
	}
	
	public String disableTextBox(String textMessage)
	{
		String disabledTextBoxMessage="";
		WebElement textBoxValidation = element.waitForPresence(textBox);
		if (textBoxValidation.isEnabled())
		{
			element.enterText(textBox, textMessage);
			element.clickElement(enableDisableButton);
			disabledTextBoxMessage = element.waitForPresence(message1).getText().trim();
			//note the below line functionality
			element.getWait().until(ExpectedConditions.textToBePresentInElementLocated(message1,"It's disabled!" ));
			return disabledTextBoxMessage;

		}
		return disabledTextBoxMessage;

		
		}	
		
	}
	

