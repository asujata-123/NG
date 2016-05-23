package com.nextgen.qa.automation.toolbox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Artifact {
	
	/**
	 * ARTIFACT CREATION AND MAINTENANCE: Opens the BufferedWriter 'writer' managing artifact file named 'fileName' at specified location ("C:\Temp").  
	 * Adds a header and timestamp to the file.  
	 * @param fileName
	 * @param deployment
	 * @param append
	 * @return writer
	 */
	public static BufferedWriter OpenArtifact(String fileName, String testName, String timeStamp){
		try {
			// MGB 05/02/2014 Reset summary report variables
			NG7TestCase.failCount= 0;
			NG7TestCase.passCount = 0;
			NG7TestCase.blockCount = 0;
			NG7TestCase.issuesStr = "";
			NG7TestCase.WIPCount = 0;
			NG7TestCase.warningCount = 0;
			NG7TestCase.timer = Calendar.getInstance().getTimeInMillis();
			NG7TestCase.testStepTranscript = "";
			
			FileWriter fstream;
			//fstream = new FileWriter("C:\\"+fileName+".csv", true);	// true = append; false = overwrite
			fstream = new FileWriter(fileName+".csv", true);	// true = append; false = overwrite
			BufferedWriter writer = new BufferedWriter(fstream);
			NG7TestCase.artifactObj = writer;
			return writer;			
		} catch (Exception e)
		{
		    System.err.println("Error: " + e.getMessage());
		    return null;
		}
	}

	public static void CreateArtifact(String fileName){
		try {
			FileWriter fstream;
			BufferedWriter writer = null;
			String timeStamp = GeneralMethods.GenerateTimeStamp();
			if ((timeStamp.contains("_0:") || GeneralMethods.CheckNLTHour(4)) && (fileName.equals("SmokeTestRunTranscript_DEV")) ||
					(fileName.equals("SmokeTestRunTranscript_trunk1")) ||
					(fileName.equals("SmokeTestRunTranscript_trunk2")) ||
					(fileName.equals("SmokeTestRunTranscript_releaseBranch")) ||
					(fileName.equals("SmokeTestRunTranscript_alpha")) ||
					(fileName.equals("SmokeTestRunTranscript_stable")) ||
					(fileName.equals("SmokeTestRunTranscript_exp"))) 
				{
				//fstream = new FileWriter("C:\\"+fileName+".csv", false);  // start a new file every day during the run of the specified hour
				fstream = new FileWriter(NG7TestCase.artifactPath+fileName+".csv", false);  // start a new file every day during the run of the specified hour
				writer = new BufferedWriter(fstream);
				//Artifact.WriteToArtifact(writer, "Time Stamp,Test Name,Test Step Description,Test Step Result,Comment,,,Timer,,Summary\n");
				Artifact.WriteToArtifact(writer, "Time Stamp,Test Name,Test Step Description,Test Data,Test Step Result,Comment,,,Timer,,Summary\n"); // MGB 1/6/2015 added testData
                Artifact.WriteToArtifact(writer, "Time Stamp,Test Name,Test Result,Passed test step count,Failed test step count,Blocked test step count,WIP test step count, Warning test setp count,Issues,SUMMARY,Tester\n");
                Artifact.CloseArtifact(writer);
			}
		} catch (Exception e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static void CreaterunParametersFile(String fileName){
		try {
			FileWriter fstream;
			BufferedWriter writer = null;
			String timeStamp = GeneralMethods.GenerateTimeStamp();
			if (timeStamp.contains("_1:")) {
				//fstream = new FileWriter("C:\\"+fileName+".csv", false);  // start a new file every day during the run of the specified hour
				fstream = new FileWriter(NG7TestCase.artifactPath+fileName+".csv", false);  // start a new file every day during the run of the specified hour
				writer = new BufferedWriter(fstream);
				//Artifact.WriteToArtifact(writer, "Time Stamp,Test Name,Test Step Description,Test Step Result,Comment,Bug,User Stories/Requirements,Summary,Tester\n");
				Artifact.WriteToArtifact(writer, "Time Stamp,Build Number\n");
                Artifact.CloseArtifact(writer);
			}
		} catch (Exception e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
	}
	
	/**
	 * ARTIFACT CREATION AND MAINTENANCE: Closes the artifact file managed by BufferedWriter 'writer'. 
	 * @param writer
	 */
	public static void CloseArtifact(BufferedWriter writer){
		try {
			//Report summary
			int countPass = NG7TestCase.passCount;
			int countFail = NG7TestCase.failCount;
			int countBlock = NG7TestCase.blockCount;
			int countWIP = NG7TestCase.WIPCount;
			int countWarning = NG7TestCase.warningCount;
			
			String result = "";
			if (NG7TestCase.failCount > 0) result = "FAIL";
			else if (NG7TestCase.blockCount > 0) result = "BLOCKED";
			else result = "PASS";
			Artifact.WriteToArtifact(writer, NG7TestCase.timeStamp +"," +NG7TestCase.testName +"," +result +"," +countPass +"," +countFail +"," +countBlock +"," +countWIP +"," +countWarning +"," +NG7TestCase.issuesStr +",SUMMARY" +"," +NG7TestCase.tester +",\n");
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void CloseParametersFile(BufferedWriter writer){
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ARTIFACT CREATION AND MAINTENANCE: Writes a string to the artifact file managed by BufferedWriter 'writer'.
	 * @param writer
	 * @param string
	 */
	public static void WriteToArtifact(BufferedWriter writer, String message){
		try {
			writer.write(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not write to artifact file.  Make sure file is closed before running test.");
			e.printStackTrace();
		}
	}

	public static void WriteToParametersFile(BufferedWriter writer, String message){
		try {
			writer.write(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not write to run parameters file.  Make sure file is closed before running test.");
			e.printStackTrace();
		}
	}

	/**
	
	 * ARTIFACT CREATION AND MAINTENANCE: Writes a formatted verification description and result to the artifact file managed by BufferedWriter 'writer'.
	 * @param writer
	 * @param string
	 * @param verificationResult
	 */
	public static void VerifyWriteToArtifact(BufferedWriter writer, String message, Boolean verificationResult){
		String result;
		if (verificationResult == true) result = "Pass"; else result = "Fail";
		WriteToArtifact(writer, message+","+result+"\n");
	}

	public static void VerifyWriteToArtifactS(BufferedWriter writer, String message, String verificationResult){
		message = message.replace(",", "");
		NG7TestCase.testStepTranscript += message+" | ";
		
		message = message.length() > 115 ? message.substring(0, 115) : message;  // MGB 1/21/2015 concatenate message to 115 chars
		message = NG7TestCase.timeStamp +"," +NG7TestCase.testName +"," +message;
		
		// Update P/F/B/WIP counters
		if (verificationResult.contains(NG7TestCase.WIP) == true) NG7TestCase.WIPCount++;
		
		if (verificationResult.contains(NG7TestCase.compliance1) == false && 
			verificationResult.contains(NG7TestCase.WIP) == false){ // MGB 4/8/2014
			if (verificationResult.contains("Pass")) NG7TestCase.passCount++;
			else if (verificationResult.contains("Fail")) NG7TestCase.failCount++;
			else if (verificationResult.contains("Block")) NG7TestCase.blockCount++;
			else if (verificationResult.contains("Warning")) NG7TestCase.warningCount++;
		
			if (verificationResult.contains(NG7TestCase.knownIssue)){
				String[] knownIssues = verificationResult.split(NG7TestCase.knownIssue); // MGB 3/12/2014
				String[] issues = knownIssues[1].split("\\s+"); // MGB 3/12/2014
				for (String issue : issues) NG7TestCase.issuesStr = NG7TestCase.issuesStr.contains(issue)==false ? NG7TestCase.issuesStr +" " +issue +" " : NG7TestCase.issuesStr;  
				NG7TestCase.issuesStr.replace(","," ");	// MGB 3/12/2014			
			}
		}
		////

		String trace = "";
		if (verificationResult.contains("Fail") || verificationResult.contains("Warning"))
			trace = ",failureTrace :" + NG7TestCase.testStepTranscript;
		
		// MGB 10/23/2014: Calculate the test step elapsed time
		long elapsedTime = Calendar.getInstance().getTimeInMillis() - NG7TestCase.timer;
		NG7TestCase.timer = Calendar.getInstance().getTimeInMillis();
		////
		
		// MGB 10/23/2014: Count commas in the verificationResult string so that the elapsed time lines up in the artifact
		String commaStr = ",,,,"; 
		int commaCount = StringUtils.countMatches(verificationResult, ",");
		for (int x = 0; x < commaCount; x++)
			commaStr = commaStr.replaceFirst(",", "");
		
		// MGB 3/6/2015: added NG7TestCase.testComment variable handling so that we can separate data from comments, since they will both share the same cell in the Jira container
		NG7TestCase.testData = NG7TestCase.testData.replace(","," ");
		if (! NG7TestCase.testData.equals("")) NG7TestCase.testData = "TEST STEP DATA: " + NG7TestCase.testData;
		if (! NG7TestCase.testComment.equals("")) NG7TestCase.testComment = NG7TestCase.testComment + "; ";
		NG7TestCase.testComment = NG7TestCase.testComment.replace(","," ") + NG7TestCase.testData;
		WriteToArtifact(writer, message+","+NG7TestCase.testComment+","+verificationResult+commaStr+elapsedTime+trace+"\n"); // MGB 1/6/2015 added testData
		NG7TestCase.testData = ""; // MGB 1/16/2015 clear testData
		NG7TestCase.testComment = ""; // MGB 1/16/2015 clear testData
	}
	
	public static void ReportCatastrophicFail(String currStepResult, String message){
	if (! currStepResult.equals("Pass")){
	    	NG7TestCase.catastrophicCheck |= true;
	    	NG7TestCase.catastrophicMsg.add("Catastrophic: "+message+","+currStepResult);
	    }
	}
	
	public static void ReportDoNotDeliverFail(String currStepResult, String message){
	if (! currStepResult.equals("Pass")){
	    	NG7TestCase.doNotDeliverCheck |= true;
	    	NG7TestCase.doNotDeliverMsg.add("doNotDeliver: "+message+","+currStepResult);
	    }
	}	
	
	public static void renderFlags(){
		NG7TestCase.testName = "CatastrophicChecks"+"_"+NG7TestCase.build;
		BufferedWriter artifact = Artifact.OpenArtifact(GeneralMethods.getArtifactName(), NG7TestCase.testName,NG7TestCase.timeStamp);
		String [] resultArray;
		String result = "";
		
		
		if (NG7TestCase.catastrophicCheck) {
			System.out.println("\n\n* * * * * Catastrophic checks:\n");
		    for (int i=0; i < NG7TestCase.catastrophicMsg.size(); i++){
		    	result = NG7TestCase.catastrophicMsg.get(i);
		   	    System.out.println(result);
		   	    resultArray = result.split(",");
		   	    if (resultArray[1].equals("Warning")){
		   	    	resultArray[0] = resultArray[1].toUpperCase()+"  "+resultArray[0];
		   	    	NG7TestCase.failCount++;
		   	    }	
		        Artifact.VerifyWriteToArtifactS(artifact, resultArray[0], resultArray[1]);
		    }
		}
		
		if (NG7TestCase.doNotDeliverCheck) {
			System.out.println("\n\n* * * * * doNotDeliver checks:\n");
		    for (int i=0; i < NG7TestCase.doNotDeliverMsg.size(); i++){
		    	result = NG7TestCase.doNotDeliverMsg.get(i);
		   	    System.out.println(result);
		   	    resultArray = result.split(",");
		   	    if (resultArray[1].equals("Warning")){
		   	    	resultArray[0] = resultArray[1].toUpperCase()+"  "+resultArray[0];
		   	    	NG7TestCase.failCount++;
		   	    }
		        Artifact.VerifyWriteToArtifactS(artifact, resultArray[0], resultArray[1]);
		    }
		}

		Artifact.CloseArtifact(artifact);
		
		try {
			artifact.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

