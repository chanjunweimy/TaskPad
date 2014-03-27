/* Public API to call dates */

package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.taskpad.storage.Task;
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

	private static DateAndTime _dateAndTimeObject = null;

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
		_dateAndTimeObject = new DateAndTime();
		return _dateAndTimeObject.getCurrentTime();
	}

	/**
	 * getTodayDate: return today's date
	 * 
	 * @return String
	 */
	@Override
	public String getTodayDate() {
		_dateAndTimeObject = new DateAndTime();
		return _dateAndTimeObject.getCurrentDate();
	}

	/**
	 * getTodayDay: return today's day
	 * 
	 * @return String
	 */
	@Override
	public String getTodayDay() {
		_dateAndTimeObject = new DateAndTime();
		return _dateAndTimeObject.getCurrentDay();
	}

	/**
	 * getTodayDateAndTime: to get today's date and the current time
	 * 
	 * @return String
	 */
	public String getTodayDateAndTime() {
		_dateAndTimeObject = new DateAndTime();
		return _dateAndTimeObject.getCurrentTimeAndDate();
	}

	/**
	 * ACCENDING_ORDER: a comparator used to sort Task by their dates.
	 */
	public static final Comparator<Task> ACCENDING_ORDER = new Comparator<Task>() {
		/**
		 * compare: compare two tasks' Date
		 * 
		 * @param e1
		 *            : task1
		 * @param e2
		 *            : task2
		 * @return int
		 */
		@Override
		public int compare(Task e1, Task e2) {
			SimpleDateFormat dateConverter = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm");
			Date d1, d2;
			try {
				d1 = dateConverter.parse(e1.getDeadline() + e1.getEndTime());
				d2 = dateConverter.parse(e2.getDeadline() + e2.getEndTime());
			} catch (ParseException e) {
				System.err.println(e.getMessage());
				return 0;
			}
			return d1.compareTo(d2);
		}
	};
	
	/**
	 * formatDateAndTimeInString
	 * This method takes in an input string and returns the string 
	 * with all the number words converted to numbers, special words converted to date
	 * and time words converted to time
	 */
	public String formatDateAndTimeInString(String input){
		return DateAndTimeRetriever.formatDateAndTimeInString(input);
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
	
	protected boolean isDay(String dayString){
		DayParser dayParser = DayParser.getInstance();
		try {
			dayParser.parseDayToInt(dayString);
		} catch (InvalidDayException e) {
			GuiManager.callOutput(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * parseDate: parse different formats of Date
	 * 
	 * @return timeString, in dd/mm/yyyy 
	 * @throws InvalidDateException 
	 * @throws DatePassedException 
	 */
	public String parseDate(String dateString) throws DatePassedException, InvalidDateException{
		SimpleDateParser dateParser = SimpleDateParser.getInstance();
		return dateParser.parseDate(dateString);
	}

	/**
	 * Check if there is a valid date in the string
	 * @param String
	 * @returns DateObject
	 */
	
	public DateObject findDate(String input){
		return DateAndTimeRetriever.findDate(input);
	}
	
	/**
	 * Check if there is a valid time in the string
	 * @param String
	 * @returns TimeObject
	 */
	public TimeObject findTime(String input){
		return DateAndTimeRetriever.findTime(input);
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

	protected boolean isNumber(String numberString) {
		NumberParser parser = NumberParser.getInstance();
		return parser.parseTheNumbers(numberString) != null;
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
	 * public static void main(String[] args){ DateAndTimeManager now = new
	 * DateAndTimeManager(); System.out.println(now.getTodayTime());
	 * System.out.println(now.getTodayDate());
	 * System.out.println(now.getTodayDay()); }
	 */
}
