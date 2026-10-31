package com.automation.pages;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.pages.ElementActions;

public class LoginPage {

	private WebDriver driver;
	private ElementActions element;
 
	 private By loginLink = By.xpath("//a[text()='Test Login Page']");
	
	 private By emailField    = By.xpath("//input[@id='username']");
	 private By passwordField = By.xpath("//input[@id='password']");
	 private By loginButton   = By.xpath("//button[@id='submit-login']");
	 private By loggedInHeaderTitle   = By.xpath("//b[text()='You logged into a secure area!']");
	 
	 //own
	 private By username = By.xpath("//h3[@id='username']");
	 private By loggedText = By.xpath("//div[@id='flash']/b");
	 
	 public LoginPage(WebDriver driver) {
	        this.driver = driver;
	        this.element = new ElementActions(driver);
	    }
	
	 public void loginclick() {
	        element.clickElement(loginLink);
	    }
	 
	 public void enterEmail(String email) {
	    	element.enterText(emailField, email);
	 }
	 
	 public void enterPassword(String password) {
	    	element.enterText(passwordField, password);
      }

	    public void clickLogin() {
	        element.clickElement(loginButton);
	    }
	    
	    // Combined Business Workflow
	    public void loginToApplication(String email, String password) {
	        this.enterEmail(email);
	        this.enterPassword(password);
	        this.clickLogin(); 
	    
	    }
	    public String loginSuccessMsgValidation()
	    {
	    	String flashText = element.waitForVisibility(loggedText).getText();
	    	//String userPresence = 
	    	if (flashText.contains("logged in"))
	    	  	return element.waitForVisibility(username).getText();
	    	return flashText;
	    	
	    }
	    public boolean isLoginSuccessful() {
	        try {
	            // We reuse our element engine logic or a quick standard driver check to see if the title renders
	            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(15));
	            return wait.until(visibilityOfElementLocated(loggedInHeaderTitle)).isDisplayed();
	        } catch (Exception e) {
	            System.err.println("[PAGE-WARN] Products dashboard title banner was not found on screen.");
	            return false; // Timed out waiting for the element, login likely failed!
	        }
	    }
}