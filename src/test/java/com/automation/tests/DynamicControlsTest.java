package com.automation.tests;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.automation.pages.DynamicControlsPage;
import com.automation.utils.ConfigReader;
import com.automation.utils.ExcelReader;

public class DynamicControlsTest extends BaseTest {

	private void navigateToDynamicControls() {
        getDriver().switchTo().defaultContent();
        getDriver().get(ConfigReader.getProperty("base.url") + "dynamic-controls");
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
            .until(ExpectedConditions.urlContains("/dynamic-controls"));
    }
		
    @Test(priority = 1, description = "Verify checkbox lifecycle structurally using unified message streams")
    public void verifyAsynchronousCheckboxStateEngine() {
        navigateToDynamicControls();
        DynamicControlsPage dynamicControlsPage = new DynamicControlsPage(getDriver());

        // Step 1: Baseline Check
        Assert.assertEquals(dynamicControlsPage.getRemoveAddButtonText(), "Remove");
        Assert.assertTrue(dynamicControlsPage.isCheckboxPresent());

        // Step 2: Trigger removal and assert the dynamic message text stream
        dynamicControlsPage.clickRemoveAddButton();
        Assert.assertEquals(dynamicControlsPage.getActionMessageText(), "It's gone!"); // ⚡ Live verification
        Assert.assertEquals(dynamicControlsPage.getRemoveAddButtonText(), "Add");
        Assert.assertFalse(dynamicControlsPage.isCheckboxPresent());

        // Step 3: Trigger adding back and assert the text reverting
        dynamicControlsPage.clickRemoveAddButton();
        Assert.assertEquals(dynamicControlsPage.getActionMessageText(), "It's back!");  // ⚡ Live verification
        Assert.assertEquals(dynamicControlsPage.getRemoveAddButtonText(), "Remove");
        Assert.assertTrue(dynamicControlsPage.isCheckboxPresent());
    }

    @Test(priority = 2, description = "Verify input field toggle structurally using unified message streams")
    public void verifyAsynchronousInputToggleEngine() {
        navigateToDynamicControls();
        DynamicControlsPage dynamicControlsPage = new DynamicControlsPage(getDriver());

        // Step 1: Baseline Check
        Assert.assertFalse(dynamicControlsPage.isInputFieldEnabled());
        Assert.assertEquals(dynamicControlsPage.getEnableDisableButtonText(), "Enable");

        // Step 2: Trigger activation and assert changes
        dynamicControlsPage.clickEnableDisableButton();
        Assert.assertEquals(dynamicControlsPage.getActionMessageText(), "It's enabled!");
        Assert.assertTrue(dynamicControlsPage.isInputFieldEnabled());
        Assert.assertEquals(dynamicControlsPage.getEnableDisableButtonText(), "Disable");

        // Step 3: Interact with the field
        dynamicControlsPage.enterTextInInputField("Structural Design Pattern Success");

        // Step 4: Deactivate field and verify final state
        dynamicControlsPage.clickEnableDisableButton();
        Assert.assertEquals(dynamicControlsPage.getActionMessageText(), "It's disabled!");
        Assert.assertFalse(dynamicControlsPage.isInputFieldEnabled());
        Assert.assertEquals(dynamicControlsPage.getEnableDisableButtonText(), "Enable");
    }
}
