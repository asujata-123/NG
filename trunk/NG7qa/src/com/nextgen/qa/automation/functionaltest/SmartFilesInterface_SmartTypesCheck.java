package com.nextgen.qa.automation.functionaltest;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.nextgen.qa.automation.pages.*;
import com.nextgen.qa.automation.smoketest.*;
import com.nextgen.qa.automation.toolbox.*;
import com.thoughtworks.selenium.Wait;


public class SmartFilesInterface_SmartTypesCheck extends NG7TestCase {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	public BufferedWriter artifact;
	public MainPage mp;
	public LoginPage lp;
	public SmartFilesPage sfp;
	
	@Before
	public void setUp() throws Exception {
		NG7TestCase.testName = "SmartFilesInterface_SmartTypesCheck";
		NG7TestCase.tester = "Maria Garcia-bodoh"; 
		artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp); // MGB 5/5/2014
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	    driver.manage().window().maximize();
	}
	  
	@Test
	public void test() throws Exception {
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		String preReq = null;
		String reqFields = "";
			
		// Pages browsed 
		mp  = new MainPage(driver, "mainPage.txt");
	    lp = new LoginPage(driver, "login.txt");
	    sfp = new SmartFilesPage(driver, "smartfilespage.txt");
	       	 
	    // Test data:
	    String organization = "Shared Cloud;GenHealth Enterprises, Inc. (GHEnt)"; 
		
	    // smartType black list: skip test for these smartType values:
	    String smartTypeBlackList = "@ 1099 Misc Code Category @"
	    		+ "@ Analytical Classification - HMG @"
	    		+ "@ Cost Centers - HMG @"
	    		+ "@ GLAccount Enterprise Level @"
	    		+ "@ Item Catalog - HMG @"
	    		+ "@ Journal Entry Source Code @"
	    		+ "@ Shopping List @"
	    		+ "@ Sub Account - HMG @"
	    		+ "@ Unspsc Codes @"
	    		+ "@ Vendor - HMG @"
	    		+ "@ Vendor Catalog @"
	    		+ "@ Smarttag Group @";
	    
		// Log in to main page
		lp.launchApplication("NG7");
        lp.login();
        GeneralMethods.delay(5000);
		currStepResult = lp.verifyLogin() ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Log in to "+GeneralMethods.getDeployment(), currStepResult); // MGB 5/5/2014
		preReq = currStepResult;
		
		// Run in local stress loop to make sure the test is robust
		for (int i = 1; i <= localStressLoop; i++){ // localStressLoop is controlled via the config.properties file
			if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
			System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
			
			// test step
			currStepResult = (preReq.equals("Fail")) ? "Blocked" :
				((driver.getTitle().equals("NG7")) ? "Pass" : "Fail");
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify that 'NG7' is in the title", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				mp.selectHomeMenuItem("SmartFiles") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Launch SmartFiles tab from Menu", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.smartfilesTabControl.isDisplayed() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check SmartFiles tab opened", currStepResult);
			String smartFilesOpened = currStepResult;
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.selectOrganization(organization) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select organization "+organization, currStepResult);
					  				
						
			// test step
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				GeneralMethods.checkIsNotEmpty(sfp.organizationList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check organization list is not empty", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.selectOrganization("GenHealth Enterprises, Inc. (GHEnt)") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select organization 'GenHealth Enterprises Inc'", currStepResult);
			
			String sfType = "";
			String sfOpens = "";
			WebElement smartFileType = null;
			
			for (int i1 = 0; i1 < sfp.smartFormTypesList.size(); i1++){
				
				smartFileType = sfp.smartFormTypesList.get(i1);
								
				try{ sfType = smartFileType.getText(); }
				catch (Exception e) { };
				
				// Skip the type if it is on the black list
				if (smartTypeBlackList.contains("@ "+sfType+" @")) continue;
				//Artifact.VerifyWriteToArtifactS(artifact, sfType, "List"); //create a list of smarttypes
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.ClickButton(smartFileType) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on SmartFile type "+sfType, currStepResult);
								// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.ClickButton(sfp.addSmartItemButton) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on Add SmartItem button SmartFile type "+sfType, currStepResult);
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.checkIsDisplayed(sfp.openSmartForm) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the Smart Form opens SmartFile type "+sfType, currStepResult);
				sfOpens = currStepResult;
				
				// test step
				GeneralMethods.delay(eventDelay);
				currStepResult = sfOpens.equals("Pass") == false ? "Blocked" :
					sfp.ClickSmartFormTab(sfp.openSmartFormGeneralTab, "Smart Form General tab") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click General Tab of SmartForm type "+sfType, currStepResult);			

				// test step
				GeneralMethods.delay(eventDelay);
				currStepResult = sfOpens.equals("Pass") == false ? "Blocked" :
					sfp.ClickSmartFormTab(sfp.openSmartFormCategoriesTab, "Smart Form Categories tab") ? "Pass" : "Fail";
				//Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Categories Tab of SmartForm type "+sfType, currStepResult);
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click SmartTags Tab of SmartForm type "+sfType, currStepResult);
				
				// test step
				currStepResult = sfOpens.equals("Pass") == false ? "Blocked" :
					GeneralMethods.ClickButton(sfp.openSmartFormCancelButton) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click 'Cancel' on the SmartForm for SmartFile type "+sfType, currStepResult);
				
				// test step
				GeneralMethods.delay(5000);
				WebElement sf = sfp.openSmartForm;
				if (GeneralMethods.checkIsDisplayed(sf)) {
					try {sfp.forceCloseSmartForm(smartFileType.getText());}
					catch (Exception e) {}
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+tempWorkaround+"Force close smartform "+smartFileType.getText(), "Fail"+tempWorkaround+"Force close smartform");
				}
				
				sfOpens = "";
				sfType = "";
				currStepResult = "Pass";
				
			}
			
			// Cleanup
			mp.closeAllOpenTabs();
			
		} // stress loop closing bracket
		
		mp.logOutUser();
		Artifact.CloseArtifact(artifact);
	}
	
	@After
	public void tearDown() throws Exception {	
		
		// Cleanup
		driver.quit();
		driver = null;
	}
}
