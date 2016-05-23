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


public class RegressionTest_FoundationAustin_28017_topMenuBarOverflowOrder extends NG7TestCase {

	  
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
	    RegistrationPage reg = new RegistrationPage(driver, "registrationpage");
	    //GeneralMethods gm = new GeneralMethods(driver, "generalmethods");
	
	    
		//System.out.println("* * * * * RONRONRON * * * * *");
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		String preReq = null;
		//String reqFields = "";
			
		// Test data
		String location = NG7TestCase.orgString;
		//String topLocationNode = mp.getTopNodeText();
		//String organization = topLocationNode + ";" +NG7TestCase.smartFilesOrg;
		String patient1Name = AutomationSettings.getTestDataItem("patient1Name"+NG7TestCase.orgSelect);
		String patient2Name = AutomationSettings.getTestDataItem("patient2Name"+NG7TestCase.orgSelect);
		String patient3Name = AutomationSettings.getTestDataItem("patient3Name"+NG7TestCase.orgSelect);
		System.out.println("* * * * * patient1Name = " +patient1Name );
		System.out.println("* * * * * patient2Name = " +patient2Name );
		System.out.println("* * * * * patient3Name = " +patient3Name );
		
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
		
		// Run in local stress loop to make sure the test is robust
		for (int i = 1; i <= localStressLoop; i++){
		    if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
		    System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
		    GeneralMethods.prepForIteration();
		    
			// test step
			System.out.println("* * * TEST STEP * * * Set service location to "+location );
			currStepResult = !preReq.equals("Pass") ? "Blocked" :
				mp.setLocation(location) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Set service location to "+location, currStepResult);
			preReq = currStepResult;	
					    
			// test step Search for 1st patient
			System.out.println("* * * TEST STEP * * * Search for 1st patient = "+patient1Name);
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(patient1Name) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for 1st patient "+patient1Name, currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Perform global search");
			String category = "patient";
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(patient1Name, category, false) ? "Pass" : "Fail"; // do not handle alerts to that the next step can test it
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for 1st patient "+patient1Name, currStepResult+catastrophicFlag);
			String preReq_globalSearch = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Verify returned global search results for category 'People'");
			mp.handleAlertsPopup();
			WebElement openPatientTab = mp.getOpenTab(patient1Name);
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				openPatientTab.getText().contains("Person Center") ? "Fail" : "Pass";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Patient Center for 1st patient = "+patient1Name+" was opened", currStepResult);
			prevStepResult = currStepResult;
			String preReq_openPatientCenter = currStepResult;
			
			String preReq_patientTabOpened = "";
			
