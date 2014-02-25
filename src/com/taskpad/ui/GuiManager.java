package com.taskpad.ui;

import com.taskpad.inputproc.InputManager;

public class GuiManager {
	private static InputFrame _inputFrame;
	private static OutputFrame _outputFrame;
	
	public GuiManager(){
	}

	public static void initialGuiManager(InputFrame inputFrame,
		OutputFrame outputFrame) {
		setInputFrame(inputFrame);
		setOutputFrame(outputFrame);
	}
	
	public static void callExit(){
		closeAllWindows();
	}

	private static void closeAllWindows() {
		_inputFrame.close();
		_outputFrame.close();
	}
	
	public static void callOutput(String out){
		GuiManager._outputFrame.addLine(out + "\n");
	}
	
	public static void passInput(String in){
		InputManager.receiveFromGui(in);
	}

	private static void setInputFrame(InputFrame _inputFrame) {
		GuiManager._inputFrame = _inputFrame;
	}

	public static void setOutputFrame(OutputFrame _outputFrame) {
		GuiManager._outputFrame = _outputFrame;
	}
	
	public static void clearOutput(){
		GuiManager._outputFrame.clearOutputBox();
	}
}
