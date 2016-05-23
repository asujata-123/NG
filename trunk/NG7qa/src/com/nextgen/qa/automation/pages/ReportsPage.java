package com.nextgen.qa.automation.pages;

import com.nextgen.qa.automation.toolbox.Artifact;
import com.nextgen.qa.automation.toolbox.AutomationSettings;
import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.ui.GenericPage;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

//import com.nextgen.qa.automation.toolbox.AutomationSettings;


//import org.openqa.selenium.By;
//import org.openqa.selenium.StaleElementReferenceException;



/**
 * 
 */

/**
 * @author raguirre changes done by subha
 *
 */
public class ReportsPage extends GenericPage {

	/**
	 * 
	 */
	public ReportsPage(WebDriver aDriver, String aLOG_FILE) {	
		   super(aDriver, aLOG_FILE);
		   this.driver = aDriver;
	       this.LOG_FILE = aLOG_FILE;
		}


	
	// Web Elements
	@FindBy(how = How.CSS, using = "div[ng-controller='ReportCtrl']")
	public WebElement openReport;
	
	 
	@FindBy(how = How.CSS, using = "div[class^='reportHeaderInfo']")
	public WebElement openReportHeaderInfo;
	
	//report title
	@FindBy(how = How.CSS, using = "li[class*='reports-navbar-tab ng-scope navbar-tab-active']")
	public WebElement openReportHeaderTitle;
	
	@FindBy(how = How.CSS, using = "div[class^='accordion-container'] div[class^='accordion-tab']")
	public List<WebElement> reportSectionTabs;
	
	//@FindBy(how = How.CSS, using = "button[type='button'][class^='export-btn']")
	//@FindBy(how = How.CSS, using = "div[class^='icons'] img[src*='export']") // MGB 7/31/2014
	@FindBy(how = How.CSS, using = "span[id='reportExport']")// MGB 12/20/2014
	public WebElement exportButton;
	
	//@FindBy(how = How.CSS, using = "span[class='toolbar-icons-sm'] img[src*='print']")
	@FindBy(how = How.CSS, using = "div[class^='icons'] img[src*='print']")
	public WebElement printButton;
	
	@FindBy(how = How.CSS, using = "span[class*='icons'] img[src*='preview']")
	//@FindBy(how = How.CSS, using = "div[class^='icons'] img[src*='preview']")
	public WebElement previewButton;
	
	@FindBy(how = How.CSS, using = "div[class*='bottombar'] button[class *= 'btn'][data-ng-click *= 'runReport']")
	public WebElement runButton;
	
	//@FindBy(how = How.CSS, using = "span[class='toolbar-icons-sm'] img[src*='graph']")
	//@FindBy(how = How.CSS, using = "div[class^='icons'] img[src*='graph']")
	@FindBy(how = How.CSS, using = "div[class^='icons'] img[src*='chart']") // MGB 9/4/2014
	public WebElement chartButton; //  MGB 9/4/2014 renamed button from graphButton
	
	// subha 03/15
	@FindBy(how = How.CSS, using = "div[class^='header-block'] img[src*='graph']") 
	public WebElement graphButton; //  for grpah button
	
	// subha 03/15
	@FindBy(how = How.CSS, using = "div[class^='header-block'] img[src*='grid-active']") 

	public WebElement iconButton; //  for ICon button
	
	//@FindBy(how = How.CSS, using = "span[class='toolbar-icons-sm'] img[src*='plus']")
	//@FindBy(how = How.CSS, using = "span[class^='header-icons'] img[src*='plus']")
	@FindBy(how = How.CSS, using = "div[class^='reports-tabbar-array'] img[src*='plus']")
	public WebElement plusButton;
	
	//@FindBy(how = How.CSS, using = "button[type='button'][class^='export-btn'] ~ ul li")
	@FindBy(how = How.CSS, using = "div[id^='reportExportDropDown'] li") // MGB 7/31/2014
	public List<WebElement> exportButtonList;
	
	@FindBy(how = How.CSS, using = "button[class^='btn']")
	public List<WebElement> footerButtons;
	
	@FindBy(how = How.CSS, using = "div[class^='report-grid'")
	public WebElement reportGrid;
	
        
    
    //actions
	/*public boolean checkReportOpened() throws Exception {
		try{
			if (this.openReport.getText().equals(reportTitle)) return true;
			System.out.println("could not find open report of title " +reportTitle);
			return false;
		} catch (Exception e) { return false; }
	}
	*/
	
	public boolean selectReportSection(String sectionName) throws Exception {
		try{
			for (WebElement tab : this.reportSectionTabs)
				if (tab.getText().equals(sectionName)) GeneralMethods.ClickButton(tab);
			System.out.println("could not find tab for section name " +sectionName);
			return false;
		} catch (Exception e) { return false; }
	}
	
	public WebElement getFooterButton(String buttonName) throws Exception {
		try{
			for (WebElement button : this.footerButtons)
				if (button.getText().contains(buttonName)) return button;
			System.out.println("could not find footer button of name " +buttonName);
			return null;
		} catch (Exception e) { return null; }
	}

}
