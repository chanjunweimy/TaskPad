/**
 * Author: Chan Jun Wei, Lynnette Ng Hui Xian, Wang Taining
 * Product: TaskPad
 * Team: W13-3j
 */

package com.taskpad.ui;

import com.taskpad.inputproc.InputMain;

public class TaskPadMain{
	public static void main(String[] args){
		new InputMain();
		InputFrame inputFrame = new InputFrame();
		OutputFrame outputFrame = new OutputFrame();
		new MinimizeKey(inputFrame, outputFrame);
	}
}
