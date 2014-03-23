/**
 * This class is for FlexiCommands for time
 */


package com.taskpad.dateandtime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeParser {
	
	private static String MESSAGE_TIME_ERROR = "Error: Invalid time format: %s. Time format should be hh:mm or hhmm";
	
	private static final String TIME_TWELVE = "12";
	private static final String TIME_EVE = "17";
	private static final String TIME_NIGHT = "19";
	private static final int TIME_NEG = -1;
	private static final String TIME_DEF = "-1";
	private static final String TIME_ZERO = "0";
	private static final String TIME_AM = "a";
	private static final String TIME_PM = "p";
	
	private static final String COLON = ":";
	private static final String SEMICOLON = ";";
	private static final String DOT = ".";
	private static final String EMPTY = "";

	private TimeParser(){
	}
	
	/* This method takes in a time and parses it
	 * 
	 */
	protected static String parseTime(String input) throws NullTimeUnitException, NullTimeValueException{
		TimeWordParser twp = TimeWordParser.getInstance();
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
	
	protected static String parseTimeInput(String input){
		String timeString = "";
		long time = 0;
		
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
		if (input.equals(EMPTY)){
			return true;
		}
		return false;
	}

	private static long decodeTime(String input){	    
        String hours = TIME_DEF, minutes = TIME_DEF;
        
        long time = TIME_NEG;
        
        time = checkMornAftEvenWords(input);
        
        if (time != TIME_NEG){
        	System.out.println("HI");
        	return time;
        }
        	    
	    if (timePatternMatch(input)) {

	        if (input.contains(COLON) || input.contains(DOT) || input.contains(SEMICOLON)) {
	            String[] inputs = input.split("[:.;]");
	            hours =  inputs[0];
	            minutes = inputs[1].substring(0, 2);
	        } else {
	            // Process strings like "8", "8p", "8pm", "2300"
	            if (input.contains(TIME_AM)) {
	            	System.out.println("AM");
	                hours = input.substring(0, input.indexOf(TIME_AM)).trim();	//am strings

	            } else if (input.contains(TIME_PM)) {
	                hours = input.substring(0, input.indexOf(TIME_PM)).trim();	//pm strings

	            } else if (input.length() < 3) {
	                hours = input;
	                
	            } else {
	                hours =  input.substring(0, input.length() - 2);
	                minutes = input.substring(input.length() - 2);
	            }
	        }
	        
	        if (input.contains(TIME_AM) && hours.equals(TIME_TWELVE)) {
	            hours = TIME_ZERO;
	        }

	        time = convertToSeconds(hours, minutes);

	        if (input.contains(TIME_PM) && !hours.equals(TIME_TWELVE)) {
	            time = addPM(time);
	        }

	        return time;
	    } 
	    else {
	    	//To take care of strings like 800 am
            if (input.contains(TIME_AM)) {
                hours = input.substring(0, input.indexOf(TIME_AM)).trim();	//am strings
                if (hours.length() > 4){		//Assume the first four numbers are valid
                	hours = hours.substring(0, 4);
                } 
               
                if (Integer.parseInt(hours) >= 12){
                	//throw new InvalidMorningTimeException(MESSAGE_INVALID_MORNING);
                }

            } else if (input.contains(TIME_PM)) {
                hours = input.substring(0, input.indexOf(TIME_PM)).trim();	//pm strings
                if (hours.length() > 4){
                	hours = hours.substring(0, 4);
                }
            }
            
	        time = convertToSeconds(hours, minutes);
	        
	        if (input.contains("p") && !hours.equals(TIME_TWELVE)) {
	            time = addPM(time);
	        }
	        
	        return time;
	    }
	 }

	private static boolean timePatternMatch(String input) {
		return time12Matches(input) || time24Matches(input);
	}
	
	private static boolean time12Matches(String input){
		Pattern time12 = Pattern.compile("^(1[012]|[1-9])([;:.][0-5][0-9])?(\\s)?(a|p|am|pm)?$");
	    Matcher time12M = time12.matcher(input);
	    
	    return time12M.matches();
	}
	
	private static boolean time24Matches(String input){
	    Pattern time24 = Pattern.compile("^(([01]?[0-9]|2[0-3])[;:.]?([0-5][0-9])?)$");
	    Matcher time24M = time24.matcher(input);
	    boolean time24Match = time24M.matches();
	    
	    return time24Match;
	}

	private static long addPM(long time) {
		time += 12 * 60 * 60 * 1000;
		return time;
	}
	
	private static long checkMornAftEvenWords(String input) {
		long time = TIME_NEG;
		String hours = TIME_ZERO;
		String minutes = TIME_ZERO;
		
        if (containsMornWords(input)){
        	hours = TIME_ZERO;
        	minutes = TIME_ZERO;
        } else if (containsAftWords(input)){
        	hours = TIME_TWELVE;
        	minutes = TIME_ZERO;
        } else if (containsEveWords(input)){
        	hours = TIME_EVE;
        	minutes = TIME_ZERO;
        } else if (containsNightWords(input)){
        	hours = TIME_NIGHT;
        	minutes = TIME_ZERO;
        } else {
        	return time;
        }
        
    	time = convertToSeconds(hours, minutes);
		return time;
	}

	private static boolean containsNightWords(String input) {
		return input.toLowerCase().contains("night") || input.toLowerCase().contains("ngt");
	}

	private static boolean containsEveWords(String input) {
		return input.toLowerCase().contains("evening") || input.toLowerCase().contains("eve");
	}

	private static boolean containsAftWords(String input) {
		return input.toLowerCase().contains("afternoon") || input.toLowerCase().contains("aft") ||
        		input.toLowerCase().contains("noon");
	}

	private static boolean containsMornWords(String input) {
		return input.toLowerCase().contains("morning") || input.toLowerCase().contains("morn");
	}

	private static long convertToSeconds(String hours, String minutes){
		return (Long.parseLong(hours)* 60 + Long.parseLong(minutes)) * 60 * 1000;
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
	
	private static boolean isInvalidTime(String timeString){
		if (timeString.equals("-1:-1")){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args){
		String input = "8am";
		long time = decodeTime(input);
		String timeString = convertMillisecondsToTime(time);
		System.out.println(input + " " + timeString);
	}
	
}
