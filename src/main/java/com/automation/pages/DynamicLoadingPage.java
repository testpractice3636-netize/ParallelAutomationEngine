package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DynamicLoadingPage {

    private final ElementActions element;

    // 📋 LOCATORS MATRIX (Pure 'By' Strategies)
    private final By linkExample1 = By.xpath("//a[contains(@href, 'loading/1')]");
    private final By startButton = By.xpath("//div[@id='start']/button");
    private final By loadingBar = By.id("loading");
    private final By finishText = By.xpath("//div[@id='finish']/h4");

    public DynamicLoadingPage(WebDriver driver) {
        this.element = new ElementActions(driver);
    }

    /**
     * Navigates into the first dynamic rendering context
     */
    public void clickExample1Link() {
        element.click(linkExample1, 10);
    }

    /**
     * Fired to trigger the rendering of the dynamic progress tracking bar
     */
    public void clickStartButton() {
        element.click(startButton, 10);
    }

    /**
     * 🏁 THE TRANSITION CONTROL FLOW:
     * Asserts that the loading spinner appears, completely finishes execution, 
     * vanishes entirely, and extracts the target dynamically rendered content.
     */
    public String getDynamicLoadedMessage(int maxWaitTime) {
        // 1. Guard against flaky overlays: Wait until the progress bar goes completely dark/invisible
        boolean hasSpinnerVanished = element.waitForElementToDisappearFluent(loadingBar, maxWaitTime);
        
        if (!hasSpinnerVanished) {
            throw new RuntimeException("[FRAMEWORK-ERROR] The application UI remained locked under the loading state!");
        }

        // 2. Fetch the text securely once the DOM structure transitions smoothly
        return element.getText(finishText, 5);
    }
}
