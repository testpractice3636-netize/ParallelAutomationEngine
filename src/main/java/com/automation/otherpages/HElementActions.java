package com.automation.otherpages;

import java.time.Duration;

import org.apache.poi.poifs.filesystem.Ole10NativeException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
	
	public void clickElement(By locator)
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
	}
	
	 public void enterText(By locator, String value) {
	    	int attempts = 0;
	    	int maxRetries = 3;
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
	    }
	
}
