package com.taskpad.alarm;

import com.taskpad.ui.GuiManager;


/**
 * 
 * @author Jun
 *
 * temp class that is currently not put
 * into executor. It should be implemented inside
 * executor anyway
 * 
 * note that: now Alarm is without desrcription,
 * we should have description in later stage
 * 
 * and note that: Alarm can only support 
 * 10 s, not 10s
 * 
 */
public class AlarmExecutor {
	
	private static final String ERROR_NEGATIVE_DELAY = "Time should be a positive integer";
	private static final int TIME_FORCE_WAIT = 1;
	private static TimerObject _forceWaitTimer = new TimerObject();
	
	//make it cannot be initialized
	private AlarmExecutor(int time){
	}
	
	protected static void initializeAlarm(int time){
		if (time >= 1){
			_forceWaitTimer.setForceStopTimer(TIME_FORCE_WAIT, time - TIME_FORCE_WAIT);
		} else {
			GuiManager.callOutput(ERROR_NEGATIVE_DELAY);
		}
	}
	
	protected static void launchAlarm(int time) {//it should be a method in executor		
		try {
			AlarmManager.setAlarm(time);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
