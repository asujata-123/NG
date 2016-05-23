package com.nextgen.qa.automation.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;



























import com.nextgen.qa.automation.Issues.CustomException;
//import com.nextgen.qa.automation.pages.ListString;
import com.nextgen.qa.automation.toolbox.*;

/*
 * This java code was adapted from article at http://blog.cloudtroopers.com/content/using-genericpage-your-java-selenium-webdriver-framework 
 */
	
/**
 * Generic Page is extended by every page in this framework, meaning that all pages can use its methods.
 * General user actions
 */
public class GenericPage {

	    private static final Logger log = Logger.getLogger(GenericPage.class.getName());
	    private static FileHandler fh = null;
	
        public WebDriver driver;
        public String LOG_FILE;
               
        //public int TIMEOUT =  AutomationSettings.getTimeout();
        public int TIMEOUT =  NG7TestCase.timeOut;
        //public String BASE_URL = AutomationSettings.getBaseURL();
        public String BASE_URL = NG7TestCase.deployment; // MGB 4/29/2014
        
        public String USER_NAME = AutomationSettings.getUserName();
        public String PASSWORD = AutomationSettings.getPassword();
        public int EVENTDELAY = AutomationSettings.getEventDelay();
        
        @FindBy(how = How.CSS, using = "div[class*='spinner']")
        public WebElement loadingSpinner;
        
        // Error WebElement objects
		@FindBy(xpath="//div/p[*= 'HTTP status code of: 504']")
	    public WebElement ErrorLockPage;
		
		//@FindBy(how = How.CSS, using = "div[class^='lockScreenBox']")
		@FindBy(how = How.CSS, using = "div[class*='error']") // MGB 1/21/2015 
	    public WebElement exceptionPopUp;
		
		//@FindBy(how = How.CSS, using = "div[class*='error-container']")
	    //public WebElement exceptionPopUp2;
		
		//@FindBy(how = How.CSS, using = "div[class*='error-message-container']")
	    //public WebElement exceptionPopUp3;
		
		//@FindBy(how = How.CSS, using = "input[type='button'][value='Unlock']")
		@FindBy(how = How.CSS, using = "a[ng-click*='unLock']") // MGB 1/21/2015
	    public WebElement unlockButton;
		
		//@FindBy(how = How.CSS, using = "a[ng-click*='unLock']")
	    //public WebElement unlockButton2;
		////
		
		// TODO: Experimental
			// types of buttons and tabs
			@FindBy(how = How.CSS, using = "div[class^='ltGreyButtonGroup'] a")
			public List<WebElement> listButtonsLightGrayButtonGroup;
			String listButtonsLightGrayButtonGroupCss = "div[class^='ltGreyButtonGroup'] a";
		
			@FindBy(how = How.CSS, using = "input[type='button'][class^='btn']")
			public List<WebElement> listButtonsClassBtn;
			String listButtonsClassBtnCss = "input[type='button'][class*='btn']";
			
			@FindBy(how = How.CSS, using = "span[class*='button']")
			public List<WebElement> listButtonsSpan;
			String listButtonsSpanCss = "span[class*='button']";
			
			@FindBy(how = How.CSS, using = "div[class^='add']")
			public List<WebElement> listButtonsClassAdd;
			String listButtonsClassAddCss = "div[class^='add']";
			
			@FindBy(how = How.CSS, using = "div[class^='header-block-item']")
			public List<WebElement> listTabsHeaderBlock;
			String listTabsHeaderBlockCss = "div[class*='header-block-item']";
		
			@FindBy(how = How.CSS, using = "ul[class*='tabbedNav'] li")
			public List<WebElement> listTabsNav;
			String listTabsNavCss = "ul[class*='tabbedNav'] li";
			
			@FindBy(how = How.CSS, using = "ul[class^='info-list'] li")
			public List<WebElement> listTabsInfoList;
			String listTabsInfoListCss = "ul[class^='info-list'] li";
			
			// types of radio buttons
			@FindBy(how = How.CSS, using = "input[type='radio']")
			public List<WebElement> listRadioButtonsTypeRadio;
			String listRadioButtonsTypeRadioCss = "input[type='radio']";
			
			// types of date fields
			@FindBy(how = How.CSS, using = "input[class *= 'hasDatePicker']")
			public List<WebElement> listDateFieldHasDatePicker;
			String listDateFieldHasDatePickerCss = "input[class *= 'hasDatepicker']";
			
			// types of text fields
			@FindBy(how = How.CSS, using = "input[type = 'text']")
			public List<WebElement> listTextFieldTypeText;
			String listTextFieldTypeTextCss = "input[type = 'text']";
			
			// types of dropdown fields
			@FindBy(how = How.CSS, using = "select[class^='customSelect']")
			public List<WebElement> listDropdownFieldsSelect;
			//String listDropdownFieldsSelectCss = "select[class^='customSelect']";
			String listDropdownFieldsSelectCss = "select";
			
			@FindBy(how = How.CSS, using = "select[class*='chosen']")
			public List<WebElement> listDropdownFieldsChosen;
			String listDropdownFieldsChosenCss = "select[class*='chosen']";
			
			// types of dropdown lists
			@FindBy(how = How.CSS, using = "li")
			public List<WebElement> listItemsLi;
			String listItemsLiCss = "li";
			
			@FindBy(how = How.CSS, using = "option")
			public List<WebElement> listItemsOption;
			String listItemsOptionCss = "option";
			
			// types of search fields
			String listSearchFieldTypeSearchCss = "input[type = 'search']";
			
			// types of widgets
			@FindBy(how = How.CSS, using = "div[class^='dashboard-widget'] h1")
			public List<WebElement> listWidgetsDashboardWidget;
			String listWidgetsDashboardWidgetCss = "div[class^='dashboard-widget'] h1";
		////
        
