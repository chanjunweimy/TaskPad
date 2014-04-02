package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/** For special holiday dates
 * 
 * @author Lynnette 
 */
public class HolidayDates {
	
	private static Map<String, String> _holidays = new HashMap<String, String>();
	private static HolidayDates _holidayDate = new HolidayDates();
	
	//private static final String STRING_NULL = "";
	
	private HolidayDates(){
		initialiseHolidayMap();
	}
	
	protected static HolidayDates getInstance(){
		return _holidayDate;
	}

	/** This method parses the date in the correct year
	 * 
	 * @param holidayDate
	 * @return String, dd/mm/yyyy
	 */
	private String parseHolidayDate(String holidayDate){
		holidayDate = addYear(holidayDate);		
		return holidayDate;
	}
	
	private String addYear(String date) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date holidayDate = null;
		try {
			holidayDate = sdf.parse(date + "/" + year);
			
		} catch (ParseException e) {
			//do nothing
		}
		
		Date now = new Date();
		if (now.compareTo(holidayDate) > 0){
			year++;
		}
		
		return date + "/" + year;
	}
	
	private static void initialiseHolidayMap(){
		_holidays.put("CHRISTMAS", "25/12");
		_holidays.put("APRIL FOOLS", "01/04");
		_holidays.put("APRIL FOOLS DAY", "01/04");
		_holidays.put("INDEPENDENCE DAY", "04/07");
		_holidays.put("LABOUR DAY", "01/05");
		_holidays.put("LABOR DAY", "01/05");
		_holidays.put("NATIONAL DAY", "09/08");
		_holidays.put("NEW YEAR", "01/01");
		_holidays.put("NEW YEAR DAY", "01/01");
	}
	
	///* Testing
	public static void main(String[] args){
		String input = " i is LABOUR DAY";
		System.out.println(input);
		HolidayDates holidayDates = HolidayDates.getInstance();
		//System.out.println(holidayDates.replaceHolidayDate(input));
	}
	//*/
	
}
