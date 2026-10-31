package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.NavigationPage;
import com.automation.pages.WebTablePage;

public class WebTableActionsTest extends BaseTest {

    // ✅ METHOD 1: Validates the row deletion URL mapping behavior
    @Test
    public void verifyDynamicRowDeleteActionUrlMapping() {
        long threadId = Thread.currentThread().getId();
        System.out.println("[THREAD-" + threadId + "] >>> STARTING TEST: DELETE ACTION URL CHECK");

        try {
            //NavigationPage navigationPage = new NavigationPage(getDriver());
            //navigationPage.contentPageLink();
            getDriver().get("https://practice.expandtesting.com/tables");

            WebTablePage tablePage = new WebTablePage(getDriver());
            
            // Trigger the delete button click using our email anchor
            tablePage.deleteUserByEmailRowAnchor("jsmith@gmail.com");

            // 🎯 URL FRAGMENT ASSERTION: Verify the application updated the URL path correctly
            String finalUrl = getDriver().getCurrentUrl();
            System.out.println("[THREAD-" + threadId + "] Post-Click Application URL: " + finalUrl);
            
            Assert.assertTrue(finalUrl.contains("#delete"), 
                    "[ASSERTION-FAIL] The URL string did not append the target '#delete' fragment path suffix!");

        } catch (Throwable e) {
            Assert.fail("Delete verification scenario execution dropped dead: " + e.getMessage());
        }
    }

    // ✅ METHOD 2: Validates the row edit URL mapping behavior
    @Test
    public void verifyDynamicRowEditActionUrlMapping() {
        long threadId = Thread.currentThread().getId();
        System.out.println("[THREAD-" + threadId + "] >>> STARTING TEST: EDIT ACTION URL CHECK");

        try {
            //NavigationPage navigationPage = new NavigationPage(getDriver());
            //navigationPage.contentPageLink();
        	getDriver().get("https://practice.expandtesting.com/tables");

        	WebTablePage tablePage = new WebTablePage(getDriver());
            
            // Trigger the delete button click using our email anchor
            
        	tablePage.editUserByEmailRowAnchor("jsmith@gmail.com");
            
        	// Direct relative XPath lookup to target the Edit link node for the user row
            
            // 🎯 URL FRAGMENT ASSERTION: Verify the application updated the URL path correctly
            String finalUrl = getDriver().getCurrentUrl();
            System.out.println("[THREAD-" + threadId + "] Post-Click Application URL: " + finalUrl);
            
            Assert.assertTrue(finalUrl.contains("#edit"), 
                    "[ASSERTION-FAIL] The URL string did not append the target '#edit' fragment path suffix!");

        } catch (Throwable e) {
            Assert.fail("Edit verification scenario execution dropped dead: " + e.getMessage());
        }
    }
}
