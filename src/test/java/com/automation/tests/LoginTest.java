package com.automation.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.automation.pages.LoginPage;
import com.automation.pages.NavigationPage;
import com.automation.utils.ExcelReader;
import com.automation.pages.*; 
import com.automation.utils.ConfigReader;

import org.testng.Assert;
import java.lang.reflect.Method;
import java.util.Map;

public class LoginTest extends BaseTest {
    
    private static final String TEST_DATA_PATH = "C:\\QA_Automation\\Workspace\\ParallelAutomationEngine\\TestDataa.xlsx";
    private static final String LOGIN_POSITIVE_SHEET     = "LoginPositive";
    private static final String LOGIN_NEGATIVE_SHEET  = "LoginNegative";
    
    /**
     * 📊 MASTER DATA ROUTER: Uses Java Reflection (java.lang.reflect.Method) 
     * to dynamically inject the correct Excel tab into the single test execution pipeline.
     */
  /*  @DataProvider(name = "UnifiedMapDataProvider", parallel = true)
    public Object[][] getUnifiedMapData(Method method) {
        // Automatically switches the target Excel sheet based on the calling method name!
        if (method.getName().equals("runPositiveLoginPipeline")) {
            return ExcelReader.getSheetMatrixAsMap(TEST_DATA_PATH, LOGIN_SHEET);
        } else if (method.getName().equals("runNegativeLoginPipeline")) {
            return ExcelReader.getSheetMatrixAsMap(TEST_DATA_PATH, NEGATIVE_SHEET);
        }
        throw new IllegalArgumentException("Fatal: No Excel sheet mapping configured for method: " + method.getName());
    }*/
    
    
    @DataProvider(name = "PositiveMapDataPipeline", parallel = true)
    public Object[][] getPositiveMapData() {
        return ExcelReader.getSheetMatrixAsMap(TEST_DATA_PATH, LOGIN_POSITIVE_SHEET);
    }
    
    @DataProvider(name = "NegativeMapDataPipeline", parallel = true)
    public Object[][] getNegativeMapData() {
        return ExcelReader.getSheetMatrixAsMap(TEST_DATA_PATH, LOGIN_NEGATIVE_SHEET);
    }

    
 // ======================================================================
    // 🚀 EXPOSED PIPELINES: Zero Redundancy, Pure Configuration
    // ======================================================================

    @Test(dataProvider = "PositiveMapDataPipeline", priority = 1)
    public void runPositiveLoginPipeline(Map<String, String> dataRow) {
        // Delegates cleanly to the master workflow with the positive control flag set to true
        executeLoginWorkflow(dataRow, true);
    }

    /*@Test(dataProvider = "NegativeMapDataPipeline", priority = 2)
    public void runNegativeLoginPipeline(Map<String, String> dataRow) {
        // Delegates cleanly to the master workflow with the positive control flag set to false
        executeLoginWorkflow(dataRow, false);
    }*/

    
    
    // 🎯 REUSE MECHANIC: The core execution engine block written EXACTLY ONCE
    private void executeLoginWorkflow(Map<String, String> dataRow, boolean isPositiveScenario) {
        try {
            String email = dataRow.get("Email"); 
            String password = dataRow.get("Password");
            
            System.out.println("------ ENTRY: Unified Pipeline Active for User: " + email + " ------");
            
            getDriver().switchTo().defaultContent();
            getDriver().get("https://expandtesting.com");
            
            NavigationPage navigationPage = new NavigationPage(getDriver());
            navigationPage.contentPageLink();
            navigationPage.cLoginLnk(getDriver());
            
            // Shared Synchronization Anchor
            org.openqa.selenium.support.ui.WebDriverWait pageWait = 
                new org.openqa.selenium.support.ui.WebDriverWait(getDriver(), java.time.Duration.ofSeconds(15));
            pageWait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/login"));
            
            LoginPage loginPage = new LoginPage(getDriver());
            loginPage.loginToApplication(email, password);
            
            String loginConfirm = loginPage.loginSuccessMsgValidation();
            System.out.println("LOGGED IN MESG :" + loginConfirm);
            String username = email.split("@")[0];
            if (loginConfirm.contains("logged"))
            Assert.assertTrue(loginConfirm.contains(username),"Not logged in");

            boolean loginStatus = loginPage.isLoginSuccessful();
            
            // ⚖️ DYNAMIC ASSERTION FORK: Handles the validation path cleanly without duplicate code!
            if (isPositiveScenario) {
                System.out.println("[VALIDATION] Checking Positive Landing Expectations...");
                Assert.assertTrue(loginStatus, "[ASSERTION-FAIL] Login failed for valid user: " + email);
                Assert.assertTrue(getDriver().getCurrentUrl().contains("secure"), "[ASSERTION-FAIL] URL mismatch!");
            } else {
                System.out.println("[VALIDATION] Checking Negative Security Restrictions...");
                Assert.assertFalse(loginStatus, "[ASSERTION-FAIL] Security Breach! Access allowed for invalid user: " + email);
            }
            
        } catch (Throwable e) {
            Assert.fail("Parallel matrix execution dropped dead: " + e.getMessage());
        }
    }

    }
