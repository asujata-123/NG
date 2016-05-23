package com.nextgen.qa.automation.toolbox;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

//import NG7Libraries.NG7TestLib;

public class MouseMethods {

	/**
	 * MOUSE OPERATION: Actions driven sequence that does the following:<li>
	 * moveToElement(element)<li>
	 * click<li>
	 * sendKeys(keyString)
	 * <p>
	 * @param driver
	 * @param element
	 * @param keyString
	 */
	public static boolean HoverOverClickSendKeys(WebDriver driver, WebElement element, String keyString, int offsetX, int offsetY){	
		try{
			Actions hoverOver = new Actions(driver);
			//hoverOver.moveToElement(element, offsetX, offsetY).click().sendKeys(keyString).build().perform();
			GeneralMethods.delay(AutomationSettings.getEventDelay());
			hoverOver.moveToElement(element, offsetX, offsetY).click().sendKeys(keyString);
			hoverOver.sendKeys(Keys.TAB).build().perform();
			return true;
		} catch (Exception e){
			System.out.println("Exception thrown on mouse operation method HoverOverClickSendKeys " + e.getMessage());
			return false;
		}
	}

	/**
	 * MOUSE OPERATION: Actions driven sequence that does the following:<li>
	 * moveToElement(element)<li>
	 * click<li>
	 * <p>
	 * @param driver
	 * @param element
	 */
	public static boolean HoverOverClick(WebDriver driver, WebElement element, int offsetX, int offsetY){	
		try{
			Actions hoverOver = new Actions(driver);
			hoverOver.moveToElement(element, offsetX, offsetY).click().build().perform();
			GeneralMethods.delay(AutomationSettings.getClickDelay());
			return true;
		} catch (Exception e){
			System.out.println("Exception thrown on mouse operation method HoverOverClick " + e.getMessage());
			return false;
		}
	}
	
