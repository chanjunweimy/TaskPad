package com.taskpad.ui;

import java.awt.Color;

import com.taskpad.alarm.AlarmManager;
import com.taskpad.input.InputManager;

public class GuiManager {
	private static final String NEWLINE = "\n\n";		
	private static final String MESSAGE_START_REMINDER = "Today's Tasks ";
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
	
	public static void showWindow(boolean isVisible){
		_inputFrame.showWindow(isVisible);
		_outputFrame.showWindow(isVisible);
	}

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
	
	public static void callOutputNoLine(String out){
		_outputFrame.addLine(out);
	}
	
	public static void callInputBox(String out){
		_inputFrame.setLine(out);
	}
	
	public static void showSelfDefinedMessage(String out, Color c, boolean isBold){
		_outputFrame.addSelfDefinedLine(out + NEWLINE, c, isBold);	
	}
	
	public static void showSelfDefinedMessageNoNewline(String out, Color c, boolean isBold){
		_outputFrame.addSelfDefinedLine(out, c, isBold);
	}

	public static void startRemindingUser(){
		remindUser(MESSAGE_START_REMINDER);
	}
	
	public static void remindUser(String out){
		_outputFrame.addReminder(out + NEWLINE);	
		//_outputFrame.addReminder(NEWLINE + out + NEWLINE + NEWLINE);	--can i change this... TN
		// ExecutorManager.showReminder();	--should not put here? TN
	}

	protected static void passInput(String in){
		InputManager.receiveFromGui(in);
	}
	
	protected static void turnOffAlarm(){
		try {
			AlarmManager.turnOffAlarm();
		} catch (Exception e) {
			//System.err.println(e.getMessage());
			//do nothing
		}
	}
	
	protected static void cancelAlarms() {
		try {
			AlarmManager.cancelAlarms();
		} catch (Exception e) {
			//do nothing
		}
	}
	
	protected static OutputFrame getOutputFrame() {
		return _outputFrame;
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
