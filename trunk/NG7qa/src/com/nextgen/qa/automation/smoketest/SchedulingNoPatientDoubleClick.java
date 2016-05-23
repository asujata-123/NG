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
import com.nextgen.qa.automation.toolbox.MouseMethods;
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
 * Smoke Test: SchedulingNoPatientDoubleClick<li>
 */
public class SchedulingNoPatientDoubleClick extends NG7TestCase {
	public static void test() throws Exception {
		NG7TestCase.testName = "SchedulingNoPatientDoubleClick";
		NG7TestCase.tester = "Maria Garcia-Bodoh";
		
		WebDriver driver = GeneralMethods.startDriver();

		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		LoginPage lp = new LoginPage(driver, "loginpage");
		MainPage mp = new MainPage(driver, "mainpage");
		SchedulerPage sp = new SchedulerPage(driver, "schedulerpage");
		NewAppointmentPage nap = new NewAppointmentPage(driver, "newappointmentpage");		
		
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		String preReq = null;
		String reqFields = "";
			
		// Test data
		String patientNameAlpha = "No Patient";
		String apptDate = GeneralMethods.GenerateDateAroundToday(12);
		String apptTime = "11:00 AM";
		//String resource = "Graciela Hernandez, MD";
		String resource = AutomationSettings.getTestDataItem("resource"+NG7TestCase.orgSelect);
		String orgString = NG7TestCase.orgString;
		String [] orgStringList = orgString.split(";");
		String apptLocation = orgStringList[orgStringList.length-1];
		
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
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Log in to "+GeneralMethods.getDeployment()+"; code="+loginSuccessCode, currStepResult); // MGB 5/5/2014
		preReq = currStepResult;
		
		// Run in local stress loop to make sure the test is robust
		if (loginSuccessCode.equals("00")) {
			for (int i = 1; i <= localStressLoop; i++){
				if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
				System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
				GeneralMethods.prepForIteration();
			    
				// test step
				currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
					mp.selectHomeMenuItem("scheduler") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Scheduler from the Home menu", currStepResult);
				String openSched_PreReq = currStepResult;
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" :
					sp.populateJumpToDate(apptDate) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the Jump to date field with the appointment date "+apptDate, currStepResult);
				
				// test step
				if (openSched_PreReq.equals("Pass")) sp.deselectAllResources();
				prevStepResult = currStepResult;
				currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" :
					sp.selectResource(resource, false) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select resource " +resource +"", currStepResult);

				// test step
				GeneralMethods.delay(eventDelay);
				prevStepResult = currStepResult;
				currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
					GeneralMethods.checkIsDisplayed(sp.apptOverlay) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check the resource's apptOverlay object is displayed", currStepResult);
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
					sp.clickElement(GeneralMethods.FindElementInObjHierarchy(sp.weeklyViewButtonGroup, sp.weeklyViewDay, ""), "Day view button") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click the Day view button", currStepResult);
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass")== false ? "Blocked" :
					//MouseMethods.HoverOverDblClick(driver, sp.apptOverlay, 100, 100) ? "Pass" : "Fail";
					MouseMethods.HoverOverDblClick(driver, sp.scrollDate, 300, sp.scrollDate.getSize().height/2) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Double-click on the scheduler grid of " +resource, currStepResult);
				GeneralMethods.delay(5000);
				
				// test step
				prevStepResult = currStepResult;
		
				currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
					nap.wizardExists() ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that New Appointment wizard pops up", currStepResult);
				String openNAP_PreReq = currStepResult;

			// test step
				prevStepResult = currStepResult;
				currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
					nap.clickCancel() ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Cancel on Add Appointment wizard", currStepResult);
				String clickedCancel = currStepResult;
				
				/// test step
				NG7TestCase.testData = "patientName='No Patient' apptDate="+apptDate+" apptTime="+apptTime+" resource="+resource;
				prevStepResult = currStepResult;
				currStepResult = (openSched_PreReq.equals("Pass") == false) ? "Blocked" :
					sp.createAppointment(nap, apptDate, apptTime, "", "1 Hr ", apptLocation, 
							"Address Type", "Home Address", "123 Main Street", "", "Austin", "Texas", "78759",
							resource, "", "") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Create an appointment", currStepResult);
				String closedNAPWizard = GeneralMethods.checkElementIsNotVisible(nap.driver, nap.newAppointmentWizard) ? "Pass" : "Fail";
				System.out.println("TEST closedNAPWizard="+closedNAPWizard);
				
				// MGB 9/15/2014: click cancel if the previous step failed
				// test step
				prevStepResult = currStepResult;
				if (!closedNAPWizard.equals("Pass")){
					System.out.println("closedNAPWizard="+closedNAPWizard);
					currStepResult = nap.clickCancel() ? "Pass" : "Fail";
					closedNAPWizard = GeneralMethods.checkElementIsNotVisible(nap.driver, nap.newAppointmentWizard) ? "Pass" : "Fail";
					System.out.println("closedNAPWizard="+closedNAPWizard);
				}
				WebElement apptPill = null;
				if (closedNAPWizard.equals("Pass")) apptPill = sp.getAppointmentFromOverlay(patientNameAlpha); 
				
/*
				// test step
				WebElement apptPill = null;
				if (closedNAPWizard.equals("Pass")){
					if (closedNAPWizard.equals("Pass")) apptPill = sp.getAppointmentFromOverlay(patientNameAlpha);
					prevStepResult = currStepResult;
					currStepResult = (closedNAPWizard.equals("Pass") == false) ? "Blocked" :
						apptPill == null ? "Fail" : "Pass";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Find appointment for " +patientNameAlpha +" in appointment overlay for selected date and provider", currStepResult); 

					// Scroll until appointment pill is visible
					int iterations = 15;
					while (apptPill == null && iterations > 0) {
						System.out.println("Scrolling to find the apptPill: "+iterations);
						GeneralMethods.scrollOnElement(driver, sp.scrollBar, "down");
						GeneralMethods.delay(500);
						apptPill = sp.getAppointmentFromOverlay(patientNameAlpha);
						iterations = iterations - 1;
					}	
				}
*/				
				// test step
				prevStepResult = currStepResult;
				currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
					apptPill == null ? "Fail" : "Pass";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Find appointment for " +patientNameAlpha +" in appointment overlay for selected date and provider", currStepResult); 

				// test step
				prevStepResult = currStepResult;
				currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
					sp.getApptDetails(sp.getAppointmentFromOverlay(patientNameAlpha)) == null ? "Fail" : "Pass";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the appointment popUp is visible when the appointment pill is clicked", currStepResult);	

				// Cleanup
				if (openNAP_PreReq.equals("Pass") || openSched_PreReq.equals("Pass"))
				   mp.closeAllOpenTabs();	
	    		
	        }
			
			if (preReq.equals("Pass")) 
				mp.logOutUser();
			driver.quit();
			driver = null;
			
			Artifact.CloseArtifact(artifact);
		    }
      }
}