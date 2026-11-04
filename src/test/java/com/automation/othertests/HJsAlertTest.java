package com.automation.othertests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HJsAlertPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HJsAlertTest extends BaseTest{

	@Test(priority=1, description="verifivation of Javascript Alert")
	
	public void verifyJsAlert()
	{
		getDriver().get(ConfigReader.getProperty("base.url")+"javascript_alerts");
		
		HJsAlertPage hJsAlert = new HJsAlertPage(getDriver());
		
		String[] alertButtons = {"Click for JS Alert", "Click for JS Confirm","Click for JS Prompt"};
		
		for(int i=0;i<alertButtons.length;i++)
		{
		String currentButton = alertButtons[i];
		String jsMessage = hJsAlert.alertAction(currentButton);
		
		String expectedKeyword = currentButton.replace("Click for JS ", ""); 
		
		Assert.assertTrue(jsMessage.contains(expectedKeyword),"Issue executing action for : " +currentButton+ ": Expecting messageto conatain :" + expectedKeyword + " :but got :" + jsMessage + "");
		
		
	}
		}
}
