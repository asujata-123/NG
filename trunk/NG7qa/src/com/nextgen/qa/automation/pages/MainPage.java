/**
 * @author mbodoh
 *
 */

package com.nextgen.qa.automation.pages;

import com.nextgen.qa.automation.toolbox.Artifact;
import com.nextgen.qa.automation.toolbox.AutomationSettings;
import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.toolbox.MouseMethods;
import com.nextgen.qa.automation.toolbox.NG7TestCase;
import com.nextgen.qa.automation.ui.GenericPage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

// trunk
public class MainPage extends GenericPage {

	/**
	 * 
	 */
	public MainPage(WebDriver aDriver, String aLOG_FILE) {	
		   super(aDriver, aLOG_FILE);
		   this.driver = aDriver;
	       this.LOG_FILE = aLOG_FILE;
		}

	public int EVENT_DELAY = AutomationSettings.getEventDelay();
	
	// Web Elements
	@FindBy(how = How.CSS, using = "div[class*='homepage-grid']")
    public WebElement homePage;
	
	@FindBy(how = How.CSS, using = "div[class^='welcome-container'] div[class*='hexagon-container']")
	public WebElement welcomeLayout;
	
	//@FindBy(how = How.CSS, using = "div[class^='hexagon-container'] div[class^='ngxSelect'][sel-return-value*='newLayoutId']")
	@FindBy(how = How.CSS, using = "div[class^='hexagon-container'] a[data-ng-click*='clickDropdown']")
    public WebElement welcomeLayoutPulldown;
	
	@FindBy(how = How.CSS, using = "div[class^='hexagon-container'] div[class^='ngxSelect'][sel-return-value*='newLayoutId'] li")
	public List<WebElement> welcomeLayoutPulldownList;
	
	@FindBy(how = How.CSS, using = "span[class^='selectListWrapper'] li")
	public List<WebElement> welcomeLayoutPulldownList2;
	
	@FindBy(how = How.CSS, using = "div[class^='upload-content'] div[class^='welcome-header-light']")
    public WebElement welcomeImagepage;
	
	@FindBy(how = How.CSS, using = "div[class^='upload-content'] div[class='welcome-header']")
    public WebElement welcomeImagepage2;
	
	@FindBy(how = How.CSS, using = "div[class^='welcome-content'] div[class^='welcome-header-light']")
    public WebElement welcomeImagepage3;
	
	@FindBy(how = How.CSS, using = "div[class^='welcome-content'] div[class='welcome-tagline']")
    public WebElement welcomeImagepage4;
	
	@FindBy(how = How.CSS, using = "div[class^='welcome-button']")
    public WebElement welcomeLayoutDone;
	
	@FindBy(how = How.CSS, using = "input[type='text'][id ='globalSearchBox']") // MGB 6/18/2014 update to handle menu in the search field
    public WebElement homeMenu;
    
	@FindBy(how = How.CSS, using = "div[id='globalSearchMenus'] ul[id^='pickList'] li[class^='pickListRow']") // MGB 7/28/2014
    public List<WebElement> homeMenuDropdownList;
    
    @FindBy(how = How.ID, using = "span[id='home-icon']")
    public WebElement homeIcon;
    
    @FindBy(how = How.CSS, using = "span[id='current-location']")
    public WebElement locationStatus;

    @FindBy(how = How.CSS, using = "div[id*='current-locationDropdown'] div[class^='text-ellipsis']") // MGB 4/23/2014
    //@FindBy(how = How.CSS, using = "div[id='current-locationDropDown'] li[data-ng-repeat^='node']") // MGB 12/30/2014
    //@FindBy(how = How.CSS, using = "div[id='current-locationDropDown'] li") // MGB 3/5/2014
    public List<WebElement> locationStatusDropdownList; 
    
    @FindBy(how = How.CSS, using = "div[id='current-locationDropdown']") // MGB 12/30/2014
    public WebElement locationStatusDropdownListContainer;
    
    //@FindBy(how = How.CSS, using = "div[id='current-locationDropDown'] div[class^='text-ellipsis'] ~ ul") // MGB 6/26/2014
    @FindBy(how = How.CSS, using = "div[id='current-locationDropdown'] li[data-ng-repeat^='node'] ul") // MGB 12/30/2014
    public List<WebElement> locationNodesList; 
    
