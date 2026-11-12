package com.automation.othertests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HHoverPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HHoverTest extends BaseTest{
	
	@Test(priority=1, description = "Verification of hovers and its text")
	public void verifyHover()
	{
		getDriver().switchTo().defaultContent();
		getDriver().get(ConfigReader.getProperty("base.url") + "hovers");
		
		HHoverPage hHoverPage = new HHoverPage(getDriver());
		
		int i = 1;
		
		hHoverPage.hoverInToImage(i);
		String hoverImgText = hHoverPage.getHoverImageText(i);
		hHoverPage.clickProfile(i);
		Assert.assertTrue(hoverImgText.contains("name: user"),"ISSUE IN CLICKING PROFILE");
		
	}
	
	/* @Test(priority = 2, description = "Verify mouse hover gesture reveals hidden profile content elements")
	    public void verifyMouseHoverAction() {
			getDriver().get(ConfigReader.getProperty("base.url") + "hovers");
	        HHoverPage hoverPage = new HHoverPage(getDriver());
	        
	        String extractedUserText = hoverPage.getUsernameOnHover();
	        System.out.println("------- Hover Profile Output Text: " + extractedUserText);
	        
	        Assert.assertEquals(extractedUserText, "name: user1", "CRITICAL ERROR: Hover action failed to reveal username text node!");
	    }*/

	    @Test(priority = 2, description = "Verify HTML5 element box drag-and-drop structural coordinate swapping")
	    public void verifyDragAndDropAction() {
			getDriver().get(ConfigReader.getProperty("base.url") + "drag_and_drop");
	        HHoverPage dragDropPage = new HHoverPage(getDriver());
	        
	        // Execute the physical dragging action swap
	        dragDropPage.swapColumns();
	        
	        String columnAHeader = dragDropPage.getColumnAHeaderText();
	        System.out.println("------- Drag & Drop Column A Position Text: " + columnAHeader);
	        
	        // If the swap was successful, column A will now display the text header "B"
	        Assert.assertEquals(columnAHeader, "B", "CRITICAL ERROR: Drag and drop action failed to swap structural grid columns!");
	    }

}
