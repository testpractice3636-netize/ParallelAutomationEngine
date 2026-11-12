package com.automation.utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.automation.tests.BaseTest;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.ScreenshotUtility;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentReportManager.getReporter();
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        ExtentTest test = extent.createTest(testName, description);
        testThread.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().log(Status.PASS, "Test Passed Successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testThread.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());

        // Fetch active driver instance from BaseTest
        Object testInstance = result.getInstance();
        WebDriver driver = ((BaseTest) testInstance).getDriver();

        // Capture screenshot using your existing utility and attach to report
        String screenshotPath = ScreenshotUtility.captureScreenshot(driver, result.getMethod().getMethodName());
        testThread.get().addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().log(Status.SKIP, "Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush(); // Crucial: writes all buffered logs into the HTML file
        }
    }
}