    @FindBy(how = How.CSS, using = "span[id='loggedInUser'][class^='navLink']")
    public WebElement loggedUserStatus;
    
    @FindBy(how = How.CSS, using = "div[id='loggedInUserDropdown'][class^='navDropDown'] li")
    public List<WebElement> loggedUserStatusDropdownList;
    
    @FindBy(how = How.CSS, using = "div[class^='systemStatus']")
    public WebElement statusIcon;
    
    //@FindBy(how = How.CSS, using = "span[class='location-user-status'] div[class^='tooltip']")
    @FindBy(how = How.CSS, using = "body span[class ^= 'ngx-tooltip']") // MGB 10/17/2014
    public WebElement statusIconToolTip;
    
    @FindBy(how = How.CSS, using = "input[type='text'][id ='globalSearchBox']")
    public WebElement globalSearchField;
    public String globalSearchFieldCss = "input[id ='globalSearchBox']";
    
    @FindBy(how = How.CSS, using = "div[class^='detailText']")
    public WebElement resultDetail;
    public WebElement primaryActionButton = null;
    public String primaryActionButtonCss = "ul[class^='primary-action-button']";
    public WebElement arrowDown = null;
    public String arrowDownCss = "div[class^='arrow-down']";
    
    @FindBy(how = How.CSS, using = "div[class*='action-required-alerts']")
    public WebElement alertsDialog;
    
    @FindBy(how = How.CSS, using = "div[id^='contextIndicator']")
    public WebElement searchContextIndicator;
    
    @FindBy(how = How.CSS, using = "div[id^='contextIndicator'] span[class*='delete']")
    public WebElement searchContextIndicatorDelete;
    
    // MENU ITEMS
    public String menuRegistration = "Registration/Check In";
    public String menuRegistrationDash = "Registration/Check-In";
    public String menuScheduler = "Scheduler";
    public String menuFinancial = "Financial Manager";
    public String menuSmartFiles = "SmartFiles";
    public String menuEDIManager = "EDI Manager";
    public String menuGFS = "GFS";
    public String menuFormDesigner = "Form Designer";
    public String menuComponentDesigner = "Component Designer";
    public String menuCPM = "CPM Monitor";
    public String menuHelp = "Help";
    
    //// GLOBAL SEARCH
    //@FindBy(how = How.CSS, using = "div[id='globalSearchMenus'] div[id='searchResults'] div[class ^= 'resultRow']")
    //@FindBy(how = How.CSS, using = "div[id='globalSearchMenus'] div[class ^= 'result-row']")
    @FindBy(how = How.CSS, using = "ul[id='globalSearchResults'] div[ng-repeat^='searchRowData']") // MGB 12/18/2014
    public List<WebElement> searchResults;
    
    // MGB 3/11/2015
    @FindBy(how = How.CSS, using = "ul[id='globalSearchResults'] li a[class*='new-registration-link']")
    public WebElement searchResultsContainerNewRegistrationLink;
     
    //@FindBy(how = How.CSS, using = "ul[id='globalSearchResults'] li[id='menu_mpi'] div[ng-repeat^='searchRowData']") // MGB 12/18/2014
    @FindBy(how = How.CSS, using = "ul[id='globalSearchResults'] li[id*='menu_people'] div[ng-repeat^='searchRowData']") // MGB 3/5/2015
    public List<WebElement> searchResultsPatientList;
    
    @FindBy(how = How.CSS, using = "ul[id='globalSearchResults'] li[id='menu_medication'] div[ng-repeat^='searchRowData']") // MGB 12/18/2014
    public List<WebElement> searchResultsMedicationList;
    
    @FindBy(how = How.CSS, using = "ul[id='globalSearchResults'] li[id='menu_report'] div[ng-repeat^='searchRowData']") // MGB 12/18/2014
    public List<WebElement> searchResultsReportList;
    
    // MGB 3/5/2015: added allergy menu list
    @FindBy(how = How.CSS, using = "ul[id='globalSearchResults'] li[id='menu_allergy'] div[ng-repeat^='searchRowData']")
    public List<WebElement> searchResultsAllergyList;
    
    //@FindBy(how = How.CSS, using = "div[id='more-like-this'][class^='suggestions'] li")
    @FindBy(how = How.CSS, using = "div[id='more-like-this'] ul[class^='suggestions'] li")
    public List<WebElement> searchResultsSuggestedList;
    ////
    
