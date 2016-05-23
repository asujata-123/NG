package com.nextgen.qa.automation.Sandbox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Hashtable;

import org.junit.After;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.nextgen.qa.automation.smoketest.*;
import com.nextgen.qa.automation.testsuites.NG7Tests;
import com.nextgen.qa.automation.toolbox.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class AutoSmokeTest_dev{
	
public static void main(String arg[]) throws Exception   
	{   
		// Determine how many times loops will be executed; for hourly regressions, keep these at 1
		// If this number needs to change for debug, use TestDevelopmentSuite.java instead and change it there.
		NG7TestCase.localStressLoop = 1; // should always be 1 on this file
		NG7TestCase.smarttypelist = 2; // should always be 2 on this file
		////
		
		// Process command line variables
		String deployment = "";
		String environment = "";
		String set = "";
		String orgSelect = "";
		String cmdLineError = "* * * Command Line Error: Must specify command line arguments:" + "\n"
		+"     arg[0] = custom environment string in region url [ integration | release | clindoc | css | edi | scheduling ...]  (required)" + "\n"
		+"     arg[1] = test set [ PREREQ | SMOKE ]  (optional; defaults to SMOKE)";
		
		try{	
			environment = arg[0];
			environment = environment.toLowerCase();
			
			deployment = AutomationSettings.getTestDataItem("build");
			deployment = deployment.replace("environment", environment);

			if (environment.equals(""))
			{
				System.out.println(cmdLineError);
				return;
			}
			
			// This variable selects that data set for the specified region.  This data set is specified on the NG7qa\config.properties file
			orgSelect = AutomationSettings.getTestDataItem("dataSet_"+environment);
			
			if (orgSelect.equals("") || orgSelect.equals("30"))
			{
				System.out.println("WARNING: The environment you specified is not currently supported by the config.properties file of this automated test \n"
						+ "project, so it is being given a default test data set value of '1'.  If that environment needs to be supported, inform the automated \n"
						+ "test project administrator so this can be added to the list of supported environments.\n");
				
				System.out.println(cmdLineError);
				orgSelect = "1";
			}
			
		} catch (Exception e){
			System.out.println(cmdLineError);
			return;
		}
		
		try{
			set = (arg[1].equals("PREREQ") || arg[1].equals("SMOKE")) ? arg[1] : "SMOKE";
		} catch (Exception e){
				set = "SMOKE";
		}
		
		NG7TestCase.deployment = deployment; 	
		NG7TestCase.set = set;
		NG7TestCase.username = AutomationSettings.getUserName();
		NG7TestCase.username = AutomationSettings.getPassword();
		NG7TestCase.artifactPath = AutomationSettings.getTestDataItem("artifactPath");
		NG7TestCase.artifact = NG7TestCase.artifactPath + "SmokeTestRunTranscript_"+environment;
		NG7TestCase.orgString = AutomationSettings.getOrgString(orgSelect);
		NG7TestCase.smartFilesOrg = AutomationSettings.getSmartFilesOrgPath(orgSelect);
		NG7TestCase.orgSelect = orgSelect;
		NG7TestCase.build = environment;
		////
		
		// Start a new log file if needed
		FileWriter fstream, ts;
		String timeStamp = GeneralMethods.GenerateTimeStamp();
		NG7TestCase.timeStamp = timeStamp;
		 
		try{
			// MGB 9/29/2014: Clear the devdeploy artifact on the first run of the day
			if (environment.equals("integration")) {
				if ((timeStamp.contains("_0:")) || GeneralMethods.CheckNLTHour(4)) 
					fstream = new FileWriter(NG7TestCase.artifact, false);
				else fstream = new FileWriter(NG7TestCase.artifact, true);
				fstream.close();
			
            }
			else { 

			   // Clear the artifacts 
			   fstream = new FileWriter(NG7TestCase.artifact+".csv", false);
			   fstream.close();
			}
			
			ts = new FileWriter(NG7TestCase.artifactPath+"ts_"+environment+".txt", false);
			ts.write(timeStamp);
			ts.close();
						
		} catch (Exception e){
			System.out.println("Exception thrown when attempting to create a new log file");
			return;
		}
		////
		
		// Run the suite
		Artifact.CreateArtifact(NG7TestCase.artifact);
		String test = "";
		int exitcode = 0;
		try {
			test = "PreRequisite";
			//boolean catastrophicCheck = PreRequisite.test(); // run initial catastrophic check
			boolean catastrophicCheck = false; // run initial catastrophic check
			if (catastrophicCheck)
				exitcode = 1;
		
			else {
				test = "NG7Tests_dev";				
				catastrophicCheck = NG7Tests_dev.test();  // run NG7 tests
				if (catastrophicCheck)
					exitcode = 1;
				else
					exitcode = 0;
			}
		
		} catch (Exception e) {
			System.out.println("\nAUTO SMOKE TEST SUITE: Exception thrown when running test " + NG7TestCase.testName + "\n" + e.getMessage());
			exitcode = 1;
		}
	
		// Clean up and exit
		Process process = Runtime.getRuntime().exec("taskkill /F /FI \"Imagename eq chromedriver.exe\"");
		
		// Print out warning flags to console and artifact
		Artifact.renderFlags();
		
		System.exit(exitcode);
	}   
}

