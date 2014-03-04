/* This helper class creates the date and time object */

package com.taskpad.timeanddate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTime {	
	private SimpleDateFormat _sdf;
	private Date _today;
	
	protected DateAndTime(){
		_today = new Date();
	}
	
	protected String getCurrentDate(){
		_sdf = new SimpleDateFormat("dd/MM/yyyy");
		return _sdf.format(_today);
	}
	
	protected String getCurrentTime(){
		_sdf = new SimpleDateFormat("HH:mm");
		return _sdf.format(_today);
	}
	
	protected String getCurrentDay(){
		_sdf = new SimpleDateFormat("E");
		return _sdf.format(_today);
	}
	
}
