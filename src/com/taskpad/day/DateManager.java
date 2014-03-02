/* Public API to call dates */

package com.taskpad.day;

public class DateManager {
	
	private static DateObject dateObject;
	
	public DateManager(){
		dateObject = new DateObject();
	}

	public void getTodayDate(){
		dateObject.getCurrentDate();
	}
	
	public void getTodayTime(){
		dateObject.getCurrentTime();
	}
	
	public void getTodayDay(){
		dateObject.getCurrentDay();
	}
	
}
