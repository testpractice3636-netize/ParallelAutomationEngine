package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class JavaScriptAlertsPage {
    private final ElementActions actions;

    // 🎯 Element Locators
    private final By alertTriggerBtn   = By.id("js-alert");
    private final By confirmTriggerBtn = By.id("js-confirm");
    private final By promptTriggerBtn  = By.id("js-prompt");
    private final By resultTextLocator = By.id("dialog-response");

    public JavaScriptAlertsPage(WebDriver driver) {
        this.actions = new ElementActions(driver);
    }

    // 🛑 SCENARIO A FLOW: Click, grab text, and accept simple alert
    public String handleSimpleAlertPipeline() {
        actions.clickElement(alertTriggerBtn);
        String alertText = actions.getAlertText();
        actions.acceptAlert();
        return alertText;
    }

    // 🛑 SCENARIO B FLOW: Click, grab text, and dismiss confirmation alert
    public String handleConfirmAlertDismissPipeline() {
        actions.clickElement(confirmTriggerBtn);
        String alertText = actions.getAlertText();
        actions.dismissAlert();
        return alertText;
    }

    // 🛑 SCENARIO C FLOW: Click, type payload text, and accept prompt alert
    public void handlePromptAlertPipeline(String textToSend) {
        actions.clickElement(promptTriggerBtn);
        actions.typeIntoAlert(textToSend);
        actions.acceptAlert();
    }

    // Read the resulting text feedback from the page
    public String getResultText() {
        return actions.getElementText(resultTextLocator);
    }
}
