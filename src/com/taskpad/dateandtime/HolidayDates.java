package com.taskpad.dateandtime;

import java.util.HashMap;
import java.util.Map;

/** For special holiday dates
 * 
 * @author Lynnette 
 */
public class HolidayDates {
	
	private static Map<String, String> _holidays = new HashMap<String, String>();
	private static HolidayDates _holidayDate = new HolidayDates();
	
	private static final String STRING_NULL = "";
	
	private HolidayDates(){
		initialiseHolidayMap();
	}
	
	protected static HolidayDates getInstance(){
		return _holidayDate;
	}
	
	
	/** This method takes in an input String and returns the corresponding date for the holiday
	 * 
	 * @param input
	 * @return holidayDate
	 */
	protected static String getHolidayDate(String input){
		String holidayDate = STRING_NULL;
		if (_holidays.containsKey(input.toUpperCase())){
			holidayDate = _holidays.get(input.toUpperCase());
		}
		
		return holidayDate;
	}
	
	private static void initialiseHolidayMap(){
		_holidays.put("CHRISTMAS", "25/12/2014");
		_holidays.put("APRIL FOOLS", "1/4/2014");
		_holidays.put("APRIL FOOLS DAY", "1/4/2014");
		_holidays.put("LABOUR DAY", "1/5/2014");
		_holidays.put("LABOR DAY", "1/5/2014");
		_holidays.put("NATIONAL DAY", "9/8/2014");
		_holidays.put("NEW YEAR", "1/1/2014");
		_holidays.put("NEW YEAR DAY", "1/1/2014");
	}
	
}
