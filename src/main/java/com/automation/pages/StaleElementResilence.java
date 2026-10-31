 	 package com.automation.utils;

	 import org.openqa.selenium.By;
	 import org.openqa.selenium.StaleElementReferenceException;
	 import org.openqa.selenium.WebDriver;
	 import org.openqa.selenium.WebElement;
	 import org.openqa.selenium.support.ui.ExpectedConditions;
	 import org.openqa.selenium.support.ui.WebDriverWait;
	 import com.automation.pages.ElementActions;

	 public class StaleElementResilence {
	     private final ElementActions actions;
	     private static final int MAX_RETRIES = 3;

	     public StaleElementResilence(WebDriver driver) {
	         this.actions = new ElementActions(driver);
	     }

	     /**
	      * Custom click wrapper that catches StaleElementReferenceExceptions, clears out 
	      * the corrupted memory references, and re-polls the DOM tree automatically.
	      */
	     public void resilientClick(By locator) {
	         int attempts = 0;
	         while (attempts < MAX_RETRIES) {
	             try {
	                 WebElement element = actions.getWait().until(ExpectedConditions.elementToBeClickable(locator));
	                 element.click();
	                 return;
	             } catch (StaleElementReferenceException e) {
	                 attempts++;
	                 System.out.println("⚠️ [STALE-RETRY] Caught reference decay for " + locator + ". Retrying element lookup (" + attempts + "/" + MAX_RETRIES + ")");
	                 if (attempts == MAX_RETRIES) throw e;
	             }
	         }
	     }
	 }
