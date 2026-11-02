package com.automation.othertests;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HBasicAuthPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HBasicAuthTest extends BaseTest{
	
	@Test(priority =1, description = "Verify basic authentication")
	public void VerifyBasicAuth()
	{
		secureNavigation();
	}
	
	public void secureNavigation()
	{
		getDriver().switchTo().defaultContent();
		
		String baseUrl = ConfigReader.getProperty("base.url");
		String uName = ConfigReader.getProperty("secure.user");
		String pass =ConfigReader.getProperty("secure.pass");
		
		 String authenticatedUrl = baseUrl.replace("https://", "https://" + uName + ":" + pass + "@") + "basic_auth"; 
	        
		getDriver().get(authenticatedUrl);
		new WebDriverWait(getDriver(), Duration.ofSeconds(10))
        .until(ExpectedConditions.urlContains("/basic_auth"));
			
		HBasicAuthPage hBasicAuth = new HBasicAuthPage(getDriver());
		Assert.assertEquals(hBasicAuth.authVerifcation(), "Congratulations! You must have the proper credentials.", "Authentication Issue!!");
	}
}
