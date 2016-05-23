package com.nextgen.qa.automation.smoketest;

//import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.nextgen.qa.automation.pages.*;
import com.nextgen.qa.automation.toolbox.*;


public class UIRecon_RegistrationWizard extends NG7TestCase {

	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "UIRecon_RegistrationWizard";
		NG7TestCase.tester = "Maria Garcia-Bodoh";
		
		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		MainPage mp = new MainPage(driver, "mainpage");
		LoginPage lp = new LoginPage(driver, "loginpage");
		RegistrationPage rp = new RegistrationPage(driver, "registrationpage");
		ErrorPage ep = new ErrorPage(driver, "errorpage");
		
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		String preReq = null;
		
		System.out.println("PROD: registration, before login");
		
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
			currStepResult = !preReq.equals("Pass") ? "Blocked" :
				mp.selectHomeMenuItem("registration") ? "Pass" : "Fail"; // MGB 10/28/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Registration from the Home menu", currStepResult);
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_RegistrationWizard: Click Registration from the Home menu");
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				rp.wizardExists() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that Registration wizard opened", currStepResult);
			String openReg_PreReq = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_RegistrationWizard: Verify Registration wizard opens after clicking Registration menu item");
							
			// test step
			if (mp.checkErrorPage()) Artifact.ReportDoNotDeliverFail(currStepResult, "UIReconRegistrationWizard: Exception detected after selecting Registration from menu");
					
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				rp.checkElementIsVisible(rp.saveButton, "registration wizard save button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Registration wizard contains Save button", currStepResult);
				
			// test step
			WebElement cancelButton = GeneralMethods.FindVisibleObject(driver, rp.cancelButtonCss);
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				cancelButton != null ? "Pass" : "Fail"; // MGB 7/21/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Registration wizard contains Cancel button", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				rp.checkElementIsVisible(rp.checkinButton, "registration wizard check-in button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Registration wizard contains Check In button", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				rp.clickWizardTab("Demographics") ? "Pass" : 
					rp.clickWizardTab("General Demographics") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Demographics tab on Registration wizard", currStepResult);
			String preReq_demographicsTab = currStepResult;

			// test step
			GeneralMethods.delay(eventDelay*2);
			prevStepResult = currStepResult;
			currStepResult = (preReq_demographicsTab.equals("Pass") == false) ? "Blocked" :
				rp.checkElementIsVisible(rp.searchField, "registration wizard search field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify there is a Search field on the Demographics tab", currStepResult);
				
			// test step
			GeneralMethods.delay(eventDelay*2);
			prevStepResult = currStepResult;
			currStepResult = (preReq_demographicsTab.equals("Pass") == false) ? "Blocked" :
				rp.checkElementIsVisible(rp.firstName, "registration wizard first name field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify there is a First Name field on the Demographics tab", currStepResult);

			// test step
			GeneralMethods.delay(eventDelay*2);
			prevStepResult = currStepResult;
			currStepResult = (preReq_demographicsTab.equals("Pass") == false) ? "Blocked" :
				rp.checkElementIsVisible(rp.middleName, "registration wizard middle name field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify there is a Middle Name field on the Demographics tab", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_demographicsTab.equals("Pass") == false) ? "Blocked" :
				rp.checkElementIsVisible(rp.lastName, "registration wizard last name field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify there is a Last Name field on the Demographics tab", currStepResult);

			// test step
			WebElement sexField = null;
			List<WebElement> sexFieldList = null;
			if (preReq_demographicsTab.equals("Pass")){
				sexField = GeneralMethods.FindVisibleObject(driver, rp.sexCss);
			   sexFieldList = GeneralMethods.FindElementsInObjHierarchy(sexField, rp.sexSelectListCss);
			}
			prevStepResult = currStepResult;
			currStepResult = (preReq_demographicsTab.equals("Pass") == false) ? "Blocked" :
				sexField != null ? "Pass" : "Fail"; // MGB 7/21/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify there is a Sex field on the Demographics tab", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_demographicsTab.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(sexField, rp.genderSelectList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Sex list is not empty", currStepResult);
			
