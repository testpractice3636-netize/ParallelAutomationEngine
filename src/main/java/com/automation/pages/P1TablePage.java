package com.automation.pages;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class P1TablePage {

    WebDriver driver;
    ElementActions element;
    
    // ✅ FIXED: Restored missing closing brackets ']' and added double slashes '//'
    private final By studentColumnHeader = By.xpath("//table[@id='example']/thead/tr[1]/th[text()='Student Name']");
    //private final By studentSortingColumn = By.xpath("//table[@id='example']//tbody/tr/td[1]");
    private final By next = By.xpath("//div[@id='example_paginate']//li/a[text()='Next']");
    private final By nextDisabled = By.xpath("//a[text()='Next']/parent::li");
    private final By tableRows = By.xpath("//table[@id='example']/tbody/tr");

    private final By studentSortingColumn = By.xpath("//table[@id='example']//tbody/tr/td[1]");
    //private final By dropdownSelectElement = By.xpath("//div[@id='example_length']");
    private final By dropdownSelectElement = By.xpath("//div[@id='example_length']//select");

    private final By nosOfDataListed = By.xpath("//div[@id='example_info']");
    
    public P1TablePage(WebDriver driver) {
        this.driver = driver;
        this.element = new ElementActions(driver);
    }
    
    public List<String> getTableData() {
        element.waitForPresence(studentSortingColumn);
        List<WebElement> actualData = element.getElements(studentSortingColumn);
        List<String> strActualData = new ArrayList<>();
        
        for (WebElement tElement : actualData) {
            strActualData.add(tElement.getText().trim());
        }
        return strActualData;
    }
    
    public void sortTable() {
        // ✅ FIXED: Changed target locator from 'next' to the actual column header click target!
        element.clickElement(studentColumnHeader);
    }
    
    public boolean clickNextPageIfEnabled() {
        // We track the parent list item node to check for the 'disabled' class token layout
        WebElement nextDisabledButton = element.waitForPresence(nextDisabled);
        
        // ✅ FIXED: Changed string lookups to exact lowercase values ("class" and "disabled")
        if (nextDisabledButton.getAttribute("class").contains("disabled")) {
            return false;
        }
        
        element.clickElement(next);
        return true;
    }
    
    public void chooseNoOfTableData(String valueOption)
    {
        // Step 1: Wait for and click the dropdown element to expand the menu options
        element.waitForPresence(dropdownSelectElement);
        //WebElement dropdownSelectElement1 = driver.findElement(dropdownSelectElement);
        element.clickElement(dropdownSelectElement);
        
        
        // Step 2: Target the specific option layout element based on text and click it
        //String optionXPath = "//option[normalize-space(.)='" + valueOption + "'] | //div[@id='example_length']//*[normalize-space(.)='" + valueOption + "']";
        String optionXPath = "//option[normalize-space(text())='" + valueOption + "'] | //div[@id='example_length']//*[normalize-space(text())='" + valueOption + "']";
        
        By targetOption = By.xpath(optionXPath);
        
        element.waitForPresence(targetOption);
        WebElement tagetOption = driver.findElement(targetOption);
        element.clickElement(targetOption);
    }
    
    public String getTableDataDisplayedCount()
    
    {
    	element.waitForPresence(nosOfDataListed);
    	
    	return driver.findElement(nosOfDataListed).getText().trim();
    }
    
    public Boolean isTargetRecordPresent(String searchName)
    {
    	element.waitForVisibility(studentSortingColumn);
    	List<WebElement> tableData = element.getElements(studentSortingColumn);
    	
    	for(WebElement tElement : tableData)
    	{
    		if (tElement.getText().trim().equalsIgnoreCase(searchName))
    			return true;
    	}
    	
    	return false;
    }
    public boolean searchAllPagesForStudent(String studentName)
    		{
    	By studentRowLocator = By.xpath("//table[@id='example']/tbody/tr/td[normalize-space(.)='" + studentName +"']");
    	
    	while(true)
    	{
    	element.waitForPresence(tableRows);
    	if(element.getElements(studentRowLocator).size()>0)
    	return true;
    
    	WebElement nextContainer = element.waitForPresence(nextDisabled);
    	if (nextContainer.getAttribute("class").contains("disabled"))
    	{
    		return false;
    	}
        WebElement oldRowReference = driver.findElement(tableRows);

    	element.clickElement(next);
    	
    	element.getWait().until(ExpectedConditions.stalenessOf(oldRowReference));
            
    	}
}
}