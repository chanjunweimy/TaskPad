/**
 * This class is for FlexiCommands for time
 */


package com.taskpad.timeanddate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeParser {
	
	private static String MESSAGE_TIME_ERROR = "Error: Invalid time format: %s. Time format should be hh:mm or hhmm";

	/* This method takes in a time and parses it
	 * 
	 */
	public static String parseTime(String input){
		TimeWordParser twp = new TimeWordParser();
		String timeString = "";
		long time = 0;
		
		timeString = twp.timeWord(input);
		if(!inputContainsTimeWords(timeString)){
			time = decodeTime(input);
			timeString = convertMillisecondsToTime(time);
		}
				
		if (isInvalidTime(timeString)){
			return timeErrorMessage(input);
		}
		
		return timeString;
	}
	
	private static boolean inputContainsTimeWords(String input) {
		if (input.equals("")){
			return true;
		}
		return false;
	}

	private static long decodeTime(String input){
		Pattern time12 = Pattern.compile("^(1[012]|[1-9])([;:.][0-5][0-9])?(\\s)?(a|p|am|pm)?$");
	    Pattern time24 = Pattern.compile("^(([01]?[0-9]|2[0-3])[;:.]?([0-5][0-9])?)$");
	    
	    Matcher time12M = time12.matcher(input);
	    boolean time12Match = time12M.matches();
	    
	    Matcher time24M = time24.matcher(input);
	    boolean time24Match = time24M.matches();
	    
        String hours = "-1", minutes = "-1";
	    
	    if (time12Match || time24Match) {

	        if (input.contains(":") || input.contains(".") || input.contains(";")) {
	            String[] inputs = input.split("[:.;]");
	            hours =  inputs[0];
	            minutes = inputs[1].substring(0, 2);
	        } else {
	            // Process strings like "8", "8p", "8pm", "2300"
	            if (input.contains("a")) {
	                hours = input.substring(0, input.indexOf("a")).trim();	//am strings

	            } else if (input.contains("p")) {
	                hours = input.substring(0, input.indexOf("p")).trim();	//pm strings

	            } else if (input.length() < 3) {
	                hours = input;
	                
	            } else {
	                hours =  input.substring(0, input.length() - 2);
	                minutes = input.substring(input.length() - 2);
	            }
	        }
	        
	        if (input.contains("a") && hours.equals("12")) {
	            hours = "0";
	        }

	        long time = (Long.parseLong(hours)* 60 + Long.parseLong(minutes)) * 60 * 1000;
	        System.out.println(time);

	        if (input.contains("p") && !hours.equals("12")) {
	            // Add 12 hours for pm times
	            time += 12 * 60 * 60 * 1000;
	        }

	        return time;
	    } 
	    else {
	    	//To take care of strings like 800 am
            if (input.contains("a")) {
                hours = input.substring(0, input.indexOf("a")).trim();	//am strings
                if (hours.length() > 4){		//Assume the first four numbers are valid
                	hours = hours.substring(0, 4);
                }

            } else if (input.contains("p")) {
                hours = input.substring(0, input.indexOf("p")).trim();	//pm strings
                if (hours.length() > 4){
                	hours = hours.substring(0, 4);
                }
            }
            
	        long time = (Long.parseLong(hours)* 60 + Long.parseLong(minutes)) * 60 * 1000;
	        
	        if (input.contains("p") && !hours.equals("12")) {
	            time += 12 * 60 * 60 * 1000;
	        }
	        
	        return time;
	    }
	 }
	
	private static String convertMillisecondsToTime(long milliseconds){
		int minutes = (int) ((milliseconds / (1000*60)) % 60);
		int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
		
		String hourString = "" + hours;
		if (hourString.length() == 1){
			hourString = "0" + hourString;
		}
		
		String minuteString = "" + minutes;
		if (minuteString.length() == 1){
			minuteString = "0" + minuteString;
		}
		String timeString = hourString + ":" + minuteString;
		
		return timeString;
	}
	
	private static String timeErrorMessage(String input){
		String errorMessage = String.format(MESSAGE_TIME_ERROR);
		return errorMessage;
	}
	
	public static boolean isInvalidTime(String timeString){
		if (timeString.equals("-1:-1")){
			return true;
		}
		return false;
	}
	
	/* Testing
	public static void main(String[] args){
		String input = "2008";
		long time = decodeTime(input);
		System.out.println("Time " + time);
		String timeString = convertMillisecondsToTime(time);
		System.out.println(input + " " + timeString);
	}
	*/
	
}
