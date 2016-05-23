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

public class UIRecon_PatientCenter extends NG7TestCase {
	  
	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "UIRecon_PatientCenter";	
		NG7TestCase.tester = "Subhashini Srinivasan";
		
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
		String location = NG7TestCase.orgString;
		String patientName = AutomationSettings.getTestDataItem("patientName"+NG7TestCase.orgSelect);
		String patientNameTypo = AutomationSettings.getTestDataItem("patientNameTypo"+NG7TestCase.orgSelect);
		String patientNameNoTypo = AutomationSettings.getTestDataItem("patientNameNoTypo"+NG7TestCase.orgSelect);
		String patientName2 = AutomationSettings.getTestDataItem("patient2Name"+NG7TestCase.orgSelect);
		String medicationName1 = AutomationSettings.getTestDataItem("medication1Name"+NG7TestCase.orgSelect);
		String medicationName2 = AutomationSettings.getTestDataItem("medication2Name"+NG7TestCase.orgSelect);

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
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for patient <patientName>", currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Perform global search");
				
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			String category = "patient";
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(patientName, category, true) ? "Pass" : "Fail"; // do not handle alerts to that the next step can test it
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for patient <patientName>", currStepResult+catastrophicFlag);
			String preReq_globalSearch = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Verify returned global search results for category 'People'");
			prevStepResult = currStepResult;
			
			// test step
		NG7TestCase.testData = "patientName = "+patientName;
			WebElement openPatientTab = null;
			if (prevStepResult.equals("Pass")) openPatientTab = mp.getOpenTab(patientName);
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				(openPatientTab != null && openPatientTab.getText().contains("Person Center")) ? "Fail" : "Pass";
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
			currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :	
				pdb.checkElementIsVisible(pdb.openPatientTab, "open patient tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the patient center opens", currStepResult);
			prevStepResult = currStepResult;
			String preReq_patientTabOpened = currStepResult; 
						 
			// test step 
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.checkIsNotEmpty(pdb.patientTabHeaderInfo) 
				&& GeneralMethods.checkListIsDisplayed(pdb.patientTabHeaderInfo) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check the summary info is displayed", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				pdb.checkElementIsVisible(pdb.patientName, "patientName") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check the patient name <patientName> is displayed", currStepResult);
			prevStepResult = currStepResult;
				
			// test step
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				pdb.checkElementIsVisible(pdb.flipperContainer, "flipper item") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check the photo/financial module flipper pane is displayed", currStepResult);
			prevStepResult = currStepResult;
							
			if (preReq_patientTabOpened.equals("Pass") && (!pdb.checkPatientPhotoVisible())) pdb.clickElement(pdb.flipperContainer, "flipper item");
			// test step
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				pdb.checkPatientPhotoVisible() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check the patient photo container is visible so that patient photo is displayed", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				pdb.clickElement(pdb.flipperContainer, "flipper item") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on patient photo container to flip the panel", currStepResult);
			prevStepResult = currStepResult;

			// test step
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				pdb.checkElementIsVisible(pdb.innerFinancialModule, "inner financial module") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check the inner financial module is displayed", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			if (preReq_patientTabOpened.equals("Pass")){
				if (pdb.checkPatientPhotoVisible()) pdb.clickElement(pdb.flipperContainer, "flipper item"); // MGB 2/27/2015: updated from old flipPhotoContainer method, which mysteriously disappeard
			}
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				! pdb.checkPatientPhotoVisible() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check the patient photo container is hidden so that photo is no longer displayed", currStepResult);
			prevStepResult = currStepResult;
					
			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Patient Center", "Patient Center Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Patient Center tab", currStepResult);
			prevStepResult = currStepResult;
			
			// test step for patient summary
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				pdb.clickElement(pdb.findVisibleTab(pdb.openPatientDashboard, pdb.tabPatientSummary, " "), "Patient Summary tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Patient Summary tab", currStepResult);
			prevStepResult = currStepResult;

			// test step
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				pdb.clickElement(pdb.findVisibleTab(pdb.openPatientDashboard, pdb.tabHealthfeed, " "), "Healthfeed tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Healthfeed tab", currStepResult);
			prevStepResult = currStepResult;
			
			
			
			// test step
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				pdb.clickTab("Patient Center", "Patient Center Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Patient Center tab", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				pdb.clickElement(pdb.findVisibleTab(pdb.openPatientDashboard, pdb.tabTimeline, ""), "Timeline view button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Timeline View button", currStepResult);
			prevStepResult = currStepResult;
				
			// test step
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				pdb.checkElementIsVisible(pdb.timeline, "Timeline object") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Timeline is displayed", currStepResult);
			prevStepResult = currStepResult;
			String preReq_timelineOpen = currStepResult;

			// test step
			if (preReq_timelineOpen.equals("Pass")) GeneralMethods.delay(5000);
			currStepResult = (preReq_timelineOpen.equals("Pass") == false) ? "Blocked" :
				pdb.checkElementIsVisible(pdb.timelineYearListBar, "timeline year bar") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check timeline year bar is displayed", currStepResult);
			prevStepResult = currStepResult;
				
			// test step
			currStepResult = (preReq_timelineOpen.equals("Pass") == false) ? "Blocked" :
				pdb.checkElementIsVisible(pdb.dashboardMenuButton, "patient dashboard menu button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check timeline filter menu button is displayed", currStepResult);
			prevStepResult = currStepResult;
				
			// test step
			currStepResult = (preReq_timelineOpen.equals("Pass") == false) ? "Blocked" :
				pdb.clickElement(pdb.dashboardMenuButton, "patient dashboard menu button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click dashboard menu button", currStepResult);
			prevStepResult = currStepResult;
							
			// test step
			currStepResult = (preReq_timelineOpen.equals("Pass") == false) ? "Blocked" :
				pdb.checkElementIsVisible(pdb.timelineMenu, "patient dashboard timeline menu") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that timeline filters list is visible", currStepResult);
			prevStepResult = currStepResult;
							
			// test step
			currStepResult = (prevStepResult.equals("Pass")==false) ? "Blocked" :
				GeneralMethods.checkIsNotEmpty(pdb.dashboardMenuList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that list of Timeline filters is not empty", currStepResult);
			prevStepResult = currStepResult;
				
			// test step
			int filterIndex = 0;
			if (prevStepResult.equals("Pass")) 
				filterIndex = GeneralMethods.PickRandomNumber(10);
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				pdb.clickElement(pdb.dashboardMenuList.get(filterIndex), "timeline filter #" +filterIndex+ " from menu list") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on time line filter # "+filterIndex, currStepResult);
			prevStepResult = currStepResult;
				
			// test step
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				pdb.checkElementIsVisible(pdb.timeline, "timeline object") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that timeline is displayed after filter is clicked", currStepResult);
			prevStepResult = currStepResult;

			// test step		
			NG7TestCase.testData = "patientName = "+patientName;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.getOpenTab(patientName) != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check patient tab for patient <patientName> is displayed below the toolbar", currStepResult);
			prevStepResult = currStepResult;
			
			// test step // for activities from patient tab Subha
			currStepResult = (preReq_patientTabOpened.equals("Pass") == false) ? "Blocked" :
				pdb.clickTab("Activities", "Activities Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Activities tab", currStepResult);
			prevStepResult = currStepResult;

			// test step
			GeneralMethods.delay(eventDelay);
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				pdb.clickAddActivity() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Add New Activity '+' button", currStepResult);
			prevStepResult = currStepResult;
				
			// test step
			WebElement wizard = pdb.addActivityForm;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				wizard != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check the Add New Activity form pops open", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				pdb.clickElement(pdb.findVisibleButton(wizard, "Cancel", " "), "Add New Activity form Cancel button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Cancel on Add New Activity form", currStepResult);
			prevStepResult = currStepResult;

			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Alerts", "Alerts Tab") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Alerts tab", currStepResult);
			prevStepResult = currStepResult;

			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Medication Allergies", "Allergies Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Medication Allergies tab", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Demographics", "Demographics Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Demographics tab", currStepResult);
			prevStepResult = currStepResult;

			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Financial", "Financial Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Financial tab", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Images", "Images Tab") ? "Pass" : 
					pdb.clickTab("Documents", "Documents Tab") ? "Pass" :"Fail"; // MGB 10/28/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Images (Documents) tab", currStepResult);
			prevStepResult = currStepResult;

			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Insurance", "Insurance Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Insurance tab", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Medications", "Medications Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Medications tab", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Orders", "Orders Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Orders tab", currStepResult);
			prevStepResult = currStepResult;
				
			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Significant Events", "Significant Events Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Significant Events tab", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Visit History", "Visit History Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Visit History tab", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Wellness Center", "Wellness Center Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Wellness Center tab", currStepResult);
			prevStepResult = currStepResult;
		
			// test step
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Problems", "Problems Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Problems tab", currStepResult);
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


