package com.automation.othertests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HCollectionPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HCollectionTest extends BaseTest{
	
	@Test(priority=1,description = "verify the status code")
	public void verifyStatusCode()
	{
		getDriver().get(ConfigReader.getProperty("base.url") + "status_codes");
		
		HCollectionPage collection = new HCollectionPage(getDriver());
		
		List<String> codeCheck = collection.verifyStatusCode();
				 
		Assert.assertTrue(codeCheck.contains("200"),"ISSUE IN STATUS CODE");
		Assert.assertTrue(codeCheck.contains("301"),"ISSUE IN STATUS CODE");

	}
	
	@Test(priority=2,description = "verify disappearing element")
	public void verifyDisappearingElement()
	{
		getDriver().get(ConfigReader.getProperty("base.url") + "disappearing_elements");
		
		HCollectionPage collection = new HCollectionPage(getDriver());
		
		Set<String> disappearingCheck = collection.verifyDisappearingElements();
		
		Assert.assertTrue(disappearingCheck.contains("About"),"ISSUE IN DISAPPEARING ELEMENTS");
		Assert.assertTrue(disappearingCheck.contains("Home"),"ISSUE IN DISAPPEARING ELEMENTS");
	    System.out.println("Is Gallery visible on this run? " + disappearingCheck.contains("Gallery"));

	}
	@Test(priority=3,description = "verify dropdown")
	public void verifydropdown()
	{
		getDriver().get(ConfigReader.getProperty("base.url") + "dropdown");
		
		HCollectionPage collection = new HCollectionPage(getDriver());
		
		Map<String,String> dropdownCheck = collection.verifyDropdown();
		
		//IS THERE ANY WAYS TO CHECK KEY AND VALUE IN SAME SENTENCE?
		Assert.assertTrue(dropdownCheck.containsKey("Option 1"),"ISSUE IN Dropdown");
		Assert.assertTrue(dropdownCheck.containsValue("2"),"ISSUE IN Dropdown");

	}
	@Test(priority=4,description = "verify table")
	public void verifytable()
	{
		getDriver().get(ConfigReader.getProperty("base.url") + "tables");
		
		HCollectionPage collection = new HCollectionPage(getDriver());
		
		List<Map<String, String>> table = collection.verifyTable();
		System.out.println("Full Table Data: " + table);

		for (Map<String, String> row : table) {
		    if (row.get("Email").equals("jdoe@hotmail.com")) {
		        Assert.assertEquals(row.get("Due"), "$100.00", "Due balance mismatch for Jason Doe!");
		    }
		}

	}
	
}
