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

public class UIRecon_Search extends NG7TestCase {
	  
	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "UIRecon_Search";	
		NG7TestCase.tester = "Maria Garcia-Bodoh";
		
		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		MainPage mp = new MainPage(driver, "mainpage");
		LoginPage lp = new LoginPage(driver, "loginpage");
		PatientDashboardPage pdb = new PatientDashboardPage(driver, "patientdashboard.txt");
	    ErrorPage ep = new ErrorPage(driver, "errorpage");
	    SecondModalPage smp = new SecondModalPage(driver, "secondmodal");
		
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
		// MGB 3/5/2015: deferred the top node text fetch to happen only if the location needs to change;
		// set location string to location without top node
		//String topLocationNode = mp.getTopNodeText();
		//String location = topLocationNode + ";" + NG7TestCase.orgString;
		String patientName = AutomationSettings.getTestDataItem("patientName"+NG7TestCase.orgSelect);
		String location = NG7TestCase.orgString;
		String locationBackup = AutomationSettings.getTestDataItem("organization1");
		String patientNameTypo = AutomationSettings.getTestDataItem("patientNameTypo"+NG7TestCase.orgSelect);
		String patientNameNoTypo = AutomationSettings.getTestDataItem("patientNameNoTypo"+NG7TestCase.orgSelect);
		String patientName2 = AutomationSettings.getTestDataItem("patient2Name"+NG7TestCase.orgSelect);
		String medicationName1 = AutomationSettings.getTestDataItem("medication1Name"+NG7TestCase.orgSelect);
		String medicationName2 = AutomationSettings.getTestDataItem("medication2Name"+NG7TestCase.orgSelect);
		String allergyName1 = AutomationSettings.getTestDataItem("allergy1Name"+NG7TestCase.orgSelect);
		
		// Run in local stress loop to make sure the test is robust
		for (int i = 1; i <= localStressLoop; i++){
		    if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
		    System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
		    GeneralMethods.prepForIteration();
		    
		    // test step
		    NG7TestCase.testData = "location = "+location;
		 	currStepResult = !preReq.equals("Pass") ? "Blocked" :
		 		mp.setLocation(location) ? "Pass" : "Fail";
		 	Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Set service location to <location>", currStepResult);
		 	preReq = currStepResult;	
			
			// test step
		 	NG7TestCase.testData = "patientName = "+patientName;
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(patientName) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for <patientName>", currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_Search: Perform global search");
				
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			String category = "patient";
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(patientName, category, true) ? "Pass" : "Fail"; // do not handle alerts to that the next step can test it
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for <patientName>", currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_Search: Verify returned global search results for category "+category);
			
			//mp.handleAlertsPopup();
			
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.checkSearchContextIndicatorIsVisible(patientName) ? "Pass" : "Fail"; // do not handle alerts to that the next step can test it
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that context indicator for <patientName> is visible", currStepResult+WIP);
			prevStepResult = currStepResult;
			
			// test step
			NG7TestCase.testData = "patientNameWithTypo = "+patientNameTypo;
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(patientNameTypo) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for <patientNameWithTypo>", currStepResult);
			prevStepResult = currStepResult;
										
			// test step
			NG7TestCase.testData = "patientNameNoTypo = "+patientNameNoTypo;
			WebElement suggestedResult = mp.findSearchResult(patientNameNoTypo, "suggested");
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.clickElement(suggestedResult,  "result "+patientNameNoTypo) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result <patientNameNoTypo> from suggested list", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			GeneralMethods.delay(eventDelay);
			NG7TestCase.testData = "patientName2 = "+patientName2;
			mp.clickElement(mp.globalSearchField, "Global search/menu field");
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(patientName2, category, true) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for <patientName2>", currStepResult);
			
			if (prevStepResult.equals("Pass"))
				mp.closeAllOpenTabs();
					
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearchSlash(patientName) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Perform global search by focusing on search field with /<patientName>", currStepResult);
			prevStepResult = currStepResult;
												
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			WebElement result = null;
			if (prevStepResult.equals("Pass")) mp.findSearchResult(patientName, "patient");
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				MouseMethods.HoverOver(mp.driver, result, 5, 5) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Hover over search result for <patientName>", currStepResult);
			prevStepResult = currStepResult;

			// test step
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.checkElementIsVisible(mp.resultDetail, "search result details") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check search result details are visible", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			NG7TestCase.testData = "medicationName1 = "+medicationName1;
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(medicationName1) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for <medicationName1>", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			category = "medication";
			NG7TestCase.testData = "medicationName1 = "+medicationName1;
			GeneralMethods.delay(eventDelay);
			mp.clickElement(mp.globalSearchField, "Global search/menu field");
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(medicationName1, category, true) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for <medicationName1>", currStepResult);
			
			// test step
			NG7TestCase.testData = "allergyName1 = "+allergyName1;
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(allergyName1) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for <allergyName1>", currStepResult);
			prevStepResult = currStepResult;
						
			// test step
			category = "allergy";
			NG7TestCase.testData = "allergyName1 = "+allergyName1;
			GeneralMethods.delay(eventDelay);
			mp.clickElement(mp.globalSearchField, "Global search/menu field");
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(allergyName1, category, true) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for <allergyName1>", currStepResult);
			
			
			// test step
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch("bogus") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for non-existent allergy 'bogus'", currStepResult);
			prevStepResult = currStepResult;
						
			// test step
			category = "allergy";
			GeneralMethods.delay(eventDelay);
			mp.clickElement(mp.globalSearchField, "Global search/menu field");
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				! mp.selectSearchResult("bogus", category, true) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that a search result for non-existent allergy 'bogus' is not returned", currStepResult);
			
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


