package com.automation.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.automation.pages.ElementActions;

public class WebTablePage {

    private final WebDriver driver;
    private final ElementActions element;

    // 🎯 Target Locators for the ExpandTesting Tables Layout Challenge
    private final By columnHeaderFirstName = By.xpath("//table[@id='table1']//span[text()='First Name']");
    private final By firstNameColumnCells  = By.xpath("//table[@id='table1']/tbody/tr/td[2]");
    private final String dynamicActionRowTemplate = "//tr[td[text()='%s']]//a[contains(@href,'#delete')]"; 
    private final String dynamicActionEdit = "//tr[td[text()='%s']]//a[contains(@href,'#edit')]";


    // PAGINATION WEB TABLE
    private final By studentRows          = By.xpath("//table[contains(@class,'table')]/tbody/tr");
   // private final By nextButton           = By.xpath("//a[text()='Next']");
    
    private final By parentNextButtonLi   = By.xpath("//a[text()='Next']/parent::li");

    // 🎯 Add these locators at the top of P1TablePage.java
      
    private final By nextButton = By.xpath("//div[@id='example_paginate']//li/a[text()='Next']");
    private final By tableBodyCells = By.xpath("//table[@id='example']/tbody/tr/td");
    private final By tableRows = By.xpath("//table[@id='example']/tbody/tr");
    private final By nextParentLI = By.xpath("//a[text()='Next']/parent::li");
    
    
// TABLE PAGE
    // 🎯 Dynamic Table Locators Map
    private final By lastNameHeader = By.xpath("//table[@id='table1']//thead//span[text()='Last Name']");
    
    // 👁️ THE OPENS-EYES LOCATOR: Sweeps the entire 1st vertical data column down the grid matrix layout!
    private final By lastNameColumnCells = By.xpath("//table[@id='table1']//tbody/tr/td[1]");
    private final By studentNameColumnCells = By.xpath("//table[@id='example']/tbody/tr/td[1]");

    
    
