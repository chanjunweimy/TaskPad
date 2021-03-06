//@author A0119646X

package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** This class takes in a date and checks if it fits any of the SimpleDateFormat
 * Returns null string if otherwise
 * 
 */

public class DateParser {

	//private static final String STRING_EMPTY = "";
	//private static final String DATE_INVALID = "Not a valid date";
	private static SimpleDateFormat _formatter = new SimpleDateFormat("dd/MM/yyyy");
	private static DateParser _dateParser = new DateParser();
	
	private static final String[] _dateWithoutYear = {
		"d MMMM", "MMMM d", "dMMMM", "MMMMd",
		"d/MMMM", "d-MMMM",
		"d MMM", "MMM d", "dMMM", "MMMd",
		"d/MMM", "d-MMM",
		"d/MM", "d-MM",  //"d.MM", //"d MM",
		"d/M", "d-M",  //"d.M", //"d M",
		
		"dd MMMM", "MMMM dd", "ddMMMM", "MMMMdd",
		"dd/MMMM", "dd-MMMM",
		"MMM dd", "dd MMM", "ddMMM", "MMMdd",
		"dd/MMM", "dd-MMM",
		"dd/M", "dd-M", //"dd.M", //"dd M", 
		"dd/MM", "dd-MM", //"dd.MM", //"dd MM", 
	};
	
	private static final String[] _dateFormats = {
		"d/MM/yy", "d-MM-yy", "d.MM.yy", //"d MM yy", 
		"d/M/yy", "d-M-yy", "d.M.yy", //"d M yy",
		"MMMM d , yy", "MMMM d, yy", "MMMM d,yy",  
		"d MMMM yy", "d MMMM , yy", "d MMMM, yy", "d MMMM,yy", 
		"d-MMMM-yy", "dMMMMyy",
		"MMM d , yy", "MMM d, yy", "MMM d,yy",  
		"d MMM yy", "d MMM , yy", "d MMM, yy", "d MMM,yy", 
		"d-MMM-yy", "d MM , yy", "d MM, yy", "d MM,yy", 
		"d M , yy", "d M, yy", "d M,yy", "dMMMyy", 
		
		"dd/MM/yy", "dd-MM-yy", "dd.MM.yy", //"dd MM yy", 
		"dd/M/yy", "dd-M-yy", "dd.M.yy", //"dd M yy",
		"MMMM dd , yy", "MMMM dd, yy", "MMMM dd,yy",  
		"dd MMMM yy", "dd MMMM , yy", "dd MMMM, yy", "dd MMMM,yy", 
		"dd-MMMM-yy", "ddMMMMyy",
		"MMM dd , yy", "MMM dd, yy", "MMM dd,yy",  
		"dd MMM yy", "dd MMM , yy", "dd MMM, yy", "dd MMM,yy", 
		"dd-MMM-yy", "dd MM , yy", "dd MM, yy", "dd MM,yy", 
		"dd M , yy", "dd M, yy", "dd M,yy", "ddMMMyy", 
		
		/*
		 * deprecated - we will only support d-m-y  
		"yy/dd/MM", "yy-dd-MM", 
		"yy.dd.MM", "yy dd MM", "yy-dd-MMM",  "yy/dd/MMM",
		"yy/d/M", "yy-d-M", "yy.d.M", "yy d M", 
		, "MMddyy", "yyMMMdd", "ddMMyy", 
		"MMMM d yy", "MMM d yy", "MMMM dd yy", "MMM dd yy", 
		*/
		//"MMMddyy",
		
		"d/MM/yyyy", "d-MM-yyyy", "d.MM.yyyy", //"d MM yyyy", 
		"d/M/yyyy", "d-M-yyyy", "d.M.yyyy", //"d M yyyy",
		"MMMM d , yyyy", "MMMM d, yyyy", "MMMM d,yyyy",  
		"d MMMM yyyy", "d MMMM , yyyy", "d MMMM, yyyy", "d MMMM,yyyy", 
		"d-MMMM-yyyy", "dMMMMyyyy",
		"MMM d , yyyy", "MMM d, yyyy", "MMM d,yyyy",  
		"d MMM yyyy", "d MMM , yyyy", "d MMM, yyyy", "d MMM,yyyy", 
		"d-MMM-yyyy", "d MM , yyyy", "d MM, yyyy", "d MM,yyyy", 
		"d M , yyyy", "d M, yyyy", "d M,yyyy", "dMMMyyyy", 
		
		"dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy", //"dd MM yyyy", 
		"dd/M/yyyy", "dd-M-yyyy", "dd.M.yyyy", //"dd M yyyy",
		"MMMM dd , yyyy", "MMMM dd, yyyy", "MMMM dd,yyyy",  
		"dd MMMM yyyy", "dd MMMM , yyyy", "dd MMMM, yyyy", "dd MMMM,yyyy", 
		"dd-MMMM-yyyy", "ddMMMMyyyy",
		"MMM dd , yyyy", "MMM dd, yyyy", "MMM dd,yyyy",  
		"dd MMM yyyy", "dd MMM , yyyy", "dd MMM, yyyy", "dd MMM,yyyy", 
		"dd-MMM-yyyy", "dd MM , yyyy", "dd MM, yyyy", "dd MM,yyyy", 
		"dd M , yyyy", "dd M, yyyy", "dd M,yyyy", "ddMMMyyyy"
		
		/*
		 * Deprecated for the reason above
		//"yyyy/dd/MM", "yyyy-dd-MM", "yyyy.dd.MM", "yyyy dd MM", 
		//"yyyy dd MMM", "yy dd MMM", "yyyy,dd MMM", "yyyy-dd-MMM", "yyyy/dd/MMM",
		//"yyyy/d/M", "yyyy-d-M", "yyyy.d.M", "yyyy d M", 
		 //"MMddyyyy", "yyyyMMMdd", "ddMMyyyy", 
		//"MMMddyyyy",
		"yyyy/d/M", "yyyy-d-M", "yyyy.d.M", "yyyy d M", 
		 "MMddyyyy", "yyyyMMMdd" "MMM d yyyy""MMM dd yyyy", 
		 */
	};
	
