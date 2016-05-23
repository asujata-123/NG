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


public class RegressionTest_FoundationAustin_topMenuBarOverflowOrder extends NG7TestCase {

	  
	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "RegressionTest_FoundationAustin_topMenuBarOverflowOrder";	
		NG7TestCase.tester = "ronf";
		
		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		MainPage mp = new MainPage(driver, "mainpage");
		LoginPage lp = new LoginPage(driver, "loginpage");
		SmartFilesPage sfp = new SmartFilesPage(driver, "smartfilespage");
		ErrorPage ep = new ErrorPage(driver, "errorpage");
		PatientDashboardPage pdb = new PatientDashboardPage(driver, "patientdashboard.txt");
	    RegistrationPage reg = new RegistrationPage(driver, "registration.txt");
	
		System.out.println("* * * * * RONRONRON * * * * *");
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
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Log in to "+GeneralMethods.getDeployment()+"; code="+loginSuccessCode, currStepResult); // MGB 5/5/2014
		preReq = currStepResult;
		
		// Test data
		String topLocationNode = mp.getTopNodeText();
		String organization = topLocationNode + ";" +NG7TestCase.smartFilesOrg;
		String location = topLocationNode + ";" +NG7TestCase.orgString;
		String patient1Name3 = AutomationSettings.getTestDataItem("patient1Name"+NG7TestCase.orgSelect);
		String patient2Name3 = AutomationSettings.getTestDataItem("patient2Name"+NG7TestCase.orgSelect);
		String patient3Name3 = AutomationSettings.getTestDataItem("patient3Name"+NG7TestCase.orgSelect);
		////
		
		// Run in local stress loop to make sure the test is robust
		for (int i = 1; i <= localStressLoop; i++){
		    if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
		    System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
		    GeneralMethods.prepForIteration();
		    
			// test step
			System.out.println("* * * * * RONRONRON Set service location to +location");
			currStepResult = !preReq.equals("Pass") ? "Blocked" :
				mp.setLocation(location) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Set service location to "+location, currStepResult);
			preReq = currStepResult;	
					    
			// test step Search for 1st patient
			System.out.println("* * * * * RONRONRON Search for 1st patient +patient1Name3");
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(patient1Name3) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for 1st patient "+patient1Name3, currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Perform global search");
				
			// test step
			System.out.println("* * * * * RONRONRON Select search result for 1st patient +patient1Name3");
			String category = "patient";
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(patient1Name3, category, false) ? "Pass" : "Fail"; // do not handle alerts to that the next step can test it
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for 1st patient "+patient1Name3, currStepResult+catastrophicFlag);
			String preReq_globalSearch = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Verify returned global search results for category 'People'");
			
			mp.handleAlertsPopup();
			
			// test step
			System.out.println("* * * * * RONRONRON Patient Center for 1st patient +patient1Name3");
			WebElement openPatientTab = mp.getOpenTab(patient1Name3);
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				openPatientTab.getText().contains("Person Center") ? "Fail" : "Pass";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Patient Center for 1st patient "+patient1Name3+" was opened", currStepResult);
			prevStepResult = currStepResult;
			String preReq_openPatientCenter = currStepResult;
			
			String preReq_patientTabOpened = "";
			
