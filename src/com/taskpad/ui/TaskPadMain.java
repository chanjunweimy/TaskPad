/**
 * Author: Chan Jun Wei, Lynnette Ng Hui Xian, Wang Taining
 * Product: TaskPad
 * Team: W13-3j
 */

package com.taskpad.ui;

import javax.swing.SwingUtilities;

import com.taskpad.input.InputMain;

public class TaskPadMain{
	public static void main(String[] args){
		Runnable runTaskPad = new Runnable() {
			public void run() {
				new InputMain();
				InputFrame inputFrame = new InputFrame();
				OutputFrame outputFrame = new OutputFrame();
				GuiManager.initialGuiManager(inputFrame, outputFrame);
			}
		};
		SwingUtilities.invokeLater(runTaskPad);
	}
}
