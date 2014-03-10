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
	
	//it should find some way to be initialized first
	//to increase TaskPad efficiency
	private static AlarmManager _alarm = new AlarmManager();
	
	private AlarmExecutor(){ //it cannot be used as object
	}
	
	public static void launchAlarm(int time) {//it should be a method in executor
		assert(_alarm != null);
		try {
			_alarm.setAlarm(time);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
