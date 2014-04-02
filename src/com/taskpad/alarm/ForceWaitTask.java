package com.taskpad.alarm;

import java.util.TimerTask;

/**
 * 
 * @author Jun
 *
 * Why should we force the user to wait?
 * It is because loading library is too slow
 * and we need to print out some words first.
 * The only way to do it is to force it to wait! ^^
 *
 */

public class ForceWaitTask extends TimerTask {
	private int _time = -1;
	
	protected ForceWaitTask(int time){
		_time = time;
	}
	
    public void run() {
    	AlarmExecutor.launchAlarm(_time);
    }
}
