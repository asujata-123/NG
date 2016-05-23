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


public class UIRecon_MainPage extends NG7TestCase {

	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "UIRecon_MainPage";	
		NG7TestCase.tester = "Maria Garcia-Bodoh";

		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		LoginPage lp = new LoginPage(driver, "loginpage");
		MainPage mp = new MainPage(driver, "mainpage");
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
				mp.checkElementIsVisible(mp.statusIcon, "Status icon") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the Status icon is displayed and is active", currStepResult);
			String preReq_mainpageopen = currStepResult;
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = !preReq_mainpageopen.equals("Pass") ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(mp.homeMenu, mp.homeMenuDropdownList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Menu button and check Menu opens", currStepResult);

			// test step
			prevStepResult = currStepResult;
			currStepResult = !preReq_mainpageopen.equals("Pass") ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(mp.locationStatus, mp.locationStatusDropdownList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Locations field and check Location Status pulldown opens", currStepResult);
			
			// test step
			prevStepResult = currStepResult;
				currStepResult = !preReq_mainpageopen.equals("Pass") ? "Blocked" :
			GeneralMethods.clickFieldCheckListIsNotEmpty(mp.locationStatus, mp.locationStatusDropdownList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click User Status button and check User Status pulldown opens", currStepResult);
			
			// test step
			NG7TestCase.testData = "build="+mp.getBuildNumber();
			prevStepResult = currStepResult;
			currStepResult = !preReq_mainpageopen.equals("Pass") ? "Blocked" : 
				! mp.getBuildNumber().equals("") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that build number displays", currStepResult+doNotDeliverFlag);
			Artifact.ReportDoNotDeliverFail(currStepResult, "UIRecon_MainPage: Check that build number is present");
			
			// test step
			NG7TestCase.testData = "time="+mp.getElapsedTime();
			prevStepResult = currStepResult;
			currStepResult = !preReq_mainpageopen.equals("Pass") ? "Blocked" : 
				! mp.getElapsedTime().equals("") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that elapsed connection time displays", currStepResult);
			MouseMethods.HoverOver(mp.driver, mp.statusIcon, -10, -10);
			
			// test step
			prevStepResult = currStepResult;
			currStepResult = !preReq_mainpageopen.equals("Pass") ? "Blocked" : 
				mp.checkElementIsVisible(mp.schedulingNotificationsWidget, "Scheduling Notification widget") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check the Scheduling Notifications widget is visible", currStepResult);		

			// test step
			prevStepResult = currStepResult;
			currStepResult = !preReq_mainpageopen.equals("Pass") ? "Blocked" : 
				mp.checkElementIsVisible(mp.getSchedulerWidgetButton(), "Main Page Scheduler button") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check the Scheduler button/widget is visible", currStepResult);
					
			// test step
			prevStepResult = currStepResult;
			currStepResult = (prevStepResult.equals("Pass")==false) ? "Blocked" :
				GeneralMethods.clickFieldCheckListIsNotEmpty(mp.resourceSelectField, mp.resourceSelectFieldList) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click the Scheduler widget Resource field and check the Resource pulldown list is not empty", currStepResult);	

			// Cleanup
			//mp.closeAllOpenTabs();
		}   					
					
		if (preReq.equals("Pass")) 
			mp.logOutUser();
		driver.quit();
		driver = null;
		
		Artifact.CloseArtifact(artifact);
	}
}

// TODO: activity board
// TODO:  Patient tracker screen