	public static boolean HoverOver(WebDriver driver, WebElement element, int offsetX, int offsetY){	
		try{
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
			Actions hoverOver = new Actions(driver);
			hoverOver.moveToElement(element, offsetX, offsetY).build().perform();
			driver.manage().timeouts().implicitlyWait(NG7TestCase.timeOut, TimeUnit.MILLISECONDS);
			return true;
		} catch (Exception e){
			System.out.println("Exception thrown on mouse operation method HoverOver " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * MOUSE OPERATION: Actions driven sequence that does the following:<li>
	 * dragAndDropBy(element, dragToX, dragToY)<li>
	 * <p>
	 * @param driver
	 * @param element
	 */
	public static boolean DragToElement(WebDriver driver, WebElement element, WebElement target){
	//public static boolean DragToElement(WebDriver driver, WebElement element, int dragToX, int dragToY){
		try{
			Actions hoverOver = new Actions(driver);
			org.openqa.selenium.Dimension elementSize = element.getSize();
			int elementCenterX = elementSize.getWidth()/2;
			int elementCenterY = elementSize.getHeight()/2;
			
			org.openqa.selenium.Dimension targetSize = target.getSize();
			int targetCenterX = targetSize.getWidth()/2;
			int targetCenterY = targetSize.getHeight()/2;
			
			int offsetX = targetCenterX - elementCenterX;
			int offsetY = targetCenterY - elementCenterY;
			
			MouseMethods.HoverOverClickContext(driver, element, elementCenterY, elementCenterY);
			hoverOver.moveToElement(element).clickAndHold(element).build().perform();
			hoverOver.moveToElement(target, offsetX, offsetY).release().build().perform();
			GeneralMethods.delay(AutomationSettings.getEventDelay());
			return true;
		} catch (Exception e){
			System.out.println("Exception thrown on mouse operation method HoverOverClickAndDrag " + e.getMessage());
			return false;
		}
	}

	/**
	 * MOUSE OPERATION: Actions driven sequence that does the following:<li>
	 * moveToElement(element)<li>
	 * contextClick<li>
	 * <p>
	 * @param driver
	 * @param element
	 */
	public static boolean HoverOverClickContext(WebDriver driver, WebElement element, int offsetX, int offsetY){	
		try{
			Actions hoverOver = new Actions(driver);
			hoverOver.moveToElement(element, offsetX, offsetY).contextClick().build().perform();
			GeneralMethods.delay(AutomationSettings.getEventDelay());
			return true;
		} catch (Exception e){
			System.out.println("Exception thrown on mouse operation method HoverOverClickContext " + e.getMessage());
			return false;
		}
	}

	/**
	 * MOUSE OPERATION: Actions driven sequence that does the following:<li>
	 * moveToElement(element)<li>
	 * doublClick<li>
	 * <p>
	 * @param driver
	 * @param element
	 */
	public static boolean HoverOverDblClick(WebDriver driver, WebElement element, int offsetX, int offsetY){
		try{
			Actions hoverOver = new Actions(driver);
			hoverOver.moveToElement(element, offsetX, offsetY).doubleClick().build().perform();
			GeneralMethods.delay(AutomationSettings.getEventDelay());
			return true;
		} catch (Exception e){
			System.out.println("Exception thrown on mouse operation method HoverOverDblClick " + e.getMessage());
			return false;
		}
	}

	/**
	 * MOUSE OPERATION: Actions driven sequence that does the following:<li>
	 * moveToElement(element)<li>
	 * doublClick<li>
	 * sendKeys(keyString)
	 * <p>
	 * @param driver
	 * @param element
	 * @param keyString
	 */
	public static boolean HoverOverDblClickSendKeys(WebDriver driver, WebElement element, String keyString, int offsetX, int offsetY){	
		try{
		   Actions hoverOver = new Actions(driver);
		   hoverOver.moveToElement(element, offsetX, offsetY).doubleClick().build().perform();
		   GeneralMethods.delay(AutomationSettings.getEventDelay());
		   return true;
		} catch (Exception e){
			System.out.println("Exception thrown on mouse operation method HoverOverDblClickSendKeys " + e.getMessage());
			return false;
		}
	}
	
	public static boolean HoverAwaySendKeys(WebDriver driver, WebElement element, String keyString, int offsetX, int offsetY){	
		try{
		   Actions hoverAway = new Actions(driver);
		   hoverAway.moveToElement(element, offsetX, offsetY).click().build().perform();
		   GeneralMethods.delay(AutomationSettings.getEventDelay());
		   hoverAway.sendKeys(keyString).build().perform();
		   return true;
		} catch (Exception e){
			System.out.println("Exception thrown on mouse operation method HoverOverDblClickSendKeys " + e.getMessage());
			return false;
		}
	}

	/**
	 * MOUSE OPERATION: Actions driven sequence that does the following:<li>
	 * clickAndHold(element)<li>
	 * delay<li>
	 * release<p>
	 * @param driver
	 * @param element
	 */
	public static boolean ClickAndHold(WebDriver driver, WebElement element, int offsetX, int offsetY){	
		try{
			Actions clickAndHold = new Actions(driver);
			clickAndHold.clickAndHold(element).build().perform();
			GeneralMethods.delay(AutomationSettings.getEventDelay());
			clickAndHold.release(element).build().perform();
			return true;
		} catch (Exception e){
			System.out.println("Exception thrown on mouse operation method ClickAndHold" + e.getMessage());
			return false;
		}
	}
	
	public static boolean SelectFromList(WebDriver driver, WebElement element, String cssData, int choice, int offsetX, int offsetY){	
		try{
			Actions hoverOver = new Actions(driver);
			hoverOver.clickAndHold(element).build().perform();
			List<WebElement> choices = element.findElements(By.cssSelector());
			WebElement option = choices.get(choice);
			hoverOver.moveToElement(option).click().build().perform();
			hoverOver.release(element).build().perform();
			return true;
		} catch (Exception e){
			System.out.println("Exception thrown on mouse operation method SelectFromList " + e.getMessage());
			return false;
		}
	}
	
	 public static boolean robotDragAndDropElement(WebDriver driver, WebElement dragFrom, WebElement dragTo) {
	       try {
	        	// Setup robot
	        	Robot robot = new Robot();
	        	robot.setAutoDelay(500);

	        	// Get location of elements
	        	org.openqa.selenium.Point toLocation = dragTo.getLocation();
	        	org.openqa.selenium.Point fromLocation = dragFrom.getLocation();
	        	
	        	//Fullscreen page so selenium coordinates work
	        	robot.mouseMove(fromLocation.x+100, fromLocation.y+125);
	        	//MouseMethods.HoverOver(driver, dragFrom, 0, 0);
	        	robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	        	//GeneralLib.delay(AutomationSettings.getEventDelay());
	       
	        	/*// Get size of elements
	        	org.openqa.selenium.Dimension fromSize = dragFrom.getSize();
	        	org.openqa.selenium.Dimension toSize = dragTo.getSize();

	        	//Get centre distance
	        	int xCentreFrom = fromSize.width / 2;
	        	int yCentreFrom = fromSize.height / 2;
	        	int xCentreTo = toSize.width / 2;
	        	int yCentreTo = toSize.height / 2;

	        	
	        	
	        	//Make Mouse coordinate centre of element
	        	toLocation.x += xOffset + xCentreTo;
	        	toLocation.y += yOffset + yCentreTo;
	        	fromLocation.x += xOffset + xCentreFrom;
	        	fromLocation.y += yOffset + yCentreFrom;*/
	        
	        	//robot.mouseMove(fromLocation.x+100, fromLocation.y+100);
	        	
	        	//Click and drag
	        	//robot.mousePress(InputEvent.BUTTON1_MASK);
	        
	        	//Drag events require more than one movement to register
	        	//Just appearing at destination doesn't work so move halfway first
	        	//robot.mouseMove(((toLocation.x - fromLocation.x) / 2) + fromLocation.x, ((toLocation.y - fromLocation.y) / 2) + fromLocation.y);
	        
	        	//Move to final position
	        	robot.mouseMove(fromLocation.x+175, toLocation.y+125);
	        	robot.mouseMove(fromLocation.x+230, toLocation.y+125);
	        
	            //Drop
	        	robot.mouseRelease(InputEvent.BUTTON1_MASK);
	        	return true;
	        } catch (Exception e) {
	        	System.out.println("Exception thrown on dragAndDropElement " + e.getMessage());
	        	return false;
	        }
	 }
	 
}

