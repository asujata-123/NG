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

import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.toolbox.MouseMethods;
import com.nextgen.qa.automation.ui.GenericPage;

/**
 * 
 */

/**
 * @author raguirre
 *
 */
public class RegistrationPage extends GenericPage {

	 
	/**
	 * 
	 */	
	public RegistrationPage(WebDriver aDriver, String aLOG_FILE) {	
	   super(aDriver, aLOG_FILE);
	   this.driver = aDriver;
       this.LOG_FILE = aLOG_FILE;
	}
	
	// Web Elements       
	@FindBy(how = How.CSS, using = "div[id ^='registrationModal_module'][class*='wizard-container']")
    public WebElement registrationWizard;
	
	@FindBy(how = How.CSS, using = "div[id ^='registrationModal_module'][class*='wizard-container'] div[class='modalCloseX']")
    public WebElement closeButton;
	
	@FindBy(how = How.CSS, using = "div[id ^='registrationModal_module'][class*='wizard-container'] div[class='modalContent']")
    public WebElement modalContent;
	
	@FindBy(how = How.CSS, using = "div[id ^='registrationModal_module'][class*='wizard-container'] input[type='button'][class^='btn'][value='Save']")
	public WebElement saveButton;
	public String saveButtonCss = "input[type='button'][class^='btn'][value='Save']";
	 
	@FindBy(how = How.CSS, using = "div[id ^='registrationModal_module'][class*='wizard-container'] input[type='button'][class^='btn'][value='Cancel']") // MGB 7/21/2014
	public WebElement cancelButton;
	public String cancelButtonCss = "input[type='button'][class^='btn'][value='Cancel']";
	
	@FindBy(how = How.CSS, using = "div[id ^='registrationModal_module'][class*='wizard-container'] input[type='button'][class^='btn'][value='Check In']")
	public WebElement checkinButton;
	public String checkinButtonCss = "input[type='button'][class^='btn'][value='Check In']";
	
	@FindBy(how = How.CSS, using = "button[class$='appointmentContinue']")
	public WebElement continueButton;
	
	@FindBy(how = How.CSS, using = "form ul[class^='tabbedNav'] li")//-[ng-click^='currentTab'] MGB 5/28/2014
	public List<WebElement> wizardTabs;
	
	@FindBy(how = How.CSS, using = "form ul[class^='tabbedNav'] li[class *= 'tabActive']")
	public WebElement selectedWizardTab;
	
	@FindBy(how = How.CSS, using = "div[class^='form-container']") // MGB 3/9/2015 updated css 
	public WebElement wizardTabFormContainer;
	
	@FindBy(how = How.CSS, using = "div[class^='form-container']") // MGB 3/9/2015 updated css 
	public List<WebElement> wizardTabFormContainers;

	@FindBy(how = How.CSS, using = "input[type='text'][name='firstName']") // MGB 7/17/2014
    public  WebElement firstName;
    
	@FindBy(how = How.CSS, using = "input[type='text'][name='middleName']") // MGB 7/17/2014
    public  WebElement middleName;

	@FindBy(how = How.CSS, using = "input[type='text'][name='lastName']") // MGB 7/17/2014
    public  WebElement lastName;

	@FindBy(how = How.CSS, using = "div[name='gender'] a") 
	public  WebElement genderField;
	
	public String genderCss = "div[name='gender'] a";
	
	public String sexCss = "div[name='sex'] a";
	public String sexSelectListCss = "li";
	@FindBy(how = How.CSS, using = "div[name='sex'] a")
	public  WebElement sexField;
	
	@FindBy(how = How.CSS, using = "span[class^='selectListWrapper'] li") 
	public  List<WebElement> sexSelectList;
		
	@FindBy(how = How.CSS, using = "span[class^='selectListWrapper'] li") // MGB 12/8/2014
	public  List<WebElement> genderSelectList;
	public String genderSelectListCss = "li";
	    
    @FindBy(how = How.CSS, using = "input[type='text'][name='dob'][class*='hasDatepicker']")
    public  WebElement datePicker;
    
    @FindBy(how = How.CSS, using = "input[id='regSSN']")
    public  WebElement regSSN;
    
    // Contact Information tab
    @FindBy(how = How.CSS, using = "div[class^='group_contacts'][ng-repeat^='address']")
    public  List<WebElement> preferredContactAddressList;
    
    //@FindBy(how = How.CSS, using = "select[class^='customSelect'][ng-model='person.ContactMethodCodeId']")
    @FindBy(how = How.CSS, using = "div[class^='registration-tabs']")
    public  List<WebElement> preferredContactRegistrationTabs;
    
