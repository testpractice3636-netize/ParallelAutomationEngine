package com.automation.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.automation.pages.MultiWindow;
import com.automation.pages.NavigationPage;
import org.testng.annotations.Test;


public class MultiWindowTest extends BaseTest {

	@Test
    public void verifyMultiWindowTab() throws InterruptedException {
       try
       {
		// Fetch ThreadLocal isolated driver cleanly from BaseTest configuration
        System.out.println("------ ENTRY 2: Test Method Successfully Invoked ------");
        NavigationPage navigationPage = new NavigationPage(getDriver());
        System.out.println("------ ENTRY 3: Navigation Elements Initialized ------");
        navigationPage.contentPageLink();

        navigationPage.multiWindowLink(getDriver());
        
        System.out.println("------ ENTRY 4: Centralized Page Navigation Complete ------");
        		
        MultiWindow multiWindow = new MultiWindow(getDriver());
        System.out.println("------ ENTRY 5: Centralized Page Navigation Complete ------");

        
        boolean isHeaderVerified = multiWindow.verifyMultipleWindow();
        
        // Explicit business validation checkpoint to ensure zero false passes
        Assert.assertTrue(isHeaderVerified, "[ASSERTION-FAIL] New tab text checkpoint failed to render on screen!");
        System.out.println("[TEST-SUCCESS] Multi-window handle verification pipeline passed cleanly.");
        
    } catch (Exception e) {
        System.err.println("[TEST-ERROR] Crash encountered inside multi-window execution thread!");
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }
	}
}