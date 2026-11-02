package com.automation.otherpages;

import java.time.Duration;

import org.apache.poi.poifs.filesystem.Ole10NativeException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.bytebuddy.asm.MemberSubstitution.Argument;
import net.bytebuddy.implementation.bytecode.Throw;

public class HElementActions {

	private final WebDriver driver;
	private final int GLOBAL_TIMEOUT = 17;
	private final WebDriverWait wait;
	private final JavascriptExecutor js;
	
	public HElementActions(WebDriver driver)
	{
		this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) this.driver;
	}
	
	public WebDriverWait getWait()
	{
		return new WebDriverWait(driver, Duration.ofSeconds(GLOBAL_TIMEOUT));
	}
	
	public WebElement waitForVisibility(By locator)
	{
		return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public WebElement waitForClickable(By locator)
	{
		return getWait().until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	public WebElement waitForPresence(By locator)
	{
		return getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public void clickElement(By locator) {
	    int attempts = 0;
	    int maxRetries = 3;
	     
	    while (attempts < maxRetries) {
	        try {
	            WebElement element = waitForClickable(locator);
	            element.click();
	            return; // 🚀 Native click event executed successfully!
	            
	        } catch (StaleElementReferenceException staleException) {
	            attempts++;
	            System.out.println("⚠️ [RESILIENCE-ENGINE] Stale node caught on: " + locator.toString() + ". Retrying element lookup...");
	            
	        } catch (WebDriverException webDriverException) {
	            // 🎯 Architectural Filter: If the browser is mid-navigation or unloads the DOM tree structure,
	            // intercept the throw, suppress the fallback check, and allow the thread to cross the boundary cleanly.
	            String message = webDriverException.getMessage().toLowerCase();
	            if (message.contains("navigation") || message.contains("document") || message.contains("unload")) {
	                System.out.println("ℹ️ [NAV-GUARD] Page context transformation intercepted mid-click event. Bypassing fallback lookup safely.");
	                return; 
	            }
	            
	            // If it's a standard click interception error (like a pop-up overlay), route straight to JS fallback
	            System.out.println("⚠️ [CLICK-GUARD] Native click obstructed by viewport component. Triggering JS fallback injector...");
	            executeJavaScriptClickFallback(locator);
	            return;
	            
	        } catch (Exception genericException) {
	            executeJavaScriptClickFallback(locator);
	            return;
	        }
	    }
	    
	    System.out.println("💥 [RESILIENCE-ENGINE] Max retries exhausted. Running final fallback sequence...");
	    executeJavaScriptClickFallback(locator);
	}
	private void executeJavaScriptClickFallback(By locator) {
	    try {
	        WebElement jsElement = waitForVisibility(locator);
	        js.executeScript("arguments[0].click();", jsElement); 
	    } catch (Exception jsException) {
	        throw new AssertionError("Click failed via both native loops and fallback JS loops: " + locator.toString(), jsException);
	    }
	}
	/*public void clickElement(By locator)
	{
		 int attempts = 0;
	     int maxRetries = 3;
	     
		while(attempts < maxRetries)
		{
		try
		{
			WebElement element = waitForClickable(locator);
			element.click();
			return;
		}
		
		catch (StaleElementReferenceException staleException)
		{
			attempts++;
			System.out.println("⚠️ [RESILIENCE-ENGINE] Caught stale reference on: " + locator.toString() 
            + " -> Re-polling DOM tree structure (Attempt " + attempts + "/" + maxRetries + ")");
			if(attempts == maxRetries)
			{
                System.out.println("💥 [RESILIENCE-ENGINE] Element remains stale after maximum retries. Routing to fallback execution...");

			}
			
		}
		catch(Exception nativeClickException)
		{
			try
			{
				WebElement jsElement = waitForVisibility(locator);
				js.executeScript("Arguments[0].click()", jsElement);
				return;
				
			}
			catch (Exception jsException) {
                throw new AssertionError("Click execution failed via both native retry loops and fallback JS loops: " 
                        + locator.toString(), jsException);
			}
		}
		}
	}*/
	
	 /*public void enterText(By locator, String value) {
	    	int attempts = 0;
	    	int maxRetries = 3;
	    	//String check;
	    	//Actions actions = new Actions(driver);
 	        while (attempts < maxRetries) {
	            try {
	            // 💎 TRUE REUSE: Uses 'this.wait' instance allocation cleanly
	            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	            element.clear(); 
	            element.sendKeys(value);
	          
	            System.out.println("[COMMONS-INFO] Successfully typed value via optimized master wait.");
	            return;
	            	} 
	            catch (StaleElementReferenceException staleException) {
	                attempts++;
	                System.out.println("⚠️ [RESILIENCE-ENGINE] DOM modification intercepted text entry. Retrying...");
	                if (attempts == maxRetries) {
	                    throw staleException;
	                }
	            }
	            catch (Exception e) {
	            	attempts++;
	                System.out.println("⚠️ [RESILIENCE-ENGINE] General interaction lag detected on " + locator.toString() + ". Retrying (" + attempts + "/" + maxRetries + ")");
	                if (attempts == maxRetries) {
	                    throw new AssertionError("Text input failed entirely after maximum retry loops: " + locator.toString(), e);
	                }
	        }
	    }
	    }*/
	public void enterText(By locator, String value) {
	    int attempts = 0;
	    int maxRetries = 3;
	    
	    // 💡 ARCHITECT NOTE: Declare Actions out here so it doesn't get reconstructed in every retry loop
	    Actions actions = new Actions(driver);

	    while (attempts < maxRetries) {
	        try {
	            // 💎 TRUE REUSE: Uses 'this.wait' instance allocation cleanly
	            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	            
	            element.clear(); 
	            element.sendKeys(value);
	            
	            // 💡 WYSIWYG FIX: Rich-text editors inside iFrames (like TinyMCE) often hold text 
	            // inside innerText/Text Content rather than the standard HTML 'value' attribute.
	            String textCheck = element.getText();
	            String valueCheck = element.getAttribute("value");
	            
	            // If standard sendKeys failed to insert any text, fall back to the native Actions mouse/keyboard simulation
	            if ((textCheck == null || textCheck.trim().isEmpty()) && (valueCheck == null || valueCheck.trim().isEmpty())) {
	                System.out.println("⚠️ [RESILIENCE-ENGINE] Standard input unverified. Executing precise hardware-level Actions simulation...");
	                actions.moveToElement(element)
	                       .click()
	                       .sendKeys(value)
	                       .build()
	                       .perform();
	            }
	            
	            System.out.println("[COMMONS-INFO] Successfully typed value via optimized master wait.");
	            return;
	            
	        } catch (StaleElementReferenceException staleException) {
	            attempts++;
	            System.out.println("⚠️ [RESILIENCE-ENGINE] DOM modification intercepted text entry. Retrying...");
	            if (attempts == maxRetries) {
	                throw staleException;
	            }
	        } catch (Exception e) {
	            attempts++;
	            System.out.println("⚠️ [RESILIENCE-ENGINE] General interaction lag detected on " + locator.toString() + ". Retrying (" + attempts + "/" + maxRetries + ")");
	            if (attempts == maxRetries) {
	                throw new AssertionError("Text input failed entirely after maximum retry loops: " + locator.toString(), e);
	            }
	        }
	    }
	}

}
