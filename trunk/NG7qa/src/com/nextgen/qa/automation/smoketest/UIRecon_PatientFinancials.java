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


public class UIRecon_PatientFinancials extends NG7TestCase {
	  
	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "UIRecon_PatientFinancials";	
		NG7TestCase.tester = "Maria Garcia-Bodoh";
		
		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		MainPage mp = new MainPage(driver, "mainpage");
		LoginPage lp = new LoginPage(driver, "loginpage");
		PatientDashboardPage pdb = new PatientDashboardPage(driver, "patientdashboard.txt");
	    ErrorPage ep = new ErrorPage(driver, "errorpage");
	
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		String preReq = null;
			
		// Test data
		String patientName = AutomationSettings.getTestDataItem("patientName"+NG7TestCase.orgSelect);
		String location = NG7TestCase.orgString;
		
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
			prevStepResult = currStepResult;			
						
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(patientName) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for patient <patientName>", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			String category = "patient";
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(patientName, category, true) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for patient <patientName>", currStepResult);
			prevStepResult = currStepResult;
			String preReq_globalSearch = currStepResult;

			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			WebElement openPatientTab = null;
			if (preReq_globalSearch.equals("Pass")) openPatientTab = mp.getOpenTab(patientName);
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				(openPatientTab != null && openPatientTab.getText().contains("Person Center")) ? "Fail" : "Pass";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Patient Center for <patientName> was opened", currStepResult);
			prevStepResult = currStepResult;
			String preReq_openPatientCenter = currStepResult;
			
			String preReq_patientTabOpened = "";
				
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :
				pdb.checkElementIsVisible(pdb.openPatientTab, "open patient tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the patient center fpr <patientName> opens", currStepResult);
			prevStepResult = currStepResult;
			preReq_patientTabOpened = currStepResult;
						
			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Financial", "Patient Dashboard Financial Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Financial tab", currStepResult);
			prevStepResult = currStepResult;
			//String preReq_financialGridOpened = currStepResult;	
				
			// test step
			if (preReq_patientTabOpened.equals("Pass")) GeneralMethods.delay(eventDelay*2);
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.checkElementIsVisible(pdb.financialGrid, "financial grid") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Financial grid opened", currStepResult);
			prevStepResult = currStepResult;
			String preReq_financialGridOpened = currStepResult;	
							
			// test step
			//WebElement chargesLink = null;
			//if (preReq_financialGridOpened.equals("Pass")) chargesLink = pdb.findVisibleElement(pdb.openPatientDashboard, pdb.linkChargesCss);
			currStepResult = (preReq_financialGridOpened.equals("Pass") == false) ? "Blocked" :
				//pdb.checkElementIsVisible(chargesLink, "charges link") ? "Pass" : "Fail";
				pdb.checkElementIsVisible(pdb.findVisibleElement(pdb.openPatientDashboard, pdb.linkChargesCss), "charges link") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Financial grid Charges link", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				//GeneralMethods.ClickButton(chargesLink) ? "Pass" : "Fail";
				GeneralMethods.ClickButton(pdb.findVisibleElement(pdb.openPatientDashboard, pdb.linkChargesCss)) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Financial grid Charges link", currStepResult);
			prevStepResult = currStepResult;
					
			// test step
			if (prevStepResult.equals("Pass")) GeneralMethods.delay(eventDelay);
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.getOpenTab("Financial Manager") != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Financial Manager / Batch Maintenance opened", "WIP"+WIP);
			prevStepResult = currStepResult;
			//Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_SearchPatientFinancials: Verify Batch Maintenance opens after Charges link is clicked");
			
			// test step
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.closeOpenTab("Financial Manager") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Close Financial Manager / Batch Maintenance tab", "WIP"+WIP);
			prevStepResult = currStepResult;
						
			// test step
			//WebElement transactionsLink = null;
			//if (preReq_financialGridOpened.equals("Pass")) transactionsLink = pdb.findVisibleElement(pdb.openPatientDashboard, pdb.linkTransactionsCss);
			currStepResult = (preReq_financialGridOpened.equals("Pass") == false) ? "Blocked" :
				pdb.checkElementIsVisible(pdb.findVisibleElement(pdb.openPatientDashboard, pdb.linkTransactionsCss), "transactions link") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Financial grid Transactions link", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.ClickButton(pdb.findVisibleElement(pdb.openPatientDashboard, pdb.linkTransactionsCss)) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Financial grid Transactions link", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			if (prevStepResult.equals("Pass")) GeneralMethods.delay(eventDelay);
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.getOpenTab("Financial Manager") != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Financial Manager / Batch Maintenance opened", "WIP"+WIP);
			prevStepResult = currStepResult;
			//Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_SearchPatientFinancials: Verify Batch Maintenance opens after Transactions link is clicked");
			
			// test step
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.closeOpenTab("Financial Manager") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Close Financial Manager / Batch Maintenance tab", "WIP"+WIP);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = (preReq_financialGridOpened.equals("Pass") == false) ? "Blocked" :
				mp.selectHomeMenuItem("Financial Manager") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Open Financial Manager from menu", currStepResult);
			prevStepResult = currStepResult;
			String financialManagerOpen_menu = currStepResult;
			
			// test step
			currStepResult = (financialManagerOpen_menu.equals("Pass") == false) ? "Blocked" :
				pdb.clickTab("Generate Claims", "Generate Claims Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Generate Claims tab", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = (financialManagerOpen_menu.equals("Pass") == false) ? "Blocked" :
				pdb.clickTab("ERA Exceptions", "ERA Exceptions tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click ERA Exceptions tab", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = (financialManagerOpen_menu.equals("Pass") == false) ? "Blocked" :
				pdb.clickTab("Statements", "Statements tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Statements tab", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = (financialManagerOpen_menu.equals("Pass") == false) ? "Blocked" :
				pdb.clickTab("Batch Maintenance", "Batch Maintenance tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Batch Maintenance tab", currStepResult);
			prevStepResult = currStepResult;

			// test step
			currStepResult = (financialManagerOpen_menu.equals("Pass") == false) ? "Blocked" :
				mp.closeOpenTab("Financial Manager") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Close Financial Manager", currStepResult);
			prevStepResult = currStepResult;				/*
			// test step
			currStepResult = (preReq_financialGridOpened.equals("Pass") == false) ? "Blocked" :
				true ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"", currStepResult);
			prevStepResult = currStepResult;
            */
			// test step
			currStepResult = (preReq_financialGridOpened.equals("Pass") == false) ? "Blocked" :
				pdb.checkElementIsVisible(pdb.visitIDPulldown, "visit ID field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Financial grid Visit ID pulldown", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = (preReq_financialGridOpened.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(pdb.visitIDPulldown, pdb.visitIDPulldownList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Financial grid Visit ID pulldown list is not empty", currStepResult+WIP);
			prevStepResult = currStepResult;
		
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
