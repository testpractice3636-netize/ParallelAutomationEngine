package com.automation.tests;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;


public class BaseTest {

    // ThreadLocal containers to prevent browser cross-talk during parallel execution
	private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    private static ThreadLocal<String> threadBrowserName = new ThreadLocal<>();
    
    // 🔑 1. THE CRITICAL GETTER: Your tests need this method to get their thread driver!
    public static WebDriver getDriver() {
        return threadDriver.get();
    }
    
    // Helper method for the reporting manager to grab the thread browser name
    public static String getBrowserName() {
        return threadBrowserName.get();
    }
    
        @BeforeMethod
        @Parameters("browserType") 
        public void setupBrowser(@Optional("chrome") String browser) {
//        public void setupBrowser(String browser) {
            threadBrowserName.set(browser);
            
            System.out.println("--- Framework Initialization Engine ---");
            System.out.println("[BASE TEST-INFO] Booting up parallel engine execution instance for: " + browser);
            
            WebDriver driverInstance = null;
            if (browser.equalsIgnoreCase("chrome")) {
                //#### Working code remove
            	//System.setProperty("webdriver.chrome.driver", "C:\\QA_Automation\\chromedriver-win64\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);

                options.setExperimentalOption("detach", true);
                
                // 🔑 THE POP-UP BYPASS ARGUMENTS FOR CHROME
                options.addArguments("--disable-popup-blocking"); // Disables automated pop-up blocks
                options.addArguments("--disable-blink-features=AutomationControlled"); // Hides "Chrome is being controlled by automation" flags
                options.addArguments("--disable-infobars");
                
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);

                driverInstance = new ChromeDriver(options);
                
            } else if (browser.trim().equalsIgnoreCase("edge")) {
                EdgeOptions options = new EdgeOptions();
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);

                // 🔑 THE POP-UP BYPASS ARGUMENTS FOR EDGE
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--disable-infobars");
                
                driverInstance = new EdgeDriver(options);
            } else {
                // 🛑 THE FAIL-FAST SAFETY GATE: Tells you instantly if an unhandled browser type string bypassed the setup!
                throw new IllegalArgumentException("CRITICAL ERROR: Unhandled browser configuration value injected: " + browser);
            }

            if (driverInstance != null) {
                driverInstance.manage().window().maximize();
                threadDriver.set(driverInstance);
            }
            
        }
        
       /* // 🚀 Temporary verification test to force TestNG to trigger the browsers
        @org.testng.annotations.Test
        public void verifyParallelLaunch() {
            System.out.println("[EXECUTION-INFO] Thread is actively running a live test scenario on: " + getBrowserName());
            getDriver().get("https://expandtesting.com");
           
            // Simple assertion gate to ensure our page loaded cleanly
            org.testng.Assert.assertTrue(getDriver().getCurrentUrl().contains("expandtesting"));
        } */
        
        @AfterMethod
        public void tearDownBrowser() {
            WebDriver activeDriver = threadDriver.get();
            if (activeDriver != null) {
                activeDriver.quit();
                System.out.println("[BASE-INFO] Browser Instance Terminated Cleanly.");
            }
            // Free up memory slots when thread closes
            threadDriver.remove();
            threadBrowserName.remove();
        }
    
}
