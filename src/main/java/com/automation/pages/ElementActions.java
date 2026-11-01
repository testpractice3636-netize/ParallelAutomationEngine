package com.automation.pages;

import java.time.Duration;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;



public class ElementActions {
		private final WebDriver driver;
	    private WebDriverWait wait;
	    private Duration timeout = Duration.ofSeconds(17); 
	    private final int GLOBAL_TIMEOUT = 15;
	    private final JavascriptExecutor js;
	
	    /**
	     * Constructor cleanly instantiates execution tools exactly ONCE per thread session.
	     */
	    public ElementActions(WebDriver driver) {
	        this.driver = driver;
	        // Allocates the single master wait instance block in heap memory
	        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(15));
	        this.js = (JavascriptExecutor) this.driver;
	    }
	    
	 //{{{{{{{{{{{{{1}}}}}}}}}}}
	   /* public ElementActions(WebDriver driver) {
		 this.driver = driver;
		 this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//        this.actions = new Actions(driver);
//	        this.js = (JavascriptExecutor) driver;
	    }*/

	    //[[[[[[[[[[2]]]]]]]]]]
	 /*public void enterText(By locator, String value) {
	       
	    	try {
	            // 💎 TRUE EXPLICIT WAIT: Replaces the custom while loop and Thread.sleep entirely!
	           WebDriverWait wait = new WebDriverWait(driver, timeout);
	    	   WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	    		element.clear(); 
	            element.sendKeys(value);
	            System.out.println("[COMMONS-INFO] Successfully typed value using Native Explicit Wait.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 */
 
	    //[[[[[[[3]]]]]]]]]]
	/* public void clickElementByJs(By locator) {
	     System.out.println("[ACTIONS-ENGINE] Injecting native JavaScript click event to locator: " + locator);
	     WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
	        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

	     // Ensure the element exists in the DOM layout view first
	     // OPTIONS ---> WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	     
	     // Cast the active driver thread into a JavascriptExecutor interface session block
	     JavascriptExecutor jsEngine = (JavascriptExecutor) driver;
	     
	     // Execute the raw script directly on the target element
	     jsEngine.executeScript("arguments[0].click();", element);
	 }*/ 
	 /* public void clickElementByJs(By locator) {
		    try {
		        // 1. Try a standard crisp click first
		        driver.findElement(locator).click();
		    } catch (org.openqa.selenium.ElementClickInterceptedException e) {
		        System.out.println("[ACTIONS-WARN] Element click blocked by an overlay. Triggering JavaScript fallback click!");
		        
		        // 2. JavaScript fallback click to bypass the cookie banner instantly
		        WebElement element = driver.findElement(locator);
		        JavascriptExecutor js = (JavascriptExecutor) driver;
		        js.executeScript("arguments[0].click();", element);
		    }
		}
		*/
	
	    //[[[[[[[[[4]]]]]]]]]
	 public WebDriverWait getWait() {
	        return new WebDriverWait(driver, Duration.ofSeconds(GLOBAL_TIMEOUT));
	    }
	
	    public WebElement waitForVisibility(By locator) {
	        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	    }
	
	    public WebElement waitForClickable(By locator) {
	        return wait.until(ExpectedConditions.elementToBeClickable(locator));
	    }

	    public WebElement waitForPresence(By locator) {
	        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	    }
	    /**
	     * Enhanced Thread-Safe Text Input Wrapper. Zero inline wait allocations.
	     */
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
	    /**
	     * Enhanced Thread-Safe Click Wrapper. Leverages JS fallback mechanism cleanly.
	     */
	    /*public void clickElement(By locator) {
	        try {
	        	WebElement element = waitForClickable(locator);
	        	element.click();
	            
	        } catch (Exception nativeClickException) {
	            try {
	                // Safe reuse of the pre-cast JavaScript execution engine variable
	                WebElement jsElement = waitForPresence(locator);
	                js.executeScript("arguments[0].click();", jsElement);
	            } catch (Exception jsException) {
	                throw new AssertionError("Click execution failed via both native and fallback JS loops: " 
	                        + locator.toString(), jsException);
	            }
	        }
	    }*/
	    public void clickElement(By locator) {
	        int attempts = 0;
	        int maxRetries = 3;

	        while (attempts < maxRetries) {
	            try {
	                // 🚀 Step 1: Attempt standard native interaction flow
	                WebElement element = waitForClickable(locator);
	                element.click();
	                return; // Native click succeeded, break out completely

	            } catch (StaleElementReferenceException staleException) {
	                attempts++;
	                System.out.println("⚠️ [RESILIENCE-ENGINE] Caught stale reference on: " + locator.toString() 
	                    + " -> Re-polling DOM tree structure (Attempt " + attempts + "/" + maxRetries + ")");
	                
	                if (attempts == maxRetries) {
	                    // If the element remains stale after max retries, allow it to fall down to the JS catch block
	                    System.out.println("💥 [RESILIENCE-ENGINE] Element remains stale after maximum retries. Routing to fallback execution...");
	                }

	            } catch (Exception nativeClickException) {
	                // 🚀 Step 2: Handle any other native exception or final stale breakout via JS Fallback
	                try {
	                    System.out.println("⚠️ [CLICK-GUARD] Native click failed. Triggering JS injector fallback...");
	                    WebElement jsElement = waitForPresence(locator);
	                    js.executeScript("arguments[0].click();", jsElement);
	                    return; // Fallback succeeded, break out completely
	                    
	                } catch (Exception jsException) {
	                    throw new AssertionError("Click execution failed via both native retry loops and fallback JS loops: " 
	                            + locator.toString(), jsException);
	                }
	            }
	        }
	    }

	    
	        /**
	         * 🚀 ENTERPRISE FLUENT WAIT ENGINE:
	         * Custom-built loop that ignores DOM errors and polls fast changing states every 200ms.
	         */
	        public WebElement waitForElementToBeClickableFluent(By locator, int timeoutInSeconds) {
	            Wait<WebDriver> fluentWait = new FluentWait<>(driver)
	                    .withTimeout(Duration.ofSeconds(timeoutInSeconds))
	                    .pollingEvery(Duration.ofMillis(200)) // Checks 5x faster than default WebDriverWait!
	                    .ignoring(NoSuchElementException.class)
	                    .ignoring(StaleElementReferenceException.class); // Prevents DOM refresh crashes

	            return fluentWait.until(ExpectedConditions.elementToBeClickable(locator));
	        }

	        /**
	         * 🔍 INVISIBILITY GUARD:
	         * Blocks execution until a dynamic loading bar/spinner completely vanishes from the DOM.
	         */
	        public boolean waitForElementToDisappearFluent(By locator, int timeoutInSeconds) {
	            Wait<WebDriver> fluentWait = new FluentWait<>(driver)
	                    .withTimeout(Duration.ofSeconds(timeoutInSeconds))
	                    .pollingEvery(Duration.ofMillis(200))
	                    .ignoring(StaleElementReferenceException.class);

	            return fluentWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	        }

	        public void click(By locator, int timeout) {
	            WebElement element = waitForElementToBeClickableFluent(locator, timeout);
	            
	            try {
	                // Step 1: Attempt standard clean user action click
	                element.click();
	            } catch (ElementClickInterceptedException e) {
	                System.out.println("⚠️ [INTERCEPTION-GUARD] Click blocked on locator: " + locator + ". Triggering self-healing layout scripts...");
	                
	                try {
	                    // Step 2: Smoothly scroll the element to the center of the screen to clear overlays
	                    JavascriptExecutor js = (JavascriptExecutor) driver;
	                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
	                    //Thread.sleep(200); // Give layout half a second to settle
	                    
	                    element.click(); // Re-try native click
	                } catch (Exception jsFallbackException) {
	                    // Step 3: Ultimate bypass - Inject JS mouse click event straight into the DOM node
	                    JavascriptExecutor js = (JavascriptExecutor) driver;
	                    js.executeScript("arguments[0].click();", element);
	                    System.out.println("🚀 [BYPASS-SUCCESS] Element successfully triggered using Javascript Execution bypass.");
	                }
	            }
	        }

	        public String getText(By locator, int timeout) {
	            Wait<WebDriver> fluentWait = new FluentWait<>(driver)
	                    .withTimeout(Duration.ofSeconds(timeout))
	                    .pollingEvery(Duration.ofMillis(200))
	                    .ignoring(NoSuchElementException.class);
	            
	            return fluentWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
	        }
	        
	        /**
	         * 🔔 ALERT READY GUARD:
	         * Dynamically loops and blocks execution until a native JavaScript alert is present on the browser.
	         */
	        public Alert waitForAlertToBePresent() {
	            return getWait().until(ExpectedConditions.alertIsPresent());
	        }

	        public void acceptAlert() {
	            waitForAlertToBePresent().accept();
	        }

	        public void dismissAlert() {
	            waitForAlertToBePresent().dismiss();
	        }

	        public String getAlertText() {
	            return waitForAlertToBePresent().getText();
	        }

	        public void typeIntoAlert(String payload) {
	            org.openqa.selenium.Alert alert = waitForAlertToBePresent();
	            alert.sendKeys(payload);
	        }

	   
		
	        public String getElementText(By locator) {
	        	return getWait()
	        			.until(ExpectedConditions.visibilityOfElementLocated(locator))
	        			.getText();
	        }
	
	        //WEBINPUT

	        public String getFieldValue(By locator) {
	        	return getWait()
	        			.until(ExpectedConditions.visibilityOfElementLocated(locator))
	        			.getAttribute("value");
	        }
	        
	        // for TABLE 
	        
	        public List<WebElement> getElements(By locator) {
	            // 💡 First, wait for the first element of the collection to appear on screen to prevent timing issues
	            getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
	            return driver.findElements(locator);
	        }
	}
	   /* public void clickElement(By locator) {
	    	try
	    	{
	        waitForClickable(locator).click();
	    	} catch (Exception e) {
	            e.printStackTrace();
	        }
	 
		    }*/

