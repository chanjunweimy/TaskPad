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

    protected TimerObject(int seconds) {
        timer = new Timer();
        timer.schedule(new AlarmTask(), seconds*1000);
	}
}
