package com.automation.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtility {

    public static String captureScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            return "Driver instance missing. Screenshot skipped.";
        }
        
        // 🕰️ Generate a unique timestamp so files never overwrite each other
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        
        // 📂 Dynamlically construct the project directory storage path
        String directoryPath = System.getProperty("user.dir") + "/target/surefire-reports/screenshots/";
        String completeFilePath = directoryPath + fileName;

        try {
            // 📸 Uncover browser camera engine layer and stream the image array
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File targetDestination = new File(completeFilePath);
            
            // 💾 Copy the floating image from temporary memory onto your hard disk
            FileUtils.copyFile(sourceFile, targetDestination);
            return completeFilePath;
        } catch (IOException e) {
            System.err.println("[UTILITY-ERROR] Critical failure copying screenshot to disk: " + e.getMessage());
            return "";
        }
    }
}
