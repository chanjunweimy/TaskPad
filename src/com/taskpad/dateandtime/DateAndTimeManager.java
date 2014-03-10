/* Public API to call dates */

package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.taskpad.execute.Task;

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

	public static final Comparator<Task> ACCENDING_ORDER = 
			new Comparator<Task>() {
			@Override
		public int compare(Task e1, Task e2) {
			SimpleDateFormat dateConverter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date d1, d2;
			try {
				d1 = dateConverter.parse(e1.getDeadline() + e1.getEndTime());
				d2 = dateConverter.parse(e2.getDeadline() + e2.getEndTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				return 0;
			}
			return d1.compareTo(d2);
		}
	};

	
	
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