    @FindBy(how = How.CSS, using = "li[class='logout']")
    public WebElement logoutOption; 
    
    @FindBy(how = How.CSS, using = "div[class^='navbar-tab patient'] span[class='navbar-tab-close']")
    public List<WebElement> tabCloseButtons;
    
    @FindBy(how = How.CSS, using = "span[id='loggedInUser'][class^='navLink']") //MGB 7/2/2014
    public WebElement userStatus;
    
    //@FindBy(how = How.CSS, using = "div[id='userStatusDropDown'][class^='navDropDown'] li")
    @FindBy(how = How.CSS, using = "div[id*='UserDropDown'][class^='navDropDown'] li") // MGB 10/17/2014
    public List<WebElement> userStatusList;
    
    @FindBy(how = How.CSS, using = "div[class='copyright-info']")
    public WebElement copyrightInfo;
     
    //layout
    @FindBy(how = How.CSS, using = "div[class^='ngxSelect'][sel-url-value-field='LayoutId'] a[class*='customSelect']")
    public WebElement layoutSelectField;
    
    @FindBy(how = How.CSS, using = "div[class^='ngxSelect'][sel-url-value-field='LayoutId'] li")
    public List<WebElement> layoutSelectList;  
    
    @FindBy(how = How.CSS, using = "div[class^='lockScreenBg']")
    public WebElement userLoginLockPopup;
    
    @FindBy(how = How.CSS, using = "div[class^='scheduling-notifications-widget']")
    public WebElement schedulingNotificationsWidget;
    
    //@FindBy(how = How.CSS, using = "input[type='button'][class^='btn'][value='Scheduler']")
    //public WebElement schedulerButton = getSchedulerWidgetButton();
    /*
    @FindBy(how = How.CSS, using = "div[class='icon_refresh']")
    public WebElement refreshIcon;
    */
    @FindBy(how = How.CSS, using = "div[class^='ngxSelect'][sel-url-value-field='resourceId'] a") // MGB 7/21/2014
    public WebElement resourceSelectField;
    
    //@FindBy(how = How.CSS, using = "div[class^='ngxSelect'][sel-url-value-field='ResourceId'] li") // MGB 7/21/2014
    @FindBy(how = How.CSS, using = "span[class^='selectListWrapper'] li") // MGB 11/10/2014
    public List<WebElement> resourceSelectFieldList;
    ////

    @FindBy(how = How.CSS, using = "div[class^='navbar-tab patient']")
    public List<WebElement> openTabs;
    
    //Ron 04/06/2015 added
    @FindBy(how = How.CSS, using = "div[class^='navbar-tab-patient-name ng-binding']")
    public WebElement tabPatientName;
    
    //Ron 04/14/2015 added
    @FindBy(how = How.CSS, using = "div[class^='pfs-financial-admin ng-scope']")
    public WebElement financialManagerCtrl;
    
    //actions
    
    public boolean clickHome() throws IOException, InterruptedException {
        return super.clickElement(homeIcon, "Home Button");
    }    

    public boolean clickHomeMenu() throws IOException, InterruptedException {
        return super.clickElement(homeMenu, "Home Menu");     
    }
    
    public boolean clickUserStatus () throws IOException, InterruptedException {
    	return super.clickElement(userStatus, "User Status button");
    }
    
    public boolean clickLoggedUserStatus () throws IOException, InterruptedException {
    	return super.clickElement(loggedUserStatus, "User Status button");
    }
    
    public boolean clickLocationStatus() throws IOException, InterruptedException {
    	return super.clickElement(locationStatus, "Location Status pulldown");     
    }
    public boolean clickScheduler() throws IOException, InterruptedException {
        return super.selectDropdownItem(homeMenuDropdownList, "Scheduler menu option", "Scheduler");
    }    
    
    public boolean clickSmartFiles() throws IOException, InterruptedException {
        return super.selectDropdownItem(homeMenuDropdownList, "SmartFiles menu option", "SmartFiles");
    }

    public boolean selectLayout(String layoutName) throws IOException, InterruptedException {
        return super.selectDropdownItem(layoutSelectList, "Layout Select List", layoutName);
    }
    
