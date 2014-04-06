package com.taskpad.alarm;

import java.util.TimerTask;

/**
 * 
 * AlarmTask is the task that should be done
 * after the timer delay, which is stop playing sound. :D
 */

//@author A0112084U

public class AlarmOffTask extends TimerTask {
	
	protected AlarmOffTask(){
	}
	
    public void run() {
    	try {
			AlarmManager.turnOffAlarm();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    }

}
