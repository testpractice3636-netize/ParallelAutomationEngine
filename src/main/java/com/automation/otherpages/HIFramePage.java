package com.automation.otherpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HIFramePage {

    private final WebDriver driver;
    private final HElementActions element;
    
    public HIFramePage(WebDriver driver) {
        this.driver = driver;
        this.element = new HElementActions(driver);
    }
    
    // Locators matching the live "WYSIWYG Editor" iFrame page layout
    private final By iframeContext = By.id("mce_0_ifr");
    private final By iframeInput = By.id("tinymce");
    private final By mainHeaderText = By.cssSelector("div#content h3");
    
    public String verifyInputIFrame(String textToType) {
        // 🚀 Step 1: Safely wait for the frame to exist and seamlessly slide the focus inside
        element.getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeContext));
        
        try {
            // Locate the input node inside the frame context boundaries
            WebElement inputField = element.getWait().until(ExpectedConditions.visibilityOfElementLocated(iframeInput));
            
            // 🛡️ Clear the default pre-populated text layout out of the box first!
            inputField.clear(); 
            inputField.sendKeys(textToType);
            
        } finally {
            // 🚀 Step 2: ALWAYS force a jump back out to the master DOM layer no matter what happens
            driver.switchTo().defaultContent();
        }
        
        // 🚀 Step 3: Fetch the header text on the main page to prove context switched back successfully
        return element.waitForVisibility(mainHeaderText).getText().trim();
    }
}
