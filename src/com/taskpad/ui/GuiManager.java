//@author A0112084U

package com.taskpad.ui;

import java.awt.Color;

import com.taskpad.alarm.AlarmManager;
import com.taskpad.input.InputManager;

public class GuiManager {
	private static final String NEWLINE = "\n\n";		
	private static final String MESSAGE_START_REMINDER = "Today's Tasks ";
	private static InputFrame _inputFrame;
	private static OutputFrame _outputFrame;
	private static OutputTableFrame _tableFrame;
	private static boolean _isDebug = false;
	private static boolean _isTableCalled = false;

	//not designed to be instantiated
	private GuiManager(){
	}

	//by default 
	public static void initialGuiManager() {
		_outputFrame = new FlexiFontOutputFrame();
		_inputFrame = new InputFrame(); 
		_tableFrame = new OutputTableFrame();
	}

	/* deprecated
	public static void initialGuiManager(InputFrame inputFrame,
		OutputFrame outputFrame) {
		setInputFrame(inputFrame);
		setOutputFrame(outputFrame);
	}
	 */
	
	public static void callTable(Object[][] data){		
		swapFrame(_outputFrame, _tableFrame);
		_tableFrame.refresh(data);
	}

	/**
	 * @param data
	 */
	private static void swapFrame(GuiFrame firstFrame, GuiFrame secondFrame) {		
		if (firstFrame.isVisible()){
			_isTableCalled = !_isTableCalled;
			firstFrame.showWindow(false);
			secondFrame.showUp(firstFrame);
		}
		
		_inputFrame.requestFocusOnInputBox();
	}
	
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
		if (!_isDebug){
			swapFrame( _tableFrame, _outputFrame);
			_outputFrame.addLine(out + NEWLINE);	
		} else{
			System.out.println(out + NEWLINE);
		}
	}
	
	public static void callOutputNoLine(String out){
		if (!_isDebug){
			swapFrame( _tableFrame, _outputFrame);
			_outputFrame.addLine(out);
		} else{
			System.out.println(out);
		}
	}
	
	/**
	 * @deprecated
	 * @param out
	 */
	protected static void callInputBox(String out){
		_inputFrame.setLine(out);
	}

	
	public static void showSelfDefinedMessage(String out, Color c, boolean isBold){
		if (!_isDebug){
			swapFrame( _tableFrame, _outputFrame);
			_outputFrame.addSelfDefinedLine(out + NEWLINE, c, isBold);	
		} else{
			System.out.println(out + NEWLINE);
		}
	}
	
	public static void showSelfDefinedMessageNoNewline(String out, Color c, boolean isBold){
		if (!_isDebug){
			swapFrame( _tableFrame, _outputFrame);
			_outputFrame.addSelfDefinedLine(out, c, isBold);
		} else{
			System.out.println(out);
		}
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
	
	public static void clearOutput(){
		_outputFrame.clearOutputBox();
	}
	
	//for debug
	public static boolean getInputFrameVisibility(){
		boolean isNormal = _inputFrame.isVisible();
		return isNormal;
	}
	
	public static boolean getOutputFrameVisibility(){
		boolean isNormal = _outputFrame.isVisible();
		return isNormal;
	}
	
	public static boolean getTableVisibility(){
		boolean isNormal = _tableFrame.isVisible();
		return isNormal;
	}
	
	public static boolean isTableActive(){
		return _isTableCalled;
	}

	public static void setDebug(boolean isDebugFlag){
		_isDebug = isDebugFlag;
		_isTableCalled = false;
	}

	/* deprecated
	private static void setInputFrame(InputFrame _inputFrame) {
		GuiManager._inputFrame = _inputFrame;
	}

	private static void setOutputFrame(OutputFrame _outputFrame) {
		GuiManager._outputFrame = _outputFrame;
	}
	 */
	
}
