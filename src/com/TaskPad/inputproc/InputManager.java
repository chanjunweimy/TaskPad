package com.TaskPad.inputproc;

import com.TaskPad.execute.ExcecutorManager;
import com.TaskPad.ui.GuiManager;

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
	
	public void receiveFromGui(String inputString){
		InputMain.receiveInput(inputString);
	}

	public void outputToGui(String outputString){
		guiManager.callOutput(outputString);
	}
	
	public void callGuiExit(){
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
