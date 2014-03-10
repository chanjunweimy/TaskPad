package com.taskpad.alarm;

import java.util.TimerTask;

/**
 * 
 * @author Jun
 *
 * AlarmTask is the task that should be done
 * after the timer delay, which is stop playing sound. :D
 */

public class AlarmTask extends TimerTask {
	private AlarmManager _alarm;
	
	protected AlarmTask(AlarmManager _alarm){
		setAlarm(_alarm);
	}
	
    public void run() {
    	_alarm.stopSong();
    }

	private void setAlarm(AlarmManager _alarm) {
		this._alarm = _alarm;
	}
}
