/*package com.automation.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.automation.pages.LoginPage;
import com.automation.pages.ExcelReader;
import com.automation.pages.NavigationPage;
import com.automation.tests.MultiWindowTest;


import org.testng.Assert;
import com.automation.pages.NavigationPage;

public class LoginTest extends BaseTest{
	
	 
	/*
	// 📊 1. THE DATA PROVIDER MATRIX: Bridges your data pipeline to the test method
    @DataProvider(name = "AuthenticationDataMatrix")
    public Object[][] getLoginDataPipeline() {
        // Temporary hardcoded matrix to verify the plumbing works before wire-up with Apache POI
        return new Object[][] {
            { "practice", "SuperSecretPassword!", "John", "Doe", "638401" }
        };
        
        /* 
         * NEXT STEP INTEGRATION (Apache POI Engine):
         * return ExcelReader.getSheetMatrix("LoginData"); 
         */
   /* } ---star and slash comment 
	
	// 💎 CONSTANT CENTRALISATION: Define the file path ONCE here at the top!
    private static final String TEST_DATA_PATH = "C:\\QA_Automation\\Workspace\\ParallelAutomationEngine\\TestDataa.xlsx";
    private static final String LOGIN_SHEET     = "LoginData";
    private static final String NEGATIVE_SHEET  = "NegativeData";
    
    // 🔑 DATA PIPELINE #1: Feeds from primary sheet
    @DataProvider(name = "ExtendedTestingUserData")
    public Object[][] getUserDataPipeline() {
        // Look how clean! No hardcoded string path here anymore.
        return ExcelReader.getSheetMatrix(TEST_DATA_PATH, LOGIN_SHEET);
    }

    // 🔑 DATA PIPELINE #2: Feeds from security exceptions sheet
    @DataProvider(name = "NegativeLoginData")
    public Object[][] getNegativeDataPipeline() {
        // Uses the same single path variable for a completely different tab!
        return ExcelReader.getSheetMatrix(TEST_DATA_PATH, NEGATIVE_SHEET);
    }

    @Test(dataProvider = "ExtendedTestingUserData") 
    public void runLiveLoginTest(String email, String password, String firstName, String lastName, String zipCode) {        
         try {
        	 System.out.print("ENTERY1");
        	 
        	 NavigationPage navigationPage = new NavigationPage(getDriver());

        	 navigationPage.contentPageLink();
            // getDriver().get("https://practice.expandtesting.com/login");

        	 navigationPage.cLoginLnk(getDriver());
        	 System.out.println("----NAVIGATING FROM MAIN TO LOGIN-----");
        	 //  Instantiate our Page Object Model layer using thread-safe driver access
             LoginPage loginPage = new LoginPage(getDriver());
        	 System.out.println("IS IT??");

             // Navigate to the target portal using the thread-isolated driver
            
             // Execute our Combined Business Workflow using the dynamic Excel variables!
             System.out.println("[TEST-INFO] Injecting extracted Excel credentials into UI fields for: " + email);
            
             loginPage.loginToApplication(email, password);
        	 System.out.println("------LOGGGED IN----------");

             System.out.println("[TEST-INFO] Validating dashboard landing page metrics...");
             //boolean loginStatus = loginPage.isLoginSuccessful();
            
             boolean loginStatus = loginPage.isLoginSuccessful();
             
             if(loginStatus)
             {
             System.out.print("Secured page");
             Assert.assertTrue(getDriver().getCurrentUrl().contains("secure"));

             }
             
         }
             catch (Exception e) {
             System.err.println("[TEST-ERROR] Runtime crash encountered inside parallel execution thread!");
             e.printStackTrace();
         }
    }
}
*/

package com.automation.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.automation.pages.LoginPage;
import com.automation.pages.NavigationPage;
import com.automation.utils.ExcelReader;

import java.util.Map;

import org.testng.Assert;

public class LoginTestOld extends BaseTest {
    
    private static final String TEST_DATA_PATH = "C:\\QA_Automation\\Workspace\\ParallelAutomationEngine\\TestDataa.xlsx";
    private static final String LOGIN_SHEET     = "LoginData";
    private static final String NEGATIVE_SHEET  = "NegativeData";
    
    /*
    // 🚀 THE FIX: Added parallel = true to unlock parallel multi-row browser spawning!
    @DataProvider(name = "ExtendedTestingUserData", parallel = true)
    public Object[][] getUserDataPipeline() {
        return ExcelReader.getSheetMatrix(TEST_DATA_PATH, LOGIN_SHEET);
    }

    @DataProvider(name = "NegativeLoginData", parallel = true)
    public Object[][] getNegativeDataPipeline() {
        return ExcelReader.getSheetMatrix(TEST_DATA_PATH, NEGATIVE_SHEET);
    }
    */
    
