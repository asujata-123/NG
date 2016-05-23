package com.nextgen.qa.automation.smoketest;

import java.io.BufferedWriter;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.nextgen.qa.automation.pages.EDIManagerPage;
import com.nextgen.qa.automation.pages.ErrorPage;
import com.nextgen.qa.automation.pages.FinancialManagerPage;
import com.nextgen.qa.automation.pages.LoginPage;
import com.nextgen.qa.automation.pages.MainPage;
import com.nextgen.qa.automation.pages.PatientDashboardPage;
import com.nextgen.qa.automation.toolbox.Artifact;
import com.nextgen.qa.automation.toolbox.AutomationSettings;
import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.toolbox.NG7TestCase;

public class CreateChargeBatch extends NG7TestCase {
	  
	@Test
	public static void test() throws Exception {
		NG7TestCase.testName = "CreateChargeBatch";	
		NG7TestCase.tester = "Sujata Sudhakar";
		
		WebDriver driver = GeneralMethods.startDriver();
		
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
		// Objects used
					MainPage mp = new MainPage(driver, "mainpage");
					LoginPage lp = new LoginPage(driver, "loginpage");
					PatientDashboardPage pdb = new PatientDashboardPage(driver, "patientdashboard.txt");
				    ErrorPage ep = new ErrorPage(driver, "errorpage");
				    EDIManagerPage em = new EDIManagerPage(driver, "EDIManager");
				    FinancialManagerPage FMP = new FinancialManagerPage(driver, "FinancialManagerPage");
				
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
							mp.selectHomeMenuItem("Financial Manager") ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Financial Manager from the Home menu", currStepResult);
						//String openSched_PreReq = currStepResult;
						String financialManagerOpen_menu = currStepResult;
						//String FinancialManagerOpened = currStepResult;
						prevStepResult = currStepResult;
						
						
						
						
						// test step
						prevStepResult = currStepResult;
						currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
							GeneralMethods.checkIsDisplayed(FMP.FinancialManagerCtrl) ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Financial Manager control is opened", currStepResult);
						String FinancialManagerOpened = currStepResult;
						
						// test step
						currStepResult = (preReq.equals("Pass") == false) ? "Blocked" :
							FMP.clickTab("Batch Maintenance", "Batch Maintenance tab") ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Batch Maintenance tab ", currStepResult);
						prevStepResult = currStepResult;
						//String openSched_PreReq = currStepResult;
						//String financialManagerOpen_menu = currStepResult;
						
						
						
						
						// test step
						prevStepResult = currStepResult;
						currStepResult = prevStepResult.equals("Pass") == false ? "Blocked" :
							GeneralMethods.checkIsDisplayed(FMP.BatchMaintenanceCtrl) ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Batch Maintenace control is opened", currStepResult);
						
						//String preReq_checkEligibilitySlidingModal = currStepResult;
						prevStepResult = currStepResult;
						String preReq_BatchMaintenaceCtrlOpened = currStepResult;
						
						
							driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						
						// test step
						//currStepResult = preReq_patientTabOpened.equals("Pass") == false ? "Blocked" :
						//currStepResult = (preReq_openPatientCenter.equals("Pass") == false) ? "Blocked" :
						/*currStepResult = preReq_BatchMaintenaceCtrlOpened.equals("Pass") == false ? "Blocked" :
							pdb.clickButton(pdb., "Select All CheckBox") ? "Pass" : "Fail";
							//GeneralMethods.checkIsDisplayed(FMP.FinancialManagerCtrl) ? "Pass" : "Fail";
							//pdb.clickButton("Check Eligibility", "Insurance Tab Check Eligibility") ? "Pass" : "Fail";
							GeneralMethods.ClickButton(FMP.findVisibleElement(FMP.BatchMaintenanceCtrl, FMP.NewBatchCss)) ? "Pass" : "Fail"; // MGB 3/9/2015 updated to use css
						FMP.clickButton('NewBatchCss', 'New Batch')? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Eligibility Button", currStepResult);
						
						prevStepResult = currStepResult;
						//String preReq_financialGridOpened = currStepResult;*/
						
						
						
						
						// test step
						//currStepResult = (BatchMaintenaceCtrlOpened.equals("Pass") == false) ? "Blocked" :
							currStepResult = preReq_BatchMaintenaceCtrlOpened.equals("Pass") == false ? "Blocked" :
							FMP.clickButton("NewBatchCss", "New Batch Button") ? "Pass" : "Fail";
						//FMP.checkElementIsVisible(FMP.findVisibleWidget(FMP.BatchMaintenanceCtrl, FMP.NewBatchButtonCss, "transactions link") ? "Pass" : "Fail";
						//FMP.checkElementIsVisible
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click New Batch Button", currStepResult);
						prevStepResult = currStepResult;
						
						// test step
						//WebElement transactionsLink = null;
						//if (preReq_financialGridOpened.equals("Pass")) transactionsLink = pdb.findVisibleElement(pdb.openPatientDashboard, pdb.linkTransactionsCss);
						//currStepResult = (preReq_BatchMaintenaceCtrlOpened.equals("Pass") == false) ? "Blocked" :
						//	FMP.checkElementIsVisible(FMP.findVisibleElement(FMP.openFinancialManagerTab, FMP.NewBatchButtonCss, "transactions link") ? "Pass" : "Fail";
						//Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Financial grid Transactions link", currStepResult);
						//prevStepResult = currStepResult;
						
												
						// test step
						/*currStepResult = (preReq_financialGridOpened.equals("Pass") == false) ? "Blocked" :
							mp.selectHomeMenuItem("Financial Manager") ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Open Financial Manager from menu", currStepResult);
						prevStepResult = currStepResult;
						String financialManagerOpen_menu = currStepResult;*/
						
								
						
						
						
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
