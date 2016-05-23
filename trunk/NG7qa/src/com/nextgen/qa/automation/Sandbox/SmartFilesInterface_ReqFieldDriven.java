package com.nextgen.qa.automation.Sandbox;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
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

import com.nextgen.qa.automation.pages.*;
import com.nextgen.qa.automation.smoketest.*;
import com.nextgen.qa.automation.toolbox.*;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.thoughtworks.selenium.Wait;


public class SmartFilesInterface_ReqFieldDriven extends NG7TestCase {
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
		NG7TestCase.testName = "SmartFilesInterface_ReqFieldDriven";
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
		boolean debugMode = false;
		String [] smartFileType = {//"Reason for cancelling an appointment", 
								   "Appointment Types",
								   //"Users", // click away after selecting Roles value; BUG
								   "Resources",
								   //"Region 1 Payers",
								   "GH Payer Plans",  // text fields not populating
								   //"Activity",
								   //"Austin Medical Group Counters", 
								   //"Forms",
								   "Charge Code",
								   //"GH Contracts",
								   //"GH Provider",
								   "Calendar Profile",
								   "Document",
								   "EDStaff",
								   "ICD-10",
								   "Med Surg Dept Locations",
								   "State",
								   "Visit Status"
								   };
		
		// smartType black list: skip test for these smartType values:
		String smartTypeBlackList = "@ 1099 Misc Code Category @"
	    		+ "@ Analytical Classification - HMG @"
	    		+ "@ Cost Centers - HMG @"
	    		+ "@ GLAccount Enterprise Level @"
	    		+ "@ Item Catalog - HMG @"
	    		+ "@ Journal Entry Source Code @"
	    		+ "@ Shopping List @"
	    		+ "@ Sub Account - HMG @"
	    		+ "@ Unspsc Codes @"
	    		+ "@ Vendor - HMG @"
	    		+ "@ Vendor Catalog @"
	    		+ "@ Smarttag Group @";
	    