        public GenericPage(WebDriver aDriver, String aLOG_FILE) {
            driver = aDriver;
            
            // MGB 3/27/2015 moved the log directory to the artifact location
            String pathStr = NG7TestCase.artifactPath + "log\\";
            Path path = Paths.get(pathStr);
            if (Files.notExists(path)) new File(pathStr).mkdir();
            //LOG_FILE = "log\\" + aLOG_FILE;
            LOG_FILE = pathStr + aLOG_FILE;
            ////
            
            PageFactory.initElements(driver, this);
            
            // initialize logging 
            try {
            	 fh = new FileHandler(LOG_FILE, false);
//            } catch (SecurityException | IOException e) {
            } catch (IOException e) {	 
            	 e.printStackTrace();
            }
                   
             fh.setFormatter(new SimpleFormatter());
             log.addHandler(fh);             
             //log.setLevel(Level.CONFIG);            
             log.setLevel(Level.INFO);
        }


//-------------------- USER ACTIONS -------------------
        public boolean clickElement_old(WebElement elementToClick, String elementName) throws IOException {
        	boolean success = true;
        	log.info( "Clicking << "+elementName+" >> button...");

            try{
            	if (this.checkLoadingSpinner()) success = false;
            	else {
            		if (this.checkErrorPage())
            			Artifact.ReportDoNotDeliverFail("Fail", "GenericPage: Exception detected and recovery attempted when attempting to click "+elementName+" during test "+NG7TestCase.testName);
            		new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(elementToClick));
            		elementToClick.click(); 
            		GeneralMethods.delay(AutomationSettings.getClickDelay()); // MGB 4/1/2014
            	}	
            } catch(Exception e){
            	log.info(e.getMessage());
                driver.switchTo().defaultContent();
                success = false;
            }
            
