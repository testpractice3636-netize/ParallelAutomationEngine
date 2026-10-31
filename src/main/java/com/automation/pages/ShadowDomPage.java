package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ShadowDomPage {
    private final WebDriver driver;
    private final ElementActions element;

    // 1. Host element must be accessible from the standard Light DOM
    private final By shadowHostLocator = By.id("shadow-host");
    
    // 2. Inner target element MUST use CSS selector (No XPath allowed here!)
    private final By innerButtonSelector = By.cssSelector("#my-btn");

    public ShadowDomPage(WebDriver driver) {
        this.driver = driver;
        this.element = new ElementActions(driver);
    }

    /**
     * Pierces the open shadow root natively to click the hidden button.
     * Zero JavaScript used for the action phase!
     */
    public String getShadowButtonText() {
        System.out.println("[POM-SHADOW] Synchronizing on the parent Shadow Host...");
        // Wait for the host wrapper to exist in the main document tree
        WebElement shadowHost = element.getWait().until(
            ExpectedConditions.presenceOfElementLocated(shadowHostLocator)
        );

        System.out.println("[POM-SHADOW] Piercing boundary to extract SearchContext...");
        // Native Selenium 4 boundary crossing
        SearchContext shadowRoot = shadowHost.getShadowRoot();

        System.out.println("[POM-SHADOW] Querying inner element inside the isolated tree...");
        // Query scoped exclusively to the internal shadow root context
        WebElement innerButton = shadowRoot.findElement(innerButtonSelector);

        // Native Selenium actions work out of the box!
        String buttonText = innerButton.getText().trim();
        innerButton.click(); 
        
        return buttonText;
    }
}
