package com.automation.othertests;

import static org.testng.Assert.assertEquals;

import com.automation.otherpages.HDynamicControlsPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HDynamicControlsTest extends BaseTest{
	
	@Test(priority = 1, description = "Remove or Add checkbox")
	public void verifyDynamicControls()
	{
		getDriver().switchTo().defaultContent();
        getDriver().get(ConfigReader.getProperty("base.url") + "dynamic_controls");
		
		HDynamicControlsPage hDynamicControl = new HDynamicControlsPage(getDriver());
		
		String remove = "It's gone!";
		String add = "It's back!";
		
		String enable = "It's enabled!";
		String disable = "It's disabled!";
		
		String textMessage = "Validating enabled text";
		
		String buttonMessage = hDynamicControl.removeAddButton();
		String actionMesssage = hDynamicControl.getActionMessage();
		
		if (buttonMessage.equals("Remove"))
			Assert.assertEquals(actionMesssage, remove, "Issue in removing checkbox");
		else
			Assert.assertEquals(actionMesssage, add, "issue in adding checkbox");
		
		String textBoxStatusMessage1 = hDynamicControl.enableTextBox();
		Assert.assertEquals(textBoxStatusMessage1, enable,"Issue in enabling textbox");
		
		String textBoxStatusMessage2 = hDynamicControl.disableTextBox(textMessage);
		Assert.assertEquals(textBoxStatusMessage2, disable,"Issue in disabling textbox");
	
		
	}
	

}
