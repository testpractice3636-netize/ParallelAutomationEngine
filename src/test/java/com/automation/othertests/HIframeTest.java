package com.automation.othertests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HIFramePage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HIframeTest extends BaseTest {

	@Test(priority=1, description = "verification of Iframe")
	public void verifyIframe()
	{
		getDriver().switchTo().defaultContent();
		getDriver().get(ConfigReader.getProperty("base.url") + "iframe");
		
		HIFramePage HIframePage = new HIFramePage(getDriver());
		String mainHeaderText = HIframePage.verifyInputIFrame();
		Assert.assertEquals(mainHeaderText, "An iFrame containing the TinyMCE WYSIWYG Editor","ISSUE WITH NAVIGATING TO DEFAULT CONTENT");
	}
}
