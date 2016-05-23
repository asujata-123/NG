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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nextgen.qa.automation.pages.*;
import com.nextgen.qa.automation.smoketest.*;
import com.nextgen.qa.automation.toolbox.*;
import com.thoughtworks.selenium.Wait;


public class PreRequisite extends NG7TestCase {

	public static boolean test() throws Exception {
		
		testName = "PreRequisite"; 
		tester = "Subha Srinivasan"; 
		
		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		MainPage mp = new MainPage(driver, "mainpage");
		LoginPage lp = new LoginPage(driver, "loginpage");
		RegistrationPage reg = new RegistrationPage(driver, "registrationpage");
		SmartFilesPage sfp = new SmartFilesPage(driver, "smartfilespage");
		ErrorPage ep = new ErrorPage(driver, "errorpage");
		
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		
		// Test data
		String visibilityCheck = GeneralMethods.visibilityCheck();	
		System.out.println("Visibility check : " + visibilityCheck);
		System.out.println("\n\nTEST: MGB 2/25/2015\n\n");

		// Run in local stress loop to make sure the test is robust
		for (int i = 1; i <= localStressLoop; i++){
		    if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
		    System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
		    GeneralMethods.prepForIteration();
		    		    
		    // test step
			NG7TestCase.testData = "site="+deployment+"  browser="+AutomationSettings.getTestDataItem("ChromeVersion");
			currStepResult = lp.launchApplication("NG7") ? "Pass" : "Fail";
		    Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Browse to NG7 deployment <site>", currStepResult+catastrophicFlag);
		    Artifact.ReportCatastrophicFail(currStepResult, "PreRequisite: Successful browsing to NG7 deployment site "+deployment);
		    String preReq_browsed = currStepResult;
		    System.out.println("loginDelay = "+NG7TestCase.loginDelay/1000+" seconds");
		    GeneralMethods.delay(NG7TestCase.loginDelay); 
		    
		    // test step
		    prevStepResult = currStepResult;
		 	currStepResult = !preReq_browsed.equals("Pass") ? "Blocked" :
		 		lp.checkElementIsVisible(lp.usernameField, "login page username field") ? "Pass" : "Fail";
		 	Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the Username field is displayed", currStepResult+catastrophicFlag);
		 	Artifact.ReportCatastrophicFail(currStepResult, "PreRequisite: Find username field on login site; is environment up and running?");
		 		    	
		 	// test step
		 	prevStepResult = currStepResult;
		 	currStepResult = !preReq_browsed.equals("Pass") ? "Blocked" :
		 		lp.checkElementIsVisible(lp.passwordField, "login page password field") ? "Pass" : "Fail";
		 	Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the Password field is displayed", currStepResult+catastrophicFlag);
		 	Artifact.ReportCatastrophicFail(currStepResult, "PreRequisite: Find password field on login site; is environment up and running?");
		 		    
		 	// test step
		 	prevStepResult = currStepResult;
		 	currStepResult = !preReq_browsed.equals("Pass") ? "Blocked" :
		 		lp.checkElementIsVisible(lp.forgotPasswordText, "login page forgot password text") ? "Pass" : "Fail";
		 	Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the forgot password text is displayed", currStepResult+catastrophicFlag);
		    
		 	// test step
		 	NG7TestCase.testData = "invalidUser = tester1 / invalidPassword = cupcake";
		 	prevStepResult = currStepResult;
		 	currStepResult = !preReq_browsed.equals("Pass") ? "Blocked" :
		 		lp.attemptLogin("tester1", "cupcake") ? "Pass" : "Fail";
		 	Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Attempt to log on with invalid credentials <invalidUser / invalidPassword>", currStepResult);
		    
		 	
		 	// MGB 3/6/2015 updated test step logic to not use isDisplayed
		 	// test step
		 	WebElement msg = null;
		 	if (preReq_browsed.equals("Pass")) {
		 		GeneralMethods.delay(eventDelay*2);
		 		msg = lp.loginMessage;
		 	}
		 	
		 	prevStepResult = currStepResult;			
		 	currStepResult = !preReq_browsed.equals("Pass") ? "Blocked" :
		 		msg != null && msg.getText().contains("Invalid Username or Password") ? "Pass" : "Fail";
		 	Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check the invalid login messages pop up for the invalid password login attempt", currStepResult+doNotDeliverFlag);
		 	Artifact.ReportDoNotDeliverFail(currStepResult, "PreRequisite: Check that invalid username/password flags errors");
		    
		    // test step
		 	prevStepResult = currStepResult;
		    currStepResult = !preReq_browsed.equals("Pass") ? "Blocked" :
		    	lp.login() ? "Pass" : "Fail";
	        Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Attempt to log into NG7 with valid credentials", currStepResult+catastrophicFlag);
	        Artifact.ReportCatastrophicFail(currStepResult, "PreRequisite: Attempt login into NG7");
		    
		 	// test step  RF 12/11/2014
	        if (preReq_browsed.equals("Pass")) GeneralMethods.delay(eventDelay*2);
		 	prevStepResult = currStepResult;
		    currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
		    	lp.passwordNotExpired() ?  "Pass" : "Fail";
	        Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check if User Password Expired Screen is displayed", currStepResult+catastrophicFlag);
	        Artifact.ReportCatastrophicFail(currStepResult, "PreRequisite: User Password Expired Screen is displayed");
		 	
			// test step
	     	if (preReq_browsed.equals("Pass") && !GeneralMethods.checkElementIsNotVisible(driver, mp.welcomeLayout)){
	     	    // MGB 3/18/2014 updated to use configurable layout variable
	         	String layout = AutomationSettings.getTestDataItem("layout_"+NG7TestCase.build); 
	         	currStepResult=	mp.handleWelcome(layout) ? "Pass" : "Fail";
	    		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"If layout selector comes up select "+layout, currStepResult);	
	    		Artifact.ReportCatastrophicFail(currStepResult, "PreRequisite: Select the layout on welcome screen");
			}
	     	String preReq_layoutSet = preReq_browsed.equals("Pass") && GeneralMethods.checkElementIsNotVisible(driver, mp.welcomeLayout) ? "Pass" : "Fail";
		    
	     	// test step
			prevStepResult = currStepResult;
			currStepResult = (preReq_layoutSet.equals("Pass")==false) ? "Blocked" :
				mp.clickHomeMenu() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on Menu pulldown", currStepResult+catastrophicFlag+catastrophicFlag);
			Artifact.ReportCatastrophicFail(currStepResult, "PreRequisite: Click Global Search/Menu field; was login successful?");
		    		
			// test step
			if (preReq_browsed.equals("Pass")) GeneralMethods.delay(eventDelay*5);
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass")==false) ? "Blocked" :
				GeneralMethods.checkListIsDisplayed(mp.homeMenuDropdownList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Menu opens", currStepResult+catastrophicFlag);
			Artifact.ReportCatastrophicFail(currStepResult, "PreRequisite: Check Menu opens after menu field is clicked");
		    
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass")==false) ? "Blocked" :
				mp.logOutUser() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Log out user", currStepResult);
			
			// test step
			if (preReq_browsed.equals("Pass")) GeneralMethods.delay(5000);
		    prevStepResult = currStepResult;
		 	currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
		 		GeneralMethods.WaitForElement(NG7TestCase.driver, NG7TestCase.waitForElementDelay, "login page username field", lp.usernameField) != null ? "Pass" : "Fail";
		 	Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that logout completed by checking login page is displayed", currStepResult+doNotDeliverFlag);
		 	Artifact.ReportDoNotDeliverFail(currStepResult, "PreRequisite: Check user can log out successfully");
		}  	
			 			
		if (catastrophicCheck)
			System.out.println("\n\n"+ catastrophicMsg);
			
		if (doNotDeliverCheck)
			System.out.println("\n\n"+ doNotDeliverMsg);
		
		// Cleanup
	 	driver.quit();
		driver = null;
		Artifact.CloseArtifact(artifact);
		
		return catastrophicCheck;
	}
}
