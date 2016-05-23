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
import com.nextgen.qa.automation.ui.GenericPage;
import com.thoughtworks.selenium.Wait;

/**
 * @author Mbodoh<li>
 * Smoke Test: RegisterPatient:<li>
 * Create a random UID and construct a patient name with it<li>
 * Search for created patient name to verify it does not exist in database<li>
 * Open patient registration wizard<li>
 * Fill in required registration fields, and name the patient with the created random name<li>
 * Complete registration<li>
 * Search for the created name and verify it now exists in the database<li>
 */
public class RegisterPatient extends NG7TestCase {
	public static void test() throws Exception {
		NG7TestCase.testName = "RegisterPatient";	
		NG7TestCase.tester = "Maria Garcia-Bodoh";
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		String preReq = null;
			
		// Test data
		String uid = GeneralMethods.CreateUID();
		String searchString = uid.replace("_", "");
		String guarantor = "Sarah Miller";
		
		WebDriver driver = GeneralMethods.startDriver();
		
		// Objects used
		MainPage mp  = new MainPage(driver, "mainPage.txt");
	    RegistrationPage reg = new RegistrationPage(driver, "registration.txt");
	    LoginPage lp = new LoginPage(driver, "loginpage");	 
	    
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
		String topLocationNode = "";
		topLocationNode = mp.getTopNodeText();
		String [] locations = NG7TestCase.orgString.split(";");
		String location = topLocationNode + ";" + NG7TestCase.orgString;
		String locationNode = locations[locations.length -1];
				
		// Run in local stress loop to make sure the test is robust
		for (int i = 1; i <= localStressLoop; i++){
			if (localStressLoop > 1) iterationStamp = Integer.toString(i) +" / " +localStressLoop +" ";
			System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
			GeneralMethods.prepForIteration();
		    
			
			if (! topLocationNode.equals(locationNode))
			   mp.setLocation(location);
							
			
			// test step
			NG7TestCase.testData = "uid="+uid;
			String category = "patient";
			boolean searched = false;
			if (preReq.equals("Pass")) {
				searched = mp.performGlobalSearch(searchString);
			}
			currStepResult = !searched ? "Blocked" :
			   mp.findSearchResult(uid, category) == null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Generate unique ID <uid> for new patient name and search for it to verify the patient does not exist", currStepResult);
			prevStepResult = currStepResult;
    		
			// test step
			currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
				mp.selectHomeMenuItem_registration() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Open the patient registration wizard", currStepResult);
			prevStepResult = currStepResult;
			
			// test step
			currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
				reg.wizardExists() ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check registration wizard opens", currStepResult);
			prevStepResult = currStepResult;
			String preReq_regWizardOpen = currStepResult;
			
			// test step
			NG7TestCase.testData = "uid="+uid +" FirstName=Anna  MiddleName=<uid> LastName=White_<uid>";
			currStepResult = !preReq_regWizardOpen.equals("Pass") ? "Blocked" :
				reg.registerPatient("Anna", uid, "white_"+uid, "female", "", "", "6/19/1990", 
						"Address Type", "Home Address", "123 Main Street", "", "Austin", "Texas", "78759", 
						"", "10/21/2014", "9:30 AM", locationNode) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Register patient Walter White_<uid>", currStepResult);
			prevStepResult = currStepResult;
			
			if (! GeneralMethods.checkElementIsNotVisible(reg.driver, reg.registrationWizard)) 
				reg.clickElement(reg.findVisibleButton(reg.registrationWizard, "Cancel", ""), "Registration Wizard Cancel button");
			
			// test step
			NG7TestCase.testData = "uid="+uid;
			if (preReq.equals("Pass")) mp.performGlobalSearch(searchString); // MGB 6/19/2014: tmp workaround
			currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
				mp.selectSearchResult(uid, category, true) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for unique id <uid> to see that newly registered patient is found", currStepResult);
			prevStepResult = currStepResult;
			
			// MGB 3/9/2015
			if (prevStepResult.equals("Pass"))
				mp.closeAllOpenTabs();
			
		} // stress loop closing bracket
		
		// Cleanup
    	if (preReq.equals("Pass"))
    		mp.logOutUser();
		driver.quit();
		driver = null;
		
		Artifact.CloseArtifact(artifact);
	}	
}
