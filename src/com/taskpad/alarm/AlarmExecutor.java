package com.taskpad.alarm;


/**
 * 
 * @author Jun
 *
 * temp class that is currently not put
 * into executor. It should be implemented inside
 * executor anyway
 * 
 * note that: now Alarm is without desrcription,
 * we should have description in later stage
 * 
 * and note that: Alarm can only support 
 * 10 s, not 10s
 * 
 */
public class AlarmExecutor {
	public AlarmExecutor(int time){ //it should be a method in executor
		try {
			AlarmManager alarm = new AlarmManager();
			alarm.setAlarm(time);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
}
