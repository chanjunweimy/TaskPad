package com.taskpad.ui;

import com.taskpad.inputproc.InputManager;

public class GuiManager {
	public GuiManager(){
	}
	
	public static void callExit(){
		System.exit(0);
	}
	
	public static void callOutput(String out){
		OutputFrame.output.append(out + "\n");
	}
	
	public static void passInput(String in){
		InputManager.receiveFromGui(in);
	}
}