    @FindBy(how = How.CSS, using = "div[class^='registration-tabs'] select")
    public  List<WebElement> preferredContactMethodSelectTypes;
    public String preferredContactMethodSelectTypeCss = "div[class^='registration-tabs'] select";
    public String preferredContactMethodSelectListTypeCss = "option";
    
    @FindBy(how = How.CSS, using = "div[class^='registration-tabs'] select option")
    public  List<WebElement> preferredContactPulldownList;
    	
    public String preferredContactMethodRadioButtonCss = "input[type='radio']";
    
    // Relationships tab
    
    // MGB 3/6/2015 added css for +Relationship link
    public String addRelationshipLinkCss = "span[data-ng-click*='showRelationship']";
    
    // MGB 3/6/2015 added relationship minor alert
    public String minorAlertMsgCss = "div[class='relationship-minor-alert']";
    
    // MGB 5/28/2014: from scheduler NAP page
    //@FindBy(how = How.CSS, using = "span[class^='global-search registration'] input")
    @FindBy(how = How.CSS, using = "div[class='global-search registration'] input")
    public WebElement searchField;
    public String searchFieldCss = "div[class='global-search registration'] input";
        
    @FindBy(how = How.CSS, using = "div[id^='personSearchResults'][class=^'regSearchResultsNew']")
    public WebElement searchResultsPane;
    
    //@FindBy(how = How.CSS, using = "div[class='resultrow']")
    @FindBy(how = How.CSS, using = "li[ng-repeat='result in personSearchResults']")
    public List<WebElement> searchResults;
    
    @FindBy(how = How.CSS, using = "button[class ^= 'btn']")
    public WebElement attachButton;
    
    @FindBy(how = How.CSS, using = "div[class*='duplicate-check']")
    public WebElement reviewWizard;
    
    @FindBy(how = How.CSS, using = "div[class*='duplicate-check'] table tr")
    public List<WebElement> reviewWizardTableRows;
    
    // Visit Information tab
    @FindBy(how = How.CSS, using = "div[name = 'ServiceLocation']")
    public WebElement serviceLocationField;
    
    @FindBy(how = How.CSS, using = "span[class ^= 'selectListWrapper'] li")
    public List<WebElement> serviceLocationFieldList;
    ////
    
    // methods	
    public WebElement getWebElement(String elementDesc, String cssData){
		return GeneralMethods.WaitForElementBySelector(this.driver, 0, elementDesc, cssData);
		//return GeneralMethods.FindElementInObjHierarchy(this.registrationWizard, cssData);
	}
    
    public boolean setFirstName(String name) throws IOException, InterruptedException {
        return super.setTextField(firstName, "First Name", name);    
    }

    public String getFirstName() throws IOException, InterruptedException {
        return super.getTextFieldValue(firstName, "First Name");
    }
  
    public boolean setMiddleName(String name) throws IOException, InterruptedException {
        return super.setTextField(middleName, "Middle Name", name);     
    }
    
    public String getMiddleName() throws IOException, InterruptedException {
        return super.getTextFieldValue(middleName, "Middle Name");
    }

    public boolean setLastName(String name) throws IOException, InterruptedException {
        return super.setTextField(lastName, "Last Name", name);     
    }

    public String getLastName() throws IOException, InterruptedException {
        return super.getTextFieldValue(lastName, "Last Name");
    }
    
    public boolean setSSN(String ssn) throws IOException, InterruptedException {
        return super.setTextField(regSSN, "SSN", ssn);     
    }
    
    public String getSSn() throws IOException, InterruptedException {
        return super.getTextFieldValue(regSSN, "SSN");
    }

    public boolean setGender(String theGender) throws IOException, InterruptedException {
    	super.clickElement(genderField, "Gender field");
        return super.selectDropdownItem(genderSelectList, "Gender", theGender);
    }
    
    public String getGender() throws IOException, InterruptedException {
        return super.getTextFieldValue(genderField, "Gender");
    }
    
    public boolean clickGender() throws IOException, InterruptedException {
        return super.clickElement(genderField, "Gender");     
    }
  
    public boolean setSexField(String sexFieldVal) throws IOException, InterruptedException {
    	super.clickElement(sexField, "Sex field");
        return super.selectDropdownItem(sexSelectList, "Sex", sexFieldVal);
    }
    
    public boolean clickDatePicker() throws IOException, InterruptedException {
        return super.clickElement(datePicker, "Date Picker");
    }
        
