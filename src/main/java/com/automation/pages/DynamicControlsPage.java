package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DynamicControlsPage {
    private final WebDriver driver;
    private final ElementActions element;

    // 🎯 Only 5 structural locators needed for the entire page module!
    private final By checkbox = By.xpath("//input[@type='checkbox']");
    private final By removeAddButton = By.xpath("//form[@id='checkbox-example']/button");
    private final By inputField = By.xpath("//form[@id='input-example']/input");
    private final By enableDisableButton = By.xpath("//form[@id='input-example']/button");
    private final By actionMessage = By.id("message"); // The single stable pointer for all dynamic messages

    public DynamicControlsPage(WebDriver driver) {
        this.driver = driver;
        this.element = new ElementActions(driver);
    }
    
    
    // --- Action Executions ---
    public void clickRemoveAddButton()
    { element.clickElement(removeAddButton); 
    }
    public void clickEnableDisableButton() 
    { element.clickElement(enableDisableButton);
    }
    public void enterTextInInputField(String text) 
    { element.enterText(inputField, text);
    }

    // --- State Extractions ---
    public String getRemoveAddButtonText() 
    { return element.waitForVisibility(removeAddButton).getText().trim();
    }
    public String getEnableDisableButtonText() 
    { return element.waitForVisibility(enableDisableButton).getText().trim();
    }

    public boolean isCheckboxPresent() {
        try { return driver.findElement(checkbox).isDisplayed(); } 
        catch (org.openqa.selenium.NoSuchElementException e) { return false; }
    }
    public boolean isInputFieldEnabled() 
    { return element.waitForVisibility(inputField).isEnabled();
    }

    /**
     * 💎 UNIFIED STATE ENGINE: Dynamically extracts whatever text is currently inside the message block.
     */
    public String getActionMessageText() {
        return element.waitForVisibility(actionMessage).getText().trim();
    }
}
