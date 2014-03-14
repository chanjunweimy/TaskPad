/* Public API to call dates */

package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.taskpad.execute.Task;
import com.taskpad.ui.GuiManager;

public class DateAndTimeManager implements TimeSkeleton, DateSkeleton {
	private static final String SPACE = " ";
	private final static String ERROR = "ERROR!";
	private final static int CONVERT = 60;
	private static final Exception EXCEPTION_INVALID_INPUT = new Exception();//don't know choose which
	private static final String EMPTY = "";

	
	private static int _multiple = 1;
	private static DateAndTime _dateAndTimeObject;
	
	public DateAndTimeManager(){
		_dateAndTimeObject = new DateAndTime();
	}

	public String getTodayTime(){
		return _dateAndTimeObject.getCurrentTime();
	}
	
	public String getTodayDate(){
		return _dateAndTimeObject.getCurrentDate();
	}
	
	public String getTodayDay(){
		return _dateAndTimeObject.getCurrentDay();
	}
	
	public String getTodayDateAndTime(){
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
	public static String parseDate(String dateString){
		return null;
	}
	
	public static String parseTime(String timeString) throws Exception{
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
	
	public static String parseNumber(String numberString) throws NullPointerException{
		NumberParser parser = new NumberParser();
		return parser.parseTheNumbers(numberString);
	}
	
	private static void calculateMultiple(String unit) throws Exception {
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
	
	private static void setMultiple(int multiple) {
		_multiple = multiple;
	}
	
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
