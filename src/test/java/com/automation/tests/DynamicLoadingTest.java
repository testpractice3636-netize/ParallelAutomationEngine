package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.DynamicLoadingPage;
import com.automation.utils.ConfigReader;

public class DynamicLoadingTest extends BaseTest {

    @Test(priority = 2, description = "Verify that the FluentWait engine successfully catches dynamic asynchronous page loads.")
    public void verifyDynamicLoadingProgressBarPipeline() {
        
        // 1. Appends securely since your config base.url already ends with a slash '/'
        String appUrl = ConfigReader.getProperty("base.url") + "dynamic-loading";
        getDriver().get(appUrl);
        
        DynamicLoadingPage loadingPage = new DynamicLoadingPage(getDriver());
        
        // 2. Click through the asynchronous workflow steps
        loadingPage.clickExample1Link();
        loadingPage.clickStartButton();
        
        // 3. Extract your newly added timeout config property dynamically
        int maxAllowedWaitDuration = Integer.parseInt(ConfigReader.getProperty("target.timeout"));
        String actualSuccessMessage = loadingPage.getDynamicLoadedMessage(maxAllowedWaitDuration);
        
        // 4. Run the validation checks
        String expectedMessage = "Hello World!";
        Assert.assertEquals(actualSuccessMessage, expectedMessage, 
            "[ASSERTION-FAIL] The dynamic message rendered on the page does not match the expected validation baseline!");
        
        System.out.println("🎉 SUCCESS: Synchronization loop passed! Extracted Text: " + actualSuccessMessage);
    }
}
