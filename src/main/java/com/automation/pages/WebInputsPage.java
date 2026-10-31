package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WebInputsPage {

    private final WebDriver driver;
    private final ElementActions element;

    // 🎯 Target Elements for the Web Inputs Scenario
    private final By numberField   = By.id("input-number");
    private final By textField     = By.id("input-text");
    private final By passwordField = By.id("input-password");
    private final By dateField     = By.id("input-date");
    private final By submitButton   = By.id("btn-display-inputs"); // fallback target if page has save buttons

    
    private final By outputNumber   = By.id("output-number");
    private final By outputText     = By.id("output-text");
    private final By outputPassword = By.id("output-password");
    private final By outputDate     = By.id("output-date");
    
    
    public WebInputsPage(WebDriver driver) {
        this.driver = driver;
        this.element = new ElementActions(driver);
    }
    
 // ======================================================================
    // 🧱 STEP 1: GRANULAR BASE METHODS (Always write these first!)
    // ======================================================================
    public void enterNumber(String number) {
        element.enterText(numberField, number);
    }

    public void enterTextValue(String text) {
        element.enterText(textField, text);
    }

    public void enterPassword(String password) {
        element.enterText(passwordField, password);
    }

    public void enterDate(String date) {
        element.enterText(dateField, date);
    }

    public void clickDisplayInputs() {
        element.clickElement(submitButton);
    }

    public void fillInputForm(String number, String text, String password, String date) {
        // Safe explicit anchor synchronization on the first field
        element.waitForVisibility(numberField);
        
        // Clean, readable reuse chain:
        this.enterNumber(number);
        this.enterTextValue(text);
        this.enterPassword(password);
        this.enterDate(date);
        this.clickDisplayInputs();
    }
   
    public String getNumberFieldValue() {
        return element.getFieldValue(numberField);
    }
   

    // 🔍 Validation Logic: Read back text directly from the UI attributes to verify values
    public String getEnteredNumberValue() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(outputNumber)).getText().trim();
    }

    public String getDisplayedTextValue() {
        return driver.findElement(outputText).getText().trim();
    }
    
    public String getDisplayedNumberOutput() {
        return element.getElementText(outputNumber).trim();
    }

    public String getDisplayedTextOutput() {
        return element.getElementText(outputText).trim();
    }
}
