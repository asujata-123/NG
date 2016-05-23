/**
 * 
 */
package com.nextgen.qa.automation.ui;

import org.openqa.selenium.*;

import java.util.NoSuchElementException;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * @author raguirre
 *
 */
public class Synchronization {

	
    public WebDriver driver;
    public String LOG_FILE;
    
	/**
	 * 
	 */
	public Synchronization(WebDriver aDriver, String aLOG_FILE) {
       driver = aDriver;
       LOG_FILE = aLOG_FILE;
	}
	
	
	
	/**
	 * 
	 */
	public boolean isElementPresent(By by) {
	   try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	}
	

	/**
	 * 
	 */
	public boolean isAlertPresent() {
	  try {
	    driver.switchTo().alert();
	    return true;
	   } catch (NoAlertPresentException e) {
	     return false;
	   }
	}
		  	
	
	
    // Wait for the page to load, timeout after 10 seconds	
	public boolean waitElementDissapears(By by, int timeOut) {
					
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
				
	}
	
	
	/*
	public boolean waitElementDissapears(WebElement element , int timeOut) {
		
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		
		return  wait.until( ExpectedConditions.visibilityOf(element) == null)  ? true : false; 
				
	}
	*/
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
				
	}

}
