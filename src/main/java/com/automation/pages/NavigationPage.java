package com.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class NavigationPage {

	
	private WebDriver driver;
	private ElementActions element;
 
	 // 🔥 UNBREAKABLE LOCATORS: Re-mapped to leverage distinct partial node strings
    //private By loginClick = By.xpath("//a[contains(@href, '/login') and contains(text(), 'Login')]");
    //private By multiWindowClick = By.xpath("//a[contains(@href, '/windows') or contains(text(), 'Windows')]");

    //private final By loginClick = By.xpath("//a[contains(@href, '/login')]");

	 private By loginClick = By.linkText("Test Login Page");
	 private By multiWindowClick = By.linkText("Multiple Windows");
    //private By nestedFramesLink = By.xpath("//a[contains(@href, '/nested-frames') or contains(text(), 'Nested Frames')]");
	 private By nestedFramesLink = By.linkText("Nested Frames");
	 private By dynamicPaginationLink = By.linkText("Dynamic Pagination Table");
	 private By webInputClick = By.linkText("Web inputs");


	 	  
	 public NavigationPage(WebDriver driver) {
	        this.driver = driver;
	        this.element = new ElementActions(driver);
	    }
	 

	  public void contentPageLink()
	    {
	    	//driver.get("https://practice.expandtesting.com/");
		driver.get("https://practice.expandtesting.com");
	    }
	  public void dynamicPaginationTable(WebDriver driver) {
			// Explicit synchronization guard to make sure the elements are present on screen before interacting
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(17));
		        wait.until(ExpectedConditions.visibilityOfElementLocated(dynamicPaginationLink));
		        
		        element.clickElement(dynamicPaginationLink);
		    }
	  
	 public void cLoginLnk(WebDriver driver) {
		// Explicit synchronization guard to make sure the elements are present on screen before interacting
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(17));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(loginClick));
	        
	        element.clickElement(loginClick);
	    }
	 public void webInputLink(WebDriver driver) {
			// Explicit synchronization guard to make sure the elements are present on screen before interacting
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(17));
		        wait.until(ExpectedConditions.visibilityOfElementLocated(webInputClick));
		        
		        element.clickElement(webInputClick);
		    }
	 public void multiWindowLink(WebDriver driver) {
		 
		// Explicit synchronization guard to make sure the elements are present on screen before interacting
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(17));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(multiWindowClick));
	        
	        element.clickElement(multiWindowClick);
	    }
	 
	 public void nestedFramesLink(WebDriver driver) {
	        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(15));
	        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(nestedFramesLink));
	        link.click();
	        // wait.until(ExpectedConditions.elementToBeClickable(nestedFramesLink));
	        //element.clickElementByJs(nestedFramesLink);
	    }
}
