package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * @author Jun and Lynnette
 *
 * make TimeWordParser to be a singleton to increase efficiency.
 */

public class TimeWordParser{
	
	private static Map<String, String[]> _timewordsMap = new HashMap<String, String[]>();
	private static Map<String, Integer> _timeunitMap = new HashMap<String, Integer>();
	private static DateAndTimeManager _numberparser = DateAndTimeManager.getInstance();
	
	private static final String TIME_SEC = "SECOND";
	private static final String TIME_MIN = "MIN";
	private static final String TIME_HOURS = "HOUR";
	private static final String TIME_DAY = "DAY";
	private static final String TIME_WEEKS = "WEEK";
	private static final String TIME_MONTH = "MONTH";
	private static final String TIME_YEAR = "YEAR";
	private static final String ERROR_NULL_UNIT = "Does not contain time unit!";
	private static final String ERROR_NULL_VALUE = "Please key in time value!";
	private static final String SPACE = " ";
	private static final String BLANK = "";
	
	private final int CONSTANT_SECOND = 1;
	private final int CONSTANT_MINUTE = CONSTANT_SECOND * 60;
	private final int CONSTANT_HOURS = CONSTANT_MINUTE * 60;
	private final int CONSTANT_DAY = CONSTANT_HOURS * 24;
	private final int CONSTANT_WEEK = CONSTANT_DAY * 7;
	
	private static String _timeword = TimeWordParser.BLANK;
	private static String _numberword = TimeWordParser.BLANK;
	private String _userTimeword = TimeWordParser.BLANK;
	//private static int _index = -2;
	
	private static TimeWordParser _timewordParser = new TimeWordParser();
	
	private TimeWordParser(){
		initialiseTimewords();
		initializeTimeUnitMap();
	}

	private void initializeTimeUnitMap() {		
		_timeunitMap.put(TIME_SEC, CONSTANT_SECOND);
		_timeunitMap.put(TIME_MIN, CONSTANT_MINUTE);
		_timeunitMap.put(TIME_HOURS, CONSTANT_HOURS);
		_timeunitMap.put(TIME_DAY, CONSTANT_DAY);
		_timeunitMap.put(TIME_WEEKS, CONSTANT_WEEK);
	}
	
	protected static TimeWordParser getInstance(){
		return _timewordParser;
	}
	
	/*
	public static void main(String[] args){
		String input = "20 hours";
		TimeWordParser twp = TimeWordParser.getInstance();
		try {
			System.out.println(twp.timeWord(input));
		} catch (NullTimeUnitException | NullTimeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	protected String timeWord(String input) throws NullTimeUnitException, NullTimeValueException{	
		String time = parseTimeWord(input);
		Date futureDate = addTime(time, TIME_SEC);
		return formatTime(futureDate);
	}
	
	/*
	protected String timeWord(String input){	
		String time = TimeWordParser.BLANK;
		if (hasTimeWord(input)){
			input = removeTimeWord(input);
			_numberword = _numberparser.parseNumber(input);
			Date newTime = addTime();
			time = formatTime(newTime);
		}
		
		return time;
	}
	 */
	
	protected String parseTimeWordWithSpecialWord(String input) throws NullTimeUnitException, NullTimeValueException{	
		if (input == null || input.equals(SPACE)){
			throw new NullTimeUnitException(ERROR_NULL_UNIT);
		}
		
		String timeWord = null;
		String specialWord = null;
		
		SpecialWordParser swp = SpecialWordParser.getInstance();
		
		String splitWord = swp.getTimeWordWithoutSpecialWords(input);
		if (splitWord == null){
			splitWord = parseTimeWord(input);
			timeWord = splitWord + SPACE + TIME_SEC;
			return timeWord(timeWord);
		}
		
		int splitPlace = input.lastIndexOf(splitWord) + splitWord.length() + 1;
		int num = 0;
		
		specialWord = input.substring(0, splitPlace).trim();
		timeWord = input.substring(splitPlace, input.length()).trim();
		
		try {
			timeWord = parseTimeWord(timeWord);
			num = Integer.parseInt(timeWord);
			timeWord = swp.parseSpecialWord(specialWord, num);
			
		} catch (NullTimeValueException e) {

			num = calculateTimeWord(input);
			timeWord = swp.parseSpecialWord(specialWord, num);
		}
		timeWord = timeWord + SPACE + TIME_SEC;
		return timeWord(timeWord);
	}
	
