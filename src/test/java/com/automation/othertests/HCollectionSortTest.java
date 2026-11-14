package com.automation.othertests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HCollectionSortPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HCollectionSortTest extends BaseTest{

	
	@Test(priority=1, description="verify the table sorting")
	public void verifyTableSorting()
	{
		getDriver().get(ConfigReader.getProperty("base.url")+"tables");
		
		HCollectionSortPage sortPage = new HCollectionSortPage(getDriver());
		
		sortPage.clickLastNameHeader();
		List<String> actualList = sortPage.getLastNames();
		System.out.println("------actualList-----"+actualList);
		List<String> expectedSortList = new ArrayList<>(actualList);
		Collections.sort(expectedSortList);
		System.out.println("------expectedSortList------"+expectedSortList);	
		
		
		Assert.assertEquals(actualList, expectedSortList, "Last Names are not sorted A-Z!");
		
	}
	@Test(priority=2, description="verify the table duplicates")
	public void verifyTableDuplicates()
	{
		getDriver().get(ConfigReader.getProperty("base.url")+"tables");
		
		HCollectionSortPage sortPage = new HCollectionSortPage(getDriver());
		
		List<String> actualEmails = sortPage.getAllEmails();
		Set<String> uniqueEmails = new HashSet<>(actualEmails);
		
		System.out.println("Total Emails in Table: " + actualEmails.size());
		System.out.println("Unique Emails in Set: " + uniqueEmails.size());
		
		Assert.assertEquals(uniqueEmails.size(), actualEmails.size(), "Duplicate email addresses found in table!");

	}
	@Test(priority=3, description="verify the table index")
	public void verifyTableIndex()
	{
		getDriver().get(ConfigReader.getProperty("base.url")+"tables");
		
		HCollectionSortPage sortPage = new HCollectionSortPage(getDriver());
		
		List<String> lastNames = sortPage.getLastNames();
		Map<String, Integer> nameCountMap = new HashMap<>();
		
		for(String name:lastNames)
		{
			nameCountMap.put(name, nameCountMap.getOrDefault(name,0)+1);
		}
		
		Assert.assertEquals(nameCountMap.get("Smith"), Integer.valueOf(1));

	}
	
	
	
	
}
