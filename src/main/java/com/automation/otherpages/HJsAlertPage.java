package com.automation.otherpages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HJsAlertPage {

	private final WebDriver driver;
	private final HElementActions action;
	
	public HJsAlertPage(WebDriver driver)
	{
		this.driver = driver;
		this.action=new HElementActions(driver);
		
	}
	
 	public String alertAction(String decideAlert)
	{
		By alertLocator = By.xpath("//div[@id='content']//li/button[text()='" + decideAlert + "']");
		
		action.clickElement(alertLocator);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Alert alertActionLocator = action.getWait().until(ExpectedConditions.alertIsPresent());
        String text = alertActionLocator.getText();
            if (decideAlert.contains("Alert")) {
            	alertActionLocator.accept(); 
            } else if (decideAlert.contains("Confirm")){
            	alertActionLocator.dismiss();
            } else
            {
                action.getWait().until(ExpectedConditions.alertIsPresent());
            	alertActionLocator.sendKeys("Js Test");
            	alertActionLocator.accept();
            }
        
        return text;
	}

	
	
}