	protected String parseTimeWord(String input) throws NullTimeUnitException, NullTimeValueException{	
		if (input == null || input.equals(SPACE)){
			throw new NullTimeUnitException(ERROR_NULL_UNIT);
		}
		
		int realTime = 0;
		
		realTime = calculateEachTimeValues(realTime, input);
		
		
		return BLANK + realTime;
	}

	private int calculateEachTimeValues(int realTime,
			String input) throws NullTimeValueException, NullTimeUnitException {
		Scanner sc = new Scanner(input);
		StringBuffer tempTime = new StringBuffer(BLANK);
		while (sc.hasNext()){
			String oneSubstring = sc.next();
			tempTime.append(oneSubstring);
			
			int secondConvertion = calculateTimeWord(oneSubstring);
			boolean hasTimeUnit = secondConvertion > 0;
			if (hasTimeUnit){
				StringBuffer oneSubStringAns = parseOneTimeWord(tempTime.toString());
				realTime += Integer.parseInt(oneSubStringAns.toString());
				tempTime = new StringBuffer(BLANK);
			} else {
				tempTime.append(SPACE);
			}
		}
		sc.close();
		
		boolean isTempTimeEmpty = tempTime.toString().equals(BLANK);
		if (!isTempTimeEmpty){
			throw new NullTimeUnitException(ERROR_NULL_UNIT);
		}
		return realTime;
	}

	private StringBuffer parseOneTimeWord(String input)
			throws NullTimeValueException, NullTimeUnitException {
		StringBuffer time = new StringBuffer();
		int exactTimeSecond = 0;
		int secondConvertion = calculateTimeWord(input);
		boolean hasTimeUnit = secondConvertion > 0;
		if (hasTimeUnit){
			input = removeTimeWord(input);
			input = input.trim();
			
			if (input == BLANK) {
				throw new NullTimeValueException(ERROR_NULL_VALUE);
			}
			
			_numberword = _numberparser.parseNumber(input);
			
			//System.err.println("DE: " + _numberword + " " + input);
			if (_numberword == null){
				throw new NullTimeValueException(ERROR_NULL_VALUE);
			}
			
			exactTimeSecond = convertSecond(secondConvertion);
			time.append(exactTimeSecond);
		} else {
			throw new NullTimeUnitException(ERROR_NULL_UNIT);
		}
		return time;
	}

	private int convertSecond(int secondConvertion) {
		return Integer.parseInt(_numberword) * secondConvertion;
	}
	
	private int calculateTimeWord(String input){
		String variations[];
		int multiply = 0;

		for (Map.Entry<String, String[]> entry : _timewordsMap.entrySet()){
			variations = entry.getValue();
			for (int i=0; i<variations.length; i++){
				if (isValueFound(variations[i], input)){
					_timeword = entry.getKey();
					
					Integer value = _timeunitMap.get(_timeword);
					
					if (value != null){
						multiply = value.intValue();
					}

					return multiply;
				}
			}
		}
		
		return multiply;
	}
	
	/*
	private boolean hasTimeWord(String input){
		String variations[];

		for (Map.Entry<String, String[]> entry : _timewordsMap.entrySet()){
			variations = entry.getValue();
			for (int i=0; i<variations.length; i++){
				if (isValueFound(variations[i], input)){
					_timeword = entry.getKey();
					return true;
				}
			}
		}
		
		return false;
	}*/

	private void initialiseTimewords() {
		initialiseSecString();
		initialiseMinString();
		initialiseHoursString();
		initialiseDayString();
		initialiseWeekString();
		initialiseMonthString();
		initialiseYearString();
	}
	
	protected boolean isTimeUnits(String input){
		return _timewordsMap.containsKey(input.toUpperCase());
	}

	private void initialiseSecString() {
		String secString[] = {"SEC", "SECONDS", "SECOND", "SECS", "S"};
		_timewordsMap.put(TIME_SEC, secString);
	}
	
	private void initialiseMinString() {
		String minString[] = {"MIN", "MINUTES", "MINUTE", "MINS", "M"};
		_timewordsMap.put(TIME_MIN, minString);
	}

