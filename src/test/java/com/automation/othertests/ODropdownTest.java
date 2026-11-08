package com.automation.othertests;

import com.automation.otherpages.ODropDownPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class ODropdownTest extends BaseTest {

	@Test(priority=1,description="verify the dropdown")
	public void verifyDropdown()
	{
		getDriver().get(ConfigReader.getProperty(("base.url") + "?");
		
		ODropDownPage dropdownPage = ODropDownPage(getDriver());
		
		dropdownPage.selectDestination("New", "New York");
	}
}
