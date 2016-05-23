package com.nextgen.qa.automation.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nextgen.qa.automation.toolbox.*;
import com.nextgen.qa.automation.ui.*;

public class PatientDashboardPage extends GenericPage {
			 
		/**
		 * 
		 */	
		public PatientDashboardPage(WebDriver aDriver, String aLOG_FILE) {	
		   super(aDriver, aLOG_FILE);
		   this.driver = aDriver;
	       this.LOG_FILE = aLOG_FILE;
		}
		
		// Web Elements - Patient dashboard
		public String patientDashboardCssData = "div[class='patient-dashboard']";
		@FindBy(how = How.CSS, using = "article[class*='patient-dashboard']")
		public WebElement openPatientDashboard;
		
		//@FindBy(how = How.CSS, using = "div[id='patient-tab'][class ^= 'navbar-tab patient']")
		@FindBy(how = How.CSS, using = "div[class *= 'patient-header-container']") // MGB 2/27/2015
		public WebElement openPatientTab;
		
		//Sujata
		@FindBy(how = How.CSS, using =  "div[id='Insurance-tab'][class ^= 'navbar-tab Insurance']")
		public WebElement openInsuranceTab;
		    
		@FindBy(how = How.CSS, using = "div[id='patient-information'][class='patient-header-info'] span[data-ng-bind^='module.moduleData.patientHeader'")
		public List<WebElement> patientTabHeaderInfo;
			
		@FindBy(how = How.CSS, using = "span[class*='tab-patient-name']") // MGB 7/17/2014
		public WebElement patientName;
		
		//@FindBy(how = How.CSS, using = "div[class^='patient-photo-container']")
		@FindBy(how = How.CSS, using = "div[class*='flipperContainer']") // MGB 8/4/2014
		public WebElement flipperContainer;
		
		@FindBy(how = How.CSS, using = "div[class^='inner-financial-module']")
		public WebElement innerFinancialModule;
		
		@FindBy(how = How.CSS, using = "div[class^='patient-photo-container']")
		public WebElement patientPhotoContainer;
		
		@FindBy(how = How.CSS, using = "div[class^='patient-photo-container'] img[class^='patient-photo']")
		public WebElement patientPhoto;
		
		//@FindBy(how = How.CSS, using = "div[class ^= 'summary_icon_down']")
		@FindBy(how = How.CSS, using = "input[type='button'][value='old patient page']")
		public WebElement summaryViewButton;
		
		//@FindBy(how = How.CSS, using = "div[class ^= 'timeline_icon_down']")
		//@FindBy(how = How.CSS, using = "div[class='header-block-item  timeline-cursor']")
		//@FindBy(how = How.CSS, using = "article[class^='flexbox-column patient-dashboard'] div[class^='header-block-item'][ngx-show-timeline]")
		@FindBy(how = How.CSS, using = "article[class^='flexbox-column patient-dashboard'] div[class^='header-block-item'][id='timeLineHeader']")
		public WebElement timelineViewButton;
		
		// MGB 9/8/2014 Experimental
		public String buttonAddActivity = "launchAddActivity";
		//public String buttonAddActivityCss = "div[class^='add-activity-section-patient']";
		//public String buttonAddActivityCss = "div[class^='add-activity']";
		public String buttonAddActivityCss = "div img[class*='add-activity']"; // MGB 2/27/2015
		public String buttonGetTodaysVisit = "getTodaysVisit";
		public String tabHealthfeed = "healthfeed";
		public String tabActivities = "activities";
		public String tabPatientSummary = "patient summary";//Subha 04/09/2015
		public String tabTimeline = "timeline"; // MGB 9/8/2014 will eventually replace timelineViewButton
		
		////
	    
	    //@FindBy(how = How.CSS, using = "div[class='timeline isotope']")
		@FindBy(how = How.CSS, using = "div[class^='timeline-body']")
	    public WebElement timeline;
	    
	    @FindBy(how = How.CSS, using = "div.line-container-line") 
	    public WebElement timelineYearListBar;
	    
	    @FindBy(how = How.CSS, using = "div.bdate")
	    public List<WebElement> timelineEntriesList;
	    
	    //@FindBy(how = How.CSS, using = "div[class^='timeline-filter']") 
	    @FindBy(how = How.CSS, using = "div[class*='timeline-filter']") // MGB 7/17/2014
	    //@FindBy(how = How.CSS, using = "div[class='timeline-filter timeline-nav-type']")
	    public WebElement timelineFilterGroup;
	    
