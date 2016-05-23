package com.nextgen.qa.automation.pages;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.nextgen.qa.automation.toolbox.*;
import com.nextgen.qa.automation.ui.GenericPage;

/**
 * 
 */

/**
 * @author mbodoh
 *
 */
public class SchedulerPage extends GenericPage {

	 
	/**
	 * 
	 */	
	public SchedulerPage(WebDriver aDriver, String aLOG_FILE) {	
	   super(aDriver, aLOG_FILE);
	   this.driver = aDriver;
       this.LOG_FILE = aLOG_FILE;
	}
	
	public LoginPage lp = new LoginPage(driver, "login.txt");
	public MainPage mp = new MainPage(driver, "main.txt");
	
	// WEB ELEMENTS    
    @FindBy(how = How.CSS, using = "section[class *= 'scheduling smartfiles']")
    public WebElement scheduler;
    
    @FindBy(how = How.CSS, using = "div[ng-click*='toggleResourcesList']")
    public WebElement resourcesPaneArrow;
    
    @FindBy(how = How.CSS, using = "div[class*='group sidebar'][ng-show*='showResources']")
    public WebElement resourcesPane;
    
    @FindBy(how = How.CSS, using = "section[class^='dateBar']")
    public WebElement dateBar;   
    
    @FindBy(how = How.CSS, using = "div[class='icon_info']")
    public WebElement infoIcon;
    
    @FindBy(how = How.CSS, using = "section[class *= 'scheduling smartfiles'] div[class='toggle-button-group day-week-buttons']")
    public WebElement weeklyViewButtonGroup;
    
    public String weeklyViewDay = "div[ng-class*='daily']";
    public String weeklyViewWeek5 = "div[ng-class*='week5']";
    public String weeklyViewWeek7 =  "div[ng-class*='week7']";
    
    @FindBy(how = How.CSS, using = "section[class *= 'scheduling smartfiles'] div[class='button-group']")
    public WebElement todayButtonGroup;
    
    @FindBy(how = How.CSS, using = "section[class *= 'scheduling smartfiles'] div[ng-click ^= 'showDateJump']")
    public WebElement jumpToDateButton;

    @FindBy(how = How.CSS, using = "section[class *= 'scheduling smartfiles'] input[ng-model = 'dateJumpString']")
    public WebElement jumpToDateField;
    
    @FindBy(how = How.CSS, using = "section[class *= 'scheduling smartfiles'] div[class='dateContainer']")
    public WebElement apptDateContainer;
    
    @FindBy(how = How.CSS, using = "section[class *= 'scheduling smartfiles'] div[class ^= 'scroll-table']") // MGB 8/27/2014
    public WebElement scrollBar;

    @FindBy(how = How.CSS, using = "a[class^='icon add-event'][ng-click ^= 'launchAddAppointment']") // MGB 7/18/2014
    public WebElement addApptButton;
    
    @FindBy(how = How.CSS, using = "section[class *= 'scheduling smartfiles'] div[class ^= 'resource-org-view'] div[class = 'itemRow']")
    public List<WebElement> resourceList;
    
    @FindBy(how = How.CSS, using = "div[class^='schedule-book-container'] table[class='resource-header']")
    public List<WebElement> resourceHeader;
    
    @FindBy(how = How.CSS, using = "div[class^='schedule-book-container'] table[class='resource-header'] td[ng-repeat^='resource in scheduling'] b") 
    public List<WebElement> selectedResourceList;

    @FindBy(how = How.CSS, using = "div[class*='appointmentOverlay']")
    public WebElement apptOverlay;

    //@FindBy(how = How.CSS, using = "section[class *= 'scheduling smartfiles'] div[class='appointmentOverlay'] div[class ^= 'appointmentHolder']")
    @FindBy(how = How.CSS, using = "div[class^='appointmentOverlay'] div[class ^= 'appointmentHolder']") // MGB 3/11/2015 updated css docd
    public List<WebElement> apptOverlayAppts;
    
    @FindBy(how = How.CSS, using = "div[class*='scroll-date']")
    public WebElement scrollDate;
    
    // Web Elements - Appt Details
    @FindBy(how = How.CSS, using = "article[ng-show*='apptDetails']")
    public WebElement apptDetails;
    
    @FindBy(how = How.CSS, using = "a[value = 'Cancel Appt']")
    public WebElement cancelAppt;
    
    @FindBy(how = How.CSS, using = "input[value = 'Yes']")
    public WebElement cancelApptYes;
    
    @FindBy(how = How.CSS, using = "input[value = 'No']")
    public WebElement cancelApptNo;
    
