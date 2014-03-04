package com.taskpad.timeanddate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

//import com.taskpad.execute.Task;

//sorting comparator created for executor
public class CompareDateAndTime {
	/*
	 //Comparator to Task not implemented yet
	 //Due to attribute changing
	public static final Comparator<Task> ACCENDING_ORDER = 
			new Comparator<Task>() {
			@Override
		public int compare(Task e1, Task e2) {
			SimpleDateFormat dateConverter = new SimpleDateFormat();
			Date d1, d2;
			try {
				d1 = dateConverter.parse(e1.getDeadlineDay());
				d2 = dateConverter.parse(e2.getDeadlineDay());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				return 0;
			}
			return 0;
		}
	};
	*/
	
	
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
	
	//to debug
	public static void main(String[] args){
		DateAndTimeManager[] managers = new DateAndTimeManager[100];
		
		
		for(DateAndTimeManager manager: managers){
			manager = new DateAndTimeManager();
			System.out.println(manager.getTodayDate() + " " + manager.getTodayTime());
		}
		
		Arrays.sort(managers, CompareDateAndTime.TEST_DATE_SORT);
		for(DateAndTimeManager manager: managers){
			System.out.println(manager.getTodayDate() + " " + manager.getTodayTime());
		}
		
	}
	
}