			// test step Search for 2nd patient
			System.out.println("* * * * * RONRONRON Search for 2nd patient +patient2Name3");
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(patient2Name3) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for 2nd patient "+patient2Name3, currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Perform global search");
				
			// test step
			System.out.println("* * * * * RONRONRON Select search result for 2nd patient +patient2Name3 RONRONRON * * * * *");
			//String category = "patient";
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(patient2Name3, category, false) ? "Pass" : "Fail"; // do not handle alerts to that the next step can test it
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for 2nd patient "+patient2Name3, currStepResult+catastrophicFlag);
			//String preReq_globalSearch = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Verify returned global search results for category 'People'");
			
			mp.handleAlertsPopup();

			// test step
			System.out.println("* * * * * RONRONRON Patient Center for 2nd patient +patient2Name3 RONRONRON * * * * *");
			//WebElement openPatientTab = mp.getOpenTab(patient2Name3);
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				openPatientTab.getText().contains("Person Center") ? "Fail" : "Pass";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Patient Center for 2nd patient "+patient2Name3+" was opened", currStepResult);
			prevStepResult = currStepResult;
			//String preReq_openPatientCenter = currStepResult;
			
			//String preReq_patientTabOpened = "";

			// test step Search for 3rd patient
			System.out.println("* * * * * RONRONRON Search for 3rd patient +patient3Name3 RONRONRON * * * * *");
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(patient3Name3) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for 3rd patient "+patient3Name3, currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Perform global search");
				
			// test step
			System.out.println("* * * * * RONRONRON Select search result for 3rd patient +patient3Name3 RONRONRON * * * * *");
			//String category = "patient";
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(patient3Name3, category, false) ? "Pass" : "Fail"; // do not handle alerts to that the next step can test it
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for 3rd patient "+patient3Name3, currStepResult+catastrophicFlag);
			//String preReq_globalSearch = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Verify returned global search results for category 'People'");
			
			mp.handleAlertsPopup();
			
			// test step
			System.out.println("* * * * * RONRONRON Patient Center for 3rd patient +patient3Name3 RONRONRON * * * * *");
			//WebElement openPatientTab = mp.getOpenTab(patient3Name3);
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				openPatientTab.getText().contains("Person Center") ? "Fail" : "Pass";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Patient Center for 3rd patient "+patient3Name3+" was opened", currStepResult);
			prevStepResult = currStepResult;
			//String preReq_openPatientCenter = currStepResult;
			
			//String preReq_patientTabOpened = "";

			// test step Click on the tab for 1st patient
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				mp.clickElement(mp.getOpenTab(patient1Name3), "Open tab for 1st patient "+patient1Name3) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on the tab for 1st patient "+patient1Name3, currStepResult);
			prevStepResult = currStepResult;
			// test step Verify the tabs are ordered 1st patient first, 2nd patient second, and 3rd patient third
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.checkTabOrderPosition(patient1Name3, 0) && 						// 1st patient
				mp.checkTabOrderPosition(patient2Name3, 1) && 						// 2nd patient
				mp.checkTabOrderPosition(patient3Name3, 2)  ? "Pass" : "Fail";		// 3rd patient
			System.out.println("Tabs are ordered 1st patient "+patient1Name3 +" first,  2nd patient" +patient2Name3 + " second, and 3rd patient" +patient3Name3 +" third");
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify the tabs are ordered 1st patient "+patient1Name3 +" first,  2nd patient" +patient2Name3 + " second, and 3rd patient" +patient3Name3 +" third", currStepResult);
			prevStepResult = currStepResult;
			
			// test step Click on the tab for 2nd patient
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				mp.clickElement(mp.getOpenTab(patient2Name3), "Open tab for 2nd patient "+patient2Name3) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on the tab for 2nd patient "+patient2Name3, currStepResult);
			prevStepResult = currStepResult;
			// test step Verify the tabs are ordered 2nd patient first, 1st patient second, and 3rd patient third
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.checkTabOrderPosition(patient1Name3, 0) && 						// 2nd patient
				mp.checkTabOrderPosition(patient2Name3, 1) && 						// 1st patient
				mp.checkTabOrderPosition(patient3Name3, 2)  ? "Pass" : "Fail";		// 3rd patient
			System.out.println("Tabs are ordered 2nd patient "+patient2Name3 +" first,  1st patient" +patient1Name3 + " second,  and 3rd patient" +patient3Name3 +" third");
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify the tabs are ordered 2nd patient "+patient2Name3 +" first,  1st patient" +patient1Name3 + " second,  and 3rd patient" +patient3Name3 +" third", currStepResult);
			prevStepResult = currStepResult;
			
			// test step Click on the tab for 3rd patient
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				mp.clickElement(mp.getOpenTab(patient3Name3), "Open tab for 3rd patient "+patient3Name3) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on the tab for 3rd patient "+patient3Name3, currStepResult);
			prevStepResult = currStepResult;
			// test step Verify the tabs are ordered 3rd patient first, 1st patient second, and 2nd patient third
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.checkTabOrderPosition(patient3Name3, 0) &&						// 3rd patient 
				mp.checkTabOrderPosition(patient1Name3, 1) && 						// 1st patient
				mp.checkTabOrderPosition(patient2Name3, 2)  ? "Pass" : "Fail";		// 2nd patient
			System.out.println("Tabs are ordered 3rd patient "+patient3Name3 +" first,  1st patient" +patient1Name3 + " second, and 2nd patient " +patient2Name3 +" third");
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify the tabs are ordered 3rd patient "+patient3Name3 +" first,  1st patient" +patient1Name3 + " second, and 2nd patient " +patient2Name3 +" third", currStepResult);
			prevStepResult = currStepResult;
			
			// test step Open 1st non-patient tab
			currStepResult = !preReq.equals("Pass") ? "Blocked" :
				mp.selectHomeMenuItem(mp.menuSmartFiles) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Launch SmartFiles tab from Menu", currStepResult);
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.checkElementIsVisible(sfp.smartfilesTabControl, "smart files interface control") ? "Pass" : "Fail";
			System.out.println("* * * * Successfuly opened 1st non-patient tab " +mp.menuSmartFiles +" * * * * ");
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check SmartFiles tab opened", currStepResult);
			String smartFilesOpened = currStepResult;
			//// test step Verify the tabs are ordered 1st patient first, 2nd patient second, 3rd patient third, and 1st non-patient 4th
			//currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
			//	mp.checkTabOrderPosition(patient1Name3, 0) &&						// 1st patient 
			//	mp.checkTabOrderPosition(patient2Name3, 1) && 						// 2nd patient
			//	mp.checkTabOrderPosition(patient3Name3, 2) && 						// 3rd patient
			//	mp.checkTabOrderPosition(mp.menuSmartFiles, 3)  ? "Pass" : "Fail";		// 1st non-patient
			//System.out.println("Tabs are ordered 3rd patient "+patient3Name3 +" first,  1st patient" +patient1Name3 + " second, and 2nd patient " +patient2Name3 +" third");
			//Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify the tabs are ordered 3rd patient "+patient3Name3 +" first,  1st patient" +patient1Name3 + " second, and 2nd patient " +patient2Name3 +" third", currStepResult);
			//prevStepResult = currStepResult;

			
			
			// test step Open 2nd non-patient tab
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Financial", "Patient Dashboard Financial Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Financial tab", currStepResult);
			prevStepResult = currStepResult;
			//String preReq_financialGridOpened = currStepResult;	
			// test step
			GeneralMethods.delay(eventDelay*2);
			currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
				pdb.checkElementIsVisible(pdb.financialGrid, "financial grid") ? "Pass" : "Fail";
			System.out.println("* * * * Successfuly opened 2nd non-patient tab = Financial * * * * ");
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Financial grid opened", currStepResult);
			prevStepResult = currStepResult;
			String preReq_financialGridOpened = currStepResult;	
			
			// test step Open 3rd non-patient tab
			currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
				mp.selectHomeMenuItem_registration() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Open the patient registration wizard", currStepResult);
			prevStepResult = currStepResult;
			// test step
			currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
				reg.wizardExists() ? "Pass" : "Fail";
			System.out.println("* * * * Successfuly opened 3rd non-patient tab = registration * * * * ");
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check registration wizard opens", currStepResult);
			prevStepResult = currStepResult;
			String preReq_regWizardOpen = currStepResult;
			
			
			
			
			
			
			
			
			// Cleanup
			mp.closeAllOpenTabs();
			
			// test step
			if (mp.checkErrorPage()) Artifact.ReportDoNotDeliverFail(currStepResult, "UIRecon_SmartFilesInterface: Exception detected");
		}
				
		// Cleanup
    	mp.logOutUser();
		driver.quit();
		driver = null;
		
		Artifact.CloseArtifact(artifact);
	}
}


