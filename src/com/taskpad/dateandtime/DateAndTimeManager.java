	//@author A0119646X

/* Public API to call dates */

package com.taskpad.dateandtime;

import java.text.ParseException;
import java.util.ArrayList;

import com.taskpad.ui.GuiManager;

/**
 * 
 * DateAndTimeManager is a facade class of dateandtime package.
 * 
 * 
 * 
 */
public class DateAndTimeManager implements TimeSkeleton, DateSkeleton {

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
	public String parseDayToDate(String input) throws InvalidDayException{
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
	 * @throws TimeErrorException
	 * @throws InvalidTimeException 
	 */
	@SuppressWarnings("deprecation")
	public String parseTime(String timeString) throws TimeErrorException, InvalidTimeException {
		TimeParser tp = TimeParser.getInstance();
		return tp.parseTime(timeString);
	}

	/**
	 * only can parse normal time, such as 1am, 11:00 ......
	 * but can parse next hour, 1 hour later......
	 * @param timeString
	 * @return
	 * @throws TimeErrorException
	 * @throws InvalidTimeException
	 */
	public String parseTimeInput(String timeString) throws TimeErrorException, InvalidTimeException {
		TimeParser tp = TimeParser.getInstance();
		return tp.parseTimeInput(timeString);
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
		return parseNumber(numberString, true);
	}
	
	/**
	 * 
	 * @param numberString
	 * @param isStrict
	 * @return
	 */
	public String parseNumber(String numberString, boolean isStrict) {
		NumberParser parser = NumberParser.getInstance();
		return parser.parseTheNumbers(numberString, isStrict);
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
		return parser.parseTheNumbers(numberString, true) != null;
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
	
	/**
	 * 
	 * @param input
	 * @return
	 * @throws InvalidQuotesException
	 */
	public ArrayList<String> searchTimeAndDate(String input) throws InvalidQuotesException{
		DateAndTimeRetriever datr = DateAndTimeRetriever.getInstance();
		return datr.searchTimeAndDate(input);
	}
	
	
	/**
	 * 
	 * @param firstDateString
	 * @param secondDateString
	 * @return
	 */
	public int compareDateAndTime(String firstDateString, String secondDateString){
		DateAndTimeRetriever datr = DateAndTimeRetriever.getInstance();
		return datr.compareDateAndTimeExecutor(firstDateString, secondDateString);
	}
	
	/**
	 * 
	 * @param dateString
	 * @return
	 */
	public int compareDateAndTime(String dateString){
		return compareDateAndTime(dateString, getTodayDate() + " 23:59");
	}
	
	/**
	 * 
	 * @param dateString
	 * @throws ParseException
	 */
	public void setDebug(String dateString) throws ParseException{
		_dateAndTimeObject.setDebugDate(dateString);
	}

}
