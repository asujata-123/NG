package com.nextgen.qa.automation.toolbox;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class NG7TestCase extends TestCase {
	public static String workingDir = System.getProperty("user.dir");
	public static String platform = System.getProperty("os.name");
	public static int defaultTimeout = AutomationSettings.getTimeout();
	public static int timeOut = defaultTimeout;
	public static int loginTimeOut = AutomationSettings.getLoginTimeout();
	public static int loginDelay = Integer.parseInt(AutomationSettings.getTestDataItem("delayLogin"));
	public static String deployment = "";
	public static String orgSelect = "";
	public static String MainSite = AutomationSettings.getBaseURL();
	public static String LoginSite = MainSite+"/login";
	public static int humanDelay;
	public static int eventDelay = AutomationSettings.getEventDelay();
	public static int clickDelay = AutomationSettings.getClickDelay();
    public static int waitForElementDelay = Integer.parseInt(AutomationSettings.getTestDataItem("delayWaitForElement"));
	public static int localStressLoop;
	public static int smarttypelist;
	public static String timeStamp;
	public static String testName;
	public static String testData = ""; // MGB 1/6/2015
	public static String testComment =""; // MGB 3/6/2015
	public static String tester;
	public static String demographics;
	public static long timer = 0; // MGB 10/23/2014 Keeps track of elapsed time in between test steps
	public static long timerSampleBefore = 0; // test step timer before
	public static long timerSampleAfter = 0; // test step timer after
	public static long readTimer = 0;
	public static int failCount= 0; 
	public static int passCount = 0;
	public static int blockCount = 0;
	public static int WIPCount = 0;
	public static int warningCount = 0;
	public static String issuesStr = "";
	public static String username = "";
	public static String password = "";
	public static String set = "";
	public static String functionalArgument = "";
	public static String artifact = "";
	public static String artifactPath = AutomationSettings.getTestDataItem("artifactPath");
    public static String chromeOptions = AutomationSettings.getOptions();
    public static String runParametersFile = "";
    public static BufferedWriter artifactObj; 
    public static String build;
    public static String jiraUrl = AutomationSettings.getTestDataItem("jiraUrl");
	public static String pythonZAPICall = AutomationSettings.getTestDataItem("PythonPath") +"ArtifactPostprocessing\\ArtifactPostprocessing.py --jiraServer="+jiraUrl
			+" --route=/jira";
    ////
    
    //public static String smartFilesOrg1 = AutomationSettings.getSmartFilesOrgPath("1");
    public static String orgString1 = AutomationSettings.getOrgString("1");
    public static String smartFilesOrg = "";
    public static String orgString = "";
    ////
    public static WebDriver driver;
	public static boolean catastrophicCheck = false;
	public static ArrayList<String> catastrophicMsg = new ArrayList<String>();
	public static boolean doNotDeliverCheck = false;
	public static ArrayList<String> doNotDeliverMsg = new ArrayList<String>();
	public static int catastrophicTimeout = AutomationSettings.getCatastrophicTimeout();
    public static String testStepTranscript = "";
      
    
	// Pattern Library css selectors
	public static String buttonCssSelector = "input[type='button'][class^='btn']";
	
	// Tags:
	public static String WIP = AutomationSettings.getWIP();
	public static String evoIssue = ",KNOWN ISSUE: EVO issue blocking the workflow";
	public static String tempWorkaround = AutomationSettings.getTempWA();
	public static String knownIssue = AutomationSettings.getKnownIssue();
	public static String compliance1 = AutomationSettings.getCompliance1();
	public static String catastrophicFlag = ",catastrophic check";
    public static String doNotDeliverFlag = ",doNotDeliver check"; 
    
    @Before
	public void setUp() throws Exception {
		
		// Reset summary report variables
		failCount= 0;
		passCount = 0;
		blockCount = 0;
		issuesStr = "";
		WIPCount = 0;
	}
	
	@Test
	public void during() throws Exception{
		
	}

	@After
	public void tearDown() throws Exception {
		
		// Reset summary report variables
		failCount= 0;
		passCount = 0;
		blockCount = 0;
		issuesStr = "";
		WIPCount = 0;
	}
}
