package com.taskpad.dateandtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** This class takes in a date and checks if it fits any of the SimpleDateFormat
 * Returns null string if otherwise
 * 
 * @author Lynnette
 *
 */

public class SimpleDateTimeParser {

	//private static final String STRING_EMPTY = "";
	private static final String DATE_INVALID = "Not a valid date";
	private static SimpleDateFormat _formatter = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateTimeParser _dateParser = new SimpleDateTimeParser();
	
	private static final String[] _dateFormats = {
		"dd/MM/yy", "dd-MM-yy", "dd.MM.yy", "dd MM yy",  
		"MMM dd yy", "MMM dd,yy", "MMM dd , yy", "MMM dd, yy",  
		"dd MMM yy", "dd MMM, yy", "dd-MMM-yy", "dd MM,yy", 
		"dd MM , yy", "dd MM, yy", "yy/dd/MM", "yy-dd-MM", 
		"yy.dd.MM", "yy dd MM", "yy-dd-MMM",  "yy/dd/MMM",
		"d/M/yy", "d-M-yy", "d.M.yy", "d M yy",
		"yy/d/M", "yy-d-M", "yy.d.M", "yy d M", 
		"ddMMyy", "ddMMMyy", "MMMddyy", "MMddyy", "yyMMMdd", 
		
		"dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy", "dd MM yyyy",
		"MMM dd yyyy", "MMM dd,yyyy", "MMM dd , yyyy", "MMM dd, yyyy",
		"dd MMM yyyy", "dd MMM,yyyy", "dd MMM , yyyy", "dd MMM, yyyy",
		"dd-MMM-yyyy", "dd MM,yyyy", "dd MM, yyyy", "dd MM , yyyy", 
		"yyyy/dd/MM", "yyyy-dd-MM", "yyyy.dd.MM", "yyyy dd MM", 
		"yyyy dd MMM", "yy dd MMM", "yyyy,dd MMM", "yyyy-dd-MMM", "yyyy/dd/MMM",
		"d/M/yyyy", "d-M-yyyy", "d.M.yyyy", "d M yyyy", 
		"yyyy/d/M", "yyyy-d-M", "yyyy.d.M", "yyyy d M", 
		"ddMMyyyy", "ddMMMyyyy", "MMMddyyyy", "MMddyyyy", "yyyyMMMdd"
	};
	
	private SimpleDateTimeParser(){
	}
	
	protected static SimpleDateTimeParser getInstance(){
		return _dateParser;
	}
	
	protected String parseDate(String input) throws InvalidDateException{
		if (input == null){
			throw new InvalidDateException(DATE_INVALID);
		}
		
		input = input.trim();
		
		String dateString = formatDate(input);
		
		if (dateString == null){
			throw new InvalidDateException(DATE_INVALID);
		} 
		
		return dateString;
	}
	
	private static String formatDate(String input){
		String dateString = null;
		for (int i=0; i<_dateFormats.length; i++){
			SimpleDateFormat sdf = new SimpleDateFormat(_dateFormats[i]);
			try {
				Date date = sdf.parse(input);
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
		System.out.println(formatDate("13-12-14"));
		System.out.println(formatDate("13 12 2014"));
		System.out.println(formatDate("1 Feb 14"));
		System.out.println(formatDate("2014 1 December"));
		System.out.println(formatDate("1December2014"));
		System.out.println(formatDate("011214"));
		//System.out.println(formatDate("03 02"));  //Will use system year at 1970
	}
	*/
}
