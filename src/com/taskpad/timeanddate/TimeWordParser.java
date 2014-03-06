package com.taskpad.timeanddate;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeWordParser extends NumberParser{

	private static Map<String, String[]> timewordsMap = new HashMap<String, String[]>();
	private static NumberParser _numberparser;
	
	private static final String TIME_MIN = "MIN";
	private static final String TIME_HOURS = "HOUR";
	private static final String TIME_DAY = "DAY";
	private static final String TIME_WEEKS = "WEEK";
	private static final String TIME_MONTH = "MONTH";
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
			Date newTime = addTime();
			time = formatTime(newTime);
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
		initialiseMonthString();
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
		if (value.equalsIgnoreCase(input)){
			return true;
		}
		
		return false;
	}
	
	private  String removeTimeWord(String input){
		return input.replace(_timeword, "");
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
		DateAndTimeManager dtm = new DateAndTimeManager();
		String todayTime = dtm.getTodayTime();	
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			date = sdf.parse(todayTime);
		} catch (ParseException e) {
			return null;	//To do: Handle exception
		}
		
		return date;
	}
	
}
