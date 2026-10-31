package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.ComplexActionsPage;
import com.automation.utils.ConfigReader;

public class ComplexActionsTest extends BaseTest {

    @Test(priority = 1, description = "Verify mouse interaction sequences for dragging Column A to Column B.")
    public void verifyDragAndDropInteraction() {
        getDriver().switchTo().defaultContent();
        getDriver().get(ConfigReader.getProperty("base.url") + "drag-and-drop");

        ComplexActionsPage actionsPage = new ComplexActionsPage(getDriver());
        
        // Capture base state text before action
        String beforeDragText = actionsPage.getColumnAText(); // Expects "A"
        
        // Execute real action chain
        actionsPage.dragBoxAToBoxB();
        
        // Capture post-action text to verify successful drop swap
        String afterDragText = actionsPage.getColumnAText();
        
        Assert.assertNotEquals(beforeDragText, afterDragText, "Issue in moving the element: Text did not change after drag-and-drop action!");
        
        System.out.println("✅ Target 3A Complete: Drag and Drop completed. New text in Column A: " + afterDragText);
    }
}
