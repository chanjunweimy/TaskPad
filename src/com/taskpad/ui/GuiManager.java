package com.taskpad.ui;

import com.taskpad.inputproc.InputManager;

public class GuiManager {
	private static InputFrame _inputFrame;
	private static OutputFrame _outputFrame;
	
	public GuiManager(){
	}
	
	public GuiManager(InputFrame inputFrame, OutputFrame outputFrame){
		initialGuiManager(inputFrame, outputFrame);
	}

	private void initialGuiManager(InputFrame inputFrame,
			OutputFrame outputFrame) {
		setInputFrame(inputFrame);
		setOutputFrame(outputFrame);
	}
	
	public static void callExit(){
		_inputFrame.close();
		_outputFrame.close();
	}
	
	public static void callOutput(String out){
		OutputFrame.output.append(out + "\n");
	}
	
	public static void passInput(String in){
		InputManager.receiveFromGui(in);
	}

	private void setInputFrame(InputFrame _inputFrame) {
		GuiManager._inputFrame = _inputFrame;
	}

	public void setOutputFrame(OutputFrame _outputFrame) {
		GuiManager._outputFrame = _outputFrame;
	}
}
