/* Public API to call dates */

package com.taskpad.dateandtime;

import java.text.ParseException;

import com.taskpad.ui.GuiManager;

/**
 * 
 * DateAndTimeManager is a facade class of dateandtime package.
 * 
 * 
 * @author Jun & Lynnette
 * 
 */
public class DateAndTimeManager implements TimeSkeleton, DateSkeleton {
	/*
	 * DEPRECATED private static final String SPACE = " "; private final static
	 * String ERROR = "ERROR!"; private final static int CONVERT = 60; private
	 * static final Exception EXCEPTION_INVALID_INPUT = new Exception();//don't
	 * know choose which private static final String EMPTY = "";
	 * 
	 * 
	 * private static int _multiple = 1;
	 */

	private static DateAndTime _dateAndTimeObject = new DateAndTime();

	private static DateAndTimeManager _managerInstance = new DateAndTimeManager();
	

	private DateAndTimeManager() {
	}

	/**
	 * getInstance: get the instance of DateAndTimeManager to use dateandtime
	 * api..
	 * 
	 * @return DateAndTimeManager
	 */
	public static DateAndTimeManager getInstance() {
		return _managerInstance;
	}

	/**
	 * getTodayTime: return the current time.
	 * 
	 * @return String
	 */
	@Override
	public String getTodayTime() {
		//_dateAndTimeObject = new DateAndTime();
		return _dateAndTimeObject.getCurrentTime();
	}

	/**
	 * getTodayDate: return today's date
	 * 
	 * @return String
	 */
	@Override
	public String getTodayDate() {
		//DateAndTime _dateAndTimeObject = new DateAndTime();
		return _dateAndTimeObject.getCurrentDate();
	}

	/**
	 * getTodayDay: return today's day
	 * 
	 * @return String
	 */
	@Override
	public String getTodayDay() {
		//DateAndTime _dateAndTimeObject = new DateAndTime();
		return _dateAndTimeObject.getCurrentDay();
	}

	/**
	 * getTodayDateAndTime: to get today's date and the current time
	 * 
	 * @return String
	 */
	public String getTodayDateAndTime() {
		//DateAndTime _dateAndTimeObject = new DateAndTime();
		return _dateAndTimeObject.getCurrentTimeAndDate();
	}

	/**
	 * parse a day (such as Monday to int)
	 * @param dayString
	 * @return int
	 * @throws InvalidDayException
	 */
	public int parseDayToInt(String dayString) throws InvalidDayException{
		DayParser dayParser = DayParser.getInstance();
		return dayParser.parseDayToInt(dayString);
	}
	
	/**
	 * To parse day to date
	 * @throws DatePassedException 
	 * @throws InvalidDayException 
	 */
	public String parseDayToDate(String input) throws InvalidDayException, DatePassedException{
		DayParser dayParser = DayParser.getInstance();
		return dayParser.parseDayToDate(input);
	}
	
