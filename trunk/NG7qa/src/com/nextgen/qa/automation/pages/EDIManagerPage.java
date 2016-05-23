	
package com.nextgen.qa.automation.pages;


	
	import java.io.IOException;
import java.util.List;

	import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

	import com.nextgen.qa.automation.toolbox.*;
import com.nextgen.qa.automation.ui.*;

	/**
	 * 
	 */

	/**
	 * @Sujata Sudhakar
	 *
	 */
	public class EDIManagerPage extends GenericPage {
		

			 
			/**
			 * 
			 */	
			public EDIManagerPage(WebDriver aDriver, String aLOG_FILE) {	
			   super(aDriver, aLOG_FILE);
			   this.driver = aDriver;
		       this.LOG_FILE = aLOG_FILE;
			}
			
			public LoginPage lp = new LoginPage(driver, "login.txt");
			public MainPage mp = new MainPage(driver, "main.txt");
			
			// Web Elements
		    @FindBy(how = How.CSS, using = "div[class^='edi-manager'][data-ng-controller='ediManagerCtrl']")
		    public WebElement openEDIManager;
		    
		    @FindBy(how = How.CSS, using = "div[class='sidebar-header-block'] h1[class='sidebar-header']")
		    		public WebElement EDIManager;
		    
		    @FindBy(how = How.CSS, using = "div[class *= 'patient-header-container']") // MGB 2/27/2015
			public WebElement openEDIManagerTab;
		    
		    @FindBy(how = How.CSS, using = "ul[class*='info-list-tabs'] li")
		    public List<WebElement> EDIManagerTabs;
		    
		    @FindBy(how = How.CSS, using = "div[class^='edi-manager-calendar']")
		    public WebElement ediManagerCalendar;
		    
		    @FindBy(how = How.CSS, using = "ul[class^='edi-tab' id = 'ediOrgTest]")
		    public WebElement ediOrgTest;
		    
		    @FindBy(how = How.CSS, using = "div[class^='edi-manager-wrapper']")
		    public WebElement ediManagerWrapper;
		    
		   // @FindBy(how = How.CSS, using = "ul[class^='info-list'][ng-click ^='ediManager']")
		   // public WebElement Calendar;
		    
		    @FindBy(how = How.CSS, using = "section[class^='EDIManager'][data-ng-controller='ediCalendarCtrl']")
		    public WebElement ediClaimManagerCtrl;
		    
		    @FindBy(how = How.CSS, using = "section[class^='EDIManager'][data-ng-controller='ediClaimManagerCtrl']")
		    public WebElement ediEraManagerCtrl;
		    
		    @FindBy(how = How.CSS, using = "section[class^='EDIManager'][data-ng-controller='ediEraManagerCtrl']")
		    public WebElement ediStatementManagerCtrl;
		    
		    //Filter By elements
		    
		    //@FindBy(how = How.CSS, using = "div[class='monthline display-inline-flex v-center'] select[ng-options*=logicalDates']")
		    //public WebElement Filterby;
		    //@FindBy(how = How.CSS, using = "div[class='monthline display-inline-flex v-center'] select[class*=edi-date-list']")
		    //public WebElement Filterby;
		   	       
		    //Check Eligibility sliding modal webelements
		    
		//  @FindBy(how = How.CSS, using = "div[class='modalTemplate ng-scope'] div[class*='check-eligibility-modal'] div[class = 'modalContent'] div[class='row']");
		 // WebElement SelectAll;
		      
		    @FindBy(how = How.CSS, using = "div[class='group organization'] li[data-ng-repeat ^= 'node in'] span")
		    public WebElement organizationTopNode;

		    @FindBy(how = How.CSS, using = "div[class='group organization'] li[data-ng-repeat ^= 'node in']")
		    public List<WebElement> organizationNodesList;
		    
		    
		    //@FindBy(how = How.CSS, using = "li[data-ng-repeat = 'node in organizations']")
		    //@FindBy(how = How.CSS, using = "li[data-ng-repeat ^= 'node in']")
		    @FindBy(how = How.CSS, using = "div[class='group organization'] span") // MGB 7/3/2014
		    public List<WebElement> organizationList;
		    
		    //@FindBy(how = How.CSS, using = "div[class='group organization'] li[data-ng-repeat='node in organizations']")
		    @FindBy(how = How.CSS, using = "div[class='group organization'] li[data-ng-repeat^='node in']")
		    public List<WebElement> organizationParentNodes;
		    
		    //@FindBy(how = How.CSS, using = "li[data-ng-repeat ^= 'node in'] ul")
		    @FindBy(how = How.CSS, using = "div[class='group organization'] li[data-ng-repeat='node in node.Children'] ul")
		    public List<WebElement> organizationNodes;
		    String organizationNodesCss = "ul[ng-show='node.open'] li";

		 // METHODS

		    public boolean wizardExists()throws IOException, InterruptedException {
		 	   return GeneralMethods.checkIsDisplayed(EDIManager);
		    }
		    
		    public boolean clickTab(String tabName, String tabDescription) throws IOException, InterruptedException {
		    	try{
					for (WebElement tab : EDIManagerTabs)
						if (tabName.equals(tab.getText())){
							super.clickElement(tab, tabDescription);
							GeneralMethods.delay(EVENTDELAY);
							return true;
						}
					return false;
				} catch (Exception e){
					return false;
				}   
		    }
	

	  public boolean clickFilterBy() throws IOException, InterruptedException {
	        return super.clickElement(Filterby, "Filterby drop down");
	    }
	  }

