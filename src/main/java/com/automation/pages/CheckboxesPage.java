package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckboxesPage {
    private final WebDriver driver;
    private final ElementActions element;

    // 🎯 Decoupled Core Strategy: Use direct, stable IDs bypassing all form/div structures!
    private final By checkbox1 = By.id("checkbox1");
    private final By checkbox2 = By.id("checkbox2");

    public CheckboxesPage(WebDriver driver) {
        this.driver = driver;
        this.element = new ElementActions(driver);
    }

    /**
     * Helper engine to dynamically map an index (0 or 1) to its explicit structural locator.
     */
    private By getLocatorByIndex(int index) {
        if (index == 0) return checkbox1;
        if (index == 1) return checkbox2;
        throw new IllegalArgumentException("CRITICAL ERROR: Invalid checkbox index [" + index + "] requested! Only 0 and 1 are valid on this page.");
    }

    /**
     * Checks selection state safely via true explicit wait parameters.
     */
    public boolean isCheckboxSelected(int index) {
        By targetLocator = getLocatorByIndex(index);
        // Wait for presence/visibility first before polling selection state
        return element.waitForPresence(targetLocator).isSelected();
        
    }

    /**
     * Enforces the target state cleanly using your dual native/JS click engine.
     */
    public void setCheckboxState(int index, boolean shouldBeChecked) {
        By targetLocator = getLocatorByIndex(index);
        boolean currentSelectionState = isCheckboxSelected(index);

        if (currentSelectionState != shouldBeChecked) {
            element.clickElement(targetLocator); // Reuses your master click execution seamlessly
        }
    }

    /**
     * Hardcoded safeguard verification count matching page specs.
     */
    public int getCheckboxesCount() {
        // Quick layout assertion checking presence of both targets
        int count = 0;
        if (element.waitForPresence(checkbox1).isDisplayed()) count++;
        if (element.waitForPresence(checkbox2).isDisplayed()) count++;
        return count;
    }
}
