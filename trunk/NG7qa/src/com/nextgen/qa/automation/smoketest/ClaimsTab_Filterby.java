package com.nextgen.qa.automation.smoketest;


import java.io.BufferedWriter;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.nextgen.qa.automation.pages.EDIManagerPage;
import com.nextgen.qa.automation.pages.ErrorPage;
import com.nextgen.qa.automation.pages.LoginPage;
import com.nextgen.qa.automation.pages.MainPage;
import com.nextgen.qa.automation.pages.PatientDashboardPage;
import com.nextgen.qa.automation.toolbox.Artifact;
import com.nextgen.qa.automation.toolbox.AutomationSettings;
import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.toolbox.NG7TestCase;

public class ClaimsTab_Filterby extends NG7TestCase {
		  
		@Test
		public static void test() throws Exception {
			NG7TestCase.testName = "ClaimsTab_FilterBy";	
			NG7TestCase.tester = "Sujata Sudhakar";
			
			WebDriver driver = GeneralMethods.startDriver();
			
			BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
			
			// Objects used
			MainPage mp = new MainPage(driver, "mainpage");
			LoginPage lp = new LoginPage(driver, "loginpage");
			PatientDashboardPage pdb = new PatientDashboardPage(driver, "patientdashboard.txt");
		    ErrorPage ep = new ErrorPage(driver, "errorpage");
		    EDIManagerPage em = new EDIManagerPage(driver, "EDIManagerPage");
		
			System.out.println("* * * * * Start of " +testName +" test * * * * *");
			int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
			
			// Test case infrastructure
			String currStepResult = null;
			String prevStepResult = null;
			String iterationStamp = "";
			String preReq = null;
				
			// Test data
			String patientName = AutomationSettings.getTestDataItem("patientName"+NG7TestCase.orgSelect);
			String location = NG7TestCase.orgString;
			
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
			    NG7TestCase.testData = "location = "+location;
				currStepResult = !preReq.equals("Pass") ? "Blocked" :
					mp.setLocation(location) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Set service location to "+location, currStepResult);
				prevStepResult = currStepResult;			

				// test step
				currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
					mp.selectHomeMenuItem("EDI Manager") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click EDI Manager from the Home menu", currStepResult);
				String openSched_PreReq = currStepResult;
				
				// test step
				currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				//currStepResult = (financialManagerOpen_menu.equals("Pass") == false) ? "Blocked" :
					em.clickTab("Claims", "Claims Tab") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Generate Claims tab", currStepResult);
				prevStepResult = currStepResult;
				
				// test step
			/*	currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				//currStepResult = (financialManagerOpen_menu.equals("Pass") == false) ? "Blocked" :
					em.clickTab("ERAs", "ERAs Tab") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Generate ERAs tab", currStepResult);
				prevStepResult = currStepResult;
				
				// test step
				currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				//currStepResult = (financialManagerOpen_menu.equals("Pass") == false) ? "Blocked" :
					em.clickTab("Statements", "Statements Tab") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Generate Statements tab", currStepResult);*/
				prevStepResult = currStepResult;
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.checkIsDisplayed(em.ediManagerCalendar) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check ediManagerCalendar control is opened", currStepResult);
				String ediManagerCalendarOpened = currStepResult;
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.checkIsDisplayed(em.ediOrgTest) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check ediManagerCalendar control is opened", currStepResult);
				String ediOrgTestOpened = currStepResult;
				
				// test step
				currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				//currStepResult = (financialManagerOpen_menu.equals("Pass") == false) ? "Blocked" :
					em.clickFilterBy() ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Filter by drop dwon pops up", currStepResult);
				prevStepResult = currStepResult;
				
				// test step
				/*currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				//currStepResult = (financialManagerOpen_menu.equals("Pass") == false) ? "Blocked" :
					pdb.clickTab("Statements", "Statements tab") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Statements tab", currStepResult);
				prevStepResult = currStepResult;*/
				
				if (preReq.equals("Pass"))
					mp.closeAllOpenTabs();	
				
				
				// Cleanup
		    	if (preReq.equals("Pass"))
		    		mp.logOutUser();
				driver.quit();
				driver = null;

				Artifact.CloseArtifact(artifact);
}
		}
}
