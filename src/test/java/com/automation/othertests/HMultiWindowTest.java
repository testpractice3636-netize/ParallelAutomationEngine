package com.automation.othertests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HMultiWindowPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HMultiWindowTest extends BaseTest{

	@Test(priority=1,description="verification of multiwindow")
	public void verificationOfMultiWindow()
	{
		getDriver().switchTo().defaultContent();
		getDriver().get(ConfigReader.getProperty("base.url")+"windows");
		
		HMultiWindowPage hMultiWindow = new HMultiWindowPage(getDriver());
		
		String newTabText = hMultiWindow.verifyNewWindow();
		
		Assert.assertEquals(newTabText, "New Window","New Tab Navigation Issue");
		
	}
}
