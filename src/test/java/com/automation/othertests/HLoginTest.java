package com.automation.othertests;

import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.bidi.network.Cookie;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.otherpages.HLoginPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;
import com.automation.utils.ExcelReader;


public class HLoginTest extends BaseTest{
	private static final String TEST_DATA_PATH = "C:\\QA_Automation\\Workspace\\ParallelAutomationEngine\\TestDataa.xlsx";
    private static final String LOGIN_POSITIVE_SHEET     = "HLoginPositive";
    //private static final String LOGIN_NEGATIVE_SHEET  = "HLoginNegative";
    
    @DataProvider(name = "PositiveMapDataPipeline", parallel = true)
    public Object[][] getPositiveMapData() {
        return ExcelReader.getSheetMatrixAsMap(TEST_DATA_PATH, LOGIN_POSITIVE_SHEET);
    }
    
   /* @DataProvider(name = "NegativeMapDataPipeline", parallel = true)
    public Object[][] getNegativeMapData() {
        return ExcelReader.getSheetMatrixAsMap(TEST_DATA_PATH, LOGIN_NEGATIVE_SHEET);
    }*/	
	
    @Test(dataProvider = "PositiveMapDataPipeline", priority = 1)
    public void runPositiveLoginPipeline(Map<String, String> dataRow) {
        // Delegates cleanly to the master workflow with the positive control flag set to true
    	verifyLogin(dataRow, true);
    }
    
    @Test(priority=1, description="Verify login")
	public void verifyLogin(Map<String, String> dataRow, boolean isPositiveScenario)
	{
    	String uname = dataRow.get("Username");
    	String pass = dataRow.get("Password");
    	
    	getDriver().get(ConfigReader.getProperty("base.url") + "two_factor");
    	
    	HLoginPage hLogin = new HLoginPage(getDriver());
 	
    	String loginMsg = hLogin.verifyLogin(uname,pass,isPositiveScenario);
    	
    	String loginFinalMsg = hLogin.verifyCode();

    	org.openqa.selenium.Cookie authorizedSessionCookie = getDriver().manage().getCookieNamed("rack.session");

    	
    	if(loginMsg.contains("Code"))
    		Assert.assertTrue(loginMsg.contains("Code"), "ISSUE IN LOGIN");
        	else
        	Assert.assertTrue(loginMsg.contains("invalid"), "ISSUE IN LOGIN");
        	
    	Assert.assertTrue(loginFinalMsg.contains("Welcome!"), "ISSUE wWITH LOGIN");
    	
    	String  injectCookieSuccessMsg = hLogin.verifyloginWithCookies(authorizedSessionCookie);
    	
    	Assert.assertTrue(injectCookieSuccessMsg.contains("secure"), "ISSUE IN COOKIE LOGIN");
    	
    	
	}

}