    public boolean setDatePicker(String date) throws IOException, InterruptedException {
        return super.setTextField(datePicker, "Date Picker", date);     
    }
  
    public boolean setVisitStartDate(String date) throws Exception {
    	WebElement startDateField = this.findVisibleDateField(this.registrationWizard, "startDate", "Visit Start Date field"); //this.wizardTabFormContainer
    	this.clickElement(startDateField, "Visit Start Date field");
    	return this.setDateField(startDateField, "Visit Start Date field", date);
    }
    
    public boolean setVisitStartTime(String time) throws Exception {
    	WebElement startTimeField = this.findVisibleTextField(this.registrationWizard, "DateTime", "Visit Start Time field"); //this.wizardTabFormContainer
    	this.clickElement(startTimeField, "Visit Start Time field");
    	return this.setDateField(startTimeField, "Visit Start Time field", time);
    }
    
    public String getDOB() throws IOException, InterruptedException {
        return super.getTextFieldValue(datePicker, "DOB");
    }
    
    public void clickSave() throws IOException, InterruptedException {
        super.clickElement(saveButton, "Save");     
    }
    
    public boolean clickWizardTab(String tabName) throws IOException, InterruptedException {
 	   try{
 		   WebElement selectedTab = this.selectedWizardTab;
 		   if (selectedTab.getText().contains(tabName)) return true;
 		   boolean clicked = false;
 		   for(WebElement tab : this.wizardTabs)
 			   if (tab.getText().contains(tabName)){
 				   clicked = MouseMethods.HoverOverClick(driver, tab, 75, 8);
 				   break;
 			   }
 	       return clicked;
 	   } catch (Exception e){
 		 return false;
 	   }
    }
      
   // sets and verifies that the first name was set 
   public boolean firstNameValidate(String fname) throws IOException, InterruptedException {	   
	   return super.textFieldValidate(firstName, "First Name", fname);
   }
    
   // sets and verifies that the middle name was set
   public boolean middleNameValidate(String fname) throws IOException, InterruptedException {	   
	   return super.textFieldValidate(middleName, "Middle Name", fname);
   }
    
   // sets and verifies that the last name was set
   public boolean lastNameValidate(String fname) throws IOException, InterruptedException {	   
	   return super.textFieldValidate(lastName, "Last Name", fname);
   }
   
   // sets and verifies that the service location was set
   public boolean setVisitLocation(String location) throws Exception {
   	WebElement visitLocationField = this.serviceLocationField;
   	this.clickElement(visitLocationField, "Visit Location field");
    return super.selectDropdownItem(serviceLocationFieldList, "Visit Location", location);
   }        

   // sets and verifies that gender was set
   public boolean genderValidate(String theGender) throws IOException, InterruptedException {
	   return super.dropDownFieldValidate(genderField, genderSelectList, "Gender field", theGender);
	   /*
	   if ( clickGender() ){
	   
	      Thread.sleep( 1000);  // TBD ... change for better synchronization
	      setGender(theGender);
	      return getGender().equals(theGender);
	   }
	   
	   return false;
	   */
   }
   
   public boolean populatePulldownValue(WebElement field, List<WebElement> fieldList, String description, String value, String optionCss, boolean random) throws IOException, InterruptedException {
	   return super.setPicklistValue(field, fieldList, description, value, optionCss, random);
   }
   
   public boolean wizardExists()throws IOException, InterruptedException {
	   try{
		   GeneralMethods.delay(3000);
		   WebDriverWait wait = new WebDriverWait(driver, 15);
		   WebElement wizard = wait.until(ExpectedConditions.visibilityOf(registrationWizard));
		   WebElement header = wizard.findElement(By.cssSelector("div[class='topCenter']"));
		   String headerText = header.getText().toLowerCase(); 
		   return headerText.contains("registration");
	   } catch (Exception e){
		   return false;
	   }
   }
   
   
   // find the registration row with the id that matches the passed String
   public WebElement getRegistrationRow (String rowId) throws Exception{
	   try{
		   for (WebElement regTab : this.preferredContactRegistrationTabs)
			   if (regTab.getAttribute("id").equals(rowId)){
				   while (! regTab.isDisplayed()) 
					   GeneralMethods.scrollOnElement(driver, this.modalContent, "down");
				   return regTab;
			   }
		   System.out.println("Registration row with id "+rowId+" was not found; verify that the id is correct");
		   return null;
	   } catch (Exception e){
		   System.out.println("Exception thrown while searching for egistration wizard row with id "+rowId+" "+e.getMessage());
		   return null;
	   }
   }
   
