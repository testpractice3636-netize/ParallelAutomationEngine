package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.SecureFramesPage;
import com.automation.utils.ConfigReader;

public class FramesTest extends BaseTest {

    @Test(priority = 1, description = "Verify sequential internal frame context traversal pipelines and text modification.")
    public void verifyIframeEditorLifecycle() {
        System.out.println("------ START: Frames Lifecycle Test Initiated ------");
        
        // 1. Core clean environment initialization
        getDriver().switchTo().defaultContent();
        String targetUrl = ConfigReader.getProperty("base.url") + "iframe";
        getDriver().get(targetUrl);
        
        SecureFramesPage framesPage = new SecureFramesPage(getDriver());

        // 2. Validate Default Base State Content
        String baselineText = "Your content goes here.";
        String actualDefaultText = framesPage.getEditorContentText(baselineText);
        Assert.assertEquals(actualDefaultText, baselineText, "[ASSERTION-FAIL] Default IFrame baseline text mismatch!");
        
        // 3. Perform Modification Action (Simulating real-world project tasks)
        String replacementPayload = "Automated Framework Text Update Validation Success!";
        framesPage.writeTextIntoEditor(replacementPayload);
        
        // 4. Verify Content was Written Correctly
        String actualUpdatedText = framesPage.getEditorContentText(replacementPayload);
        Assert.assertEquals(actualUpdatedText, replacementPayload, "[ASSERTION-FAIL] Modified editor content missing from iframe DOM context!");
        
        System.out.println("🎉 SUCCESS: IFrame read-write pipeline passed validation execution.");
    }
    
    @Test(priority = 2, description = "Verify boundary crossing context switches inside embedded IFrame elements.")
    public void verifyIFrameInteractionPipeline() {
        long threadId = Thread.currentThread().getId();
        System.out.println("[THREAD-" + threadId + "] >>> STARTING SCENARIO 7: IFRAME SWITCH ENGINE");
        try
        {
        	
        
        getDriver().switchTo().defaultContent();
        String targetUrl = ConfigReader.getProperty("base.url") + "iframe";
        getDriver().get(targetUrl);
        
        SecureFramesPage framesPage = new SecureFramesPage(getDriver());
     
        String actualSuccessMsg = framesPage.subscribeToNewsletterInsideFrame("automation_learner@test.com");
        String expectedSuccessMsg = "You are now subscribed!";
        
        // ✅ Run the verification engine check
        Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, 
            "[ASSERTION-FAIL] Newsletter registration text mismatch inside the iframe canvas!");
        
        System.out.println("✅ Scenario 7 Passed: Resilient single-method IFrame workflow verified.");
        
    } catch (Throwable e) {
        Assert.fail("IFrame context switching pipeline dropped dead: " + e.getMessage());
    }        
    }
}
