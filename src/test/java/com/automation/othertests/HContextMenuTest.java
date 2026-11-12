package com.automation.othertests;

import java.io.ObjectInputFilter.Config;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HContextMenuPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HContextMenuTest extends BaseTest{
    @Test(priority = 1, description = "Verify native right-click interaction triggers and clears JavaScript alert contexts")
    public void verifyRightClickAlertHandling() {
        // 1. Navigate to the absolute live context menu application route
    	 getDriver().get(ConfigReader.getProperty("base.url") + "context_menu");
    	 
        
        HContextMenuPage contextPage = new HContextMenuPage(getDriver());
        
        // 2. Execute the right-click handshake and capture the browser text popup strings
        String expectedAlertText = "You selected a context menu";
        String actualAlertText = contextPage.rightClickAndGetAlertText();
        
        System.out.println("------- Context Menu Alert Extracted Text: " + actualAlertText);
        
        // 3. Confirm the modal action was captured correctly before dismissal
        Assert.assertEquals(actualAlertText, expectedAlertText, "CRITICAL ERROR: Failed to interact with or read the native JS alert popup modal!");
    }
}