    @FindBy(how = How.CSS, using = "article[class *= 'cancelAppt']")
    public WebElement cancelationPopup;
    String cancelationPopupCSS = "article[class *= 'cancelAppt']";
    
    @FindBy(how = How.CSS, using = "select[ng-model = 'cancelReasonId']")
    public WebElement cancelationReasonSelector;
    String cancelationReasonSelectorCSS = "select[ng-model = 'cancelReasonId']";
    
    @FindBy(how = How.CSS, using = "select[ng-model = 'cancelReasonId'] option")
    public List<WebElement> cancelationReasonSelectorList;
    //// 
    
    
    // METHODS

    public boolean wizardExists()throws IOException, InterruptedException {
 	   return GeneralMethods.checkIsDisplayed(scheduler);
    }
    
    public boolean clickNewApptButton() throws IOException, InterruptedException {
        return super.clickElement(addApptButton, "Add Appointment Button");
    }
    
    public boolean clickCancelApptYes() throws IOException, InterruptedException {
        return super.clickElement(cancelApptYes, "Cancelation popup Yes button");
    }
    
    public boolean clickCancelApptNo() throws IOException, InterruptedException {
        return super.clickElement(cancelApptNo, "Cancelation popup No button");
    }
    
    public boolean selectResource(String resource, boolean random) throws IOException, InterruptedException {
       try{
          if (random == true){
    	     int randomNum = GeneralMethods.PickRandomNumber(resourceList.size()-1);
    		 WebElement doc = resourceList.get(randomNum);
    		 return super.clickElement(doc, "select and click random resource "+doc.getText());
    	  } else {
    	     for(WebElement doctor: resourceList)
    		    if (doctor.getText().contains(resource)) 
    			   return super.clickElement(doctor, "resource "+doctor.getText());
    	  }	
          return false;
       } catch (Exception e){
          return false;
	   }    
    }
    
    public void deselectAllResources(){
       try{
    	  WebElement resource;
    	  int selectedSize = this.selectedResourceList.size();
    	  List<WebElement> selected = this.selectedResourceList;
    	  for (int i = 0; i < selectedSize; i++){
    		  selected = this.selectedResourceList;
    		  resource = selected.get(0);
    		  if (resource.isDisplayed()) {
    			  this.selectResource(resource.getText(), false);
    			  GeneralMethods.delay(2000);
    		  }
    	  }
    	  GeneralMethods.delay(2000);
       } catch (Exception e){}    
    }
    
    public boolean populateJumpToDate(String date) throws IOException, InterruptedException {
    	super.clickElement(jumpToDateButton, "Scheduler Jump To Date button");
    	boolean success = super.setTextField(jumpToDateField, "Scheduler Jump to Date field", date+Keys.ENTER);
    	GeneralMethods.delay(2000);
    	return success;
    }
    
    public boolean selectCancelationReason(WebDriver driver, String reason) throws IOException, InterruptedException {
    	boolean success = false;
    	WebElement cancelButton = cancelAppt;
    	super.clickAndWaitForElement(cancelAppt, "Appointment Details Cancel link", cancelationPopup);
    	super.clickElement(cancelationReasonSelector, "Cancelation popup");
    	success = super.selectDropdownItem(cancelationReasonSelectorList, "Cancellation reason dropdown value", reason);
    	success &= this.clickCancelApptYes();
    	return success;
    }

    public boolean checkDateContainerMatch(String date) throws IOException, InterruptedException {
    	String date2 = GeneralMethods.ConstructDate(date);
	    return apptDateContainer.getText().contains(date2) ? true : false;
    }
    
    public WebElement getAppointmentFromOverlay(String name) throws IOException, InterruptedException {
    	GeneralMethods.setTimeout(0);
    	WebElement appointmentObj = null;
		try{
			for (WebElement appointment : apptOverlayAppts)
				if (appointment.getText().contains(name)) {
                    appointmentObj = appointment;
					break;
				}
			GeneralMethods.resetTimeout();
			return appointmentObj;
		} catch (Exception e) {
			System.out.println("Exception thrown on GetAppointmentFromOverlay " +e.getMessage());
			GeneralMethods.resetTimeout();
			return null;
		}
	}
	