    // 🚀 MAP DATA PROVIDER #1: Positive Data
    @DataProvider(name = "PositiveMapDataPipeline", parallel = true)
    public Object[][] getPositiveMapData() {
        return ExcelReader.getSheetMatrixAsMap(TEST_DATA_PATH, LOGIN_SHEET);
    }

    // 🚀 MAP DATA PROVIDER #2: Negative Data
    @DataProvider(name = "NegativeMapDataPipeline", parallel = true)
    public Object[][] getNegativeMapData() {
        return ExcelReader.getSheetMatrixAsMap(TEST_DATA_PATH, NEGATIVE_SHEET);
    }
    
    
    @Test(dataProvider = "PositiveMapDataPipeline") 
    public void runLiveLoginTest(Map<String, String> dataRow) {        
         try {
             // Extract variables dynamically using the exact column header string names
             String email = dataRow.get("Email"); // Adjust key string to match your exact Excel header text
             String password = dataRow.get("Password");
              //<---------detect the hand curesor
             System.out.println("------ ENTRY: Map Injection Thread Active ------");
             System.out.println("[DATA-DRIVEN] Extracting dynamic keys from map container for: " + email);
             
             getDriver().switchTo().defaultContent();
             getDriver().get("https://expandtesting.com");
             
             NavigationPage navigationPage = new NavigationPage(getDriver());
             navigationPage.contentPageLink();
             navigationPage.cLoginLnk(getDriver());
             
             // 3. 🛡️ THE SYNCHRONIZATION ANCHOR: Force the thread to wait until the login page is fully active
             //org.openqa.selenium.support.ui.WebDriverWait pageWait = 
               //      new org.openqa.selenium.support.ui.WebDriverWait(getDriver(), java.time.Duration.ofSeconds(15));
                // pageWait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/login"));
                 
             
             
             LoginPage loginPage = new LoginPage(getDriver());
             loginPage.loginToApplication(email, password);
             
             boolean loginStatus = loginPage.isLoginSuccessful();
             if (loginStatus) {
                 System.out.println("[TEST-SUCCESS] Secured page landing verified.");
                 Assert.assertTrue(getDriver().getCurrentUrl().contains("secure"), "[ASSERTION-FAIL] URL mismatch!");
             }
             
         } catch (Throwable e) {
             Assert.fail("Parallel Map iteration execution failed: " + e.getMessage());
         }
    }
    
    /*
    //USING ARRAY
    @Test(dataProvider = "ExtendedTestingUserData") 
    public void runLiveLoginTest(String email, String password, String firstName, String lastName, String zipCode) {        
         try {
             System.out.println("------ ENTRY: Excel Data Loop Thread Active ------");
             System.out.println("[DATA-DRIVEN] Parsing execution row for user: " + email);
             
             // 🚀 CLEAN CANVAS BRIDGE: Ensure each thread begins on a fresh, clean landing portal page
             getDriver().switchTo().defaultContent();
             getDriver().get("https://practice.expandtesting.com/");
             
             NavigationPage navigationPage = new NavigationPage(getDriver());
             navigationPage.contentPageLink();
             navigationPage.cLoginLnk(getDriver());
             
             
             System.out.println("---- NAVIGATING FROM MAIN TO LOGIN -----");
             LoginPage loginPage = new LoginPage(getDriver());
            
             System.out.println("[TEST-INFO] Injecting extracted Excel credentials into UI fields...");
             loginPage.loginToApplication(email, password);
             System.out.println("------ LOGGED IN MAPPING COMPLETE ----------");

             //org.testng.Assert.fail("Forcing a controlled test failure to test our new monitor listener!");
             
             System.out.println("[TEST-INFO] Validating dashboard landing page metrics...");
             boolean loginStatus = loginPage.isLoginSuccessful();
             
             if (loginStatus) {
                 System.out.println("[TEST-SUCCESS] Secured page landing verified.");
                 Assert.assertTrue(getDriver().getCurrentUrl().contains("secure"), "[ASSERTION-FAIL] URL mismatch!");
             }
             
         } catch (Throwable e) { // 💡 Uses Throwable to catch both Exceptions and Assertion errors cleanly!
             System.err.println("[TEST-ERROR] Runtime crash encountered inside parallel matrix loop: " + e.getMessage());
             Assert.fail("Parallel test iteration execution dropped dead: " + e.getMessage());
         }
    } */
}
