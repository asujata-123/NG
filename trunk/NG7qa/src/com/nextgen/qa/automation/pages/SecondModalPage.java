package com.nextgen.qa.automation.pages;

//import NG7Libraries.GeneralLib;
//import NG7Libraries.LoginLib;
//import NG7Libraries.NG7TestCase;

import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.ui.GenericPage;
//import com.nextgen.qa.automation.toolbox.AutomationSettings;









//import org.openqa.selenium.By;
//import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
 








import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class SecondModalPage extends GenericPage {

	 
	public SecondModalPage(WebDriver aDriver, String aLOG_FILE) {	
	   super(aDriver, aLOG_FILE);
	   this.driver = aDriver;
       this.LOG_FILE = aLOG_FILE;
	}
	
	// Web Elements
    @FindBy(how = How.CSS, using = "ng-include[class = 'secondary-modal']")
    public static List<WebElement> secondModalContainers;
	
    @FindBy(how = How.CSS, using = "div[class*='slidingModal']")
    public static List<WebElement> slidingModalContainers;
    
    @FindBy(how = How.CSS, using = "input[value = 'Save']")
    public static WebElement saveButton;
    
    @FindBy(how = How.CSS, using = "div[class='modalFooter']")
    public static List<WebElement> modalFooterList;
    
    @FindBy(how = How.CSS, using = "div[class = 'crudform dashboard']")
    public static WebElement modalCrudForm;
    
    
    // cssSelector data
    public static String buttonCssData = "input[type='button'][class^='btn']";
    
    //actions     
    public WebElement getVisibleModal() throws IOException, InterruptedException {
        try{
        	for(WebElement container : secondModalContainers)
        		if (container.isDisplayed()) return container;
        	return null;
        } catch (Exception e){
        	return null;
        }
    }
     
    public WebElement getVisibleModalFooter() throws IOException, InterruptedException {
        try{
        	for(WebElement footer : this.modalFooterList)
        		if (footer.isDisplayed()) return footer;
        	return null;
        } catch (Exception e){
        	return null;
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

    public boolean clickSave() throws IOException, InterruptedException {
        WebElement modal = this.getVisibleModal();
        WebElement saveButton = GeneralMethods.FindElementInObjHierarchy(modal, "input[value = 'Save']");
        return this.clickElement(saveButton, "New Appointment Save button");
    }

	public boolean ClickModalFooterButton (String buttonName) throws IOException, InterruptedException  {
		try{
			WebElement mod = this.getVisibleModal();
			WebElement button = GeneralMethods.FindElementInObjHierarchy(mod, buttonCssData+"[value='"+buttonName+"']");
			return GeneralMethods.ClickButton(button);
		} catch (Exception e) { return false; }
	}
	
	public boolean clickDismissConflictsSaveButton() throws IOException, InterruptedException {
		try{
			WebElement modal = this.getVisibleModal();
	        WebElement saveButton = GeneralMethods.FindElementInObjHierarchy(modal, "input[ng-click='dismissConflictsAndSave()']");
	        return this.clickElement(saveButton, "New Appointment Save button");
		} catch (Exception e) { return false; }
	}
	
	public boolean ClickSlidingModalButton (String buttonName) throws IOException, InterruptedException  {
		try{
			WebElement mod = this.getVisibleSlidingModal();
			WebElement button = GeneralMethods.FindElementInObjHierarchy(mod, buttonCssData+"[value='"+buttonName+"']");
			return GeneralMethods.ClickButton(button);
		} catch (Exception e) { return false; }
	}
}
