package com.nextgen.qa.automation.pages;

import com.nextgen.qa.automation.toolbox.Artifact;
import com.nextgen.qa.automation.toolbox.AutomationSettings;
import com.nextgen.qa.automation.toolbox.GeneralMethods;
import com.nextgen.qa.automation.toolbox.NG7TestCase;
import com.nextgen.qa.automation.ui.GenericPage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

//import com.nextgen.qa.automation.toolbox.AutomationSettings;


//import org.openqa.selenium.By;
//import org.openqa.selenium.StaleElementReferenceException;



/**
 * 
 */

/**
 * @author raguirre changes done by subha
 *
 */
public class OrderPage extends GenericPage {

	/**
	 * 
	 */
	public OrderPage(WebDriver aDriver, String aLOG_FILE) {	
		   super(aDriver, aLOG_FILE);
		   this.driver = aDriver;
	       this.LOG_FILE = aLOG_FILE;
		}

	PatientDashboardPage pdb = new PatientDashboardPage(driver, "patientdashboard.txt");
	
	// Web Elements
	@FindBy(how = How.CSS, using = "div[ng-click^='clickMenu()']")
	public WebElement openOrderMenu;

	@FindBy(how=How.CSS,using = "div[class^='order-new-menu-list']")
	public WebElement parentorderMenu;
	
   //@FindBy(how= How.CSS, using ="li")
  // public List<WebElement> orderMenu;
   
   @FindBy(how= How.CSS, using ="li[data-secure-url^='/nav/patient/:patientId/patientOrderManagement/labs']")
   public WebElement createLabOrder;

   ///nav/patient/:patientId/patientOrderManagement/labs
   
   @FindBy(how= How.CSS, using ="div[class^='ngxTypeAheadPg'][data-ta-search-field^='description'] input")
   public WebElement webtest; // div class and input 
  // public String testSelectListCss = "li";
   
   @FindBy(how= How.CSS, using ="div[class^='ngxTypeAheadPg'][data-ta-search-field='DiagnosticServiceProvidername'] input")
   public WebElement webentity;
   
   @FindBy(how= How.CSS, using ="div[class^='ngxTypeAheadPg'][data-ta-search-field='ProviderFullname'] input")
   public WebElement webprovider;
   
   @FindBy(how= How.CSS, using ="[data-ta-select-options='diagnoses'] input")
   public WebElement webDiagnosis;
   
   @FindBy(how= How.CSS, using ="div[class^='modalFooter'] input[value='Place Order']")
   public WebElement placeOrder;
   
   
   //@FindBy(how= How.CSS, using ="div[class^='order-center'] div[ng-cell] ")
  //@FindBy(how= How.CSS, using ="div[class^='order-center'] div[ng-style *='rowStyle']")
   @FindBy(how= How.CSS, using ="div[class='grid-row-inner ng-scope']")
   public List <WebElement> orderRow; // change this  element
   
  // @FindBy(how= How.CSS, using ="div[class ^= 'ngContentCell']")
  // @FindBy(how= How.CSS, using ="div[class *='ngCell  col0 colt0']")
   //public List <WebElement> orderNameCol;
   String orderNameCol = "div[class *='ngCell  col0 colt0']";
   String orderDateCol= "div[class *='ngCell  col5 colt5']";
   
  // @FindBy(how= How.CSS, using ="div[class *='ngCell  col5 colt5']")
  // public List <WebElement> orderDateCol;
   
  // @FindBy(how= How.CSS, using ="div[class^='ngContentCell'] div[ng-show *='OrderDate']")
   //@FindBy(how=How.CSS, using="div[ng-cell]")
 //  public WebElement orderDate;
  
  // @FindBy(how= How.CSS, using ="div[class ^= 'ngContentCell'] div[ng-bind^='orderName']")
   //@FindBy(how=How.CSS, using="div[ng-cell]")
  // public WebElement orderName;
   
