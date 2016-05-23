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

public class UIRecon_MedCinSearch extends NG7TestCase {
	  
	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "UIRecon_MedCinSearch";	
		NG7TestCase.tester = "Maria Garcia-Bodoh";
		
		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		MainPage mp = new MainPage(driver, "mainpage");
		LoginPage lp = new LoginPage(driver, "loginpage");
		PatientDashboardPage pdb = new PatientDashboardPage(driver, "patientdashboard.txt");
	    
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		String preReq = null;
			
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
		String location = NG7TestCase.orgString;
		String locationBackup = AutomationSettings.getTestDataItem("organization1");
		String patientName = AutomationSettings.getTestDataItem("patientName"+NG7TestCase.orgSelect);
		String patientNameTypo = AutomationSettings.getTestDataItem("patientNameTypo"+NG7TestCase.orgSelect);
		String patientNameNoTypo = AutomationSettings.getTestDataItem("patientNameNoTypo"+NG7TestCase.orgSelect);
		String patientName2 = AutomationSettings.getTestDataItem("patient2Name"+NG7TestCase.orgSelect);
		String reason = "headache";
		String activityName = "History of Present Illness";
				

		// Run in local stress loop to make sure the test is robust
		for (int i = 1; i <= localStressLoop; i++){
		    if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
		    System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
		    GeneralMethods.prepForIteration();
		    
		    // test step
		    NG7TestCase.testData = "location = "+location;
			currStepResult = !preReq.equals("Pass") ? "Blocked" :
		 		mp.setLocation(location) ? "Pass" : "Fail";
		 	Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Set service location to "+location, currStepResult);
		 	preReq = currStepResult;
			
			// test step
		 	NG7TestCase.testData = "patientName = "+patientName;
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(patientName) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for patient <patientName>", currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_MedCinSearch: Perform global search");
				
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			String category = "patient";
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(patientName, category, true) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for <patientName>", currStepResult+catastrophicFlag);
			String preReq_globalSearch = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_MedCinSearch: Verify returned global search results for category 'People'");
			
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			WebElement openPatientTab = mp.getOpenTab(patientName);
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				openPatientTab.getText().contains("Person Center") ? "Fail" : "Pass";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Patient Center for <patientName> was opened", currStepResult);
			prevStepResult = currStepResult;
			String preReq_openPatientCenter = currStepResult;
			
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :
				mp.clickElement(mp.getOpenTab(patientName), "Open tab for patient "+patientName) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on the tab for patient <patientName>", currStepResult);
			prevStepResult = currStepResult;
		
			// test step
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :	
				pdb.checkElementIsVisible(pdb.openPatientTab, "patient center") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the patient center opens", currStepResult);
			prevStepResult = currStepResult;
			String preReq_patientTabOpened = currStepResult; 
				
			// test step
			if (prevStepResult.equals("Pass") && !pdb.checkElementIsVisible(pdb.patientPhoto, "patient photo")) pdb.clickElement(pdb.flipperContainer, "flipper item");
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				pdb.checkElementIsVisible(pdb.patientPhoto, "patient photo") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check the patient photo is displayed", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Patient Center", "Patient Center Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Patient Center tab", currStepResult);
			prevStepResult = currStepResult;

			// test step
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				pdb.clickElement(pdb.findVisibleTab(pdb.openPatientDashboard, pdb.tabActivities, " "), "Activities tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Activities tab", currStepResult);
			prevStepResult = currStepResult;
			/*
			// test step
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				pdb.addActivityCard("History of Present Illness") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Add activity card for 'History of Present Illness'", currStepResult);
			prevStepResult = currStepResult;
			*/
			// test step
			NG7TestCase.testData = "activity name = "+activityName;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				pdb.selectActivity(activityName) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select activity card for <activity name>", currStepResult);
			prevStepResult = currStepResult;

			// test step
			NG7TestCase.testData = "reason = "+reason;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				pdb.setTextField(pdb.activityBarTypeAheadField, "Reason for Visit field", reason) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the Reason for Visit search field with <reason>", currStepResult);
			prevStepResult = currStepResult;
			GeneralMethods.delay(eventDelay*2);
			
			// test step
			NG7TestCase.testData = "reason = "+reason;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				pdb.activitySearchList.size() > 2? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that results for reason <reason name> are returned by MedCin server", currStepResult);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_MedCinSearch: Check that MedCin search for "+reason+" returns results");	
						
			if (preReq_patientTabOpened.equals("Pass")) 
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


