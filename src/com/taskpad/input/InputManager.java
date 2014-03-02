/** This class is used for calling APIs from other packages
 * 
 * @author Lynnette
 *
 */

package com.taskpad.input;

import java.awt.Color;

import com.taskpad.data.DataManager;
import com.taskpad.execute.ExcecutorManager;
import com.taskpad.ui.GuiManager;


public class InputManager {
	
	public static void startInputProcessor(){
		new InputMain();
	}
	
	public static void receiveFromGui(String inputString){
		InputMain.receiveInput(inputString);
	}

	public static void outputToGui(String outputString){
		GuiManager.callOutput(outputString);
	}
	
	public static void outputFormatString(String outputString, Color c, boolean isBold){
		GuiManager.showSelfDefinedMessage(outputString, c, isBold);
	}
	
	public static void callGuiExit(){
		GuiManager.callExit();
	}
	
	public static void clearScreen(){
		GuiManager.clearOutput();
	}
	
	public static void passToExecutor(Input input){
		ExecutorManager.receiveFromInput(input);
	}
	
	public static int retrieveNumberOfTasks(){
		return DataManager.retrieveNumberOfTasks();
	}
	
}
