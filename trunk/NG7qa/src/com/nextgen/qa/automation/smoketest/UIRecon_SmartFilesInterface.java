package com.nextgen.qa.automation.smoketest;

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


public class UIRecon_SmartFilesInterface extends NG7TestCase {

	  
	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "UIRecon_SmartFilesInterface";	
		NG7TestCase.tester = "Maria Garcia-Bodoh";
		
		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		MainPage mp = new MainPage(driver, "mainpage");
		LoginPage lp = new LoginPage(driver, "loginpage");
		SmartFilesPage sfp = new SmartFilesPage(driver, "smartfilespage");
		ErrorPage ep = new ErrorPage(driver, "errorpage");
	
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
     	
        // test step // MGB 3/18/2014 updated to use configurable layout variable
     	String layout = AutomationSettings.getTestDataItem("layout_"+NG7TestCase.build); 
     	currStepResult=	mp.handleWelcome(layout) ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"If layout selector comes up select "+layout, currStepResult);	
		preReq = currStepResult;
     	
		NG7TestCase.testData = "regionTested = "+NG7TestCase.deployment;
     	String loginSuccessCode = mp.checkLoginSucceeded();
        currStepResult = loginSuccessCode.equals("00") ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Log in to NG7", currStepResult); // MGB 5/5/2014
		preReq = currStepResult;
		
		// Test data
		String organization = NG7TestCase.orgString;
		
		
		// Run in local stress loop to make sure the test is robust
		for (int i = 1; i <= localStressLoop; i++){
		    if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
		    System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
		    GeneralMethods.prepForIteration();
		    
			// test step
		    NG7TestCase.testData = "location = "+organization;
	     	currStepResult = !preReq.equals("Pass") ? "Blocked" :
				mp.setLocation(organization) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Set service location to <location>", currStepResult);
			preReq = currStepResult;	
					    
		    // test step
			currStepResult = !preReq.equals("Pass") ? "Blocked" :
				mp.selectHomeMenuItem(mp.menuSmartFiles) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Launch SmartFiles tab from Menu", currStepResult);
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.checkElementIsVisible(sfp.smartfilesTabControl, "smart files interface control") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check SmartFiles tab opened", currStepResult);
			String smartFilesOpened = currStepResult;
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.CheckSmartFilesOrgListDisplayed() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check organization list is not empty", currStepResult+catastrophicFlag);
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_SmartFilesInterface: Verify SmartFiles interface organization list is not empty"+catastrophicFlag);
				
			// test step
			NG7TestCase.testData = "organization = "+organization;
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.selectOrganization(organization) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select organization <organization>", currStepResult);
							
			// test step
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.checkElementIsVisible(sfp.smartfileTypeSearchField, "smart type search field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"SmartFile type search field is displayed", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.checkElementIsVisible(sfp.smartItemsSearchField, "smart items search field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"SmartItems search field is displayed", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				GeneralMethods.checkListIsDisplayed(sfp.smartFormTypesList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"SmartType list is not empty", currStepResult+catastrophicFlag);
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_SmartFilesInterface: Verify the SmartFiles interface SmartType list is not empty");
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.ClickButton(sfp.GetSmartFileType(driver, "Appointment Types")) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on SmartFile type Appointment Types", currStepResult);	
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				//GeneralMethods.checkIsNotEmpty(sfp.smartfilesList) ? "Pass" : "Fail";
				GeneralMethods.checkListIsDisplayed(sfp.smartfilesList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check SmartFile List is displayed", currStepResult);				

			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.FindElementInObjHierarchy(sfp.smartfilesList.get(0), sfp.buttonCssData+"[value='Update']") != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check SmartFile Update button is displayed", currStepResult);				
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.FindElementInObjHierarchy(sfp.smartfilesList.get(0), sfp.buttonCssData+"[value='Remove']") != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check SmartFile Remove button is displayed", currStepResult);				
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.FindElementInObjHierarchy(sfp.smartfilesList.get(0), sfp.buttonCssData+"[value='Filter']") != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check SmartFile Filter button is displayed", currStepResult);	
			
			// test step
			NG7TestCase.testData = "ruleType = "+"Rule Set";
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.searchSmartFileType("Rule Set") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search SmartFile type <ruleType>", currStepResult);
			
			// test step
			NG7TestCase.testData = "ruleType = "+"Rule Set";
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.ClickButton(sfp.GetSmartFileType(driver, "Rule Set")) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on SmartFile type <ruleType>", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.checkElementIsVisible(sfp.importRuleSetButton, "Import Rule Set button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Import Rule Set button is displayed", currStepResult);	

			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.clickElement(sfp.importRuleSetButton, "Import Rule Set button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Import Rule Set button", currStepResult);	

			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.checkElementIsVisible(sfp.importRuleSetForm, "Import Rule Set form") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Import Rule Set form opens", currStepResult);
					
			// test step
			WebElement cancelButton = GeneralMethods.FindElementInObjHierarchy(sfp.importRuleSetForm, "div[ng-click='closePhotoUploader()']");
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.ClickButton(cancelButton) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Import Rule Set form Cancel button", currStepResult);	
					
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.checkElementIsVisible(sfp.directIndirectSlider, "Direct/Indirect slider") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Direct/Indirect slider is displayed", currStepResult);	
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				//sfp.checkElementIsVisible(sfp.gearButton, "SmartFiles Gear button") ? "Pass" : "Fail";
				sfp.clickElement(sfp.gearButton, "SmartFiles Gear button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Gear button", currStepResult);
			/*
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.clickElement(sfp.findVisibleButton(sfp.openSmartfilesInterface, sfp.buttonAddNeworg, " "), "Organization Menu button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click the Organization Menu button", currStepResult);
			*/
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.clickElement(sfp.findVisibleDropdownListItem(sfp.openSmartfilesInterface, "Add New Organization", " "), "Add New Organization menu item") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select Add New Organization", currStepResult);
								
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.checkElementIsVisible(sfp.addNewOrgForm, "Add New Organization form") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Add New Organization form opens", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.clickElement(sfp.findVisibleButton(sfp.addNewOrgForm, "Cancel", ""), "Add New Organization form Cancel button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Cancel on Add New Organization form", currStepResult);
		}
				
		// Cleanup
    	if (preReq.equals("Pass"))
    		mp.logOutUser();
		driver.quit();
		driver = null;
		
		Artifact.CloseArtifact(artifact);
	}
}


