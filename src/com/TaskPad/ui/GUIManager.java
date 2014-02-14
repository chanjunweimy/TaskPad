package com.TaskPad.ui;

import com.TaskPad.inputproc.Input;

public class GUIManager {
	public GUIManager(){
	}
	
	public static void callExit(){
		System.exit(0);
	}
	
	public static void callOutput(String out){
		OutputFrame.output.append(out + "\n");
	}
	
	public static void passInput(String in){
		Input.receiveInput(in);
	}
}
