package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.SecureHoverPage;

public class HoverTest extends BaseTest {

    @Test(description = "Verify parsing pipeline for multi-profile hover gesture actions chains")
    public void verifyMultiProfileHoverGesturesPipeline() {
        System.out.println("------ ENTRY 16: Mouse Hover Gestures Loop Active ------");
        
        SecureHoverPage hoverPage = new SecureHoverPage(getDriver());
        getDriver().get("https://practice.expandtesting.com/hovers");
        // 🧪 Loop from profile index 1 to 3 to validate all profile cards sequentially
        for (int i = 1; i <= 3; i++) {
            getDriver().switchTo().defaultContent();
           
            
            // 1. Execute dynamic parameterized hover gesture
            hoverPage.hoverOverProfile(i);
            
            // 2. Validate the dynamic text overlay mapping matching the index loop
            String expectedCaption = "name: user" + i;
            String actualCaptionText = hoverPage.getProfileCaptionText(i);
            
            Assert.assertEquals(actualCaptionText, expectedCaption, "[ASSERTION-FAIL] Profile text asset mismatch at index: " + i);
            System.out.println("ASSERTION HOVER PASSED FOR USER " + i);
            
            // 3. Click the indexed dynamic redirection link path
            hoverPage.clickProfileLink(i);
            
            // 4. Validate landing dashboard profile state
            String actualHeaderText = hoverPage.getProfilePageHeaderText();
            Assert.assertTrue(actualHeaderText.contains("User Profile"), "[ASSERTION-FAIL] Landing page header mismatch for user: " + i);
            System.out.println("ASSERTION REDIRECTION PASSED FOR USER " + i);
        }
        
        System.out.println("------ ENTRY 17: Multi-Hover Loop Completed Successfully ------");
    }
}