	private void initialiseHoursString() {
		String hourString[] = {"HOUR", "HOURS", "HR", "HRS", "H"};
		_timewordsMap.put(TIME_HOURS, hourString);
		
	}

	private void initialiseDayString() {
		String dayString[] = {"DAY", "D", "DAYS"};
		_timewordsMap.put(TIME_DAY, dayString);
		
	}

	private void initialiseWeekString() {
		String weekString[] = {"WEEK", "WEEKS", "WK", "WKS"};
		_timewordsMap.put(TIME_WEEKS, weekString);
		
	}
	
	private void initialiseMonthString(){
		String monthString[] = {"MONTH", "MONTHS", "MTH", "MTHS"};
		_timewordsMap.put(TIME_MONTH, monthString);
	}

	private void initialiseYearString() {
		String yearString[] = {"YEAR", "YEARS", "YR", "YRS"};
		_timewordsMap.put(TIME_YEAR, yearString);
	}
	
	private boolean isInteger(String unknown){
		try {
			Integer.parseInt(unknown);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}
	
	private  boolean isValueFound(String value, String input) {
		if (input == BLANK || input == null){
			return false;
		}
		
		String timeValue;
		int idx = 0;
		input = input.trim();
		input = input.toUpperCase();
		String[] numArr = input.split(SPACE);
		
		/**
		 * Let's search for unit that is separated by SPACE first.
		 * The last word should be the time unit,
		 * but maybe it is "", so need to check.
		 */
		for (int i = numArr.length - 1; i >= 0; i--){
			
			if (numArr[i].equals(TimeWordParser.BLANK)){
				continue;
			} else {
				if (numArr[i].equals(value)){
					_userTimeword = value;
					return true;
				} else {
					idx = i;
					break;
				}
			}
		}
		
		/**
		 * If it is not separated by SPACE, then it probably be something like this:
		 * num + unit, ex: 1s, 10m
		 */
		timeValue = numArr[idx].substring(0, numArr[idx].length() - 1);
		if (isInteger(timeValue)){
			boolean hasUnit = numArr[idx].endsWith(value);
			if (hasUnit){
				_userTimeword = value;
				return true;
			} 
		}
		
		/** 
		 * @author Jun
		 * We cannot just see if input contains that value
		 * six also contains "s" but it is not the time unit
		 */
		/*
		if (input.contains(value)){
			_index = input.toUpperCase().indexOf(value);
			return true;
		}
		 */
		
		return false;
	}
	
	private  String removeTimeWord(String input){
		input = input.toUpperCase();
		int idx = input.lastIndexOf(_userTimeword);
		 
		//we should ensure we have already checked what is the _timeword before proceeding
		assert (idx >= 0);
		
		return input.substring(0, idx);
		/**
		 * below can only delete time unit 
		 * that is with SPACE which is no longer suitable
		 */
		/*
		int index = input.indexOf(' ', _index);
		String replace;
		if (index == -1){
			replace = input.substring(0, _index);
		} else {
			String temp = input.substring(_index, input.indexOf(' ', _index));
			replace = input.replace(temp, "");
		}
		*/ 
				
//		return input.replaceAll("(?i)"+_timewordOriginal, "").trim();
	}
	
	private String formatTime(Date time){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return sdf.format(time);
	}
	
	private Date addTime(String value, String unit){
		Date date = getCurrentTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int increment = Integer.parseInt(value);
		
		switch (unit){
		case TIME_SEC:
			cal.add(Calendar.SECOND, increment);
			break;
		case TIME_MIN:
			cal.add(Calendar.MINUTE, increment);
			break;
		case TIME_HOURS:
			cal.add(Calendar.HOUR, increment);
			break;
		case TIME_DAY:
			cal.add(Calendar.DAY_OF_YEAR, increment);
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
	
	/*
	private Date addTime(){
		Date date = getCurrentTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int increment = Integer.parseInt(_numberword);
		
		switch (_timeword){
			case TIME_SEC:
				cal.add(Calendar.SECOND, increment);
				break;
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
	*/
	
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
	
	public static void main (String[] args){
		try {
			System.out.println(TimeWordParser.getInstance().parseTimeWordWithSpecialWord("next next 2 hour"));
		} catch (NullTimeUnitException | NullTimeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