   public boolean selectPreferredContactMethod(WebElement obj, String cssField, String cssValue, boolean random, String description) throws Exception {
	   try{
		   if (random) {
			   cssField = "address";
			   cssValue = "Home Address";
		   }
		   this.pickDropDownValue(obj, cssField, cssValue, "dropdown item value "+cssValue);
		   //WebElement radioButton = super.findVisibleRadioButton(obj, cssField, description+" radio button");
		   String cssFieldLC = cssField.toLowerCase();
		   List<WebElement> labels = GeneralMethods.FindElementsInObjHierarchy(obj, "label");
		   for (WebElement radioButton : labels) {
			   if (radioButton.getText().toLowerCase().contains("preferred contact method") && 
					   radioButton.getAttribute("for").toLowerCase().contains(cssFieldLC) &&
					       radioButton.isDisplayed())
				   return super.clickElement(radioButton, description+" radio button");   
		   }
		   System.out.println("Could not find radio button "+description);
		   return false;
	   } catch (Exception e) {
		   System.out.println("Exception thrown by selectPreferredContactMethod " +e.getMessage());
		   return false;
	   }
   }
   
   public WebElement getFieldFromList(List<WebElement> list, String id) throws InterruptedException{
   	try{
   		for (WebElement field : list) 
   			if (field.getAttribute("id").equals(id)) return field;
   		System.out.println("Could not find element "+ id);
   		return null;
   	} catch (Exception e) { 
   		System.out.println("Exception when trying to find element "+ id);
   		return null;
   	}
   }
   
   public boolean performPatientSearch(String searchString)throws IOException, InterruptedException {
       try{
       	WebElement visibleSearchField = this.findVisibleSearchField(this.wizardTabFormContainer, "search", "Registration wizard form container search field");
       	return MouseMethods.HoverOverClickSendKeys(driver, visibleSearchField, searchString, 10, 10);
       } catch (Exception e) {
    	   System.out.println("performPatientSearch: Exception thrown when searching for patient/guarantor "+e.getMessage());
    	   return false; 
       }
   }
   
   public boolean attachPatient(String patientName) throws IOException, InterruptedException {
	   try{
		   this.performPatientSearch(patientName);
		   GeneralMethods.delay(1000);
		   List<WebElement> results = this.searchResults;
		   WebElement button = null;
		   for(WebElement result : results){
			   if (result.getText().contains(patientName)){	   
				  GeneralMethods.delay(1000);
			      button = GeneralMethods.FindElementInObjHierarchy(result, "input[class ^= 'btn'][value='Select']");
			      if (button == null) button = GeneralMethods.FindElementInObjHierarchy(result, "input[class ^= 'btn'][value='Attach']");
			      break;
			   }
			   GeneralMethods.scrollOnElement(driver, this.searchResultsPane, "down");
		   }
		   if (button == null){
			   System.out.println("Could not attach patient/guarantor ");
			   return false;
		   }
	       GeneralMethods.ClickButton(button);
	       return true;
	   } catch (Exception e){
		 System.out.println("Exception thrown when attaching patient/guarantor "+e.getMessage() );
		 return false;
	   }
   }
   
   public WebElement getOpenForm(String tabName){
	   try{
		   for (WebElement form : this.wizardTabFormContainers){
			   if (form.getAttribute("ng-show").contains(tabName)) return form;
		   }
		   System.out.println("getOpenForm: could not find for for tab "+tabName);
		   return null;
	   } catch (Exception e){
		   System.out.println("getOpenForm: exception thrown "+e.getMessage());
		   return null;
	   }
   }
   
