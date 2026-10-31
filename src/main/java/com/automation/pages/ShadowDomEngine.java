package com.automation.architecture.engine;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ShadowDomEngine {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // 1. The Shadow Host Locator: Must reside in the standard Light DOM
    private final By shadowHostLocator = By.cssSelector("my-custom-element");
    
    // 2. The Internal Target Selector: Must use CSS Selectors (XPath will fail here)
    private final By innerInputSelector = By.cssSelector("input#secret-input");

    public ShadowDomEngine(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Strategy A: Native Selenium 4 API Piercing
     * Uses SearchContext to shift the driver's lookup focus past the boundary wall.
     */
    public void enterTextNativeShadowDOM(String payload) {
        System.out.println("[SHADOW-ENGINE] Locating the structural Shadow Host element...");
        
        // Step 1: Secure the host element in the Light DOM
        WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(shadowHostLocator));

        System.out.println("[SHADOW-ENGINE] Extracting the SearchContext boundary...");
        // Step 2: Use Selenium 4 native API to grab the shadow root context
        SearchContext shadowRoot = shadowHost.getShadowRoot();

        System.out.println("[SHADOW-ENGINE] Locating inner target element via decoupled search context...");
        // Step 3: Find the element relative to the shadow root node
        WebElement innerInput = shadowRoot.findElement(innerInputSelector);
        
        innerInput.clear();
        innerInput.sendKeys(payload);
    }

    /**
     * Strategy B: Asynchronous JavaScript Injection Fallback
     * Bypasses the WebDriver binding pipeline entirely by pulling the element via the browser execution loop.
     */
    public void enterTextJavaScriptShadowDOM(String payload) {
        System.out.println("[SHADOW-ENGINE-JS] Executing Javascript DOM traversal query...");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Uses the browser native .shadowRoot object pool to locate and interact with the hidden input field directly
        String script = "return document.querySelector('my-custom-element').shadowRoot.querySelector('input#secret-input');";
        
        WebElement innerInput = (WebElement) js.executeScript(script);
        
        if (innerInput != null) {
            innerInput.clear();
            innerInput.sendKeys(payload);
        } else {
            throw new RuntimeException("[SHADOW-DOM-FAILURE] Unable to locate target inside shadow tree via JS compilation.");
        }
    }
}
