package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SecureFramesPage {
    private final WebDriver driver;
    private final ElementActions elementActions;
    private final ElementActions element;


    // 🎯 Clear, descriptive, finalized locators
    private final By richTextIframeLocator = By.id("mce_0_ifr");
   private final By iframeInnerBodyText   = By.id("tinymce");
   // private final By iframeInnerBodyText   = By.xpath("//body[@id='tinymce']/p");

   //embedded iframe
   private final By embeddedFrameLocator = By.xpath("//iframe[contains(@src, 'email-subscribe')]");
		   
   // 🎯 Targets hidden deep inside the embedded iframe universe
   private final By emailInputField = By.id("email");
   private final By subscribeButton = By.id("btn-subscribe");
   private final By successMessage = By.id("success-message");
   
   
    public SecureFramesPage(WebDriver driver) {
        this.driver = driver;
        this.elementActions = new ElementActions(driver);
        this.element = new ElementActions(driver);

    }

    /**
     * Extracts text from inside the iframe editor area.
     * Dynamic parameter allows this page object method to be reused for ANY validation text string.
     */
    public String getEditorContentText(String expectedTextToWaitFor) {
        System.out.println("[POM-FRAMES] Shifting execution context into iframe structure...");
        
        driver.switchTo().defaultContent();
        
        // 🚀 ATOMIC SYNCHRONIZATION: Uses your framework's global parameter-free getWait()!
        WebDriverWait wait = elementActions.getWait(); 
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(richTextIframeLocator));

        // Wait dynamically for the target text to be fully loaded inside the HTML layout
        wait.until(ExpectedConditions.textToBePresentInElementLocated(iframeInnerBodyText, expectedTextToWaitFor));
        
        WebElement textNode = elementActions.waitForVisibility(iframeInnerBodyText);
        String frameText = textNode.getText().trim();

        System.out.println("[POM-FRAMES] Text extracted successfully.");
        
        // Always reset focus back to main page context before exit
        driver.switchTo().defaultContent();
        return frameText;
    }

    /**
     * 📝 REAL WORK EXTENSION: Interacts dynamically with the Rich Text Editor canvas.
     */
    public void writeTextIntoEditor(String newText) {
        driver.switchTo().defaultContent();
        
        // Uses your framework's global parameter-free getWait()!
        WebDriverWait wait = elementActions.getWait();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(richTextIframeLocator));

        WebElement editorBody = elementActions.waitForVisibility(iframeInnerBodyText);
        try
        {
        	
            editorBody.click();

        editorBody.clear(); // Wipe out default content
        editorBody.sendKeys(newText); // Write production payload
        }catch (Exception e) {
            System.out.println("⚠️ [RICH-TEXT-GUARD] Native typing failed or lost focus. Triggering JS injector fallback...");
            
            // Step 3: Ultimate Bypass - Inject text directly into the TinyMCE body DOM node
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            js.executeScript("arguments[0].innerHTML = arguments[1];", editorBody, newText);
        }
        driver.switchTo().defaultContent();
    }
    
    public String subscribeToNewsletterInsideFrame(String testEmail)
    {
       // WebElement frameElement = element.waitForPresence(embeddedFrameLocator);
        //driver.switchTo().frame(frameElement);
        driver.switchTo().defaultContent();

        element.getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(embeddedFrameLocator));
                   
        
        element.waitForPresence(emailInputField);
        driver.findElement(emailInputField).clear();
        element.enterText(emailInputField, testEmail);
        
        element.clickElement(subscribeButton);

        //for assert 
        By SucCessMessage = By.xpath("//div[@id='success-message']");
        String sucessText = element.waitForVisibility(SucCessMessage).getText().trim();
        
        driver.switchTo().defaultContent();
        return sucessText;
 
    }
    
}
