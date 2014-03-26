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

	private static final String DAY_INVALID = "Not a valid day";
	
	private static Map<String, Integer> _mapWeek = new HashMap<String, Integer>();
	
	private static final String[] _dayInWeek = {
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
		for (int i = 0; i < _dayInWeek.length; i++){
			_mapWeek.put(_dayInWeek[i], i % 7);
		}
	}

	protected static DayParser getInstance(){
		return _parseDay;
	}
	
	
	/**
	 * parseDayToDate: to get the date of the day
	 * @param input String
	 * @return String
	 */
	protected String parseDayToDate(String input){
		return null;
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
			System.out.println(a.parseDayToInt("MON"));
		} catch (InvalidDayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//*/
}
