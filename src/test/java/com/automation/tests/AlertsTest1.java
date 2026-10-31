package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.AlertsPage;
import com.automation.utils.ConfigReader;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class AlertsTest1 extends BaseTest {

    private void navigateToAlertsModule() {
        getDriver().switchTo().defaultContent();
        // 🎯 Pointing to the real active page endpoint
        String targetUrl = ConfigReader.getProperty("base.url") + "js-dialogs"; 
        getDriver().get(targetUrl);
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
            .until(ExpectedConditions.urlContains("/js-dialogs"));
    }

    @Test(priority = 1, description = "Verify information alert can be accepted")
    public void verifyStandardJsAlertLifecycle() {
        navigateToAlertsModule();
        AlertsPage alertsPage = new AlertsPage(getDriver());

        alertsPage.triggerJsAlert();
        Assert.assertEquals(alertsPage.getAlertMessage(), "I am a Js Alert", "[ASSERTION-FAIL] Alert text mismatch!");
        
        alertsPage.acceptAlert();
        Assert.assertEquals(alertsPage.getResultNotificationText(), "OK", "[ASSERTION-FAIL] Expected result response to switch to 'OK'!");
    }

    @Test(priority = 2, description = "Verify confirmation choice alert modal can be successfully dismissed")
    public void verifyJsConfirmationDismissalLifecycle() {
        navigateToAlertsModule();
        AlertsPage alertsPage = new AlertsPage(getDriver());

        alertsPage.triggerJsConfirm();
        alertsPage.dismissAlert(); // Simulate clicking 'Cancel'
        Assert.assertEquals(alertsPage.getResultNotificationText(), "Cancel", "[ASSERTION-FAIL] Result text failed to capture Cancel selection!");
    }

    // 💡 YOUR COMPLETED INDEPENDENT CHALLENGE TEST METHOD
    @Test(priority = 3, description = "Verify confirmation choice alert modal can be successfully accepted")
    public void verifyJsConfirmationAcceptance() {
        navigateToAlertsModule();
        AlertsPage alertsPage = new AlertsPage(getDriver());

        alertsPage.triggerJsConfirm();
        alertsPage.acceptAlert(); // Simulate clicking 'OK'
        Assert.assertEquals(alertsPage.getResultNotificationText(), "Ok", "[ASSERTION-FAIL] Result text failed to capture OK selection!");
    }

    @Test(priority = 4, description = "Verify user text input can be successfully injected inside prompt popups")
    public void verifyJsPromptInputLifecycle() {
        navigateToAlertsModule();
        AlertsPage alertsPage = new AlertsPage(getDriver());
        String secretInputMessage = "High-Yield Automation Validation";

        alertsPage.triggerJsPrompt();
        alertsPage.typeInPrompt(secretInputMessage);
        alertsPage.acceptAlert();
        
        // The live UI prints out: "You entered: <text>"
        Assert.assertEquals(alertsPage.getResultNotificationText(), secretInputMessage, 
            "[ASSERTION-FAIL] Prompt response text output mismatched!");
    }
}
