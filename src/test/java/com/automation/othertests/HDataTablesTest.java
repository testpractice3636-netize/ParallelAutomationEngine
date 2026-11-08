package com.automation.othertests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.otherpages.HDataTablesPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HDataTablesTest extends BaseTest {

    @Test(priority = 1, description = "Verify dynamic web table parsing capabilities")
    public void verifyGridDataExtraction() {
        getDriver().get(ConfigReader.getProperty("base.url") + "tables");
        
        HDataTablesPage tablePage = new HDataTablesPage(getDriver());
        
        String dueAmount = tablePage.getUserDueBalanceByEmail("fbach@yahoo.com", "Due");

        Assert.assertEquals(dueAmount, "$51.00", "Grid balance data mismatch detected!");
        
        tablePage.deleteUserByLastName("Smith");
        
        Assert.assertTrue(getDriver().getCurrentUrl().contains("#delete"), "Action click failed to register hash redirection.");
    
    }
}