			// test step
			WebElement genderField = null;
			List<WebElement> genderFieldList = null;
			if (preReq_demographicsTab.equals("Pass")){
			   genderField = GeneralMethods.FindVisibleObject(driver, rp.genderCss);
			   genderFieldList = GeneralMethods.FindElementsInObjHierarchy(genderField, rp.genderSelectListCss);
			}
			prevStepResult = currStepResult;
			currStepResult = (preReq_demographicsTab.equals("Pass") == false) ? "Blocked" :
				genderField != null ? "Pass" : "Fail"; // MGB 7/21/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify there is a Gender field on the Demographics tab", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_demographicsTab.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(genderField, rp.genderSelectList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Gender list is not empty", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_demographicsTab.equals("Pass") == false) ? "Blocked" :
				rp.checkElementIsVisible(rp.datePicker, "registration wizard DOB field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify there is a DOB field on the Demographics tab", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				rp.clickWizardTab("Extended Demographics") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Extended Demographics tab on Registration wizard", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				rp.clickWizardTab("Contact Information") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Contact Information tab on Registration wizard", currStepResult);
												
			// test step
			//WebElement contactMethodField = rp.getFieldFromList(rp.preferredContactAddressList, "addressType0_");
			//WebElement contactMethodField = rp.getFieldFromList(rp.preferredContactAddressList, "addressTypepersonId0_");
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				//contactMethodField != null ? "Pass" : "Fail";
				GeneralMethods.checkIsNotEmpty(rp.preferredContactAddressList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Preferred Contact Method Address0 item is visible", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				rp.clickWizardTab("Relationships") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Relationships tab on Registration wizard", currStepResult);

			// MGB 3/6/2015: added Add Relationship link check and guarantor 18 and older message
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.FindVisibleObject(driver, rp.addRelationshipLinkCss) != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify there is a +Relationship link in the Relationships tab", currStepResult);		
			
			// test step
			WebElement msg = null;
			if (prevStepResult.equals("Pass"))
				msg = GeneralMethods.FindVisibleObject(driver, rp.minorAlertMsgCss);
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				 msg != null && msg.getText().contains("18 years or older")? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify there is a 'Guarantor must be 18 years or older' message in the Relationships tab", currStepResult);	
			
			// MGB 3/6/2015: removed Relationships tab person search field check
/*			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.FindVisibleObject(driver, rp.searchFieldCss) != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify there is a person search field in the Relationships tab", currStepResult);		
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				rp.clickWizardTab("Employers") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Employers tab on Registration wizard", currStepResult);
*/				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				rp.clickWizardTab("Insurance") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Insurance tab on Registration wizard", currStepResult);
/*
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				rp.clickWizardTab("Patient Providers") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Providers tab on Registration wizard", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				rp.clickWizardTab("Organization Defined Fields") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Organization Defined Fields tab on Registration wizard", currStepResult);
*/			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				rp.clickWizardTab("Visit Information") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Visit Information tab on Registration wizard", currStepResult);
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openReg_PreReq.equals("Pass") == false) ? "Blocked" :
				rp.clickWizardTab("Print Forms") ? "Pass" : 
					rp.clickWizardTab("Forms") ? "Pass" :"Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Forms tab on Registration wizard", currStepResult);

			// test step
			prevStepResult = currStepResult;
	        currStepResult = (!openReg_PreReq.equals("Pass") && cancelButton == null) ? "Blocked" :
				GeneralMethods.ClickButton(cancelButton) ? "Pass" : "Fail"; // MGB /21/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Cancel button on Registration wizard", currStepResult);
		}
						
		// Cleanup
		if (preReq.equals("Pass"))
			mp.logOutUser();
		driver.quit();
		driver = null;
		
		Artifact.CloseArtifact(artifact);
	}	
}
