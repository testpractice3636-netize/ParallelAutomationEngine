package com.automation.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SecureAlertsPage {
    private WebDriver driver;
    private ElementActions elementActions;

    // 🎯 Dashoard Element Locators
    private final By alertButton = By.id("js-alert");
    private final By confirmButton = By.id("js-confirm");
    private final By resultText = By.id("dialog-response");

    public SecureAlertsPage(WebDriver driver) {
        this.driver = driver;
        this.elementActions = new ElementActions(driver);
    }

    public void clickAlertButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(alertButton));
        		//elementToBeClickable(alertButton)).click();
            	elementActions.clickElement(alertButton);

    }

    public void clickConfirmButton() {
        //elementActions.clickElementByJs(confirmButton);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(confirmButton));
        		//elementToBeClickable(confirmButton)).click();
        elementActions.clickElement(confirmButton);
    }

   /* public String getAlertTextAndHandle(boolean clickOk) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // 🚀 GATE 1: Explicitly wait until the native dialog overlay is present in memory
        Alert alertInstance = wait.until(ExpectedConditions.alertIsPresent());
        
        // Extract content text asset
        String text = alertInstance.getText();
        
        // GATE 3: Unfreeze the browser context shell
        if (clickOk) {
            alertInstance.accept(); // Natively clicks "OK" / "Confirm"
        } else {
            alertInstance.dismiss(); // Natively clicks "Cancel" / "Close"
        }
        
        return text;
    } */
    
    public String getAlertTextAndHandle(boolean clickOk) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // 🚀 Step 1: Ensure the alert context wrapper is completely present in memory
        Alert alertInstance = wait.until(ExpectedConditions.alertIsPresent());
        String text = alertInstance.getText();
        
        // 🚀 Step 2: Clear out intercept deadlocks using a robust action retry loop
        int retries = 0;
        while (retries < 3) {
            try {
                if (clickOk) {
                    alertInstance.accept(); // Clicks OK
                } else {
                    alertInstance.dismiss(); // Clicks Cancel
                }
                break; // Action succeeded, break the retry loop
            } catch (Exception e) {
                retries++;
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            }
        }
        driver.switchTo().defaultContent();
        
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        return text;
    }

    public String getPageResultNotification() {
       
         
    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
         
         // 🚀 Step 3: Wait specifically for the conditional element text to stabilize in the DOM tree
         wait.until(ExpectedConditions.presenceOfElementLocated(resultText));
         return driver.findElement(resultText).getText().trim();
    	 
    	 /*------------------1---------------------*/
         // 🚀 COMBINED SYNCHRONIZATION: Wait until the item is written to DOM and visible
        /* WebElement resultElement = wait.until(ExpectedConditions.presenceOfElementLocated(resultText));
         wait.until(ExpectedConditions.visibilityOf(resultElement));
         
         return resultElement.getText().trim();*/
    	 
    	 /*  -----------------2----------------*/
    	// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //return wait.until(ExpectedConditions.visibilityOfElementLocated(resultText)).getText().trim();
        //presenceOfElementLocated(resultText)).getText().trim();
    }
}
