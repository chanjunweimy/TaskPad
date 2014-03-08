/* This helper class creates the date and time object */

package com.taskpad.timeanddate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTime {	
	private SimpleDateFormat _formater;
	private Date _today;
	
	protected DateAndTime(){
		_today = new Date();
	}
	
	protected String getCurrentDate(){
		_formater = new SimpleDateFormat("dd/MM/yyyy");
		return _formater.format(_today);
	}
	
	protected String getCurrentTime(){
		_formater = new SimpleDateFormat("HH:mm");
		return _formater.format(_today);
	}
	
	protected String getCurrentDay(){
		_formater = new SimpleDateFormat("E");
		return _formater.format(_today);
	}
	
	protected String getCurrentTimeAndDate(){
		_formater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return _formater.format(_today);
	}
	
}