	/*
	private static final String[] MONTHS = {
		
	};
	
	private static HashMap _monthMap = new HashMap();
	*/
	
	private DateParser(){
	}
	
	protected static DateParser getInstance(){
		return _dateParser;
	}
	
	protected String parseDate(String input) throws InvalidDateException{
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
	 */
	private String getActualDate(String input){
		String dateStringWithoutYear = null;
		String dateString = null;
		
		input = input.toUpperCase();
		
		dateStringWithoutYear = formatDateWithoutYear(input);
		dateString = formatDate(input);

		if (dateString == null || dateString.trim().isEmpty()){
			dateString = dateStringWithoutYear;
		}
		return dateString;
	}
	
	private static String formatDateWithoutYear(String input){
		assert (input != null);
		
		String dateString = null;
		for (String dwy: _dateWithoutYear){
			
			SimpleDateFormat sdf = new SimpleDateFormat(dwy);
			try {
				//System.out.println(_dateFormats[i]);
				
				//to strictly follow the format
				sdf.setLenient(false);
				
				Date date = sdf.parse(input);
				
				
				boolean isWrongFormat = !input.equals(sdf.format(date).toUpperCase());
				if (isWrongFormat){
					continue;
				}
				
				date = setYear(date);
				
				/*
				if (isPassed(date) ){
					throw new DatePassedException();
				}
				*/
				
				dateString = _formatter.format(date);
				break;
			} catch (ParseException e){
				//do nothing
			}
		}
		return dateString;
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
		assert (input != null);
		
		String dateString = null;
		for (String df : _dateFormats){
			
			SimpleDateFormat sdf = new SimpleDateFormat(df);
			try {
				//System.out.println(_dateFormats[i]);
				
				//to strictly follow the format
				sdf.setLenient(false);
				
				Date date = sdf.parse(input);
				
				
				boolean isWrongFormat = !input.equals(sdf.format(date).toUpperCase());
				if (isWrongFormat){
					continue;
				}
				/*
				if (isPassed(date) ){
					throw new DatePassedException();
				}
				*/
				
				dateString = _formatter.format(date);
				break;
			} catch (ParseException e){
				//do nothing
			}
		}
		return dateString;
	}
	
	/**
	 * should not be true because even if 
	 * the date has passed it can be
	 * startDate also
	 * @deprecated
	 * @param date
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean isPassed(Date date) {
		/*
		Date now = new Date();
		return now.compareTo(date) > 0;
		*/
		
		return false;
	}
	
	///* Testing
	public static void main (String[] args){
		//System.out.println(formatDate("13-12-14"));
		//System.out.println(formatDate("13 12 2014"));
		//System.out.println(formatDate("1 Feb 14"));
		//System.out.println(formatDate("2014 1 December"));
		//System.out.println(formatDate("1December2014"));
		//System.out.println(formatDate("011214"));
		//System.out.println(formatDateWithoutYear("03 01"));  //Will use system year at 1970
		//System.out.println(formatDate("Oct 18,93"));
		try {
			System.out.println(DateParser.getInstance().parseDate("18Oct"));
		} catch (InvalidDateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//*/
}