	    //@FindBy(how = How.CSS, using = "div[class^='timeline-menu-wrapper']")
	    //public WebElement timelineFilterWrapper;
	    
	    //@FindBy(how = How.CSS, using = "div[class^='timeline-menu-wrapper'] img[alt='Menu']")
	    @FindBy(how = How.CSS, using = "div[class^='hamburger-menu-wrapper']") // MGB 7/17/2014 
	    public WebElement dashboardMenuButton; // MGB 7/17/2014 : renamed this from timelineFilterMenu to enhance scope of functionality of this button
	    
	    //@FindBy(how = How.CSS, using = "div[class^='timeline-filter'] li[ng-repeat='category in timeline.categories']")
	    //@FindBy(how = How.CSS, using = "div[class^='timeline-menu-wrapper'] li[ng-repeat='category in timeline.categories']")
	    //@FindBy(how = How.CSS, using = "div[class^='hamburger-menu-wrapper'] li[ng-repeat='category in timeline.categories']") // MGB 7/17/2014
	    @FindBy(how = How.CSS, using = "div[class^='hamburger-menu-wrapper'] li[ng-repeat*='category in timeline.categories']") // MGB 10/7/2014
	    public List<WebElement> dashboardMenuList; // MGB 7/17/2014 : renamed this from timelineFiltersList to enhance scope of functionality of this button
	    
	    @FindBy(how = How.CSS, using = "div[class^='hamburger-menu-wrapper'] div[class^='flex-it'][id='timelinemenu']") 
	    public WebElement timelineMenu; 
	    
	    //@FindBy(how = How.CSS, using = "div[class^='timeline-filter'] li[ng-repeat='category in timeline.categories'][ng-class*='active']")
	    @FindBy(how = How.CSS, using = "div[class*='timeline-filter'] li[ng-class*='active']") // MGB 7/17/2014
	    public List<WebElement> selectedTimelineFiltersList;
	    
	    @FindBy(how = How.CSS, using = "form[name='activityForm']")
	    public WebElement addActivityForm;
	    
	    @FindBy(how = How.CSS, using = "div[class^='ngxTypeAheadPg'][name='activity']")
	    public WebElement activityTypePulldown;
	    
	    @FindBy(how = How.CSS, using = "div[class^='selectListWrapper'] li")
	    public List<WebElement> activityTypePulldownList;
	    
	    // Web Elements - PFS Charges tab
	        
	    //@FindBy(how = How.CSS, using = "div[class='dashboard-tabs'] li")
	    @FindBy(how = How.CSS, using = "ul[class*='info-list-tabs'] li")
	    public List<WebElement> dashboardTabs;
	    
	    @FindBy(how = How.CSS, using = "ul[class*='info-list-buttons'] li")
	    public List<WebElement> dashboardButtons;
	    
