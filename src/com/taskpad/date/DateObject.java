/* This class creates the date object */

package com.taskpad.date;

import java.text.SimpleDateFormat;

public class DateObject {
	
	private static DateObject dateObject;
	private static SimpleDateFormat sdf;
	
	protected DateObject(){
		dateObject = new DateObject();
	}
	
	protected String getCurrentDate(){
		sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(dateObject);
	}
	
	protected String getCurrentTime(){
		sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(dateObject);
	}
	
	protected String getCurrentDay(){
		sdf = new SimpleDateFormat("E");
		return sdf.format(dateObject);
	}
	
}
