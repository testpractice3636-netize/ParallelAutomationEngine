package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.pages.CheckboxesPage;
import com.automation.utils.ConfigReader;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class CheckboxesTest extends BaseTest {

    private void navigateToCheckboxesModule() {
        getDriver().switchTo().defaultContent();
        String targetUrl = ConfigReader.getProperty("base.url") + "checkboxes";
        getDriver().get(targetUrl);
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
            .until(ExpectedConditions.urlContains("/checkboxes"));
    }

    @Test(priority = 1, description = "Verify baseline rendering states and total count of check operations")
    public void verifyDefaultCheckboxLayoutMatrix() {
        navigateToCheckboxesModule();
        CheckboxesPage checkboxesPage = new CheckboxesPage(getDriver());

        // Validate total items matching page layout
        Assert.assertEquals(checkboxesPage.getCheckboxesCount(), 2, "[ASSERTION-FAIL] Checkboxes missing from layout view!");

        // Validate practice page initialization states (Box 1 unchecked, Box 2 checked)
        Assert.assertFalse(checkboxesPage.isCheckboxSelected(0), "[ASSERTION-FAIL] Checkbox 1 should be unchecked by default!");
        Assert.assertTrue(checkboxesPage.isCheckboxSelected(1), "[ASSERTION-FAIL] Checkbox 2 should be checked by default!");
    }

    @Test(priority = 2, description = "Verify execution engine can toggle and select unchecked boxes seamlessly")
    public void verifyCheckboxSelectionToggles() {
        navigateToCheckboxesModule();
        CheckboxesPage checkboxesPage = new CheckboxesPage(getDriver());

        // Toggle states cleanly
        checkboxesPage.setCheckboxState(0, true);
        Assert.assertTrue(checkboxesPage.isCheckboxSelected(0), "[ASSERTION-FAIL] Checkbox 1 failed to select!");

        checkboxesPage.setCheckboxState(1, false);
        Assert.assertFalse(checkboxesPage.isCheckboxSelected(1), "[ASSERTION-FAIL] Checkbox 2 failed to deselect!");
    }
}