   String cssData= "div[class ^= 'ngContentCell'] div[ng-bind^='orderName']";
   String cssData1= "div[class ^= 'ngContentCell'] div[ng-bind^='orderDateTime']";
   
   
 public boolean createOrders( WebElement webelement1,WebElement webelement2, WebElement webelement3, WebElement webelement4, String order, String testDescription,String entity,String provider)
 //, String entity, String provider)
 {
	try {       // Creating Lab orders
	 // Enter all the details and select place order.
	 //WebElement testfield = null;
		//List<WebElement> testFieldList = null;
		Boolean success;
		
		   System.out.println(order);
		   //testFieldList = GeneralMethods.FindElementsInObjHierarchy(webelement1, testSelectListCss);
		   //GeneralMethods.clickFieldCheckListIsNotEmpty(webelement1, testFieldList);
		   //success = setTextField(webelement1, "Description field", testDescription); // this.setUserName(super.USER_NAME)
		   GeneralMethods.delay(NG7TestCase.eventDelay);
		  GeneralMethods.clickFieldSelectList(webelement1, testDescription)  ;
		  GeneralMethods.clickFieldSelectList(webelement2, entity)  ;
		  GeneralMethods.clickFieldSelectList(webelement4,provider);
		  //Assuming the patient has diagnosis already
		  GeneralMethods.clickFieldSelectListFirst(webelement3);
		  GeneralMethods.ClickButton(placeOrder);
	 return true;
 }
	catch (Exception e){
		return false;
	}


}
 
 public boolean addDiagnosis( String diagnosis, String description)
 {
	 try {
		 System.out.println(description);
		 GeneralMethods.delay(EVENTDELAY);
		 GeneralMethods.checkIsDisplayed(pdb.editDiagnosis);
		 //System.out.println(" the description"+pdb.editDiagnosis.getAttribute("ng-click"));
		 System.out.println(" the tag"+pdb.editDiagnosis.getTagName());
		 System.out.println(" the description"+pdb.editDiagnosis.getAttribute("ng-click"));
		 pdb.clickElement(pdb.editDiagnosis, "Diagnosis");
		 pdb.clickElement(pdb.attachDiagnosis, "Click attach Diagnosis");
		 GeneralMethods.clickFieldSelectList(pdb.diagnosis, diagnosis);
		 GeneralMethods.ClickButton(pdb.attachDiagnosisButton);
		 GeneralMethods.ClickButton(pdb.attachDiagnosisDone);
		 GeneralMethods.ClickButton(pdb.diagnosisSave);
		 return true; 
	 }
	catch (Exception e){
		return false;
	}
 }
 
