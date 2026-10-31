package com.automation.othertests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.otherpages.HNestedFramesPage;
import com.automation.tests.BaseTest;

public class HNestedFramesTest extends BaseTest {

    @Test(priority = 4, description = "Verify structural navigation pipelines across legacy nested frames sets.")
    public void verifyNestedFramesContextTraversal() {
        System.out.println("------ START: Legacy Nested Frames Pipeline Initiated ------");
        
        // 1. Direct browser routing to the frame environment sandbox
        getDriver().switchTo().defaultContent();
        getDriver().get("https://the-internet.herokuapp.com/nested_frames");
        
        HNestedFramesPage framesPage = new HNestedFramesPage(getDriver());

        // 2. Traversal Loop 1: Extract and validate from Parent-Top -> Child-Middle
        String actualMiddleText = framesPage.getMiddleFrameText();
        Assert.assertEquals(actualMiddleText, "MIDDLE", 
            "[ASSERTION-FAIL] Content verification inside middle frame context failed!");
        System.out.println("✅ Middle Frame Content Confirmed: " + actualMiddleText);

        // 3. Traversal Loop 2: Extract and validate from Bottom Frame
        String actualBottomText = framesPage.getBottomFrameText();
        Assert.assertEquals(actualBottomText, "BOTTOM", 
            "[ASSERTION-FAIL] Content verification inside bottom frame context failed!");
        System.out.println("✅ Bottom Frame Content Confirmed: " + actualBottomText);
        
        System.out.println("🎉 SUCCESS: Cross-frame structural validation test passed without errors.");
    }
    
    @Test(priority = 4, description = "Verify relative traversal accuracy across sibling top frames layouts.")
    public void verifySiblingFramesContextTraversalPipeline() {
        System.out.println("------ START: Sibling Frames Verification Initiated ------");
        
        // 1. Initialize environment context
        getDriver().switchTo().defaultContent();
        getDriver().get("https://the-internet.herokuapp.com/nested_frames");
        
        HNestedFramesPage framesPage = new HNestedFramesPage(getDriver());

        // 2. Fetch the entire collection matrix from the page object layer
        String[] collectedFrameTexts = framesPage.getAllTopSiblingFramesData();
        for(String collectedFrameText : collectedFrameTexts)
        {
        System.out.println("---------ALL FRAME TEXT  :  "  +collectedFrameText + "----------");
        }
        // 3. 🎯 ASSERTION CHECKPOINT GATEWAY: Verify each sibling independently!
        Assert.assertEquals(collectedFrameTexts[0], "LEFT", 
            "[ASSERTION-FAIL] Sibling context alignment failed inside the LEFT frame container!");
            
        Assert.assertEquals(collectedFrameTexts[1], "MIDDLE", 
            "[ASSERTION-FAIL] Sibling context alignment failed inside the MIDDLE frame container!");
            
        Assert.assertEquals(collectedFrameTexts[2], "RIGHT", 
            "[ASSERTION-FAIL] Sibling context alignment failed inside the RIGHT frame container!");
        
        System.out.println("🎉 SUCCESS: All 3 sibling top frame contexts matched validation expectations!");
    }
}
