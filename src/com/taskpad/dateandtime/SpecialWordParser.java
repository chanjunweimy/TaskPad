package com.taskpad.dateandtime;

import java.util.HashMap;
import java.util.Map;

/**
 * SpecialWordParser: parses special words like next and previous
 * 
 * @author Jun, Lynnette
 *
 */
public class SpecialWordParser {
	
	//private static Map<Integer, String[]> _specialWordMap = new HashMap<Integer, String[]>();
	private static Map<String, Integer> _specialWordMap = new HashMap<String, Integer>();
	
	private static SpecialWordParser _specialWordParser = new SpecialWordParser();
	
	private static final String[] _nextMap = {
		"NEXT", "NXT", "FOLLOWING", "COMING"
	};
	
	private static final String[] _prevMap = {
		"PREVIOUS", "PREV", "PAST", "LAST"
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
	
	/**
	 * parseSpecialWord parses special sentence like
	 * next next Monday.
	 * 
	 * It can solve types like 
	 * next next Monday
	 * (special words... + DayWord/TimeUnit)
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
	protected int parseSpecialWord(String specialWord){
		int res = 0;
		return res;
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
