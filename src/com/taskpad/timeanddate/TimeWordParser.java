package com.taskpad.timeanddate;

import java.util.HashMap;
import java.util.Map;

public class TimeWordParser extends NumberParser{

	private static Map<String, String[]> timewordsMap = new HashMap<String, String[]>();
	private static NumberParser _numberparser;
	
	private static  final String TIME_MIN = "MIN";
	private static final String TIME_HOURS = "HOUR";
	private static final String TIME_DAY = "DAY";
	private static final String TIME_WEEKS = "WEEK";
	private static final String TIME_YEAR = "YEAR";
	
	private  String _timeword = "";
	private  String _numberword = "";
	
	public TimeWordParser(){
		initialiseTimewords();
		_numberparser = new NumberParser();
	}
	
	public String timeWord(String input){		
		String time = "";
		
		if (containsTimeWord(input)){
			input = removeTimeWord(input);
			_numberword = _numberparser.parseTheNumbers(input);
			long timeLong = addTime();
			time = convertMillisecondsToTime(timeLong);
		}
		
		return time;
	}
	
	public boolean containsTimeWord(String input){
		String variations[];

		for (Map.Entry<String, String[]> entry : timewordsMap.entrySet()){
			variations = entry.getValue();
			for (int i=0; i<variations.length; i++){
				if (isValueFound(variations[i], input)){
					_timeword = entry.getKey();
					return true;
				}
			}
		}
		
		return false;
	}

	private void initialiseTimewords() {
		initialiseMinString();
		initialiseHoursString();
		initialiseDayString();
		initialiseWeekString();
		initialiseYearString();
	}

	private void initialiseMinString() {
		String minString[] = {"MIN", "MINUTES", "MINUTE", "MINS", "M"};
		timewordsMap.put(TIME_MIN, minString);
	}

	private void initialiseHoursString() {
		String hourString[] = {"HOUR", "HOURS", "HR", "HRS", "H"};
		timewordsMap.put(TIME_HOURS, hourString);
		
	}

	private void initialiseDayString() {
		String dayString[] = {"DAY", "D", "DAYS"};
		timewordsMap.put(TIME_DAY, dayString);
		
	}

	private void initialiseWeekString() {
		String weekString[] = {"WEEK", "WEEKS", "WK", "WKS"};
		timewordsMap.put(TIME_WEEKS, weekString);
		
	}

	private void initialiseYearString() {
		String yearString[] = {"YEAR", "YEARS", "YR", "YRS"};
		timewordsMap.put(TIME_YEAR, yearString);
	}
	
	private  boolean isValueFound(String value, String input) {
		input = input.trim();
		if (value.equalsIgnoreCase(input)){
			return true;
		}
		
		return false;
	}
	
	private  String removeTimeWord(String input){
		return input.replace(_timeword, "");
	}
	
	private  String convertMillisecondsToTime(long milliseconds){
		int minutes = (int) ((milliseconds / (1000*60)) % 60);
		int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
		
		String hourString = "" + hours;
		if (hourString.length() == 1){
			hourString = "0" + hourString;
		}
		
		String minuteString = "" + minutes;
		if (minuteString.length() == 1){
			minuteString = "0" + minuteString;
		}
		String timeString = hourString + ":" + minuteString;
		
		return timeString;
	}
	
	private long addTime(){
		DateAndTimeManager dtm = new DateAndTimeManager();
		String todayTime = dtm.getTodayTime();	
		
		return 0;
	}
	
}
