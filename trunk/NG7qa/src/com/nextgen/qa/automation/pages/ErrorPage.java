package com.nextgen.qa.automation.pages;



	import com.nextgen.qa.automation.toolbox.AutomationSettings;
import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.toolbox.NG7TestCase;
import com.nextgen.qa.automation.ui.GenericPage;
import com.nextgen.qa.automation.Issues.CustomException;

	import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

	import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

	public class ErrorPage extends GenericPage {

		public ErrorPage(WebDriver aDriver, String aLOG_FILE) {	
			   super(aDriver, aLOG_FILE);
			   this.driver = aDriver;
		       this.LOG_FILE = aLOG_FILE;
			}

		public int EVENT_DELAY = AutomationSettings.getEventDelay();
		
		// Web Elements
		//@FindBy(how = How.CSS, using = "div.p-title")
		@FindBy(xpath="//div/p[*= 'HTTP status code of: 504']")
	    public WebElement ErrorLockPage;
		
		@FindBy(how = How.CSS, using = "div[class^='lockScreenBox']")
	    public WebElement exceptionPopUp;
		
		@FindBy(how = How.CSS, using = "input[type='button'][value='Unlock']")
	    public WebElement unlockButton;
		
			    
	    //actions
	    
	  
	    public boolean checkErrorPage() throws IOException{
	    
	         	  try{
	                 
	         		 if (! GeneralMethods.checkElementIsNotVisible(driver, ErrorLockPage)) throw new CustomException();
	                 if (! GeneralMethods.checkElementIsNotVisible(driver, exceptionPopUp)) throw new CustomException();
	                return false;   
	             } catch(Exception CustomException){
	             	CustomException.getMessage();
	             	super.clickElement(unlockButton, "Unlock button");
	             	return false;
	             }
	    }
}