    public boolean performGlobalSearch(String searchString)throws IOException, InterruptedException {
    	try{
    		boolean success = super.clickElement(globalSearchField, "Global Search field");
    		if (!GeneralMethods.checkElementIsNotVisible(this.driver, this.searchContextIndicatorDelete)) 
    			this.clickElement(this.searchContextIndicatorDelete, "search context delete button");
    		globalSearchField.clear();
    		globalSearchField.sendKeys(searchString);
    		GeneralMethods.delay(1000);
    		return true;
    	} catch (Exception e) { return false; }
    } 
    
    public boolean performGlobalSearchSlash(String searchString)throws IOException, InterruptedException {
    	try{
    		globalSearchField.clear();
    		int xOffset = globalSearchField.getSize().getWidth() + 20;
    		MouseMethods.HoverAwaySendKeys(this.driver, globalSearchField, '/'+searchString, xOffset, 0);
    		GeneralMethods.delay(1000);
    		return true;
    	} catch (Exception e) { return false; }
    }
    
    public boolean selectSearchResult_old(String string, String category, boolean handleAlerts) throws Exception {
    	try{
    		driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
    		System.out.println("Select "+string+" from category "+category);
    		List<WebElement> list;
    		if (category.equals("patient")) list = this.searchResultsPatientList;
    		else if (category.equals("medication")) list = this.searchResultsMedicationList;
    		else if (category.equals("report")) list = this.searchResultsReportList;
    		else if (category.equals("suggested")) list = this.searchResultsSuggestedList;
    		else list = this.searchResults;
    		System.out.println("Number of "+category+" search results: " +list.size());
    		String resultLC;
    		String stringLC;
    		for (WebElement result : list){
    			stringLC = string.toLowerCase();
    			resultLC = result.getText().toLowerCase();
    			System.out.println("stringLC="+stringLC+" resultLC="+resultLC);
    			if (resultLC.contains(stringLC)){
    				GeneralMethods.delay(1000);
    				this.clickElement(result, "search result "+ stringLC);
    				if ((!category.equals("suggested")) && (handleAlerts)) this.handleAlertsPopup();
    				driver.manage().timeouts().implicitlyWait(NG7TestCase.defaultTimeout, TimeUnit.MILLISECONDS);
    				return true;
    			}
    		}
    		// Hover away from the global search field if the result is not found
    		super.hoverAwayAndClick(this.homeMenu);
    		driver.manage().timeouts().implicitlyWait(NG7TestCase.defaultTimeout, TimeUnit.MILLISECONDS);
    		return false;
    	} catch (Exception e) { 
    		System.out.println("selectSearchResult: Exception thrown");
    		driver.manage().timeouts().implicitlyWait(NG7TestCase.defaultTimeout, TimeUnit.MILLISECONDS);
    		return false; }
	}

    public boolean selectSearchResult(String string, String category, boolean handleAlerts) throws Exception {
    	try{
    		System.out.println("Select "+string+" from category "+category);
    		WebElement result = null;
    		GeneralMethods.delay(1000);
    		result = findSearchResult(string, category);
    		if (result != null){
    		   this.clickElement(result, "search result "+ string);
    		   if ((!category.equals("suggested")) && (handleAlerts)) this.handleAlertsPopup();
    		   driver.manage().timeouts().implicitlyWait(NG7TestCase.defaultTimeout, TimeUnit.MILLISECONDS);
    		   return true;
    		}
    		// Hover away from the global search field if the result is not found
    		super.hoverAwayAndClick(this.homeMenu);
    		return false;
    	} catch (Exception e) { 
    		System.out.println("selectSearchResult: Exception thrown");
    		driver.manage().timeouts().implicitlyWait(NG7TestCase.defaultTimeout, TimeUnit.MILLISECONDS);
    		return false; }
	}
    
    public WebElement findSearchResult(String string, String category) throws Exception {
    	try{
    		GeneralMethods.setTimeout(0);
    		System.out.println("Find "+string+" from category "+category);
    		List<WebElement> list;
    		if (category.equals("patient")) list = this.searchResultsPatientList;
    		else if (category.equals("report")) list = this.searchResultsReportList;
    		else if (category.equals("suggested")) list = this.searchResultsSuggestedList;
    		else if (category.equals("medication")) list = this.searchResultsMedicationList;
    		else if (category.equals("allergy")) list = this.searchResultsAllergyList; 		
    		else list = this.searchResults;
    		System.out.println("Number of "+category+" search results: " +list.size());
    		String resultLC;
    		String stringLC;
    		WebElement searchResult = null;
    		for (WebElement result : list){
    			stringLC = string.toLowerCase();
    			resultLC = result.getText().toLowerCase();
    			if (resultLC.contains(stringLC)){
    				searchResult = result;
    				break;
    			}
    		}
    		if (searchResult == null) System.out.println("Could not find search result matching " +string +" category " +category);
    		GeneralMethods.resetTimeout();
    		return searchResult;
    	} catch (Exception e) { 
    		System.out.println("Exception thrown while attempting to find search result "+string+" in category "+category);
    		return null; 
    	}
	}
    
