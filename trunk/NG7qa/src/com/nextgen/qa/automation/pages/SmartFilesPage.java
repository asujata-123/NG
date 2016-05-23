package com.nextgen.qa.automation.pages;

import com.nextgen.qa.automation.toolbox.Artifact;
import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.toolbox.MouseMethods;
import com.nextgen.qa.automation.toolbox.NG7TestCase;
import com.nextgen.qa.automation.ui.GenericPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SmartFilesPage extends GenericPage {
	 
	public SmartFilesPage(WebDriver aDriver, String aLOG_FILE) {	
	   super(aDriver, aLOG_FILE);
	   this.driver = aDriver;
       this.LOG_FILE = aLOG_FILE;
	}
	
	// Web Elements
    @FindBy(how = How.CSS, using = "section[class^='smartfiles'][data-ng-controller='SmartfilesCtrl']")
    public WebElement smartfilesTabControl;
    
    @FindBy(how = How.CSS, using = "section[class^='smartfiles']")
    public WebElement openSmartfilesInterface;
    
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
    
    @FindBy(how = How.CSS, using = "div[class='gear']")
    public WebElement gearButton;   
    
    @FindBy(how = How.CSS, using = "div[ng-show='showOrgMenu'] li a")
    public List<WebElement> gearButtonList; 
    
    @FindBy(how = How.CSS, using = "form[name='orgForm']")
    public WebElement addNewOrgForm;
    
    @FindBy(how = How.CSS, using = "div[class*='spinner']")
    public WebElement loadingSpinner;
	
    @FindBy(how = How.CSS, using = "input[id = 'smartfileSearch']")
    public WebElement smartfileTypeSearchField;
    
    @FindBy(how = How.CSS, using = "input[id='smartItemSearch']")
    public WebElement smartItemsSearchField;
    
    @FindBy(how = How.CSS, using = "li[class ^= 'smartfilesRow']")
    public List<WebElement> smartfilesSearchResultsList;
    
    @FindBy(how = How.CSS, using = "li[data-ng-repeat='item in smartitems']")
    public List<WebElement> smartfilesList;
    
    @FindBy(how = How.CSS, using = "li[data-ng-repeat^='file in filteredFiles'][class^='smartfilesRow']")
    public List<WebElement> smartFormTypesList;
    
    @FindBy(how = How.CSS, using = "div[class='group smart-item']")
    public WebElement smartItemsFrame;
    
    @FindBy(how = How.CSS, using = "input[type='button'][class^='btn'][value = 'Add SmartItem']")
    public WebElement addSmartItemButton;
    
    @FindBy(how = How.CSS, using = "input[type='button'][class^='btn'][value = 'Import Rule Set']")
    public WebElement importRuleSetButton;
    
    @FindBy(how = How.CSS, using = "div[class='rule-xlsx-file-upload']")
    public WebElement importRuleSetForm;
    
    @FindBy(how = How.CSS, using = "div[class*='direct-indirect-togglebox']")
    public WebElement directIndirectSlider;
    
    //@FindBy(how = How.CSS, using = "form[name='smartForm']")
    @FindBy(how = How.CSS, using = "div[class^='slidingModal active-sliding-modal']")
    public WebElement openSmartForm;
    public String openSmartFormCssData = "div[class^='slidingModal active-sliding-modal']";

    @FindBy(how = How.CSS, using = "div[class^='slidingModal active-sliding-modal'] label")
    public List<WebElement> openSmartFormFieldNames;
    
    @FindBy(how = How.CSS, using = "div[class^='slidingModal active-sliding-modal'] input[type='button'][class^='btn'][value = 'Add']")
    public WebElement openSmartFormAddButton;    
    
    @FindBy(how = How.CSS, using = "div[class^='slidingModal active-sliding-modal'] input[type='button'][class^='btn'][value = 'Update']")
    public WebElement openSmartFormUpdateButton;    

    @FindBy(how = How.CSS, using = "div[class^='slidingModal active-sliding-modal'] input[type='button'][class^='btn'][value = 'Cancel']")
    public WebElement openSmartFormCancelButton;  
    
    // 7/9/2014 MGB : May be a temporary workaround... verifying with Anand and Nick if this button is correct
    @FindBy(how = How.CSS, using = "div[class^='slidingModal active-sliding-modal'] input[type='button'][class^='btn'][value = 'Add Existing']")
    public WebElement openSmartFormAddExistingButton; 
    ////
    
    @FindBy(how = How.CSS, using = "div[class^='slidingModal active-sliding-modal'] input[type='button'][class^='btn'][value = 'Delete']")
    public WebElement openSmartFormDeleteButton;
    
    @FindBy(how = How.CSS, using = "div[class^='slidingModal active-sliding-modal'] input[type='button'][class^='btn'][value = 'Remove']")
    public WebElement openSmartFormRemoveButton;
    
    @FindBy(how = How.CSS, using = "div[class^='slidingModal active-sliding-modal'] li[ng-click*='smartcategories']")
    public WebElement openSmartFormCategoriesTab; 
    
    @FindBy(how = How.CSS, using = "div[class^='slidingModal active-sliding-modal'] li[ng-click*='smartgeneral']")
    public WebElement openSmartFormGeneralTab;
    
    @FindBy(how = How.CSS, using = "div[class^='crudform-header']")
    public WebElement openSmartFormHeader;
    
    public String requiredFieldLabelsCSS = "label[class*='required']";
    public String fieldLabelsCSS = "label";
    public String requiredFieldsCSS = "class*='required'";
    public String requiredFieldIndicatorCSS = "i[class='required-ind']";
    
    //@FindBy(how = How.CSS, using = "div[class^='slidingModal active-sliding-modal'] input[name='name'][type='text']")
    //public static WebElement cancelationReasonName;
    
    // SmartForm fields cssSelector data
    public static String[] cancelationReasonName = {"div[class^='crudform-header']", "label", "Name", "input", "text", "AppointmentCancelReason"};
    public static String[] apptTypeAppointmentLabel = {"div[class^='crudform-header']", "label", "Appointment Label", "input", "text", "Appointment Type"};
    public static String[] apptTypeColor = {"div[class^='crudform-header']", "label", "Color", "div[class^='color-picker']", "select", "Appointment Type", "b"};
    public static String[] apptTypeDuration = {"div[class='control-group']", "label", "Duration", "input", "select", "Appointment Type", "option"};
    public static String[] apptTypeSendReminder = {"div[class='control-group']", "label", "Send Reminder", "input", "select[class^='customSelect']", "select", "Appointment Type", "option"};
    public static String[] apptTypeNote = {"div[class='control-group']", "label", "Note", "input", "textarea", "text", "Appointment Type"};
    public static String[] newOrgName = {"div[class='control-group']", "label", "Organization Name", "input", "text", "Create New Organization"};
    
    // cssSelector data
    public static String buttonCssData = "input[type='button'][class^='btn']";
    
    // Experimental
    public String buttonAddNeworg = "showOrgMenu";
    ////
    
    //actions    
    
    public boolean setTextFieldByLabel (String labelName, String fieldDescription, String fieldValue) throws IOException, InterruptedException {
        return super.setTextFieldByLabel(labelName, "input[type='text']", fieldDescription, this.openSmartFormFieldNames, fieldValue);  
    }

     
    public boolean setPulldownFieldByLabel (String labelName, String cssSelector, String fieldDescription, String fieldValue, boolean random) throws IOException, InterruptedException {
        return super.setPullDownFieldByLabel(labelName, cssSelector, fieldDescription, this.openSmartFormFieldNames, fieldValue, random);
    }    
    
    public boolean selectOrganization (String orgName)  throws IOException, InterruptedException {
    	//return super.selectDropdownItem(this.organizationList, "Organization", orgName);	
    	try {
    		GeneralMethods.delay(NG7TestCase.eventDelay);
    		boolean success = false;
    		String[] organizations = orgName.split(";");
    		WebElement org;
    		List<WebElement> nodesList = this.organizationParentNodes;
    		//int i = 0;
    		int i = 1;
    		for (int j=0; j < nodesList.size(); j++){
    			if (i == organizations.length) break;
    			org = nodesList.get(j);
    			if (org.getText().equals(organizations[i])) {
    				String classAttr = org.getAttribute("class");
    				if ( !classAttr.contains("open") ){
    					success = super.clickElement(org, org.getText());
    					nodesList = GeneralMethods.FindElementsInObjHierarchy(org, this.organizationNodesCss);
    					j=0;
    					i++;
    				}
    				continue;
    			}
    		}
    		return success;
    	} catch (Exception e) { return false; }
    }
    
    public boolean searchSmartFileType (String smartFileType) throws IOException, InterruptedException {
    	try{
    		WebElement field = smartfileTypeSearchField;
    		field.clear();
    		field.sendKeys(smartFileType);
    		return true;
    	} catch (Exception e) { return false; }		
    }
    
    public WebElement GetSmartFileType(WebDriver driver, String type) throws IOException, InterruptedException  {
   		try{
   			for(WebElement item : smartfilesSearchResultsList)
   				if (item.getText().contains(type)) return item;
   			return null;
    	} catch (Exception e) { return null; }
    }
    
	public WebElement FindSmartFile (String itemName) throws IOException, InterruptedException {
		//this.setTextField(this.smartItemsSearchField, "Smart items search field", itemName);
		try{
			this.smartItemsSearchField.clear();
			this.smartItemsSearchField.sendKeys(itemName);
			
			this.WaitForSmartFilesList();
			GeneralMethods.delay(500);
			List<WebElement> list = this.smartfilesList;
			
			// Attempt to find smartFile with by name
			for(WebElement item : list)
				
				if (item.isDisplayed() && item.findElement(By.cssSelector("div[class ^= 'item-header']")).getText().contains(itemName)) return item;
			
			// Attempt to find smartFile with by partial name
			String partialItemName = itemName.substring(0,4);
			this.smartItemsSearchField.clear();
			this.smartItemsSearchField.sendKeys(partialItemName);
			this.WaitForSmartFilesList();
			GeneralMethods.delay(500);
			list = this.smartfilesList;
			for(WebElement item : list)
				
				if (item.isDisplayed() && item.findElement(By.cssSelector("div[class ^= 'item-header']")).getText().contains(partialItemName)) return item;
			
			// Return null if no smartFile was found
			return null;
			
		} catch (Exception e) { return null; }
	}
    
	public boolean ClickSmartFormButton (String sfName, String buttonName) throws IOException, InterruptedException  {
		try{
			WebElement sf = FindSmartFile(sfName);
			WebElement button = GeneralMethods.FindElementInObjHierarchy(sf, buttonCssData+"[value='"+buttonName+"']");
			return GeneralMethods.ClickButton(button);
		} catch (Exception e) { return false; }
	}
	
	// Manage SmartForm fields
    public WebElement GetSmartFormField(String[] fieldData) throws IOException, InterruptedException  {
    	return GeneralMethods.GetFieldByLabelName(openSmartForm, fieldData[0], fieldData[1], fieldData[2], fieldData[3]);
    }
    
    public boolean PopulateSmartFormfield(String[] fieldData, String value, boolean random) throws IOException, InterruptedException {
    	WebElement field = this.GetSmartFormField(fieldData);
    	if (fieldData[4].equals("text")) return super.setTextField(field, fieldData[5]+" "+fieldData[2]+" field", value);
    	else if (fieldData[4].equals("select")) {
    		super.clickElementDelay(field, fieldData[5]+" "+fieldData[2]+" field", 20, 5);
    		List<WebElement> values = GeneralMethods.FindElementsInObjHierarchy(field, fieldData[6]);
    		String val = null;
        	if (random == true) val = super.getRandomDropdownItemText(values, fieldData[5]+" "+fieldData[2]+" field", fieldData[6]);
            super.selectDropdownItem(values, fieldData[5]+" "+fieldData[2]+" field", val);  
            return super.clickElement(field, fieldData[5]+" "+fieldData[2]+" field");
    	}
    	else return false;
    }
    
    public boolean ClickSmartFormTab(WebElement tab, String tabName) throws IOException, InterruptedException {
    	try{
    		if (GeneralMethods.checkIsDisplayed(tab) == false) return false;
    		this.clickElement(tab, tabName);
    		return tab.getAttribute("class").equals("tabActive") ? true : false;
    	} catch (Exception e) { return false; }
    }
    
	public WebElement CreateNewRule (String dataFile, String ruleName) throws IOException, InterruptedException {
		try{
			searchSmartFileType("Rules");
			GeneralMethods.ClickButton(GetSmartFileType(driver, "Rule Set"));
			GeneralMethods.ClickButton(addSmartItemButton);
			WebElement form = openSmartForm;
			
			BufferedReader file = GeneralMethods.OpenDataFile(dataFile); // get data file
			
			// populate the form fields as driven by the data file
			String[] line; 
			WebElement field = null;
			while((line = GeneralMethods.ReadDataFileLine(file)) != null) {
				if(line[0].equals(ruleName)){
				   field = (!line[1].equals("null") && !line[2].equals("null") && !line[3].equals("null")) ? 
						   GeneralMethods.GetFieldByLabelName(form, line[1], line[2], line[3], line[4]) :
							   (!line[1].equals("null") && line[2].equals("null") && line[3].equals("null")) ?
									   GeneralMethods.FindRelatedElement(driver, form, line[1], line[4]) :
										   GeneralMethods.FindElementInObjHierarchy(form, line[4]); 
				   if (line[4].contains("text")) super.setTextField(field, "Rule template text field", line[5]);
				   if (line[4].contains("select")) {
					   super.clickElement(field, "Rule form select field "+line[3]);
					   List<WebElement> options = GeneralMethods.FindElementsInObjHierarchy(field, "option");
					   super.selectDropdownItem(options, "Rule template select field", line[5]);
				   }
				} 
		   }	
		   GeneralMethods.ClickButton(openSmartFormAddButton);
		   WebElement rule = FindSmartFile(ruleName);
		   return rule;
		} catch (Exception e) {
			System.out.println("Exception thrown at CreateSmartFile " +e.getMessage());
			return null;
		}
	}
	
	public WebElement AddSmartFile_dataDriven (String dataFile, String sfTypeName, String uid) throws IOException, InterruptedException {
		try {
			searchSmartFileType(sfTypeName);
			GeneralMethods.ClickButton(GetSmartFileType(driver, sfTypeName));
			GeneralMethods.ClickButton(addSmartItemButton);
			WebElement form = openSmartForm;
			
			BufferedReader file = GeneralMethods.OpenDataFile(dataFile); // get data file
			if (file == null) return null;
			
			boolean foundType = false;
			String[] line; 
			WebElement field = null;
			String sfType = "";
			String header = "";
			String rowCSS = "";
			String labelCSS = "";
			String labelName = "";
			String tagCSS = "";
			String inputForm = "";
			String listItemTagCSS = "";
			String value = "";
			String sfName = "";
			
			// populate the form fields as driven by the data file
			while((line = GeneralMethods.ReadDataFileLine(file)) != null) {
				
				field = null;
				
				// Get the smart file type from the data line; exit while loop if the appt type has already been processed
				sfType = line[0];
				if (foundType && !sfType.equals(sfTypeName)) break; 
				
				// Get the rest of the data 
				header = line[1];
				rowCSS = line[2];
				labelCSS = line[3];
				labelName = line[4];
				tagCSS = line[5];
				inputForm = line[6];
				listItemTagCSS = line[7];
				value = line[8];
				sfName = line[9];
				
			
				// Manage smart file name
				value = value.replace("sfnuid", uid);
				sfName += uid;
			
				if(sfType.equals(sfTypeName)){
					foundType = true;
					field = GeneralMethods.GetFieldByLabelName(form, rowCSS, labelCSS, labelName, tagCSS);
					if (field == null) field = GeneralMethods.FindElementInObjHierarchy(form, tagCSS);
					super.clickElement(field, "SmartForm field "+labelName);
					if (inputForm.equals("text") || inputForm.equals("password")) {
						super.setTextField(field, "SmartForm text field", value);
					}
					else if (inputForm.equals("select")) {
						List<WebElement> options = GeneralMethods.FindElementsInObjHierarchy(field, "option");
						if (value.equals("null")) value = super.getRandomDropdownItemText(options, "SmartForm select field list", listItemTagCSS);
						super.selectDropdownItem(options, "Rule template select field", value);
						super.clickElement(field, "Smart form custom select field "+labelName);
					}
					else if (inputForm.equals("chosen")) super.setTextField(field, "SmartForm field", value+Keys.ENTER);
					else if (inputForm.equals("datePicker")) super.setDateField(field, "SmartForm date field", value);
				}
							 
			}	
			
			file.close();
			
			GeneralMethods.ClickButton(openSmartFormAddButton);
			GeneralMethods.delay(5000);
			this.setTextField(this.smartItemsSearchField, "Smart items search field", uid);
			WebElement createdSmartFile = FindSmartFile(sfName);
			return createdSmartFile;
		} catch (Exception e) { return null; }
	}

	public boolean ManageSmartFile_dataDriven (String requestedAction, String dataFile, String sfTypeName, String uid) throws IOException, InterruptedException {
		try {
			if (requestedAction.equals("add")) {
				searchSmartFileType(sfTypeName);
				GeneralMethods.ClickButton(GetSmartFileType(driver, sfTypeName));
			}
			
			WebElement form;
			
			BufferedReader file = GeneralMethods.OpenDataFile(dataFile); // get data file
			if (file == null) return false;
			
			boolean foundType = false;
			boolean success = false;
			String crumbs = "00";
			
			String[] line; 
			WebElement field = null;
			String sfType = "";
			String header = "";
			String rowCSS = "";
			String labelCSS = "";
			String labelName = "";
			String tagCSS = "";
			String inputForm = "";
			String listItemTagCSS = "";
			String value = "";
			String sfName = "";
			String action = "";
			
			// populate the form fields as driven by the data file
			while((line = GeneralMethods.ReadDataFileLine(file)) != null) {
				
				field = null;
				
				// Get the smart file type from the data line; exit while loop if the appt type has already been processed
				if (foundType && !line[0].equals(sfTypeName)) break; 
				
				// Move on if the type or the action are not what the method call is for
				if (!line[10].equals(requestedAction) || !line[0].equals(sfTypeName)) continue;
				
				// Get the data 
				sfType = line[0];
				header = line[1];
				rowCSS = line[2];
				labelCSS = line[3];
				labelName = line[4];
				tagCSS = line[5];
				inputForm = line[6];
				listItemTagCSS = line[7];
				value = line[8];
				sfName = line[9];
				action = line[10];
			
				
				// Manage smart file name and field data values
				value = value.replace(" sfnuid", uid);
				if (sfName.contains("nosfnuid")) sfName = sfName.replace(" nosfnuid", "");
				else sfName += uid;
				
				if (action.equals("remove")){
					crumbs += "R";
					foundType = true;
					if (WaitForSmartFilesList()==false) return false;
					this.ClickSmartFormButton(sfName, "Remove");
					form = openSmartForm;
					if (!GeneralMethods.checkIsDisplayed(form)){
						crumbs += "FNO";
						System.out.println("Crumb trail : " + crumbs);
						return false;
					}
					if (GeneralMethods.checkIsDisplayed(this.openSmartFormDeleteButton)){
						success = GeneralMethods.ClickButton(this.openSmartFormDeleteButton);
						crumbs += "01";
					}
					else {
						success = GeneralMethods.ClickButton(this.openSmartFormRemoveButton);
						crumbs += "02";
					}
						
					//return this.openSmartForm;
					System.out.println("Crumb trail : " +crumbs);
					return success;
				}
			
				if (action.equals("update")){
					crumbs += "U";
					foundType = true;
					if (WaitForSmartFilesList() == false) return false;
					String sfName_old;
					if (sfName.contains("sf_upd")) sfName_old = sfName.replace(" sf_upd", "");
					else sfName_old = sfName.replace("sfupd", "");
						
					this.ClickSmartFormButton(sfName_old, "Update");
					form = openSmartForm;
					if (!GeneralMethods.checkIsDisplayed(form)){
						crumbs += "FNO";
						System.out.println("Crumb trail : " + crumbs);
						return false;
					}
					field = GeneralMethods.GetFieldByLabelName(form, rowCSS, labelCSS, labelName, tagCSS);
					if (field != null) super.setTextField(field, "SmartForm text field", value);
					crumbs += "03";
				}	
				
				if (action.equals("add")){
					crumbs += "A";
					foundType = true;
					if (GeneralMethods.checkIsDisplayed(openSmartForm) == false) GeneralMethods.ClickButton(addSmartItemButton);
					form = openSmartForm;
					if (!GeneralMethods.checkIsDisplayed(form)){
						crumbs += "FNO";
						System.out.println("Crumb trail : " + crumbs);
						return false;
					}
					field = GeneralMethods.GetFieldByLabelName(form, rowCSS, labelCSS, labelName, tagCSS);
					if (field == null) field = GeneralMethods.FindElementInObjHierarchy(form, tagCSS);
					if (field != null) super.clickElement(field, "SmartForm field "+labelName);
					if (inputForm.equals("text") || inputForm.equals("password")){
						super.setTextField(field, "SmartForm text field", value);
						crumbs += "04";
					}
					else if (inputForm.equals("select") && (field != null)) {
						//List<WebElement> options = GeneralMethods.FindElementsInObjHierarchy(field, "option");
						super.clickElement(field, "Smart form custom select field "+labelName);
						List<WebElement> options;
						if (tagCSS.contains("TypeAhead")) options = GeneralMethods.FindElementsInObjHierarchy(this.openSmartForm, listItemTagCSS);
						else options = GeneralMethods.FindElementsInObjHierarchy(field, listItemTagCSS);
						GeneralMethods.delay(500);
						System.out.println("List size = " +options.size() + ", "+options.get(0).getText());
						if (value.equals("null")) value = super.getRandomDropdownItemText(options, "SmartForm select field list", listItemTagCSS);
						super.selectDropdownItem(options, "Rule template select field", value);
						if (!tagCSS.contains("TypeAhead")) super.clickElement(field, "Smart form custom select field "+labelName);
						crumbs += "05";
					}
					else if (inputForm.equals("chosen") && (field != null)){ 
						super.setTextField(field, "SmartForm field", value+Keys.ENTER);
						crumbs += "06";
					}
					else if (inputForm.equals("datePicker") && (field != null)) {
						super.setDateField(field, "SmartForm date field", value);
						crumbs += "07";
					}
					else if (inputForm.equals("typeahead") && (field != null)) {
						field.sendKeys(value);
						List<WebElement> options = GeneralMethods.FindElementsInObjHierarchy(this.openSmartForm, listItemTagCSS);
						System.out.println("List size = " +options.size());
						if (value.equals("null")) value = super.getRandomDropdownItemText(options, "SmartForm select field list", listItemTagCSS);
						super.selectDropdownItem(options, "Rule template select field", value);
						GeneralMethods.delay(1000);
						crumbs += "08";
					}
				}
			}	
			
			file.close();
			
			if (requestedAction.equals("update")) {
				success = GeneralMethods.ClickButton(openSmartFormUpdateButton);
				success &= (field != null);
				crumbs += "08";
			}
			else {
				if (GeneralMethods.checkIsDisplayed(openSmartFormAddExistingButton)) success = GeneralMethods.ClickButton(openSmartFormAddExistingButton);
				else success = GeneralMethods.ClickButton(openSmartFormAddButton);
				success &= (field != null);
				crumbs += "09";
				GeneralMethods.delay(1000);
				if (sfName.contains(uid)) this.setTextField(this.smartItemsSearchField, "Smart items search field", uid);
				else this.setTextField(this.smartItemsSearchField, "Smart items search field", sfName);
				WaitForSmartFilesList();
			}
			System.out.println("Crumb trail =" +crumbs);
			
			WebElement updatedSmartFile = FindSmartFile(sfName);
			return (updatedSmartFile != null);
			//return(this.smartfilesList.get(0) != null);
		} catch (Exception e) { return false; }
	}
	
	public boolean PopulateSmartFileFieldByLabelName(WebElement label, String value) throws Exception {
		try{
			
			WebElement field = null;
			int i;
			int ii;
			String labelText =  label.getText().replace(this.requiredFieldIndicatorCSS, "");
			String labelTag = label.getTagName();
			
			// Populate field if field is TypeAhead
			String [] selectTypes = { "div[class^='ngxTypeAheadMultiple']", "input[type='text'][class^='ngxTypeAheadPgInput']" };
			String [] optionTypes = { "b", "li", "option" };
			for (i=0; i < selectTypes.length; i++) {
				field = GeneralMethods.GetFieldByLabelName(this.openSmartForm, "div[class^='control-group']", labelTag, labelText, selectTypes[i]);
				if (field != null){
					super.clickElement(field, "Smart form select field "+labelText);
					GeneralMethods.delay(1000);
					List<WebElement> options = null;
					for(ii=0; ii < optionTypes.length; ii++){
						options = GeneralMethods.FindElementsInObjHierarchy(this.openSmartForm, optionTypes[ii]);
						if (options.size() != 0) break;
					}
					String optionVal = super.getRandomDropdownItemText(options, "Smart form select field", optionTypes[ii]);
					System.out.println("* * * * * * * options.size() = "+options.size()+"; optionVal = "+optionVal);
					boolean success = super.selectDropdownItem(options, "SmartForm TypeAhead field "+labelText, optionVal);
					MouseMethods.HoverOverClick(this.driver, this.openSmartForm, -25, 0); // temp workaround; click away
					return success;
				}
			}
			
			// Populate field if it is a select field
			String [] selectTypes2 = { "select[class^='customSelect']", "div[class^='ngxSelect']" };
			String [] optionTypes2 = { "option","li","b" };
			for (i=0; i < selectTypes.length; i++) {
				field = GeneralMethods.GetFieldByLabelName(this.openSmartForm, "div[class^='control-group']", labelTag, labelText, selectTypes2[i]);
				if (field != null){
					super.clickElement(field, "Smart form select field "+labelText);
					List<WebElement> options = null;
					for(ii=0; ii < optionTypes.length; ii++){
						options = GeneralMethods.FindElementsInObjHierarchy(this.openSmartForm, optionTypes[ii]);
						if (options.size() != 0) break;
					}
					String optionVal = super.getRandomDropdownItemText(options, "Smart form select field", optionTypes[ii]);
					boolean success = super.selectDropdownItem(options, "SmartForm select field "+labelText, optionVal);
					if (!selectTypes[i].contains("TypeAhead")) super.clickElement(field, "Smart form select field "+labelText);
					return success;
				}
			}
			
			// Populate field if field is a text field
			String [] textTypes = { "input[type='password']", "input[type='text']", "textarea" };
			for (i=0; i < textTypes.length; i++) {
				field = GeneralMethods.GetFieldByLabelName(this.openSmartForm, "div[class^='control-group']", labelTag, labelText, textTypes[i]);
				if (field != null){
					if (textTypes[i].contains("password")) value = "1Passw0rd_12345!";
					return super.setTextField(field, "SmartForm text field "+labelText, value);
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
			field = GeneralMethods.GetFieldByLabelName(this.openSmartForm, "div[class^='control-group']", labelTag, labelText, "input[class*='hasDatepicker']");
			if (field != null){
				value = GeneralMethods.GenerateRandomDate(1);
				return super.setDateField(field, "SmartForm date field", value);
			}
			
			return false;
		} catch (Exception e) {
			System.out.println("Exception while populating field " + label.getText());
			return false;
		}
	}

	public boolean openSmartType(String smartType) throws Exception{
		try{
			this.searchSmartFileType(smartType);
			GeneralMethods.ClickButton(GetSmartFileType(driver, smartType));
			return GeneralMethods.ClickButton(addSmartItemButton);
		} catch (Exception e) { return false; }
	}
	
	public boolean forceCloseSmartForm (String description) throws IOException, InterruptedException{
		return super.clickCloseXButton(this.openSmartForm, "modal", "Force close SmartForm"+description);
	}
    
	public boolean WaitForSmartFilesList() throws IOException, InterruptedException{
		this.driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean success = false;
		try{
			int timeout = 0;
			while(this.smartfilesList.size()==0 && (timeout < 6)){ 
				GeneralMethods.delay(1500);
				timeout++;
			}
			if (this.smartfilesList.size() > 0){
				GeneralMethods.delay(1500);
				success = true;
			}
			else success = false;
		} catch (Exception e) { success = false; }
		this.driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.SECONDS);
		return success;
	}
	
	public boolean WaitForSmartFileToClose() throws IOException, InterruptedException{
		this.driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean success = false;
		try{
			int timeout = 0;
			while(GeneralMethods.checkIsDisplayed(this.openSmartForm) && (timeout < 10)) {
				GeneralMethods.delay(1000);
				timeout++;
			}
			timeout = 0;
			while(!(!GeneralMethods.checkIsDisplayed(this.loadingSpinner)) && (timeout < 10)) {
				System.out.println("Spinner still visible");
				GeneralMethods.delay(1000);
				timeout++;
			}
			if (!GeneralMethods.checkIsDisplayed(this.openSmartForm) && (!GeneralMethods.checkIsDisplayed(this.loadingSpinner))) success = true;
			else success = false;
		} catch (Exception e) { success = false; }
		this.driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.SECONDS);
		return success;
	}
	
	public boolean CheckSmartFilesOrgListDisplayed(){
		try{
			boolean listDisplayed = false;
			listDisplayed = GeneralMethods.checkListIsDisplayed(this.smartFormTypesList);
			if (!listDisplayed)
				this.clickElement(this.organizationTopNode, "smart files list top node");
			listDisplayed = GeneralMethods.checkListIsDisplayed(this.smartFormTypesList);
			//this.clickElement(this.organizationTopNode, "smart files list top node");
			return listDisplayed;
		} catch (Exception e){
			System.out.println("CheckSmartFilesOrgListDisplayed: Exception thrown "+e.getMessage());
			return false;
		}
	}
	
	public boolean checkSmartFileClosed(){
		try{
			boolean closed = false;
			closed = GeneralMethods.checkElementIsNotVisible(this.driver, this.openSmartForm);
			for(int i=0; i < 6000; i++){
				GeneralMethods.delay(1000);
				closed = GeneralMethods.checkElementIsNotVisible(this.driver, this.openSmartForm);
				if (closed)
					break;
			}
			System.out.println("Did the smart file close? "+closed);
			return closed;
		} catch(Exception e) {
			System.out.println("checkSmartFileClosed: Exception thrown "+e.getMessage() );
			return false;
		}
	}
}