	    //String [] smartTypeIssue = {
	    Hashtable ht = new Hashtable();
	    ht.put("Activity", "NGTRUNK-17636: requirement not fully implemented yet");
	    ht.put("Allergies", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Allergy Codes", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    //ht.put("Appointment Types", "NGTRUNK-20007: required fields are not highlighted");
	    ht.put("Auditing Event Type", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Charge Code", "NGTRUNK-20036: Units field not tagged as required field");
	    ht.put("Dashboard Layout", "NGTRUNK-20013: smart file does not save");
	    ht.put("Diagnostic Service Provider", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Forms", "NGTRUNK-20014: Launch Point pull down empty");
	    ht.put("GH EDI Configurations", "NGTRUNK-20016: incorrect required field label");
	    ht.put("GH Financial Posting", "NGTRUNK-20018: smart file does not save");
	    //ht.put("GH Providers", "NGTRUNK-19100: smart file not saving; NGTRUNK-20020: no required fields are highlighted; NGTRUNK-20021: Provider Name not retaining create value");
	    ht.put("GH Statement Configuration", "NGTRUNK-20022: smart file does not save");
	    ht.put("LOINC Codes", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Medication Library", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Orders", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Organization Address Type", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Organization Contact Type", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Procedure Order Codes", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Radiology Order Codes", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Reporting Job Status", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Reporting Job Type", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Rule Set", "WIP: still implementing this test code");
	    //ht.put("Scheduling Category", "NGTRUNK-20023: no required fields are highlighted");
	    ht.put("Scheduling Templates", "WIP: still implementing this test code");
	    ht.put("Shared Person Cloud Level", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Shopping List Item", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Vendor Contract - GHEnt", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Vendor Discount Term", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Vendor Payment Term", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    ht.put("Vital Code Library", "NGTRUNK-18884: Per Anand Kumar this type might not be visible to the user");
	    		
		//String [] smartFileType = {"Appointment Types"};
		String uid  = GeneralMethods.CreateUID();
		
		// Pages browsed 
		mp  = new MainPage(driver, "mainPage.txt");
	    lp = new LoginPage(driver, "login.txt");
	    sfp = new SmartFilesPage(driver, "smartfilespage.txt");
	    smp = new SecondModalPage(driver, "secondmodal.txt");
	       	 	     
		// Log in to main page
		lp.launchApplication("NG7");
		lp.login();
        GeneralMethods.delay(5000);
        String loginSuccessCode = mp.checkLoginSucceeded();
        currStepResult = lp.verifyLogin() && loginSuccessCode.equals("00") ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Log in to "+GeneralMethods.getDeployment()+"; success code = "+loginSuccessCode, currStepResult); // MGB 5/5/2014
		preReq = currStepResult;
		
		// Run in local stress loop to make sure the test is robust
		if (loginSuccessCode.equals("00")) {
		for (int i0 = 1; i0 <= localStressLoop; i0++){ // localStressLoop is controlled via the config.properties file
			//if (localStressLoop > 1) iterationStamp = Integer.toString(i0) +" / " +localStressLoop +" ";
			//System.out.println("* * * * * *  Local stress loop iteration # " +iterationStamp);
			
			// test step
			if (!GeneralMethods.checkElementIsNotVisible(driver, mp.welcomeLayout)){
				currStepResult = (!preReq.equals("Pass")) ? "Blocked" :
					mp.handleWelcome("Staff Layout") ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select Staff Layout", currStepResult);	
				preReq = currStepResult;
			}
            
			// test step
			prevStepResult = currStepResult;
			String build = mp.getBuildNumber();
			currStepResult = (prevStepResult.equals("Fail")) ? "Blocked" :
				build != null ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"NG7 build version number: "+build, currStepResult);
			
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
			GeneralMethods.delay(eventDelay*2);
			prevStepResult = currStepResult;
			currStepResult = smartFilesOpened.equals("Pass") == false ? "Blocked" :
				sfp.selectOrganization(organization) ? "Pass" : "Fail";
			Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select organization "+organization, currStepResult);
			
			// Test variables
			WebElement smartForm = null;
			List<WebElement> requiredFields;
			List<WebElement> requiredFieldLabels;
			List<WebElement> fieldLabels;
			WebElement reqFieldIndicator;
			int size;
			int j;
			String typeName = "";
			String issueString = "";
			String issueFlag = ""; // knownIssue, WIP
			int numberOfSmartTypes;
			WebElement openSmartForm = null;
			
			// Timer variables
			long before;
			long after;
			long delay;
			String elapsed;
			
			if (debugMode == true) numberOfSmartTypes = smartFileType.length; 
			else numberOfSmartTypes = sfp.smartFormTypesList.size(); 
			//for (int i=0; i < numberOfSmartTypes; i++){
			for (int i=98; i < numberOfSmartTypes; i++){
			//for (int i=0; i < 25; i++){
				
				// Initialize timer variables
				elapsed = "";
				before = 0;
				after = 0;
				delay = 0;
				////
								
				sfp.smartfileTypeSearchField.clear();	
				
				typeName = "";
				issueString = "";
				issueFlag = ""; // knownIssue, WIP
				try { 
					if (debugMode == true) typeName = smartFileType[i]; 
					else typeName = sfp.smartFormTypesList.get(i).getText();
					
					if (typeName.equals("")) {
						System.out.println("Error when looking for smart type in array place " +i);
						continue;
					}
				} catch (Exception e) { 
					System.out.println("Exception thrown when looking for smart type in array place " +i);
					continue; 
				}
				
				// Skip the type if it is on the black list
				if (smartTypeBlackList.contains("@ "+typeName+" @")) continue;
				
				// Skip if blocked by an issue
				issueString = (String) ht.get(typeName); 
				if ( issueString != null ) {
					if (issueFlag.contains("WIP")) issueFlag = WIP;
					else issueFlag = knownIssue;
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Test is blocked; see Comments column", "Blocked"+issueFlag+issueString);
					continue;
				}
				
				// Initialize 
				currStepResult = "Pass"; 
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					sfp.searchSmartFileType(typeName) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Search for smartType on smartType list", currStepResult);
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.ClickButton(sfp.GetSmartFileType(driver, typeName)) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+"# "+i+" : Select smartType ", currStepResult);
				
				// test step
				before = Calendar.getInstance().getTimeInMillis();
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.ClickButton(sfp.addSmartItemButton) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Click Add SmartItem button", currStepResult);
				
				openSmartForm = GeneralMethods.WaitForElement(driver, 10, typeName+": open SmartForm", sfp.openSmartForm);
				if (openSmartForm != null){
				   after = Calendar.getInstance().getTimeInMillis();
				   delay = after - before;
				   elapsed = Integer.toString((int) delay);
				}
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.checkIsDisplayed(sfp.openSmartForm) ? "Pass" : "Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Check that smartType opened", currStepResult+",,"+elapsed);
				
				// find list of required fields
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
				prevStepResult = currStepResult;
				if (prevStepResult.equals("Pass")){
					
					//requiredFields = GeneralMethods.FindElementsInObjHierarchy(sfp.openSmartForm, sfp.requiredFieldsCSS);
					fieldLabels = GeneralMethods.FindElementsInObjHierarchy(sfp.openSmartFormHeader, sfp.fieldLabelsCSS);
					requiredFieldLabels = GeneralMethods.FindElementsInObjHierarchy(sfp.openSmartForm, sfp.requiredFieldLabelsCSS);
				
					// Populate all the required fields
					String labelName = "";
					if ((requiredFieldLabels != null) && (requiredFieldLabels.size() > 0))  {
						//size = requiredFieldLabels.size();
						for (j = 0; j < requiredFieldLabels.size(); j++){
							labelName = requiredFieldLabels.get(j).getText();
							
							// test step
							if (GeneralMethods.WaitForElementBySelector(driver, 0, "Required field indicator", sfp.requiredFieldIndicatorCSS)==null){
								currStepResult = "Pass";
								Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Missing required field indicator; skipping field "+labelName, currStepResult);
								continue;
							}
							
							// test step
							currStepResult = !smartFilesOpened.equals("Pass") ? "Blocked" :
								sfp.PopulateSmartFileFieldByLabelName(requiredFieldLabels.get(j), uid) ? "Pass" : "Fail";
							Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Populate required field "+labelName, currStepResult);	
						}
					}
					
					else {
						// test step
						currStepResult = "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": No required fields were found", currStepResult);
					}
						
					/*
					// If there are no required fields, look for labels with "Name" or "Label" in the header
					else{
						if (fieldLabels != null) size = fieldLabels.size();
						else size = 0;
						if (size > 0)
							for(WebElement label : fieldLabels)
								if (label.getText().contains("Name") || label.getText().contains("Label")){
									labelName = label.getText();
									
									// test step
									currStepResult = !smartFilesOpened.equals("Pass") ? "Blocked" :
										sfp.PopulateSmartFileFieldByLabelName(label, uid) ? "Pass" : "Fail";
									Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Populate Label/Name field "+labelName, currStepResult);
								}
					}
					*/
				}
				driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.SECONDS);
				
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					GeneralMethods.ClickButton(sfp.openSmartFormAddButton) ? "Pass" : 
						GeneralMethods.ClickButton(sfp.openSmartFormAddExistingButton) ? "Pass" :"Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Click on Add/Add Existing button", currStepResult);
				
				// test step
				//GeneralMethods.delay(eventDelay*2);
				sfp.WaitForSmartFileToClose();
				if (!GeneralMethods.checkElementIsNotVisible(driver, sfp.openSmartForm)){
					try {sfp.forceCloseSmartForm(typeName);}
					catch (Exception e) {}
					currStepResult = "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Force close smartForm after Add", currStepResult);
				}
				sfp.WaitForSmartFileToClose();
								
				// test step
				prevStepResult = currStepResult;
				currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
					sfp.FindSmartFile(uid) != null ? "Pass" :"Fail";
				Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+typeName+": Verify smartFile type was successfully created", currStepResult);
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
