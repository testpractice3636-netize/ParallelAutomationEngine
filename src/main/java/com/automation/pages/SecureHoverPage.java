package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SecureHoverPage {
    private WebDriver driver;
    private ElementActions elementActions;
    
    // 🎯 FIX: Declare these as plain text Strings so Java can format them dynamically!
    private final String dynamicImageXpath   = "(//div[@class='figure']/img)[%d]";
    private final String dynamicCaptionXpath = "(//div[@class='figcaption']/h5)[%d]";
    private final String dynamicLinkXpath    = "(//div[@class='figcaption']/a)[%d]";

    // Profile Landing Page Identifier
    private final By profileHeaderText = By.tagName("h1");
    
    public SecureHoverPage(WebDriver driver) {
        this.driver = driver;
        this.elementActions = new ElementActions(driver);
    }

    public void hoverOverProfile(int profileIndex) {
        // 🚀 Now String.format takes a raw text String and builds the By locator perfectly!
        By imageLocator = By.xpath(String.format(dynamicImageXpath, profileIndex));
        WebElement imgElement = elementActions.waitForVisibility(imageLocator);
        
        Actions actions = new Actions(driver);
        actions.moveToElement(imgElement).perform();
        System.out.println("[HOVER-ENGINE] Mouse pointer hovered over profile card wrapper index: " + profileIndex);
    }

    public String getProfileCaptionText(int profileIndex) {
        By captionLocator = By.xpath(String.format(dynamicCaptionXpath, profileIndex));
        return elementActions.waitForVisibility(captionLocator).getText().trim();
    }
    
    public void clickProfileLink(int profileIndex) {
        By linkLocator = By.xpath(String.format(dynamicLinkXpath, profileIndex));
        System.out.println("((((((((((WHAT IS INSIDE 1)))))))))))");
        elementActions.clickElement(linkLocator);
        System.out.println("[HOVER-ENGINE] Clicked dynamic View Profile link asset index: " + profileIndex);
    }

    public String getProfilePageHeaderText() {
        System.out.println("((((((((((WHAT IS INSIDE 1.1)))))))))))");

        return elementActions.waitForVisibility(profileHeaderText).getText().trim();
    }
}
