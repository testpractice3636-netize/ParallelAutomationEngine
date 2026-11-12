package com.automation.otherpages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HCalendarPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // 🎯 Preserving your live DOM locators with corrected syntax
    private final By openCalendarLink = By.xpath("//button[text()='Open calendar']");
    private final By calenderLocator = By.id("calendar-header");
    private final By monthYearHeaderLabel = By.xpath(".//strong[@id='calendar-month']");
    private final By nextMonthButton = By.xpath(".//button[@id='next-month']"); // Fixed bracket typo and added dot-slash style
    private final By appointmentFieldInput = By.id("widget-date");

    public HCalendarPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectFutureDate(String targetMonthYear, String targetDay) {
        // Step 1: Open the calendar view
        wait.until(ExpectedConditions.elementToBeClickable(openCalendarLink)).click();
        
        // Scope onto the master header box container element
        WebElement calenderHeaderBox = wait.until(ExpectedConditions.visibilityOfElementLocated(calenderLocator));
        
        // 🔄 Step 2: The Month Navigation Loop (Fixed to read live text on every loop spin)
        while (!calenderHeaderBox.findElement(monthYearHeaderLabel).getText().trim().equalsIgnoreCase(targetMonthYear)) {
            
            // Search for the next button from the master box container, NOT the text label element!
            calenderHeaderBox.findElement(nextMonthButton).click();
            
            // Small timing gap to allow the calendar animation slide to render
            try { Thread.sleep(300); } catch (InterruptedException e) {}
        }   
        try { Thread.sleep(300); } catch (InterruptedException e) {}
        // 🚀 Step 3: Proceeding with Dynamic Day Coordinate Strike!
        // We target the active cells directly using the index string template approach
       // String directDayXpath = String.format("//table[@class='calendar-table']//td[not(contains(@class, 'muted'))][%s]", targetDay);
        String targetDayXpath = String.format("(//table//td)[%s]", targetDay);
        //String targetDayXpath = String.format(".//td[contains(text(), '%s') and text()!='']", targetDay);
        //String targetDayXpath = String.format("(.//td[not(contains(@class,'muted')) and text()!=''])[%s]", targetDay);


     // Use a clean explicit wait directly on the calculated index coordinate path
     WebElement targetDayElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(targetDayXpath)));
     targetDayElement.click();
        
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    public String getSelectedDateValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(appointmentFieldInput))
                   .getAttribute("value")
                   .trim();
    }
}
