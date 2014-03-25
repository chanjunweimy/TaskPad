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
		"MMM dd yy", "MMM dd , yy", "MMM dd, yy", "MMM dd,yy",  
		"dd MMM yy", "dd MMM , yy", "dd MMM, yy", "dd MMM,yy", 
		"dd-MMM-yy", "dd MM , yy", "dd MM, yy", "dd MM,yy", 
		"d/M/yy", "d-M-yy",  "d.M.yy", "d M yy", 
		"ddMMMyy", "MMMddyy",
		
		/*
		 * we only support d-m-y in the momment 
		 */
		//"yy/dd/MM", "yy-dd-MM", 
		//"yy.dd.MM", "yy dd MM", "yy-dd-MMM",  "yy/dd/MMM",
		//"yy/d/M", "yy-d-M", "yy.d.M", "yy d M", 
		//, "MMddyy", "yyMMMdd", "ddMMyy", 	
		
		"dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy", "dd MM yyyy",
		"MMM dd yyyy", "MMM dd , yyyy", "MMM dd, yyyy", "MMM dd,yyyy", 
		"dd MMM yyyy", "dd MMM , yyyy", "dd MMM, yyyy", "dd MMM,yyyy", 
		"dd-MMM-yyyy", "dd MM, yyyy", "dd MM , yyyy",  "dd MM,yyyy", 
		"d/M/yyyy", "d-M-yyyy", "d.M.yyyy", "d M yyyy", 
		"ddMMyyyy", "ddMMMyyyy", "MMMddyyyy",
		//"yyyy/dd/MM", "yyyy-dd-MM", "yyyy.dd.MM", "yyyy dd MM", 
		//"yyyy dd MMM", "yy dd MMM", "yyyy,dd MMM", "yyyy-dd-MMM", "yyyy/dd/MMM",
		//"yyyy/d/M", "yyyy-d-M", "yyyy.d.M", "yyyy d M", 
		 //"MMddyyyy", "yyyyMMMdd"
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
				
				dateString = _formatter.format(date);
				break;
			} catch (ParseException e){
				//do nothing
			}
		}
		return dateString;
	}
	
	///* Testing
	public static void main (String[] args){
		//System.out.println(formatDate("13-12-14"));
		//System.out.println(formatDate("13 12 2014"));
		//System.out.println(formatDate("1 Feb 14"));
		//System.out.println(formatDate("2014 1 December"));
		//System.out.println(formatDate("1December2014"));
		//System.out.println(formatDate("011214"));
		//System.out.println(formatDate("03 02"));  //Will use system year at 1970
		//System.out.println(formatDate("Oct 18,93"));
	}
	//*/
}
