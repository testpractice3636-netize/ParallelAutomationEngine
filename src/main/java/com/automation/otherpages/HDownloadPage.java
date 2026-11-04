package com.automation.otherpages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HDownloadPage {
	private final HElementActions action;
	private final WebDriver driver;

	public HDownloadPage(WebDriver driver)
	{
		this.driver=driver;
		this.action=new HElementActions(driver);
	}


	public void uploadFile(String file)
	{
		By dynamicFileLocator = By.xpath("//div[@id='content']//a[contains(text(), '" + file + "')]");
	        WebElement fileElement = action.waitForPresence(dynamicFileLocator);

	        
	        /*try {
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("arguments[0].style.border='3px solid orange';", fileElement);
	            Thread.sleep(500); 
	        } catch (Exception e) {
	           
	        }*/
	        try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        action.clickElement(dynamicFileLocator);
	        try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	}

