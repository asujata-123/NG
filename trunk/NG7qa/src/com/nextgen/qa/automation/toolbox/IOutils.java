/*
 * generic class to get input stream and loading properties
 */
package com.nextgen.qa.automation.toolbox;
 
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOutils {


  public IOutils()
  {
  }
  
 /*
  *
  */
  public static InputStream getInputStream(String file) {
  
    InputStream input = null;
 
	try {
 
		input = new FileInputStream(file);
	  
	} catch (IOException ex) {
		ex.printStackTrace();
	} 
 
    return input; 
  }
 
 
}