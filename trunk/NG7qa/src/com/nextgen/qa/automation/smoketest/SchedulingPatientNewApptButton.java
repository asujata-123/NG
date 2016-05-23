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
import com.nextgen.qa.automation.toolbox.Artifact;
import com.nextgen.qa.automation.toolbox.AutomationSettings;
import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.toolbox.NG7TestCase;
import com.thoughtworks.selenium.Wait;

import java.awt.RenderingHints.Key;
import java.io.BufferedWriter;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Wait;

/**
 * @author Mbodoh<li>
 * Smoke Test: Scheduling:<li>
 * Open Scheduler via Calendar tab<li>
 * Click Add Appointment button<li>
 * Verify New Appointment wizard pops up<li>
 * Cancel New Appointment creation<li>
 */
public class SchedulingPatientNewApptButton extends NG7TestCase {
	public static void test(WebDriver driver, MainPage mp, SchedulerPage sp, NewAppointmentPage nap) throws Exception {
		NG7TestCase.testName = "SchedulingPatientNewApptButton";
		NG7TestCase.tester = "Maria Garcia-Bodoh";
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp); // MGB 5/5/2014

		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		String preReq = null;
		String reqFields = "";
			
		// Test data
		String patientName = "Sarah Miller";
		String patientNameAlpha = "Miller, Sarah";
		String apptTime = "2:00 PM";
		
		// test step // MGB 3/18/2014 updated to use configurable layout variable
     	String layout = AutomationSettings.getTestDataItem("layout_"+NG7TestCase.build); 
     	currStepResult=	mp.handleWelcome(layout) ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"If layout selector comes up select "+layout, currStepResult);	
		preReq = currStepResult;
		
		for (int i = 1; i <= localStressLoop; i++){
			if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
			System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
			GeneralMethods.prepForIteration();
		    
			// test step
			currStepResult = mp.selectHomeMenuItem("Scheduler") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click 'Scheduler' from the Home menu", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				sp.wizardExists() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that Scheduler opened", currStepResult);
			String openSched_PreReq = currStepResult;
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" : 
				sp.clickNewApptButton() ? "Pass" : "Fail" ;
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Add Appointment button", currStepResult);
		
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.wizardExists() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that New Appointment wizard pops up", currStepResult);
		
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.clickCancel() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Cancel Appointment creation by clicking the Cancel button on the New Appointment wizard", currStepResult);

			/*
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.wizardClosed() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that New Appointment wizard closes", currStepResult);
			*/
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" : 
				sp.clickNewApptButton() ? "Pass" : "Fail" ;
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Add Appointment button", currStepResult);
			
			// test step
			GeneralMethods.delay(5000);
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.attachPatient(patientName) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Attach patient " +patientName +" to the appointment", currStepResult); 
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				//nap.clickWizardTab("Appt") ? "Pass" : "Fail";
				nap.clickWizardTab("Appointment") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on Appointment tab", currStepResult);
			
			String apptDate = "";
			//String apptTime = "";
			String resource = "";
			String apptType = "";
			String apptDuration = "";
			String apptLocation = "";
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.setApptDate("", true) ? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true) apptDate = nap.getApptDate();
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the New Appointment Date field with date " +apptDate, currStepResult);
		
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.setApptResource("Guillermo Martinez, MD", false) ? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true) resource = nap.getApptResource();
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the New Appointment Resources field with "+resource, currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.setApptTime(apptTime, false) ? "Pass" : "Fail";
			//if (currStepResult.equals("Pass") == true) try {apptTime = nap.getApptTime();} catch (Exception e) {}
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the New Appointment Time field with time "+apptTime, currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.setApptLocation("", true) ? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true) apptLocation = nap.getApptLocation();
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the New Appointment Service Location field with "+apptLocation, currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.setApptType("", true) ? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true) apptType = nap.getApptType();
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the New Appointment Type field with "+apptType, currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.setApptDuration("1 Hr ", false) ? "Pass" : "Fail";
				//nap.setApptDuration("1 hr", false) ? "Pass" : "Fail";
				//nap.setApptDuration("60 mins", false) ? "Pass" : "Fail";
			if (currStepResult.equals("Pass") == true) apptDuration = nap.getApptDuration();
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the New Appointment Duration field with 1 Hr", currStepResult);
			
			// test step
			GeneralMethods.delay(1000);
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.ClickButton(nap.viewConflictsButton) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"View appointment conflicts", currStepResult);
						
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.ClickButton(nap.ignoreConflictsButton) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Ignore appointment conflicts", currStepResult);

