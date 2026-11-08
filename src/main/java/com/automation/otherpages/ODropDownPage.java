package com.automation.otherpages;


	package com.automation.otherpages;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import java.time.Duration;
	import java.util.List;

	public class ODropDownPage {

	    private final WebDriver driver;
	    private final WebDriverWait wait;

	    // Locators
	    private final By dropdownContainer = By.id("dest-picker");
	    private final By searchInput = By.className("dropdown-search-input");
	    private final By menuWrapper = By.className("dropdown-menu-wrapper");
	    
	    // 🎯 Task: Construct a dynamic string or nested locator strategy for the option items
	    // Hint: Think about how you want to target the specific city text securely.

	    public HDropdownPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    }

	    public void selectDestination(String partialInput, String fullCityText) {
	        // Step 1: Find the parent container element
	        WebElement parentContainer = driver.findElement(dropdownContainer);
	        
	        // Step 2: Click the trigger to open the input field
	        parentContainer.click();
	        
	        // Step 3: Type your partial search input ('New')
	        driver.findElement(searchInput).sendKeys(partialInput);
	        
	        // 🛑 YOUR TURN: Write the next steps!
	        // 1. Wait until the menuWrapper is explicitly visible.
	        WebElement dropdownBox = wait.until(ExpectedConditions.visibilityOfElementLocated(menuWrapper));
	        // 2. Fetch the dynamic list elements or construct your dynamic relational XPath.
	        List <WebElement> dropdownList = dropdownBox.findElements(By.className("menu-option-item"));
	        // 3. Loop or target the matching 'fullCityText' and click it securely.
	        for(WebElement element:dropdownList) {
	        	String listItem = element.getText().trim();
	        	if (listItem.equalsIgnoreCase(fullCityText))
	        	{
	        		element.click();
	        	}
	        }
	    }
	}


