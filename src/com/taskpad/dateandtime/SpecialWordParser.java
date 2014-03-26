package com.taskpad.dateandtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * SpecialWordParser: parses special words like next and previous
 * 
 * @author Jun, Lynnette
 *
 */
public class SpecialWordParser {
	
	private static final int DAY_WEEK = 7;

	private static final String EMPTY = "";

	//private static Map<Integer, String[]> _specialWordMap = new HashMap<Integer, String[]>();
	private static Map<String, Integer> _specialWordMap = new HashMap<String, Integer>();
	
	private static SpecialWordParser _specialWordParser = new SpecialWordParser();
	
	private static final String[] _nextMap = {
		"NEXT", "NXT", "FOLLOWING", "COMING", "TOMORROW"
	};
	
	private static final String[] _prevMap = {
		"PREVIOUS", "PREV", "PAST", "LAST", "YESTERDAY"
	};
	
	private static final String[] _thisMap = {
		"THIS"
	};
	
	private SpecialWordParser(){
		initialiseSpecialWordMap();
	}
	
	protected static SpecialWordParser getInstance(){
		return _specialWordParser;
	}
	
	protected String parseSpecialDay(String specialDay, int userDay){
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		DayParser dp = DayParser.getInstance();
		TimeWordParser twp = TimeWordParser.getInstance();
		
		String todayDay = datm.getTodayDay();
		int todayDayStat = getTodayDaye(dp, todayDay);
		int nxt = 1;
		
		if (specialDay == SpecialWordParser.EMPTY){
			return getNextDay(userDay, twp, todayDayStat, nxt);
		}
		
		specialDay = specialDay.toUpperCase();
		
		Scanner sc = new Scanner(specialDay);
		
		while (sc.hasNext()){
			String specialToken = sc.next();
			
			nxt += _specialWordMap.get(specialToken).intValue();
		}
		sc.close();
		

		return null;
	}

	/**
	 * @param userDay
	 * @param twp
	 * @param todayDayStat
	 */
	private String getNextDay(int userDay, TimeWordParser twp, int todayDayStat, int nxt) {
		todayDayStat -= userDay;
		
		if (todayDayStat < 0){
			todayDayStat += DAY_WEEK;
		}
		
		nxt--;
		todayDayStat += DAY_WEEK * nxt;
		
		try {
			return twp.timeWord(todayDayStat + "d");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			assert (false);
		}
		return null;
	}

	private int getTodayDaye(DayParser dp, String todayDay) {
		int todayDayStat = 0;
		try {
			todayDayStat = dp.parseDayToInt(todayDay);
		} catch (InvalidDayException e) {
			assert (false);
		}
		return todayDayStat;
	}
	
	/**
	 * parseSpecialWord parses special sentence like
	 * next next hour.
	 * 
	 * It can solve types like 
	 * next next hour
	 * (special words... + TimeUnit)
	 * 
	 * or
	 * 
	 * next next 1 hour 
	 * (sepcial words... + integer + TimeUnit)
	 * 
	 * It is the only method that parses words like next and prev.
	 * 
	 * @author Jun
	 * @param specialWord String
	 * @return int
	 */
	protected String parseSpecialWord(String specialWord, boolean multiply, int num){
		return null;
	}
	
	private void initialiseSpecialWordMap() {
		initializeNextMap();
		initializePrevMap();
		initializeThisMap();
	}

	private void initializeThisMap() {
		for (String myThis : _thisMap){
			_specialWordMap.put(myThis, 0);
		}
		
	}

	private void initializePrevMap() {
		//_specialWordMap.put(+1, _nextMap);
		
		for (String next : _nextMap){
			_specialWordMap.put(next, +1);
		}
		
	}

	private void initializeNextMap() {
		//_specialWordMap.put(-1, _prevMap);		
		
		for (String prev : _prevMap){
			_specialWordMap.put(prev, -1);
		}
	}
	
	
}
