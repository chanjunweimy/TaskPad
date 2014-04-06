//@author A0112084U

package com.taskpad.alarm;

import java.util.TimerTask;

/**
 * 
 * AlarmTask is the task that should be done
 * after the timer delay, which is stop playing sound. :D
 */

public class AlarmOnTask extends TimerTask {
	
	protected AlarmOnTask(){
	}
	
    public void run() {
    	try {
			AlarmManager.runAlarm();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    }

}
