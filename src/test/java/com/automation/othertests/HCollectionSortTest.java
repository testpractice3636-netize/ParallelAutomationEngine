package com.automation.othertests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
	
	
	
}
