package com.automation.pages;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MultiWindow {

	private WebDriver driver;
    private ElementActions element;
    
    public MultiWindow(WebDriver driver)
    {
    	this.driver=driver;
    	this.element=new ElementActions(driver);;
    	
    }
	
	 private By multiWindowLink = By.linkText("Click Here");
	 //private By nextTabHeader = By.xpath("//div[@class='example']/h1[contains(text(),'Window')]");
	// private By nextTabHeader = By.xpath("//h1[contains(text(),'Window')]");
	 private By nextTabHeader = By.tagName("h1");
	 
	 public boolean verifyMultipleWindow() throws InterruptedException {
	        // Save the original parent handle context first
	        String parentHandle = driver.getWindowHandle();
	        
	        // Click the link using our explicit wait wrapper tool
	        element.clickElement(multiWindowLink);
	        
	        // 🔑 THE STABILITY FIX: Asynchronously wait up to 10 seconds for the URL context to load!
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
	        
	        // Context shift focus to child window tab handle layout
	       Set<String> totalTabs = driver.getWindowHandles();
	        for (String individualTab : totalTabs) {
	            if (!individualTab.equalsIgnoreCase(parentHandle)) {
	                driver.switchTo().window(individualTab);
	                break;
	            }
	        }
	        
	        // Asynchronously wait up to 15 seconds for the remote server domain context to load
	        wait.until(ExpectedConditions.visibilityOfElementLocated(nextTabHeader));
	        String currentTabUrl = driver.getCurrentUrl();
	        System.out.println("[POM-VERIFICATION] Active child  tab target URL: " + currentTabUrl);
	        boolean isHeaderPresent = driver.findElement(nextTabHeader).isDisplayed();
	        // Cleanly terminate the child tab and restore focus
	        driver.close();
	        driver.switchTo().window(parentHandle);
	        
	     
	        	return isHeaderPresent;
	       
	    }
	    
}
