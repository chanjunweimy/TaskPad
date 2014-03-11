package com.taskpad.alarm;

import java.util.Timer;

/**
 * 
 * @author Jun
 *
 * TimerObject is the timer
 * that counts how long the 
 * song plays
 */

public class TimerObject {
	Timer timer;

    protected TimerObject(boolean isOn, int seconds) {
        timer = new Timer();
        seconds *= 1000;
        if (!isOn){
        	timer.schedule(new AlarmOffTask(), seconds);
        } else {
        	timer.schedule(new AlarmOnTask(), seconds);
        }
	}
    
    public TimerObject(int seconds, int time){
    	timer = new Timer();
    	timer.schedule(new ForceWaitTask(time), seconds);
    }
    
}
