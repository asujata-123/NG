package com.nextgen.qa.automation.toolbox;
//MGB
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Dim;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class GeneralMethods {
	
	@FindBy(how = How.CSS, using = "div[class*='spinner']")
    public WebElement loadingSpinner;
	
	/**
	 * Generic delay function, causes the execution script to sleep for the specified duration.
	 * @param delayLength delay duration in milliseconds.
	 * 
	 */
	public static void delay (int delayLength) {
		//System.out.println("Start delay..." +delayLength);
		try {
			Thread.sleep(delayLength); 
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Creates a unique string of 2 groups of numbers separated by a "_".
	 * @return returns unique ID string
	 * 
	 */
	public static String CreateUID(){
		System.out.println("Create unique identifier");
		double x = Math.random();
		double y = Math.random();
		return Double.toString(x).substring(3,9) + "_" + Double.toString(y).substring(3,9);
	}
	
	/**
	 * Calculates and returns a random integer between zero and the value specified by 'ceiling'.
	 * @param ceiling
	 * @return
	 */
	public static int PickRandomNumber(int ceiling){
		System.out.println("Pick random number between 0 and " +ceiling);
		Random r = new Random();
		System.out.println("Selected random number is " +r.nextInt(ceiling));
		return r.nextInt(ceiling);
	}
	
	@SuppressWarnings("deprecation")
	public static String GenerateRandomDate(int yearCeiling){
		System.out.println("Generate a random date " +yearCeiling +" years from the current year");
		String prefix = "";
		Random day = new Random ();
		
		String dayS = "";
		int randomDay = day.nextInt(27) + 1;
		prefix = randomDay < 10 ? "0" : "";
		dayS = prefix + Integer.toString(randomDay);
		prefix = "";
				
		Random month = new Random ();
        String monthS = "";
		int randomMonth = month.nextInt(11) + 1;
		prefix = randomMonth < 10 ? "0" : "";
		monthS = prefix + Integer.toString(randomMonth);
		
		Calendar now = Calendar.getInstance(); 
		int currentYear = now.get(Calendar.YEAR);   
		//System.out.println("Current year - " + currentYear);
		int randomYear = currentYear + yearCeiling;
		String randomDate = monthS+"/"+dayS+"/"+randomYear;
		return randomDate;
	}
	
	public static String GenerateRandomDOB(int yearRange){
		System.out.println("Generate a random date within " +yearRange +" years before the current year");
		
		String dayS;
		Random day = new Random ();
		int randomDay = day.nextInt(27) + 1;
		if (randomDay < 10) dayS = "0" + Integer.toString(randomDay);
		else dayS = Integer.toString(randomDay);
		
		String monthS;
		Random month = new Random ();
		int randomMonth = month.nextInt(11) + 1;
		if (randomMonth < 10) monthS = "0" + Integer.toString(randomMonth);
		else monthS = Integer.toString(randomMonth);
		
		Calendar now = Calendar.getInstance(); 
		int currentYear = now.get(Calendar.YEAR);   
		//System.out.println("Current year - " + currentYear);
		int randomYear = currentYear - yearRange;
		String randomDate = monthS+"/"+dayS+"/"+randomYear;
		return randomDate;
	}
	
	public static String GenerateTimeStamp(){
		System.out.println("Generate timestamp");
		Calendar now = Calendar.getInstance(); 
		
		int year = now.get(Calendar.YEAR);   
		
		int month = now.get(Calendar.MONTH) +1;
		String monthStr = GeneralMethods.GetMonthString(month);
		String monthNumberStr = GeneralMethods.GetMonthNumber(monthStr);
		
		int date = now.get(Calendar.DATE);
		String dateStr = (date < 10) ? "0"+Integer.toString(date) : Integer.toString(date);
		
		int hour = now.get(Calendar.HOUR_OF_DAY); 
		int min = now.get(Calendar.MINUTE); 
		
		String firstTenMins = (min < 10) ? "0" : "";
				
		//String timeStamp = year+monthStr+date +"_runHour_" +time;
		String timeStamp = year+monthNumberStr+dateStr +"_" +hour +":" +firstTenMins+min;
		return timeStamp;
	}
	
	public static boolean CheckNLTHour(int hourCheck){
		System.out.println("Check that hour is earlier than "+hourCheck);
		Calendar now = Calendar.getInstance(); 
		int hour = now.get(Calendar.HOUR_OF_DAY);
		if (hour <= hourCheck) return true;
		else return false;
	}
	
	/**
	 * 	/**
	 * To create days around a specific date: 
	 * 
	 * String today = GeneralLib.GenerateDateAroundToday(0);
	 * String tomorrow = GeneralLib.GenerateDateAroundToday(1);
	 * String yesterday = GeneralLib.GenerateDateAroundToday(-1); 
	 * 
	 * @param dayOffset
	 * @return
	 */
	public static String GenerateDateAroundToday(int dayOffset){
		System.out.println("Generate a date around today - " +dayOffset +" days");
		Calendar today = Calendar.getInstance(); 
		Calendar offsetDate = (Calendar) today.clone();
		offsetDate.add(Calendar.DAY_OF_YEAR, dayOffset);
		
	    String date = offsetDate.getTime().toString();
		int year = offsetDate.get(Calendar.YEAR);
		
		String prefixMonth;
		int month = offsetDate.get(Calendar.MONTH) +1;
		prefixMonth = (month < 10) ?  "0" : ""; 
		
		String prefixDay;
		int day = offsetDate.get(Calendar.DATE);
		prefixDay = (day < 10) ? "0" : "";
		
		return prefixMonth+month +"/" +prefixDay+day  +"/" +year;
		//return date;
	}
	
	
	/**
	 * Maximizes the driver window.
	 * @param driver
	 */
	public static void Maximize(WebDriver driver){
		driver.manage().window().maximize();
	}
	
	/**
	 * Returns button from the obj hierarchy matching 'buttonName'.
	 * @param obj
	 * @param buttonName
	 * @return
	 */
	public static WebElement GetButton (WebElement obj, String buttonName){
		try{
			System.out.println("Get SmartItem button " + buttonName);
			return obj.findElement(By.cssSelector("input[value = '"+buttonName+"']"));
		} catch (Exception e){
			System.out.println("Could not get SmartItem button " + buttonName + " " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Clears the field referenced by 'element'
	 * @param element
	 * @return
	 */
	public static boolean ClearField(WebElement element){
		System.out.println("Clear field");
		try{
			element.clear();
			GeneralMethods.delay(NG7TestCase.eventDelay);
			return true;
		} catch (Exception e){
			System.out.println("Exception thrown while clearing login field");
			return false;
		}
	}
	
	public static boolean ClickButton(WebElement button){
		try{
			System.out.println("Click button " + button.getText());
			button.click();
			GeneralMethods.delay(NG7TestCase.clickDelay);
			return true;
		} catch (Exception e){
			System.out.println("Could not click button " +e.getMessage() );
			return false;
		}
	}
	
	// Finding web elements
	/**
	 * Waits for timeout seconds for visibility of an object specified by cssData.  Returns the object if found within the timeout, or null if the object is not found within the timout period.
	 * @param driver : reference to the ChromeDriver
	 * @param timeout : timeout value in seconds; 
	 * @param elementDescription : Name of the window or object (so that the print statement makes sense)
	 * @param cssData : css handle to find the element by
	 * @return
	 */
	public static WebElement WaitForElementBySelector (WebDriver driver, int timeout, String elementDescription, String cssData){
		System.out.println("Wait for element " +elementDescription +" and time out after " +timeout +" seconds");
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			System.out.println("Waiting for element "+elementDescription);
			WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssData)));
			return item;
		} catch (Exception e){
			System.out.println("Exception thrown on WaitForElementBySelector " +e.getMessage() );
			return null;
		}
	}
	
	public static WebElement WaitForElement (WebDriver driver, int timeout, String elementDescription, WebElement element){
		//System.out.println("Wait for element " +elementDescription +" and time out after " +timeout +" seconds");
		try{
			/*
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			WebElement item = wait.until(ExpectedConditions.visibilityOf(element));
			return item;
			*/
			// MGB 1/2/2015 workaround to chrome/Jenkins visibility issue
			boolean displayed = false;
			for (int i=0; i <= timeout; i++){
				//displayed = MouseMethods.HoverOver(NG7TestCase.driver, element, 5, 5) ? true : false;
				displayed = element.isDisplayed() ? true : false;
			    GeneralMethods.delay(timeout*1000);
			    if (displayed)
			    	break;
			}
			return element;
			////
		} catch (Exception e){
			//System.out.println("Exception thrown on WaitForElement " +e.getMessage() );
			return null;
		}
	}
	
	public static List<WebElement> WaitForElements (WebDriver driver, int timeout, String elementDescription, List<WebElement> elements){
		System.out.println("Wait for element " +elementDescription +" and time out after " +timeout +" seconds");
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElements(elements));
			return items;
		} catch (Exception e){
			System.out.println("Exception thrown on WaitForElements " +e.getMessage() );
			return null;
		}
	}
	
	public static WebElement FindElementInObjHierarchy (WebElement obj, String cssData){
		System.out.println("Find element matching " +cssData +" in Object Hierarchy");
		try{
			WebElement element = obj.findElement(By.cssSelector(cssData));
			return element;
		} catch (Exception e){
			System.out.println("Object matching " +cssData +" was not found");
			return null;
		}
	}

	public static WebElement FindElementInObjHierarchy (WebElement obj, String cssData, String text){
		System.out.println("Find element matching " +cssData +" in Object Hierarchy");
		try{
			List<WebElement> elements = obj.findElements(By.cssSelector(cssData));
			if (elements.size() == 1) return elements.get(0);
			for (WebElement element : elements)
				if (element.getText().contains(text)) return element;
			return null;
		} catch (Exception e){
			System.out.println("Objects matching " +cssData +" was not found");
			return null;
		}
	}
	
	public static List<WebElement> FindElementsInObjHierarchy (WebElement obj, String cssData){
		System.out.println("Find elements matching " +cssData +" in Object Hierarchy");
		try{
			List<WebElement> elements = obj.findElements(By.cssSelector(cssData));
			return elements;
		} catch (Exception e){
			System.out.println("Objects matching " +cssData +" was not found");
			return null;
		}
	}
	
	public static WebElement FindElementInDriver (WebDriver driver, String cssData){
		System.out.println("Find element matching " +cssData +" in driver");
		try{
			WebElement element = driver.findElement(By.cssSelector(cssData));
			return element;
		} catch (Exception e){
			System.out.println("Object matching " +cssData +" was not found");
			return null;
		}
	}
	
	public static List<WebElement> FindElementsInDriver (WebDriver driver, String cssData){
		System.out.println("Find elements matching " +cssData +" in driver");
		try{
			List<WebElement> elements = driver.findElements(By.cssSelector(cssData));
			return elements;
		} catch (Exception e){
			System.out.println("Objects matching " +cssData +" was not found");
			return null;
		}
	}
	
	public static WebElement FindRelatedElement(WebDriver driver, WebElement obj, String cssDataReference, String cssData){
		System.out.println("Find child with matching " +cssData +" in specified parent " +cssDataReference);
		try{
			WebElement parent = obj.findElement(By.cssSelector(cssDataReference));
			WebElement child = parent.findElement(By.cssSelector(cssData));
			return child;
		} catch (Exception e){
			System.out.println("Exception thrown on FindChildElement " +e.getMessage());
			return null;	
		}
	}
	////
	
	public static String ExtractDateSubstrings(String date, String substr){
		Calendar cal = Calendar.getInstance();
		
		String dateSplit;
		List<String> substrings = Arrays.asList(date.split("/"));
		
		String val;
		
		if (substr.equals("month")){
        	val = substrings.get(0);
        	val = GetMonthString(Integer.parseInt(val));
		}
		else if (substr.equals("day")) {
			val = substrings.get(1);
			int valInt = Integer.parseInt(val);
			val = Integer.toString(valInt);
		}
		else if (substr.equals("year")) val = substrings.get(2);	
		else val = "dateError";
		
		return val;
	}
	
	public static String ConstructDate(String date){
		String month = GeneralMethods.ExtractDateSubstrings(date, "month");
		String day = GeneralMethods.ExtractDateSubstrings(date, "day");
		String year = GeneralMethods.ExtractDateSubstrings(date, "year");
		
		return month +" " +day +", " +year;
	}
	
	public static String ConstructDateAbbr(String date){
		String month = GeneralMethods.ExtractDateSubstrings(date, "month");
		String day = GeneralMethods.ExtractDateSubstrings(date, "day");
		String year = GeneralMethods.ExtractDateSubstrings(date, "year");
		
		return month.substring(0, 3) +" " +day +", " +year;
	}
		
	public static String GetMonthString(int month){
		String monthString;
        switch (month) {
            case 1:  monthString = "January";
                     break;
            case 2:  monthString = "February";
                     break;
            case 3:  monthString = "March";
                     break;
            case 4:  monthString = "April";
                     break;
            case 5:  monthString = "May";
                     break;
            case 6:  monthString = "June";
                     break;
            case 7:  monthString = "July";
                     break;
            case 8:  monthString = "August";
                     break;
            case 9:  monthString = "September";
                     break;
            case 10: monthString = "October";
                     break;
            case 11: monthString = "November";
                     break;
            case 12: monthString = "December";
                     break;
            default: monthString = "Invalid month";
                     break;
        }            
        return monthString;
	}
	
	public static String GetMonthNumber(String month){
		String monthNumber =  month.startsWith("Jan") ? "01" :
				month.startsWith("Feb") ? "02" :
					month.startsWith("Mar") ? "03" :	
						month.startsWith("Apr") ? "04" :	
							month.startsWith("May") ? "05" :	
								month.startsWith("Jun") ? "06" :	
									month.startsWith("Jul") ? "07" :	
										month.startsWith("Aug") ? "08" :	
											month.startsWith("Sep") ? "09" :	
												month.startsWith("Oct") ? "10" :	
													month.startsWith("Nov") ? "11" :	
														month.startsWith("Dec") ? "12" : "unknownMonth"; 	
        
        return monthNumber;
	}
		
	public static boolean SwitchToWindow(WebDriver driver, String windowTitle){
		String voiceCtrlWindow = null;
		try {
			// Get set of all open window handles and iterate through them to find the Voice Control window
			Set s=driver.getWindowHandles();
			Iterator ite=s.iterator();
			String windowName;
			String windowTitleLC = windowTitle.toLowerCase();
			while(ite.hasNext())
			{
			    driver.switchTo().window(ite.next().toString());
			    windowName = driver.getTitle();
			    if (windowName.toLowerCase().contains(windowTitleLC)) return true;
			}
			return false;
		} catch (Exception e){
			System.out.println("Exception thrown on GetVoiceControlWindow " + e.getMessage());
			return false;
		}
		
	}
	
	public static boolean PatternLibComplianceCheckWithTag(WebElement obj, String field, String value, String tagValue, String label){
		System.out.println("Perform a Pattern Library compliance check for object " + label);
		try{
			String tag = obj.getTagName();
			String attr = obj.getAttribute(field);
			if (attr.contains(value) && tag.equals(tagValue)) return true;
			return false;
		} catch (Exception e){
			System.out.println ("Exception thrown on PatternLibComplianceCheck " +e.getMessage());
			return false;
		}
	}
	
	public static WebElement FindvisibleObject(List<WebElement> objs){
		System.out.println("Find the displayed object from a list");
		try{
			for(WebElement obj: objs)
				if (obj.isDisplayed()) return obj;
			System.out.println("Could not find displayed object on list");
			return null;
		} catch (Exception e) {
			System.out.println("Exception on FindvisibleObject " +e.getMessage());
			return null;
		}
	}
	
	// Data file management
	public static BufferedReader OpenDataFile(String fileName){
		System.out.println("Open data file " + fileName);
		try{
			String file = fileName;
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";
			br = new BufferedReader(new FileReader(file));
			return br;

		} catch (Exception e) {
			System.out.println("Exception on OpenDataFile " +e.getMessage());
			return null;
		}
	}
	
	public static String[] ReadDataFileLine(BufferedReader br){
		System.out.println("Read line from open data file ");
		try{
			String line = br.readLine();
			String[] lineArray = line.split("\t");
			lineArray[5] = lineArray[5].replace("\"", "");
			return lineArray;
		} catch (Exception e) {
			System.out.println("Exception on ReadDataFileLine " +e.getMessage());
			return null;
		}
	}
	////
	
	// General form methods
	public static Boolean ClickFormTab (WebDriver driver, WebElement form, String tabName){
		System.out.println("Click on form tab " +tabName);
		try{
			WebElement tab = GeneralMethods.GetFormTab(driver, form, tabName);
			MouseMethods.HoverOverClick(driver, tab, 20, 10);
			GeneralMethods.delay(NG7TestCase.eventDelay);
			return true;
		} catch (Exception e){
			System.out.println("Exception thrown on ClickFormTab " +e.getMessage());
			return false;
		}
	}
	
	public static WebElement GetFormTab (WebDriver driver, WebElement form, String tabName){
		System.out.println("Get the element for form tab " +tabName);
		try{
			// Return the selected tab if it is the requested tab
			//WebElement selectedTab = WizardLib.GetSelectedWizTab(form);
			//if (selectedTab.getText().contains(tabName)) return selectedTab;
			
			// Cycle through all the tabs in the form to find the requested one
			List<WebElement> tabs = form.findElement(By.cssSelector("div[class $= '-tabs']")).findElements(By.cssSelector("li[ng-click ^= 'setCurrentTab']"));
			String name;
			for (WebElement tab : tabs)
				if (tabName.equals(tab.getText())) return tab;
			return null;
		} catch (Exception e){
			System.out.println("Exception thrown on GetFormTab" +e.getMessage()); 
			return null;
		}
	}
	////
	
	public static boolean checkIsDisplayed(WebElement elementToCheck){
		boolean isDisplayed = false; 
		try{
			//GeneralMethods.delay(NG7TestCase.eventDelay); // RF 12/11/2014
			//isDisplayed = elementToCheck.isDisplayed();
			isDisplayed = (GeneralMethods.WaitForElement(NG7TestCase.driver, 2, "", elementToCheck) != null) ? true : false;
			return isDisplayed;
		} catch (Exception e){
			return false;
		}
	}
	
	public static boolean checkIsNotEmpty(List<WebElement> elementListToCheck){
		boolean isDisplayed = false; 
		try{
			boolean isNotEmpty = elementListToCheck.isEmpty() ? false : true;
			return isNotEmpty;
		} catch (Exception e){
			return false;
		}
	}

	public static boolean clickFieldCheckListIsNotEmpty(WebElement parentField, List<WebElement> elementListToCheck){
		try{
			GeneralMethods.ClickButton(parentField);
			GeneralMethods.delay(NG7TestCase.clickDelay);
			// MGB 12/15/2014
			WebElement element = GeneralMethods.WaitForElement(NG7TestCase.driver, 2, "List elements", elementListToCheck.get(0));
			////
			boolean isNotEmpty = elementListToCheck.isEmpty() ? false : true;
			GeneralMethods.ClickButton(parentField);
			MouseMethods.HoverOverClick(NG7TestCase.driver, parentField, parentField.getSize().getWidth() + 10, 0);
			return isNotEmpty;
		} catch (Exception e){
			return false;
		}
	}

	public static boolean clickFieldFindListItems(WebElement form, WebElement parentField){
		try{
			GeneralMethods.ClickButton(parentField);
			GeneralMethods.delay(NG7TestCase.eventDelay*2);
			
			List<WebElement> li;
			boolean success = false;
			
			li = GeneralMethods.FindElementsInObjHierarchy(form, "li");
			
			if (li.size() != 0)
				success = true;
			
			else
				success = false;
			
			//GeneralMethods.ClickButton(parentField);
			MouseMethods.HoverOverClick(NG7TestCase.driver, parentField, parentField.getSize().getWidth() + 5, 0);
			return success;
			
		} catch (Exception e){
			return false;
		}
	}
	
	public static boolean checkListIsDisplayed(List<WebElement> elementListToCheck){
		boolean isDisplayed = false; 
		try{
			WebElement element = GeneralMethods.WaitForElement(NG7TestCase.driver, NG7TestCase.waitForElementDelay, "", elementListToCheck.get(0));
			//boolean success = (element == null) ? false : true;
			boolean success = element.isDisplayed() ? true : false;
			return success;
			////
		} catch (Exception e){
			return false;
		}
	}
	
	public static WebElement GetFieldByLabelName(WebElement obj, String cssValueDataRow, String cssValueLabel, String labelName, String cssValueField){
		try{
			System.out.println("GetFieldByLabelName " +labelName);
			List<WebElement> rows = obj.findElements(By.cssSelector(cssValueDataRow));
			WebElement label = null;
			WebElement field = null;
			WebElement row = null;
			for (WebElement row1: rows){
				label = row1.findElement(By.cssSelector(cssValueLabel));
				if (label.getText().contains(labelName)) {
					row = row1;
					break;
				}
				row = null; // if a match is not found on the label
			}
			if (row != null) field = row.findElement(By.cssSelector(cssValueField));
			return field;
		} catch (Exception e) {
			System.out.println("Exception thrown on GetFieldByLabelName " +e.getMessage());
			return null;
		}
	}
		
	public static WebElement GetFieldByLabelNameFromDriver(WebDriver driver, String cssValueDataRow, String cssValueLabel, String labelName, String cssValueField){
		try{
			System.out.println("GetFieldFromDriverByLabelName " +labelName);
			List<WebElement> rows = driver.findElements(By.cssSelector(cssValueDataRow));
			WebElement label = null;
			WebElement field = null;
			WebElement row = null;
			for (WebElement row1: rows){
				label = row1.findElement(By.cssSelector(cssValueLabel));
				if (label.getText().contains(labelName)) {
					row = row1;
					break;
				}
				row = null; // if a match is not found on the label
			}
			if (row != null) field = row.findElement(By.cssSelector(cssValueField));
			return field;
		} catch (Exception e) {
			System.out.println("Exception thrown on GetFieldFromDriverByLabelName " +e.getMessage());
			return null;
		}
	}
	
	public static String getDeployment() {
        return NG7TestCase.deployment.equals("") == false ? NG7TestCase.deployment : AutomationSettings.getBaseURL(); 
	}
	
	public static String getArtifactName() {
        return NG7TestCase.artifact.equals("") == false ? NG7TestCase.artifact : AutomationSettings.getArtifact(); 
	}
	
	public static String getRunParametersFilename() {
        return NG7TestCase.runParametersFile.equals("") == false ? NG7TestCase.runParametersFile : AutomationSettings.getRunParametersFilename(); 
	}
	
	public static boolean ClickDismissButton(WebElement obj) {
		try{
			GeneralMethods.ClickButton(obj.findElement(By.cssSelector("input[type='button'][value='Dismiss']")));
			return true;
		} catch (Exception e) { return false; }
	}
	
	public static WebElement findBanner(WebDriver driver) {
		try{
			return driver.findElement(By.cssSelector("div[class^='banner']"));
		} catch (Exception e) { return null; }
	}
	
	public static boolean ClickVisibleButton(WebDriver driver, String cssValue) {
		try{
			List<WebElement> buttons = driver.findElements(By.cssSelector(cssValue));  
			WebElement button = GeneralMethods.FindvisibleObject(buttons);
			GeneralMethods.ClickButton(button);
			return true;
		} catch (Exception e) { return false; }
	}

	public static WebElement FindVisibleObject(WebDriver driver, String cssValue) {
		try{
			driver.manage().timeouts().implicitlyWait(NG7TestCase.defaultTimeout, TimeUnit.MILLISECONDS);
			List<WebElement> buttons = driver.findElements(By.cssSelector(cssValue));
			WebElement button = GeneralMethods.FindvisibleObject(buttons);
			return button;
		} catch (Exception e) { return null; }
	}

	public static boolean scrollOnElement(WebDriver driver, WebElement elementToScrollOn, String direction) {
	    try{
		    Dimension size = elementToScrollOn.getSize();
			int x = size.width-5;
			int y = 5;
			if (direction.equals("down")) y = size.height-5;
			return MouseMethods.HoverOverClick(driver, elementToScrollOn, x, y);
		  } catch (Exception e) { return false; }
	}
	
	public static boolean checkElementIsNotVisible(WebDriver driver, WebElement element){
		GeneralMethods.delay(NG7TestCase.eventDelay);
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean isNotVisible = true;
		try {
			if(element.isDisplayed())
			//if (MouseMethods.HoverOver(driver, element, 5, 5))
				isNotVisible=false;
		} catch (Exception e) { 
			isNotVisible=true; 
		}
			driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.SECONDS);
			return isNotVisible;
	}
	
	public static boolean waitUntilNotVisible (WebElement elementToDisappear, String description, int timeout) {
		try {
			int t=0;
			while(elementToDisappear.isDisplayed() && t < timeout){
				System.out.println("Element "+description+ " still visible");
				GeneralMethods.delay(500);
				t++;
			}
			if (! elementToDisappear.isDisplayed()) return true;
			else return false;
		}  catch (Exception e) { 
			System.out.println("Exception on waitUntilNotVisible " + e.getMessage());
			return false; }
	}
	
	public static WebDriver startDriver (){
		
		// MGB 3/24/2015 set path to ChromeDriver executable
		String chromeDriverExe = "";
		//if (NG7TestCase.platform.toLowerCase().contains("windows")) chromeDriverExe = NG7TestCase.workingDir+"\\configuration\\chromedriver.exe";
		//else chromeDriverExe = NG7TestCase.workingDir+"\\configuration\\chromedriver";
		if (NG7TestCase.platform.toLowerCase().indexOf("windows")>=0) chromeDriverExe = NG7TestCase.workingDir+"\\configuration\\chromedriver.exe";
		else chromeDriverExe = NG7TestCase.workingDir+"//configuration//chromedriver";
		System.setProperty("webdriver.chrome.driver", chromeDriverExe); 
		//
		
		try{
			if (AutomationSettings.getTestDataItem("ChromeVersion").equals("canary")){
				System.out.println("Starting Chrome Canary...");
				String path = AutomationSettings.getTestDataItem("ChromeCanaryPath");
				ChromeOptions options = new ChromeOptions();
				options.setBinary(path);
				options.addArguments(NG7TestCase.chromeOptions);
				WebDriver driver = new ChromeDriver(options);
				driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				NG7TestCase.driver = driver;
				return driver;	
			}
			
			else {
			   ChromeOptions options = new ChromeOptions();
			   options.addArguments(NG7TestCase.chromeOptions);
			   WebDriver driver = new ChromeDriver(options);
			   driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.SECONDS);
			   driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			   driver.manage().window().maximize();
			   NG7TestCase.driver = driver;
			   return driver;
			}
		} catch (Exception e) {
			//String path = "C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe";
			String path = AutomationSettings.getTestDataItem("ChromeProductionPath");
			ChromeOptions options = new ChromeOptions();
			options.setBinary(path);
			options.addArguments(NG7TestCase.chromeOptions);
			WebDriver driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return driver;
		}
	}
	 
	public static void prepForIteration(){
		NG7TestCase.testStepTranscript = "";
	}
	
	public static String visibilityCheck() {
		try {
			String result = "";
			NG7TestCase.driver.navigate().to("https://www.google.com/");
			
			WebElement searchField = NG7TestCase.driver.findElement(By.cssSelector("input[name='q']"));
			result = searchField.isDisplayed() ? "Found google search field | " : "Did not find google search field | ";
			
			return result;
		} catch (Exception e){
			System.out.println("visibilityCheck: found exception" + e.getMessage());
			return "";
		}
	}
	
	public static void updateJIRATestResultContainer() {
		Thread t = new Thread(new Runnable()
		{
			public void run()
			{
				try{
					String call = NG7TestCase.pythonZAPICall+
							" --timestamp=" +NG7TestCase.timeStamp+
							" --testname=" +NG7TestCase.testName+
							" --region=" +NG7TestCase.build+
							" --update=true > " +NG7TestCase.artifactPath +"zapiCallOut.txt";
					System.out.println("Update JIRA test results with "+call);
					Runtime.getRuntime().exec(call);
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
		});	
		t.start();
	}
	
	// timerSample : before if when=0; after if when=1
	public static void timerSample(int when){
	   try{
		  if (when == 0) NG7TestCase.timerSampleBefore = Calendar.getInstance().getTimeInMillis();
		  else NG7TestCase.timerSampleAfter = Calendar.getInstance().getTimeInMillis();
	   } catch (Exception e){
		  System.out.println("timerSample: exception "+e.getMessage());
	   }
	}
	
	public static long readTimer(){
		try{
		   return NG7TestCase.readTimer = NG7TestCase.timerSampleAfter - NG7TestCase.timerSampleBefore;
		} catch (Exception e) {
			System.out.println("readTimer: exception "+e.getMessage());
			return 0;
		}
	}
	////
	
	// timeout management
	public static void setTimeout(int seconds){
		try{
			NG7TestCase.driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		} catch (Exception e){
			System.out.println("setTimeout: exception "+e.getMessage());
		}
	}
	
	public static void resetTimeout(){
		try{
			NG7TestCase.driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.SECONDS);
		} catch (Exception e){
			System.out.println("resetTimeout: exception "+e.getMessage());
		}
	}
	
	public static boolean clickFieldSelectList(WebElement selectElement, String description){
		try{
			   GeneralMethods.checkIsDisplayed(selectElement);
			   selectElement.sendKeys(description);
			   GeneralMethods.delay(NG7TestCase.eventDelay);
			   GeneralMethods.delay(NG7TestCase.eventDelay);
			   selectElement.sendKeys(Keys.DOWN);
			   GeneralMethods.delay(NG7TestCase.eventDelay);
			   selectElement.sendKeys(Keys.ENTER);
			   GeneralMethods.delay(NG7TestCase.eventDelay);
			return true;
			 }
		    
		catch (Exception e){
			return false;
		}
	}

	public static boolean clickFieldSelectListFirst(WebElement selectElement){
		try{
			   GeneralMethods.checkIsDisplayed(selectElement);
			   System.out.println(selectElement.getTagName());
			   System.out.println(selectElement.getAttribute("class"));
			   selectElement.sendKeys(Keys.ENTER);
			   GeneralMethods.delay(NG7TestCase.eventDelay);
			   selectElement.sendKeys(Keys.DOWN);
			   selectElement.sendKeys(Keys.ENTER);
			   GeneralMethods.delay(NG7TestCase.eventDelay);
		       return true;
			 }
		    
		catch (Exception e){
			return false;
		}
	}

	

}


