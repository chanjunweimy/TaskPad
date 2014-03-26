package com.taskpad.dateandtime;

import java.util.HashMap;
import java.util.Map;

/**
 * DayParser: a singleton parser that parses day such as Monday, Tuesday,
 * and also BigFestival such as Christmas and New Year
 * 
 * @author Jun
 */
public class DayParser {

	private static final String SPACE = " ";

	private static final String DAY_INVALID = "Not a valid day";
	
	private static Map<String, Integer> _mapWeek = new HashMap<String, Integer>();
	
	
	//private static final String DAY_YESTERDAY = "yesterday";
	//private static final String DAY_TOMORROW = "tomorrow"; 
	private static final String[] DAY_TODAY = {
		"today", 
		"tdy",
		"now"
	};
	
	private static final String[] DAY_IN_WEEK = {
		"sunday", 
		"monday",
		"tuesday",
		"wednesday",
		"thursday",
		"friday",
		"saturday",
		
		"sun",
		"mon",
		"tues",
		"wed",
		"thurs",
		"fri",
		"sat"
	};
	
	private static DayParser _parseDay = new DayParser();
	
	private DayParser(){
		initializeMapWeek();
	}
	
	private void initializeMapWeek() {		
		for (int i = 0; i < DAY_IN_WEEK.length; i++){
			_mapWeek.put(DAY_IN_WEEK[i], i % 7);
		}
	}

	protected static DayParser getInstance(){
		return _parseDay;
	}
	
	
	/**
	 * parseDayToDate: to get the date of the day
	 * @param input String
	 * @return String
	 * @throws InvalidDayException 
	 * @throws DatePassedException 
	 */
	protected String parseDayToDate(String input) throws DatePassedException, InvalidDayException{
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		
		for (String todayVariation : DAY_TODAY){
			if (todayVariation.equals(input)){
				return datm.getTodayDate();
			}
		}
		
		SpecialWordParser swp = SpecialWordParser.getInstance();
		String specialDay = null;

		String[] analyzes = input.split(SPACE);
		int len = analyzes.length;

		int userDay = -1;
		try {
			userDay = parseDayToInt(analyzes[len - 1]);
		} catch (InvalidDayException e) {
			//do nothing
		}

		boolean isDay = userDay >= 0 && userDay < 7;
		if (isDay){
			specialDay = input.substring(0, input.lastIndexOf(analyzes[len - 1])).trim();
			specialDay = swp.parseSpecialDay(specialDay, userDay);
		} else {
			specialDay = input.substring(0, input.lastIndexOf(analyzes[len - 1])).trim();
			specialDay = swp.parseSpecialDay(specialDay, analyzes[len - 1]);
		}
		
		if (specialDay != null){
			specialDay = specialDay.split(SPACE)[0];
		}

		return specialDay;
	}
	
	/**
	 * should pass in String like Sunday, Monday
	 * Mon, Monday and parses them to int
	 * @param input
	 * @return int
	 * @throws InvalidDayException 
	 */
	protected int parseDayToInt(String input) throws InvalidDayException{
		//initializeMapWeek();
		
		if (input == null){
			throw new InvalidDayException(DayParser.DAY_INVALID);
		}
		
		Integer value = _mapWeek.get(input.toLowerCase());
		
		if (value == null){
			throw new InvalidDayException(DayParser.DAY_INVALID);
		}
		
		return value.intValue();
	}
	
	/*
	public static void main(String[] args){
		DayParser a = DayParser.getInstance();
		try {
			System.out.println(a.parseDayToDate("nxt Monday"));
		} catch (InvalidDayException | DatePassedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	//*/
}
