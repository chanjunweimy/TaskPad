package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	
	/** FOR JUNWEI: I can't think how to effectively do this :'(
	 * This method takes an input String, finds if there is holiday dates
	 * @param input
	 * @return String with holidayDates replaced, else returns input
	 */
	
	protected static String replaceHolidayDate(String input){	
		
		return input;
	}

	/** This method parses the date in the correct year
	 * 
	 * @param holidayDate
	 * @return String, dd/mm/yyyy
	 */
	private static String parseHolidayDate(String holidayDate){
		holidayDate = addYear(holidayDate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		Date date = null;
		try {
			date = sdf.parse(holidayDate);
			
		} catch (ParseException e) {
			//do nothing
		}
		return sdf.format(date);
	}
	
	private static String addYear(String date) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		return date+"/"+year;
	}
	
	private static void initialiseHolidayMap(){
		_holidays.put("CHRISTMAS", "25/12");
		_holidays.put("APRIL FOOLS", "1/4");
		_holidays.put("APRIL FOOLS DAY", "1/4");
		_holidays.put("LABOUR DAY", "1/5");
		_holidays.put("LABOR DAY", "1/5");
		_holidays.put("NATIONAL DAY", "9/8");
		_holidays.put("NEW YEAR", "1/1");
		_holidays.put("NEW YEAR DAY", "1/1");
	}
	
	/* Testing
	public static void main(String[] args){
		String input = "25/12";
		System.out.println(parseHolidayDate(input));
	}
	//*/
	
}