	public boolean scrollToHour(String time) throws IOException, InterruptedException {
		try{
			Dimension size = scrollBar.getSize();
			int height = size.getHeight();
			int width = size.getWidth();
			if (time.contains("AM")) height = height/2; // if time is in the morning, set height of the scrolling to midday
			List<String> hour = Arrays.asList(time.split(":")); // get the hour of time 
			int multiplier = Integer.parseInt(hour.get(0)); // convert the string to integer
			multiplier += 3; // need this in case the appointment popup is needed, it needs room to open 
			int step = height/12;
			MouseMethods.HoverOverClick(driver, scrollBar, width-5, step*multiplier);
			GeneralMethods.delay(3000);
			return true;
		} catch (Exception e){
			System.out.println("Could not scroll to time " +time +" " +e.getMessage() );
			return false;
		}
	}
	
	public WebElement findScrollDateTime(WebElement scheduler, String time) throws IOException, InterruptedException {
		try {
			WebElement container = scheduler.findElement(By.cssSelector("div.schedule-book-container"));
			WebElement scrollDate = container.findElement(By.cssSelector("div.scroll-date"));
			List<WebElement> timeSlots = scrollDate.findElements(By.cssSelector("div[ng-repeat ^= 'time in times']"));
			for (WebElement timeSlot : timeSlots) if (timeSlot.getText().equals(time)) return timeSlot;
			System.out.println("Could not find time slot " + time);
			return null;
		} catch (Exception e) {
			System.out.println("Exception thrown at FindScrollDatetime " + e.getMessage());
			return null;
		}
	}
	
	public WebElement getApptDetails(WebElement apptInOverlay) throws IOException, InterruptedException {
		try{
			super.clickAndWaitForElement(apptInOverlay, "Appointment pill", apptDetails);
			return apptDetails;
		} catch (Exception e) { return null; }
	}
	
	public boolean checkIfResourcesPaneExpanded()
	{
		try{
			String attr = this.resourcesPane.getAttribute("class");
			//System.out.println("checkIfResourcesPaneExpanded: class attr = "+attr);
			if (attr.contains("hide")) return false;
			else return true;
		} catch (Exception e) { 
			System.out.println("checkIfResourcesPaneExpanded: exception thrown");
			return false; 
		}
	}
	
	public boolean toggleResourcesPane(String direction) {
		try{
			String directionLC = direction.toLowerCase();
			if (directionLC.equals("expand")){
				if (!checkIfResourcesPaneExpanded())
					this.clickElement(this.resourcesPaneArrow, "resource pane expand/collapse arrow");
				return true;
			}
			else if (directionLC.equals("collapse")){
				if (checkIfResourcesPaneExpanded())
					this.clickElement(this.resourcesPaneArrow, "resource pane expand/collapse arrow");
				return true;
			}
			else {
				System.out.println("Direction needs to be either 'expand' or 'collapse'");
				return false;
			}
		} catch (Exception e) { return false; }
	}
	
	public boolean deleteAppointment(String patientName){
		try {
			boolean success = false;
			WebElement pill = this.getAppointmentFromOverlay(patientName);
			WebElement details = this.getApptDetails(pill);
			this.clickElement(this.findVisibleButton(details, "Delete", ""), "Delete link");
			success = this.clickElement(this.findVisibleButton(this.scheduler, "Yes, Delete", ""), "appt delete confirmation button");
			return success;
		} catch (Exception e){
			System.out.println("deleteAppointment: exception thrown " +e.getMessage());
			return false;
		}
	}
    ////
	
