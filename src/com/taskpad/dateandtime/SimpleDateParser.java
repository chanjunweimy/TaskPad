package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** This class takes in a date and checks if it fits any of the SimpleDateFormat
 * Returns null string if otherwise
 * 
 * @author Lynnette
 *
 */

public class SimpleDateParser {

	//private static final String STRING_EMPTY = "";
	private static final String DATE_INVALID = "Not a valid date";
	private static SimpleDateFormat _formatter = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateParser _dateParser = new SimpleDateParser();
	
	private static final String[] _dateWithoutYear = {
		"dd/MM", "dd-MM", "dd.MM", "dd MM",  
		"MMM dd",  
		"dd MMM",
		"dd MM",
		"d/M", "d-M",  "d.M", "d M", 
		"ddMMM", "MMMdd"
	};
	
	private static final String[] _dateFormats = {
		"dd/MM/yy", "dd-MM-yy", "dd.MM.yy", "dd MM yy",  
		"MMM dd yy", "MMM dd , yy", "MMM dd, yy", "MMM dd,yy",  
		"dd MMM yy", "dd MMM , yy", "dd MMM, yy", "dd MMM,yy", 
		"dd-MMM-yy", "dd MM , yy", "dd MM, yy", "dd MM,yy", 
		"d/M/yy", "d-M-yy",  "d.M.yy", "d M yy", 
		"ddMMMyy", 
		
		/*
		 * deprecated - we will only support d-m-y  
		"yy/dd/MM", "yy-dd-MM", 
		"yy.dd.MM", "yy dd MM", "yy-dd-MMM",  "yy/dd/MMM",
		"yy/d/M", "yy-d-M", "yy.d.M", "yy d M", 
		, "MMddyy", "yyMMMdd", "ddMMyy", 	
		*/
		//"MMMddyy",
		
		"dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy", "dd MM yyyy",
		"MMM dd yyyy", "MMM dd , yyyy", "MMM dd, yyyy", "MMM dd,yyyy", 
		"dd MMM yyyy", "dd MMM , yyyy", "dd MMM, yyyy", "dd MMM,yyyy", 
		"dd-MMM-yyyy", "dd MM, yyyy", "dd MM , yyyy",  "dd MM,yyyy", 
		"d/M/yyyy", "d-M-yyyy", "d.M.yyyy", "d M yyyy", 
		"ddMMMyyyy", 
		
		/*
		 * Deprecated for the reason above
		//"yyyy/dd/MM", "yyyy-dd-MM", "yyyy.dd.MM", "yyyy dd MM", 
		//"yyyy dd MMM", "yy dd MMM", "yyyy,dd MMM", "yyyy-dd-MMM", "yyyy/dd/MMM",
		//"yyyy/d/M", "yyyy-d-M", "yyyy.d.M", "yyyy d M", 
		 //"MMddyyyy", "yyyyMMMdd"， "ddMMyyyy", 
		//"MMMddyyyy",
		"yyyy/d/M", "yyyy-d-M", "yyyy.d.M", "yyyy d M", 
		 "MMddyyyy", "yyyyMMMdd"
		 */
	};
	
	private SimpleDateParser(){
	}
	
	protected static SimpleDateParser getInstance(){
		return _dateParser;
	}
	
	protected String parseDate(String input) throws InvalidDateException{
		if (input == null){
			throw new InvalidDateException(DATE_INVALID);
		}
		
		input = input.trim();
		
		String dateString = null;
		
		dateString = formatDateWithoutYear(input);
		if (dateString == null){
			dateString = formatDate(input);
		}
		
		if (dateString == null){
			throw new InvalidDateException(DATE_INVALID);
		} 
		
		return dateString;
	}
	
	private static String formatDateWithoutYear(String input){
		assert (input == null);
		
		String dateString = null;
		for (int i=0; i<_dateWithoutYear.length; i++){
			
			SimpleDateFormat sdf = new SimpleDateFormat(_dateWithoutYear[i]);
			try {
				//System.out.println(_dateFormats[i]);
				
				Date date = sdf.parse(input);
				
				boolean isWrongFormat = !input.equals(sdf.format(date));
				if (isWrongFormat){
					continue;
				}
				
				
				date = setYear(date);
				
				if (isPassed(date) ){
					continue;
				}
				
				dateString = _formatter.format(date);
				break;
			} catch (ParseException e){
				//do nothing
			}
		}
		return dateString;
	}

	private static boolean isPassed(Date date) {
		Date now = new Date();
		return now.compareTo(date) > 0;
	}
	
	private static Date setYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, year);
		date = calendar.getTime();
		
		return date;
	}

	private static String formatDate(String input){
		assert (input == null);
		
		String dateString = null;
		for (int i=0; i<_dateFormats.length; i++){
			
			SimpleDateFormat sdf = new SimpleDateFormat(_dateFormats[i]);
			try {
				//System.out.println(_dateFormats[i]);
				
				Date date = sdf.parse(input);
				
				boolean isWrongFormat = !input.equals(sdf.format(date));
				if (isWrongFormat){
					continue;
				}
				
				if (isPassed(date) ){
					continue;
				}
				
				dateString = _formatter.format(date);
				break;
			} catch (ParseException e){
				//do nothing
			}
		}
		return dateString;
	}
	
	/* Testing
	public static void main (String[] args){
		//System.out.println(formatDate("13-12-14"));
		//System.out.println(formatDate("13 12 2014"));
		//System.out.println(formatDate("1 Feb 14"));
		//System.out.println(formatDate("2014 1 December"));
		//System.out.println(formatDate("1December2014"));
		//System.out.println(formatDate("011214"));
		//System.out.println(formatDateWithoutYear("03 01"));  //Will use system year at 1970
		//System.out.println(formatDate("Oct 18,93"));
	}
	//*/
}
