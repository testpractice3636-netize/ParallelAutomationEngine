package com.automation.tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.lang.reflect.Field;
//import com.automation.utils.ScreenshotUtility;
import com.automation.tests.BaseTest;
import com.automation.utils.ScreenshotUtility;

public class TestFailureListener implements ITestListener {

    // 🟢 Milestone 1: Triggers the exact millisecond any test starts running
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("[MONITOR] Test started: " + result.getName());
    }

    // 🔵 Milestone 2: Triggers automatically only if a test completes with NO errors
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("[MONITOR] SUCCESS! Test passed cleanly: " + result.getName());
    }

    // 🔴 Milestone 3: THE TARGET TRIGGER! Triggers instantly if a test throws an error or fails an assert
    @SuppressWarnings("unchecked")
    @Override
    public void onTestFailure(ITestResult result) {
    	String testName = result.getName();
        System.err.println("[MONITOR] >>> ALERT: Test failed: " + testName);

        // 🔑 THE SIMPLE FIX: Use your public static getter method to pull the driver safely!
        WebDriver activeDriver = BaseTest.getDriver();

        if (activeDriver != null) {
            System.out.println("[MONITOR] ThreadLocal browser instance successfully isolated via getDriver().");
            
            // 📸 Execute our screenshot capture utility
            String savedPath = ScreenshotUtility.captureScreenshot(activeDriver, testName);
            System.out.println("[MONITOR] Automated failure snapshot successfully written to disk at: " + savedPath);
        } else {
            System.err.println("[MONITOR-ERROR] The active driver session is NULL. No browser instance was open.");
        }
        //UNDERSTANDING PURPOSE
        /*
        try {
            // 3. REFLECTION: Look inside the class to find the variable named "driver"
            driverField = currentTestClassInstance.getClass().getDeclaredField("driver");
            
            // 4. BYPASS PRIVACY: Force Java to unlock the field if it is marked private
            driverField.setAccessible(true);
            
            // 5. READ VALUE: Fetch the object value stored inside that variable
            Object driverValue = driverField.get(currentTestClassInstance);
            
            // 6. UNPACK THREADLOCAL: Check if it's wrapped in ThreadLocal and unpack it safely
            if (driverValue instanceof ThreadLocal) {
                System.out.println("[MONITOR] ThreadLocal driver wrapper detected. Unpacking active thread slot...");
                activeDriver = ((ThreadLocal<WebDriver>) driverValue).get();
            } else if (driverValue instanceof WebDriver) {
                System.out.println("[MONITOR] Standard WebDriver instance detected.");
                activeDriver = (WebDriver) driverValue;
            }

        } catch (NoSuchFieldException e) {
            System.err.println("[MONITOR-ERROR] Could not find a field named 'driver' in " + currentTestClassInstance.getClass().getSimpleName());
        } catch (IllegalAccessException e) {
            System.err.println("[MONITOR-ERROR] Security policy blocked reading the driver field.");
        }*/

        // 7. VERIFY EXTRACTED DRIVER: Ensure we have a working browser session
        if (activeDriver != null) {
            System.out.println("[MONITOR] Driver successfully isolated for thread: " + Thread.currentThread().getId());
            
            // 📸 THE LINKING STEP: Call the utility class and pass our isolated details!
            String savedPath = ScreenshotUtility.captureScreenshot(activeDriver, testName);
            
            // Print the final delivery verification trace to your log console
            System.out.println("[MONITOR] Automated failure snapshot successfully written to disk at: " + savedPath);
        } else {
            System.err.println("[MONITOR-ERROR] Isolated driver is NULL. Cannot capture screenshot.");
        }
    }

    // 🟡 Milestone 4: Triggers if a test is skipped due to a dependency block
    @Override
    public void onTestSkipped(ITestResult result) {}

    // Required boilerplate methods for the interface setup
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
