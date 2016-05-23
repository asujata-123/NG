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


@SuppressWarnings("unused")
public class PlaceOrder extends NG7TestCase {

	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "Place Order";	
		NG7TestCase.tester = "Subha Srinivasan";

		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
		LoginPage lp = new LoginPage(driver, "loginpage");
		MainPage mp = new MainPage(driver, "mainpage");
		ErrorPage ep = new ErrorPage(driver, "errorpage");
		OrderPage op= new OrderPage(driver,"orderPage");
		PatientDashboardPage pdb = new PatientDashboardPage(driver, "patientdashboard.txt");
	    SecondModalPage smp = new SecondModalPage(driver, "secondmodal");
	    
		
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String currStepResult = null;
		String prevStepResult = null;
		String iterationStamp = "";
		String preReq = null;
		String location = NG7TestCase.orgString;
		String patientName = AutomationSettings.getTestDataItem("patientName"+NG7TestCase.orgSelect);
        String labOrder = AutomationSettings.getTestDataItem("labOrder"+NG7TestCase.orgSelect);
        String entity = AutomationSettings.getTestDataItem("serviceProviderName"+NG7TestCase.orgSelect);
        String provider = AutomationSettings.getTestDataItem("labProvider"+NG7TestCase.orgSelect);
        String diagnosis = AutomationSettings.getTestDataItem("diagnosis"+NG7TestCase.orgSelect);
        String medicationName1 = AutomationSettings.getTestDataItem("medication1Name"+NG7TestCase.orgSelect);
		String medicationName2 = AutomationSettings.getTestDataItem("medication2Name"+NG7TestCase.orgSelect);
	
		
		// Log in to main page
		lp.launchApplication("NG7");
		lp.login();
 	
		
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
		   
		   NG7TestCase.testData = "location = "+location;
			currStepResult = !preReq.equals("Pass") ? "Blocked" :
				mp.setLocation(location) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Set service location to <location>", currStepResult);
			preReq = currStepResult;	
			
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
				mp.performGlobalSearch(patientName) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for patient <patientName>", currStepResult+catastrophicFlag);
			prevStepResult = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "Place Order: Perform global search");
				
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			String category = "patient";
			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
				mp.selectSearchResult(patientName, category, true) ? "Pass" : "Fail"; // do not handle alerts to that the next step can test it
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for patient <patientName>", currStepResult+catastrophicFlag);
			String preReq_globalSearch = currStepResult;
			Artifact.ReportCatastrophicFail(currStepResult, "Place Order: Verify returned global search results for category 'People'");
			prevStepResult = currStepResult;
			
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			WebElement openPatientTab = null;
			if (prevStepResult.equals("Pass")) openPatientTab = mp.getOpenTab(patientName);
			currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
				(openPatientTab != null && openPatientTab.getText().contains("Patient Center")) ? "Fail" : "Pass";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Patient Center for <patientName> was opened", currStepResult);
			prevStepResult = currStepResult;
			String preReq_openPatientCenter = currStepResult;
			
			// test step
			NG7TestCase.testData = "patientName = "+patientName;
			currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :
				mp.clickElement(mp.getOpenTab(patientName), "Open tab for patient "+patientName) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on the tab for patient <patientName>", currStepResult);
			prevStepResult = currStepResult;
		
			// test step
			currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :	
				pdb.checkElementIsVisible(pdb.openPatientTab, "open patient tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the patient center opens", currStepResult);
			
			prevStepResult = currStepResult;
			currStepResult = preReq_openPatientCenter.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Patient Center", "Patient Center Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Patient Center tab", currStepResult);
			
			
			//Select Visit History
			//add diagnosis in visit history
			// test step
			
			prevStepResult = currStepResult;
			currStepResult = preReq_openPatientCenter.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Visit History", "Visit History Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Visit History tab", currStepResult);
			
			//add diagnosis
			prevStepResult = currStepResult;
			
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				//add diagnosis
			op.addDiagnosis(diagnosis, "Add Diagnosis ")? "Pass": "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Add Diagnosis", currStepResult);
			
			prevStepResult = currStepResult;
			//String preReq_patientTabOpened = currStepResult; 
			currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				pdb.clickTab("Orders", "Orders Tab") ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Orders tab", currStepResult);
			prevStepResult = currStepResult;
            
			//test step verify order menu is displayed
			prevStepResult = currStepResult;
			
			 currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
				 op.checkElementIsVisible(op.openOrderMenu,"Click Order Menu") == false ? "Blocked":
					 op.clickElement(op.openOrderMenu, "Order Menu") ?"Pass": "Fail";
					 //GeneralMethods.clickFieldCheckListIsNotEmpty(op.openOrderMenu, op.orderMenu)?"Pass": "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Order Menu", currStepResult);
			
			//Create Lab Order
			prevStepResult = currStepResult;
			currStepResult = prevStepResult.equals("Pass")==false ? "Blocked":
			         op.clickElement(op.createLabOrder, "Lab Order")== false ? "Blocked":
				    //GeneralMethods.clickFieldSelectList(op.parentorderMenu) == false ? "Blocked" :
			        op.createOrders( op.webtest, op.webentity,  op.webDiagnosis,  op.webprovider,"Creating Lab Order",labOrder,entity,provider)? "Pass":"Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Create Lab Orders", currStepResult);    	 
		   
					
					// work around for orders verifying -- remove it later
					prevStepResult = currStepResult;
					currStepResult = preReq_openPatientCenter.equals("Pass") == false ? "Blocked" :
						pdb.clickTab("Visit History", "Visit History Tab") ? "Pass" : "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Visit History tab", currStepResult);
					prevStepResult = currStepResult;
					//String preReq_patientTabOpened = currStepResult; 
					currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
						pdb.clickTab("Orders", "Orders Tab") ? "Pass" : "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Orders tab", currStepResult);
					
			//verify Lab order is created
					prevStepResult = currStepResult;
					currStepResult = prevStepResult.equals("Pass")==false ? "Blocked":
					//GeneralMethods.clickFieldSelectList(op.parentorderMenu) == false ? "Blocked" :
					op.verifyOrder( labOrder,"Verify Lab Order")? "Pass":"Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify Lab Orders", currStepResult);  
					
					prevStepResult = currStepResult;
					currStepResult = preReq_openPatientCenter.equals("Pass") == false ? "Blocked" :
						pdb.clickTab("Visit History", "Visit History Tab") ? "Pass" : "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Visit History tab", currStepResult);
					
					
					//add diagnosis
					prevStepResult = currStepResult;
					
					currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
						//add diagnosis
					op.deleteDiagnosis(diagnosis, "Delete Diagnosis ")? "Pass": "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Delete Diagnosis", currStepResult);
					
		
			// Cleanup
			mp.closeAllOpenTabs();
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
