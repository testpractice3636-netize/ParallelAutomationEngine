package com.automation.otherpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HHoverPage {

	private final WebDriver driver;
	private final HElementActions action;
	private final Actions actions;
	
	public HHoverPage(WebDriver driver)
	{
		this.driver = driver;
		this.action = new HElementActions(driver);
		this.actions = new Actions(driver);
	}
	
	//private final By hoverImage = By.cssSelector(" dic#content img");
	private final String hoverImage = ("//div[@id='content']//div[@class='figure']/img[%d]");
	private final String hoverImageCaption = ("//div[@id='content']//div[@class='figcaption']/h5[%d]");
	private final String profileLink = ("//div[@id='content']//div[@class='figcaption']/a[%d]");
	
	
	//direct drag drop
	  private final By columnA = By.id("column-a");
    private final By columnB = By.id("column-b");
    
	public void hoverInToImage(int no)
	{
		By hoverImageNo = By.xpath(String.format(hoverImage, no));
		WebElement element = action.waitForVisibility(hoverImageNo);
		
		
		actions.moveToElement(element).build().perform();
		
		//nested recall
		/*actions.moveToElement(label).click()
	       .moveToElement(textBox).click()
	       .build().perform();
		*/
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
	/* REFER FOR SINGLE METHOD CHECK
    public String getUsernameOnHover() {
        WebElement avatar = element.getWait().until(ExpectedConditions.visibilityOfElementLocated(firstAvatarImg));
        
        // 🚀 Step 1: Physical mouse hover execution chain
        actions.moveToElement(avatar)
               .build() // Package the chain
               .perform(); // Dispatch to browser hardware engine
               
        // Step 2: Extract text from the newly revealed caption layout
        return element.getWait()
                      .until(ExpectedConditions.visibilityOfElementLocated(firstAvatarCaption))
                      .getText()
                      .trim();
    }
	 */
	public void swapColumns() {
        WebElement source = action.getWait().until(ExpectedConditions.visibilityOfElementLocated(columnA));
        WebElement target = action.getWait().until(ExpectedConditions.visibilityOfElementLocated(columnB));

        // 🚀 Advanced Actions Chain Option 1: The Direct Method
        actions.dragAndDrop(source, target).perform();
        
        /* 
        💡 Interview Knowledge Note: 
        If the direct dragAndDrop method ever fails due to custom JS library constraints on a site, 
        you can build the manual micro-interaction mouse chain yourself like this:
        
        actions.clickAndHold(source)
               .moveToElement(target)
               .release(target)
               .build()
               .perform();
        */
    }

    public String getColumnAHeaderText() {
        return action.getWait().until(ExpectedConditions.visibilityOfElementLocated(columnA)).getText().trim();
    }
}
