package com.nextgen.qa.automation.smoketest;

//import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.nextgen.qa.automation.pages.*;
import com.nextgen.qa.automation.toolbox.*;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;


public class UIRecon_SchedulerNewAppointmentWizard extends NG7TestCase {
	  
	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "UIRecon_SchedulerNewAppointmentWizard";	
		NG7TestCase.tester = "Maria Garcia-Bodoh";
		
		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		MainPage mp = new MainPage(driver, "mainpage");
		LoginPage lp = new LoginPage(driver, "loginpage");
		SchedulerPage sp = new SchedulerPage(driver, "schedulerpage.txt");
	    NewAppointmentPage nap = new NewAppointmentPage(driver, "newapptpage.txt");
	    ErrorPage ep = new ErrorPage(driver, "errorpage");
	
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

		// Run in local stress loop to make sure the test is robust
		for (int i = 1; i <= localStressLoop; i++){
		    if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
		    System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
		    GeneralMethods.prepForIteration();
		    
			// test step
			currStepResult = !preReq.equals("Pass") ? "Blocked" :
				mp.selectHomeMenuItem("scheduler") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Scheduler from the Home menu", currStepResult);
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_SchedulerNewAppointmentWizard: Click Scheduler from the Home menu");
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				sp.wizardExists() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that Scheduler opened", currStepResult);
			String openSched_PreReq = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_SchedulerNewAppointmentWizard: Verify Scheduler opens after clicking Scheduler menu item");
			
			// test step
			GeneralMethods.delay(eventDelay);
			prevStepResult = currStepResult;
			currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" :
				sp.checkElementIsVisible(sp.scheduler, "scheduler page") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Scheduler is displayed", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" :
			    sp.toggleResourcesPane("collapse") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Collapse Resources pane", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" :
			    sp.toggleResourcesPane("expand") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Expand Resources pane", currStepResult);

			// test step
			GeneralMethods.delay(eventDelay);
			prevStepResult = currStepResult;
			currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.checkListIsDisplayed(sp.resourceList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Resource List is not empty", currStepResult);
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_SchedulerNewAppointmentWizard: Verify Scheduler Resource List is not empty");
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" :
				//GeneralMethods.checkIsDisplayed(sp.weeklyViewButtonGroup) ? "Pass" : "Fail";
				sp.checkElementIsVisible(sp.weeklyViewButtonGroup, "weekly view button group") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Weekly View button group is displayed", currStepResult);
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" :
				sp.checkElementIsVisible(GeneralMethods.FindElementInObjHierarchy(sp.weeklyViewButtonGroup, sp.weeklyViewDay), "weekly view day button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Weekly View Day button is displayed", currStepResult);	
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" :
				sp.checkElementIsVisible(GeneralMethods.FindElementInObjHierarchy(sp.weeklyViewButtonGroup, sp.weeklyViewWeek5), "weekly view week 5 button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Weekly View Week (5) button is displayed", currStepResult);				
		
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" :
				sp.checkElementIsVisible(GeneralMethods.FindElementInObjHierarchy(sp.weeklyViewButtonGroup, sp.weeklyViewWeek7), "weekly view week 7 button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Weekly View Week (7) button is displayed", currStepResult);	
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" :
				sp.checkElementIsVisible(sp.addApptButton, "Add Appointment button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Add Appointment button", currStepResult);	
							
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" : 
				sp.clickNewApptButton() ? "Pass" : "Fail" ;
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Add Appointment button", currStepResult);
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_SchedulerNewAppointmentWizard: Click Add Appointment button");
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.wizardExists() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that New Appointment wizard pops up", currStepResult);
			String preReq_openWizard = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "UIRecon_SchedulerNewAppointmentWizard: Verify Add Appointment wizard after Add Appointment button is clicked");
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openWizard.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.saveButton, "new appointment wizard save button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify New Appointment wizard contains Save button", currStepResult);
			
