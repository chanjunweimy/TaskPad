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

	private static DayParser _parseDay = new DayParser();
	
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
		"sat",
	};
	
	
	
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
	 * should pass in String like Sunday, Monday
	 * Mon, Monday
	 * @param input
	 * @return int
	 * @throws InvalidDayException 
	 */
	protected int parseDay(String input) throws InvalidDayException{
		Integer value = _mapWeek.get(input.toLowerCase());
		
		if (value == null){
			throw new InvalidDayException(DayParser.DAY_INVALID);
		}
		
		return value.intValue();
	}
}
