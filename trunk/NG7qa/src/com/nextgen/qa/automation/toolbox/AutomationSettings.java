package com.nextgen.qa.automation.toolbox;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;


public class AutomationSettings {
	
   private static final Properties props;
   //private static final String configFile = NG7TestCase.workingDir+"\\configuration\\config.properties";
   // 04/11/2014 MGB notes:
   // - hash to retrieve tester name from config file, indexed by test name
   static {
		    	           	
	        props = new Properties();
	        String configFile="";
	        if (NG7TestCase.platform.toLowerCase().indexOf("windows")>=0) configFile = NG7TestCase.workingDir+"\\configuration\\config.properties";
			else configFile = NG7TestCase.workingDir+"//configuration//config.properties";
	 	  	InputStream input = IOutils.getInputStream(configFile);
	 	  	
	 	   	// load a properties file
	 	   	try {
	 	     
	 	   		props.load(input);
	 	     
	 	   	} catch (IOException ex) {
	 	   		ex.printStackTrace();
	 	   	} finally {
	 	   		if (input != null) {
	 	   			try {
	 	   				input.close();
	 	   			} catch (IOException e) {
	 	   				e.printStackTrace();
	 	   			}
	 	   		}
	 	   	}
		   		   
	}   
	    
	     	    
   //getters	    
	
	public static int getTimeout() {		
	   int timeout = Integer.parseInt(props.getProperty("timeout","30"));				
	   return timeout;		
     }
	
	public static int getLoginTimeout() {		
		   int timeout = Integer.parseInt(props.getProperty("loginTimeout","30"));				
		   return timeout;		
	     }

	public static String getBaseURL() {		
	   return props.getProperty("baseurl","");					   		
     }
	
	public static String getUserName() {		
		   return props.getProperty("username","");					   		
	     }

	public static String getPassword() {		
		   return props.getProperty("password","");					   		
	     }
	
	public static String getOptions() {		
		   return props.getProperty("options","");					   		
	     }

	// Delays
	public static int getEventDelay() {		
		   int delay = Integer.parseInt(props.getProperty("eventdelay","30"));				
		   return delay;		
	     }
	
	public static int getClickDelay() {		
		   int delay = Integer.parseInt(props.getProperty("clickdelay","30"));				
		   return delay;		
	     }
	public static int getDelay(String type){
		   int delay = Integer.parseInt(props.getProperty("delay"+type,"30"));		
		   return delay;
	}
	////
	
	public static int getLocalStressLoopIterations() {		
		   int iterations = Integer.parseInt(props.getProperty("localstressloop","30"));				
		   return iterations;		
	     }
	
	public static String getWIP() {		
		   return props.getProperty("WIP","30");					   		
	     }
	
	public static String getKnownIssue() {		
		   return props.getProperty("knownIssue","30");					   		
	     }
	
	public static String getTempWA() {		
		   return props.getProperty("tempWorkaround","30");					   		
	     }
	
	public static String getCompliance1() {		
		   return props.getProperty("compliance1","30");					   		
	     }
	
	public static String getArtifact() {		
		   return props.getProperty("artifact","30");					   		
	     }
	
	public static String getRunParametersFilename() {		
		   return props.getProperty("parametersFile","30");					   		
	     }
	
	public static String getSmartFilesOrgPath(String pathNumber) {		
		   return props.getProperty("smartFilesOrg"+pathNumber,"30");					   		
	     }
	
	public static String getOrgString(String strNumber) {		
		   return props.getProperty("orgString"+strNumber,"30");					   		
	     }
	
	public static String getBuild(String build) {		
		   return props.getProperty("build"+build,"30");					   		
	     }
	
	public static String getTestDataItem(String buildIndex) {
		return props.getProperty(buildIndex,"30");
	}

	public static int getCatastrophicTimeout() {
		return Integer.parseInt(props.getProperty("catastrophicTimeout", "30"));
	}
	
    /*
     * test
     */
    /* public static void main(String[] args) {
    
       Enumeration<Object> keys;
       Enumeration<Object> vals;
             
    	// get the property value and print it out
    	System.out.println(AutomationSettings.getTimeout());
    	System.out.println(AutomationSettings.getBaseURL());
   	
    	keys = props.keys();
        vals = props.elements();
     
   	    while ( keys.hasMoreElements() )
   	      System.out.println(keys.nextElement() +  "=" + vals.nextElement() );
   	    
       }
*/
	
}
