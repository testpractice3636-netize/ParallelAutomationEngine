package com.automation.otherpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.automation.otherpages.HElementActions;

public class HDynamicLoadingPage {

	private final WebDriver driver;
	private final HElementActions element;
	
	public HDynamicLoadingPage(WebDriver driver)
	{
		this.driver = driver;
		this.element = new HElementActions(driver);
	}
	
	//private final By dynamicLoadingLink = By.xpath("//div[@id='content']//p[@href='/dynamic_loading/2']");
	private final By dynamicLoadingLink = By.cssSelector(" div#content a[href='/dynamic_loading/2']");
	
	//private final By startButton = By.xpath("//div[@id='start']/button");
	private final By startButton = By.cssSelector(" div#start>button");
	
	//private final By loading = By.xpath("//div[@id='loading']");
	private final By loading = By.cssSelector(" div#loading");
	
	//private final By finish = By.xpath("//div[@id='finish']/h4");
	private final By finish = By.cssSelector(" div#finish>h4");
	
	
	public Boolean loadDynamicLink()
	{
		element.clickElement(dynamicLoadingLink);
		Boolean chk = element.getWait().until(ExpectedConditions.urlContains("2"));
		//return element.getWait().until(d -> driver.getCurrentUrl().contains(urlVal));
		return chk;
	}
	
	public String loadDynamicText()
	{
		element.waitForPresence(startButton);
		element.clickElement(startButton);
		element.getWait().until(ExpectedConditions.invisibilityOfElementLocated(loading));
		return element.waitForPresence(finish).getText().trim();
			
	}
}
