package com.taskpad.timeanddate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;


import com.taskpad.execute.Task;

//sorting comparator created for executor
public class CompareDateAndTime {
	
	public static final Comparator<Task> ACCENDING_ORDER = 
			new Comparator<Task>() {
			@Override
		public int compare(Task e1, Task e2) {
			SimpleDateFormat dateConverter = new SimpleDateFormat();
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
	
	
	/**
	 * ==================below is testing=============================================================
	 * Please don't proceed if you don't want to see unrelated stuff!!! ^^
	 * 
	 */
	// this testComparator is working! 
	/*
	public static final Comparator<DateAndTimeManager> TEST_DATE_SORT = 
			new Comparator<DateAndTimeManager>() {
		@Override
		public int compare(DateAndTimeManager o1, DateAndTimeManager o2) {
			SimpleDateFormat dateConverter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			Date d1, d2;
			try {
				d1 = dateConverter.parse(o1.getTodayDate() + " " + o1.getTodayTime());
				d2 = dateConverter.parse(o2.getTodayDate() + " " + o2.getTodayTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				return 0;
			}
			return d1.compareTo(d2);
		}
	};
	*/
	
	
	//to debug
	/*
	public static void main(String[] args){
		DateAndTimeManager[] managers = new DateAndTimeManager[100];
		
		
		for (int i = 0; i < managers.length; i++){
			managers[i] = new DateAndTimeManager();
		}
		
		for (DateAndTimeManager manager: managers){
			manager = new DateAndTimeManager();
			System.out.println(manager.getTodayDate() + " " + manager.getTodayTime());
		}
		
		Arrays.sort(managers, CompareDateAndTime.TEST_DATE_SORT);
		//.sort(managers);
		for(DateAndTimeManager manager: managers){
			System.out.println(manager.getTodayDate() + " " + manager.getTodayTime());
		}
		
	}
	*/
	
}
