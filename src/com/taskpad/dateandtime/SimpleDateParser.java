package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** This class takes in a date and checks if it fits any of the SimpleDateFormat
 * Returns null string if otherwise
 * 
 * @author Lynnette, Jun
 *
 */

public class SimpleDateParser {

	//private static final String STRING_EMPTY = "";
	//private static final String DATE_INVALID = "Not a valid date";
	private static SimpleDateFormat _formatter = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateParser _dateParser = new SimpleDateParser();
	
	private static final String[] _dateWithoutYear = {
		"d MMM", "MMM d", "dMMM", "MMMd",
		"d/MM", "d-MM",  "d.MM", "d MM",
		"d/M", "d-M",  "d.M", "d M",
		
		
		"dd/M", "dd-M", "dd.M", "dd M", 
		"dd/MM", "dd-MM", "dd.MM", "dd MM",  
		"MMM dd", "dd MMM", "ddMMM", "MMMdd"

	};
	
	private static final String[] _dateFormats = {
		"d/MM/yy", "d-MM-yy", "d.MM.yy", "d MM yy", 
		"d/M/yy", "d-M-yy", "d.M.yy", "d M yy",
		"MMM d yy", "MMM d , yy", "MMM d, yy", "MMM d,yy",  
		"d MMM yy", "d MMM , yy", "d MMM, yy", "d MMM,yy", 
		"d-MMM-yy", "d MM , yy", "d MM, yy", "d MM,yy", 
		"d M , yy", "d M, yy", "d M,yy", "dMMMyy", 
		
		"dd/MM/yy", "dd-MM-yy", "dd.MM.yy", "dd MM yy", 
		"dd/M/yy", "dd-M-yy", "dd.M.yy", "dd M yy",
		"MMM dd yy", "MMM dd , yy", "MMM dd, yy", "MMM dd,yy",  
		"dd MMM yy", "dd MMM , yy", "dd MMM, yy", "dd MMM,yy", 
		"dd-MMM-yy", "dd MM , yy", "dd MM, yy", "dd MM,yy", 
		"dd M , yy", "dd M, yy", "dd M,yy", "ddMMMyy", 
		
		/*
		 * deprecated - we will only support d-m-y  
		"yy/dd/MM", "yy-dd-MM", 
		"yy.dd.MM", "yy dd MM", "yy-dd-MMM",  "yy/dd/MMM",
		"yy/d/M", "yy-d-M", "yy.d.M", "yy d M", 
		, "MMddyy", "yyMMMdd", "ddMMyy", 	
		*/
		//"MMMddyy",
		
		"d/MM/yyyy", "d-MM-yyyy", "d.MM.yyyy", "d MM yyyy", 
		"d/M/yyyy", "d-M-yyyy", "d.M.yyyy", "d M yyyy",
		"MMM d yyyy", "MMM d , yyyy", "MMM d, yyyy", "MMM d,yyyy",  
		"d MMM yyyy", "d MMM , yyyy", "d MMM, yyyy", "d MMM,yyyy", 
		"d-MMM-yyyy", "d MM , yyyy", "d MM, yyyy", "d MM,yyyy", 
		"d M , yyyy", "d M, yyyy", "d M,yyyy", "dMMMyyyy", 
		
		"dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy", "dd MM yyyy", 
		"dd/M/yyyy", "dd-M-yyyy", "dd.M.yyyy", "dd M yyyy",
		"MMM dd yyyy", "MMM dd , yyyy", "MMM dd, yyyy", "MMM dd,yyyy",  
		"dd MMM yyyy", "dd MMM , yyyy", "dd MMM, yyyy", "dd MMM,yyyy", 
		"dd-MMM-yyyy", "dd MM , yyyy", "dd MM, yyyy", "dd MM,yyyy", 
		"dd M , yyyy", "dd M, yyyy", "dd M,yyyy", "ddMMMyyyy"
		
		/*
		 * Deprecated for the reason above
		//"yyyy/dd/MM", "yyyy-dd-MM", "yyyy.dd.MM", "yyyy dd MM", 
		//"yyyy dd MMM", "yy dd MMM", "yyyy,dd MMM", "yyyy-dd-MMM", "yyyy/dd/MMM",
		//"yyyy/d/M", "yyyy-d-M", "yyyy.d.M", "yyyy d M", 
		 //"MMddyyyy", "yyyyMMMdd"锛�"ddMMyyyy", 
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
	
	protected String parseDate(String input) throws InvalidDateException, DatePassedException{
		if (input == null){
			throw new InvalidDateException();
		}
		
		input = input.trim();
		
		String dateString = getActualDate(input);
		
		if (dateString == null){
			throw new InvalidDateException();
		} 
		
		return dateString;
	}

	
	/**
	 * getActualDate: as formatDateWithoutYear() might parses
	 * a String with "dd mm yy" also, so, we have to consider cases:
	 * if we can parse it without year, but not with year, then is without year ; 
	 * if we can parse it without year and also with year, then is with year;
	 * if we can parse it with year only, then of course is with year;
	 * then if both also can't parse, then is null.
	 * @param input
	 * 				: String
	 * @return String
	 * @throws InvalidDateException 
	 * @throws DatePassedException 
	 */
	private String getActualDate(String input) throws DatePassedException {
		String dateStringWithoutYear = null;
		String dateString = null;
		
		try {
			dateStringWithoutYear = formatDateWithoutYear(input);
		} catch (DatePassedException e) {
			try {
				dateString = formatDate(input);
			} catch (DatePassedException e1) {
				throw e1;
			}
		}
		
		try {
			dateString = formatDate(input);
		} catch (DatePassedException e) {
			throw e;
		}

		if (dateString == null){
			dateString = dateStringWithoutYear;
		}
		return dateString;
	}
	
	private static String formatDateWithoutYear(String input) throws DatePassedException{
		assert (input != null);
		
		String dateString = null;
		for (String dwy: _dateWithoutYear){
			
			SimpleDateFormat sdf = new SimpleDateFormat(dwy);
			try {
				//System.out.println(_dateFormats[i]);
				
				//to strictly follow the format
				sdf.setLenient(false);
				
				Date date = sdf.parse(input);
				
				/*
				boolean isWrongFormat = !input.equals(sdf.format(date)) && !dwy.contains("MMM");
				if (isWrongFormat){
					continue;
				}*/
				
				date = setYear(date);
				
				if (isPassed(date) ){
					throw new DatePassedException();
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

	private static String formatDate(String input) throws DatePassedException{
		assert (input != null);
		
		String dateString = null;
		for (String df : _dateFormats){
			
			SimpleDateFormat sdf = new SimpleDateFormat(df);
			try {
				//System.out.println(_dateFormats[i]);
				
				//to strictly follow the format
				sdf.setLenient(false);
				
				Date date = sdf.parse(input);
				
				/*
				boolean isWrongFormat = !input.equals(sdf.format(date)) && !df.contains("MMM");
				if (isWrongFormat){
					continue;
				}*/
				
				if (isPassed(date) ){
					throw new DatePassedException();
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
