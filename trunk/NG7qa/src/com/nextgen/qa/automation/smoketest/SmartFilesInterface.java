package com.nextgen.qa.automation.smoketest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
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


public class SmartFilesInterface extends NG7TestCase {

	public static void test() throws Exception {
		NG7TestCase.testName = "SmartFilesInterface";	
		NG7TestCase.tester = "Maria Garcia-Bodoh";
		
		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		LoginPage lp = new LoginPage(driver, "loginpage");
		MainPage mp = new MainPage(driver, "mainpage");
		SmartFilesPage sfp= new SmartFilesPage(driver,"smartfilespage");
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
		String topNode = mp.getTopNodeText();
		String organization = topNode + ";" +NG7TestCase.smartFilesOrg;
		String location = NG7TestCase.orgString;
		String dataFile = "C:\\NG7\\qa\\UITests\\trunk\\NG7qa\\data\\SmartFiles.txt";
		/*String [] smartFileType = {"Reason for cancelling an appointment", 
								   "Appointment Types", 
								   "Healthwise Medical Group Users",
								   "Resources",
								   "Region 1 Payers",
								   "GH Payer Plans", 
								   "Activity",
								   "Austin Medical Group Counters",
								   "Forms",
								   "Charge Code",
								   "GH Contracts",
								   "GH Provider"
								   };
		*/
		String [] smartFileType = {"Appointment Types"};
		String uid;
		String sfClosed = "";
		
		// Run in local stress loop to make sure the test is robust
		if (loginSuccessCode.equals("00")) {
		for (int i = 1; i <= localStressLoop; i++){ // localStressLoop is controlled via the config.properties file
			if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
			System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
			GeneralMethods.prepForIteration();
		    
			uid  = " " + GeneralMethods.CreateUID();
			
			// test step
			currStepResult = mp.selectHomeMenuItem(mp.menuSmartFiles) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Launch SmartFiles from Menu", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.checkIsDisplayed(sfp.smartfilesTabControl) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check SmartFiles opened", currStepResult);
			String smartFilesOpened = currStepResult;
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.CheckSmartFilesOrgListDisplayed() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check organization list is not empty", currStepResult);
						
			// test step
			NG7TestCase.testData = "organization = "+organization;
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.selectOrganization(organization) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select organization <organization>", currStepResult);
		  				
			// test step
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.searchSmartFileType("Reason for cancelling an appointment") ? "Pass" :
					sfp.searchSmartFileType("Appointment Cancel Reasons") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search smartFile type for reason for cancelling an appointment", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.ClickButton(sfp.GetSmartFileType(driver, "Reason for cancelling an appointment")) ? "Pass" : 
					GeneralMethods.ClickButton(sfp.GetSmartFileType(driver, "Appointment Cancel Reasons")) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on smartFile type for reason for cancelling an appointment", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.ClickButton(sfp.addSmartItemButton) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on Add SmartItem button", currStepResult);

			// test step
			GeneralMethods.delay(eventDelay);
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.openSmartForm.getText().toString().contains("Reason for cancelling an appointment") ? "Pass" : 
					sfp.openSmartForm.getText().toString().contains("Appointment Cancel Reasons") ? "Pass" :"Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that SmartItem for reasonf or cancelling an appointment opens", currStepResult);			

			// test step
			String sf1_name = "random cancellation reason" +uid;
			NG7TestCase.testData = "smartFileName = "+sf1_name;
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.PopulateSmartFormfield(sfp.cancelationReasonName,  sf1_name+Keys.TAB, false) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the Cancel Reason Name field with <smartFileName>", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.ClickButton(sfp.openSmartFormAddButton) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on the SmartForm Add button", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.checkSmartFileClosed() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check smartform closed", currStepResult);
			sfClosed = currStepResult;
			
			if (currStepResult.equals("Fail")) {
				try {
					boolean closed = sfp.forceCloseSmartForm("Appointment cancellation reason");
					if (closed)
						sfClosed = "Pass";
				}
				catch (Exception e) {}
			}
			
			// test step
			NG7TestCase.testData = "smartFileName = "+sf1_name;
			prevStepResult = currStepResult;
			currStepResult = sfClosed.equals("Pass") == false ? "Blocked" :
				sfp.FindSmartFile(sf1_name) != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Find created random cancellation reason smart file <smartFileName>", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.searchSmartFileType("Appointment Types") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search SmartFile type Appointment Types", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.ClickButton(sfp.GetSmartFileType(driver, "Appointment Types")) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on SmartFile type Appointment Types", currStepResult);
			
