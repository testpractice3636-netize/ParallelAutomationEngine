package com.automation.othertests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.otherpages.HFileUploadPage;
import com.automation.tests.BaseTest;
import com.automation.utils.ConfigReader;

public class HFileUploadTest extends BaseTest{
	
	@Test(priority=1, description="Verification of upload file")
	public void verifyUploadFile()
	{
		getDriver().get(ConfigReader.getProperty("base.url")+"upload");
		
		String filePath =System.getProperty("user.home")+ File.separator + "Downloads" + File.separator + "UploadTest.txt";;
		
		//String filePath = base + File.separator + "Downloads" + File.separator + "UploadTest.txt";

		
		HFileUploadPage hFileUpload = new HFileUploadPage(getDriver());
		
		hFileUpload.chooseFile(filePath);
		String uploadSucessMsg = hFileUpload.uploadFile();
		
		Assert.assertTrue(uploadSucessMsg.contains("File Uploaded!"), "ISUEE WITH UPLOADING FILE");
		
	}

}
