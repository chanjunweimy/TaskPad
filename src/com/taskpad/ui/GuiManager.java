//@author A0112084U

package com.taskpad.ui;

import java.awt.Color;

import javax.swing.SwingUtilities;

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

	//invoke all frames to a thread
	public static void initialGuiManager() {
		Runnable runInitialization = new Runnable(){
			@Override
			public void run(){
				_outputFrame = new FlexiFontOutputFrame();
				_inputFrame = new InputFrame(); 
				_tableFrame = new OutputTableFrame();
			}
		};
		SwingUtilities.invokeLater(runInitialization);
	}

	/* deprecated
	public static void initialGuiManager(InputFrame inputFrame,
		OutputFrame outputFrame) {
		setInputFrame(inputFrame);
		setOutputFrame(outputFrame);
	}
	 */
	
	public static void callTable(Object[][] data){		
		final Object[][] userData = data;
		Runnable runCall = new Runnable(){
			@Override
			public void run(){
				swapFrame(_outputFrame, _tableFrame);
				_tableFrame.refresh(userData);
			}
		};
		SwingUtilities.invokeLater(runCall);
		
	}

	/**
	 * @param data
	 */
	private static void swapFrame(final GuiFrame firstFrame, final GuiFrame secondFrame) {	
		Runnable runSwap = new Runnable(){
			@Override
			public void run(){
				if (firstFrame.isVisible()){
					_isTableCalled = !_isTableCalled;
					firstFrame.hideWindow();
					secondFrame.showUp(firstFrame);
				}
				
				_inputFrame.requestFocusOnInputBox();
			}
		};
		SwingUtilities.invokeLater(runSwap);
		
	}
	
	
	public static void showWindow(final boolean isVisible){
		Runnable runShow = new Runnable(){
			@Override
			public void run(){
				_inputFrame.showWindow(isVisible);
				swapFrame( _tableFrame, _outputFrame);	
			}
		};
		SwingUtilities.invokeLater(runShow);
		
	
	}
	

	public static void callExit(){
		closeAllWindows();

	}

	private static void closeAllWindows() {
		Runnable runExit = new Runnable(){
			@Override
			public void run(){
					_inputFrame.close();
					_outputFrame.close();
					_tableFrame.close();
			}
		};
		SwingUtilities.invokeLater(runExit);
		
	}

	public static void callOutput(final String out){
		callOutputNoLine(out + NEWLINE);
		
	}
	
	public static void callOutputNoLine(final String out){
		Runnable runCall = new Runnable(){
			@Override
			public void run(){
				if (!_isDebug){
					swapFrame( _tableFrame, _outputFrame);
					_outputFrame.addLine(out);
				} else{
					System.out.println(out);
				}
			}
		};
		SwingUtilities.invokeLater(runCall);
		
	}
	
	/**
	 * @deprecated
	 * @param out
	 */
	protected static void callInputBox(String out){
		_inputFrame.setLine(out);
	}

	
	public static void showSelfDefinedMessage(String out, Color c, boolean isBold){
		showSelfDefinedMessageNoNewline(out + NEWLINE, c, isBold);
		
	}
	
	public static void showSelfDefinedMessageNoNewline(final String out, final Color c, final boolean isBold){
		Runnable runShow = new Runnable(){
			@Override
			public void run(){
				if (!_isDebug){
					swapFrame( _tableFrame, _outputFrame);
					_outputFrame.addSelfDefinedLine(out, c, isBold);
				} else{
					System.out.println(out);
				}
			}
		};
		SwingUtilities.invokeLater(runShow);
		
	}

	public static void startRemindingUser(){
		remindUser(MESSAGE_START_REMINDER);		
	}
	
	public static void remindUser(final String out){
		Runnable runRemind= new Runnable(){
			@Override
			public void run(){
				_outputFrame.addReminder(out + NEWLINE);
			}
		};
		SwingUtilities.invokeLater(runRemind);
			
		//_outputFrame.addReminder(NEWLINE + out + NEWLINE + NEWLINE);	--can i change this... TN
		// ExecutorManager.showReminder();	--should not put here? TN
	}

	protected static void passInput(final String in){
		Runnable runPass= new Runnable(){
			@Override
			public void run(){
				InputManager.receiveFromGui(in);
			}
		};
		SwingUtilities.invokeLater(runPass);
		
	}
	
	protected static void turnOffAlarm(){
		Runnable runAlarmOff = new Runnable() {
			@Override
			public void run() {
				try {
					AlarmManager.turnOffAlarm();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		SwingUtilities.invokeLater(runAlarmOff);
	}
	
	protected static void cancelAlarms() {
		Runnable runAlarmCancel = new Runnable() {
			@Override
			public void run() {
				try {
					AlarmManager.turnOffAlarm();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		SwingUtilities.invokeLater(runAlarmCancel);
	}
	
	protected static OutputFrame getOutputFrame() {
		return _outputFrame;
	}
	
	public static void clearOutput(){
		Runnable runClear = new Runnable() {
			@Override
			public void run() {
				_outputFrame.clearOutputBox();
			}
		};
		SwingUtilities.invokeLater(runClear);
		
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
