/**
 * @author mbodoh
 *
 */

package com.nextgen.qa.automation.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.toolbox.MouseMethods;
import com.nextgen.qa.automation.toolbox.NG7TestCase;
import com.nextgen.qa.automation.ui.GenericPage;

/**
 * 
 */

/**
 * @author mbodoh
 *
 */
public class NewAppointmentPage extends GenericPage { 

	 
	/**
	 * 
	 */	
	public NewAppointmentPage(WebDriver aDriver, String aLOG_FILE) {	
	   super(aDriver, aLOG_FILE);
	   this.driver = aDriver;
       this.LOG_FILE = aLOG_FILE;
	}
	
	public RegistrationPage rp = new RegistrationPage(driver, "registrationNAP.txt");
	
	// Web Elements - New Appointment wizard
	@FindBy(how = How.CSS, using = "div[id ^='registrationModal_module']")
	public WebElement newAppointmentWizard; // = rp.registrationWizard;

	
	public WebElement modalContent = rp.modalContent;
	public WebElement closeButton = rp.closeButton;
	
	@FindBy(how = How.CSS, using = "input[type='button'][value='Save']") // MGB 7/19/2014
	public WebElement saveButton; // = rp.saveButton;
	 
	@FindBy(how = How.CSS, using = "input[type='button'][value='Cancel']") // MGB 7/19/2014
	public WebElement cancelButton; // = rp.cancelButton;
	public String cancelButtonCss = "input[type='button'][value='Cancel']";
	
	@FindBy(how = How.CSS, using = "input[type='button'][value='Check In']")// MGB 7/19/2014
	public WebElement checkinButton; // = rp.checkinButton;
	
	@FindBy(how = How.CSS, using = "button[class$='appointmentContinue']")// MGB 7/19/2014
	public WebElement continueButton; // = rp.continueButton;
	
	public List<WebElement> wizardTabs = rp.wizardTabs;
	
	public WebElement selectedWizardTab = rp.selectedWizardTab;	
	
	// Web Elements - Appointment tab
	@FindBy(how = How.CSS, using = "input[name^='appointmentStartDate'][class*='hasDatepicker']") // MGB 12/18/2014
    public WebElement apptDateField;
    
    //@FindBy(how = How.CSS, using = "div[class^='chosen-container']") // MGB 7/31/2014
    @FindBy(how = How.CSS, using = "ngx-type-ahead-multiple[ta-placeholder='Select Resources']") // MGB 12/18/2014
    public WebElement apptResourceField;

    //public String resourceFieldCss = "div[class^='chosen-container']"; // MGB 7/31/2014
	public String resourceFieldCss = "ngx-type-ahead-multiple[ta-placeholder='Select Resources']"; // MGB 12/3/2014
    
