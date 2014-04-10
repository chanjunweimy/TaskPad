//@author A0112084U

package com.taskpad.alarm;

import java.util.LinkedList;
import java.util.Timer;
import java.util.logging.Logger;

/**
 * 
 * TimerObject is the timer
 * that counts how long the 
 * song plays
 */

public class TimerObject {
	private final static Logger LOGGER = Logger.getLogger("TaskPad");
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
		LOGGER.info("cancelling...");
		LOGGER.info("timers number: " + _timers.size());
		for (int i = 0; i < _timers.size(); i++){
			_timers.get(i).cancel();
			_timers.get(i).purge();
			LOGGER.info("timer " + i + " " + _timers.get(i).toString());
		}
		_timers.clear();
	}
    
}
