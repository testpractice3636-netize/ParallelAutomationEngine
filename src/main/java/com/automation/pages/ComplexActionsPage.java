package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ComplexActionsPage {
	private final WebDriver driver;
	private final ElementActions element;
	
	private final By columnA = By.id("column-a");
	 private final By columnB = By.id("column-b");
	 
	 
	public ComplexActionsPage(WebDriver driver)
	{
		this.driver = driver;
		this.element = new ElementActions(driver);
	}
	
	    
	public void dragBoxAToBoxB() {
        System.out.println("[POM-ACTIONS] Locating real drag-and-drop columns...");
        
        // Synchronize using your framework's visibility actions
        WebElement sourceBox = element.waitForVisibility(columnA);
        WebElement targetBox = element.waitForVisibility(columnB);

        System.out.println("[POM-ACTIONS] Executing W3C precision mouse movement macro chain...");
        new Actions(driver)
            .clickAndHold(sourceBox)
            .moveToElement(targetBox)
            .release(targetBox)
            .build()
            .perform();
            
        
        System.out.println("[POM-ACTIONS] Drag-and-drop sequence dispatched.");
    }
	 
	 public String getColumnAText() {
	        return element.waitForVisibility(columnA).getText().trim();
	    }
	

}
