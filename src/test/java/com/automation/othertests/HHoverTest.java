package com.automation.othertests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HHoverPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HHoverTest extends BaseTest{
	
	@Test(priority=1, description = "Verification of hovers and its text")
	public void verifyHover()
	{
		getDriver().switchTo().defaultContent();
		getDriver().get(ConfigReader.getProperty("base.url") + "hovers");
		
		HHoverPage hHoverPage = new HHoverPage(getDriver());
		
		int i = 1;
		
		hHoverPage.hoverInToImage(i);
		String hoverImgText = hHoverPage.getHoverImageText(i);
		hHoverPage.clickProfile(i);
		Assert.assertTrue(hoverImgText.contains("name: user"),"ISSUE IN CLICKING PROFILE");
		
		
		
	}

}