	    //@FindBy(how = How.CSS, using = "div[class^='patient-dash-financial']")
	    public static String financialGridCssData = "article[class6='flexbox-column patient-dashboard'] div[class^='patient-dash-financial-grid ']";
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class^='patient-dash-financial-grid']")
	    public WebElement financialGrid;
	    
	    //sujata  - 
	    
	   // <article class="flexbox-column patient-dashboard full-height ng-scope" data-ng-controller="TimelineCtrl">
	    
	    public static String InsuranceTabCssData = "article[class6='flexbox-column patient-dashboard'] div[class^='patient-dash-tab-insurance']";
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class^='patient-dash-tab-insurance']")
	    public WebElement insuranceGrid;
	    
	   // public static String ClaimsCssData = "div[class^='slidingModal active-sliding-modal']";
	  // @FindBy(how = How.CSS, using = "div[class*='slidingModal active-sliding-modal'] div[class^=''flexbox-column patient-create-claim ng-scope']";
	    public WebElement DemandBillingGrid;
	   
	    // <div class="slidingModal active-sliding-modal" ng-class="{'active-sliding-modal': findModuleScope.modalGo[0]}">
	    
	  //  public static String ClaimsCssData1 = "div[class^='flexbox-column patient-create-claim ng-scope']";
	  //  @FindBy(how = How.CSS, using = "div[class*='patient-create-claim']"
	    		//    public WebElement DemandBillingGrid;
	    
	    //Sujata
		   //public String linkClaimsCss = "a href[ng-click*='+ Claims]']";
	    
	    //public WebElement linkClaimsCss = "a href[contains(text(+ Claims)),'Searching')]";
	    
	  // public WebElement linkClaimsCss = driver.findElement(By.xpath("//span*[contains(text(),'Create Claim')]"));
	   // public WebElement linkClaimsCss = driver.findElement(By.xpath("//span*[contains(class^='ng-isolate-scope')]"));
	    
	    @FindBy(how = How.CSS, using = "div[class*='header-block-links-right'] span[class='ng-isolate-scope]']")
	    public String linkClaimsCss;
	    //Sujata 5/20/2015
	   // @FindBy(how = How.CSS, using = "div[class*='ngx-checkbox insurance-checkbox'] span[class='ng-isolate-scope]']")
	   // public List<WebElement> HeaderBlockLinksRightButtons; 
	    
	    @FindBy(how = How.CSS, using = "div[class*='header-block-links-right'] span[class='ng-isolate-scope]']")
	    public List<WebElement> HeaderBlockLinksRightButtons;
	    
	   // @FindBy(how = How.CSS, using = "div[class*='header-block-links-right'] span[ng-click^='findModuleScope.newItemFormTemplate[0]']")
	    //public String buttonCreateClaimCss;
	    
	 // @FindBy(how = How.CSS, using = "div[class*='header-block-links-right'] span[ ngx-secure=secure-url=""")
	   // public String buttonCreateClaimCss;
	    
	 @FindBy(how = How.CSS, using = "div[class*='header-block-links-right'] span[class='ng-isolate-scope']")
	   public String buttonCreateClaimCss;
	    
	  // var x = document.getElementsByTagName("BUTTON")[0].innertext;
	   //document.("Create Claim").innerHTML = x; 
	    
	 // public List<WebElement> linkClaimsCss = driver.findElements(By.linkText(buttonCreateClaimCss));
	    
	   // public List<WebElement> linkClaimCss = driver.findElements(By.linkText(buttonCreateClaimCss));
	    
	   // public List<WebElement> linkClaimCss = driver.FindElement(By.linkText(buttonCreateClaimCss)).Click();
	    
	   // driver.findElement(By.xpath("//a[contains(@href, ng-click="findModuleScope.newItemFormTemplate[0] = 'views/core/patient-create-claim.html';" ng-open-sliding-modal="" class="flex-it margin-top-auto margin-bottom-auto margin-left-auto ng-isolate-scope" ngx-secure="" secure-url="/nav/edi/claims" secure-permission="create" secure-action="hide" style="text-align: right;" ng-class="{'create-claim-link-disabled': (!selectedVisit.visitPersonPlans || selectedVisit.visitPersonPlans.length == 0) || (!selectedVisit.charges || selectedVisit.charges.length == 0) || (selectedVisit.isFinancialStatusOnHold) }">+ Claims</a>)]"));
	    
	   // public WebElement linkClaimsCss = driver.findElement(By.xpath("//div[@class='patient-dash-info-box']//a[@href='/<a title="+ Claims" href="/accounting.html">accounting</a>.html'][i[@class='icon-usd']]/following-sibling::h4"
	    
	    //public List<WebElement> linkClaimsCss = driver.findElements(By.xpath("//h4/a[contains(text(),+ Claims)]"));
	    
	    // public WebElement linkClaimsCss = driver.findElement(By.xpath("//div[@class='patient-dash-info-box']//a[@href='/<a title="+ Claims" href="/accounting.html">accounting</a>.html'][i[@class='icon-usd']]/following-sibling::h4"
	    
	   // public List<WebElement> linkClaimsCss = driver.findElements(By.xpath("//parent::div//div[@class='flexbox-row']/h2[contains(text(),'Claims')]/following-sibling::a[ng-click='views/core/patient-create-claim.html']"));
	    
	    //ng-click="findModuleScope.newItemFormTemplate[0] = 'views/core/patient-create-claim.html';"
	    
	    //public List<WebElement> linkClaimsCss = driver.findElements(By.xpath("//div[@class='patient-dash-info-box']//a[@href='/<a title="+ Claims]);"));
	    
	    		//public List<WebElement> ClaimsCssData = driver.findElements(By.xpath("//div[@class='patient-dash-info-box']/ancestor::div[@class='flexbox-row']/following-sibling::h2[@class='flex-it-3 margin-top-auto margin-bottom-auto']/contains::a[ng-click^='+ Claims]']"));	
	    	//public WebElement linkClaimsCss = driver.findElement(By.xpath("(//*[@class='patient-dash-info-box'] [innerText: "+ Claims]")));
	    
	   // @FindBy(how = How.CSS, using = "div[class='class ='patient-dash-info-box-top''] a[ng-click='findModuleScope.newItemFormTemplate[0]']")
		// public WebElement linkClaimsCss;
		   
		 // public WebElement linkClaimsCss= driver.findElement(By.linkText("+ Claims"));
		    
		   // public String linkClaimsCss = "a[ng-class='create-claim-link-disabled']";
		 //public String linkClaimsCss = "a[ng-className='Claims']";
		//public String linkClaimsCss = "div[class ='patient-dash-info-box-top']";
		 
		//@FindBy(how = How.CSS, using = "div[class^='patient-dash-info-box-top'] a[ng-class='create-claim-link-disabled']")
		    
		 @FindBy(how = How.CSS, using = "div[class='visit-person-plan']div[class='ng-scope']div[class='claims-bar'] div[class='patient-dash-info-resp-view'] span[class='kilo'] ahref[ng-click^='getUrl']")
		 public WebElement claimsrespviewCss;
		    //sujata 5/8/2015
		  // public String buttonCreateClaimCss = "span[ng-click*='CreateClaim']";
		 
		 //@FindBy(how = How.CSS, using = (By.xpath("//div[@class='visit-person-plan']"));
		   
		  // sujata	   5/11/2015
		   
		  // @FindBy(how = How.CSS, using = "div[class *='batch-header-right flexbox-row flex-end v-center']" div[class='flexbox-row'] div[class ]
		    
		    		//public String buttonCreateClaim = "a href[ng-click*='findModuleScope.newItemFormTemplate[0] = 'views/core/patient-create-claim.html']";
	    
	  //public WebElement linkClaimsCss = driver.findElement(By.xpath("(//*[@class='patient-dash-info-box'] a[ng-open-sliding-modal][1]")); visible element not found
	   
	 //public WebElement linkClaimsCss = driver.findElement(By.xpath("(//*[@class='patient-dash-info-box'] a[contains(.,'ng-open-sliding-modal')][1])")).click();
	    
	    //public WebElement linkClaimsCss = driver.findElement(By.className("flex-it margin-top-auto margin-bottom-auto margin-left-auto ng-isolate-scope"));
	    
	   //public WebElement linkClaimsCss =  (WebElement) (driver).executeScript("return $('.patient-dash-info-box-top.patient-dash-info-box').find('a[ng-open-sliding-modal]')[1]");
	 
	    
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class^='patient-dash-financial-grid-right']")
	    public List<WebElement> financialGridRight;
	    
	    //@FindBy(how = How.CSS, using = "div[class^='patient-dash-financial'] div[class='top-heading-links'] a")
	    //@FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class^='patient-dash-financial-grid'] div[class='top-heading-links'] a")
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class^='patient-dash-financial-grid']  a[ng-click^='getUrl']") // MGB 7/24/2014
	    public List<WebElement> financialTabLinks;
	    //Sujata
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class^='patient-dash-Insurance-grid']  a[ng-click^='getUrl']") // MGB 7/24/2014
	    public List<WebElement> CheckEligibility; //sujata
	    
	    public String linkChargesCss = "a[ng-click='onChargeClicked()']";
	    public String linkTransactionsCss = "a[ng-click='onTransactionClicked()']";
	    //sujata
	    //public String buttonCheckEligibility = "[ng-click='checkEligibility(false)']";
	    public String buttonCheckEligibilityCss = "span[ng-click*='checkEligibility']"; // MGB 3/6/2015 added tag, added Css to the name, and made argument value more generic
	    //Sujata 5/20/2015
	   //public String SelectAllCheckbox= "input type[ng-click*='selectAllInsurances()']";
	    
	    //public String buttonCheckEligi = "input[type='button'][class='btn btn-small btn-primary'][ng-click*='checkEligibility()']";
	    
	    public String buttonCheckEligiCss = "input[ng-click*='checkEligibility()']";
	    
	   // <input [type='button'] [class='btn btn-small btn-primary'][value='Check'] ng-click="checkEligibility()"
	    		
	    		  @FindBy(how = How.CSS, using  = "div[class*='modalFooter'] input[type = 'button'][class='btn btn-small btn-primary'][ng-click='checkEligibility']")
		 
	    public List<WebElement> modalFooterButtons;
	   
	  // public String SelectAllCheckbox = "input [class^='edi-checkbox']";
	   //Sujata
	   @FindBy(how = How.CSS, using = "input[class^='edi-checkbox']")
	    public WebElement 	SelectAllCheckbox;
	   
	   @FindBy(how = How.CSS, using = "input[ng-click='gridRowClicked(plan)']")
	    public WebElement 	SelectplanCheckbox;
	   	   	    
	   // public String SelectAllCss = "div[class*='ngx-checkbox insurance-checkbox '] input type[ng-click*='selectAllInsurances(gridCheckbox.selectAll)']";
	    
	   
	   // public String linkClaimsCss = "a[ng-click='onClaimsClicked()']";
	    
	    
	    //Sujata 5/24/2015
	    
	    @FindBy(how = How.CSS, using  = "div[class*='ngx-checkbox insurance-checkbox'] input[type = 'checkbox'][class^='edi-checkbox']")
		 
	    public WebElement SelectAll;
	    
	    //Sujata 5/22/2015
	    @FindBy(how = How.CSS, using = "div[class*='check-eligibility-modal ']")
		public WebElement checkeligibilitymodal ;
	    
	    @FindBy(how = How.CSS, using = "ng-include[class = 'modalTemplate ng-scope']")
	    public static WebElement checkEligibilitySlidingModal;
	    
	    //Sujata 5/26/2015
	    
	    @FindBy(how = How.CSS, using = "div[class='slidingModal active-sliding-modal']")
		public WebElement activeeligibilitymodal ;
	    
	    @FindBy(how = How.CSS, using = "div[class*='check-eligibility-modal '] div[class*='modalContent ']")
		public WebElement  modalContentCheckboxes ;
	    
	    
	    @FindBy(how = How.CSS, using = "div[class='ngx-checkbox insurance-checkbox ']")
	    
	    public WebElement InsuranceCheckBox;
	   
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class^='patient-dash-financial-grid'] select[ng-model $= 'DateType']")
	    public WebElement dateTypeSelector;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class^='patient-dash-financial-grid'] input[type='button'][class^='btn']")
	    public List<WebElement> financialGridButtons;
	   //Sujata     
	   @FindBy(how = How.CSS, using = "article[class*='Insurance Tab'] div[class^='Insurance Tab''] input[type='button'][class^='btn']")
	    public List<WebElement> InsuranceTabButton;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class^='patient-dash-financial-grid'] div[class='icon_transfer_monies']")
	    public WebElement transferMoniesIcon;
	    
	    //@FindBy(how = How.CSS, using = "article[class='flexbox-column patient-dashboard'] input[ng-model='patient.financialSearchVisitNumber']")
	    //@FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] input[ng-model='patient.financialSearchVisitNumber']")
	    //@FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class^='patient-dash-financial-grid'] div[class*='ngxTypeAhead'][data-ta-input-name='VisitId']")
	    @FindBy(how = How.CSS, using = "div[class^='single-input'][data-ta-value-field='VisitId']") // MGB 1/5/2015
	    public WebElement visitIDPulldown;		

	    //@FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class^='patient-dash-financial-grid'] div[class*='ngxTypeAhead'][data-ta-input-name='VisitId'] li")
	    @FindBy(how = How.CSS, using = "div[class ^= 'selectListWrapper'] li")
	    public List<WebElement> visitIDPulldownList;
	    
	    //@FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class ^= 'patient-dash-totals-amount']")
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class ^= 'patient-dash-totals-amount']")
	    public WebElement visitTotalField;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] table[class ^= 'patient-dash-financial-grid-table'][ng-class *= 'charge']")
	    public WebElement chargeTable;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class ^= 'patient-dash-totals-amount']")
	    public WebElement chargeField;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] input[type='text'][name='ServiceBeginDate']")
	    public WebElement beginDateField;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] input[type='text'][name='ServiceEndDate']")
	    public WebElement endDateField;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] input[type='text'][ng-model='chargeProcedure.ProcedureCode']")
	    public WebElement chargeProcedureCodeField;		
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[ng-repeat ^= 'chargeProcedureModifier']")
	    public List<WebElement> chargeProcedureModifierGroupList;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] input[type='text'][ng-model='chargeProcedure.Units']")
	    public WebElement chargeUnitsField;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] input[type='text'][ng-model='charge.RevenueCode']")
	    public WebElement chargeRevenueCodeField;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] input[type='text'][ng-model='charge.Amount']")
	    public WebElement chargeAmountField;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class='note-container'] i") // div[ng-class*='narrative'] i[class='icon-note'] 
	    public WebElement narrativeIcon;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] div[class='meat'] textarea[class^='commentaryInput']") 
	    public WebElement narrativeTextField;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] input[type='button'][class^='btn'][value='Save']")
	    public WebElement chargeSaveButton;
	    
	    @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] input[type='button'][class^='btn'][value='Cancel']")
	    public WebElement chargeCancelButton;
	    
	 //   @FindBy(how = How.CSS, using = "article[class*='patient-dashboard'] input[type='button'][class^='btn'][value='Post']")
	  //
	    
	    @FindBy(how = How.CSS, using = "li[data-ng-repeat^='editableVisit in editableVisits']")
	    public List<WebElement> visitsList;
	    
	    // WebElements - Active visit 
	 	@FindBy(how = How.CSS, using = "aside[class*='visit-left-panel']")
	 	public WebElement openActiveVisit;
	 	
	 	@FindBy(how = How.CSS, using = "div[class*='visit-activity-area'] div[ng-repeat*='activity in activities']")
	 	public List<WebElement> activityCardsList;
	 	
	 	@FindBy(how = How.CSS, using = "aside[class*='activity-bar']")
	 	public WebElement activityBar;
	 	
	 	//@FindBy(how = How.CSS, using = "aside[class*='activity-bar'] input[class*='ngxTypeAheadPgInput']")
	 	@FindBy(how = How.CSS, using = "input[placeholder='Concept Search']")
	 	public WebElement activityBarTypeAheadField;
	 	
	 	//@FindBy(how = How.CSS, using = "li[data-ng-repeat^='taItem in taList']")
	 	@FindBy(how = How.CSS, using = "li[ng-repeat ^= 'item']")
	 	public List<WebElement> activitySearchList;
	 	
	 	// Experimental
	 	public String buttonTodaysVisit = "div[ng-click *= 'TodaysVisit']";
	 	//Sujata
		//public String checkEligibility = "div[ng-click *= 'Check Eligibility']";
		public WebElement InsuranceTab;
		//<a ng-click="setEditSection('DiagnosisCodes');" ngx-secure="" secure-url="/nav/patient/:patientId/patientVisit/:visitId" secure-permission="Update" secure-action="hide" class="text-color-beta cursor-pointer ng-isolate-scope">Edit</a>
		// Add Diagnosis 
		//
		//"div[class='cornerButtonParent']
		@FindBy(how = How.CSS, using =  "div [class='cornerButtonParent'] a[ng-click *='DiagnosisCodes']")
		//@FindBy(how = How.CSS, using = "a [ng-click *= 'setEditSection']") setEditSection('VisitInfo');
	 	public WebElement editDiagnosis;
	    
		@FindBy(how = How.CSS, using = "a[ng-click *='launchDiagnosisCodesAttachWizard()']")
		public WebElement attachDiagnosis;
		
		@FindBy(how = How.CSS, using = "input[placeholder='Search DiagnosisCodes']")
		public WebElement diagnosis;
		
		@FindBy(how = How.CSS, using = "input[value='Attach']")
		public WebElement attachDiagnosisButton;
		
		 public String buttonCheckEligibility = "[ng-click='checkEligibility(false)']"; 
		
		@FindBy(how = How.CSS, using = "div[class='modalFooter'] input[value='Done']")
		public WebElement attachDiagnosisDone;
		
		@FindBy(how = How.CSS, using = "div [ng-show *='DiagnosisCodes'] input[value='Save']")
		public WebElement diagnosisSave;
		
		@FindBy(how = How.CSS, using = "input[value='Unattach']")
		public WebElement unattachDiagnosisButton;
		
	 	////
	 	
	    // methods
	    
	    public boolean clickTab(String tabName, String tabDescription) throws IOException, InterruptedException {
	    	try{
				for (WebElement tab : dashboardTabs)
					if (tabName.equals(tab.getText())){
						super.clickElement(tab, tabDescription);
						GeneralMethods.delay(EVENTDELAY);
						return true;
					}
				return false;
			} catch (Exception e){
				return false;
			}   
	   // }
	    
	    //public WebElement clickButton(String ButtonName, List<WebElement> ButtonList, String ButtonDescription) throws IOException, InterruptedException {
	    //	try{
				//for (WebElement Button : dashboardButtons)
					//if (ButtonName.equals(Button.getText())){
						//super.clickElement(Button, ButtonDescription);
						//GeneralMethods.delay(EVENTDELAY);
						//return Button;
				//	}
				//return null;
			//} catch (Exception e){
			//	return null;
			}   
	   // }
	    
	   // public WebElement getInsuranceTabButton(String ButtonName, List<WebElement> ButtonList, String ButtonDescription) throws IOException, InterruptedException {
	    	//try{
			//	for (WebElement Button : ButtonList)
				//	if (ButtonName.equals(Button.getText())){
						//super.clickElement(Button, ButtonDescription);
						//GeneralMethods.delay(EVENTDELAY);
					//	return Button;
			//return null;
				//	}
				//return null;
			//} catch (Exception e){
			//	return null;
			//}   
	  //  }
	    
	   // public WebElement url = '/nav/patient/' + patientId + '/patientInsurance/PersonPlanId/' + data.PersonPlanId + '/checkInsuranceEligibility'
	    
	    //Sujata
	    
	    public WebElement getInsuranceTabButton(String buttonName, String buttonDescription) throws IOException, InterruptedException {
	    	try{
				for (WebElement button : InsuranceTabButton)
					
					if (button.getAttribute("value").contains(buttonName))
						return button;
				return null;
	    		
			} catch (Exception e){
				return null;
			}   
	    }
	    //Sujata 5/26/2015
	    public WebElement getmodalFooterButtons(String buttonName, String buttonDescription) throws IOException, InterruptedException {
	    	try{
				for (WebElement button : modalFooterButtons)
					
					if (button.getAttribute("value").contains(buttonName))
						return button;
				return null;
	    		
			} catch (Exception e){
				return null;
			}   
	    }
	    //Sujata 5/19/2015

	    //Sujata 5/19/2015
	    
	    public WebElement getHeaderBlockLinksRightButtons(String buttonName, String buttonDescription) throws IOException, InterruptedException {
	    	try{
				for (WebElement button : HeaderBlockLinksRightButtons)
					
					if (button.getAttribute("value").contains(buttonName))
						return button;
				return null;
	    		
			} catch (Exception e){
				return null;
			}   
	    }
	    
	    
	    public WebElement getFinancialTabLink(String linkName, List<WebElement> linkList, String linkDescription) throws IOException, InterruptedException {
	    	try{
				for (WebElement link : linkList)
					if (linkName.equals(link.getText()))
						return link;
				return null;
			} catch (Exception e){
				return null;
			}   
	    }
	    
	    public WebElement getFinancialGridButton(String buttonName, String buttonDescription) throws IOException, InterruptedException {
	    	try{
				for (WebElement button : financialGridButtons)
					if (button.getAttribute("value").contains(buttonName))
						return button;
				return null;
	    		
			} catch (Exception e){
				return null;
			}   
	    }
	    
	    //Sujata 5/26/2015
	    
	    
	    
	    
	   
	    
	    
	    
	    public boolean clickFinancialGridButton(String buttonName) throws IOException, InterruptedException {
	    	WebElement button = this.getFinancialGridButton(buttonName, buttonName);
	    	return super.clickElement(button, buttonName);
	    }
	    
	    //Sujata 5/26/2015
	    
	    public boolean clickInsuranceCheckBox() throws IOException, InterruptedException {
	        return super.clickElement(InsuranceCheckBox, "Click SelectAll Check Box");
	    }
	    
	    public boolean clickSelectAllCheckBox() throws IOException, InterruptedException {
	        return super.clickElement(SelectAll, "Click SelectAll Check Box");
	    }
	    
	   // public boolean clicInsuranceCheckBox(String CheckBoxName) throws IOException, InterruptedException {
	    	//WebElement CheckBox = this.getInsuranceCheckBox(CheckBoxName, CheckBoxName);
	    	//return super.clickElement(CheckBox, CheckBoxName);
	    
	    
	    public boolean clickSelectAll() throws IOException, InterruptedException {
	 	 	 	   return GeneralMethods.ClickButton(SelectAllCheckbox);
	    }
	    
	    
	    
	    public boolean clickSelectplanCheckbox() throws IOException, InterruptedException {
		 	  		 	   return GeneralMethods.ClickButton(SelectplanCheckbox);
		    }
	    
	   
	    public boolean clickCheckButton() throws IOException, InterruptedException {
	        return super.clickButton(modalFooterButtons);     
	    }
	    
	    public WebElement getFinancialGridIcon(String iconName, List<WebElement> iconList, String iconDescription) throws IOException, InterruptedException {
	    	try{
				for (WebElement icon : iconList)
					if (iconName.equals(icon.getText()))
						return icon;
				return null;
			} catch (Exception e){
				return null;
			}   
	    }
	    
	    public boolean findSelectedTimelineFilter(String filterName) throws IOException, InterruptedException {
	    	try{
	    		for (WebElement selectedFilter : this.selectedTimelineFiltersList)
	    			if (selectedFilter.getText().contains(filterName)) return true;
	    		return false;
	    	} catch (Exception e) { return false; }
	    }
	    
	    public boolean selectActivity (String activityName){
	    	try{
	    		boolean success = false;
	    		String name = activityName.toLowerCase();
	    		for (WebElement card : this.activityCardsList)
	    			if (card.getText().toLowerCase().contains(name)){
	    				success = super.clickElement(card, "Activity card "+activityName);
	    				GeneralMethods.delay(NG7TestCase.eventDelay);
	    				return true;
	    			}
	    		System.out.println("selectActivity: could not find activity card "+activityName);
	    		return false;
	    	} catch (Exception e) { 
	    		System.out.println("selectActivity: exception thrown when attempting to click activity "+activityName);
	    		return false; }
	    }
	    
	    public boolean clickAddActivity() throws Exception{
	    	return this.clickElement(this.findVisibleElement(this.openPatientDashboard, this.buttonAddActivityCss), "Add New Activity");
	    }
	    
	    public boolean addActivityCard (String activityName){
	    	try{
	    		boolean success = false;
	    		String name = activityName.toLowerCase();
	    		this.clickAddActivity();
	    		if (this.addActivityForm != null){
	    			this.clickElement(this.activityTypePulldown, "activity type pulldown");
	    			success = this.selectDropdownItem(this.activityTypePulldownList, "activity type pulldown list", activityName);
	    		}
	    		return success;
	    	} catch (Exception e) { 
	    		System.out.println("selectActivity: exception thrown when attempting to click activity "+activityName);
	    		return false; }
	    }
	    
	    public boolean checkPatientPhotoVisible(){
	    	try{
	    		String style = this.patientPhotoContainer.getAttribute("style");
	    		System.out.println("visibility style :" + style);
	    		if (style.contains("visible")) 
	    			return true;
	    		else 
	    			return false;
	    	} catch (Exception e){
	    		System.out.print("checkPatientPhotoVisible: exception thrown when checking for patient photo visibility: "+e.getMessage());
	    		return false;}
	    	}
	   //Sujata
		public boolean clickButton(String checkInsuranceEligibility2, String buttonDescription) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			try{
				for (WebElement Button : InsuranceTabButton)
					if (checkInsuranceEligibility2.equals(Button.getText())){
						super.clickElement(Button, buttonDescription);
						GeneralMethods.delay(EVENTDELAY);
						return true;
					}
				return false;
			} catch (Exception e){
			return false;}
		}
