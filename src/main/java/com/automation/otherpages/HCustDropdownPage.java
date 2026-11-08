package com.automation.otherpages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HCustDropdownPage {
		private final WebDriver driver;
		private final HElementActions action;
	    private final WebDriverWait wait;

		public HCustDropdownPage(WebDriver driver)
		{
			this.driver=driver;
			this.action=new HElementActions(driver);
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(17));
		}
		
		private final By selectDropDownLocator = By.id("fruit-combo");
		
		private final By selectItem = By.id("fruit-listbox");
		
		public Boolean selectItems(String item)
		{
			WebElement chooseDropdrownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(selectDropDownLocator));
			chooseDropdrownElement.click();
			
			WebElement listItemsBoxElement = wait.until(ExpectedConditions.visibilityOfElementLocated(selectItem));

			List<WebElement> listItemsElement = listItemsBoxElement.findElements(By.className("option"));
			for(WebElement listItem:listItemsElement)
			{
				String currentItem = listItem.getText().trim();
				if(currentItem.contains(item))
				{
					listItem.click();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
					
				}
				
			}
		 String afterSelect = chooseDropdrownElement.getText().trim();
		 Boolean isSelected = afterSelect.contains(item);
			return isSelected;
		}
}