    public boolean selectSearchResultPatient(String string) throws Exception {
    	try{
    		System.out.println("string is "+string);
    		for (WebElement result : searchResultsPatientList){
    			if (result.getText().contains(string)){
    				result.click();
    				GeneralMethods.delay(3000);
    				statusIcon.click(); // 5/12/2014 MGB: added this line to minimize search results
    				return true;
    			}
    			System.out.println("result is "+result.getText());
    		}
    		return false;
    	} catch (Exception e) { return false; }
	}
    
    public boolean logOutUser() throws IOException, InterruptedException {
    	super.clickElement(loggedUserStatus, "Logged User Status button");
    	GeneralMethods.delay(AutomationSettings.getEventDelay());
    	return super.clickElement(logoutOption, "Log out option");
    }
    
    public boolean selectHomeMenuItem_registration() throws IOException, InterruptedException {
    	try {
    		boolean success = this.selectHomeMenuItem(this.menuRegistrationDash);
    		return success;
    	} catch (Exception e) {
    		System.out.println("Excetion from selecthomeMenuItem_registration "+e.getMessage());
    		return false;
    	}
    }
    
    // MGB 2/27/2015: added missing try/catch blocks
    public boolean selectHomeMenuItem(String item) throws IOException, InterruptedException {
    	try{
    	   GeneralMethods.timerSample(0);
    	   GeneralMethods.setTimeout(0);
    	   if (GeneralMethods.checkElementIsNotVisible(driver, this.homeMenuDropdownList.get(0))) this.clickHomeMenu();
    	   GeneralMethods.timerSample(1);
    	   this.homeMenu.clear(); // MGB 6/18/2014 clear in order for menu to pop open
    	   this.clickHomeMenu();
    	   boolean success = super.selectDropdownItemContains(homeMenuDropdownList, "Home menu option "+item, item);
    	   GeneralMethods.resetTimeout();
    	   return success;
    	} catch (Exception e) {
    		System.out.println("selectHomeMenuItem: exception found " +e.getMessage());
    		return false;
    	}
    }
    
    public boolean closeAllOpenTabs() throws Exception {
    	try{
    		if (GeneralMethods.checkIsNotEmpty(tabCloseButtons))
    			for (WebElement closeButton : tabCloseButtons) 
    				super.clickElement(closeButton, "Tab Close button");
    			return true;
    	} catch (Exception e) {
    		return false;
    	}
	}
	
    public boolean closeOpenTab(String tabName) throws IOException, InterruptedException {
    	try{
    		for (WebElement tab : this.openTabs){
    			if(tab.getText().equals(tabName)){
    				WebElement closeButton = GeneralMethods.FindElementInObjHierarchy(tab, "span[class='navbar-tab-close']");
    				return GeneralMethods.ClickButton(closeButton);
    			}
    		}
    		System.out.println("Could not find tab of name "+tabName);
    		return false;
    	} catch (Exception e) { return false; }
	}
    
    public WebElement getOpenTab(String tabName) throws IOException, InterruptedException {
    	try{
    		for (WebElement tab : openTabs) 
    			if (tab.getText().contains(tabName)) return tab;
    		return null;
    	} catch (Exception e) { return null; }
	}
    
    public boolean searchAndSelect(String searchString) throws IOException, InterruptedException {
    	try{
    		String category = null;
    		if (this.performGlobalSearch(searchString)) return this.selectSearchResult(searchString,category,true);
    		else return false;
    	} catch (Exception e) { return false; }
    }
    
    public String findLocationString(String locationNode) throws IOException{
    	try{
    		WebElement tree = this.locationStatus;
    		super.clickElement(this.locationStatus, "Location field");
    		String locationString = "";
    		for (WebElement node : this.locationStatusDropdownList)
    			System.out.println("cssInfo" + node.getCssValue("Test"));
    		return "Test";
    			
    			
    	} catch ( Exception e ) {
    		System.out.println("Could not find location node " + locationNode);
    		return "";
    	}
    }
    
