package com.automation.othertests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HDynamicLoadingPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HDynamicLoadingTest extends BaseTest{

	@Test(priority=1, description="Validate the dynamically loading element")
	public void VerifyDynamicLoadingText()
	{
		getDriver().switchTo().defaultContent();
		getDriver().get(ConfigReader.getProperty("base.url") + "dynamic_loading");
		
		String urlValue = "2";
		String dynamicText = "Hello World!";
		HDynamicLoadingPage hdylo = new HDynamicLoadingPage(getDriver());
		System.out.println("......Enter 1.......");
		hdylo.loadDynamicLink();
		System.out.println("url is valid.....:");
		
		//Assert.assertTrue(urlState, "ISSUE in loading dynamic url");
		
		String uIdynamicText = hdylo.loadDynamicText();
		
		Assert.assertEquals(dynamicText, uIdynamicText, "Issue inloading dynamic Text");
		
	}
	
}
