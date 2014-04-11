//@author A0112084U

package com.taskpad.dateandtime;

import java.util.HashMap;
import java.util.Map;

/**
 * DayParser: a singleton parser that parses day such as Monday, Tuesday,
 * and also BigFestival such as Christmas and New Year
 * 
 */

public class DayParser {

	private static final String SPACE = " ";

	//private static final String DAY_INVALID = "Not a valid day";
	
	private static Map<String, Integer> _mapWeek = new HashMap<String, Integer>();
	
	
	private static final String[] DAY_TODAY = {
		"today", 
		"tdy",
		"2day"
	};
	
	private static final String[] DAY_NOW = { 
		"now",
		"nw"
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
		"tue",
		"wed",
		"thu",
		"fri",
		"sat",
		
		"sund",
		"mond",
		"tues",
		"wedn",
		"thur",
		"frid",
		"satd",
		
		"sunda",
		"monda",
		"tuesd",
		"wedne",
		"thurs",
		"frida",
		"satur"
	};
	
	private static DayParser _parseDay = new DayParser();
	
	private DayParser(){
		initializeMapWeek();
	}
	
	protected boolean isDay(String input){
		if( _mapWeek.containsKey(input.toLowerCase())){
			return true;
		} else {
			for (String todayVariation : DAY_TODAY){
				if (todayVariation.equals(input)){
					return true;
				}
			}
			
			for (String nowVariation : DAY_NOW){
				if (nowVariation.equals(input)){
					return true;
				}
			}
		}
		
		return false;
	}

	protected boolean isTdy(String input) {
		for (String nowVariation : DAY_NOW){
			if (nowVariation.equals(input)){
				return true;
			}
		}

		return false;
	}
	
	protected boolean isNow(String input) {
		for (String todayVariation : DAY_TODAY) {
			if (todayVariation.equals(input)) {
				return true;
			}
		}

		return false;
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
	protected String parseDayToDate(String input) throws InvalidDayException{
		//DateAndTimeManager datm = DateAndTimeManager.getInstance();
		DateAndTimeRetriever datr = DateAndTimeRetriever.getInstance();
		
		if (input == null || input.trim().isEmpty()){
			throw new InvalidDayException();
		}
		
		input = input.toLowerCase();
		for (String todayVariation : DAY_TODAY){
			if (todayVariation.equals(input)){
				return datr.getTodayDate();
			}
		}
		
		for (String nowVariation : DAY_NOW){
			if (nowVariation.equals(input)){
				return datr.getTodayDateAndTime();
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
			//because it maybe is word like tmr
		}

		specialDay = input.substring(0, input.lastIndexOf(analyzes[len - 1])).trim();
		boolean isDay = userDay >= 0 && userDay < 7;
		if (isDay){
			specialDay = swp.parseSpecialDay(specialDay, userDay);
		} else {
			specialDay = swp.parseSpecialDay(specialDay, analyzes[len - 1]);
		}
		
		specialDay = discardTime(specialDay);

		return specialDay;
	}

	/**
	 * @param specialDay
	 * @return
	 */
	private String discardTime(String specialDay) {
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
			throw new InvalidDayException();
		}
		
		Integer value = _mapWeek.get(input.toLowerCase());
		
		if (value == null){
			throw new InvalidDayException();
		}
		
		return value.intValue();
	}
	
	public static void main(String[] args){
		DayParser a = DayParser.getInstance();
		
		/*
		String input = "next next prev Sun";
		String sub = "";
		for (int i=0; i<input.length(); i++){
			for (int j=1; j<=input.length()-i; j++){
				sub = input.substring(i, i+j);
				try {
					System.out.println(a.parseDayToDate(sub));
				} catch (InvalidDayException | DatePassedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		*/
		
		try {
			System.out.println(a.parseDayToDate("MAN"));
		} catch (InvalidDayException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}
