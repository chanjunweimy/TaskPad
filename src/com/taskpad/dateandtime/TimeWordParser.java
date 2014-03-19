package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.taskpad.ui.GuiManager;

/**
 * 
 * @author Jun
 *
 * make TimeWordParser to be a singleton to increase efficiency.
 */

public class TimeWordParser extends NumberParser{
	
	private static Map<String, String[]> timewordsMap = new HashMap<String, String[]>();
	private static Map<String, Integer> timeunitMap = new HashMap<String, Integer>();
	private static NumberParser _numberparser = new NumberParser();
	
	private static final String TIME_SEC = "SECOND";
	private static final String TIME_MIN = "MIN";
	private static final String TIME_HOURS = "HOUR";
	private static final String TIME_DAY = "DAY";
	private static final String TIME_WEEKS = "WEEK";
	private static final String TIME_MONTH = "MONTH";
	private static final String TIME_YEAR = "YEAR";
	private final static String ERROR = "!";
	
	private final int CONSTANT_SECOND = 1;
	private final int CONSTANT_MINUTE = CONSTANT_SECOND * 60;
	private final int CONSTANT_HOURS = CONSTANT_MINUTE * 60;
	private final int CONSTANT_DAY = CONSTANT_HOURS * 24;
	private final int CONSTANT_WEEK = CONSTANT_DAY * 7;
	
	private static String _timeword = "";
	private static String _numberword = "";
	private static int _index = -2;
	
	private static TimeWordParser _timewordParser = new TimeWordParser();
	
	private TimeWordParser(){
		initialiseTimewords();
		initializeTimeUnitMap();
	}

	private void initializeTimeUnitMap() {		
		timeunitMap.put(TIME_SEC, CONSTANT_SECOND);
		timeunitMap.put(TIME_MIN, CONSTANT_MINUTE);
		timeunitMap.put(TIME_HOURS, CONSTANT_HOURS);
		timeunitMap.put(TIME_DAY, CONSTANT_DAY);
		timeunitMap.put(TIME_WEEKS, CONSTANT_WEEK);
	}
	
	protected static TimeWordParser getInstance(){
		return _timewordParser;
	}
	
	/*
	protected static void main(String[] args){
		String input = "20 hours";
		TimeWordParser twp = new TimeWordParser();
		System.out.println(twp.timeWord(input));
	}
	*/
	
	protected String parseTimeWord(String input) throws Exception{	
		StringBuffer time = new StringBuffer();
		int exactTimeSecond = 0;
		int secondConvertion = calculateTimeWord(input);
		boolean hasTimeUnit = secondConvertion > 0;
		if (hasTimeUnit){
			input = removeTimeWord(input);
			_numberword = _numberparser.parseTheNumbers(input);
			
			exactTimeSecond = Integer.parseInt(_numberword) * secondConvertion;
			time.append(exactTimeSecond);
		} else {
			GuiManager.callOutput(ERROR);
		}
		
		return time.toString();
	}
	
	protected String timeWord(String input){	
		String time = "";
		if (containsTimeWord(input)){
			input = removeTimeWord(input);
			_numberword = _numberparser.parseTheNumbers(input);
			Date newTime = addTime();
			time = formatTime(newTime);
		}
		
		return time;
	}
	
	protected int calculateTimeWord(String input){
		String variations[];
		int multiply = 0;

		for (Map.Entry<String, String[]> entry : timewordsMap.entrySet()){
			variations = entry.getValue();
			for (int i=0; i<variations.length; i++){
				if (isValueFound(variations[i], input)){
					_timeword = entry.getKey();
					return multiply;
				}
			}
		}
		
		return multiply;
	}
	
	private boolean containsTimeWord(String input){
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
		initialiseSecString();
		initialiseMinString();
		initialiseHoursString();
		initialiseDayString();
		initialiseWeekString();
		initialiseMonthString();
		initialiseYearString();
	}

	private void initialiseSecString() {
		String secString[] = {"SEC", "SECONDS", "SECOND", "SECS", "S"};
		timewordsMap.put(TIME_SEC, secString);
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
	
	private void initialiseMonthString(){
		String monthString[] = {"MONTH", "MONTHS", "MTH", "MTHS"};
		timewordsMap.put(TIME_MONTH, monthString);
	}

	private void initialiseYearString() {
		String yearString[] = {"YEAR", "YEARS", "YR", "YRS"};
		timewordsMap.put(TIME_YEAR, yearString);
	}
	
	private  boolean isValueFound(String value, String input) {
		input = input.trim();
		if (input.toUpperCase().contains(value)){
			_index = input.toUpperCase().indexOf(value);
			return true;
		}
		
		return false;
	}
	
	private  String removeTimeWord(String input){
		int index = input.indexOf(' ', _index);
		String replace;
		if (index == -1){
			replace = input.substring(0, _index);
		} else {
			String temp = input.substring(_index, input.indexOf(' ', _index));
			replace = input.replace(temp, "");
		}
				
//		return input.replaceAll("(?i)"+_timewordOriginal, "").trim();
		return replace.trim();
	}
	
	private String formatTime(Date time){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return sdf.format(time);
	}
	
	private Date addTime(){
		Date date = getCurrentTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int increment = Integer.parseInt(_numberword);
		
		switch (_timeword){
			case TIME_MIN:
				cal.add(Calendar.MINUTE, increment);
				break;
			case TIME_HOURS:
				cal.add(Calendar.HOUR, increment);
				break;
			case TIME_WEEKS:
				cal.add(Calendar.WEEK_OF_MONTH, increment);
				break;
			case TIME_MONTH:
				cal.add(Calendar.MONTH, increment);
				break;
			case TIME_YEAR:
				cal.add(Calendar.YEAR, increment);
				break;
			default:
				break;
		}
		
		return cal.getTime();
	}
	
	private Date getCurrentTime(){
		Date date;
		DateAndTimeManager dtm = DateAndTimeManager.getInstance();
		String todayTime = dtm.getTodayDateAndTime();
//		String todayTime = dtm.getTodayTime();	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			date = sdf.parse(todayTime);
		} catch (ParseException e) {
			return null;	//To do: Handle exception
		}
		
		return date;
	}
	
}