			// test step Search for 2nd patient
			System.out.println("* * * TEST STEP * * * Search for 2nd patient = "+patient2Name);
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(patient2Name) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for 2nd patient = "+patient2Name, currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Perform global search");
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(patient2Name, category, false) ? "Pass" : "Fail"; // do not handle alerts to that the next step can test it
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for 2nd patient "+patient2Name, currStepResult+catastrophicFlag);
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Verify returned global search results for category 'People'");
			mp.handleAlertsPopup();
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				openPatientTab.getText().contains("Person Center") ? "Fail" : "Pass";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Patient Center for 2nd patient "+patient2Name+" was opened", currStepResult);
			prevStepResult = currStepResult;
			

			// test step Search for 3rd patient
			System.out.println("* * * TEST STEP * * * Search for 3rd patient = "+patient3Name +", Verify tab order is pat3, pat2, and pat1 (after123) " );
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(patient3Name) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for 3rd patient "+patient3Name, currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Perform global search");
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(patient3Name, category, false) ? "Pass" : "Fail"; // do not handle alerts to that the next step can test it
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for 3rd patient "+patient3Name, currStepResult+catastrophicFlag);
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_PatientCenter: Verify returned global search results for category 'People'");
			mp.handleAlertsPopup();
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				openPatientTab.getText().contains("Person Center") ? "Fail" : "Pass";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Patient Center for 3rd patient "+patient3Name+" was opened", currStepResult);
			prevStepResult = currStepResult;
			//VERIFY TAB ORDER == pat1, pat3, and pat2"
				currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
					mp.checkTabOrderPosition(patient3Name, 1) 
					&& mp.checkTabOrderPosition(patient2Name, 2) 
					&& mp.checkTabOrderPosition(patient1Name, 3) ? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true)
				{
				System.out.println(         "*** VERIFY PASSED +++ (after p1p2p3) Tab order is correct (3rd patient "+patient3Name +" 1st,  2nd patient " +patient2Name + " 2nd, and 1st patient " +patient1Name +" 3rd) ");
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify tab order is correct (3rd patient "+patient3Name +" 1st,  2nd patient " +patient2Name + " 2nd, and 1st patient " +patient1Name +" 3rd)", currStepResult);
				}
			else
				{
				System.out.println(  "*** VERIFY FAILED --- (after p1p2p3) Tabs are NOT ordered correctly (3rd patient "+patient3Name +" 1st,  2nd patient " +patient2Name + " 2nd, and 1st patient " +patient1Name +" 3rd) ");
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Tabs are NOT ordered correctly (3rd patient "+patient3Name +" 1st,  2nd patient " +patient2Name + " 2nd, and 1st patient " +patient1Name +" 3rd)", currStepResult);
				}
			

			
			// test step Open 1st non-patient tab
			System.out.println("* * * TEST STEP * * * Click 1st non-patient tab == " + mp.menuSmartFiles );
			currStepResult = !preReq.equals("Pass") ? "Blocked" :
				mp.selectHomeMenuItem(mp.menuSmartFiles) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Launch SmartFiles tab from Menu", currStepResult);
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.checkElementIsVisible(sfp.smartfilesTabControl, "smart files interface control") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check SmartFiles tab opened", currStepResult);
			String smartFilesOpened = currStepResult;
	

			// test step Open 2nd non-patient tab
			System.out.println("* * * TEST STEP * * * Click 2nd non-patient tab == " + mp.menuFinancial );
			currStepResult = !preReq.equals("Pass") ? "Blocked" :
				mp.selectHomeMenuItem(mp.menuFinancial) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Financial tab", currStepResult);
			GeneralMethods.delay(eventDelay*2);
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				pdb.checkElementIsVisible(mp.financialManagerCtrl, "financial grid") ? "Pass" : "Fail"; 
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that Financial Manager page opened", currStepResult);
			String financialManagerOpened = currStepResult;
		//	String preReq_financialGridOpened = currStepResult;	

			
		    // test step Open 3rd non-patient tab
			System.out.println("* * * TEST STEP * * *  Click 3rd non-patient tab == " +mp.menuRegistrationDash +", Verify tab order is pat3, pat2, pat1, non3, non2, non1 (after p1p2p3 np1np2np3) "  );
			currStepResult = !preReq.equals("Pass") ? "Blocked" :
