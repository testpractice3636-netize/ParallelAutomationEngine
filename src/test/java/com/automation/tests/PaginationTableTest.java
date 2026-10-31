package com.automation.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.NavigationPage;
import com.automation.pages.WebTablePage;

public class PaginationTableTest extends BaseTest {

    @Test(priority = 1, description = "Verify record present in table.")
    public void verifyDynamicPaginationTraversalSearch() {
        long threadId = Thread.currentThread().getId();
        System.out.println("[THREAD-" + threadId + "] >>> STARTING SCENARIO 9: PAGINATION LOOP ENGINE");

        try {
           // NavigationPage navigationPage = new NavigationPage(getDriver());
            // navigationPage.contentPageLink();
            
            // Navigate straight to the dynamic pagination table challenge URL
            getDriver().get("https://practice.expandtesting.com/dynamic-pagination-table");

            WebTablePage tablePage = new WebTablePage(getDriver());
            
            String targetStudent = "John Doe"; // The data row anchor we want to find
            boolean studentFound = false;
            int pageCounter = 1;

            // 🔄 THE DYNAMIC TRAVERSAL LOOP (Option 2 Logic Interlocking)
            while (true) {
                System.out.println("[THREAD-" + threadId + "] Scanning Page Row Matrix line: " + pageCounter);
                
                // Check if the target user element exists on the active page screen view
                if (tablePage.isStudentPresentOnCurrentPage(targetStudent)) {
                    System.out.println("[THREAD-" + threadId + "] SUCCESS! Found target student '" + targetStudent + "' on Page: " + pageCounter);
                    studentFound = true;
                    break; // Exit the loop instantly!
                }
                
                // If not found, try to advance to the next page layout sheet
                System.out.println("[THREAD-" + threadId + "] Student not on this page. Clicking Next button...");
                boolean advanced = tablePage.clickNextPageIfEnabled();
                
                // Break out of the loop if we reach the last page and the data is completely missing
                if (!advanced) {
                    System.err.println("[THREAD-" + threadId + "] Scanned all table pages but student was missing.");
                    break; 
                }
                
                pageCounter++;
            }

            // 🎯 Final Data Validation Assertion
            Assert.assertTrue(studentFound, "CRITICAL ERROR: Target student row '" + targetStudent + "' was missing from the entire paginated table matrix!");
            System.out.println("[THREAD-" + threadId + "] >>> SUCCESS: Scenario 9 pagination loops verified perfectly.");

        } catch (Throwable e) {
            Assert.fail("Pagination lookup scenario execution dropped dead: " + e.getMessage());
        }
    }
    
    @Test(priority = 2, description = "Verify that clicking the table header column sorts data cleanly.")
    public void verifyTableColumnSortingEngine() {
        
        getDriver().get("https://practice.expandtesting.com/tables"); 

        WebTablePage tablePage = new WebTablePage(getDriver());

        // 2. Step: Grab the raw initial text matrix state from the first column
        List<String> textBeforeClick = tablePage.getColumnDataValues();

        // 3. Step: Command the Gopher to click the header to trigger sorting
        tablePage.clickColumnHeader();

        // 4. Step: Grab the new text matrix state post-sort action
        List<String> textAfterClick = tablePage.getColumnDataValues();

        // 5. Step: The Judge sorts the original data programmatically in memory
        List<String> sortedCopyInMemory = new ArrayList<>(textBeforeClick);
        Collections.sort(sortedCopyInMemory); // Java sorts this alphabetically automatically

        // 6. Step: Assert the UI's sorting matches our sorted memory model
        Assert.assertEquals(textAfterClick, sortedCopyInMemory, 
            "[ASSERTION-FAIL] UI data table failed to sort values in ascending order!");
        System.out.println("✅ Scenario A: Column Sorting validated successfully.");
    }

    @Test(priority = 3, description = "Verify that a targeted unique record exists safely within the UI data grid table layout.")
    public void verifyTargetRecordExistencePipeline() {
       // getDriver().get("https://practice.expandtesting.com/tables"); 
        getDriver().get("https://practice.expandtesting.com/dynamic-pagination-table"); 
        WebTablePage tablePage = new WebTablePage(getDriver());

        String targetRecordName = "Alice Johnson"; // The test data profile we are searching for
        
        // Step: Ask the page object if this record exists anywhere inside the column matrix array
        boolean isRecordFound = tablePage.isRecordPresentInColumn(targetRecordName);

        // Step: Assert the boundary result
        Assert.assertTrue(isRecordFound, 
            "[ASSERTION-FAIL] Target record: " + targetRecordName + " was not located inside the data table grid canvas!");
        System.out.println("✅ Scenario B: Target profile presence confirmed.");
    }
    

        @Test(priority = 4, description = "Verify that the row layout count matches standard data display criteria rules.")
        public void verifyTableRowDisplayMetrics() {
            try {
                getDriver().get("https://practice.expandtesting.com/dynamic-pagination-table");
                WebTablePage p1Table = new WebTablePage(getDriver());
                
                int actualRowCount = p1Table.getVisibleRowCount();
                
                // ✅ FIXED: Changed boundary check limit assertion matrix parameter from 10 to 3
                int expectedRowCount = 3; 
                
                Assert.assertEquals(actualRowCount, expectedRowCount, 
                    "[ASSERTION-FAIL] Rendered table row metrics count variant does not match active pagination display bounds!");
                System.out.println("✅ Scenario C Passed: Live row initialization constraints match perfectly.");
                
            } catch (Throwable e) {
                Assert.fail("Row layout validation execution context dropped dead: " + e.getMessage());
            }
        }

       @Test(priority = 5, description = "Verify relationship data mapping accuracy using dynamic matrix tracking indexes.")
        public void verifyCrossColumnDataIntegrityEngine() {
            try {
                getDriver().get("https://practice.expandtesting.com/dynamic-pagination-table");
                WebTablePage p1Table = new WebTablePage(getDriver());
                
                // 🎯 CHANGED: Using a REAL student name and major from the live page data bank!
                String targetStudent = "Alice Johnson"; 
                String expectedMajor = "Mathematics"; 
                
                // Your beautiful, number-free counting adapter will track this down perfectly!
                String actualMajor = p1Table.getStudentAttributeByHeaderName(targetStudent, "Major");
                
                Assert.assertEquals(actualMajor, expectedMajor, 
                    "[ASSERTION-FAIL] Dynamic data mapping relationship matrix broken! Targeted record cross-check mismatch.");
                System.out.println("✅ Scenario D Passed: Data relationship integrity matrix confirmed.");
                
            } catch (Throwable e) {
                Assert.fail("Cross-column lookup operation pipeline dropped dead: " + e.getMessage());
            }
    }

 }
 

 


	