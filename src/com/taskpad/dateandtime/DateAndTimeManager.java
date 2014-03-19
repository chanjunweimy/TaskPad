/* Public API to call dates */

package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.taskpad.execute.Task;


/**
 * 
 * @author Jun
 *
 * use singleton to 
 * implement this class
 * because a manager can do all the parsing
 * and can return all the date and time
 *
 */
public class DateAndTimeManager implements TimeSkeleton, DateSkeleton {
	/* DEPRECATED
	private static final String SPACE = " ";
	private final static String ERROR = "ERROR!";
	private final static int CONVERT = 60;
	private static final Exception EXCEPTION_INVALID_INPUT = new Exception();//don't know choose which
	private static final String EMPTY = "";

	
	private static int _multiple = 1;
	*/
	
	private static DateAndTime _dateAndTimeObject = null;
	
	private static DateAndTimeManager _managerInstance = new DateAndTimeManager();
	
	private DateAndTimeManager(){
	}
	
	public static DateAndTimeManager getInstance() {
		return _managerInstance;
	}

	@Override
	public String getTodayTime(){
		_dateAndTimeObject = new DateAndTime();
		return _dateAndTimeObject.getCurrentTime();
	}
	
	@Override
	public String getTodayDate(){
		_dateAndTimeObject = new DateAndTime();
		return _dateAndTimeObject.getCurrentDate();
	}
	
	@Override
	public String getTodayDay(){
		_dateAndTimeObject = new DateAndTime();
		return _dateAndTimeObject.getCurrentDay();
	}
	
	public String getTodayDateAndTime(){
		_dateAndTimeObject = new DateAndTime();
		return _dateAndTimeObject.getCurrentTimeAndDate();
	}

	public static final Comparator<Task> ACCENDING_ORDER = 
			new Comparator<Task>() {
			@Override
		public int compare(Task e1, Task e2) {
			SimpleDateFormat dateConverter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date d1, d2;
			try {
				d1 = dateConverter.parse(e1.getDeadline() + e1.getEndTime());
				d2 = dateConverter.parse(e2.getDeadline() + e2.getEndTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				return 0;
			}
			return d1.compareTo(d2);
		}
	};
	
	
	/**
	 * These 2 methods are going to implement
	 * to do the parseDate() and parseTime() thing
	 * @return
	 */
	public String parseDate(String dateString){
		return null;
	}
	
	public String parseTime(String timeString){
		return null;
	}
	
	/**
	 * convertToSecond: convert time from any unit to second
	 * @param timeString: time value + time unit, ex: 1 min, one min, 1s
	 * @return: return the value of seconds.
	 * @throws NullTimeUnitException: User did not key in time unit
	 * @throws NullTimeValueException: User did not key in time value / not valid time value
	 */
	public String convertToSecond(String timeString) throws NullTimeUnitException, NullTimeValueException{
		TimeWordParser twp = TimeWordParser.getInstance();
		return twp.parseTimeWord(timeString);
	}
	
	/**
	 * parseNumber: parse a languange number to a real number String, ex: one to 1
	 * @param numberString: language number or normal number
	 * @return the normal number String
	 */
	public String parseNumber(String numberString){
		NumberParser parser = NumberParser.getInstance();
		return parser.parseTheNumbers(numberString);
	}
	
	
	/* DEPRECATED
	public String parseTime(String timeString) throws Exception{
		String inputString[] = timeString.split(SPACE);
		int length = inputString.length;
		
		String numberString = inputString[length-2].trim();
		numberString = parseNumber(numberString);
		
		int t = Integer.parseInt(numberString);
		
		String unit = inputString[length - 1];
		calculateMultiple(unit);
		
		t *= _multiple;
		
		return t + EMPTY;
	}
	 */
	
	
	
	/* DEPRECATED
	private void calculateMultiple(String unit) throws Exception {
		switch (unit.toLowerCase()){
		case "s":
		case "second":
		case "seconds":
		case "sec":
		case "secs":
			setMultiple(_multiple);
			break;

		case "m":
		case "minute":
		case "minutes":
		case "min":
		case "mins":
			setMultiple(_multiple * CONVERT);
			break;

		case "h":
		case "hour":
		case "hours":
		case "hr":
		case "hrs":
			setMultiple(_multiple * CONVERT * CONVERT);
			break;

		default:
			GuiManager.callOutput(ERROR);
			throw EXCEPTION_INVALID_INPUT;
		}
	}
	
	private void setMultiple(int multiple) {
		_multiple = multiple;
	}
	*/

	
	
	//for debug:
	/*
	public static void main(String[] args){
		DateAndTimeManager now = new DateAndTimeManager();
		System.out.println(now.getTodayTime());
		System.out.println(now.getTodayDate());
		System.out.println(now.getTodayDay());
	}
	*/
}
