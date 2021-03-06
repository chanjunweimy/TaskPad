//@author A0112084U

package com.taskpad.alarm;

import java.awt.Color;

import javax.swing.JApplet;

import com.taskpad.ui.GuiManager;

public class AlarmManager extends JApplet{

	
	private static final long serialVersionUID = 4348001564533802036L;		//Randomly generated
	//private static final Exception EXCEPTION_ERROR = new Exception();
	private final static String SONG_DEFAULT = "pokemon.mid";
	private static Sound _alarm = null;
	private final static int ALARM_DURATION = 60;
	private static boolean _isPlaying = false;
	private static TimerObject _startAlarmTimer = new TimerObject();
	
	private static String _desc = "";
	private static final String MESSAGE_ALARM = "ALARM!! %s";
	private static final String MESSAGE_CANCEL_ALARM = "Cancelling Alarm";
	private static final String MESSAGE_STOP_ALARM = "Stopping Alarm";
	private static final String MESSAGE_NO_ALARM = "No alarm has been set";
	private static final String MESSAGE_UNABLE_SET_ALARM = "Error: Unable to set alarm";
	
	private AlarmManager(){
		/* deprecated, we no longer wants it to be an object
		try {
			initializeSong();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		*/
	}
	
	/* deprecated
	public AlarmManager(String otherSong) throws Exception{
		initializeSong(otherSong);
	}
	*/
	
	private static void initializeSong() throws Exception{
		_alarm = setUpSong();
	}
	
	public static void setAlarm(String desc, int time){
		_desc = desc;
		ensureInitialization();

		boolean isOn = true;
		_startAlarmTimer.setAlarmTimer(isOn, time);
	}  
	
	private static void ensureInitialization() {
		if (_alarm == null){
			try {
				initializeSong();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	private static Sound setUpSong() throws Exception {
		Sound testSong = new Sound(SONG_DEFAULT);
		return testSong;
	}
	
	protected static void turnOnAlarm() throws Exception{
		showGuiWindow();
		outputAlarmDesc();
		
		if (_alarm == null){
			GuiManager.callOutput(MESSAGE_UNABLE_SET_ALARM);
			return;
			//throw EXCEPTION_ERROR;
		}
		
		if (!_isPlaying){
			_alarm.playSound();
			_isPlaying = true;
		} else {
			turnOffAlarm();
			_alarm.playSound();
			_isPlaying = true;
		}
	}
	
	private static void outputAlarmDesc() {
		String alarmOutput = String.format(MESSAGE_ALARM, _desc);
		GuiManager.showSelfDefinedMessage(alarmOutput, Color.RED, true);		
	}

	private static void showGuiWindow() {
		GuiManager.showWindow(true);		
	}

	public static void turnOffAlarm() throws Exception{
		
		if (_alarm == null){
			GuiManager.callOutput(MESSAGE_NO_ALARM);
			//throw EXCEPTION_ERROR;
			return;
		}
			
		if (_isPlaying){
			_alarm.stopSound();
			_isPlaying = false;
		} else{
			//throw EXCEPTION_ERROR;
			return;
		}
		GuiManager.callOutput(MESSAGE_STOP_ALARM);
	}
	
	public static void cancelAlarms() throws Exception{
		/*
		if (_alarm == null){
			//throw EXCEPTION_ERROR;
			return;
		}
		*/

				
		if (_isPlaying){
			_alarm.stopSound();
			TimerObject.cancelAlarms();
			_isPlaying = false;
		} else {
			TimerObject.cancelAlarms();
		}
		GuiManager.callOutput(MESSAGE_CANCEL_ALARM);

	}
	
	protected static void runAlarm() throws Exception{
		turnOnAlarm();
		boolean isOn = false;
		_startAlarmTimer.setAlarmTimer(isOn, ALARM_DURATION);
	}
	
	public static void initializeAlarm(String desc, int time){
		AlarmExecutor.initializeAlarm(desc, time);
	} 
	
	/* deprecated
	private void initializeSong(String otherSong) throws Exception{
		_alarm = setUpSong(otherSong);
	}*/
	
	/* deprecated
	protected void playSong(){
		assert (_alarm != null);
		_alarm.playSound();
		_isPlaying = true;
	}
	*/
	/* deprecated
	private Sound setUpSong(String otherSong) throws Exception {
		Sound testSong = new Sound(otherSong);
		return testSong;
	}
	*/
	
	/* test alarm
	public static void main(String[] args){
		AlarmManager alarm = null;
		try {
			alarm = new AlarmManager();
			alarm.runAlarm();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			System.err.println("cannot start song");
			
		}
	}
	*/
}
