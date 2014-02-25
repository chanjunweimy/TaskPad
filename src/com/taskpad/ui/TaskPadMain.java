/**
 * Author: Chan Jun Wei, Lynnette Ng Hui Xian, Wang Taining
 * Product: TaskPad
 * Team: W13-3j
 */

package com.taskpad.ui;

import javax.swing.SwingUtilities;

public class TaskPadMain{	
	public static void main(String[] args){
		runProgram();
	}

	private static void runProgram() {
		Runnable runTaskPad = new TaskPadLauncher();
		SwingUtilities.invokeLater(runTaskPad);
	}
}
