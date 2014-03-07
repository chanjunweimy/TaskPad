/* Public API to call dates */

package com.taskpad.timeanddate;

public class DateAndTimeManager implements TimeSkeleton, DateSkeleton {
	
	private static DateAndTime _dateAndTimeObject;
	
	public DateAndTimeManager(){
		_dateAndTimeObject = new DateAndTime();
	}

	public String getTodayTime(){
		return _dateAndTimeObject.getCurrentTime();
	}
	
	public String getTodayDate(){
		return _dateAndTimeObject.getCurrentDate();
	}
	
	public String getTodayDay(){
		return _dateAndTimeObject.getCurrentDay();
	}
	
	public String getTodayDateAndTime(){
		return _dateAndTimeObject.getCurrentTimeAndDate();
	}


	
	
	//for debug:
	/*
	public static void main(String[] args){
		DateAndTimeManager now = new DateAndTimeManager();
		System.out.println(now.getTodayTime());
		System.out.println(now.getTodayDate());
		System.out.println(now.getTodayDay());
	}
	*/
}
