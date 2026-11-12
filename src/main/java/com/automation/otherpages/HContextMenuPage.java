package com.automation.otherpages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HContextMenuPage {

    private final WebDriver driver;
    private final HElementActions element;
    private final Actions actions;

    // Locator for the specific structural hot-spot right-click area box
    private final By hotSpotBox = By.id("hot-spot");

    public HContextMenuPage(WebDriver driver) {
        this.driver = driver;
        this.element = new HElementActions(driver);
        this.actions = new Actions(driver);
    }

    public String rightClickAndGetAlertText() {
        WebElement box = element.getWait().until(ExpectedConditions.visibilityOfElementLocated(hotSpotBox));
        
        // 🚀 Step 1: Perform a hardware right-click on the target coordinate element box
        actions.contextClick(box).perform();
        
        // 🚀 Step 2: Explicitly wait for the browser engine's native JS alert to pop up on screen
        element.getWait().until(ExpectedConditions.alertIsPresent());
        
        // Step 3: Switch driver focus directly into the alert modal context layer
        String alertMessage = driver.switchTo().alert().getText().trim();
        
        // 🚀 Step 4: Accept/Dismiss the alert modal to unfreeze the browser screen layout
        driver.switchTo().alert().accept(); 
        
        return alertMessage;
    }
}
