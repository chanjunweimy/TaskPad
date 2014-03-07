/**
 * Author: Chan Jun Wei, Lynnette Ng Hui Xian, Wang Taining
 * Product: TaskPad
 * Team: W13-3j
 */

package com.taskpad.launcher;

import java.util.LinkedList;

import javax.swing.SwingUtilities;

import org.eclipse.swt.awt.SWT_AWT;

import com.taskpad.data.DataFile;
import com.taskpad.data.DataManager;
import com.taskpad.execute.Task;

public class TaskPadMain{	

	private TaskPadMain(){
	}
	
	public static void main(String[] args){
		runProgram();
	}

	private static void runProgram() {
		LinkedList<Task> tasks = new LinkedList<Task>();
		DataManager.storeBack(tasks, DataFile.FILE);
		DataManager.storeBack(tasks, DataFile.FILE_PREV);
		
		Runnable runTaskPad = new TaskPadLauncher();
		SwingUtilities.invokeLater(runTaskPad);
	}
}
