package com.automation.tests;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.NavigationPage;
import com.automation.pages.WebTablePage;

public class WebTableTest extends BaseTest {

    @Test
    public void verifyDynamicWebTableSortingDataMatrix() {
        long threadId = Thread.currentThread().getId();
        System.out.println("[THREAD-" + threadId + "] >>> STARTING TARGET 4: WEBTABLE SORT ENGINE");

        try {
           // NavigationPage navigationPage = new NavigationPage(getDriver());
            //navigationPage.contentPageLink();
            
            // Navigate to the specific tables practice module page path
            getDriver().get("https://practice.expandtesting.com/tables");

            WebTablePage tablePage = new WebTablePage(getDriver());

            // 1. Capture the initial baseline unsorted data matrix array straight from the UI rows
            List<String> baselineMatrix = tablePage.getColumnDataTextMatrix();
            System.out.println("[THREAD-" + threadId + "] Baseline Data Matrix from UI: " + baselineMatrix);
            System.out.println("--------------PRINT LIST TEST BEGIN--------------" + tablePage.getColumnDataTextMatrix());

            // 2. Trigger the sorting action by clicking the target header column field
            System.out.println("[THREAD-" + threadId + "] Clicking FirstName column header to trigger sorting...");
            tablePage.clickSortByFirstName();

            // 3. Extract the new arrangement from the UI rows after sorting has completed
            List<String> sortedUiMatrix = tablePage.getColumnDataTextMatrix();
            System.out.println("[THREAD-" + threadId + "] Sorted Data Matrix from UI: " + sortedUiMatrix);

            // 4. Generate a target evaluation list using Java's built-in sorting logic on our baseline array
            List<String> targetSortedComparisonMatrix = baselineMatrix.stream()
                    .sorted()
                    .collect(Collectors.toList());
            System.out.println("--------------PRINT LIST TEST--------------" + baselineMatrix.stream()
            .sorted()
            .collect(Collectors.toList()));

            // 5. Data Integrity Validation Check
            Assert.assertEquals(sortedUiMatrix, targetSortedComparisonMatrix, 
                    "CRITICAL ERROR: The application's UI table sort action failed to arrange data correctly!");
            
            System.out.println("[THREAD-" + threadId + "] >>> SUCCESS: Table column data matrix verified perfectly!");

        } catch (Throwable e) {
            System.err.println("[THREAD-" + threadId + "] Critical WebTable execution exception caught!");
            e.printStackTrace();
            Assert.fail("Aborting dynamic table iteration due to framework runtime crash: " + e.getMessage());
        }
    }
}
