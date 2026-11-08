package com.automation.otherpages;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HLoginPage {

	private final WebDriver driver;
	private final HElementActions action;
	
	public HLoginPage(WebDriver driver)
	{
		this.driver=driver;
		this.action=new HElementActions(driver);
	}
	
	private final By loginFlashMsg = By.cssSelector(" div#flash-messages div#flash");
	
	//private final By uNameTextbox = By.xpath("//form[@id='two-factor-login']//label[@for='username']/following-sibling::input[@id='username']");
	
	//Rather than capturing label locators seperatly combined in one locator which indirectly verify label and its relevant textbox 
	private final By uNameTextbox = By.cssSelector("form#two-factor-login label[for='username'] + input#username");
	private final By uPassTextbox = By.cssSelector("form#two-factor-login label[for='password'] + input#password");
	
	 
	private final By loginNext = By.cssSelector("form#two-factor-login button[type='submit']");
	
	private final By codePageMsg = By.cssSelector(" div#content h2");
	private final By codeLocator = By.cssSelector(" div#content strong#otp-code");
	private final By codeTextBox = By.cssSelector("form#two-factor-login label[for='code'] ~ input#code");
	private final By codeTextBox1 = By.id("code");
			
	//private final By codeVerify = By.cssSelector("form#two-factor-login button[type='submit']");
	//private final By codeVerify = By.xpath("//form[@id='two-factor-login']//button[text()='Verify']");
	private final By codeVerify = By.xpath("//button[@type='submit']");

	private final By loginSucessMsg = By.cssSelector(" div#content h4");
	private final By loginSecureMsg = By.cssSelector(" div#content h2");


	public String verifyLogin(String uname, String pass, Boolean isPositiveData)
	{
		action.enterText(uNameTextbox,uname);
		action.enterText(uPassTextbox, pass);
		action.clickElement(loginNext);
		if(isPositiveData)
			return action.waitForVisibility(codePageMsg).getText().trim();
		else
			return action.waitForVisibility(loginFlashMsg).getText().trim();
			
		
	}
		
	public String verifyCode()
	{
		WebElement element = action.waitForVisibility(codeLocator);
		String getCode = element.getText().trim();
	   
	   action.enterText(codeTextBox1, getCode);
		
	   action.clickElement(codeVerify);
		
		String loginSucess=action.waitForVisibility(loginSucessMsg).getText().trim();
		return loginSucess;
		
	}
	
		/*@public Boolean verifyloginWithDeleteCookies()
	{
		Cookie savedCookies = driver.manage().getCookieNamed("rack.session");
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		Boolean isLoggedOut = action.waitForVisibility(uNameTextbox).isDisplayed();
		return isLoggedOut;
		
	}*/
		
		public String verifyloginWithCookies(Cookie validCapturedCookie)
		{
		    driver.manage().deleteAllCookies();
		    driver.navigate().refresh();
		    
		    try {
		        Thread.sleep(2000);
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		    
		    if (validCapturedCookie == null) {
		        throw new RuntimeException("❌ Test Architecture Failure: The captured cookie passed into the injection layer was null!");
		    }
		    
		    Boolean isLoggedOut = action.waitForVisibility(uNameTextbox).isDisplayed();
		    if (isLoggedOut)
		    {
		    	Cookie reconstructedCookie = new Cookie.Builder(validCapturedCookie.getName(), validCapturedCookie.getValue())
		    	        .domain(validCapturedCookie.getDomain())
		    	        .path(validCapturedCookie.getPath())
		    	        .isSecure(validCapturedCookie.isSecure())
		    	        .isHttpOnly(validCapturedCookie.isHttpOnly())
		    	        .build();
		    	
		    	
		        driver.manage().addCookie(reconstructedCookie);
		        
		        driver.navigate().refresh();
		    }
		    
		    try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    // Return the dashboard secure message verification target text
		    return action.waitForVisibility(loginFlashMsg).getText().trim();
		}

		

}