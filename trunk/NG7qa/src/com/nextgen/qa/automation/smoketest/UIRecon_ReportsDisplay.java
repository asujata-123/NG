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
import com.nextgen.qa.automation.smoketest.*;
import com.nextgen.qa.automation.Issues.*;
import com.nextgen.qa.automation.toolbox.*;
import com.thoughtworks.selenium.Wait;

/**
 * @author subha,mbodoh<li>
 *  changes done by subha.
 * Smoke Test: Report Display:<li>
 * This smoke test verifies that reports can be searched for and selected from the report search category<li>
 * 		Search for "Report" from the global search field.
 * 		Select sample_report result.
 * 		Verify that the selected report displays.
 * 		Verify the report WebElements.
 * 		Dismiss the open report by closing the tab.
 * 		Open report interface from main menu
 */
public class UIRecon_ReportsDisplay extends NG7TestCase {

	@Test
	public static void test() throws Exception {
		try {
			NG7TestCase.testName = "UIRecon_ReportsDisplay";	
			NG7TestCase.tester = "Subha";
	
			WebDriver driver = GeneralMethods.startDriver();
		
			BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), testName+"  ",timeStamp);
		
			// Objects used
			MainPage mp = new MainPage(driver, "mainpage");
			LoginPage lp = new LoginPage(driver, "loginpage");
			ReportsPage rp = new ReportsPage(driver, "reportspage.txt");
			ErrorPage ep = new ErrorPage(driver, "errorpage");
			SecondModalPage smp = new SecondModalPage(driver, "secondmodal.txt"); 
	
			System.out.println("* * * * * Start of " +testName +" test * * * * *");
			int localStressLoop = AutomationSettings.getLocalStressLoopIterations();
		
			// Test case infrastructure
			String currStepResult = null;
			String prevStepResult = null;
			String iterationStamp = "";
			String preReq = null;
			String reporttitlereturn= null;
		
			// Test data
			String reportTitle1 = AutomationSettings.getTestDataItem("reportTitle1");
			String reportTitle2 = AutomationSettings.getTestDataItem("reportTitle2");
			String category = "report";
			//String [] method = {"searchResult", "menu"};
			String [] method = {"menu"};
			String subFolderName = "Scheduling";
			//String folderName = "System Report Folder";
			String folderName = "Base Reports";
			
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

			    String preReq_openedReport = "";
			    String reportTabOpen = "";
		    	String reportModalOpen ="";
		    	
			    for (int i1 = 0; i1 < method.length; i1++){
			    	
			    	// Open the reports interface by the selected method
			    	if (method[i1].equals("searchResult")){ 
			    		// test step
			    		System.out.println("value of the donot deliver1"  +doNotDeliverCheck );
			    		NG7TestCase.testData = "reportTitle = "+reportTitle1;
						currStepResult = !preReq.equals("Pass") ? "Blocked" :
							mp.performGlobalSearch(reportTitle1) ? "Pass" : 
								mp.performGlobalSearch(reportTitle2) ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Perform global search for report <reportTitle>", currStepResult);
						
						// test step
						prevStepResult = currStepResult;
						currStepResult = (prevStepResult.equals("Pass") == false) ? "Blocked" :
							mp.selectSearchResult(reportTitle1, category, false) ? "Pass" : 
								mp.selectSearchResult(reportTitle2, category, false) ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Select report from results of category "+category, currStepResult);
						preReq_openedReport = currStepResult;
			    	} 
			    	
			    	else {
			    		// test step
						prevStepResult = currStepResult;
						System.out.println("value of the donot deliver2"  +doNotDeliverCheck );
						currStepResult = !preReq.equals("Pass") ? "Blocked" :
							mp.selectHomeMenuItem("reports") ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Open Reports interface from home menu", currStepResult);
			    	
						WebElement modal = smp.getVisibleSlidingModal();
						if (modal != null)
							reportModalOpen = "Pass";
						else
							reportModalOpen = "Fail";
			    		
						// test step
						List<WebElement> reportFolder = GeneralMethods.FindElementsInObjHierarchy(modal, "ul[class *= 'flex-auto folder-explorer'] li");
						prevStepResult = currStepResult;
						currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
							GeneralMethods.checkIsNotEmpty(reportFolder) ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Reports Folder list is not empty", currStepResult);
					
						// test step
						NG7TestCase.testData = "folder name = "+folderName;
						prevStepResult = currStepResult;
						currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
							smp.clickElement(smp.findElementInList("li", folderName, reportFolder, ""), folderName) ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on "+folderName, currStepResult);
					
						// test step
						List<WebElement> reportsSubFolder = GeneralMethods.FindElementsInObjHierarchy(modal, "ul[class *= 'sub-folder-explorer'] li");
						prevStepResult = currStepResult;
						currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
							GeneralMethods.checkIsNotEmpty(reportsSubFolder) ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Reports sub folder list is not empty", currStepResult);
					
						// test step
						prevStepResult = currStepResult;
						currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
							smp.clickElement(smp.findElementInList("li", subFolderName, reportsSubFolder, ""), subFolderName) ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on "+subFolderName, currStepResult);
					
						// test step
						List<WebElement> reportFiles = GeneralMethods.FindElementsInObjHierarchy(modal, "ul[class *= 'flex-auto file-explorer'] li");
						prevStepResult = currStepResult;
						currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
							GeneralMethods.checkIsNotEmpty(reportsSubFolder) ? "Pass" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Reports file list is not empty", currStepResult);
					
						// test step
						NG7TestCase.testData = "reportTitle = "+reportTitle1;
						prevStepResult = currStepResult;
						currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
							smp.clickElement(smp.findElementInList("li", reportTitle1, reportFiles, ""), reportTitle1) ? "Pass" :"Fail"; 
								//smp.clickElement(smp.findElementInList("li", reportTitle2, reportFiles, ""), reportTitle2) ? "Pass" :"Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on report file <reportTitle>", currStepResult);
						String preReq_clickedFile = currStepResult;
					
						// test step
						prevStepResult = currStepResult;
						currStepResult = !preReq_clickedFile.equals("Pass") ? "Blocked" :
							smp.clickElement(smp.findVisibleButton(modal, "Open Report", ""), "Open Report button") ? "Pass" :
								smp.clickElement(smp.findVisibleButton(modal, "Cancel", ""), "Cancel button") ? "Fail" : "Fail";
						Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click on Open Report button", currStepResult);
						preReq_openedReport = currStepResult;	
			    	}
					// start from here for report tab check what is displayed
					// test step
			    	prevStepResult = currStepResult;
					currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
						mp.getOpenTab("Reports") != null ? "Pass" : "Fail";
					System.out.println("Check report tab");
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Report tab is open", currStepResult);
					reportTabOpen = currStepResult;
			    		
					// test step
					prevStepResult = currStepResult;
					currStepResult = !reportTabOpen.equals("Pass") ? "Blocked" :
						rp.checkElementIsVisible(rp.openReportHeaderInfo, "report header info") ? "Pass" : "Fail";
					
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the report contains a header", currStepResult);
					String openReport_PreReq = currStepResult;
					
					//test step -- check the title of the report
					prevStepResult = currStepResult;
					currStepResult = !reportTabOpen.equals("Pass") ? "Blocked":
					  rp.getText(rp.openReportHeaderTitle, "report title").toString().trim().equals(reportTitle1) ? "Pass"  : "Fail";
		            Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that the report matches the title", currStepResult);
					
								
					// test step
					prevStepResult = currStepResult;
					currStepResult = !openReport_PreReq.equals("Pass") ? "Blocked" :
						GeneralMethods.checkListIsDisplayed(rp.reportSectionTabs) ? "Pass" : "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check open report contains section tabs", currStepResult);
		   