/*			// test step
			prevStepResult = currStepResult;
			currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
				nap.clickWizardTab("Contact Information") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Contact Information tab on New Appointment window", currStepResult);
							
			// test step
			prevStepResult = currStepResult;
			currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
				nap.populatePulldownValue(nap.preferredContactMethod, nap.preferredContactMethodList, "Preferred Contact Method", "", "option", true) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate Preferred Contact Method field on Contact Information tab", currStepResult);
*/
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				//nap.clickContinue() ? "Pass" : "Fail";
				nap.clickSave() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Continue/Save", currStepResult);
/*						
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				//sm.clickSave() ? "Pass" : "Fail";
				//sm.clickDismissConflictsSaveButton() ? "Pass" : "Fail";
				GeneralMethods.ClickVisibleButton(driver, "input[ng-click='dismissConflictsAndSave()']") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click the Save button", currStepResult);
*/			
			sp.deselectAllResources();
						
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				sp.selectResource(resource, false) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select resource " +resource +"", currStepResult);
						
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				sp.populateJumpToDate(apptDate) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the Jump to date field with the appointment date "+apptDate, currStepResult);
			String jtd_PreReq = currStepResult;
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				sp.scrollToHour(apptTime) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Scroll to the appointment time "+apptTime, currStepResult);
			 
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				 sp.checkDateContainerMatch(apptDate)? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the calendar banner contains the appointment date", currStepResult);
			GeneralMethods.delay(5000);  // wait 5 seconds
			
			// test step
			prevStepResult = currStepResult;
			WebElement apptInOverlay = null;
			currStepResult = (jtd_PreReq.equals("Fail") || jtd_PreReq.equals("Blocked")) ? "Blocked" :
				sp.getAppointmentFromOverlay(patientNameAlpha) == null ? "Fail" : "Pass";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Find appointment for " +patientNameAlpha +" in appointment overlay for selected date and provider", currStepResult);
			
			// test step
			WebElement apptPill = sp.getAppointmentFromOverlay(patientNameAlpha);
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				apptPill != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the appointment popUp is visible when the appointment pill is clicked", currStepResult); // TODO	
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				sp.getApptDetails(apptPill) == null ? "Fail" : "Pass";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Get the appointment details object from the pill", currStepResult);
						
			/*// TODO: Check that the appointment popUp includes all the entered information
			prevStepResult = currStepResult;
			boolean dataCheck = false;
			if (prevStepResult.equals("Pass")) {
				dataCheck = (popUp.getText().contains(location)) ? true : false;
				if (duration == "1 Hr") duration = "60 Minutes";
				dataCheck &= (popUp.getText().contains(duration)) ? true : false;
			}
			currStepResult = (dataCheck == true) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+" Check that the appointment popUp includes the entered information", currStepResult+WIP);
			*/
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				sp.selectCancelationReason(driver, "Appt No Longer Needed") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Cancel appointment with reason Appt No Longer Needed", currStepResult);
			
			// test step
			GeneralMethods.delay(eventDelay); // tempWorkaround
			apptPill = sp.getAppointmentFromOverlay(patientNameAlpha);
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.checkIsDisplayed(apptPill)==false ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the appointment popUp is no longer visible after cancellation", currStepResult);	
					
			// Cleanup
			mp.closeAllOpenTabs();
			
		} // stress loop closing bracket
		Artifact.CloseArtifact(artifact);
	}
}
