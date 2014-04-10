//@author A0112084U

/* This helper class creates the date and time object */

package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTime {	
	private Date _today;
	
	//date used in debugging
	private Date _debugDate = null;
	
	protected DateAndTime(){
	}

	/**
	 * 
	 */
	private void setupDateAndTime() {
		boolean isNotDebugging = _debugDate == null;
		if (isNotDebugging){
			_today = new Date();
		} else {
			_today = _debugDate;
		}
	}
	
	protected String getCurrentDate(){
		setupDateAndTime();
		
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		return formater.format(_today);
	}
	
	protected String getCurrentTime(){
		setupDateAndTime();
		
		SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
		return formater.format(_today);
	}
	
	protected String getCurrentDay(){
		setupDateAndTime();
		
		SimpleDateFormat formater = new SimpleDateFormat("EEEE");
		return formater.format(_today);
	}
	
	protected String getCurrentTimeAndDate(){
		setupDateAndTime();
		
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return formater.format(_today);
	}
	
	/**
	 * setDebugDate: setup a date for debugging. Used when debugging
	 * @param dateString
	 * @throws ParseException
	 */
	protected void setDebugDate(String dateString) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		_debugDate = formater.parse(dateString);
	}
	
}