    public WebTablePage(WebDriver driver) {
        this.driver = driver;
        this.element = new ElementActions(driver);
    }
    /**
     * Extracts the raw string values from a target table column on screen.
     * @return A list of strings matching row text fields.
     */
    public List<String> getColumnDataTextMatrix() {
        // Sync and make sure the rows are actively tracking on the DOM layout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> cellElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(firstNameColumnCells));
        System.out.println("--------------PRINT LIST--------------" + cellElements.stream().map(WebElement::getText).map(String::trim).collect(Collectors.toList()));
        // Use Java Streams to map elements to clean, trimmed text chunks
        
        //---TO UNDERSTAND BELOW FOW LOOP ----------
    /* // 1. Create a blank list to hold the final results
        List<String> cleanTextList = new ArrayList<>();

        // 2. Loop through every web element manually
        for (WebElement element : cellElements) {
            // 3. Extract text
            String rawText = element.getText();
            // 4. Clean/Trim text
            String cleanText = rawText.trim();
            // 5. Add to our new list
            cleanTextList.add(cleanText);
        }*/
        return cellElements.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    /**
     * Triggers the UI sort algorithm by clicking the FirstName header button.
     */
    public void clickSortByFirstName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(columnHeaderFirstName));
        element.clickElement(columnHeaderFirstName);
    }
    public void deleteUserByEmailRowAnchor(String targetEmail) {
        // 1. Substitute the real email into our XPath template layout
        String formattedXpath = String.format(dynamicActionRowTemplate, targetEmail);
        By dynamicButtonLocator = By.xpath(formattedXpath);
        
        System.out.println("[PAGE-ACTION] Resolving relative lookup selector: " + formattedXpath);
        
        // 2. Enforce structural synchronization guards before triggering interaction events
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(dynamicButtonLocator));
        
        // 3. Fire interaction using your robust framework JS fallback layer
        element.clickElement(dynamicButtonLocator);
    }

    /**
     * Validates that a user row has been successfully removed from the DOM layout.
     * @param targetEmail The email string that should no longer exist.
     * @return true if the element completely vanishes from the screen view.
     */
    public boolean verifyRowIsDeleted(String targetEmail) {
         By cellLocator = By.xpath("//table[@id='table1']//td[text()='" + targetEmail + "']");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(cellLocator));
        } catch (Exception e) {
            return false;
        }
    }
        public void editUserByEmailRowAnchor(String targetEmail) {
             
        	 String formattedXpath = String.format(dynamicActionEdit, targetEmail);
             By editLinkLocator = By.xpath(formattedXpath);
             
        	element.getWait().until(ExpectedConditions.visibilityOfElementLocated(editLinkLocator));
        	element.clickElement(editLinkLocator);
    }

        /**
         * Checks if a specific student is present on the CURRENT active page view screen.
         */
        public boolean isStudentPresentOnCurrentPage(String studentName) {
            By specificStudentXpath = By.xpath("//table[contains(@class,'table')]/tbody/tr/td[text()='" + studentName + "']");
            // Returns true if size is greater than 0, completely avoiding throw crashes!
            return driver.findElements(specificStudentXpath).size() > 0;
        }

        /**
         * Clicks the "Next" button if it is enabled to load the next set of data rows.
         * @return true if the click was successful, false if the button is disabled (last page reached).
         */
        public boolean clickNextPageIfEnabled() {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nextLiElement = driver.findElement(parentNextButtonLi);
            
            if (nextLiElement.getAttribute("class").contains("disabled")) {
                System.out.println("[PAGE-INFO] Reached the final page. Cannot click Next.");
                return false;
            }
            
            // 🚀 THE FIX STEP 1: Capture a reference to an existing old row BEFORE clicking!
            // This gives Selenium an active memory pointer to track for staleness.
            WebElement oldRowReference = driver.findElement(studentRows);
            
            // Explicit sync wait before triggering the navigation click
            wait.until(ExpectedConditions.elementToBeClickable(nextButton));
            element.clickElement(nextButton);
            
            // 🚀 THE FIX STEP 2: Pass that pre-captured old row object into the staleness check!
            // The driver will pause here until this specific old row completely vanishes from the DOM.
            wait.until(ExpectedConditions.stalenessOf(oldRowReference));
            
            System.out.println("[PAGE-ACTION] Table page advanced successfully. Old rows are stale.");
            return true;
        }
        
        // TABLE ....................
        public void clickColumnHeader() {
            element.clickElement(lastNameHeader);
        }

        public List<String> getColumnDataValues() {
            // 🚀 Calls your new element actions multi-element collection block safely!
            List<WebElement> cellElements = element.getElements(lastNameColumnCells);
            List<String> cellTextList = new ArrayList<>();

            for (WebElement cell : cellElements) {
                cellTextList.add(cell.getText().trim());
            }
            return cellTextList;
        }

        public boolean isRecordPresentInColumn(String targetRecord) {
            List<WebElement> cellElements = element.getElements(studentNameColumnCells);

            for (WebElement cell : cellElements) {
            	System.out.println("<<<<<<<<<" +cell.getText());

                if (cell.getText().trim().equalsIgnoreCase(targetRecord)) {
                	System.out.println("<<<<<<<<<" +cell.getText());
                    return true; 
                }
            }
            return false;
        }         
     
        public int getVisibleRowCount() {
            try {
                // 🛡️ CRITICAL SYNCHRONIZATION STEP: Wait for cell elements to populate the DOM first!
                element.waitForPresence(tableBodyCells);
                return element.getElements(tableRows).size();
            } catch (Exception e) {
                return 0;
            }
        }
        
        public boolean navigateToRowContainingStudent(String studentName) {
            // Wait for the table body to finish loading records before scanning names
            element.waitForPresence(tableBodyCells);
            
           // By studentRowLocator = By.xpath("//table[@id='example']/tbody/tr/td[text()='" + studentName + "']");
            By studentRowLocator = By.xpath("//table[@id='example']/tbody/tr/td[normalize-space(text())='" + studentName + "']");

            while (true) {
                // Check if the student exists on the active page canvas layout view sheet
                if (element.getElements(studentRowLocator).size() > 0) {
                    return true; 
                }
                
                // Handle the page boundary condition check safely
                WebElement nextLI = element.waitForPresence(nextParentLI);
                if (nextLI.getAttribute("class").contains("disabled")) {
                    return false; 
                }
                
                // Advance page view layout sheet
                element.clickElement(nextButton);
                
                // Standard explicit wait wait for the new page DOM text to refresh after clicking next
                try { Thread.sleep(600); } catch (InterruptedException e) { }
            }
        }
        
        public String getStudentAttributeByHeaderName(String studentName, String columnHeaderName) {
            boolean rowExists = navigateToRowContainingStudent(studentName);
            if (!rowExists) {
                throw new RuntimeException("[AUTOMATION-ERROR] Student record profile: '" + studentName + "' was not found anywhere across the pagination sheets!");
            }
            
            // Number-free counting syntax engine
            String dynamicXPath = "//table[@id='example']/tbody/tr[td[text()='" + studentName + "']]"
                    + "/td[count(//table[@id='example']/thead/tr/th[text()='" + columnHeaderName + "']/preceding-sibling::th) + 1]";
                    
            By dynamicCellLocator = By.xpath(dynamicXPath);
            element.waitForPresence(dynamicCellLocator);
            return element.getElements(dynamicCellLocator).get(0).getText().trim();
        }
        }


