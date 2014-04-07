//@author A0112084U

package com.taskpad.launcher;


import javax.swing.SwingUtilities;
 
/**
 * 
 * Author: Chan Jun Wei, Lynnette Ng Hui Xian, Wang Taining
 * Product: TaskPad
 * Team: W13-3j
 *
 */
public class TaskPadMain{
	
	private TaskPadMain(){
	}
	
	public static void main(String[] args){
		setUpLogging();
		runProgram();
	}
	  
	private static void runProgram() {
		Runnable runTaskPad = new TaskPadLauncher();
		SwingUtilities.invokeLater(runTaskPad);
	} 

	private static void setUpLogging() {
		LogManager.getInstance().setUpGlobalLogger();
	}
}
