package com.automation.othertests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HCalendarPage;
import com.automation.tests.BaseTest;

public class HCalendarTest extends BaseTest {

    @Test(priority = 1, description = "Verify live application calendar widget navigation and selection mechanics")
    public void verifyLiveCalendarSelection() {
        // Navigate to the absolute live environment URL path
        getDriver().get("https://the-internet.herokuapp.com/date_pickers");
        
        HCalendarPage calendarPage = new HCalendarPage(getDriver());
        
        // Let's execute selection logic for Christmas Day 2026!
        calendarPage.selectFutureDate("December 2026", "25");
        
        // Verify the application field updated its display layout value correctly
        String actualSelectedDate = calendarPage.getSelectedDateValue();
        System.out.println("------- Live App Date Output Value: " + actualSelectedDate);
        
        Assert.assertEquals(actualSelectedDate, "2026-12-24", "The calendar selection value mismatch error!");
    }
}