//				//mp.selectHomeMenuItem("registration") ? "Pass" : "Fail"; 
				mp.selectHomeMenuItem(mp.menuRegistrationDash) ? "Pass" : "Fail"; 
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Registration from the Home menu", currStepResult);
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_RegistrationWizard: Click Registration from the Home menu");
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				reg.wizardExists() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that Registration wizard opened", currStepResult);
			//String openReg_PreReq = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_RegistrationWizard: Verify Registration wizard opens after clicking Registration menu item");
			//VERIFY TAB ORDER == pat3, pat2, pat1, non3, non2, non1"
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.checkTabOrderPosition(patient3Name, 1) 
				&& mp.checkTabOrderPosition(patient2Name, 2) 
				&& mp.checkTabOrderPosition(patient1Name, 3) 
				&& mp.checkTabOrderPosition("Registration/Check-In", 4)  
				&& mp.checkTabOrderPosition((mp.menuFinancial), 5)
				&& mp.checkTabOrderPosition((mp.menuSmartFiles), 6)	? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true) 
				{
				System.out.println("***VERIFY PASSED +++ (after p1p2p3 np1np2np3) Tab order is correct "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+  "Verified the tab order is correct "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			else
				{
				System.out.println("***VERIFY FAILED --- (after p1p2p3 np1np2np3) Tabs are NOT ordered correctly "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+               "Tabs are NOT ordered correctly "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			prevStepResult = currStepResult;
			

			//CLICK 1st patient VERIFY TAB ORDER IS pat1, pat3, pat2, non3, non2, non1 
			System.out.println("* * * TEST STEP * * *  Click 1st patient tab, " + patient1Name +" Verify tab order is pat1, pat3, pat2, non3, non2, non1 (after p1p2p3 np1np2np3 p1) " );
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				mp.clickElement(mp.getOpenTab(patient1Name), "Open tab for 1st patient "+patient1Name) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on the tab for 1st patient = "+patient1Name, currStepResult);
			//prevStepResult = currStepResult;
			//VERIFY TAB ORDER == pat1, pat3, pat2, non3, non2, non1"
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.checkTabOrderPosition(patient1Name, 1) 
				&& mp.checkTabOrderPosition(patient3Name, 2) 
				&& mp.checkTabOrderPosition(patient2Name, 3) 
				&& mp.checkTabOrderPosition("Registration/Check-In", 4)  
				&& mp.checkTabOrderPosition((mp.menuFinancial), 5)
				&& mp.checkTabOrderPosition((mp.menuSmartFiles), 6)	? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true) 
				{
				System.out.println("***VERIFY PASSED +++ (after p1p2p3 np1np2np3 p1) Tab order is correct "
					+ "(1st patient "+patient1Name + " 1st, 3rd patient " +patient3Name + " 2nd, 2nd patient " +patient2Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+  "Verified the tab order is correct "
					+ "(1st patient "+patient1Name + " 1st, 3rd patient " +patient3Name + " 2nd, 2nd patient " +patient2Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			else
				{
				System.out.println("***VERIFY FAILED --- (after p1p2p3 np1np2np3 p1) Tabs are NOT ordered correctly "
					+ "(1st patient "+patient1Name + " 1st, 3rd patient " +patient3Name + " 2nd, 2nd patient " +patient2Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+               "Tabs are NOT ordered correctly "
					+ "(1st patient "+patient1Name + " 1st, 3rd patient " +patient3Name + " 2nd, 2nd patient " +patient2Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			prevStepResult = currStepResult;

		
			//CLICK 2nd patient VERIFY TAB ORDER IS pat2, pat1, pat3, non3, non2, non1  
			System.out.println("* * * TEST STEP * * *  Click 2nd patient tab " +patient2Name + " Verify tab order is pat2, pat1, pat3, non3, non2, non1 (after p1p2p3 np1np2np3 p1p2) " );
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				mp.clickElement(mp.getOpenTab(patient2Name), "Open tab for 1st patient "+patient2Name) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on the tab for 2nd patient = "+patient2Name, currStepResult);
			prevStepResult = currStepResult;
			//VERIFY TAB ORDER == pat2, pat1, pat3, non3, non2, non1"
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.checkTabOrderPosition(patient2Name, 1) 
				&& mp.checkTabOrderPosition(patient1Name, 2) 
				&& mp.checkTabOrderPosition(patient3Name, 3) 
				&& mp.checkTabOrderPosition("Registration/Check-In", 4)  
				&& mp.checkTabOrderPosition((mp.menuFinancial), 5)
				&& mp.checkTabOrderPosition((mp.menuSmartFiles), 6)	? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true) 
				{
				System.out.println("***VERIFY PASSED +++  (after p1p2p3 np1np2np3 p1p2) Tab order is correct "
						+ "(2nd patient "+patient2Name + " 1st, 1st patient " +patient1Name + " 2nd, 3rd patient " +patient3Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+  "Verified the tab order is correct "
						+ "(2nd patient "+patient2Name + " 1st, 1st patient " +patient1Name + " 2nd, 3rd patient " +patient3Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			else
				{
				System.out.println("***VERIFY FAILED --- (after p1p2p3 np1np2np3 p1p2) Tabs are NOT ordered correctly "
					+ "(2nd patient "+patient2Name + " 1st, 1st patient " +patient1Name + " 2nd, 3rd patient " +patient3Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+               "Tabs are NOT ordered correctly "
					+ "(2nd patient "+patient2Name + " 1st, 1st patient " +patient1Name + " 2nd, 3rd patient " +patient3Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			prevStepResult = currStepResult;


			//CLICK 3rd patient VERIFY TAB ORDER IS pat3, pat2, pat1, non3, non2, non1   
			System.out.println("* * * TEST STEP * * *  Click 3rd patient tab " +patient3Name + ", Verify tab order is pat3, pat2, pat1, non3, non2, non1 (after p1p2p3 np1np2np3 p1p2p3) " );
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				mp.clickElement(mp.getOpenTab(patient3Name), "Open tab for 3rd patient "+patient3Name) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on the tab for 3rd patient = "+patient2Name, currStepResult);
			prevStepResult = currStepResult;
			//VERIFY TAB ORDER == pat3, pat2, pat1, non3, non2, non1"
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.checkTabOrderPosition(patient3Name, 1) 
				&& mp.checkTabOrderPosition(patient2Name, 2) 
				&& mp.checkTabOrderPosition(patient1Name, 3) 
				&& mp.checkTabOrderPosition("Registration/Check-In", 4)  
				&& mp.checkTabOrderPosition((mp.menuFinancial), 5)
				&& mp.checkTabOrderPosition((mp.menuSmartFiles), 6)	? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true) 
				{
				System.out.println("***VERIFY PASSED +++  (after p1p2p3 np1np2np3 p1p2p3) Tab order is correct "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+  "Verified the tab order is correct "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			else
				{
				System.out.println("***VERIFY FAILED --- (after p1p2p3 np1np2np3 p1p2p3) Tabs are NOT ordered correctly "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+               "Tabs are NOT ordered correctly "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			prevStepResult = currStepResult;



			//CLICK 1st non-patient VERIFY TAB ORDER IS pat3, pat2, pat1, non3, non2, non1 
			System.out.println("* * * TEST STEP * * *  Click 1st non-patient tab = " + mp.menuSmartFiles + ", Verify tab order is pat3, pat2, pat1, non3, non2, non1 (after p1p2p3 np1np2np3 p1p2p3 np1)" );
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				mp.clickElement(mp.getOpenTab(mp.menuSmartFiles), "Open tab for 1st non-patient " + mp.menuSmartFiles ) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click non-pat1 tab = " +mp.menuSmartFiles , currStepResult);
			prevStepResult = currStepResult;
			//VERIFY TAB ORDER == pat3, pat2, pat1, non3, non2, non1"
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
			mp.checkTabOrderPosition(patient3Name, 1) 
			&& mp.checkTabOrderPosition(patient2Name, 2) 
			&& mp.checkTabOrderPosition(patient1Name, 3) 
			&& mp.checkTabOrderPosition("Registration/Check-In", 4)  
			&& mp.checkTabOrderPosition((mp.menuFinancial), 5)
			&& mp.checkTabOrderPosition((mp.menuSmartFiles), 6)	? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true) 
				{
				System.out.println("***VERIFY PASSED +++  (after p1p2p3 np1np2np3 p1p2p3 np1) Tab order is correct "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+  "Verified the tab order is correct "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			else
				{
				System.out.println("***VERIFY FAILED --- (after p1p2p3 np1np2np3 p1p2p3 np1) Tabs are NOT ordered correctly "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+               "Tabs are NOT ordered correctly "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			prevStepResult = currStepResult;

			
			

			//CLICK 2nd non-patient VERIFY TAB ORDER IS pat3, pat2, pat1, non3, non2, non1 
			System.out.println("* * * TEST STEP * * *  Click 2nd non-patient tab = " + mp.menuFinancial + ", Verify tab order is pat3, pat2, pat1, non3, non2, non1 (after p1p2p3 np1np2np3 p1p2p3 np1np2)" );
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				mp.clickElement(mp.getOpenTab(mp.menuFinancial), "Open tab for 1st non-patient " + mp.menuFinancial ) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click non-pat2 tab = " +mp.menuFinancial , currStepResult);
			prevStepResult = currStepResult;
			//VERIFY TAB ORDER == pat3, pat2, pat1, non3, non2, non1"
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
			mp.checkTabOrderPosition(patient3Name, 1) 
			&& mp.checkTabOrderPosition(patient2Name, 2) 
			&& mp.checkTabOrderPosition(patient1Name, 3) 
			&& mp.checkTabOrderPosition("Registration/Check-In", 4)  
			&& mp.checkTabOrderPosition((mp.menuFinancial), 5)
			&& mp.checkTabOrderPosition((mp.menuSmartFiles), 6)	? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true) 
				{
				System.out.println("***VERIFY PASSED +++  (after p1p2p3 np1np2np3 p1p2p3 np1np2) Tab order is correct "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+  "Verified the tab order is correct "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			else
				{
				System.out.println("***VERIFY FAILED --- (after p1p2p3 np1np2np3 p1p2p3 np1np2) Tabs are NOT ordered correctly "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+               "Tabs are NOT ordered correctly "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			prevStepResult = currStepResult;

			
			

			//CLICK 3rd non-patient VERIFY TAB ORDER IS pat3, pat2, pat1, non3, non2, non1 
			System.out.println("* * * TEST STEP * * *  Click 3rd non-patient tab = " +mp.menuRegistrationDash + ", Verify tab order is pat3, pat2, pat1, non3, non2, non1 (after p1p2p3 np1np2np3 p1p2p3 np1np2np3)" );
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				mp.clickElement(mp.getOpenTab(mp.menuRegistrationDash), "Open tab for 1st non-patient " +mp.menuRegistrationDash ) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click non-pat3 tab = " +mp.menuRegistrationDash , currStepResult);
			prevStepResult = currStepResult;
			//VERIFY TAB ORDER == pat3, pat2, pat1, non3, non2, non1"
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
			mp.checkTabOrderPosition(patient3Name, 1) 
			&& mp.checkTabOrderPosition(patient2Name, 2) 
			&& mp.checkTabOrderPosition(patient1Name, 3) 
			&& mp.checkTabOrderPosition("Registration/Check-In", 4)  
			&& mp.checkTabOrderPosition((mp.menuFinancial), 5)
			&& mp.checkTabOrderPosition((mp.menuSmartFiles), 6)	? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true) 
				{
				System.out.println("***VERIFY PASSED +++  (after p1p2p3 np1np2np3 p1p2p3 np1np2np3) Tab order is correct "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+  "Verified the tab order is correct "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			else
				{
				System.out.println("***VERIFY FAILED --- (after p1p2p3 np1np2np3 p1p2p3 np1np2np3) Tabs are NOT ordered correctly "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th" );
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+               "Tabs are NOT ordered correctly "
					+ "(3rd patient "+patient3Name + " 1st, 2nd patient " +patient2Name + " 2nd, 1st patient " +patient1Name + " 3rd, 3rd non-patient (Registration/Check-In) 4th, 2nd non-patient " + mp.menuFinancial + " 5th, 1st non-patient " + mp.menuSmartFiles + " 6th )", currStepResult);
				}
			prevStepResult = currStepResult;

			




		

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


