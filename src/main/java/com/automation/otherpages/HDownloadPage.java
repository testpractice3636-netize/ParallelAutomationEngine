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

	public void downloadFileByName(String fileName) {
        By dynamicFileLocator = By.xpath("//a[contains(@data-testid, '" + fileName + "')]");
        WebElement fileElement = action.waitForPresence(dynamicFileLocator);

        // 🎨 VISUAL CONFIRMATION FLASH: Highlights the element right before the click!
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='3px solid orange';", fileElement);
            Thread.sleep(500); // Small pause so your eyes can see the flash highlight
        } catch (Exception e) {
            // Silence highlight errors so it doesn't break the main execution flow
        }

        action.clickElement(dynamicFileLocator);
    }
	}

