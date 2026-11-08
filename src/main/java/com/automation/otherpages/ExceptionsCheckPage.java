package com.automation.otherpages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExceptionsCheckPage {

    private final WebDriver driver;
    private final HElementActions action; 
    private final WebDriverWait wait;

    public ExceptionsCheckPage(WebDriver driver) {
        this.driver = driver;
        this.action = new HElementActions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(17));
    }

    private final By removeAddButton   = By.cssSelector("form#checkbox-example button");
    private final By targetCheckbox    = By.cssSelector("form#checkbox-example input[type='checkbox']");
    private final By checkboxStatusMsg = By.cssSelector("form#checkbox-example p#message");

    private final By enableDisableBtn  = By.cssSelector("form#input-example button");
    private final By textInputBox      = By.cssSelector("form#input-example input[type='text']");
    private final By inputStatusMsg    = By.cssSelector("form#input-example p#message");

    public String removeCheckboxAndGetMessage() {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(removeAddButton));
            btn.click();
        } catch (ElementNotInteractableException e) {
            WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(removeAddButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        } catch (TimeoutException e) {
            // Re-throw with clean framework structural diagnostics
            throw new AssertionError("❌ Element discovery timed out on structural locator target: " + removeAddButton, e);
        }
            
        // Flawless synchronisation selection
        wait.until(ExpectedConditions.invisibilityOfElementLocated(targetCheckbox));
        return action.waitForVisibility(checkboxStatusMsg).getText().trim();
    }

    public String enableInputAndTypeText(String customText) {
        try {
            // Step 1: Click Enable
            WebElement actionBtn = wait.until(ExpectedConditions.elementToBeClickable(enableDisableBtn));
            actionBtn.click();
            
            // Step 2: Synchronize on input readiness & type payload
            WebElement textBox = wait.until(ExpectedConditions.elementToBeClickable(textInputBox));
            textBox.sendKeys(customText);
            
            // Step 3: Click Disable to align with your assertion target text
            actionBtn.click();
            
        } catch (ElementNotInteractableException e) {
            WebElement actionBtn = wait.until(ExpectedConditions.presenceOfElementLocated(enableDisableBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", actionBtn);
        } catch (TimeoutException e) {
            throw new AssertionError("❌ Dynamic controls failed to mutate element states within runtime allocation limit: " + enableDisableBtn, e);
        }
        
        // 🌟 THE FIX: Replace Thread.sleep with a dynamic visibility wait for the text update
        WebElement inputStatusElement = wait.until(ExpectedConditions.visibilityOfElementLocated(inputStatusMsg));
        return inputStatusElement.getText().trim();
    }
}
