package com.automation.tests;

import java.io.File;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.automation.pages.SecureDownloadPage;
import com.automation.utils.ConfigReader;

public class SecureDownloadTest extends BaseTest {

    private final String targetedFile = "some-file.txt"; // Kept globally for setup visibility
    private File systemDownloadsFolder;

    private void navigateToSecureDownloadModule() {
        getDriver().switchTo().defaultContent();
        
        String rawBaseUrl = ConfigReader.getProperty("base.url"); 
        String username = ConfigReader.getProperty("secure.user");   
        String password = ConfigReader.getProperty("secure.pass");   

        String authenticatedUrl = rawBaseUrl.replace("https://", "https://" + username + ":" + password + "@") + "download-secure"; 
        
        getDriver().get(authenticatedUrl);
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
            .until(ExpectedConditions.urlContains("/download-secure"));
    }

    /**
     * 🧹 ADVANCED CLEANER LAYER: Automatically runs before the test starts.
     * Iterates through the folder to wipe out ALL previous matching files or numbered duplicates.
     */
    @BeforeMethod
    public void cleanupPreviousDownloads() {
        String homeUserDirectory = System.getProperty("user.home");
        systemDownloadsFolder = new File(homeUserDirectory + File.separator + "Downloads");

        if (systemDownloadsFolder != null && systemDownloadsFolder.exists() && systemDownloadsFolder.isDirectory()) {
            File[] existingFiles = systemDownloadsFolder.listFiles();
            if (existingFiles != null) {
                int deleteCount = 0;
                for (File file : existingFiles) {
                    // Wipes out 'some-file.txt', 'some-file (1).txt', etc.
                    if ((file.getName().contains(targetedFile))) {
                        if (file.delete()) {
                            deleteCount++;
                        }
                    }
                }
                if (deleteCount > 0) {
                    System.out.println("🧹 PRE-TEST CLEANUP: Successfully deleted " + deleteCount + " old matching files from Downloads.");
                }
            }
        }
    }
	
    @Test(priority = 1, description = "Verify file click execution and physical system folder arrival validation")
    public void verifySecureFileDownloadLifecycle() {
        navigateToSecureDownloadModule();
        SecureDownloadPage securePage = new SecureDownloadPage(getDriver());
        
        // Trigger the download click action
        securePage.clickDownloadFile(targetedFile);
        
        // 🔍 THE PARTIAL MATCH VERIFICATION ENGINE
        boolean isFileDownloadedSuccessfully = false;
        int maxWaitTimeoutInSeconds = 10;
        File finalMatchedFile = null;
        
        for (int i = 0; i < maxWaitTimeoutInSeconds * 2; i++) {
            File[] downloadFolderFiles = systemDownloadsFolder.listFiles();
            
            if (downloadFolderFiles != null) {
                for (File currentFile : downloadFolderFiles) {
                    String actualName = currentFile.getName();
                    
                    // 1. Check if the file name CONTAINS your target string
                    // 2. Ensure it is fully written (length > 0)
                    // 3. Ensure it's not a temporary browser stream (.crdownload or .tmp)
                    if (actualName.contains(targetedFile) 
                            && currentFile.length() > 0 
                            && !actualName.endsWith(".crdownload") 
                            && !actualName.endsWith(".tmp")) {
                        
                        isFileDownloadedSuccessfully = true;
                        finalMatchedFile = currentFile;
                        break;
                    }
                }
            }
            
            if (isFileDownloadedSuccessfully) {
                break;
            }
            
            try {
                Thread.sleep(500); // Check again every 500 milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Final Core Assertion Validation
        Assert.assertTrue(isFileDownloadedSuccessfully, 
            "[ASSERTION-FAIL] A file containing '" + targetedFile + "' failed to arrive inside the local Downloads folder!");
        
        System.out.println("SUCCESS: Verified fresh file arrived safely! Actual Name: " 
            + finalMatchedFile.getName() + " | Size: " + finalMatchedFile.length() + " bytes.");
    }
}
