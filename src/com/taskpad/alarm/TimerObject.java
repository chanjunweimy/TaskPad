package com.taskpad.alarm;

import java.util.LinkedList;
import java.util.Timer;

/**
 * 
 * TimerObject is the timer
 * that counts how long the 
 * song plays
 */

//@author A0112084U

public class TimerObject {
	private static LinkedList<Timer> _timers = new LinkedList<Timer>();

	protected TimerObject(){
	}
	
	/* DEPRECATED
    protected TimerObject(boolean isOn, int seconds) {
        initializeTimer(isOn, seconds);
	}
	*/

	protected void setAlarmTimer(boolean isOn, int seconds) {
		Timer alarmTimer = new Timer();
        seconds *= 1000;
        if (!isOn){
        	alarmTimer.schedule(new AlarmOffTask(), seconds);
        } else {
        	alarmTimer.schedule(new AlarmOnTask(), seconds);
        }
        
        _timers.add(alarmTimer);
	}

	protected void setForceStopTimer(int seconds, int time) {
		Timer forceStopTimer = new Timer();
    	forceStopTimer.schedule(new ForceWaitTask(time), seconds);
    	
    	_timers.add(forceStopTimer);
	}
	
	protected static void cancelAlarms(){
		for (Timer timer: _timers){
			timer.cancel();
		}
	}
    
}
