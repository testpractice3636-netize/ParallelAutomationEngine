package com.automation.otherpages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HDataTablesPage {

    private final WebDriver driver;
    private final HElementActions action;

    public HDataTablesPage(WebDriver driver) {
        this.driver = driver;
        this.action = new HElementActions(driver);
    }

    // 📋 TASK 1: Use clear CSS / XPath strategies to anchor to Table 1 elements
    private final By table1RowsPath = By.xpath("//table[@id='table1']//tbody/tr");
   // private String targetsDataXpath = "//table[@id='table1']/tbody/tr[td[normalize-space()='%s']]/td[" +"count(//table[@id='table1']/thead/tr/th[normalize-space()='%s']/preceding-sibling::th) + 1" +"]";
    //private String targetsHeader = "//table[@id='table1']/thead/tr[th[" +"count(//table[@id='table1']/tbody/tr[td[normalize-space()='%s']]/td[normalize-space()='%s']/preceding-sibling::td) + 1" +"]";
    private String dynamicDeleteXpathStr = "//table[@id='table1']/tbody/tr[td[normalize-space()='%s']]/td[" +
 	       "count(//table[@id='table1']/thead/tr[th[normalize-space()='%s']/preceding-sibling::th) + 1" + "]//a[text()='delete']";								
    private String nestedXpath = "./td[count(//table[@id='table1']/thead/tr/th[normalize-space()='%s']/preceding-sibling::th) + 1]"; 
   private String deldel = "//table[@id='table1']//td[normalize-space()='%s']/following-sibling::td//a[text()='delete']";
 
    /**
     * Workflow: Loop through the rows dynamically to find the user by their Email,
     * then locate and return the "Due" balance amount text in that exact same row.
     */
    public String getUserDueBalanceByEmail(String emailId, String due) {
    	// SOLUTION 1
    	/*String xpathColumnValueForTargets = String.format(targetsDataXpath, emailId, due);
    	By columnValueForTargetsLocator = By.xpath(xpathColumnValueForTargets);
    	String columnValue = driver.findElement(columnValueForTargetsLocator).getText().trim(); 
    			
       return columnValue;
       */
       // SOLUTION 2
    	String dueAmt ="";
    	Boolean recordMatch = false;
       List<WebElement> DataList = driver.findElements(table1RowsPath);
  
       for (WebElement DataList1 : DataList) {
    	   String currentRow = DataList1.getText().trim();
    	   if(currentRow.contains(emailId))
    	   {
    		 //  recordMatch = true;
    	   String nestedXpath1 = String.format(nestedXpath,due);
    	   By nestedLocator = By.xpath(nestedXpath1);
           dueAmt = DataList1.findElement(nestedLocator).getText().trim();
           System.out.println("wild check ---------->"+dueAmt);
        }
      //  if(!recordMatch)
        //    throw new RuntimeException("❌ Automation Failure: User with email " + emailId + " was not found in Table 1 grid.");
    
    }
      return dueAmt;
    }


    public void deleteUserByLastName(String lastName) {
    	

    	String deleteFormattedXpath = String.format(deldel, lastName);
    	By dynamicDeleteLink = By.xpath(deleteFormattedXpath);
        
    	action.clickElement(dynamicDeleteLink);
    	
     }
}