			// test step
			GeneralMethods.delay(2000);
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.ClickButton(sfp.addSmartItemButton) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on Add SmartItem button", currStepResult);

			// test step
			GeneralMethods.delay(eventDelay);
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.openSmartForm.getText().toString().contains("Appointment Type") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that SmartItem with header Appointment Type opens", currStepResult);			

			// test step
			String sf2_name = "migraine checkup"+uid;
			NG7TestCase.testData="label="+sf2_name;
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.PopulateSmartFormfield(sfp.apptTypeAppointmentLabel, sf2_name+Keys.TAB, false) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the Appointment Label field with <label>", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.ClickButton(sfp.openSmartFormAddButton) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on the SmartForm Add button", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.checkSmartFileClosed() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check smartform closed", currStepResult);
						
			if (currStepResult.equals("Fail")) {
				try {
					boolean closed = sfp.forceCloseSmartForm("Appointment Type");
					if (closed)
					   sfClosed = "Pass";
			} catch (Exception e) {}
			}
			
			// test step
			NG7TestCase.testData="smartFileName2="+sf2_name;
			GeneralMethods.delay(2000);
			prevStepResult = currStepResult;
			currStepResult = sfClosed.equals("Pass") == false ? "Blocked" :
				sfp.FindSmartFile(sf2_name) != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Find smart file <smartFileName2>", currStepResult);

			// test step
			NG7TestCase.testData="smartFileName2="+sf2_name;
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Fail") || prevStepResult.equals("Blocked")) ? "Blocked" :
				sfp.ClickSmartFormButton(sf2_name, "Update") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Update on the SmartFile entry <smartFileName2", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Fail") || prevStepResult.equals("Blocked")) ? "Blocked" :
				GeneralMethods.ClickButton(sfp.openSmartFormCancelButton) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click 'Cancel' on the SmartForm", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.checkSmartFileClosed() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check smartform closed", currStepResult);
						
			if (currStepResult.equals("Fail")) {
				try {
					boolean closed = sfp.forceCloseSmartForm("Appointment Type");
					if (closed)
					   sfClosed = "Pass";}
				catch (Exception e) {}
			}
			
			// test step
			NG7TestCase.testData="smartFileName2="+sf2_name;
			prevStepResult = currStepResult;
			currStepResult = (sfClosed.equals("Fail") || prevStepResult.equals("Blocked")) ? "Blocked" :
				sfp.ClickSmartFormButton(sf2_name, "Update") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Update on SmartFile entry <smartFileName2>", currStepResult);

			// test step
			sf2_name += "_upd";
			NG7TestCase.testData="smartFileName2_updated="+sf2_name;
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.PopulateSmartFormfield(sfp.apptTypeAppointmentLabel, sf2_name+Keys.TAB, false) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Update the value on the Appointment Label field to <smartFileName2_updated>", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.ClickButton(sfp.openSmartFormUpdateButton) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on the SmartForm Update button", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.checkSmartFileClosed() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check smartform closed", currStepResult);
						
			if (currStepResult.equals("Fail")) {
				try {
					boolean closed = sfp.forceCloseSmartForm("Appointment Type");
					if (closed)
					   sfClosed = "Pass";
				}
				catch (Exception e) {}
			}
			
			// test step
			NG7TestCase.testData="name = "+sf2_name;
			GeneralMethods.delay(2000);
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				sfp.FindSmartFile(sf2_name) != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Find created smart file <name>", currStepResult);

			// test step
			NG7TestCase.testData="name = "+sf2_name;
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Fail") || prevStepResult.equals("Blocked")) ? "Blocked" :
				sfp.ClickSmartFormButton(sf2_name, "Remove") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Remove on SmartFile entry <name>", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Fail") || prevStepResult.equals("Blocked")) ? "Blocked" :
				GeneralMethods.ClickButton(sfp.openSmartFormRemoveButton) ? "Pass" : "Fail"; // MGB 7/30/2014
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Delete on the confirmation dialog", currStepResult);				

			// Cleanup
			mp.closeAllOpenTabs();	
		
    		} // stress loop closing bracket
		}
	
		// Cleanup
    	mp.logOutUser();
		driver.quit();
		driver = null;
		
		Artifact.CloseArtifact(artifact);
	}
}