//Sujata 5/27/2015
		
		public boolean clickButton2(String Check, String buttonDescription) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			try{
				for (WebElement Button : modalFooterButtons)
					if (modalFooterButtons.equals(Button.getText())){
						super.clickElement(Button, buttonDescription);
						GeneralMethods.delay(EVENTDELAY);
						return true;
					}
				return false;
			} catch (Exception e){
			return false;}
		}
		//sujata 5/19/2015
		
		public boolean clickButton1(String CreateClaim, String buttonDescription) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			try{
				for (WebElement Button : HeaderBlockLinksRightButtons)
					if (CreateClaim.equals(Button.getText())){
						super.clickElement(Button, buttonDescription);
						GeneralMethods.delay(EVENTDELAY);
						return true;
					}
				return false;
			} catch (Exception e){
			return false;}
		}
		
		
		
		public boolean ClicklinkClaimsCss(String string, String string2) {
			// TODO Auto-generated method stub
			return false;
		}

		public WebElement findVisibleElement(WebElement openPatientDashboard2,
				WebElement linkClaimsCss2) {
			// TODO Auto-generated method stub
			return null;
		}
		



//Sujata 5/22/2015

//actions     
/*public WebElement getVisibleModal() throws IOException, InterruptedException {
    try{
    	for(WebElement container : checkEligibilitySlidingModal)
    		if (container.isDisplayed()) return container;
    	return null;
    } catch (Exception e){
    	return null;
    }
}
		public boolean ClickSlidingModalButton (String buttonName) throws IOException, InterruptedException  {
			try{
				WebElement mod = this.getVisibleSlidingModal();
				WebElement button = GeneralMethods.FindElementInObjHierarchy(mod, buttonCssData+"[value='"+buttonName+"']");
				return GeneralMethods.ClickButton(button);
			} catch (Exception e) { return false; }
		}*/
	}
		
		
		
		

