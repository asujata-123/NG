package com.nextgen.qa.automation.Sandbox;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.util.Calendar;
import java.util.Hashtable;
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


public class NG7Tests_dev extends NG7TestCase {

	public static boolean test() throws Exception {
		testName = "NG7Tests_dev"; 
		tester = "Maria Garcia-Bodoh";
	
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		System.out.println("* * * * * Start of " +testName +" test * * * * *");
		int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
		// Test case infrastructure
		String iterationStamp = "";
		String currStepResult;
		String prevStepResult;
		String preReq;
			
		// Test data
		NG7TestCase.testName = testName;
		
				
				if (NG7TestCase.set.equals("PREREQ") || NG7TestCase.set.equals("SMOKE")){
					//UIRecon_MainPage.test();
					//Runtime.getRuntime().exec(NG7TestCase.pythonZAPICall+NG7TestCase.testName);
					
					//UIRecon_PatientCenter.test();
					//UIRecon_PatientFinancials.test();
					//UIRecon_RegistrationWizard.test();
					//UIRecon_ReportsDisplay.test();
					//UIRecon_SchedulerNewAppointmentWizard.test();
					//UIRecon_SmartFilesInterface.test();
					////UIRecon_MedCinSearch.test();  // MGB 3/25/2015 test name will be updated to something other than MedCinSearch (NQA-166)
					//UIRecon_Search.test();
				} 
				
				if (NG7TestCase.set.equals("SMOKE")) {
					//SmartFilesInterface.test();
					//RegisterPatient.test();
					//SchedulingNoPatientDoubleClick.test();
					//SchedulingPatientNewApptButton.test();
					//SchedulingSearchAhead.test();	
					//CheckEligibility.test();
					//CreateClaim_VisitHistory.test();
					//SiblingAndParentInXpath.test();
					//CheckEligibility_VisitHistory.test();
					//CalendarView.test();
					//CreateChargeBatch.test();
					//PlaceOrder.test();
					//CheckEligibility_InactiveInsurance.test();
					//CheckEligibility_MultipleInsurances.test();
					//CheckEligibility_VisitHistory_MultipleInsurances.test();
					//ClaimsTab_Filterby.test();
					//CheckEligibility_SelectedPlan.test();
				}
	//if (doNotDeliverCheck == true) System.out.println(doNotDeliverMsg);
		return catastrophicCheck | doNotDeliverCheck;
   }
}
