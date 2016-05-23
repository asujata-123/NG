package com.nextgen.qa.automation.Sandbox;

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
import org.openqa.selenium.chrome.ChromeOptions;

import com.nextgen.qa.automation.pages.*;
import com.nextgen.qa.automation.smoketest.*;
import com.nextgen.qa.automation.toolbox.*;
import com.thoughtworks.selenium.Wait;

public class SmartFilesInterface_traverse extends NG7TestCase {
	  
	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "_testCaseTemplate";	
		NG7TestCase.tester = "Maria Garcia-Bodoh";
		
		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		MainPage mp = new MainPage(driver, "mainpage");
		LoginPage lp = new LoginPage(driver, "loginpage");
		PatientDashboardPage pdb = new PatientDashboardPage(driver, "patientdashboard.txt");
	    ErrorPage ep = new ErrorPage(driver, "errorpage");
	    SmartFilesPage sfp = new SmartFilesPage(driver, "smartfilespage");
		
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		String preReq = null;
		String reqFields = "";		
		
		// Log in to main page
        lp.launchApplication("NG7");
     	lp.login();
     	
     	// test step
     	currStepResult=	mp.handleWelcome("Staff Layout") ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"If layout selector comes up select Staff Layout", currStepResult);	
		preReq = currStepResult;
     	
     	String loginSuccessCode = mp.checkLoginSucceeded();
        currStepResult = loginSuccessCode.equals("00") ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Log in to NG7", currStepResult); // MGB 5/5/2014
		preReq = currStepResult;

		// Test data
		String topLocationNode = mp.getTopNodeText();
		String patientName = AutomationSettings.getTestDataItem("patientName"+NG7TestCase.orgSelect);
		String location = topLocationNode + ";" +NG7TestCase.orgString;
		String locationBackup = topLocationNode + ";" +AutomationSettings.getTestDataItem("organization1");
		String patientNameTypo = AutomationSettings.getTestDataItem("patientNameTypo"+NG7TestCase.orgSelect);
		String patientNameNoTypo = AutomationSettings.getTestDataItem("patientNameNoTypo"+NG7TestCase.orgSelect);
		String patientName2 = AutomationSettings.getTestDataItem("patient2Name"+NG7TestCase.orgSelect);
		String medicationName1 = AutomationSettings.getTestDataItem("medication1Name"+NG7TestCase.orgSelect);
		String medicationName2 = AutomationSettings.getTestDataItem("medication2Name"+NG7TestCase.orgSelect);
		String allergyName1 = AutomationSettings.getTestDataItem("allergy1Name"+NG7TestCase.orgSelect);
		
		boolean debugMode = true;
		String [] smartFileType = {//"Reason for cancelling an appointment", 
								   "Appointment Types",
								   //"Users", // click away after selecting Roles value; BUG
								   "Resources",
								   //"Region 1 Payers",
								   "GH Payer Plans",  // text fields not populating
								   //"Activity",
								   //"Austin Medical Group Counters", 
								   //"Forms",
								   "Charge Code",
								   //"GH Contracts",
								   //"GH Provider",
								   "Calendar Profile",
								   "Document",
								   "EDStaff",
								   "ICD-10",
								   "Med Surg Dept Locations",
								   "State",
								   "Visit Status"
								   };
		
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
				
		String uid  = GeneralMethods.CreateUID();
		
		// Run in local stress loop to make sure the test is robust
		for (int i = 1; i <= localStressLoop; i++){
		    if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
		    System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
		    GeneralMethods.prepForIteration();
		    
		    // test step
		 	currStepResult = !preReq.equals("Pass") ? "Blocked" :
		 		mp.setLocation(location) ? "Pass" : "Fail";
		 	Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Set service location to "+location, currStepResult);
		 	preReq = currStepResult;	
			
			// test step
			if (!GeneralMethods.checkElementIsNotVisible(driver, mp.welcomeLayout)){
				currStepResult = (!preReq.equals("Pass")) ? "Blocked" :
					mp.handleWelcome("Staff Layout") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select Staff Layout", currStepResult);	
				preReq = currStepResult;
			}
            
			// test step
			prevStepResult = currStepResult;
			String build = mp.getBuildNumber();
			currStepResult = (prevStepResult.equals("Fail")) ? "Blocked" :
				build != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"NG7 build version number: "+build, currStepResult);
			
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
				GeneralMethods.checkIsDisplayed(sfp.smartfilesTabControl) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check SmartFiles tab opened", currStepResult);
			String smartFilesOpened = currStepResult;
			
			// test step
			GeneralMethods.delay(eventDelay*2);
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.selectOrganization(location) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select organization "+location, currStepResult);
			
			// Test variables
			WebElement smartForm = null;
			List<WebElement> requiredFields;
			List<WebElement> requiredFieldLabels;
			List<WebElement> fieldLabels;
			WebElement reqFieldIndicator;
			int size;
			int j;
			String typeName = "";
			String issueString = "";
			String issueFlag = ""; // knownIssue, WIP
			int numberOfSmartTypes;
			WebElement openSmartForm = null;
			
			// Timer variables
			long before;
			long after;
			long delay;
			String elapsed;
			
			if (debugMode == true) numberOfSmartTypes = smartFileType.length; 
			else numberOfSmartTypes = sfp.smartFormTypesList.size(); 
			//for (int i=0; i < numberOfSmartTypes; i++){
			for (int y=98; y < numberOfSmartTypes; y++){
			//for (int i=0; i < 25; i++){
				
				// Initialize timer variables
				elapsed = "";
				before = 0;
				after = 0;
				delay = 0;
				////
								
				sfp.smartfileTypeSearchField.clear();	
				
				typeName = "";
				issueString = "";
				issueFlag = ""; // knownIssue, WIP
				try { 
					if (debugMode == true) typeName = smartFileType[y]; 
					else typeName = sfp.smartFormTypesList.get(y).getText();
					
					if (typeName.equals("")) {
						System.out.println("Error when looking for smart type in array place " +y);
						continue;
					}
				} catch (Exception e) { 
					System.out.println("Exception thrown when looking for smart type in array place " +y);
					continue; 
				}
				
				// Skip the type if it is on the black list
				if (smartTypeBlackList.contains("@ "+typeName+" @")) continue;
				
				// Initialize 
				currStepResult = "Pass"; 
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					sfp.searchSmartFileType(typeName) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Search for smartType on smartType list", currStepResult);
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.ClickButton(sfp.GetSmartFileType(driver, typeName)) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+"# "+y+" : Select smartType ", currStepResult);
				
				// test step
				before = Calendar.getInstance().getTimeInMillis();
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.ClickButton(sfp.addSmartItemButton) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Click Add SmartItem button", currStepResult);
				
				openSmartForm = GeneralMethods.WaitForElement(driver, 10, typeName+": open SmartForm", sfp.openSmartForm);
				if (openSmartForm != null){
				   after = Calendar.getInstance().getTimeInMillis();
				   delay = after - before;
				   elapsed = Integer.toString((int) delay);
				}
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.checkIsDisplayed(sfp.openSmartForm) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Check that smartType opened", currStepResult+",,"+elapsed);
				
				// find list of required fields
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
				prevStepResult = currStepResult;
				if (prevStepResult.equals("Pass")){
					
					//requiredFields = GeneralMethods.FindElementsInObjHierarchy(sfp.openSmartForm, sfp.requiredFieldsCSS);
					fieldLabels = GeneralMethods.FindElementsInObjHierarchy(sfp.openSmartFormHeader, sfp.fieldLabelsCSS);
					requiredFieldLabels = GeneralMethods.FindElementsInObjHierarchy(sfp.openSmartForm, sfp.requiredFieldLabelsCSS);
				
					// Populate all the required fields
					String labelName = "";
					if ((requiredFieldLabels != null) && (requiredFieldLabels.size() > 0))  {
						//size = requiredFieldLabels.size();
						for (j = 0; j < requiredFieldLabels.size(); j++){
							labelName = requiredFieldLabels.get(j).getText();
							
							// test step
							if (GeneralMethods.WaitForElementBySelector(driver, 0, "Required field indicator", sfp.requiredFieldIndicatorCSS)==null){
								currStepResult = "Pass";
								Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Missing required field indicator; skipping field "+labelName, currStepResult);
								continue;
							}
							
							// test step
							currStepResult = !smartFilesOpened.equals("Pass") ? "Blocked" :
								sfp.PopulateSmartFileFieldByLabelName(requiredFieldLabels.get(j), uid) ? "Pass" : "Fail";
							Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Populate required field "+labelName, currStepResult);	
						}
					}
					
					else {
						// test step
						currStepResult = "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": No required fields were found", currStepResult);
					}
						
					/*
					// If there are no required fields, look for labels with "Name" or "Label" in the header
					else{
						if (fieldLabels != null) size = fieldLabels.size();
						else size = 0;
						if (size > 0)
							for(WebElement label : fieldLabels)
								if (label.getText().contains("Name") || label.getText().contains("Label")){
									labelName = label.getText();
									
									// test step
									currStepResult = !smartFilesOpened.equals("Pass") ? "Blocked" :
										sfp.PopulateSmartFileFieldByLabelName(label, uid) ? "Pass" : "Fail";
									Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Populate Label/Name field "+labelName, currStepResult);
								}
					}
					*/
				}
				driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.SECONDS);
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.ClickButton(sfp.openSmartFormAddButton) ? "Pass" : 
						GeneralMethods.ClickButton(sfp.openSmartFormAddExistingButton) ? "Pass" :"Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Click on Add/Add Existing button", currStepResult);
				
				// test step
				//GeneralMethods.delay(eventDelay*2);
				sfp.WaitForSmartFileToClose();
				if (!GeneralMethods.checkElementIsNotVisible(driver, sfp.openSmartForm)){
					try {sfp.forceCloseSmartForm(typeName);}
					catch (Exception e) {}
					currStepResult = "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Force close smartForm after Add", currStepResult);
				}
				sfp.WaitForSmartFileToClose();
								
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					sfp.FindSmartFile(uid) != null ? "Pass" :"Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Verify smartFile type was successfully created", currStepResult);
			
			// If the course of the test opened any tabs, this needs to be done in order to clean up before log-out
			if (prevStepResult.equals("Pass"))
				mp.closeAllOpenTabs();
		}
		
		// Cleanup
    	if (preReq.equals("Pass"))
    		mp.logOutUser();
		driver.quit();
		driver = null;
		
		Artifact.CloseArtifact(artifact);
	}
  }
}