    @FindBy(how = How.CSS, using = "div[class ^= 'chosenList'] li") // MGB 12/15/2014
    public List<WebElement> apptResourceFieldList;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][name='appointmentType']")
    public WebElement apptTypeField;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][name='appointmentType'] option")
    public List<WebElement> apptTypeFieldList;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][name='appointmentDuration']")
    public WebElement apptDurationField;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][name='appointmentDuration'] option")
    public List<WebElement> apptDurationFieldList;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][name='appointmentStartDateTimeTime']")
    public WebElement apptTimeField;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][name='appointmentStartDateTimeTime'] option")
    public List<WebElement> apptTimeFieldList;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][name='appointmentStartDateTimeTime'] option[selected='selected']")
    public WebElement apptTimeSelected;
    
    @FindBy(how = How.CSS, using = "select[class *= 'customSelect'][name='appointmentServiceLocation']")
    public WebElement apptLocationField;
    
    @FindBy(how = How.CSS, using = "select[class *= 'customSelect'][name='appointmentServiceLocation'] option")
    public List<WebElement> apptLocationFieldList;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][name='appointmentReferringProviderId']")
    public WebElement apptReferringProvider;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][name='appointmentReferringProviderId'] option")
    public List<WebElement> apptReferringProviderList;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][name='appointmentRenderingProviderId']")
    public WebElement apptRenderingProvider;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][name='appointmentRenderingProviderId'] option")
    public List<WebElement> apptRenderingProviderList;
    
    @FindBy(how = How.CSS, using = "input[type='text'][name='appointmentDescription']")
    public WebElement apptDescription;
    
    @FindBy(how = How.CSS, using = "textarea[type='text'][name='appointmentNote']")
    public WebElement apptNote;
    
    @FindBy(how = How.CSS, using = "div[class='conflict-details-label'] span[ng-click^='appointmentMeta.showConflicts = true']")
    public WebElement viewConflictsButton;
    
    @FindBy(how = How.CSS, using = "div[class='conflict-details-label'] span[ng-click^='appointmentMeta.showConflicts = false']")
    public WebElement hideConflictsButton;
    
    @FindBy(how = How.CSS, using = "div[class='info-and-controls-right'] input[type='button'][class^='btn'][value*='Ignore']")
    public WebElement ignoreConflictsButton;
    
    // Web Elements - Demographics/Patient tab
    @FindBy(how = How.CSS, using = "div[class='global-search registration'] input")
    public WebElement searchField;
    public String searchFieldCss = "div[class='global-search registration'] input";
    
    @FindBy(how = How.CSS, using = "div[id^='personSearchResults'][class=^'regSearchResultsNew']")
    public WebElement searchResultsPane;
    public String searchResultsPaneCss = "";
    
    @FindBy(how = How.CSS, using = "div[class='resultrow']")//-MGB 5/28/2014
    public List<WebElement> searchResults;
    
    //@FindBy(how = How.CSS, using = "button[class ^= 'btn']")//-MGB 5/28/2014
    public WebElement attachButton = rp.attachButton; 
    
    //@FindBy(how = How.CSS, using = "input[ng-click *= 'toggleNoPatient']")
    @FindBy(how = How.CSS, using = "input[name='noPatient']")
    public WebElement noPatientOption;
    
    // Web Elements - Search Ahead slide-out
    @FindBy(how = How.CSS, using = "div[class *= 'regIdTab'] div img[ng-click*='scheduler']") // MGB 3/3/2015
    public WebElement searchAheadTab;
    
    @FindBy(how = How.CSS, using = "div[class^='search-ahead'] select[class='customSelect'] option[value='between']")
    public WebElement searchAheadTimeBetweenSelector;
    
    @FindBy(how = How.CSS, using = "div[class^='search-ahead']")
    public WebElement searchAheadPanel;
    
    @FindBy(how = How.CSS, using = "div[class^='search-ahead'] section[class^=search-container'] article")
    public WebElement searchAheadModalContent;
    
    @FindBy(how = How.CSS, using = "select[class^='customSelect'][name='appointmentSearchAheadStartDateTimeTime']")
    public WebElement searchAheadStartTimeField;
    
    @FindBy(how = How.CSS, using = "select[class^='customSelect'][ng-model='appointmentSearchAhead.startDateTimeTime'][name='appointmentSearchAheadStartDateTimeTime'] option")
    public List<WebElement> searchAheadStartTimeFieldList;
    
    @FindBy(how = How.CSS, using = "select[class^='customSelect'][ng-model='appointmentSearchAhead.selectedEndTime'][name='appointmentSearchAheadselectedEndTime']")
    public WebElement searchAheadEndTimeField;
    
    @FindBy(how = How.CSS, using = "select[class^='customSelect'][name='appointmentSearchAheadselectedEndTime'] option")
    public List<WebElement> searchAheadEndTimeFieldList;
    
    @FindBy(how = How.CSS, using = "input[data-ng-model*='appointmentSearchAhead.startDateTimeDate'][class*='hasDatepicker']")
    public WebElement searchAheadStartDateField;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][ng-model*='appointmentSearchAhead.duration']")
    public WebElement searchAheadDurationField;

    @FindBy(how = How.CSS, using = "select[class*='customSelect'][ng-model*='appointmentSearchAhead.duration'] option")
    public List<WebElement> searchAheadDurationFieldList;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect appointment-type-search-ahead']")
    public WebElement searchAheadAppTypeField;

    @FindBy(how = How.CSS, using = "select[class*='customSelect'][ng-model*='appointmentSearchAhead.appointmentId'] option")
    public List<WebElement> searchAheadApptTypeFieldList;
    
    @FindBy(how = How.CSS, using = "ngx-type-ahead-multiple[ta-input-name^='searchAheadResources']") //  div[class^='ngxTypeAheadMultiple']") // MGB 12/17/2014
    public WebElement searchAheadResourceField;

    @FindBy(how = How.CSS, using = "div[class ^= 'chosenList'] li") // MGB 12/15/2014
    public List<WebElement> searchAheadResourceFieldList;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][ng-model*='appointmentSearchAhead.selectedCategories'] ~ div[class^='chosen-container']")
    public WebElement searchAheadCategoryField;

    @FindBy(how = How.CSS, using = "select[class*='customSelect'][ng-model*='appointmentSearchAhead.selectedCategories'] option")
    public List<WebElement> searchAheadCategoryFieldList;
    
    @FindBy(how = How.CSS, using = "ngx-type-ahead-multiple[ta-value-field*='organizationId']")
    public WebElement searchAheadLocationField;
    
    @FindBy(how = How.CSS, using = "div[class ^= 'chosenList'] li") // MGB 12/15/2014
    public List<WebElement> searchAheadLocationFieldList;
    
    @FindBy(how = How.CSS, using = "select[class*='customSelect'][ng-model*='appointmentSearchAhead.resourceCriteria']")
    public WebElement searchAheadResourceCriteria;
    
    @FindBy(how = How.CSS, using = "section[class ^= 'results-container']")
    public WebElement searchAheadResultsContainer;
    
    @FindBy(how = How.CSS, using = "div[ng-click ^= 'model.suggestedDaysView']")
    public WebElement searchAheadFiltersButton;
    
    @FindBy(how = How.CSS, using = "div[class='form-group days-of-the-week'] button[type='button'][class^='btn'][ng-click *='toggleDaysFilter']")
    public List<WebElement> searchAheadDaysGroup;
    
    @FindBy(how = How.CSS, using = "input[class^='btn'][value = 'Suggest']")
    public WebElement searchAheadSuggestButton;
    
    @FindBy(how = How.CSS, using = "div[class $= 'active activated'] li[ng-repeat ^= 'appt in model.searchAhead']")
    public List<WebElement> suggestedDatesList;
    
    // Web Elements - Save Appt Modal
    @FindBy(how = How.CSS, using = "ng-include[class='secondary-modal']")
    public WebElement saveApptModal;
    
    // Experimental
    public String buttonTransportationNeeded = "Is Transportation Needed?";
    ////
    
    // methods
    
    public boolean clickSave() throws IOException, InterruptedException {
        return super.clickElement(saveButton, "New  Appointment Save button");     
    }
    
    public boolean clickCancel() throws Exception {
        return super.clickElement(super.findVisibleButton(newAppointmentWizard, "Cancel", ""), "New  Appointment Cancel button");
    }
    
    public boolean clickContinue() throws IOException, InterruptedException {
        return super.clickElement(continueButton, "New  Appointment Continue button");
    }
    
    // MGB 8/26/2014: changed to use method from RegistrationPage  
    public boolean performPatientSearch(String searchString)throws IOException, InterruptedException {
       return rp.performPatientSearch(searchString);
    } 
    ////
    
    public boolean setApptDate(String date, boolean random) throws IOException, InterruptedException {
    	if (random == true) date = GeneralMethods.GenerateRandomDate(0);
    	return super.setDateField(apptDateField, "Appointment Date", date);    
    }

    public boolean setApptResource(String resource, boolean random) throws IOException, InterruptedException {
    	super.clickElement(apptResourceField, "New Apointment Resource");
    	if (random == true) resource = super.getRandomDropdownItemText(apptResourceFieldList, "Appointment Resource List", "option"); // WebElement dropdownField, String elementName, String dropDownItemCssSelector
        return super.selectDropdownItem(apptResourceFieldList, "Appointment Resource", resource);  
    }
    
    public boolean setApptType(String type, boolean random) throws IOException, InterruptedException {
    	super.clickElementDelay(apptTypeField, "Appointment Type", 20, 5);
    	if (random == true) type = super.getRandomDropdownItemText(apptTypeFieldList, "Appointment Type List", "option"); // WebElement dropdownField, String elementName, String dropDownItemCssSelector
        return super.selectDropdownItem(apptTypeFieldList, "Appointment Type", type);
        //return super.clickElement(apptTypeField, "Appointment Type");
    }
    
    public boolean setApptDuration(String duration, boolean random) throws IOException, InterruptedException {
    	super.clickElementDelay(apptDurationField, "Appointment Duration", 20, 5);
    	if (random == true) duration = super.getRandomDropdownItemText(apptDurationFieldList, "Appointment Duration List", "option"); // WebElement dropdownField, String elementName, String dropDownItemCssSelector
        return super.selectDropdownItem(apptDurationFieldList, "Appointment Duration", duration);
        //return super.clickElement(apptDurationField, "Appointment Duration");
    }

    public boolean setApptTime(String time, boolean random) throws IOException, InterruptedException {
    	super.clickElementDelay(apptTimeField, "Appointment Time", 20, 5);
    	if (random == true) time = super.getRandomDropdownItemText(apptTimeFieldList, "Appointment Time List", "option"); // WebElement dropdownField, String elementName, String dropDownItemCssSelector
        return super.selectDropdownItem(apptTimeFieldList, "Appointment Time", time);  
        //return super.clickElement(apptTimeField, "Appointment Time");
    }
    
    public boolean setApptLocation(String location, boolean random) throws IOException, InterruptedException {
    	super.clickElementDelay(apptLocationField, "Appointment Location", 20, 5);
    	if (random == true) location = super.getRandomDropdownItemText(apptLocationFieldList, "Appointment Location List", "option"); // WebElement dropdownField, String elementName, String dropDownItemCssSelector
        return super.selectDropdownItem(apptLocationFieldList, "Appointment Location", location); 
        //return super.clickElement(apptLocationField, "Appointment Location");
    }

    public boolean setSearchAheadEndTime(String time, boolean random) throws IOException, InterruptedException {
    	//return super.setTextField(searchAheadEndTimeField, "Search Ahead End Time field", time);    
    	super.clickElementDelay(searchAheadEndTimeField, "Search Ahead End Time field", 20, 5);
    	if (random == true) time = super.getRandomDropdownItemText(searchAheadEndTimeFieldList, "Search Ahead End Time field list", "option"); // WebElement dropdownField, String elementName, String dropDownItemCssSelector
        super.selectDropdownItem(searchAheadEndTimeFieldList, "Search Ahead End Time field", time); 
        return super.clickElement(searchAheadEndTimeField, "Search Ahead End Time field");
    }
    
    public boolean setSearchAheadStartDate(String date) throws IOException, InterruptedException {
    	return super.setDateField(searchAheadStartDateField, "Search Ahead Beginning Date field", date);    
    }
    
    public String getApptDate() throws IOException, InterruptedException {
    	return super.getTextFieldValue(apptDateField, "Appointment Date");
    }
    
    public String getApptResource() throws IOException, InterruptedException {
        return super.getChosenFieldValue(apptResourceField, "Appointment Resource");
    }
    
    public String getApptType() throws IOException, InterruptedException {
        //return super.getDropdownFieldValue(apptTypeField, "Appointment Type");
    	return this.apptTypeFieldList.get(1).getText();
    }
    
    public String getApptDuration() throws IOException, InterruptedException {
        //return super.getDropdownFieldValue(apptDurationField, "Appointment Duration");
    	return this.apptDurationFieldList.get(1).getText();
    }
    
    public String getApptTime() throws IOException, InterruptedException {
    	return super.getDropdownFieldValue(apptTimeField, "Appointment Time");
    	//return this.apptTimeSelected.getText();
    }
    
    public String getApptLocation() throws IOException, InterruptedException {
        //return super.getDropdownFieldValue(apptLocationField, "Appointment Location");
    	return this.apptLocationFieldList.get(1).getText();
    }
        
   // MGB 8/26/2014: changed to use method from RegistrationPage  
   // Attach a patient to the new appointment 
   public boolean attachPatient(String patientName) throws IOException, InterruptedException {
	   return rp.attachPatient(patientName);
   }
   ////
   
   public boolean clickNoPatient() throws IOException, InterruptedException {
	   //return super.clickCheckbox(noPatientOption, "No Patient checkbox");
	   return GeneralMethods.ClickButton(noPatientOption);
   }
   
   // Click on the specific wizard tab
   public boolean clickWizardTab(String tabName) throws IOException, InterruptedException {
	  return rp.clickWizardTab(tabName);
   }
   
   // sets and verifies that the middle name was set
   public boolean dateFieldValidate(String date) throws IOException, InterruptedException {	   
	   return super.dateFieldValidate(apptDateField, "Appointment Date", date);
   }
    
   
   
   public boolean wizardExists()throws IOException, InterruptedException {
	   try{
		   WebDriverWait wait = new WebDriverWait(driver, 15);
		 
		   WebElement wizard = wait.until(ExpectedConditions.visibilityOf(newAppointmentWizard));
		   WebElement header = wizard.findElement(By.cssSelector("div[class='topCenter']"));
		 
		   return header.getText().equals("Add Appointment");
	   } catch (Exception e){
		   return false;
	   }
   }
   
   public boolean wizardClosed()throws IOException, InterruptedException {
	   try{
		   WebDriverWait wait = new WebDriverWait(driver, 3);
		   WebElement wizard = wait.until(ExpectedConditions.visibilityOf(newAppointmentWizard));
		   return wizard.isDisplayed() ? false : true;
	   } catch (Exception e){
		   return false;
	   }
   }
   
   public boolean clickSearchAheadTab() throws IOException, InterruptedException {	   
	   super.clickElement(searchAheadTab, "Search Ahead tab");
	   //GeneralMethods.delay(NG7TestCase.eventDelay);
	   return true;
   }     
   
   public boolean enableWeekends() throws IOException, InterruptedException{
	   boolean enabled = true;
	   if (this.searchAheadDaysGroup.get(0).getAttribute("class").contains("selected") == false) 
		      enabled &= GeneralMethods.ClickButton(this.searchAheadDaysGroup.get(0)); // enable Sunday
	   if (this.searchAheadDaysGroup.get(6).getAttribute("class").contains("selected") == false) 
		      enabled &= GeneralMethods.ClickButton(this.searchAheadDaysGroup.get(6)); // enable Saturday
	   return enabled;
   }
   
   public boolean clickSearchAheadSuggestButton() throws IOException, InterruptedException{
	   return super.clickElement(searchAheadSuggestButton, "Search Ahead Suggest button");
   }
   
   public boolean findDateInResult(WebElement result, String dateFormatted){
	    WebElement dateObj = GeneralMethods.FindElementInObjHierarchy(result, "div[class ^= 'time']");
		String date;
		if (dateObj == null) date = "";
		else date = dateObj.getText();
		return date.contains(dateFormatted) ? true : false;
   }
   
   public boolean populatePulldownValue(WebElement field, List<WebElement> fieldList, String description, String value, String optionCss, boolean random) throws IOException, InterruptedException {
	   return super.setPicklistValue(field, fieldList, description, value, optionCss, random);
   }

   // MGB 8/26/2014: changed to use method from RegistrationPage  
   public boolean selectPreferredContactMethod(String index, String method, boolean random) throws Exception {
	   return rp.selectPreferredContactMethod(apptDateField, index, method, random, method);
   }
   ////
   
    //use cases
    public void createApptDoubleClick() throws IOException, InterruptedException{         	    
    
    }
    
    public void createApptNewApptButton() throws IOException, InterruptedException{         	    
        
    }
    
    //use cases
    public boolean createAppointment(boolean noPatient, String fn, String mn, String ln, String gender, String ssn, 
    		String apptDate, String contactType, String contactSubType, String guarantor, String visitDate, String visitTime, String visitLocation) 
    				throws IOException, InterruptedException{         	    
    	try{
    		boolean success;
    		GeneralMethods.delay(1000);
    		if (this.wizardExists()){
    			this.clickWizardTab("General Demographics");
    			if (noPatient) this.clickNoPatient();
    			if (! gender.equals("")) System.out.println("Set gender field: "+ rp.setGender(gender));
    			if (! fn.equals("")) System.out.println("Set first name field: "+ rp.setFirstName(fn));
    			if (! mn.equals("")) System.out.println("Set middle name field: " + rp.setMiddleName(mn));
    			if (! ln.equals("")) System.out.println("Set last name field: "+ rp.setLastName(ln));
    			if (! apptDate.equals("")) {
    				rp.clickDatePicker();
    				System.out.println("Set date of birth: "+ rp.setDatePicker(apptDate));
    			}
    			if (!ssn.equals("")) System.out.println("Set SSN field: "+ rp.setSSN(ssn));
    			
    			if (contactType.equals("")){
    			   this.clickWizardTab("Contact Information");
                   String rowCss = "div[class^='group_contacts'][ng-repeat*='address']";
                   WebElement row = GeneralMethods.FindElementInObjHierarchy(rp.registrationWizard, rowCss);
    			   System.out.println("Set preferred contact method: " +
    			      this.PopulateFieldByLabelName(rp.registrationWizard, rowCss, "Address Type", "Home Address"));	
    			   System.out.println("Click preferred contact method radio button: " +
    				  this.clickElement(GeneralMethods.FindElementInObjHierarchy(row, "input[type='radio']") , "radio button"));
    			}
    			
    			if (!guarantor.equals("")){
    				this.clickWizardTab("Relationships");
    				System.out.println("Set guarantor: " +
    					this.attachPatient(guarantor));
    			}
    			
    			this.clickWizardTab("Visit Information");
    			System.out.println("Set Visit start date: "+
    				rp.setVisitStartDate(visitDate));
    			System.out.println("Set Visit start time: " +
    				rp.setVisitStartTime(visitTime));
    			
    			rp.clickWizardTab("Organization Defined Fields");
    			List<WebElement> requiredFields = GeneralMethods.FindElementsInObjHierarchy(rp.wizardTabFormContainer, "input[class*='required']");
    			if (requiredFields != null && requiredFields.size() > 0)
    				for (WebElement field : requiredFields) 
    					this.setTextField(field, "required field", "123ABC");
         
    			boolean clickedSave = this.clickElement(this.saveButton, "Registration window Save button");
    			
    			if (! GeneralMethods.checkElementIsNotVisible(this.driver, rp.reviewWizard)){
    				System.out.println("Handle review: " +
    					rp.handleReview(ln));
    				clickedSave = this.clickElement(this.saveButton, "Registration window Save button");
    			}
    			
    			return clickedSave;
    		}
    		System.out.println("Registration wizard is not open");
    		return false;
    	} catch (Exception e){
    			System.out.println("registerPatient: Exception thrown "+e.getMessage());
    			return false;
    	}		
    }    
	/**
	 * @param args
	 */
	public void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
