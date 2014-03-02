package com.taskpad.ui;

import java.awt.Color;

import com.taskpad.input.InputManager;

public class GuiManager {
	private static final String NEWLINE = "\n";
	private static final String MESSAGE_START_REMINDER = "HELLO! Reminder: ";
	private static InputFrame _inputFrame;
	private static OutputFrame _outputFrame;

	//not designed to be instantiated
	private GuiManager(){
	}

	//by default 
	public static void initialGuiManager() {
		_inputFrame = new InputFrame();
		_outputFrame = new FlexiFontOutputFrame();
	}

	/* deprecated
	public static void initialGuiManager(InputFrame inputFrame,
		OutputFrame outputFrame) {
		setInputFrame(inputFrame);
		setOutputFrame(outputFrame);
	}
	 */

	public static void callExit(){
		closeAllWindows();

	}

	private static void closeAllWindows() {
		_inputFrame.close();
		_outputFrame.close();
	}

	public static void callOutput(String out){
		_outputFrame.addLine(out + NEWLINE);
	}
	
	public static void showSelfDefinedMessage(String out, Color c, boolean isBold){
		_outputFrame.addSelfDefinedLine(out + NEWLINE, c, isBold);
	}

	public static void remindUser(){
		remindUser(MESSAGE_START_REMINDER);
	}
	
	private static void remindUser(String out){
		_outputFrame.addReminder(out + NEWLINE);
	}

	public static void passInput(String in){
		InputManager.receiveFromGui(in);
	}

	/* deprecated
	private static void setInputFrame(InputFrame _inputFrame) {
		GuiManager._inputFrame = _inputFrame;
	}

	private static void setOutputFrame(OutputFrame _outputFrame) {
		GuiManager._outputFrame = _outputFrame;
	}
	 */

	public static void clearOutput(){
		_outputFrame.clearOutputBox();
	}
}
