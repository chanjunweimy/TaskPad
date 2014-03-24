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

	private static final String STRING_EMPTY = "";
	
	private static String _dateString = STRING_EMPTY;
	private static SimpleDateFormat _formatter = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat _sdf;
	private static Date _date;
	
	private static final String[] _dateFormats = {
		"dd/MM/yyyy", "dd/MM/yy", "dd-MM-yyyy", "dd-MM-yy", "dd.MM.yyyy", "dd.MM.yy", "dd MM yyyy", "dd MM yy",
		"MMM dd yyyy", "MMM dd yy", "MMM dd,yyyy", "MMM dd , yyyy", "MMM dd,yy", "MMM dd , yy", "MMM dd, yyyy", "MMM dd, yy", 
		"dd MMM yyyy", "dd MMM yy", "dd MMM,yyyy", "dd MMM , yyyy", "dd MMM, yy", "dd MMM, yyyy",
		"dd-MMM-yyyy", "dd-MMM-yy", "dd MM,yyyy", "dd MM, yyyy", "dd MM , yyyy", "dd MM,yy", "dd MM , yy", "dd MM, yy",
		"yyyy/dd/MM", "yy/dd/MM", "yyyy-dd-MM", "yy-dd-MM", "yyyy.dd.MM", "yy.dd.MM", "yyyy dd MM", "yy dd MM",
		"yyyy dd MMM", "yy dd MMM", "yyyy,dd MMM", "yyyy-dd-MMM", "yy-dd-MMM", "yyyy/dd/MMM", "yy/dd/MMM",
		"d/M/yyyy", "d/M/yy", "d-M-yyyy", "d-M-yy", "d.M.yyyy", "d.M.yy", "d M yyyy", "d M yy",
		"yyyy/d/M", "yy/d/M", "yyyy-d-M", "yy-d-M", "yyyy.d.M", "yy.d.M", "yyyy d M", "yy d M"
	};
	
	private SimpleDateTimeParser(){
	}
	
	protected String SimpleDateTime(String input){
		tryParseDate(input);
		
		return _dateString;
	}
	
	private static String tryParseDate(String input){
		for (int i=0; i<_dateFormats.length; i++){
			_sdf = new SimpleDateFormat(_dateFormats[i]);
			try {
				_date = _sdf.parse(input);
				_dateString = _formatter.format(_date);
			} catch (ParseException e){
				//do nothing
			}
		}
		return _dateString;
	}
		
	public static void main (String[] args){
		System.out.println(tryParseDate("13-12-14"));
		System.out.println(tryParseDate("13 12 2014"));
		System.out.println(tryParseDate("1 Feb 14"));
		System.out.println(tryParseDate("2014 1 December"));
	}
	
}
