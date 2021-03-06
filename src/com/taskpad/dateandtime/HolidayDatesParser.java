//@author A0119646X

package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** For special holiday dates
 * 
 */

public class HolidayDatesParser {
	
	private static Map<String, String> _holidays = new HashMap<String, String>();
	private static HolidayDatesParser _holidayDate = new HolidayDatesParser();
	
	//private static final String STRING_NULL = "";
	
	private HolidayDatesParser(){
		initialiseHolidayMap();
	}
	
	protected static HolidayDatesParser getInstance(){
		return _holidayDate;
	}
	
	/**
	 * This method takes an input String, finds if there is holiday dates
	 * @param input
	 * @return String with holidayDates replaced, else returns input
	 */
	
	protected String replaceHolidayDate(String input){	
		if (input == null){
			return null;
		}
		String holidayDate = _holidays.get(input.toUpperCase());
		if (holidayDate != null){
			holidayDate = parseHolidayDate(holidayDate);
		} else {
			holidayDate = null;
		}
		return holidayDate;
	}

	/** This method parses the date in the correct year
	 * 
	 * @param holidayDate
	 * @return String, dd/mm/yyyy
	 */
	private String parseHolidayDate(String holidayDate){
		holidayDate = addYear(holidayDate);
		
		/*
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		Date date = null;
		try {
			date = sdf.parse(holidayDate);
			
		} catch (ParseException e) {
			//do nothing
		}
		return sdf.format(date);
		*.
		*/
		
		return holidayDate;
	}
	
	private String addYear(String date) {
		int year = getThisYear();
		
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
		
		//System.out.println(now.toString() + "\n" + holidayDate.toString());
		
		return date + "/" + year;
	}

	/**
	 * @return
	 */
	private int getThisYear() {
		DateAndTimeRetriever datr = DateAndTimeRetriever.getInstance();
		String today = datr.getTodayDate();
		String yearString = today.split("/")[2];
		return Integer.parseInt(yearString);
		//return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	private static void initialiseHolidayMap(){
		_holidays.put("CHRISTMAS", "25/12");
		_holidays.put("APRIL FOOLS", "01/04");
		_holidays.put("APRIL FOOL", "01/04");
		_holidays.put("APRIL FOOLS DAY", "01/04");
		_holidays.put("APRIL FOOL DAY", "01/04");
		_holidays.put("INDEPDENCE DAY", "04/07");
		_holidays.put("LABOUR DAY", "01/05");
		_holidays.put("LABOR DAY", "01/05");
		_holidays.put("NATIONAL DAY", "09/08");
		_holidays.put("NEW YEAR", "01/01");
		_holidays.put("NEW YEAR DAY", "01/01");
	}
	
	/* Testing
	public static void main(String[] args){
		String input = "LABOUR DAY";
		HolidayDatesParser holidayDates = HolidayDatesParser.getInstance();
		System.out.println(holidayDates.replaceHolidayDate(input));
		System.out.println(holidayDates.replaceHolidayDate("RANDOM"));

	}
	//*/
	
}
