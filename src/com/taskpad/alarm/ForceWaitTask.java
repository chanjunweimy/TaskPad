package com.taskpad.alarm;

import java.util.TimerTask;

public class ForceWaitTask extends TimerTask {
	private int _time = -1;
	
	protected ForceWaitTask(int time){
		_time = time;
	}
	
    public void run() {
    	AlarmExecutor.launchAlarm(_time);
    }
}
