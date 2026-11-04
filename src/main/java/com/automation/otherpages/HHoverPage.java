package com.automation.otherpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class HHoverPage {

	private final WebDriver driver;
	private final HElementActions action;
	public HHoverPage(WebDriver driver)
	{
		this.driver = driver;
		this.action = new HElementActions(driver);
	}
	
	//private final By hoverImage = By.cssSelector(" dic#content img");
	private final String hoverImage = ("//div[@id='content']//div[@class='figure']/img[%d]");
	private final String hoverImageCaption = ("//div[@id='content']//div[@class='figcaption']/h5[%d]");
	private final String profileLink = ("//div[@id='content']//div[@class='figcaption']/a[%d]");
	
	public void hoverInToImage(int no)
	{
		By hoverImageNo = By.xpath(String.format(hoverImage, no));
		WebElement element = action.waitForVisibility(hoverImageNo);
		
		Actions a =new Actions(driver);
		a.moveToElement(element).perform();
		
		
	}
	
	public String getHoverImageText(int no)
	{
		By hoverImageText = By.xpath(String.format(hoverImageCaption, no));
		WebElement element = action.waitForVisibility(hoverImageText);
		
		return element.getText().trim();
	}
	
	public void clickProfile(int no)
	{
		By profileClickNo = By.xpath(String.format(profileLink, no));
		
		action.clickElement(profileClickNo);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
