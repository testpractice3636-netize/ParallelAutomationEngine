package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.JavaScriptAlertsPage;

public class JavaScriptAlertsTest extends BaseTest {

    @Test(priority = 5, description = "Validate interaction flows across ExpandTesting JavaScript Native Dialogs.")
    public void verifyJavaScriptAlertsEnginePipelines() {
        System.out.println("------ START: JavaScript Alerts Engine Test Suite Initiated ------");
        
        // 1. Direct browser routing to the correct alerts sandbox environment
        getDriver().get("https://practice.expandtesting.com/js-dialogs");
        
        JavaScriptAlertsPage alertsPage = new JavaScriptAlertsPage(getDriver());

        // 🛑 SCENARIO A: Simple Alert Box Execution Flow
        String simpleAlertText = alertsPage.handleSimpleAlertPipeline();
        Assert.assertEquals(simpleAlertText, "I am a Js Alert", "[FAIL] Simple alert text mismatch!");
        System.out.println("✅ Scenario A (Simple Alert) Checked Successfully.");

        // 🛑 SCENARIO B: Confirmation Box Flow (Dismiss Path)
        String confirmAlertText = alertsPage.handleConfirmAlertDismissPipeline();
        Assert.assertEquals(confirmAlertText, "I am a Js Confirm", "[FAIL] Confirm alert text mismatch!");
        Assert.assertEquals(alertsPage.getResultText(), "Cancel", "[FAIL] UI text did not show Cancel status!");
        System.out.println("✅ Scenario B (Confirmation Cancel) Checked Successfully.");

        // 🛑 SCENARIO C: Prompt Input Injection Flow
        String executionPayload = "Automation Mastery";
        alertsPage.handlePromptAlertPipeline(executionPayload);
        Assert.assertEquals(alertsPage.getResultText(), executionPayload, "[FAIL] Typed text payload mismatch!");
        System.out.println("✅ Scenario C (Prompt Input Injection) Checked Successfully.");
        
        System.out.println("🎉 SUCCESS: Entire JavaScript Alerts workflow validated cleanly!");
    }
}