            log.info("DONE"  + (success ?  "" : " with errors") );
            return success;
        }
        
        public boolean clickElement(WebElement elementToClick, String elementName) throws IOException {
        	boolean success = true;
        	log.info( "Clicking << "+elementName+" >> button...");

            try{
            	success = GeneralMethods.ClickButton(elementToClick);
            	if (!success){
            		this.checkLoadingSpinner();
            		this.checkErrorPage();
            		success = GeneralMethods.ClickButton(elementToClick); // re-try
            	}	
            } catch(Exception e){
            	log.info(e.getMessage());
                driver.switchTo().defaultContent();
                System.out.println("clickElement: Exception thrown");
                success = false;
            }
            
            log.info("DONE"  + (success ?  "" : " with errors") );
            System.out.println("clickElement: returning success="+success);
            return success;
        }
        
        public boolean checkElementIsVisible_old(WebElement elementToCheck, String elementName) throws IOException {
        	boolean success = true;
        	log.info( "Check element << "+elementName+" >> is visible");

            try{
               if (this.checkLoadingSpinner()) success = false;
               else {
                  if (this.checkErrorPage())
            		 Artifact.ReportDoNotDeliverFail("Fail", "GenericPage: Exception detected and recovery attempted when attempting to click "+elementName+" during test "+NG7TestCase.testName);
            	  success = GeneralMethods.checkIsDisplayed(elementToCheck);
               } 
            } catch(Exception e){
            	log.info(e.getMessage());
                driver.switchTo().defaultContent();
                success = false;
            }
            
            
            log.info("DONE"  + (success ?  "" : " with errors") );
            return success;
        }
        
        public boolean checkElementIsVisible(WebElement elementToCheck, String elementName) throws IOException {
        	boolean success = true;
        	log.info( "Check element << "+elementName+" >> is visible");

            try{
            	success = GeneralMethods.checkIsDisplayed(elementToCheck); 
            	if (!success) {
            		this.checkLoadingSpinner();
            		this.checkErrorPage();
            		//success = GeneralMethods.checkIsDisplayed(elementToCheck); // re-try
            		WebElement findElement = GeneralMethods.WaitForElement(this.driver, 5, elementName, elementToCheck); // re-try
            		success = findElement != null ? true : false;
            	}
               
            } catch(Exception e){
            	log.info(e.getMessage());
                driver.switchTo().defaultContent();
                success = false;
            }
            
            log.info("DONE"  + (success ?  "" : " with errors") );
            return success;
        }
        
        public boolean clickCheckbox(WebElement elementToClick, String elementName) throws IOException {
        	boolean success = true;
        	log.info( "Clicking << "+elementName+" >> checkbox...");

            try{
                new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(elementToClick));
                MouseMethods.ClickAndHold(driver, elementToClick, 6, 6);
            }catch(Exception e){
            	log.info(e.getMessage());
                driver.switchTo().defaultContent();
                success = false;
            }
            
            log.info("DONE"  + (success ?  "" : " with errors") );
            return success;
        }
        
        public boolean clickElementDelay(WebElement elementToClick, String elementName, int x, int y) throws IOException {
        	boolean success = true;
        	log.info( "Clicking << "+elementName+" >> button...");

            try{
                new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(elementToClick));
                MouseMethods.ClickAndHold(driver, elementToClick, x, y);
            }catch(Exception e){
            	log.info(e.getMessage());
                driver.switchTo().defaultContent();
                success = false;
            }
            
            log.info("DONE"  + (success ?  "" : " with errors") );
            return success;
        }
        
        // MGB 4/5/2014
        public boolean dblClickElement(WebElement elementToClick, String elementName) throws IOException {
        	boolean success = true;
        	log.info( "Clicking << "+elementName+" >> button...");

            try{
                new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(elementToClick));
                MouseMethods.HoverOverDblClick(driver, elementToClick, 20, 20);
                GeneralMethods.delay(AutomationSettings.getClickDelay()); 
            }catch(Exception e){
            	log.info(e.getMessage());
                driver.switchTo().defaultContent();
                success = false;
            }
            
            log.info("DONE"  + (success ?  "" : " with errors") );
            return success;
        }
        public boolean setTextField(WebElement elementToPopulate, String elementName, String textToInput) throws IOException {
        	boolean success = true;
        	log.info("Populating << "+elementName+" >> field: "+textToInput);
            try{
                new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(elementToPopulate));
                elementToPopulate.clear(); // MGB 4/28/2014
                //elementToPopulate.sendKeys(textToInput+Keys.ENTER);
                elementToPopulate.sendKeys(textToInput);
            }catch(Exception e){
            	log.info(e.getMessage());
            	success = false;
            }
            
            log.info("DONE"  + (success ?  "" : " with errors") );
            return success;
        }
 
        public boolean setDateField(WebElement elementToPopulate, String elementName, String textToInput) throws IOException {
        	boolean success = true;
        	String formattedDate = textToInput.replace("/", "");
        	log.info("Populating << "+elementName+" >> field: "+textToInput);
            try{
                new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(elementToPopulate));
                //elementToPopulate.sendKeys(textToInput+Keys.TAB);
                elementToPopulate.sendKeys(formattedDate+Keys.TAB);
            }catch(Exception e){
            	log.info(e.getMessage());
            	success = false;
            }
            
            log.info("DONE"  + (success ?  "" : " with errors") );
            return success;
        }
               
        public boolean selectDropdownItem(List<WebElement> elementList, String elementName, String value) throws IOException {        
        	boolean success = false;
        	String valueLC = value.toLowerCase().trim();
        	log.info("Selecting item from << "+elementName+" >> dropdown: "+value);
        	
        	try{
            	for (WebElement element : elementList)
            	{
            		//GeneralMethods.delay(1000);
            		//GenHealth Central Hospital (GCH)
         
            		
            		if (element.getText().toLowerCase().trim().equals(valueLC)) {
            		//if(element.getText().toLowerCase().trim().equals("genhealth central hospital (gch)")){
            			
            			success = this.clickElement(element, elementName+":"+value);
            			break;
            		}
            		
            	}
            }catch(Exception e){
               	log.info(e.getMessage());
                driver.switchTo().defaultContent();
                success = false;
            }            
            
            log.info("DONE"  + (success ?  "" : " with errors") );
            return success;
        }
        
        public boolean selectDropdownItemContains(List<WebElement> elementList, String elementName, String value) throws IOException {        
        	boolean success = false;
        	String valueLC = value.toLowerCase();
        	log.info("Selecting item from << "+elementName+" >> dropdown that contains '"+value+"'");
        	
            try{
            	for (WebElement element : elementList)
            	{
            		if (element.getText().toLowerCase().contains(valueLC)) {
            			this.clickElement(element, elementName+":"+value);
            			success = true;
            			break;
            		}
            	}
            }catch(Exception e){
               	log.info(e.getMessage());
                driver.switchTo().defaultContent();
                success = false;
            }            
            
            log.info("DONE"  + (success ?  "" : " with errors") );
            return success;
        }
        
        public boolean clickAndSelectDropdown(WebElement element, List<WebElement> list, String description, String value) throws IOException{
        	boolean success = false;
        	log.info("Click << "+description+" >> and select dropdown value: "+value);
        	this.clickElement(element, description);
        	GeneralMethods.delay(NG7TestCase.clickDelay);
        	// Wait for spinner to disappear
        	boolean elementVisible = GeneralMethods.waitUntilNotVisible(this.loadingSpinner, "loading spinner", 10000);
        	
        	System.out.println("Click << "+description+" >> and select dropdown value: "+value);
        	GeneralMethods.delay(NG7TestCase.clickDelay);
        	return this.selectDropdownItem(list, description, value);
        }
        
        public boolean expandDropdownItem(List<WebElement> elementList, List<WebElement> nodesList, String dropdownName, String expansionCssSelector) throws IOException {  
        	boolean success = false;
        	log.info("Expanding item << "+dropdownName+" >>");
        	try{
        		// MGB 6/26/2014: updated to check if node is expanded
        		//for (WebElement element : elementList)
        		WebElement element;
        		WebElement node;
        		for(int i=0; i < elementList.size(); i++) {
        			element = elementList.get(i);
        			node = nodesList.get(i);
        			//System.out.println("2- element list item "+element.getText());
            		if (element.getText().contains(dropdownName)) {
            			if (node.getAttribute("class").contains("hide")){
            				WebElement expansionButton = GeneralMethods.FindElementInObjHierarchy(element, expansionCssSelector);
            				GeneralMethods.ClickButton(expansionButton);
            			}
            			success = true;
            			break;
            		}
        		}
        		return success;
        	} catch(Exception e){
        		log.info(e.getMessage());
        		driver.switchTo().defaultContent();
        		success = false;
        	}            
        
        	log.info("DONE"  + (success ?  "" : " with errors") );
        	return success;
        }
        
        public String getRandomDropdownItemText(List<WebElement> choices, String elementName, String dropDownItemCssSelector){
        	String itemTextValue = "";
        	log.info("Get the text value of random item from << "+elementName+">> dropdown list");
        	try{
        		int selection = GeneralMethods.PickRandomNumber(choices.size()-1);
				itemTextValue = choices.get(selection).getText().equals("Select") ? choices.get(selection+1).getText() : choices.get(selection).getText();
				return itemTextValue;
        	}catch(Exception e){
               	log.info(e.getMessage());
               	return itemTextValue;
            }
        }

        public String getTextFieldValue(WebElement elementToEcho, String elementName) throws IOException {
        	log.info("Get value for << "+elementName+" >> field...");
            String output = "";
            try{
                new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(elementToEcho));
                //output = elementToEcho.getText(); // MGB 4/5/2014
                output = elementToEcho.getAttribute("value");
                log.info("Read: "+output);
                
            }catch(StaleElementReferenceException sere){
                log.info("StaleElementReferenceException exception caught... retrying...");
                driver.switchTo().defaultContent();
                output = elementToEcho.getText();
                log.info("Read: "+output);
            }
            log.info("DONE");
            return output;
        }
        
        public String getChosenFieldValue(WebElement elementToEcho, String elementName) throws IOException {
        	log.info("Get value for << "+elementName+" >> field...");
            String output = "";
            try{
                new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(elementToEcho));
                output = elementToEcho.getText();
                log.info("Read: "+output);
            }catch(StaleElementReferenceException sere){
                log.info("StaleElementReferenceException exception caught... retrying...");
                driver.switchTo().defaultContent();
                output = elementToEcho.getText();
                log.info("Read: "+output);
            }
            log.info("DONE");
            return output;
        }
        
        public String getDropdownFieldValue(WebElement elementToEcho, String elementName) throws IOException {
        	log.info("Get value for << "+elementName+" >> field...");
            String output = "";
            try{
                new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(elementToEcho));
                clickElement(elementToEcho, elementName);
                //output = elementToEcho.getText();
                output = elementToEcho.getAttribute("value");
                clickElement(elementToEcho, elementName);
                //output = output.replace("SelectSelect", "");
                log.info("Read: "+output);
            }catch(StaleElementReferenceException sere){
                log.info("StaleElementReferenceException exception caught... retrying...");
                driver.switchTo().defaultContent();
                output = elementToEcho.getText();
                log.info("Read: "+output);
            }
            log.info("DONE");
            return output;
        }
        
        public boolean textFieldValidate(WebElement element, String elementName, String value) throws IOException{
        	
        	return setTextField(element, elementName, value) ? getTextFieldValue(element, elementName).equals(value) : false; 
        }
        
        public boolean dateFieldValidate(WebElement element, String elementName, String value) throws IOException{
        	
        	return setDateField(element, elementName, value) ? getTextFieldValue(element, elementName).equals(value) : false; 
        }

        public boolean dropDownFieldValidate(WebElement element, List<WebElement> elementList, String elementName, String value) throws IOException{
        	
        	return selectDropdownItem(elementList, elementName, value) ? getDropdownFieldValue(element, elementName).equals(value) : false; 
        }
        
        public boolean setTextFieldByLabel (String labelName, String cssSelector, String fieldDescription, List<WebElement> labelList, String fieldValue) throws IOException, InterruptedException {
        	try{
        		WebElement field = null;
        		for(WebElement label : labelList)
        			if (label.getText().contains(labelName)) {
        				field = label.findElement(By.cssSelector("label >"+cssSelector));
        				this.setTextField(field, fieldDescription, fieldValue);
        				return true;
        			}
        		return false;
        	}
        	catch (Exception e) {return false;}    
        }
        
        public boolean setPullDownFieldByLabel (String labelName, String cssSelector, String fieldDescription, List<WebElement> fieldsList, String fieldValue, boolean random) throws IOException, InterruptedException {
        	try{
        		for(WebElement field : fieldsList)
        			if (field.getText().contains(labelName)) {
        				this.clickElement(field, fieldDescription);
        				List<WebElement> values = GeneralMethods.FindElementsInObjHierarchy(field, cssSelector);
        				if (random == true) fieldValue = this.getRandomDropdownItemText(values, fieldDescription+" List", cssSelector); 
        				this.selectDropdownItem(values, fieldDescription, fieldValue);
        				return this.clickElement(field, fieldDescription);
        			}
        		return false;
        	} catch (Exception e) { return false; }
        }
        /*        
        public boolean setPullDownFieldByLabel_wip (String labelName, String fieldValue, boolean random) throws IOException, InterruptedException {
        	try{
        		WebElement field = GeneralMethods.GetFieldByLabelNameFromDriver(driver, cssValueDataRow, cssValueLabel, labelName, cssValueField);
        		
        		for( : fieldsList)
        			if (field.getText().contains(labelName)) {
        				this.clickElement(field, fieldDescription);
        				List<WebElement> values = GeneralMethods.FindElementsInObjHierarchy(field, cssSelector);
        				if (random == true) fieldValue = this.getRandomDropdownItemText(values, fieldDescription+" List", cssSelector); 
        				this.selectDropdownItem(values, fieldDescription, fieldValue);
        				return this.clickElement(field, fieldDescription);
        			}
        		return false;
        	} catch (Exception e) { return false; }
        }
        */
        public boolean clickCloseXButton (WebElement form, String type, String description) throws IOException, InterruptedException {
        	try{
        		WebElement closeButton;
        		if (type.equals("modal")) closeButton = GeneralMethods.FindElementInObjHierarchy(form, "div[class='modalCloseX']");
        		else closeButton = GeneralMethods.FindElementInObjHierarchy(form, "div[class='modalCloseX']");
        		return GeneralMethods.ClickButton(closeButton);
        	} catch (Exception e) { return false; }
        }
        
        // click and 
        // wait for WaitForElementPresent 
        public void clickAndWaitForElement(WebElement elementToClick, String elementName, WebElement nextElement) throws IOException {
        	
        	this.clickElement(elementToClick, elementName);
            new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(nextElement));        	
        	
        	//WaitForElementPresent
        //visibilityOfElementLocated
        }
        
        public boolean setPicklistValue(WebElement field, List<WebElement> fieldList, String description, String value, String optionCss, boolean random) throws IOException, InterruptedException {
        	this.clickElementDelay(field, description, 20, 5);
        	if (random == true) value = this.getRandomDropdownItemText(fieldList, description, optionCss); // WebElement dropdownField, String elementName, String dropDownItemCssSelector
            this.selectDropdownItem(fieldList, description, value); 
            return this.clickElement(field, description);
        }        

        public boolean setPicklistValueByCss(WebElement field, String description, String value, String optionCss, boolean random) throws IOException, InterruptedException {
        	this.clickElementDelay(field, description, 20, 5);
        	List<WebElement> list = GeneralMethods.FindElementsInObjHierarchy(field, optionCss);
        	this.clickElementDelay(field, description, 20, 5);
        	return this.setPicklistValue(field, list, description, value, optionCss, random);
        }  
        
        public boolean clickButtonFromPulldown(WebElement field, List<WebElement> fieldList, String description, String value, String optionCss, boolean random) throws IOException, InterruptedException {
        	this.clickElementDelay(field, description, 20, 5);
        	if (random == true) value = this.getRandomDropdownItemText(fieldList, description, optionCss); // WebElement dropdownField, String elementName, String dropDownItemCssSelector
            return this.selectDropdownItem(fieldList, description, value); 
        }        

        public boolean hoverAway(WebElement element, int offsetX, int offsetY) throws InterruptedException {
        	try{
        		return MouseMethods.HoverOver(this.driver, element, offsetX, offsetY);
        	} catch(Exception e) { return false; }
        }
        
        public boolean hoverAwayAndClick(WebElement element) throws InterruptedException {
        	try{
        		return MouseMethods.HoverOverClick(this.driver, element, element.getSize().getWidth() + 5, 0);
        	} catch(Exception e) { return false; }
        }
        
        public String getText(WebElement elementToEcho, String elementName) throws IOException {
        	log.info("Get value for << "+elementName+" >> field...");
        	String output = "";
            try{
                new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(elementToEcho));
                //output = elementToEcho.getText(); // MGB 4/5/2014
                output = elementToEcho.getText();
                log.info("Read: "+output);
                
            }catch(StaleElementReferenceException sere){
                log.info("StaleElementReferenceException exception caught... retrying...");
                driver.switchTo().defaultContent();
                output = elementToEcho.getAttribute("value");
                log.info("Read: "+output);
            }
            log.info("DONE");
            return output;
        }

        public boolean checkErrorPage() throws CustomException, IOException{
           boolean exception = false;
           boolean exception2 = false;
           boolean exception3 = false;
     		  
           try{    
        	   	if (! GeneralMethods.checkElementIsNotVisible(driver, exceptionPopUp))
        	   		exception = true;
        	   	//else if (! GeneralMethods.checkElementIsNotVisible(driver, exceptionPopUp2))
        	   	//	exception2 = true;
        	   	//else if (! GeneralMethods.checkElementIsNotVisible(driver, exceptionPopUp3))
        	   	//	exception3 = true;
       				 
        	   	if (exception)
        	   		throw new CustomException();
   	
              return false;   
           } catch(CustomException e){
           	
           	String artifactMsg = NG7TestCase.testName + ": GenericPage/checkErrorPage EXCEPTION DETECTED %%attempt%%";
           	if (exception && exceptionPopUp.getText().contains("503")) artifactMsg = "STATUS 503 exception detected; " + artifactMsg;
           	if (exception && exceptionPopUp.getText().contains("505")) artifactMsg = "STATUS 505 exception detected; " + artifactMsg;
           	
           	boolean clickedExceptionButton = false;
           	
           	if (GeneralMethods.checkIsDisplayed(unlockButton)) 
           		clickedExceptionButton = GeneralMethods.ClickButton(unlockButton);
           	
           	//else if (GeneralMethods.checkIsDisplayed(unlockButton2)) 
           	//	clickedExceptionButton = GeneralMethods.ClickButton(unlockButton2);
           	
           	if (clickedExceptionButton) 
           		artifactMsg = artifactMsg.replace("%%attempt%%", "attempted recovery");
           	else
           		artifactMsg = artifactMsg.replace("%%attempt%%", "could not attempt recovery");
           		
           	Artifact.VerifyWriteToArtifactS(NG7TestCase.artifactObj, artifactMsg, "Warning"); 
           	Artifact.ReportDoNotDeliverFail("Warning", artifactMsg);
           	return clickedExceptionButton;
           }
        }
        
        public boolean checkLoadingSpinner() throws CustomException{
        	try{
        		if (! GeneralMethods.checkElementIsNotVisible(driver, loadingSpinner)) throw new CustomException();
        		return false;
        	} catch(Exception CustomException){
               	CustomException.getMessage();
               	int timeout = 15;
               	if (loadingSpinner.isDisplayed() && timeout > 0){
               		GeneralMethods.delay(1000);
               		timeout = timeout - 1;
               	}
               	
               	if (loadingSpinner.isDisplayed()){ 
               		Artifact.ReportDoNotDeliverFail("Warning", NG7TestCase.testName+": loading spinner active for more than 5 seconds");
               		Artifact.VerifyWriteToArtifactS(NG7TestCase.artifactObj, "Loading spinner active for more than 5 seconds", "Warning");
               		return true;
               	}
               	Artifact.VerifyWriteToArtifactS(NG7TestCase.artifactObj, "GenericPage/checkLoadingspinner: Response after the previous step was slower but spinner went away before 5 seconds", "Warning");
               	return false;
               }
        }
        
        // Experimental
        public WebElement findVisibleElement(WebElement obj, String cssData) throws Exception{
        	try{
        		List<WebElement> elements = GeneralMethods.FindElementsInObjHierarchy(obj, cssData); 
        		for (WebElement element : elements)
        			if (element.isDisplayed()) return element;
        		System.out.println("GenericPage.findElement: could not find visible element "+cssData);
                return null;   
             } catch(Exception e){
            	 System.out.println("GenericPage.findElement: Exception thrown when looking for element "+cssData);
             	return null;
             }
          }
        
        public WebElement findElementInList(String cssData, String itemText, List<WebElement> list, String listDescription) throws Exception{
        	try{
           		for (WebElement element : list){
        			try{
        				if ((! cssData.equals("")) && (element.getAttribute("ng-click").contains(cssData))) 
        					if (element.isDisplayed()) return element;
        			} catch (Exception e){
        				if (element.getText().contains(itemText)) return element;
        			}
        		}
        		System.out.println("GenericPage.findElementInList: could not find visible element "+itemText+" in list "+listDescription);
                return null;			
        	} catch (Exception e) {
        		System.out.println("GenericPage.findElementInList: Exception thrown when attempting to find element "+itemText+" in list "+listDescription+" "+e.getMessage());
                return null;
        	}
        }
        
        public WebElement findVisibleButton(WebElement obj, String cssData, String buttonDescription) throws Exception{
        	try{
        		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        		WebElement button = null;
        		
        		List<WebElement> list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listButtonsLightGrayButtonGroupCss);
        		if (list != null){
        			for (WebElement element : list) // look for button in group light gray button
        				if (element.getAttribute("ng-click")!=null && element.isDisplayed())
        				if (element.getAttribute("ng-click").contains(cssData)) {
        					button = element;
        					break;
        				}
        		}
        
        		if (button == null){
        			String cssDataLC = cssData.toLowerCase();
        			list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listButtonsClassBtnCss);
        			if (list != null){
        				for (WebElement element : list) // look for button in group class ^= btn
        					if (element.getAttribute("value")!=null && element.isDisplayed())
        						if (element.getAttribute("value").toLowerCase().equals(cssDataLC)) {
        							button = element;
        							break;
        					}
        			}
        		}
        		
        		if (button == null){
        			list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listButtonsSpanCss);
        			if (list != null){
        				for (WebElement element : list) // look for button of tag span
        					if (element.getAttribute("ng-click")!=null && element.isDisplayed())
        						if (element.getAttribute("ng-click").contains(cssData)) {
        							button = element;
        							break;
        						}
        			}
        		}
        		
        		if (button == null){
            		list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listButtonsClassAddCss);
            		if (list != null){
            			for (WebElement element : list) // look for button of tag span
            				if (element.getAttribute("ng-click")!=null && element.isDisplayed())
            					if (element.getAttribute("ng-click").contains(cssData)) {
        							button = element;
        							break;
        						}
            		}
        		}
        		
        		driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.SECONDS);
        		if (button == null) System.out.println("GenericPage.findVisibleButton: could not find button "+buttonDescription);		
        		return button;
        	} catch (Exception e){
        		System.out.println("GenericPage.findVisibleButton: Exception thrown when attempting to find button "+buttonDescription+" "+e.getMessage());
                return null;
        	}
        }
        
        public WebElement findVisibleTab(WebElement obj, String cssData, String tabDescription) throws Exception{
        	try{
        		String cssDataLC = cssData.toLowerCase();
        		List<WebElement> list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listTabsHeaderBlockCss);
        		if (list != null){
            		for (WebElement element : list) // look for tab in header tabs
            			if (element.getAttribute("ng-click")!=null && element.getAttribute("ng-click").contains(cssData)) return element;
            			else if (element.getText().contains(cssData)) return element;
        		}
        		
        		list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listTabsNavCss);
        		if (list != null){
            		for (WebElement element : list) // look for tab in nav tabs
            			if (element.getAttribute("ng-click")!=null && element.getAttribute("ng-click").contains(cssData)) return element;
            			else if (element.getText().contains(cssData)) return element;
        		}
        		
        		list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listTabsInfoListCss);
        		if (list != null){
            		for (WebElement element : list) // look for tab in nav tabs
            			if (element.getAttribute("ng-click")!=null && element.getAttribute("ng-click").contains(cssData)) return element;
            			else if (element.getText().contains(cssData)) return element;
        		}
        		
        		System.out.println("GenericPage.findVisibleTab: could not find tab "+tabDescription);		
        		return null;
        	} catch (Exception e){
        		System.out.println("GenericPage.findVisibleTab: Exception thrown when attempting to find tab "+tabDescription+" "+e.getMessage());
                return null;
        	}
        }
        
        public WebElement findVisibleWidget(WebElement obj, String cssData, String description) throws Exception {
        	try{
        		String cssDataLC = cssData.toLowerCase();
        		List<WebElement> list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listWidgetsDashboardWidgetCss);
        		if (list != null){
            		for (WebElement element : list) // look for widget in class^=dashboard-widget
            			if (element.getText().contains(cssData)) return element;
        		}
        		        		
        		System.out.println("GenericPage.findVisibleWidget: could not find widget "+description);		
        		return null;
        	} catch (Exception e){
        		System.out.println("GenericPage.findVisibleWidget: Exception thrown when attempting to find widget "+description+" "+e.getMessage());
                return null;
        	}
        }
        
        public WebElement findVisibleDropdownField(WebElement obj, String cssData, String itemDescription) throws Exception{
        	try{
        		String cssDataLC = cssData.toLowerCase();
        		List<WebElement> list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listDropdownFieldsSelectCss);
        		if (list != null){
            		for (WebElement element : list) // look for item in select
            			if (element.getAttribute("id").toLowerCase().contains(cssDataLC)) 
            				return element;
        		}
        		
        		list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listDropdownFieldsChosenCss);
        		if (list != null){
            		for (WebElement element : list) // look for item in chosen
            			if (element.getAttribute("id").toLowerCase().contains(cssDataLC) ||
            				element.getAttribute("ng-model").toLowerCase().contains(cssDataLC))
            				return element;
        		}
        	
        		System.out.println("GenericPage.findVisibleDropdownField: could not find item "+itemDescription);		
        		return null;
        	} catch (Exception e){
        		System.out.println("GenericPage.findVisibleDropdownField: Exception thrown when attempting to find item "+itemDescription+" "+e.getMessage());
                return null;
        	}
        }
        
        public WebElement findVisibleDropdownListItem(WebElement obj, String cssData, String itemDescription) throws Exception{
        	try{
        		List<WebElement> list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listItemsLiCss);
        		if (list != null){
            		for (WebElement element : list) // look for item in nav dropdown
        			if (element.getText().contains(cssData)) 
        				if (element.isDisplayed()) return element;
        		}
        		
        		list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listItemsOptionCss);
        		if (list != null){
            		for (WebElement element : list) // look for item in option
            			if (element.getText().contains(cssData)) 
            				if (element.isDisplayed()) return element;
        		}
        		
        		System.out.println("GenericPage.findVisibleDropdownListItem: could not find item "+itemDescription);		
        		return null;
        	} catch (Exception e){
        		System.out.println("GenericPage.findVisibleDropdownListItem: Exception thrown when attempting to find item "+itemDescription+" "+e.getMessage());
                return null;
        	}
        }
        
        public WebElement findVisibleDateField(WebElement obj, String cssData, String itemDescription) throws Exception{
        	try{
        		List<WebElement> list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listDateFieldHasDatePickerCss);  
        		if (list != null){
        			for (WebElement element : list) // look for item in class*=hasDatePickerif (element.getAttribute("name").contains(cssData)) 
        				if (element.getAttribute("name").contains(cssData)) return element;
        		}
        	
        		System.out.println("GenericPage.findVisibleDateField: could not find item "+itemDescription);		
        		return null;
        	} catch (Exception e){
        		System.out.println("GenericPage.findVisibleDateField: Exception thrown when attempting to set item "+itemDescription+" "+e.getMessage());
                return null;
        	}
        }
        
        public WebElement findVisibleTextField(WebElement obj, String cssData, String itemDescription) throws Exception{
        	try{
        		List<WebElement> list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listTextFieldTypeTextCss);  
        		if (list != null){
        		for (WebElement element : list) // look for item in type=text
        			if (element.getAttribute("name").contains(cssData)) 
        				if (element.isDisplayed()) return element;
        		}
        		
        		System.out.println("GenericPage.findVisibleTextField: could not find item "+itemDescription);		
        		return null;
        	} catch (Exception e){
        		System.out.println("GenericPage.findVisibleTextField: Exception thrown when attempting to set item "+itemDescription+" "+e.getMessage());
                return null;
        	}
        }
        
        public WebElement findVisibleRadioButton(WebElement obj, String cssData, String itemDescription) throws Exception{
        	try{
        		List<WebElement> list = GeneralMethods.FindElementsInObjHierarchy(obj, this.listRadioButtonsTypeRadioCss);  
        		if (list != null){
        			for (WebElement element : list) // look for item in type='radio'
        			if (element.getAttribute("id").contains(cssData)) 
        				return element;
        		}
        		
        		System.out.println("GenericPage.findVisibleRadioButton: could not find item "+itemDescription);		
        		return null;
        	} catch (Exception e){
        		System.out.println("GenericPage.findVisibleRadioButton: Exception thrown when attempting to find item "+itemDescription+" "+e.getMessage());
                return null;
        	}
        }
        
        public WebElement findVisibleSearchField(WebElement obj, String cssData, String itemDescription) throws Exception{
        	try{
        		List<WebElement> searchFieldsList = GeneralMethods.FindElementsInObjHierarchy(obj, this.listSearchFieldTypeSearchCss);
        		if (searchFieldsList != null){
        			for (WebElement element : searchFieldsList) // look for item in type='search'
        			if (element.getAttribute("ng-model").contains(cssData)) 
        				if (element.isDisplayed()) return element;
        		}
        		
        		System.out.println("GenericPage.findVisibleSearchField: could not find item "+itemDescription);		
        		return null;
        	} catch (Exception e){
        		System.out.println("GenericPage.findVisibleSearchField: Exception thrown when attempting to find item "+itemDescription+" "+e.getMessage());
                return null;
        	}
        }
        
        public boolean pickDropDownValue(WebElement obj, String cssDataField, String cssDataValue, String valueDescription) throws Exception{
        	try{
        		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        		boolean success = false;
        		WebElement field = this.findVisibleDropdownField(obj, cssDataField, "");
        		success = this.clickElement(field, "dropdown field");
        		success &= this.clickElement(this.findVisibleDropdownListItem(field, cssDataValue, ""), valueDescription);
        		this.clickElement(field, "dropdown field");
        		driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.SECONDS);
        		return success;
        	} catch (Exception e){
        		System.out.println("GenericPage.pickDropDownValue: Exception thrown when attempting to pick item "+valueDescription+" "+e.getMessage());
                return false;
        	}
        }
        
    	public boolean PopulateFieldByLabelName(WebElement form, String rowCss, String label, String fieldValue) throws Exception {
    		try{
    			driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
    			WebElement field = null;
    			int i;
    			int ii;
    			List<WebElement> rows = GeneralMethods.FindElementsInObjHierarchy(form, rowCss);

    			// find the object that has matching label
    			WebElement labelObj = null;
    			WebElement row = null;
    			
    			   for(WebElement r: rows){
    			      try{
    				     labelObj = GeneralMethods.FindElementInObjHierarchy(r, "label");
    				     if (labelObj.getText().equals(label)){
    				        row = r;
    					    break;
    				     }
    			      } catch (Exception e){
    			    	  continue;
    			      }
    			   }
    	
    			if (row == null) {
    				System.out.println("PopulateFieldByLabelName: could not find row with label "+label);
    				driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.MILLISECONDS);
    				return false;
    			}
    			
    			// Populate field if field is TypeAhead
    			String [] selectTypes = { "div[class^='ngxTypeAheadMultiple']", "input[type='text'][class^='ngxTypeAheadPgInput']" };
    			String [] optionTypes = { "b", "li", "option" };
    			for (i=0; i < selectTypes.length; i++) {
    				field = GeneralMethods.FindElementInObjHierarchy(row, selectTypes[i]);
    				if (field != null){
    					this.clickElement(field, "row field "+label);
    					GeneralMethods.delay(1000);
    					List<WebElement> options = null;
    					for(ii=0; ii < optionTypes.length; ii++){
    						options = GeneralMethods.FindElementsInObjHierarchy(form, optionTypes[ii]);
    						if (options.size() != 0) break;
    					}
    					String optionVal = this.getRandomDropdownItemText(options, "Smart form select field", optionTypes[ii]);
    					System.out.println("* * * * * * * options.size() = "+options.size()+"; optionVal = "+optionVal);
    					boolean success = this.selectDropdownItem(options, "SmartForm TypeAhead field "+label, optionVal);
    					MouseMethods.HoverOverClick(this.driver, form, -25, 0); // temp workaround; click away
    					driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.MILLISECONDS);
    					return success;
    				}
    			}
    			
    			// MGB 3/9/2015: added support for selectListWrapper
    			// Populate field if it is a select field
    			String type = "";
    			String [] selectTypes2 = { "select[class^='customSelect']", "div[class*='ngxSelect']"};
    			String [] optionTypes2 = { "option","li","b" };
    			for (i=0; i < selectTypes.length; i++) {
    				//field = GeneralMethods.GetFieldByLabelName(this.openSmartForm, "div[class^='control-group']", labelTag, labelText, selectTypes2[i]);
    				field = GeneralMethods.FindElementInObjHierarchy(row, selectTypes2[i]);
    				if (field != null){
    					this.clickElement(field, "Smart form select field "+label);
    					List<WebElement> options = null;
    					//GeneralMethods.setTimeout(0);
    					options = GeneralMethods.FindElementsInDriver(NG7TestCase.driver, "span[class*='selectListWrapper'] li");
    					type = "li";
    					//GeneralMethods.resetTimeout();
    					if (options.size() == 0){
    					   for(ii=0; ii < optionTypes.length; ii++){
    					      options = GeneralMethods.FindElementsInObjHierarchy(row, optionTypes[ii]);
    					      type = optionTypes[ii];
    					      if (options.size() != 0) break;
    					   }
    					}
    					String optionVal = fieldValue;
    					if (optionVal.equals("")) optionVal = this.getRandomDropdownItemText(options, "Smart form select field", type);
    					boolean success = this.selectDropdownItem(options, "SmartForm select field "+label, optionVal);
    					if (!selectTypes[i].contains("TypeAhead")) this.clickElement(field, "Smart form select field "+label);
    					driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.MILLISECONDS);
    					return success;
    				}
    			}
    			
    			// Populate field if field is a text field
    			String [] textTypes = { "input[type='password']", "input[type='text']", "textarea" };
    			for (i=0; i < textTypes.length; i++) {
    				//field = GeneralMethods.GetFieldByLabelName(this.openSmartForm, "div[class^='control-group']", labelTag, labelText, textTypes[i]);
    				field = GeneralMethods.FindElementInObjHierarchy(row, textTypes[i]);
    				if (field != null){
    					if (textTypes[i].contains("password")) fieldValue = "1Passw0rd_12345!";
    					driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.MILLISECONDS);
    					return this.setTextField(field, "SmartForm text field "+label, fieldValue);
    				}
    			}
    			/*
    			// Populate field if field is an input type
    			field = GeneralMethods.GetFieldByLabelName(this.openSmartForm, "div[class^='control-group']", labelTag, labelText, "input");
    			if (field != null){
    				if (field.getAttribute("class").contains("TypeAhead")){
    					//field.sendKeys(value);
    					super.clickElement(field, "Smart form select field "+labelText);
    					List<WebElement> options = GeneralMethods.FindElementsInObjHierarchy(this.openSmartForm, "li");
    					return super.selectDropdownItem(options, "SmartForm TypeAhead field "+labelText, options.get(0).getText());
    				}
    				else return super.setTextField(field, "SmartForm input text field "+labelText, value);
    			}
    			*/
    			// Populate field if field is a date picker
    			//field = GeneralMethods.GetFieldByLabelName(this.openSmartForm, "div[class^='control-group']", labelTag, labelText, "input[class*='hasDatepicker']");
    			field = GeneralMethods.FindElementInObjHierarchy(row, "input[class*='hasDatepicker']");
				
    			if (field != null){
    				fieldValue = GeneralMethods.GenerateRandomDate(1);
    				driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.MILLISECONDS);
    				return this.setDateField(field, "SmartForm date field", fieldValue);
    			}
    			
    			driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.MILLISECONDS);
    			return false;
    		} catch (Exception e) {
    			System.out.println("Exception while populating field " + label);
    			return false;
    		}
    	}

        ////
 }

