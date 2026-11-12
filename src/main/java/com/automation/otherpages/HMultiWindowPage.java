package com.automation.otherpages;

import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HMultiWindowPage {

    private final WebDriver driver;
    private final HElementActions element;
    
    public HMultiWindowPage(WebDriver driver) {
        this.driver = driver;
        this.element = new HElementActions(driver);
    }
    
    private final By clickHereLink = By.cssSelector("div#content a");
    private final By newTabMessage = By.cssSelector("div.example h3");
    
    public String verifyNewWindow() {
        // Step 1: Capture the exact starting window identity anchor
        String parentWindowId = driver.getWindowHandle();
        
        // Trigger the action that spawns the new tab
        element.clickElement(clickHereLink);
        
        // Step 2: Explicitly wait for the operating system window array layer to scale up to 2
        element.getWait().until(ExpectedConditions.numberOfWindowsToBe(2));
        
        Set<String> allWindowHandles = driver.getWindowHandles();
        
        // Step 3: Shift focus onto the child window context safely
        for (String currentHandle : allWindowHandles) {
            if (!currentHandle.equals(parentWindowId)) {
                driver.switchTo().window(currentHandle);
                break;
            }
        }
        
        // 🛡️ Step 4: Wrap child actions inside try-finally to prevent locking up your driver suite!
        try {
            return element.waitForVisibility(newTabMessage).getText().trim();
        } finally {
            // No matter what happens above, cleanly close the child tab and restore root handle focus!
            driver.close(); 
            driver.switchTo().window(parentWindowId);
        }
    }
}