			// test step
			WebElement cancelButton = GeneralMethods.FindVisibleObject(driver, nap.cancelButtonCss);
			prevStepResult = currStepResult;
			currStepResult = (preReq_openWizard.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(cancelButton, "new appointment wizard cancel button") ? "Pass" : "Fail"; // MGB 7/29/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify New Appointment wizard contains Cancel button", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openWizard.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.checkinButton, "new appointment wizard check in button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify New Appointment wizard contains Check In button", currStepResult);
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openWizard.equals("Pass") == false) ? "Blocked" :
				nap.clickWizardTab("General Demographics") ? "Pass" :"Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click General Demographics tab on New Appointment window", currStepResult);
			String preReq_demographicsTab = currStepResult;
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_demographicsTab.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.searchField, "new appointment wizard search field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify there is a Search field on the Patient tab", currStepResult);
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_demographicsTab.equals("Pass") == false) ? "Blocked" :
				//nap.checkElementIsVisible(nap.noPatientOption, "new appointment wizard no patient option button") ? "Pass" : "Fail";
				nap.clickElement(nap.noPatientOption, "No Patient checkbox") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify there is a No Patient button on the Patient tab", currStepResult);
		    	
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openWizard.equals("Pass") == false) ? "Blocked" :
				nap.clickWizardTab("Contact Information") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Contact Information tab on New Appointment window", currStepResult);
							
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openWizard.equals("Pass") == false) ? "Blocked" :
				nap.clickWizardTab("Relationships") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Relationships tab on New Appointment window", currStepResult);
            	
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openWizard.equals("Pass") == false) ? "Blocked" :
				nap.clickWizardTab("Insurance") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Insurance tab on New Appointment window", currStepResult);
							
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openWizard.equals("Pass") == false) ? "Blocked" :
				nap.clickWizardTab("Appointment") == true ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Appointment tab on New Appointment window", currStepResult);
			String preReq_apptTab = currStepResult;

			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_apptTab.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.apptDateField, "new appointment wizard appt date field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Appt tab contains a Date field", currStepResult);
						
			// test step
			WebElement resourceField = GeneralMethods.FindVisibleObject(driver, nap.resourceFieldCss);
			prevStepResult = currStepResult;
			currStepResult = (preReq_apptTab.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(resourceField, "new appointment wizard resource field") ? "Pass" : "Fail"; // MGB 7/30/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Appt tab contains a Resources field", currStepResult);
						
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(resourceField, nap.apptResourceFieldList) ? "Pass" : "Fail"; // MGB 7/30/2014
				//GeneralMethods.clickFieldFindListItems(nap.newAppointmentWizard, resourceField) ? "Pass" : "Fail"; // MGB 7/30/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Resources list is not empty", currStepResult);
			MouseMethods.HoverOverClick(driver, nap.apptResourceField, -5, 0); // click away from Resources field
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_apptTab.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.apptTimeField, "new appointment wizard time field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Appt tab contains a Time field", currStepResult);
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(nap.apptTimeField, nap.apptTimeFieldList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Time list is not empty", currStepResult);
			//GeneralMethods.scrollOnElement(driver, nap.modalContent, "down");
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_apptTab.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.apptLocationField, "new appointment wizard location field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Appt tab contains a Service Location field", currStepResult);
			//GeneralMethods.scrollOnElement(driver, nap.modalContent, "down");
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(nap.apptLocationField, nap.apptLocationFieldList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Service Location list is not empty", currStepResult);
			//GeneralMethods.scrollOnElement(driver, nap.modalContent, "down");
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_apptTab.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.apptTypeField, "new appointment wizard appt type field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Appt tab contains a Appointment Type field", currStepResult);
			//GeneralMethods.scrollOnElement(driver, nap.modalContent, "down");
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(nap.apptTypeField, nap.apptTypeFieldList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Type list is not empty", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openWizard.equals("Pass") == false) ? "Blocked" :
				nap.clickSearchAheadTab() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on Search Ahead tab to open the Search Ahead panel", currStepResult);
			
			// test step
			GeneralMethods.delay(eventDelay*2);
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.searchAheadPanel, "search ahead panel") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead opens", currStepResult);
			String preReq_openSearchAhead = currStepResult; 

			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openSearchAhead.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.searchAheadEndTimeField, "search ahead end time select field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead panel contains a End time select field", currStepResult); 

			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(nap.searchAheadEndTimeField, nap.searchAheadEndTimeFieldList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead End time list is not empty", currStepResult);			
			//GeneralMethods.scrollOnElement(driver, nap.searchAheadModalContent, "down");
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openSearchAhead.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.searchAheadStartDateField, "search ahead start date field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead panel contains a Beginning Date picker field", currStepResult); 
			//GeneralMethods.scrollOnElement(driver, nap.searchAheadModalContent, "down");
						
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openSearchAhead.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.searchAheadDurationField, "search ahead duration field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead panel contains a Duration field", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(nap.searchAheadDurationField, nap.searchAheadDurationFieldList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead Duration list is not empty", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			//WebElement searchAheadApptTypeField = (nap.searchAheadAppTypeField != null) ? nap.searchAheadAppTypeField : nap.searchAheadAppTypeField2;
			currStepResult = (preReq_openSearchAhead.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.searchAheadAppTypeField, "search ahead appointment type field") ? "Pass" :"Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead panel contains an Appointment Type field", currStepResult+WIP); 
			
			// test step
			NG7TestCase.testComment = "WIP: the test result for the step will be ignored";
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(nap.searchAheadAppTypeField, nap.searchAheadApptTypeFieldList) ? "Pass" :"Fail";
				//GeneralMethods.clickFieldFindListItems(nap.newAppointmentWizard, nap.searchAheadAppTypeField) ? "Pass" : "Fail"; // MGB 12/16/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead Appointment Type list is not empty", currStepResult+WIP);
			//GeneralMethods.scrollOnElement(driver, nap.searchAheadModalContent, "down");
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openSearchAhead.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.searchAheadResourceField, "search ahead resource select field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead panel contains a Resource select field", currStepResult+WIP); 
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(nap.searchAheadResourceField, nap.searchAheadResourceFieldList) ? "Pass" : "Fail";
				//GeneralMethods.clickFieldFindListItems(nap.searchAheadResourceField, resourceField) ? "Pass" : "Fail"; // MGB 12/16/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead Resource list is not empty", currStepResult+WIP);

			if (! currStepResult.equals("Blocked")) MouseMethods.HoverOverClick(nap.driver, nap.searchAheadResourceField, nap.searchAheadResourceField.getSize().getWidth() + 5, 0);
			
 			// test step
			//WebElement searchAheadLocationField = nap.findVisibleDropdownField(nap.newAppointmentWizard, "appointmentSearchAhead.OrganizationId", "Search Ahead location pulldown field");
			prevStepResult = currStepResult;
			currStepResult = (preReq_openSearchAhead.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.searchAheadLocationField, "search ahead location select field") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead panel contains a Location select field", currStepResult); 	
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(nap.searchAheadLocationField, nap.searchAheadLocationFieldList) ? "Pass" : "Fail";
				//GeneralMethods.clickFieldFindListItems(nap.newAppointmentWizard, nap.searchAheadLocationField) ? "Pass" : "Fail"; // MGB 12/16/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead Location list is not empty", currStepResult+WIP);			
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openSearchAhead.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.checkIsNotEmpty(nap.searchAheadDaysGroup) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead panel contains a Days of the Week selector group", currStepResult); 	
				
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_openSearchAhead.equals("Pass") == false) ? "Blocked" :
				nap.checkElementIsVisible(nap.searchAheadSuggestButton, "search ahead suggest button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Search Ahead panel contains a Suggest button", currStepResult);	
				
			// test step
			prevStepResult = currStepResult;
	        currStepResult = (preReq_openSearchAhead.equals("Pass") == false) ? "Blocked" :
	        	nap.clickSearchAheadTab() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on Search Ahead tab to close the Search Ahead panel", currStepResult);
				
			// test step
			prevStepResult = currStepResult;
	          currStepResult = (preReq_openWizard.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.ClickButton(cancelButton) ? "Pass" : "Fail"; // MGB 7/30/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Cancel button on New Appointment window", currStepResult);
		}	
			
		// Cleanup
    	if (preReq.equals("Pass"))
    		mp.logOutUser();
		driver.quit();
		driver = null;
		
		Artifact.CloseArtifact(artifact);
	}
}



