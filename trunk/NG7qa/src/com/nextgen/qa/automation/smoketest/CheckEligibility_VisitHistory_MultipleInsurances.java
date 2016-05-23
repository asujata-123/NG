package com.nextgen.qa.automation.smoketest;

import java.io.BufferedWriter;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.nextgen.qa.automation.pages.ErrorPage;
import com.nextgen.qa.automation.pages.LoginPage;
import com.nextgen.qa.automation.pages.MainPage;
import com.nextgen.qa.automation.pages.PatientDashboardPage;
import com.nextgen.qa.automation.pages.SecondModalPage;
import com.nextgen.qa.automation.toolbox.Artifact;
import com.nextgen.qa.automation.toolbox.AutomationSettings;
import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.toolbox.NG7TestCase;

public class CheckEligibility_VisitHistory_MultipleInsurances extends NG7TestCase {
	  
@Test
public static void test() throws Exception {
	NG7TestCase.testName = "CheckEligibility_VisitHistory_MultipleInsurances";	
	NG7TestCase.tester = "Sujata Sudhakar";
	
	WebDriver driver = GeneralMethods.startDriver();
	
	BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
	
	// Objects used
	MainPage mp = new MainPage(driver, "mainpage");
	LoginPage lp = new LoginPage(driver, "loginpage");
	PatientDashboardPage pdb = new PatientDashboardPage(driver, "patientdashboard.txt");
  ErrorPage ep = new ErrorPage(driver, "errorpage");
  SecondModalPage smp = new SecondModalPage(driver, "secondmodal.txt"); 

	System.out.println("* * * * * Start of " +testName +" test * * * * *");
	int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
	
	// Test case infrastructure
	String currStepResult = null;
	String prevStepResult = null;
	String iterationStamp = "";
	String preReq = null;
	String checkEligibilitySlidingModal ="";
		
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
		//NG7TestCase.testData = "patientName = "+patientName;
		patientName = "Seth Curringa";
		currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
			mp.performGlobalSearch(patientName) ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Search for patient <patientName>", currStepResult);
		prevStepResult = currStepResult;
		
		// test step
		NG7TestCase.testData = "patientName = "+patientName;
		String category = "patient";
		currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
			mp.selectSearchResult(patientName, category, true) ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select search result for patient <patientName>", currStepResult);
		prevStepResult = currStepResult;
		String preReq_globalSearch = currStepResult;

		// test step
		NG7TestCase.testData = "patientName = "+patientName;
		WebElement openPatientTab = null;
		if (preReq_globalSearch.equals("Pass")) openPatientTab = mp.getOpenTab(patientName);
		currStepResult = (preReq_globalSearch.equals("Pass") == false) ? "Blocked" :
			(openPatientTab != null && openPatientTab.getText().contains("Person Center")) ? "Fail" : "Pass";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Patient Center for <patientName> was opened", currStepResult);
		prevStepResult = currStepResult;
		String preReq_openPatientCenter = currStepResult;
		
		String preReq_patientTabOpened = "";
			
		// test step
		NG7TestCase.testData = "patientName = "+patientName;
		currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :
			pdb.checkElementIsVisible(pdb.openPatientTab, "open patient tab") ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the patient center fpr <patientName> opens", currStepResult);
		prevStepResult = currStepResult;
		preReq_patientTabOpened = currStepResult;
		
						
		// test step
		currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
			pdb.clickTab("Financial", "Patient Dashboard Financial Tab") ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Financial tab", currStepResult);
		prevStepResult = currStepResult;
		//String preReq_financialGridOpened = currStepResult;
		
		// test step
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
			currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :
			pdb.clickTab("Visit History", "Patient Dashboard Visit History Tab") ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Visit History tab", currStepResult);
		prevStepResult = currStepResult;
		//String preReq_financialGridOpened = currStepResult;
		
		// test step
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
		/*currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :
			pdb.clickTab("Insurance", "Patient Dashboard Insurance Tab") ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Insurance tab", currStepResult);
		prevStepResult = currStepResult;
		//String preReq_financialGridOpened = currStepResult;*/
		
					
		// test step
		//if (preReq_patientTabOpened.equals("Pass")) GeneralMethods.delay(eventDelay*2);
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
		//	pdb.checkElementIsVisible(pdb.InsuranceTab, "Insurance Tab") ? "Pass" : "Fail";
		//Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Insurance tab opened", currStepResult);
		//prevStepResult = currStepResult;
		//String preReq_financialGridOpened = currStepResult;	
		
		// test step
		currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
		//currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :
			//pdb.clickButton("Check Eligibility", "Insurance Tab Check Eligibility") ? "Pass" : "Fail";
			GeneralMethods.ClickButton(pdb.findVisibleElement(pdb.openPatientDashboard, pdb.buttonCheckEligibilityCss)) ? "Pass" : "Fail"; // MGB 3/9/2015 updated to use css
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Eligibility Button", currStepResult);
		prevStepResult = currStepResult;
		String preReq_checkEligibilitySlidingModal = currStepResult;
		//String preReq_financialGridOpened = currStepResult;
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		WebElement modal = smp.getVisibleSlidingModal();
		if (modal != null)
			checkEligibilitySlidingModal = "Pass";
		else
			checkEligibilitySlidingModal = "Fail";
		
		// test step
		prevStepResult = currStepResult;
		currStepResult = preReq_checkEligibilitySlidingModal.equals("Pass") == false ? "Blocked" :
			//nap.checkElementIsVisible(nap.noPatientOption, "new appointment wizard no patient option button") ? "Pass" : "Fail";
			pdb.clickElement(pdb.SelectAllCheckbox, "Select All CheckBox") ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Verify All Payer plan ", currStepResult);
		String preReq_CheckButtonEnabled = currStepResult;
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		// test step
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
		currStepResult = (preReq_CheckButtonEnabled.equals("Pass") == false) ? "Blocked" :
			//pdb.clickButton("Check Eligibility", "Insurance Tab Check Eligibility") ? "Pass" : "Fail";
			GeneralMethods.ClickButton(pdb.findVisibleElement(pdb.activeeligibilitymodal, pdb.buttonCheckEligiCss)) ? "Pass" : "Fail"; // MGB 3/9/2015 updated to use css
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Eligibility Button", currStepResult);
		prevStepResult = currStepResult;
	//String preReq_checkEligibilitySlidingModal = currStepResult;
		
		// test step
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
		/*currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :
			pdb.clickTab("Insurance", "Patient Dashboard Insurance Tab") ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Insurance tab", currStepResult);
		prevStepResult = currStepResult;
		//String preReq_openInsuranceTab = currStepResult;
		String preReq_openInsuranceTab = "";*/
		
		// test step
		/*prevStepResult = currStepResult;
		currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
			GeneralMethods.checkIsDisplayed(pdb.openInsuranceTab) ? "Pass" : "Fail";
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Insurance tab is opened", currStepResult);
		//String BatchMaintenaceCtrlOpened = currStepResult;

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);*/
		
		// test step
		/*prevStepResult = currStepResult;
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
		currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :
		//currStepResult = (preReq_openInsuranceTab("Pass") == false) ? "Blocked" :
			//pdb.clickButton("Check Eligibility", "Insurance Tab Check Eligibility") ? "Pass" : "Fail";
			GeneralMethods.ClickButton(pdb.findVisibleElement(pdb.openInsuranceTab, pdb.buttonNewInsuranceCss)) ? "Pass" : "Fail"; // MGB 3/9/2015 updated to use css
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"New Insurance Button", currStepResult);
		prevStepResult = currStepResult;*/
		
		
		// test step
		/*prevStepResult = currStepResult;
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
		currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :
		//currStepResult = (preReq_openInsuranceTab("Pass") == false) ? "Blocked" :
			//pdb.clickButton("Check Eligibility", "Insurance Tab Check Eligibility") ? "Pass" : "Fail";
			GeneralMethods.ClickButton(pdb.findVisibleElement(pdb.openPatientDashboard, pdb.buttonNewInsuranceCss)) ? "Pass" : "Fail"; // MGB 3/9/2015 updated to use css
		Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"New Insurance Button", currStepResult);
		prevStepResult = currStepResult;*/
		
/*		  			currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
		//GeneralMethods.ClickButton(chargesLink) ? "Pass" : "Fail";
		GeneralMethods.ClickButton(pdb.findVisibleElement(pdb.openPatientDashboard, pdb.linkChargesCss)) ? "Pass" : "Fail";
	Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Financial grid Charges link", currStepResult);
	prevStepResult = currStepResult;			
*/				
		// test step
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
			//pdb.clickButton("InsuranceTabButton", "Insurance Tab Check Eligibility") ? "Pass" : "Fail";
		//Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Eligibility Button", currStepResult);
		//prevStepResult = currStepResult;
		//String preReq_financialGridOpened = currStepResult;
		
		// test step
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
		//	pdb.clickButton(pdb.checkInsuranceEligibility, "Insurance Tab Check Eligibility") ? "Pass" : "Fail";
		//Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"checkInsuranceEligibility", currStepResult);
		//prevStepResult = currStepResult;
		//String preReq_financialGridOpened = currStepResult;
		
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
			//pdb.clickButton(pdb.url = '/nav/patient/' + patientId + '/patientInsurance/PersonPlanId/' + data.PersonPlanId + '/checkInsuranceEligibility', "Insurance Tab Check Eligibility") ? "Pass" : "Fail";
		//Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"checkInsuranceEligibility", currStepResult);
		//prevStepResult = currStepResult;
		//String preReq_financialGridOpened = currStepResult;
		
		
		// test step
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
		//currStepResult = pdb.clickTab("Insurance", "Patient Dashboard Insurance Tab") ? "Pass" : "Fail";
							//	pdb.clickButton("checkInsuranceEligibility", "Insurance Tab Check Eligibility") ? "Pass" : "Fail";
		//Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"checkInsuranceEligibility", currStepResult);
		//prevStepResult = currStepResult;
		//String preReq_financialGridOpened = currStepResult;
		
		
		// test step
		//currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
		//	GeneralMethods.ClickButton(openPatientTab.click(Insurance); Eligibility) ? "Pass" : "Fail";
		//GeneralMethods.ClickButton(pdb.findVisibleElement(pdb.openPatientDashboard, pdb.Css)) ? "Pass" : "Fail";
			//GeneralMethods.ClickButton(pdb.findVisibleElement(pdb.openPatientDashboard, checkEligibilityInsurance)) ? "Pass" : "Fail";
		//Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Financial grid Charges link", currStepResult);
		//prevStepResult = currStepResult;
		
		//if (preReq_patientTabOpened.equals("Pass")) GeneralMethods.delay(eventDelay*2);
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
			//pdb.checkElementIsVisible(pdb.financialGrid, "financial grid") ? "Pass" : "Fail";
		//Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Financial grid opened", currStepResult);
		//prevStepResult = currStepResult;
		//String preReq_financialGridOpened = currStepResult;	
		
		// test step
	//	public String CheckEligibility;
		//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
			//GeneralMethods.ClickButton(pdb.getInsuranceTabButton, buttonDescription)Check Eligibility) ? "Pass" : "Fail";
		//currStepResult= pdb.clickTab("Insurance", "Patient Dashboard Insurance Tab") ? "Pass" : "Fail";
			//pdb.clickButton(InsuranceTabButton, "Patient Dashboard Check Eligibility button") ? "Pass" : "Fail";
			//GeneralMethods.ClickButton(pdb.checkEligibility pdb.openPatientDashboard, pdb.linkChargesCss)) ? "Pass" : "Fail";
		//Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click checkEligibilityInsurance", currStepResult);
	//	prevStepResult = currStepResult;
		//String preReq_financialGridOpened = currStepResult;
		
		//if (preReq_patientTabOpened.equals("Pass"))
			//mp.closeAllOpenTabs();	
		}
		
		// Cleanup
  	if (preReq.equals("Pass"))
  		mp.logOutUser();
		driver.quit();
		driver = null;

		Artifact.CloseArtifact(artifact);
}

private static boolean preReq_openInsuranceTab(String string) {
	// TODO Auto-generated method stub
	return false;
}
}