    //use cases
    public boolean registerPatient(String fn, String mn, String ln, String sex, String gender, String ssn, String dob, 
    		String contactType, String contactSubType, String address1, String address2, String city, String state, String zip,
    		String guarantor, 
    		String visitDate, String visitTime, String visitLocation) throws IOException, InterruptedException{         	    
    	try{
    		boolean success;
    		GeneralMethods.delay(1000);
    		if (this.wizardExists()){
    			this.clickWizardTab("General Demographics");
    			
    			if (! gender.equals("")) {
    				System.out.println("Set gender field: "+
    						this.setGender(gender));
    			}
    			System.out.println("Set sex field: "+ 
        				this.setSexField(sex));
    			
    			System.out.println("Set first name field: "+ 
    				this.setFirstName(fn));
    			if (!mn.equals("")) System.out.println("Set middle name field: "+ 
    				this.setMiddleName(mn));
    			System.out.println("Set last name field: "+ 
    				this.setLastName(ln));
    			this.clickDatePicker();     
    			System.out.println("Set date of birth: "+ 
    				this.setDatePicker(dob));
    			if (!ssn.equals("")) System.out.println("Set SSN field: "+ 
    				this.setSSN(ssn));
    			
    			this.clickWizardTab("Contact Information");
                String rowCss = "div[class^='group_contacts'][ng-repeat*='address']";
                WebElement row = GeneralMethods.FindElementInObjHierarchy(this.registrationWizard, rowCss);
    			System.out.println("Set preferred contact method: " +
    				this.PopulateFieldByLabelName(this.registrationWizard, rowCss, contactType, contactSubType));	
    			System.out.println("Click preferred contact method radio button: " +
    				this.clickElement(GeneralMethods.FindElementInObjHierarchy(row, "input[type='radio']") , "radio button"));
    			
    			if (contactSubType.equals("Home Address")){
    			   rowCss = "div[class='display-flex contact-input-group']";
    			   rowCss = "div[class='flex-it']";
    			   System.out.println("Populate the address line 1: " + this.PopulateFieldByLabelName(this.registrationWizard, rowCss, "Address", address1));
    			   if (!address2.equals("")) System.out.println("Populate the address line 2: " + this.PopulateFieldByLabelName(this.registrationWizard, rowCss, "Address", address2));
    			   System.out.println("Populate the address zip: " + this.PopulateFieldByLabelName(this.registrationWizard, rowCss, "ZIP Code | Postal Code", zip));
    			   System.out.println("Populate the address city: " + this.PopulateFieldByLabelName(this.registrationWizard, rowCss, "City", city));
    			   System.out.println("Populate the address state: " + this.PopulateFieldByLabelName(this.registrationWizard, rowCss, "State", state));
    			}
    			
    			if (!guarantor.equals("")){
    				this.clickWizardTab("Relationships");
    				System.out.println("Set guarantor: " +
    					this.attachPatient(guarantor));
    			}
    			
    			this.clickWizardTab("Visit Information");
    			System.out.println("Set Visit location: "+
    			    this.setVisitLocation(visitLocation));
    			System.out.println("Set Visit start date: "+
    				this.setVisitStartDate(visitDate));
    			System.out.println("Set Visit start time: " +
    				this.setVisitStartTime(visitTime));
    			
    			if (this.clickWizardTab("Organization Defined Fields")){
    			   WebElement form = this.getOpenForm("odf");
    			   if (form != null){
    				   List<WebElement> requiredFields = GeneralMethods.FindElementsInObjHierarchy(form, "input[class*='required']");
    			       if (requiredFields != null && requiredFields.size() > 0)
    			          for (WebElement field : requiredFields) 
    			             this.setTextField(field, "required field", "123ABC");
    			   }
    			}
         
    			boolean clickedSave = this.clickElement(this.saveButton, "Registration window Save button");
    			
    			if (! GeneralMethods.checkElementIsNotVisible(this.driver, this.reviewWizard)){
    				System.out.println("Handle review: " +
    					this.handleReview(ln));
    				clickedSave = this.clickElement(this.saveButton, "Registration window Save button");
    			}
    			
    			GeneralMethods.delay(5000);
    			return clickedSave;
    		}
    		System.out.println("Registration wizard is not open");
    		return false;
    	} catch (Exception e){
    			System.out.println("registerPatient: Exception thrown "+e.getMessage());
    			return false;
    	}		
    }
    
    public boolean handleReview(String data) throws Exception {
    	try {
    		String dataLC = data.toLowerCase();
    		boolean selectedRow = false;
    		for (WebElement row : this.reviewWizardTableRows)
    			if (row.getText().toLowerCase().contains(dataLC)) {
    				WebElement selector = GeneralMethods.FindElementInObjHierarchy(row, "td input[type='radio']");
    				this.clickElement(selector, "duplicate person review selector");
    				selectedRow = true;
    			}
    		if (selectedRow) return this.clickElement(this.findVisibleButton(this.reviewWizard, "Done", ""), "Review Wizard Done button");
    		else return this.clickElement(this.findVisibleButton(this.reviewWizard, "Cancel", ""), "Review Wizard Cancel button");
    	} catch (Exception e) {
    		System.out.println("handleReview: Exception thrown "+e.getMessage());
    		return false;
    	}
    }
    
	/**
	 * @param args
	 */
	public  void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
