package com.automation.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.pages.WebInputsPage;
import com.automation.utils.ExcelReader;
import com.automation.pages.NavigationPage;

public class WebInputsTest extends BaseTest {

    private static final String TEST_DATA_PATH = "C:\\QA_Automation\\Workspace\\ParallelAutomationEngine\\TestDataa.xlsx";
    private static final String WEBINPUT_POSITIVE_SHEET    = "WebInputsPositive";
    private static final String WEBINPUT_NEGATIVE_SHEET  = "WebInputsNegative";


    @DataProvider(name = "PositiveWebInputsPipeline", parallel = true)
    public Object[][] getPostiveInputsData() {
        // Leverages your local, synchronized memory-safe reader
        return ExcelReader.getSheetMatrixAsMap(TEST_DATA_PATH, WEBINPUT_POSITIVE_SHEET);
    }
    
    @DataProvider(name = "NegativeWebInputsPipeline", parallel = true)
    public Object[][] getNegativeInputsData() {
        // Leverages your local, synchronized memory-safe reader
        return ExcelReader.getSheetMatrixAsMap(TEST_DATA_PATH, WEBINPUT_NEGATIVE_SHEET);
    }

    
    @Test(dataProvider = "PositiveWebInputsPipeline", priority = 1)
    public void runPositiveInputsPipeline(Map<String, String> dataRow) {
    	verifyDynamicWebInputsScenario(dataRow, true);
    }

    @Test(dataProvider = "NegativeWebInputsPipeline", priority = 2)
    public void runNegativeInputsPipeline(Map<String, String> dataRow) {
    	verifyNumberFieldRejectsAlphabets(dataRow, false);
    }
    
    
     
    public void verifyDynamicWebInputsScenario(Map<String, String> dataRow, boolean isPositiveScenario)
    {
    	if (dataRow == null || dataRow.get("Number") == null) return;

        // 📥 Clean extraction of variables from the dataRow Map row collection
        String num  = dataRow.get("Number");
        String text = dataRow.get("Text");
        String pass = dataRow.get("Password");
        String date = dataRow.get("Date");
        String expectedMsg = dataRow.get("Expected message");
        
        long threadId = Thread.currentThread().getId();
        System.out.println("[THREAD-" + threadId + "] >>> STARTING SCENARIO 6 FOR: " + text);

        System.out.println("------ START: Web Inputs Active Pipeline for Test Record: " + text + " ------");

        try {
            // 1. Navigation Flow Routine
            NavigationPage navigationPage = new NavigationPage(getDriver());
            navigationPage.contentPageLink();
            navigationPage.webInputLink(getDriver());

            WebInputsPage inputsPage = new WebInputsPage(getDriver());

            // ⚖️ DYNAMIC ROUTING FORK
            if (isPositiveScenario) {
                System.out.println("[TRACK] Executing Positive Integrity Workflow Form Submission...");
                
                // Call the aggregated single workflow method
                inputsPage.fillInputForm(num, text, pass, date);

                // Verify the UI panel output mirrors what we sent out completely
                Assert.assertEquals(inputsPage.getDisplayedNumberOutput(), num, "[FAIL] Output number mismatch!");
                Assert.assertEquals(inputsPage.getDisplayedTextOutput(), text, "[FAIL] Output text string mismatch!");
                System.out.println("✅ Positive Matrix Data Checked Successfully.");

            } else {
                System.out.println("[TRACK] Executing Negative Field-Level Boundary Restrictions...");
                
                // Granular Validation Check: Type bad data (e.g. text characters) into the number field
                inputsPage.enterNumber(num); 
                
                // Extract the value attribute from the text box right now
                String currentTextInBox = inputsPage.getNumberFieldValue();
                
                // Assert that the field successfully blocked the data and remained empty!
                Assert.assertEquals(currentTextInBox, "", 
                    "[FIELD-SECURITY-FAIL] Number text box accepted invalid text inputs: " + currentTextInBox);
                System.out.println("✅ Negative Security Boundary Checked Successfully.");
            }

        } catch (Throwable e) {
            Assert.fail("Web Inputs execution track dropped dead mid-run: " + e.getMessage());
        }
    }
    
    
    public void verifyNumberFieldRejectsAlphabets(Map<String, String> dataRow, boolean isPositiveScenario)
    {
    	if (dataRow == null || dataRow.get("Number") == null) return;

        // 📥 Clean extraction of variables from the dataRow Map row collection
        String num  = dataRow.get("Number");
        String text = dataRow.get("Text");
        String pass = dataRow.get("Password");
        String date = dataRow.get("Date");
        String expectedMsg = dataRow.get("Expected message");
        

    	long threadId = Thread.currentThread().getId();
        System.out.println("[THREAD-" + threadId + "] >>> STARTING SCENARIO 6 FOR: " + text);

        System.out.println("------ START: Web Inputs Active Pipeline for Test Record: " + text + " ------");

        try {
            // 💡 FIX: Added missing navigation flow routine so the browser lands on the right page before checking elements!
            NavigationPage navigationPage = new NavigationPage(getDriver());
            navigationPage.contentPageLink();
            navigationPage.webInputLink(getDriver());

            WebInputsPage webIPPage = new WebInputsPage(getDriver());
            
            // 1. Inject invalid characters (e.g., "asd@#") into the number box
            webIPPage.enterNumber(num); 
            
            // 2. Extract the backend text out of the input element's "value" attribute
            String actualTextInBox = webIPPage.getNumberFieldValue(); 
            System.out.println("Text remaining inside the input field: " + actualTextInBox);
            
            // 3. Assert that the field successfully blocked it and remains empty!
            Assert.assertEquals(actualTextInBox, "", 
                "[FAIL] UI Bug: The number input field accepted alphabetic/special characters: " + actualTextInBox);
            System.out.println("✅ Negative Security Boundary Checked Successfully.");
            
        } catch (Throwable e) {
            Assert.fail("Web Inputs negative execution track dropped dead: " + e.getMessage());
        }
    }

}
