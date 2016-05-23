package com.nextgen.qa.automation.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.ui.GenericPage;

public class FinancialManagerPage extends GenericPage {
	

	 
	/**
	 * 
	 */	
	public FinancialManagerPage(WebDriver aDriver, String aLOG_FILE) {	
	   super(aDriver, aLOG_FILE);
	   this.driver = aDriver;
       this.LOG_FILE = aLOG_FILE;
	}
	
	public LoginPage lp = new LoginPage(driver, "login.txt");
	public MainPage mp = new MainPage(driver, "main.txt");
	
	// Web Elements
	//5/27/2015
	public String PrintBatchReportsCss = "input[data-ng-click*='openReport()']";
	public String NewBatchCss = "input[data-ng-click*='findModuleScope.newItemFormTemplate[0]']";
	@FindBy(how = How.CSS, using = "span[class*='text-capitalize']")
	public WebElement FinancialManager;
	
	//@FindBy(how = How.CSS, using = "div[id='patient-tab'][class ^= 'navbar-tab patient']")
			@FindBy(how = How.CSS, using = "div[class *= 'patient-header-container']") 
			public WebElement openFinancialManagerTab;
			
			@FindBy(how = How.CSS, using = "ul[class*='info-list-tabs'] li")
		    public List<WebElement> FinancialManagerTabs;
			//@FindBy(how = How.CSS, using = "div[class ^='batch-header-right']")
			//public WebElement BatchHeaderRightButtons;
			
			// @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class^='patient-dash-financial-grid'] input[type='button'][class^='btn']")
			 //   public List<WebElement> financialGridButtons;
			
			   @FindBy(how = How.CSS, using = "div[class*='sliding-modal']")
			    public static List<WebElement> slidingModalContainers;
			
			 @FindBy(how = How.CSS, using = "div[class *='batch-header-right flexbox-row flex-end v-center'] input[type='button'][class^='btn'][value='New Batch']")
			    public List<WebElement> NewBatchButtonCss;
			 
			//public WebElement NewBatchButton = driver.findElement(By.cssSelector(".NewBatchButton"));
			 
			 @FindBy(how = How.CSS, using = "div[class *='dashboard-right']")
			    public List<WebElement> BatchMaintenanceDashboardRight;
			
			@FindBy(how = How.CSS, using = "div[class ='batch-header-Left']")
			public WebElement BatchHeaderLeft;
			
						
    @FindBy(how = How.CSS, using = "div[class^='pfs-financial-admin'][ng-controller='FinancialManagerCtrl']")
    public WebElement FinancialManagerCtrl;
    
    @FindBy(how = How.CSS, using = "div[class^='pfs-batch-maintenace'][ng-controller='BatchMaintenanceCtrl']")
    public WebElement BatchMaintenanceCtrl;
    
    @FindBy(how = How.CSS, using = "div[class='sidebar-header-block'] h1[class='sidebar-header']")
    		public WebElement EDIManager;
    
    @FindBy(how = How.CSS, using = "div[class *= 'patient-header-container']") // MGB 2/27/2015
	public WebElement openEDIManagerTab;
    
    @FindBy(how = How.CSS, using = "ul[class*='info-list-tabs'] li")
    public List<WebElement> EDIManagerTabs;
    
   // @FindBy(how = How.CSS, using = "ul[class^='info-list'][ng-click ^='ediManager']")
   // public WebElement Calendar;
    
    @FindBy(how = How.CSS, using = "section[class^='EDIManager'][data-ng-controller='ediCalendarCtrl']")
    public WebElement ediClaimManagerCtrl;
    
    @FindBy(how = How.CSS, using = "section[class^='EDIManager'][data-ng-controller='ediClaimManagerCtrl']")
    public WebElement ediEraManagerCtrl;
    
    @FindBy(how = How.CSS, using = "section[class^='EDIManager'][data-ng-controller='ediEraManagerCtrl']")
    public WebElement ediStatementManagerCtrl;
    
      
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
 	   return GeneralMethods.checkIsDisplayed(FinancialManager);
    }
    
    public boolean clickNewBatchButton() throws IOException, InterruptedException {
        return super.clickElement((WebElement) NewBatchButtonCss, "New Batch Button");
    }
    
    public boolean clickTab(String tabName, String tabDescription) throws IOException, InterruptedException {
    	try{
			for (WebElement tab : FinancialManagerTabs)
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
    	
    	public boolean clickButton(String buttonName, String buttonDescription) throws IOException, InterruptedException {
    	    	try{
    				for (WebElement button : NewBatchButtonCss)
    					if (buttonName.equals(button.getText())){
    						super.clickElement(button, buttonDescription);
    						GeneralMethods.delay(EVENTDELAY);
    						return true;
    					}
    				return false;
    			} catch (Exception e){
    				return false;
    			}   
    	}
    	    	
    	    public WebElement getVisibleSlidingModal() throws IOException, InterruptedException {
    	    	        try{
    	    	        	for(WebElement modal : slidingModalContainers)
    	    	        		if (modal.isDisplayed()) return modal;
    	    	        	return null;
    	    	        } catch (Exception e){
    	    	        	return null;
    	    	        }
    	    	    
    }

			public WebElement BatchMaintenanceCtrl() {
				return BatchHeaderLeft;
				// TODO Auto-generated method stub
				
			}





}
