/**
 * @author mbodoh
 *
 */

package com.nextgen.qa.automation.pages;


import com.nextgen.qa.automation.toolbox.AutomationSettings;
import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.toolbox.NG7TestCase;
import com.nextgen.qa.automation.ui.GenericPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert.*;

public class LoginPage extends GenericPage {

	 
	public LoginPage(WebDriver aDriver, String aLOG_FILE) {	
	   super(aDriver, aLOG_FILE);
	   this.driver = aDriver;
       this.LOG_FILE = aLOG_FILE;
	}
	
	// Web Elements
    //@FindBy(how = How.CSS, using = "input[class^='main-login-username']") // MGB 6/3/2014
	@FindBy(how = How.CSS, using = "input[type='text'][data-ng-model='username']")
    public WebElement usernameField;
	
    //@FindBy(how = How.CSS, using = "input[type='password']") // MGB 6/3/2014
	@FindBy(how = How.CSS, using = "input[type='password'][data-ng-model='password']")
    public WebElement passwordField;
     
    //@FindBy(how = How.CSS, using = "div[class='loginSubmit']")
    //public WebElement submitButton;
	
	@FindBy(how = How.CSS, using = "input[type='submit'][value='login']")
    public WebElement loginButton;
    
    //@FindBy(how = How.CSS, using = "form[class ^= 'front']") // MGB 6/3/2014
    @FindBy(how = How.CSS, using = "form[name='loginForm']")
    public WebElement frontForm;

    @FindBy(how = How.CSS, using = "div[class = 'loginText']")
    public WebElement loginText;
    
    @FindBy(how = How.CSS, using = "div[class = 'login-forgot-pwd']")
    public WebElement forgotPasswordText;
    
    //@FindBy(how = How.CSS, using = "div[class = 'dashboard change-password-dashboard']") // RF 12/11/2014
    @FindBy(how = How.CSS, using = "div[class *= 'reset-password']") // MGB 1/16/2015
    public WebElement expiredPasswordPage;
    
    //@FindBy(how = How.CSS, using = "div[class = 'loginForm'] i[class = 'error-message']") // MGB 6/3/2014
    //@FindBy(how = How.CSS, using = "div[class ^= 'login-form'] i[class = 'error-message']")
    @FindBy(how = How.CSS, using = "div[class *= 'login_invalid'] i[class *= 'error-message']")
    public List<WebElement> errorMessageList;
    
    @FindBy(how = How.CSS, using = "span[data-ng-show*='loginMessage']")
    public WebElement loginMessage;
    
    //actions  
    
    public void setUserName(String user) throws IOException, InterruptedException {
        super.setTextField(usernameField, "User Name", user);     
    }
     
    public void setPassword(String thePassword) throws IOException, InterruptedException {
        super.setTextField(passwordField, "Password", thePassword);     
    }

     
    public void clickSubmit() throws IOException, InterruptedException {
        //super.clickElement(submitButton, "Login Button");
    	super.clickElement(loginButton, "Login Button");
    }    

     
	
     // page use cases    
    public boolean launchApplication(String title) throws InterruptedException {  
    	long elapsedTimeB = Calendar.getInstance().getTimeInMillis();
    	int browseDelay = AutomationSettings.getDelay("Login");
    	this.driver.manage().timeouts().implicitlyWait(browseDelay, TimeUnit.SECONDS);
    	this.driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    	boolean navigated = false;
	    try {
    		String url = GeneralMethods.getDeployment(); 
    		this.driver.navigate().to(url);
    		String appTitle = driver.getTitle();  
    		System.out.println("title: "+appTitle);
    		
    		for (int i=0; i < NG7TestCase.loginTimeOut; i++) {
    			GeneralMethods.delay(1000);
    			navigated = verifyLogin();
    			if (navigated) break;
    		}
    		
    	} catch (Exception e) {
    		System.out.println("Exception thrown by launchApplication: " +e.getMessage());
    	}
    	
	    // Calculate the test step elapsed time
		long elapsedTimeA = Calendar.getInstance().getTimeInMillis() - elapsedTimeB;
		System.out.println("\n\nLOGIN (s): "+elapsedTimeA);
		////
		
    	return navigated;
    }
    
    
    public void closeApplication() {       	
    	this.driver.close();    	
    }
    
    
    public boolean attemptLogin(String user, String password) throws IOException, InterruptedException{         	    
        try{
        	long elapsedTimeB = Calendar.getInstance().getTimeInMillis();
        	this.setUserName(user);
        	this.passwordField.clear();
        	this.passwordField.sendKeys(password);
            this.clickSubmit();
            
            // Calculate the test step elapsed time
    		long elapsedTimeA = Calendar.getInstance().getTimeInMillis() - elapsedTimeB;
    		System.out.println("\n\nattemptLogin() (s): "+elapsedTimeA);
    		////
    		
        	return true;
        } catch (Exception e) { return false; }
            	    
     }

// RF 12/11/2014
    public boolean passwordNotExpired() throws IOException, InterruptedException{         	    
        try{
        	//boolean success = GeneralMethods.checkIsDisplayed(this.expiredPasswordPage);
        	//boolean success = GeneralMethods.checkIsDisplayed();
        	boolean success = GeneralMethods.checkElementIsNotVisible(this.driver, expiredPasswordPage) ;       	
        	
        	return success;
        } catch (Exception e) { return false; }
            	    
     }
////

     public boolean login() throws IOException, InterruptedException{
        try{
          long elapsedTimeB = Calendar.getInstance().getTimeInMillis();	
    	  this.usernameField.clear();
	 	  this.passwordField.clear();
    	  boolean success = true;
          success &= super.setTextField(this.usernameField, "Username field", super.USER_NAME); // this.setUserName(super.USER_NAME);
          this.passwordField.sendKeys(super.PASSWORD);
          success &= super.clickElement(this.loginButton, "Login button"); // this.clickSubmit();
          System.out.println("Wait 3s after login");
          GeneralMethods.delay(3000);
          // Calculate the test step elapsed time
  		  long elapsedTimeA = Calendar.getInstance().getTimeInMillis() - elapsedTimeB;
  		  System.out.println("\n\nlp.login() (s): "+elapsedTimeA);
  		  ////
          return success;
        } catch (Exception e){
        	System.out.println("login: Exception thrown " + e.getMessage());
        	return false;
        }
      }
      
     public boolean verifyLogin() throws InterruptedException{
    	 String bannerText = this.driver.getTitle();
    	 boolean check = false;
    	 check = bannerText.equals("NG7") ? true :
    		 bannerText.equals("NEXTGEN Now") ? true : false;
    	 
    	 
    	 //WebElement container = null;
    	 //container = this.driver.findElement(By.cssSelector("div[class^='login-container']"));
    	//List<WebElement> inputs = this.driver.findElements(By.cssSelector("input"));
    	 WebElement container = GeneralMethods.FindVisibleObject(this.driver, "div[class^='login-container']");
 		 List<WebElement> inputs = GeneralMethods.FindElementsInObjHierarchy(container, "input");
    	 
 		 //System.out.println("container visible? " +container.isDisplayed());
 		 //System.out.println("number of inputs? " +inputs.size());
 		 
 		 check &= container != null ? true : false;
 		 check &= container != null && inputs.size() > 0 ? true : false;
 		
    	 return check;
     }
 }
