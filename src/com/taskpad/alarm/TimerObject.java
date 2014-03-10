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

    protected TimerObject(int seconds, AlarmManager alarm) {
        timer = new Timer();
        timer.schedule(new AlarmTask(alarm), seconds*1000);
	}
}