    public boolean setLocation(String location) throws IOException{
    	try {
    		boolean success = false;
    		String[] locations = location.split(";");
    		System.out.println("locations size " + locations.length);
    		if (this.locationStatus.getText().equals(locations[locations.length-1])) success = true;
    		else {
    			String topNode = this.getTopNodeText();
    			location = topNode +";"+location;
    			
    			super.clickAndSelectDropdown(this.locationStatus, this.locationStatusDropdownList, "location dropdown", topNode);
    			if (!GeneralMethods.checkElementIsNotVisible(driver, this.welcomeLayout)) this.handleWelcome(AutomationSettings.getTestDataItem("layout_"+NG7TestCase.build));
    			super.clickElement(this.locationStatus, "Location field");
    			
    			for (int i=0; i < locations.length; i++) 
    				if (i == locations.length-1) success = super.selectDropdownItem(this.locationStatusDropdownList, "Location dropdown", locations[i]); //if ((locations.length >= 2) && (i <= locations.length -2)) super.expandDropdownItem(this.locationStatusDropdownList, locations[i], "i[ng-click^='toggleOpen']");
    				else super.expandDropdownItem(this.locationStatusDropdownList, this.locationNodesList, locations[i], "i[ng-click^='toggleOpen']");
    			if (!GeneralMethods.checkElementIsNotVisible(driver, this.welcomeLayout)) this.handleWelcome("Staff Layout");
    			success = this.locationStatus.getText().trim().equals(locations[locations.length-1].trim()) ? true : false;
    		}
    		if (success == false) super.clickElement(this.locationStatus, "Location field");
    		return success;
    	} catch (Exception e) { return false; }
    }
    
    public String getBuildNumber() throws InterruptedException {
    	try{
    		String info = this.displayStatusTooltip();
    		String [] infoArry = info.split("Version");
    		infoArry[1] = infoArry[1].replace(" - ", "");
    		return infoArry[1]; 
    	} catch (Exception e) { 
    		return ""; 
    	}
    }
    
    public String getElapsedTime() throws InterruptedException {
    	try{
    		String info = this.displayStatusTooltip();
    		String [] infoArry = info.split("Version");
    		infoArry[0] = infoArry[0].replace("Status: Connected | Duration: ", "");
    		return infoArry[0]; 
    	} catch (Exception e) { 
    		return ""; 
    	}
    }
    
    public String displayStatusTooltip() throws InterruptedException {
    	try{
    		String info = "";
    		boolean success = true;
    		if (MouseMethods.HoverOver(this.driver, this.statusIcon, 1, 1))
    			if (! this.statusIconToolTip.isDisplayed()) success = false;
    			else 
    				info = this.statusIconToolTip.getText();
    		//info = StringUtils.chomp(info); // MGB 10/27/2014
    		info = info.replace("\n", ""); // MGB 10/27/2014
    		return info; 
    	} catch (Exception e) { return ""; }
    }
    
    public String checkLoginSucceeded() throws Exception {
    	String success = "00";
    	
    	if (! GeneralMethods.checkIsDisplayed(this.homeMenu)) 
    		success += "01";
    	
    	this.checkErrorPage();
    		
    	return success;
    }
    
    public boolean cleanupForTest() throws IOException, InterruptedException
    {
    	//this.closeAllOpenTabs();
    	return true;
    }
    