	/**
	 * @deprecated
	 * @param dayString
	 * @return
	 */
	protected boolean isDay(String dayString){
		DayParser dayParser = DayParser.getInstance();
		try {
			dayParser.parseDayToInt(dayString);
		} catch (InvalidDayException e) {
			//GuiManager.callOutput(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * parseDate: parse different formats of Date
	 * 
	 * @return timeString, in dd/mm/yyyy 
	 * @throws InvalidDateException 
	 */
	public String parseDate(String dateString) throws InvalidDateException{
		DateParser dateParser = DateParser.getInstance();
		return dateParser.parseDate(dateString);
	}

	/**
	 * Check if there is a valid date in the string
	 * @param String
	 * @returns DateObject
	 */
	
	public DateObject findDate(String input){
		DateAndTimeRetriever datr = DateAndTimeRetriever.getInstance();
		return datr.findDate(input);
	}
	
	/**
	 * Check if there is a valid time in the string
	 * @param String
	 * @returns TimeObject
	 */
	public TimeObject findTime(String input){
		DateAndTimeRetriever datr = DateAndTimeRetriever.getInstance();
		return datr.findTime(input);
	}
	
	/**
	 * parseTime: parse different format of time
	 * @param timeString
	 * @return String
	 * @throws NullTimeUnitException
	 * @throws NullTimeValueException
	 * @throws TimeErrorException
	 * @throws InvalidTimeException 
	 */
	public String parseTime(String timeString) throws NullTimeUnitException,
			NullTimeValueException, TimeErrorException, InvalidTimeException {
		return TimeParser.parseTime(timeString);
	}

	public String parseTimeInput(String timeString) throws TimeErrorException, InvalidTimeException {
		return TimeParser.parseTimeInput(timeString);
	}

	/**
	 * convertToSecond: convert time from any unit to second
	 * 
	 * @param timeString
	 *            : time value + time unit, ex: 1 min, one min, 1s
	 * @return: String
	 * @throws NullTimeUnitException
	 *             : User did not key in time unit
	 * @throws NullTimeValueException
	 *             : User did not key in time value / not valid time value
	 */
	public String convertToSecond(String timeString)
			throws NullTimeUnitException, NullTimeValueException {
		TimeWordParser twp = TimeWordParser.getInstance();
		return twp.parseTimeWord(timeString);
	}

	protected boolean isSecondInstance(String timeString) {
		TimeWordParser twp = TimeWordParser.getInstance();

		try {
			twp.parseTimeWord(timeString);
		} catch (NullTimeUnitException | NullTimeValueException e) {
			GuiManager.callOutput(e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * parseNumber: parse a language number to a real number String, ex: one to
	 * 1. It returns null when error occurs.
	 * 
	 * @param numberString
	 *            : language number or normal number
	 * @return String
	 */
	public String parseNumber(String numberString) {
		NumberParser parser = NumberParser.getInstance();
		return parser.parseTheNumbers(numberString);
	}
	
	
	/**
	 * Takes in a string and changes number words to numbers
	 * i.e. "one day in forever" changes to "1 day in forever"
	 * @param input
	 * @return string changed to numerics
	 */
	public String parseNumberString(String input){
		return DateAndTimeRetriever.getInstance().parseNumber(input);
	}

	protected boolean isNumber(String numberString) {
		NumberParser parser = NumberParser.getInstance();
		return parser.parseTheNumbers(numberString) != null;
	}
	
	public String parseTimeWord(String input) throws NullTimeUnitException, NullTimeValueException{
		return TimeWordParser.getInstance().parseTimeWordWithSpecialWord(input);
	}
	
	/**
	 * Takes in a string and formats date and time to TaskPad standards 
	 * dd/mm/yyyy and hh:mm
	 * @param input
	 * @return string with date and time replaced by standard date and time 
	 * or input string if no date and time exists
	 * @throws InvalidQuotesException 
	 */
	public String formatDateAndTimeInString(String input) throws InvalidQuotesException{
		DateAndTimeRetriever datr = DateAndTimeRetriever.getInstance();
		return datr.formatDateAndTimeInString(input);
	}
	
	public void setDebug(String dateString) throws ParseException{
		_dateAndTimeObject.setDebugDate(dateString);
	}

	/*
	 * DEPRECATED public String parseTime(String timeString) throws Exception{
	 * String inputString[] = timeString.split(SPACE); int length =
	 * inputString.length;
	 * 
	 * String numberString = inputString[length-2].trim(); numberString =
	 * parseNumber(numberString);
	 * 
	 * int t = Integer.parseInt(numberString);
	 * 
	 * String unit = inputString[length - 1]; calculateMultiple(unit);
	 * 
	 * t *= _multiple;
	 * 
	 * return t + EMPTY; }
	 */

	/*
	 * DEPRECATED private void calculateMultiple(String unit) throws Exception {
	 * switch (unit.toLowerCase()){ case "s": case "second": case "seconds":
	 * case "sec": case "secs": setMultiple(_multiple); break;
	 * 
	 * case "m": case "minute": case "minutes": case "min": case "mins":
	 * setMultiple(_multiple * CONVERT); break;
	 * 
	 * case "h": case "hour": case "hours": case "hr": case "hrs":
	 * setMultiple(_multiple * CONVERT * CONVERT); break;
	 * 
	 * default: GuiManager.callOutput(ERROR); throw EXCEPTION_INVALID_INPUT; } }
	 * 
	 * private void setMultiple(int multiple) { _multiple = multiple; }
	 */
	
	// for debug:
	/*
	 public static void main(String[] args){
		 DateAndTimeManager now = new DateAndTimeManager(); System.out.println(now.getTodayTime());
		 System.out.println(now.getTodayDate());
		 System.out.println(now.getTodayDay()); 
		 System.out.println(DateAndTimeManager.getInstance().parseNumberString("nine day in forever"));
	 }
	 */
}
