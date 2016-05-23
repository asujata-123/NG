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


public class SchedulingSearchAhead extends NG7TestCase {
	private WebDriver driver;
	private ChromeOptions options;
	private StringBuffer verificationErrors = new StringBuffer();
	public BufferedWriter artifact;
	public MainPage mp;
	public LoginPage lp;
	public RegistrationPage reg;


	@Test
	public void test() throws Exception {
		NG7TestCase.testName = "SchedulingSearchAhead"; 
		NG7TestCase.tester = "Maria Garcia-Bodoh"; 
		
		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
	  
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
		GeneralMethods.prepForIteration();
	    
		// Pages browsed 
		MainPage mp  = new MainPage(driver, "mainPage.txt");
	    LoginPage lp = new LoginPage(driver, "login.txt");
	    SchedulerPage sp = new SchedulerPage(driver, "scheduler.txt");
	    NewAppointmentPage nap = new NewAppointmentPage(driver, "newappt.txt");  	 
	    SecondModalPage sm = new SecondModalPage(driver, "secondmodal.txt"); 
		   	 	     
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
		for (int i = 1; i <= localStressLoop; i++){ // localStressLoop is controlled via the config.properties file
			if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
			System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
			
			// test step
			//if (GeneralMethods.WaitForElementBySelector(driver, 1, "Welcome popup", mp.welcomeLayoutCssData) != null){
			// remove the below comment block when new layout is completed -- subha
			/*if (!GeneralMethods.checkElementIsNotVisible(driver, mp.welcomeLayout)){
				currStepResult = (!preReq.equals("Pass")) ? "Blocked" :
					mp.handleWelcome("Staff Layout") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select Staff Layout", currStepResult);	
				preReq = currStepResult;
			}*/

			if (preReq.equals("Pass")) mp.cleanupForTest();
			
			/*
			// test step
			currStepResult = (preReq.equals("Fail")) ? "Blocked" :
				mp.clickSchedulerButton() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Scheduler button on the Calendar tab", currStepResult);
			*/
			
			// test step
			currStepResult = (preReq.equals("Fail")) ? "Blocked" :
				//mp.selectHomeMenuItem("Scheduler") ? "Pass" : "Fail";
				GeneralMethods.ClickButton(mp.schedulerButton) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Scheduler from the Home menu", currStepResult);
						
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				sp.wizardExists() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that Scheduler opened", currStepResult);
			String openSched_PreReq = currStepResult;
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" : 
				sp.clickNewApptButton() ? "Pass" : "Fail" ;
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Add Appointment button", currStepResult);
		
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.wizardExists() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that New Appointment wizard pops up", currStepResult);
			String NAopens = currStepResult;
								
			// test step
			prevStepResult = currStepResult;
			currStepResult = (NAopens.equals("Pass") == false) ? "Blocked" :
				//nap.clickWizardTab("Appt") ? "Pass" : "Fail";
				nap.clickWizardTab("Appointment") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on Appointment tab", currStepResult);
						
			// test step
			prevStepResult = currStepResult;
			currStepResult = (NAopens.equals("Pass") == false) ? "Blocked" :
				nap.clickSearchAheadTab() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on Search Ahead tab", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				GeneralMethods.checkIsDisplayed(nap.searchAheadPanel) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Search Ahead tab opens", currStepResult);
			String preReq_SAopen = currStepResult;
						
			// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_SAopen.equals("Pass") == false) ? "Blocked" :
				nap.setSearchAheadEndTime("11:59 PM", false) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the Search Ahead time frame fields", currStepResult+WIP);

			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				nap.enableWeekends() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Include weekend days for the searches", currStepResult);
						
			// test step
			prevStepResult = currStepResult;
			String today = GeneralMethods.GenerateDateAroundToday(0);
			currStepResult = (prevStepResult.equals("Pass")==false) ? "Blocked" :
				nap.setSearchAheadStartDate(today) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Populate the Search Ahead date field with "+today, currStepResult);
						
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass")==false) ? "Blocked" :
				nap.clickSearchAheadSuggestButton() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on the Search Ahead Suggest button", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass")==false) ? "Blocked" :
				nap.suggestedDatesList.size() > 0 ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that Suggested Days and Times results are given", currStepResult+knownIssue+"NGTRUNK-15252");
						
			// test step
			prevStepResult = currStepResult;
			String todayFormatted = GeneralMethods.ConstructDateAbbr(today);
			currStepResult = (prevStepResult.equals("Pass")==false) ? "Blocked" :
				nap.findDateInResult(nap.suggestedDatesList.get(0), todayFormatted) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that dates were suggested for starting date "+today, currStepResult);
								
			// test step
			prevStepResult = currStepResult;
			currStepResult = (NAopens.equals("Pass")==false) ? "Blocked" :
				nap.clickCancel() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Cancel Appointment creation", currStepResult);
									
			// Cleanup
			mp.closeAllOpenTabs();	
			
	    } // stress loop closing bracket
		
		mp.logOutUser();
		}
		
		Artifact.CloseArtifact(artifact);
	}
				
	@After
	public void tearDown() throws Exception {
		// Cleanup
		driver.quit();
		driver = null;
	}
}
