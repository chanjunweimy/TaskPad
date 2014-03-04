/**
 * Author: Chan Jun Wei, Lynnette Ng Hui Xian, Wang Taining
 * Product: TaskPad
 * Team: W13-3j
 */

package com.taskpad.launcher;

import javax.swing.SwingUtilities;

public class TaskPadMain{	
	//private constructor signifies that
	//this class cannot be created as an instance
	private TaskPadMain(){
	}
	
	public static void main(String[] args){
		runProgram();
	}

	private static void runProgram() {
		Runnable runTaskPad = new TaskPadLauncher();
		SwingUtilities.invokeLater(runTaskPad);
	}
}
