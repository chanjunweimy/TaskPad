package com.taskpad.alarm;

import com.taskpad.ui.GuiManager;


/**
 * 
 * @author Jun
 * 
 * and note that: Alarm can only support 
 * 10 s, not 10s
 * 
 */
public class AlarmExecutor {
	
	private static final String ERROR_NEGATIVE_DELAY = "Time should be a positive integer";
	private static final int TIME_FORCE_WAIT = 1;
	private static TimerObject _forceWaitTimer = new TimerObject();
	
	private static String _desc = "";
	
	private AlarmExecutor(int time){
	}
	
	protected static void initializeAlarm(String desc, int time){
		_desc = desc;
		if (time >= 1){
			_forceWaitTimer.setForceStopTimer(TIME_FORCE_WAIT, time - TIME_FORCE_WAIT);
		} else {
			GuiManager.callOutput(ERROR_NEGATIVE_DELAY);
		}
	}
	
	protected static void launchAlarm(int time) {		
		try {
			AlarmManager.setAlarm(_desc, time);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
