package com.automation.otherpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NestedFramesPage {
    private final WebDriver driver;

    // 🎯 FRAME NAME STRATEGIES (Standard frames frequently use string names/IDs)
    private final String parentTopFrame   = "frame-top";
    private final String childMiddleFrame = "frame-middle";
    private final String parentBottomFrame = "frame-bottom";
    
    // 🔍 Inner text locators inside the frame document bodies
    private final By frameBodyLocator = By.tagName("body");

    public NestedFramesPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigates down two levels to read text from the Middle Frame inside the Top Frameset.
     */
    public String getMiddleFrameText() {
        System.out.println("[POM-FRAMES] Traversing down to parent-top -> child-middle frame context...");
        
        // Always orient layout path safely from root canvas
        driver.switchTo().defaultContent();
        
        // 1. Step into the upper parent frameset container
        driver.switchTo().frame(parentTopFrame);
        
        // 2. Step straight down into the middle child window frame
        driver.switchTo().frame(childMiddleFrame);
        
        String text = driver.findElement(frameBodyLocator).getText().trim();
        
        // Clean out context focus vector back to root canvas before exit
        driver.switchTo().defaultContent();
        return text;
    }

    /**
     * Navigates directly into the Bottom Frame which sits outside the Top Frameset.
     */
    public String getBottomFrameText() {
        System.out.println("[POM-FRAMES] Traversing cleanly to bottom frame context...");
        
        driver.switchTo().defaultContent();
        
        // Step directly into the separate bottom frame container node
        driver.switchTo().frame(parentBottomFrame);
        
        String text = driver.findElement(frameBodyLocator).getText().trim();
        
        driver.switchTo().defaultContent();
        return text;
    }
    
    public String[] getAllTopSiblingFramesData() {
        String[] frameDataCollection = new String[3];

        // 1. Establish initial entry vector into the parent frameset
        driver.switchTo().defaultContent();
        driver.switchTo().frame("frame-top");
        
        // 2. Extract from Sibling 1: LEFT
        driver.switchTo().frame("frame-left");
        frameDataCollection[0] = driver.findElement(frameBodyLocator).getText().trim();
        
        // 3. Move context seamlessly to Sibling 2: MIDDLE using parentFrame()
        driver.switchTo().parentFrame(); // Back into 'frame-top'
        driver.switchTo().frame("frame-middle");
        frameDataCollection[1] = driver.findElement(frameBodyLocator).getText().trim();
        
        // 4. Move context seamlessly to Sibling 3: RIGHT using parentFrame()
        driver.switchTo().parentFrame(); // Back into 'frame-top'
        driver.switchTo().frame("frame-right");
        frameDataCollection[2] = driver.findElement(frameBodyLocator).getText().trim();
        
        // 5. Always clean up driver state before returning data matrix
        driver.switchTo().defaultContent();
        
        return frameDataCollection;
    }
    /* understanding
    public void extractAllTopSiblingFramesText() {
        // 1. Start from Main Root Canvas
        driver.switchTo().defaultContent();
        driver.switchTo().frame("frame-top");
        
        // 2. Step into Middle
        driver.switchTo().frame("frame-middle");
        String middleText = driver.findElement(frameBodyLocator).getText();
        System.out.println("Middle Frame: " + middleText);
        
        // 3. 🚀 STEP BACK ONE LEVEL (Back into 'frame-top')
        driver.switchTo().parentFrame(); 
        
        // 4. Step directly into Sibling Left
        driver.switchTo().frame("frame-left");
        String leftText = driver.findElement(frameBodyLocator).getText();
        System.out.println("Left Frame: " + leftText);
        
        // 5. 🚀 STEP BACK ONE LEVEL AGAIN (Back into 'frame-top')
        driver.switchTo().parentFrame();
        
        // 6. Step directly into Sibling Right
        driver.switchTo().frame("frame-right");
        String rightText = driver.findElement(frameBodyLocator).getText();
        System.out.println("Right Frame: " + rightText);
        
        // 7. Clean up context fully before exiting the pipeline
        driver.switchTo().defaultContent();
    }*/
}