					// test step
					prevStepResult = currStepResult;
					currStepResult = !openReport_PreReq.equals("Pass") ? "Blocked" :
						GeneralMethods.checkElementIsNotVisible(driver, rp.reportGrid) ? "Pass" : "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that report grid is not visible", currStepResult);
					
					
						
					// test step
					prevStepResult = currStepResult;
					currStepResult = !openReport_PreReq.equals("Pass") ? "Blocked" :
						rp.clickElement(rp.runButton, "runReport button") ? "Pass" : "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Click Run button", currStepResult);
					
					
					
					
					
					// test step
					GeneralMethods.delay(40000);
					prevStepResult = currStepResult;
					currStepResult = !openReport_PreReq.equals("Pass") ? "Blocked" :
						GeneralMethods.clickFieldCheckListIsNotEmpty(rp.exportButton, rp.exportButtonList) ? "Pass" : "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check open report contains an export button with export options", currStepResult);
					
					// test step
					// Modified by subha commenting below since there is no preview and can not come back from print preview to reports 
					//future work 
					/*prevStepResult = currStepResult;
					currStepResult = !openReport_PreReq.equals("Pass") ? "Blocked" :
						rp.checkElementIsVisible(rp.previewButton, "open report preview button") ? "Pass" : "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check open report contains a preview button", currStepResult);*/
					
					//test step
					// Subha - added chart 
					prevStepResult = currStepResult;
					currStepResult = !openReport_PreReq.equals("Pass") ? "Blocked" :
						     !rp.checkElementIsVisible(rp.graphButton,"Click Graph button") ? "Blocked":
					    	 rp.clickElement(rp.graphButton, "Graph Button" )? "Pass": "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Graph Button", currStepResult);
					
					//test step 
					// Subha - added Icon
					prevStepResult = currStepResult;
					currStepResult = !openReport_PreReq.equals("Pass") ? "Blocked" :
						!rp.checkElementIsVisible(rp.iconButton,"Click Icon button") ? "Blocked":
					    	 rp.clickElement(rp.iconButton, "Icon Button" )? "Pass": "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check Icon Button", currStepResult);
					
					//future development - select the chart and printing the reports
					
					// test step 	
					//Changes done by Subha -03/15
					
					prevStepResult = currStepResult;
					GeneralMethods.ClickButton(rp.getFooterButton("Run"));
					GeneralMethods.delay(40000);
					currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
						rp.checkElementIsVisible(rp.reportGrid, "report grid") ? "Pass" : "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check that report grid appears", currStepResult );
					
					
					// test step
					//Changes done by Subha -03/15 
					//GeneralMethods.delay(40000);
					prevStepResult = currStepResult;
					currStepResult = !prevStepResult.equals("Pass") ? "Blocked" :
						rp.getFooterButton("Save") != null ? "Pass" : "Fail";
					Artifact.VerifyWriteToArtifactS(artifact, iterationStamp+"Check open report contains a Save button", currStepResult);
					
					if (reportTabOpen.equals("Pass"))
						mp.closeAllOpenTabs(); // close all open reports
			    }
			}
				
			// Cleanup
			if(preReq.equals("Pass"))
				mp.logOutUser();
			driver.quit();
			driver = null;
			
			Artifact.CloseArtifact(artifact);
		} catch (Exception e){
			
		}
	}
}


// TODO (as of Sprint 21 review demo) coming soon: multiple reports open
