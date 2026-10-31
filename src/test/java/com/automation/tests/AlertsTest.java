package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.SecureAlertsPage;

public class AlertsTest extends BaseTest {

    @Test(description = "Verify parsing pipeline for native browser alerts and confirms")
    public void verifyNativeAlertsPipeline() {
        System.out.println("------ ENTRY 14: Native Alerts Test Pipeline Active ------");
        
        // Ensure browser thread window clean slate focus alignment
        getDriver().switchTo().defaultContent();
        getDriver().get("https://practice.expandtesting.com/js-dialogs");
        
        SecureAlertsPage alertsPage = new SecureAlertsPage(getDriver());
        
        // ==========================================
        // 🧪 VALIDATION BLOCK 1: Simple Info Alert
        // ==========================================
        System.out.println("[ALERTS-INFO] Step 1: Triggering standard alert...");
        alertsPage.clickAlertButton();
        
        String actualAlertText = alertsPage.getAlertTextAndHandle(true);
        
        // 🎯 ASSERTION CHECKPOINT GATE 1
        System.out.println("ASSERTION 1 PASSED");
        Assert.assertEquals(actualAlertText, "I am a Js Alert", "[ASSERTION-FAIL] Simple alert textual assets mismatch!");
        Assert.assertEquals(alertsPage.getPageResultNotification(), "OK", "[ASSERTION-FAIL] Dynamic response text mismatch!");
        System.out.println("ASSERTION 2 PASSED");

        System.out.println("[ALERTS-SUCCESS] Information alert validation passed.");

        // ==========================================
        // 🧪 VALIDATION BLOCK 2: Confirmation Alert (Dismiss path)
        // ==========================================
        System.out.println("[ALERTS-INFO] Step 2: Triggering confirmation dialog...");
        alertsPage.clickConfirmButton();
        System.out.println("???IS PAGE LIVE?????");

        String actualConfirmText = alertsPage.getAlertTextAndHandle(false);
        
        // 🎯 ASSERTION CHECKPOINT GATE 2

        Assert.assertEquals(actualConfirmText, "I am a Js Confirm", "[ASSERTION-FAIL] Confirm dialog textual assets mismatch!");
        System.out.println("ASSERTION 3 PASSED");

        Assert.assertEquals(alertsPage.getPageResultNotification(), "Cancel");
        System.out.println("ASSERTION 4 PASSED");

        System.out.println("[ALERTS-SUCCESS] Confirmation dialogue dismiss validation passed.");
        
        System.out.println("------ ENTRY 15: Alerts Execution Completed Successfully ------");
    }
}
