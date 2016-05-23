package com.nextgen.qa.automation.Sandbox;

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


public class SmartFilesInterface_DemoSmartFiles extends NG7TestCase {
	private WebDriver driver;
	private ChromeOptions options;
	private StringBuffer verificationErrors = new StringBuffer();
	public BufferedWriter artifact;
	public MainPage mp;
	public LoginPage lp;
	public SmartFilesPage sfp;
	public SecondModalPage smp;
	
	@Before
	public void setUp() throws Exception {
		NG7TestCase.testName = "SmartFilesInterface_DemoSmartFiles";
		NG7TestCase.tester = "Maria Garcia-Bodoh"; 
		artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp); // MGB 5/5/2014
		options = new ChromeOptions();
	    options.addArguments(chromeOptions);
	    driver = new ChromeDriver(options);
	    driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	    driver.manage().window().maximize();
	    GeneralMethods.delay(eventDelay);
	}
	  
	@Test
	public void test() throws Exception {
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		String preReq = null;
		String reqFields = "";
		
		// Test data
		//String organization = NG7TestCase.smartFilesOrg1; 
		String organization = NG7TestCase.orgString;
		
		String dataFile = "C:\\NG7\\qa\\UITests\\trunk\\NG7qa\\data\\DemoSmartFiles.txt";
		String [] smartFileType = {//"Reason for cancelling an appointment", 
								   //"Appointment Types",
								   //"Users",
								   //"Resources",
								   //"Region 1 Payers",
								   //"GH Payer Plans", 
								   "Activity",
								   "Austin Medical Group Counters", 
								   "Forms",
								   //"Charge Code",
								   //"GH Contracts",
								   "GH Provider"
								   };
		
		//String [] smartFileType = {"Appointment Types"};
		String uid  = " " +GeneralMethods.CreateUID();
		
		// Pages browsed 
		mp  = new MainPage(driver, "mainPage.txt");
	    lp = new LoginPage(driver, "login.txt");
	    sfp = new SmartFilesPage(driver, "smartfilespage.txt");
	    smp = new SecondModalPage(driver, "secondmodal.txt");
	       	 	     
		// Log in to main page
		lp.launchApplication("NG7");
        lp.login();
        String loginSuccessCode = mp.checkLoginSucceeded();
        currStepResult = lp.verifyLogin() && loginSuccessCode.equals("00") ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Log in to "+GeneralMethods.getDeployment()+"; success code = "+loginSuccessCode, currStepResult); // MGB 5/5/2014
		preReq = currStepResult;
		
		// Run in local stress loop to make sure the test is robust
		//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // speed up the test a bit
		if (loginSuccessCode.equals("00")) {
		for (int i = 1; i <= localStressLoop; i++){ // localStressLoop is controlled via the config.properties file
			if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
			System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
			
			// test step
			//if (GeneralMethods.WaitForElementBySelector(driver, 1, "Welcome popup", mp.welcomeLayoutCssData) != null){
			if (!GeneralMethods.checkElementIsNotVisible(driver, mp.welcomeLayout)){
				currStepResult = (!preReq.equals("Pass")) ? "Blocked" :
					mp.handleWelcome("Staff Layout") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select Staff Layout", currStepResult);	
				preReq = currStepResult;
			}
            
			// test step
			currStepResult = (preReq.equals("Fail")) ? "Blocked" :
				((driver.getTitle().equals("NG7")) ? "Pass" : "Fail");
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify that 'NG7' is in the title", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				mp.selectHomeMenuItem("SmartFiles") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Launch SmartFiles tab from Menu", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				GeneralMethods.checkIsDisplayed(sfp.smartfilesTabControl) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check SmartFiles tab opened", currStepResult);
			String smartFilesOpened = currStepResult;
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.selectOrganization(organization) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select organization "+organization, currStepResult+WIP+"this fails sometimes; working on a fix");
			
			WebElement smartForm = null;
			for (i=0; i < smartFileType.length; i++){
		        
				
				// Skip any tests that are not ready yet
				if (smartFileType.equals("Activity")) {
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Activity smart file requirement not complement implemented yet", currStepResult+knownIssue+"NGTRUNK-17636");
					continue;
				}
				
				// Initialize 
				currStepResult = "Pass"; 
				
				// test step
				currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
					sfp.ManageSmartFile_dataDriven("add", dataFile, smartFileType[i], uid) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Create smart file of type "+smartFileType[i], currStepResult);				
				
				// test step
				//if (GeneralMethods.WaitForElementBySelector(driver, 1, "SmartForm that should have closed", sfp.openSmartFormCssData) != null){
				if (!GeneralMethods.checkElementIsNotVisible(driver, sfp.openSmartForm)){
					try {sfp.forceCloseSmartForm(smartFileType[i]);}
					catch (Exception e) {}
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+tempWorkaround+"Force close smartform after Add of type "+smartFileType[i], "Fail");
				}
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					sfp.ManageSmartFile_dataDriven("update", dataFile, smartFileType[i], uid) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Update created smart file of type "+smartFileType[i], currStepResult);
				
				// test step
				//if (GeneralMethods.WaitForElementBySelector(driver, 1, "SmartForm that should have closed", sfp.openSmartFormCssData) != null){
				if (!GeneralMethods.checkElementIsNotVisible(driver, sfp.openSmartForm)){
					try {sfp.forceCloseSmartForm(smartFileType[i]);}
					catch (Exception e) {}
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+tempWorkaround+"Force close after smartform Update of type "+smartFileType[i], "Fail");
				}
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					sfp.ManageSmartFile_dataDriven("remove", dataFile, smartFileType[i], uid) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Remove created smart file of type "+smartFileType[i], currStepResult);
				
				// test step
				GeneralMethods.delay(eventDelay);
				//WebElement sf = sfp.openSmartForm;
				
				// test step
				//if (GeneralMethods.WaitForElementBySelector(driver, 1, "SmartForm that should have closed", sfp.openSmartFormCssData) != null){
				if (!GeneralMethods.checkElementIsNotVisible(driver, sfp.openSmartForm)){
					try {sfp.forceCloseSmartForm(smartFileType[i]);}
					catch (Exception e) {}
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+tempWorkaround+"Force close after smartform Remove of type "+smartFileType[i], "Fail");
				}
				
			}
			
		} // stress loop closing bracket
		
		mp.closeAllOpenTabs();
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
