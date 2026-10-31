package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Alert;

public class AlertsPage {
    private final WebDriver driver;
    private final ElementActions element;

    // 🎯 Live-Synchronized UI Locators using explicit IDs
    private final By jsAlertButton = By.id("js-alert");
    private final By jsConfirmButton = By.id("js-confirm");
    private final By jsPromptButton = By.id("js-prompt");
    private final By resultText = By.id("dialog-response"); // 📍 Live site ID container for text outcomes

    public AlertsPage(WebDriver driver) {
        this.driver = driver;
        this.element = new ElementActions(driver);
    }

    // --- Button Triggers ---
    public void triggerJsAlert() { element.clickElement(jsAlertButton); }
    public void triggerJsConfirm() { element.clickElement(jsConfirmButton); }
    public void triggerJsPrompt() { element.clickElement(jsPromptButton); }

    // --- Native Alert Engine Operations ---
    private Alert switchToActiveAlert() {
        return element.getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());
    }

    public String getAlertMessage() { return switchToActiveAlert().getText(); }
    public void acceptAlert() { switchToActiveAlert().accept(); }
    public void dismissAlert() { switchToActiveAlert().dismiss(); }
    public void typeInPrompt(String text) { switchToActiveAlert().sendKeys(text); }

    // --- State Extractions ---
    public String getResultNotificationText() {
        return element.waitForVisibility(resultText).getText().trim();
    }
}
