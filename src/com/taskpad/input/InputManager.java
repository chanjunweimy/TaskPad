package com.taskpad.input;

import com.taskpad.execute.ExcecutorManager;
import com.taskpad.ui.GuiManager;

/** This class is used for calling APIs from other packages
 * 
 * @author Lynnette
 *
 */

public class InputManager {
	
	private static GuiManager guiManager;
	private static ExcecutorManager executorManager;
	
	public InputManager(){
		guiManager = new GuiManager();
	}
	
	public static void receiveFromGui(String inputString){
		InputMain.receiveInput(inputString);
	}

	public static void outputToGui(String outputString){
		guiManager.callOutput(outputString);
	}
	
	public static void callGuiExit(){
		guiManager.callExit();
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
