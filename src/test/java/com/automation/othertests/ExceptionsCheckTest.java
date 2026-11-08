package com.automation.othertests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.ExceptionsCheckPage;
import com.automation.otherpages.HDynamicControlsPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class ExceptionsCheckTest extends BaseTest{
	
	@Test(priority=1, description="verify dynamic controls")
	public void verifyDynamicControls()
	{
		getDriver().switchTo().defaultContent();
        getDriver().get(ConfigReader.getProperty("base.url") + "dynamic_controls");
		
        ExceptionsCheckPage hDynamicControl = new ExceptionsCheckPage(getDriver());
		
        String textMessage = "Disable check";
		String cBoxStatus = hDynamicControl.removeCheckboxAndGetMessage();
		
		String inputStatusMsg = hDynamicControl.enableInputAndTypeText(textMessage);
		Assert.assertTrue(cBoxStatus.contains("gone"), "ISSUE IN REMOVING CHECKBOX");
		Assert.assertTrue(inputStatusMsg.contains("disabled"), "ISSUE IN DISABLING TEXT");

	}
}
