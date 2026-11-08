package com.automation.othertests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HCustDropdownPage;
import com.automation.otherpages.ODropDownPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;


	public class HCustDropdownTest extends BaseTest {

		@Test(priority=1,description="verify the dropdown")
		public void verifyDropdown()
		{
			getDriver().get(ConfigReader.getProperty("base.url") + "custom_dropdown");
			
			HCustDropdownPage dropdownPage = new HCustDropdownPage(getDriver());
			
			Boolean isSelected = dropdownPage.selectItems("Banana");
			Assert.assertTrue(isSelected, "ISSUE IN SELECTING DROPDOWN");
		}
	}