 public boolean verifyOrder( String orderValue, String description)
 {
	 boolean returnValue= false, returnValue1= false;
	 String value1, value2;
	 Date dt = new Date();
	 SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
	 String output,output1;
	 try {
		 System.out.println(description);
		 System.out.println(" date "+dt.toString());
		 GeneralMethods.delay(EVENTDELAY);
		 output= df2.format(dt);
		 output1="04/30/2015";
		 System.out.println(" date formatted"+output);
		 //value1= getText(orderElement, " Nameof order");
		 //returnValue = value1.contains(orderValue) == false ?  false : true;
		// value2= getText(orderDateElement, "date " );
		 //returnValue = value2.contains(dt.toString()) == false ?  false : true;
		 GeneralMethods.delay(EVENTDELAY);
		 returnValue=verifyTextList(orderRow,orderNameCol,orderValue,cssData);
		 returnValue1=verifyTextList(orderRow,orderDateCol,output,cssData1);
		 
		 return returnValue==true && returnValue1 == true ? true:false;
		 
	 }
	catch (Exception e){
		return false;
	}
 }
/* public static boolean verifyTextList(List <WebElement> listRowElement,List <WebElement> cells, List <WebElement> dateCells, String text,String date1,String cssData,String cssData1){
		int col_num,col_num_date,row_num,col_num_last,row_num_last;
		boolean returnValue=false,returnValue1= false;
		System.out.println(text);
		try{
			System.out.println("Number of Rows="+listRowElement.size());
			row_num=1;
			row_num_last=listRowElement.size();
           for(WebElement rowElement : listRowElement)
        	{  
        	  //List<WebElement> cells= rowElement.findElements(By.cssSelector("div[class *='ngCell  col0 colt0']"));
        	  
        	 System.out.println(" Number of Cols="+cells.size());
        	 col_num=1;col_num_date=1;
        	 System.out.println(" the value of current row"+ row_num);
        	 
        	
        	   for(WebElement colElement : cells)
        	   {
        		System.out.println( "value of row "+row_num);
        		//WebElement colName = colElement.findElement(By.cssSelector("div[class ^= 'ngContentCell'] div[ng-bind^='orderName']"));
        		WebElement colName = GeneralMethods.FindElementInObjHierarchy(colElement, cssData);
        		    	  
                  if (row_num == row_num_last) 
                   {
            	   System.out.println(" the value of first column"+colName.getText());
                   returnValue= colName.getText().contains(text) == true ? true:false ;
                   System.out.println("the vlaue of returnValue "+returnValue);
                   }
                   col_num++;
              	 }
                // to get the Date
        	   for(WebElement colElement1 : dateCells)
        	   {
        		System.out.println( "value of date row"+row_num);
        		//WebElement colName = colElement.findElement(By.cssSelector("div[class ^= 'ngContentCell'] div[ng-bind^='orderName']"));
        		WebElement colName1 = GeneralMethods.FindElementInObjHierarchy(colElement1, cssData1);
        		System.out.println(" the value of first column"+colName1.getText()+"the column number is"+ col_num_date );        	  
        	  
                  if (row_num == row_num_last) 
                   {
            	   System.out.println(" the value of last row column 6 "+colName1.getText() + " row number " + row_num );
                   returnValue1= colName1.getText().contains(date1) == true ? true:false ;
                   System.out.println("the vlaue of returnValue "+returnValue1);
                   }
                   col_num_date++;
              	 }
              
        
            row_num++;
         }
			return returnValue==true && returnValue1 == true ? true:false ;
		}
		catch (Exception e){
			return false;
		}
	}*/
 public static boolean verifyTextList(List <WebElement> listRowElement,String colCssData,  String text,String cssData){
		int col_num,row_num,col_num_last,row_num_last;
		boolean returnValue=false;
		System.out.println(text);
		try{
			
			row_num=1;
			row_num_last=listRowElement.size();
			System.out.println("the no of rows "+ row_num_last);	 
        for(WebElement rowElement : listRowElement)
     	{  
     	  List<WebElement> cells= rowElement.findElements(By.cssSelector(colCssData));
     	 // rowElement.findElements(By.)
     	
     	 col_num=1;
     	 
     	System.out.println("the no of cols"+ cells.size());
     	  
     	   for(WebElement colElement : cells)
     	   {
     		
     		//WebElement colName = colElement.findElement(By.cssSelector("div[class ^= 'ngContentCell'] div[ng-bind^='orderName']"));
     		WebElement colName = GeneralMethods.FindElementInObjHierarchy(colElement, cssData);
     		// System.out.println("Before if"+colName.getText());	  
               if (row_num == row_num_last) 
                {
         	   System.out.println("the value of column"+colName.getText());
                returnValue= colName.getText().contains(text) == true ? true:false ;
                }
                col_num++;
           	 }
            
     	  
           
     
         row_num++;
      }
			return returnValue ;
		}
		catch (Exception e){
			return false;
		}
	}
 public boolean deleteDiagnosis( String diagnosis, String description)
 {
	 try {
		 System.out.println(description);
		 GeneralMethods.delay(EVENTDELAY);
		 GeneralMethods.checkIsDisplayed(pdb.editDiagnosis);
		 //System.out.println(" the description"+pdb.editDiagnosis.getAttribute("ng-click"));
		 System.out.println(" the tag"+pdb.editDiagnosis.getTagName());
		 System.out.println(" the description"+pdb.editDiagnosis.getAttribute("ng-click"));
		 pdb.clickElement(pdb.editDiagnosis, "Diagnosis");
		 pdb.clickElement(pdb.attachDiagnosis, "Click attach Diagnosis");
		 GeneralMethods.clickFieldSelectList(pdb.diagnosis, diagnosis);
		 GeneralMethods.ClickButton(pdb.unattachDiagnosisButton);
		 GeneralMethods.ClickButton(pdb.attachDiagnosisDone);
		 GeneralMethods.ClickButton(pdb.diagnosisSave);
		 return true; 
	 }
	catch (Exception e){
		return false;
	}
 }
 
}