	/**
	 * @param args
	 */
	public void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@SuppressWarnings("null")
	//New layout changes - subha
	public boolean handleWelcome(String layout) throws IOException{
		boolean success = true;
		try{
			GeneralMethods.delay(1000);
	     	if (!GeneralMethods.checkElementIsNotVisible(this.driver, this.welcomeLayout)){	
	     		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
	     		success &= super.clickAndSelectDropdown(this.welcomeLayoutPulldown, this.welcomeLayoutPulldownList2, "Layout selector", layout) ? true :
					super.clickAndSelectDropdown(this.welcomeLayoutPulldown, this.welcomeLayoutPulldownList, "Layout selector", layout) ? true : false;
				success &= super.clickElement(welcomeLayoutDone, "Layout select - done");
				// two more selection once for the image and one for Welcomes page
				//verify the image page
				
				success &= super.clickElement(welcomeLayoutDone, "Layout select - done");
				
				// verify the  welcome text
				GeneralMethods.delay(NG7TestCase.eventDelay);
				success &= super.clickElement(welcomeLayoutDone, "Layout select - done");
				System.out.println("Layout selected : "+success);
				driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.MILLISECONDS);
			}
			//GeneralMethods.delay(NG7TestCase.eventDelay*2);
			//return success;
	     	if (!success){
	     		System.out.println("WARNING: Could not select layout "+layout);
	     		Artifact.ReportDoNotDeliverFail("Warning", "Could not select layout "+layout);
	     	}
	     	return true;
		} catch (Exception e) { return false; }
	}
	
	public boolean verifyTitleLayout(WebElement element1,WebElement element2, String string1, String string2) throws IOException {
		boolean success = true;
		try {
		String ImageValue = super.getText(element1, "Welcome Image");
		String ImageValue1 = super.getText(element2, "Welcome Image2");
		if (ImageValue.equalsIgnoreCase(string1) && ImageValue1.equalsIgnoreCase(string2))
		{
		success = true ;	
		}
		else 
		{
			success = false;
		}
		System.out.println(" the boolean"+success);
		return success;
		}
		catch (Exception e) { return false;}
	}
    // end of Layout Changes
	
	// MGB 09/16/2014
	public boolean handleAlertsPopup() throws Exception {
		try{
		   driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
		   if (this.checkElementIsVisible(alertsDialog, "select search result alerts dialog")) 
			   super.clickElement(super.findVisibleButton(alertsDialog, "Continue", ""), "Alerts dialog Continue button");
		   driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.MILLISECONDS);
		   return true;
		} catch (Exception e) {
			System.out.println("Exception thrown on handleAlertsPopup " + e.getMessage());
			return false;
		}
	}
	////
	
	public WebElement getSchedulerWidgetButton() throws Exception{
		return this.findVisibleWidget(this.homePage, "Scheduler", "Scheduler widget button");
	}
	
	public String getTopNodeText() throws Exception{
		try {
			NG7TestCase.driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
			String nodeName = this.locationStatusDropdownList.get(0).getText();
			
			if (nodeName.equals("")){
			   this.clickLocationStatus();
			   nodeName = this.locationStatusDropdownList.get(0).getText();
			  }
				
			this.clickLocationStatus();
			MouseMethods.HoverOverClick(NG7TestCase.driver, this.locationStatus, -10, 0); // MGB 1/21/2015 workaround because list doest close when status field is clicked
			NG7TestCase.driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.MILLISECONDS);
			return nodeName;
		} catch (Exception e){
			System.out.println("Exception thrown on getTopNodeText() "+ e.getMessage());
			return "";
		}
	}
	
	public boolean checkSearchContextIndicatorIsVisible(String context) throws Exception{
		try {
			WebElement indicator = GeneralMethods.WaitForElement(this.driver, NG7TestCase.waitForElementDelay, "search context indicator", this.searchContextIndicator);
			String contextLC = context.toLowerCase();
			if (indicator != null && this.searchContextIndicator.getText().toLowerCase().contains(contextLC)) 
				return true;
			else
				return false;
		} catch (Exception e) {
			System.out.println("Exception thrown at checkSearchContextIndicatorIsVisible "+e.getMessage());
			return false;
		}
	}
	
////Ron new function 04/02/2015
	public boolean checkTabOrderPosition(String tabName, int tabOrder) throws Exception{
		try 
		{
			int i=0;
			//System.out.println("***** before checkTabOrderPosition "  );
			//System.out.println("***** tabName is " +tabName + " ,  tabOrder is " +tabOrder );
			for (WebElement tab : openTabs) 
			{
				i++;
				//System.out.println("***** tab.getText " +i + " is " + tab.getText() + " for tabOrder " +tabOrder );
				if (tab.getText().contains(tabName) && tabOrder==i ) 
        			{
        			//System.out.println("***** #inside the if#    for loop number " +i  ) ;
        			return true;
        			}
        		else
        			{
        			//System.out.println("***** #inside the else#    for loop number " +i  ) ;
        			}
			}
			//System.out.println("***** end of checkTabOrderPosition ") ;

		} catch (Exception e) {
			System.out.println("Exception thrown at checkTabOrderPosition "+e.getMessage());
		}
		return false;
	}
	
	////
	


}
