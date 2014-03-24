package com.taskpad.dateandtime;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Jun
 * 
 * @category
 * DayParser: a singleton parser that parses day such as Monday, Tuesday,
 * and also BigFestival such as Christmas and New Year
 */
public class DayParser {

	private DayParser _parseDay = new DayParser();
	
	private static Map<String, Integer> _mapWeek = new HashMap<String, Integer>();
	
	private static final String[] _dayInWeek = {
		"Sunday",
		"Monday",
		"Tuesday",
		"Wednesday",
		"Thursday",
		"Friday",
		"Saturday"
	};
	
	
	
	private DayParser(){
		initializeMapWeek();
	}
	
	private void initializeMapWeek() {
		for (int i = 0; i < _dayInWeek.length; i++){
			_mapWeek.put(_dayInWeek[i], i);
		}
	}

	protected DayParser getInstance(){
		return _parseDay;
	}
	
	protected String parseDay(String input){
		return null;
	}
}
