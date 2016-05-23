package com.nextgen.qa.automation.functionaltest;

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

import com.nextgen.qa.automation.pages.*;
import com.nextgen.qa.automation.smoketest.*;
import com.nextgen.qa.automation.toolbox.*;
import com.thoughtworks.selenium.Wait;


public class CreateRules extends NG7TestCase {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	public BufferedWriter artifact;
	public MainPage mp;
	public LoginPage lp;
	public RegistrationPage reg;
	public SmartFilesPage sfp;

	@Before
	public void setUp() throws Exception {
		NG7TestCase.testName = "CreateRules";
		NG7TestCase.tester = "Maria Garcia-Bodoh";
		artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	    driver.manage().window().maximize();
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
		String location = "GenHealth Hospital (GHH);GenHealth Central Hospital (GCH)";
		String organization = "Shared Cloud;GenHealth Enterprises, Inc. (GHEnt)"; 
		String dataFile = "C:\\NG7\\qa\\UITests\\trunk\\NG7qa\\data\\SmartFileCreationData.txt";
		 
		
		// TODO: instantiate the pages browsed here (classes are in package com.nextgen.qa.automation.pages) 
		// Pages browsed 
		mp  = new MainPage(driver, "mainPage.txt");
	    lp = new LoginPage(driver, "login.txt");
	    sfp = new SmartFilesPage(driver, "smartfilespage.txt");
		   	 	     
		// Log in to main page
		lp.launchApplication("NG7");
        lp.login();
        GeneralMethods.delay(5000);
		currStepResult = lp.verifyLogin() && mp.setLocation(location) ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Log in to "+GeneralMethods.getDeployment(), currStepResult+WIP); // MGB 5/5/2014
		preReq = currStepResult;
		
		// Run in local stress loop to make sure the test is robust
		for (int i = 1; i <= localStressLoop; i++){ // localStressLoop is controlled via the config.properties file
			if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
			System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
			
			// test step
			currStepResult = (preReq.equals("Fail")) ? "Blocked" :
				((driver.getTitle().equals("NG7")) ? "Pass" : "Fail");
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify that 'NG7' is in the title", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass")==false ? "Blocked" :
				mp.selectHomeMenuItem("SmartFiles") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Launch SmartFiles tab from Menu", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass")==false ? "Blocked" :
				sfp.selectOrganization(organization) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select organization 'GenHealth Enterprises Inc'", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass")==false ? "Blocked" :
				sfp.CreateNewRule(dataFile, "Smoke Test Rule - banner")!=null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Create Rule 'Smoke Test Rule - banner'", currStepResult);
						
		} // stress loop closing bracket
		
		mp.logOutUser();
		Artifact.CloseArtifact(artifact);
	}
	
	@After
	public void tearDown() throws Exception {	
		
		// Cleanup
		driver.quit();
		driver = null;
	}
}