	// WORKFLOWS
	/**
	 * Assumption: Scheduler is open
	 * @param nap
	 * @param date
	 * @param time
	 * @param apptType
	 * @param duration
	 * @param location
	 * @param resource
	 * @param patient
	 * @return
	 */
	public boolean createAppointment(NewAppointmentPage nap, String date, String time, String type, String duration, String location, 
			String contactType, String contactSubType, String address1, String address2, String city, String state, String zip,
			String resource, String patient, String guarantor) throws Exception {
		try{
			boolean success = true;
			
			// Manage patient name
			String patientAlpha;
			if (patient.equals("")) patientAlpha = "No Patient";
			else{
				String[] temp = patient.split(" ");
				int size = temp.length;
				patientAlpha = temp[size-1] + "," ;
				for(int i=0; i < size-2; i++) patientAlpha += " "+temp[i];
			}
			
			// Launch Add Appointment wizard if not open already
			//if(! nap.wizardExists()){
			if (GeneralMethods.checkElementIsNotVisible(nap.driver, nap.newAppointmentWizard)){
			   this.clickNewApptButton();
			   success = nap.wizardExists();
			}
			
			// Set patient name or select No Patient
			if (success){
				success = nap.clickWizardTab("General Demographics");
				
				if (patient.equals("")) success &= nap.clickNoPatient();
				else success &= nap.attachPatient(patient);
										
				// Fill out required appointment fields
				nap.clickWizardTab("Appointment");
				
				boolean random = resource.equals("") ? true : false;
				success &= super.setPicklistValue(nap.apptResourceField, nap.apptResourceFieldList, "Resource time", resource, "option", random);
			
				random = time.equals("") ? true : false;
				success &= super.setPicklistValue(nap.apptTimeField, nap.apptTimeFieldList, "Appt time", time, "option", random);
			
				random = type.equals("") ? true : false;
				success &= super.setPicklistValue(nap.apptTypeField, nap.apptTypeFieldList, "Appt type", type, "option", random);
			
				random = duration.equals("") ? true : false;
				success &= super.setPicklistValue(nap.apptDurationField, nap.apptDurationFieldList, "Appt duration", duration, "option", random);
			
				random = location.equals("") ? true : false;
				success &= super.setPicklistValue(nap.apptLocationField, nap.apptLocationFieldList, "Appt location", location, "option", random);
				
				if (date.equals("")) date = GeneralMethods.GenerateRandomDate(2); 
				success &= super.setDateField(nap.apptDateField, "Appt date", date);
				
				nap.clickWizardTab("Contact Information");
				
				String rowCss = "div[class^='group_contacts'][ng-repeat*='address']";
                WebElement row = GeneralMethods.FindElementInObjHierarchy(nap.newAppointmentWizard, rowCss);
    			System.out.println("Set preferred contact method: " +
    				this.PopulateFieldByLabelName(nap.newAppointmentWizard, rowCss, contactType, contactSubType));	
    			System.out.println("Click preferred contact method radio button: " +
    				this.clickElement(GeneralMethods.FindElementInObjHierarchy(row, "input[type='radio']") , "radio button"));
    			
    			if (contactSubType.equals("Home Address")){
    			   rowCss = "div[class='display-flex contact-input-group']";
    			   rowCss = "div[class='flex-it']";
    			   System.out.println("Populate the address line 1: " + this.PopulateFieldByLabelName(nap.newAppointmentWizard, rowCss, "Address", address1));
    			   if (!address2.equals("")) System.out.println("Populate the address line 2: " + this.PopulateFieldByLabelName(nap.newAppointmentWizard, rowCss, "Address", address2));
    			   System.out.println("Populate the address zip: " + this.PopulateFieldByLabelName(nap.newAppointmentWizard, rowCss, "ZIP Code | Postal Code", zip));
    			   System.out.println("Populate the address city: " + this.PopulateFieldByLabelName(nap.newAppointmentWizard, rowCss, "City", city));
    			   System.out.println("Populate the address state: " + this.PopulateFieldByLabelName(nap.newAppointmentWizard, rowCss, "State", state));
    			}
				
				nap.clickWizardTab("Relationships");
			
				if (guarantor.equals("")) guarantor = "Sarah Miller";
				else success &= nap.attachPatient(guarantor);
				
				success &= nap.clickSave();
				
			} else { 
				System.out.println("Could not populate all required fields; clicking NAP close button");
				GeneralMethods.ClickButton(nap.closeButton);
			}
			/*				
			if (success){
				// Ignore conflicts and save
				success &= GeneralMethods.ClickButton(nap.viewConflictsButton);
				success &= GeneralMethods.ClickButton(nap.ignoreConflictsButton);
				success &= nap.clickSave();
			}
			*/
			if (success){
				// Check that appointment was created
				//this.selectResource(resource, false);
				//this.populateJumpToDate(date);
			
				success &= (this.getAppointmentFromOverlay(patientAlpha) != null);
			}
			
			return success;
			
		} catch (Exception e) {
			System.out.println("createAppointment: Exception thrown by workflow " +e.getMessage());
			return false;
		}
	}
	////
   
	/**
	 * @param args
	 */
	
	@Test
	public void main() throws Exception {
		ChromeOptions options = new ChromeOptions();
	    options.addArguments(NG7TestCase.chromeOptions);
	    driver = new ChromeDriver(options);
	    driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.SECONDS);
	    driver.manage().window().maximize();
	    lp.launchApplication("NG7");
     	lp.login();
     	
     	mp.handleWelcome("");
     	
     	WebElement schedulerWidgetButton = mp.getSchedulerWidgetButton();
     	GeneralMethods.ClickButton(schedulerWidgetButton);
     	
     	//this.createAppointment(nap, "5/23/2015", "11:00 AM", "", "1 Hr", "", "", "", "Sarah Miller");
     	
	    

	}

}
