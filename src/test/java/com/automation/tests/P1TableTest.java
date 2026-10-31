package com.automation.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.P1TablePage;

public class P1TableTest extends BaseTest {
    
    @Test(priority = 1, description = "Verify that clicking the Student Name column header sorts data correctly.")
    public void tableSort() {
        try {
            getDriver().get("https://practice.expandtesting.com/dynamic-pagination-table");
            P1TablePage p1Table = new P1TablePage(getDriver());
            
            // Gather initial text matrix state
            List<String> actualTableData = p1Table.getTableData();
            
            // Click to sort
            p1Table.sortTable();
            
            // Gather post-sort UI text matrix state
            List<String> sortedTableData = p1Table.getTableData();
            
            // Compute expected sort memory layout model
            List<String> sortedCopyInMemory = new ArrayList<>(actualTableData);
            Collections.sort(sortedCopyInMemory);
            Assert.assertEquals(actualTableData, sortedCopyInMemory, 
                "[ASSERTION-FAIL] Web UI table column failed to display values in sorted ascending order!");
            System.out.println("✅ Test 1 Passed: Column sorting successfully validated.");
            
        } catch (Throwable e) {
            Assert.fail("Table sort scenario execution dropped dead: " + e.getMessage());
        }
    }
    
    @Test(priority = 2, description = "Verify pagination traversal mechanics work seamlessly across pages.")
    public void checkAllDataUsingNext() {
        long threadId = Thread.currentThread().getId();
        System.out.println("[THREAD-" + threadId + "] >>> STARTING SCENARIO 2: PAGINATION LOOP ENGINE");
        
        int pageCounter = 0;
        try {
            getDriver().get("https://practice.expandtesting.com/dynamic-pagination-table");
            P1TablePage p1Table = new P1TablePage(getDriver());
            
            while (true) {
                boolean advanced = p1Table.clickNextPageIfEnabled();
                if (!advanced) {
                    break; 
                }
                pageCounter++;
            }
            
            // Note: Adjust the expected page integer bound based on the live app's total dataset pages count!
            Assert.assertTrue(pageCounter >= 1, "[ASSERTION-FAIL] Pagination click tracking failed to advance pages!");
            System.out.println("✅ Test 2 Passed: Successfully traversed through pagination sheets.");
            
        } catch (Throwable e) {
            Assert.fail("Pagination lookup scenario execution dropped dead: " + e.getMessage());
        }
    }
    
    @Test(priority = 3, description = "Verify target name search existence.")
    public void isTargetNamePrestentInTable() {
        long threadId = Thread.currentThread().getId();
        System.out.println("[THREAD-" + threadId + "] >>> STARTING SCENARIO 2: PAGINATION LOOP ENGINE");
        
        try {
            getDriver().get("https://practice.expandtesting.com/dynamic-pagination-table");
            P1TablePage p1Table = new P1TablePage(getDriver());
            
            p1Table.chooseNoOfTableData("10");
            String dataDisplayedCount = p1Table.getTableDataDisplayedCount();
            
            // Note: Adjust the expected page integer bound based on the live app's total dataset pages count!
            Assert.assertEquals(dataDisplayedCount,"Showing 1 to 10 of 10 entries", "[ASSERTION-FAIL] Pagination dara record mis-Match!");
                        
            String targetSearchName = "Alice Johnson";
            Boolean nameExist = p1Table.isTargetRecordPresent(targetSearchName);
            Assert.assertTrue(nameExist,"Searched record not found!");
            
            
        } catch (Throwable e) {
            Assert.fail("Pagination lookup scenario execution dropped dead: " + e.getMessage());
        }
    }
    
    @Test(priority = 4, description = "Verify that the custom pagination looping loop sweeps completely across sheets safely.")
    public void verifyDynamicPaginationScannerEngine() {
        try {
            getDriver().get("https://practice.expandtesting.com/dynamic-pagination-table");
            P1TablePage p1Table = new P1TablePage(getDriver());
            
            // Let's look for a student that sits deep inside page 3 or 4 
            String deepTargetStudent = "Daniel Martinez"; 
            
            boolean isRecordFound = p1Table.searchAllPagesForStudent(deepTargetStudent);
            
            Assert.assertTrue(isRecordFound, "[ASSERTION-FAIL] Dynamic scanner failed to locate record: " + deepTargetStudent);
            System.out.println("✅ Scenario 6 Passed: Dynamic Pagination Scanner executed flawlessly.");
            
        } catch (Throwable e) {
            Assert.fail("Dynamic traversal sequence operation dropped dead: " + e.getMessage());
        }
    }
}
