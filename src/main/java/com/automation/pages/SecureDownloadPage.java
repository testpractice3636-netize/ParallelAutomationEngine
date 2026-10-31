package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SecureDownloadPage {

    private final WebDriver driver;
    private final ElementActions element;

    public SecureDownloadPage(WebDriver driver) {
        this.driver = driver;
        this.element = new ElementActions(driver);
    }

    /**
     * Finds the dynamic file, highlights it visually for debugging, and triggers the download.
     */
    public void clickDownloadFile(String fileName) {
        By dynamicFileLocator = By.xpath("//a[contains(@data-testid, '" + fileName + "')]");
        WebElement fileElement = element.waitForPresence(dynamicFileLocator);

        // 🎨 VISUAL CONFIRMATION FLASH: Highlights the element right before the click!
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='3px solid orange';", fileElement);
            Thread.sleep(500); // Small pause so your eyes can see the flash highlight
        } catch (Exception e) {
            // Silence highlight errors so it doesn't break the main execution flow
        }

        element.clickElement(dynamicFileLocator);
    }
      }
