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
	
	private static GuiManager guiManager;
	private static ExcecutorManager executorManager;
	private static DataManager dataManager;
	
	public InputManager(){
		guiManager = new GuiManager();
		dataManager = new DataManager();
		executorManager = new ExcecutorManager();
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
	
	public static void clearScreen(){
		guiManager.clearOutput();
	}
	
	public static void passToExecutor(Input input){
		executorManager.receiveFromInput(input);
	}
	
	public static int retrieveNumberOfTasks(){
		return dataManager.retrieveNumberOfTasks();
	}
	
}
