package com.taskpad.dateandtime;

import java.util.HashMap;
import java.util.Map;

public class SpecialWordParser {
	
	private static Map<Integer, String[]> _specialWordMap = new HashMap<Integer, String[]>();
	
	private static SpecialWordParser _specialWordParser = new SpecialWordParser();
	
	private static final String[] _nextMap ={
		"NEXT", "NXT", "FOLLOWING", "COMING"
	};
	
	private static final String[] _prevMap ={
		"PREVIOUS", "PREV", "PAST", "LAST"
	};
	
	private SpecialWordParser(){
		initialiseSpecialWordMap();
	}
	
	protected static SpecialWordParser getInstance(){
		return _specialWordParser;
	}
	
	private void initialiseSpecialWordMap() {
		initializeNextMap();
		initializePrevMap();
	}

	private void initializePrevMap() {
		_specialWordMap.put(+1, _nextMap);
		
	}

	private void initializeNextMap() {
		_specialWordMap.put(-1, _prevMap);		
	}
	
	
}
