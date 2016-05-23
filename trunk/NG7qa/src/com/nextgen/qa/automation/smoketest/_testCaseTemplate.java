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

public class _testCaseTemplate extends NG7TestCase {
	  
	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "_testCaseTemplate";	
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
		String topLocationNode = mp.getTopNodeText();
		String patientName = AutomationSettings.getTestDataItem("patientName"+NG7TestCase.orgSelect);
		String location = topLocationNode + ";" +NG7TestCase.orgString;
		String locationBackup = topLocationNode + ";" +AutomationSettings.getTestDataItem("organization1");
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
		 	currStepResult = !preReq.equals("Pass") ? "Blocked" :
		 		mp.setLocation(location) ? "Pass" : "Fail";
		 	Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Set service location to "+location, currStepResult);
		 	preReq = currStepResult;	
			
			// test step : this illustrates test step formatted as catastrophic check
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :  // the pre-requisite can be any previous test step or check
				<boolean_check> ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Description of test step", currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "Description of high-level catastrophic check");
				
			// test step : this illustrates test step formatted as doNotDeliver check
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :  // the pre-requisite can be any previous test step or check
				<boolean_check> ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Description of test step", currStepResult+doNotDeliverFlag);
			prevStepResult = currStepResult;
			Artifact.ReportDoNotDeliverFail(currStepResult, "Description of high-level doNotDeliver check");
				
			// test step : this illustrates a formatted test step;  all test steps should look like this unless
			// it needs to be formatted as a castastrophicCheck test step or as a doNotDeliverCheck test step
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :  // the pre-requisite can be any previous test step or check
				<boolean_check> ? "Pass" : "Fail"; 
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Description of test step", currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			
			// If the course of the test opened any tabs, this needs to be done in order to clean up before log-out
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


