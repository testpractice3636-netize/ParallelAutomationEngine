package com.automation.othertests;

import java.io.File;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.automation.otherpages.HDownloadPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HDownloadTest extends BaseTest {
    private final String targetedFile = "01_front.jpg";
    private File systemDownloadsFolder;

    private void navigateToDownload() {
        getDriver().get(ConfigReader.getProperty("base.url") + "download");
    }

    @BeforeMethod
    public void cleanupPreviousDownloads() {
        String homeUserDirectory = System.getProperty("user.home");
        systemDownloadsFolder = new File(homeUserDirectory + File.separator + "Downloads");

        if (systemDownloadsFolder.exists() && systemDownloadsFolder.isDirectory()) {
            File[] existingFiles = systemDownloadsFolder.listFiles();
            if (existingFiles != null) {
                int deleteCount = 0;
                for (File file : existingFiles) {
                    if (file.getName().contains(targetedFile)) {
                        if (file.delete()) {
                            deleteCount++;
                        }
                    }
                }
                if (deleteCount > 0) {
                    System.out.println("🧹 CLEANUP: Successfully purged " + deleteCount + " legacy matching files.");
                }
            }
        }
    }

    @Test(priority = 1, description = "Verify download lifecycle via native IO Polling")
    public void verifySecureFileDownloadLifecycle() {
        navigateToDownload();
        HDownloadPage downloadPage = new HDownloadPage(getDriver());
        
        downloadPage.downloadFileByName(targetedFile);
        
        boolean isFileDownloadedSuccessfully = false;
        int maxWaitTimeoutInSeconds = 10;
        
        // Loop polling interval: Checks every 500ms for up to 10 seconds
        for (int i = 0; i < maxWaitTimeoutInSeconds * 2; i++) {
            File[] downloadFolderFiles = systemDownloadsFolder.listFiles();
            
            if (downloadFolderFiles != null) {
                for (File currentFile : downloadFolderFiles) {
                    String actualName = currentFile.getName();
                    
                    if (actualName.contains(targetedFile) 
                            && currentFile.length() > 0 
                            && !actualName.endsWith(".crdownload") 
                            && !actualName.endsWith(".tmp")) {
                        
                        isFileDownloadedSuccessfully = true;
                        break;
                    }
                }
            }
            
            if (isFileDownloadedSuccessfully) {
                break;
            }
            
            try {
                Thread.sleep(500); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } // 🎯 Loop completes out cleanly here before checking final status!

        Assert.assertTrue(isFileDownloadedSuccessfully, 
            "[ASSERTION-FAIL] A file containing '" + targetedFile + "' failed to arrive or stream completely down to local Downloads folder!");
    }
}
