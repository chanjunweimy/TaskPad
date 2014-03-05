/**
 * This class is for FlexiCommands for time
 */


package com.taskpad.timeanddate;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeParser {
	
	private static int TIME_LENGTH = 5;
	
	private static String MESSAGE_TIME_ERROR = "Error: Invalid time format: %s. Time format should be hh:mm or hhmm";

	/* This method takes in a time and parses it
	 * 
	 */
	public static String parseTime(String input){
		if (input.length() > TIME_LENGTH){
			return String.format(MESSAGE_TIME_ERROR, input);
		}
		
		String time = replaceColonTypo(input);
		
		return time;
	}
	
	/* This method takes in a time and check if its a valid 24h clock time
	 * If not, decode it
	 * 
	 */
	public boolean checkIfValidTime(String input){
		String time = stripTimeDelimiters(input);
		int timeInt = Integer.parseInt(time);
		
		if (timeInt >= 0000 && timeInt <= 2400){
			return true;
		} 
		
		return false;
	}
	
	private static long decodeTime(String input){
		Pattern time12 = Pattern.compile("^(1[012]|[1-9])([:.][0-5][0-9])?(\\s)?(a|p|am|pm)?$");
	    Pattern time24 = Pattern.compile("^(([01]?[0-9]|2[0-3])[:.]?([0-5][0-9])?)$");
	    
	    Matcher time12M = time12.matcher(input);
	    boolean time12Match = time12M.matches();
	    
	    Matcher time24M = time24.matcher(input);
	    boolean time24Match = time24M.matches();
	    
	    if (time12Match || time24Match) {

	        String hours = "0", minutes = "0";

	        if (input.contains(":") || input.contains(".")) {
	            String[] inputs = input.split("[:.]");
	            hours =  inputs[0];
	            minutes = inputs[1].substring(0, 2);
	        } else {
	            // Process strings like "8", "8p", "8pm", "2300"
	            if (input.contains("a")) {
	                hours = input.substring(0, input.indexOf("a")).trim();
	            } else if (input.contains("p")) {
	                hours = input.substring(0, input.indexOf("p")).trim();
	            } else if (input.length() < 3) {
	                hours = input;
	            } else {
	                hours =  input.substring(0, input.length() - 2);
	                minutes = input.substring(input.length() - 2);
	            }
	        }
	        if (input.contains("a") && hours.equals("12")) {
	            // 12am is actually zero hours
	            hours = "0";
	        }

	        long time = (Long.parseLong(hours)* 60 + Long.parseLong(minutes)) * 60 * 1000;

	        if (input.contains("p") && !hours.equals("12")) {
	            // "pm" adds 12 hours to the total, except for 12pm
	            time += 12 * 60 * 60 * 1000;
	        }

	        return time;
	    }
	    return 0;
	    
	}
	
	private static String convertToTime(long milliseconds){
		Date date = new Date(milliseconds);
		return "";
	}
	
	private String stripTimeDelimiters(String input){
		String time = input.replaceAll(":", "").trim();
		time = time.replaceAll(" ", "");
		time = time.replaceAll(".", "");
		
		return time;
	}
	
	private static String replaceColonTypo(String time){
		return time.replace(";", ":").trim();
	}
	
//	public static void main(String[] args){
//		String input = "13:00";
//		System.out.println(parseTime(input));
//	}
	
}
