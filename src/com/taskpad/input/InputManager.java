package com.taskpad.input;

import com.taskpad.data.DataManager;
import com.taskpad.execute.ExcecutorManager;
import com.taskpad.ui.GuiManager;

/** This class is used for calling APIs from other packages
 * 
 * @author Lynnette
 *
 */

public class InputManager {
	
	public static void receiveFromGui(String inputString){
		InputMain.receiveInput(inputString);
	}

	public static void outputToGui(String outputString){
		GuiManager.callOutput(outputString);
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
