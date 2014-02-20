package com.TaskPad.inputproc;

import com.TaskPad.ui.GuiManager;

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
	
	public static void passToExecutor(Input input){
		//
	}
	
	public static int retrieveNumberOfTasks(){
		int numberOfTasks = 0;
		//Call function to get number
		return numberOfTasks;
	}
	
}
