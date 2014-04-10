//@author A0119646X

/**
 * This class is for FlexiCommands for time
 */

package com.taskpad.dateandtime;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeParser {
	
	private final static Logger LOGGER = Logger.getLogger("TaskPad");

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

	private static TimeParser _timeParser = new TimeParser();
	
	private TimeParser(){
	}
	
	protected static TimeParser getInstance(){
		return _timeParser;
	}
	
	/**
	 * parses input to time format:
	 * it supports 1pm -> 13:00 like parseTimeInput
	 * and also 1 hour.
	 * But we are going to use DateAndTimeRetriever to do this
	 * @deprecated
	 * @param input
	 * @return
	 * @throws TimeErrorException
	 * @throws InvalidTimeException
	 */
	protected String parseTime(String input) throws TimeErrorException, InvalidTimeException{
		TimeWordParser twp = TimeWordParser.getInstance();
		
		String timeString = EMPTY;
		
		input = input.trim();
		
		LOGGER.info(input);
		try {
			timeString = twp.parseTimeWordWithSpecialWord(input);
		} catch (NullTimeUnitException | NullTimeValueException e) {
			LOGGER.warning(e.getMessage());
			timeString = parseTimeInput(input);
		}

		if (input.isEmpty()){
			throw new InvalidTimeException();
		}
		
		return timeString;
	}
	
	/**
	 * parses time like 1pm to standard format 13:00
	 * @param input
	 * @return
	 * @throws TimeErrorException
	 * @throws InvalidTimeException
	 */
	protected String parseTimeInput(String input) throws TimeErrorException, InvalidTimeException{
		String timeString = EMPTY;
		long time = 0;
		
		LOGGER.info("input is passed into parseTimeInput() :" + input);
		if (input == null){
			LOGGER.info(input + " is null!");
			throw new InvalidTimeException();
		}
		
		//to solve 8 am case
		input = input.replaceAll(" ", "");
		
		if (isDouble(input)){
			LOGGER.info(input + " is double!");
			throw new InvalidTimeException();
		}		
		
		//to solve 8 AM case
		input = input.toLowerCase();
		
		if(isNotEmptyString(input)){
			time = decodeTime(input);
			timeString = convertMillisecondsToTime(time);
		} else {
			throw new InvalidTimeException();
		}

		if (isInvalidTime(timeString)){
			throw new TimeErrorException(input);
		}
		
		return timeString;
	}
	
	private boolean isDouble(String input){
		try {
			Double.parseDouble(input);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}
	
	private boolean isNotEmptyString(String timeString) {
		return !timeString.trim().isEmpty();
	}

	/**
	 * @deprecated
	 * @param input
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean inputContainsTimeWords(String input) {
		if (input.equals(EMPTY)){
			return true;
		}
		return false;
	}

	private long decodeTime(String input) throws InvalidTimeException{	    
		input = input.trim();
        String hours = TIME_DEF, minutes = TIME_DEF;
        
        long time = TIME_NEG;
        
        time = checkMornAftEvenWords(input);
        
        if (time != TIME_NEG){
        	return time;
        }
        	    
	    if (timePatternMatch(input)) {
	    	LOGGER.info(input + " matched timePattern");
	    	
	        if (input.contains(COLON) || input.contains(DOT) || input.contains(SEMICOLON)) {
		    	LOGGER.info(input + " has time punctuation");

	            String[] inputs = input.split("[:.;]");
	            hours =  inputs[0];
	            minutes = inputs[1].substring(0, 2);
	            
	            LOGGER.info("hours: " + hours);
            	LOGGER.info("minutes: " + minutes);
	        } else {
	            // Process strings like "8", "8p", "8pm", "2300"
	            if (input.contains(TIME_AM)) {
	            	LOGGER.info(input + " matched timePattern and contains AM! time:" + time);
	            	
	                hours = input.substring(0, input.indexOf(TIME_AM)).trim();	//am strings
	                minutes = TIME_ZERO;
	                
	            	LOGGER.info("hours: " + hours);
	            	LOGGER.info("minutes: " + minutes);
	                
	                checkIfInvalidTimeString(hours, minutes, input);

	            } else if (input.contains(TIME_PM)) {
	            	LOGGER.info(input + " matched timePattern and contains PM! time:" + time);
	            	
	                hours = input.substring(0, input.indexOf(TIME_PM)).trim();	//pm strings
	                minutes = TIME_ZERO;
	                
	                LOGGER.info("hours: " + hours);
	            	LOGGER.info("minutes: " + minutes);
	                
	                checkIfInvalidTimeString(hours, minutes, input);

	            } /* else if (input.length() < 3) { SUPPORTED By Checking is double value
	            	/* Deprecated - not supportting single date values
	                hours = input;
	                minutes = TIME_ZERO;

	            	throw new InvalidTimeException(input);
	                
	            }*//* else { UNREACHABLE??
	            	LOGGER.info(input + " matched timePattern but no contains AM or PM! time:" + time);
	            	
	                hours =  input.substring(0, input.length() - 2);
	                minutes = input.substring(input.length() - 2);
	                
	                LOGGER.info("hours: " + hours);
	            	LOGGER.info("minutes: " + minutes);
	            }*/
	        }
	        	        
	        if (input.contains(TIME_AM) && hours.equals(TIME_TWELVE)) {
	        	LOGGER.info(input + " contains 12. Time:" + time);
	            hours = TIME_ZERO;
	            //minutes = TIME_ZERO;
	        }

	        time = convertToSeconds(hours, minutes);
	        LOGGER.info("Matched timePattern. time: " + time);

	        if (input.contains(TIME_PM) && !hours.equals(TIME_TWELVE)) {
	            time = addPM(time);
	            
	            LOGGER.info(input + " has pm but not 12pm. Time:" + time);
	        }

	        return time;
	    } else {
	    	LOGGER.info(input + " not matched timePattern");
	    	
	    	//To take care of strings like 800am
            if (input.contains(TIME_AM)) {
            	LOGGER.info(input + " not matched timePattern and contains AM! time:" + time);

                hours = input.substring(0, input.indexOf(TIME_AM)).trim();	//am strings
                LOGGER.info("hours: " + hours);
                
                if (hours.length() >= 4){ 
                	minutes = hours.substring(2, 4);
                	hours = hours.substring(0, 2);
                } else if (hours.length() == 3) {
                	minutes = hours.substring(1, 3);
                	hours = hours.substring(0, 1);
                } else {
                	throw new InvalidTimeException();
                }
                
                LOGGER.info("hours: " + hours);
            	LOGGER.info("minutes: " + minutes);
                
                checkIfInvalidTimeString(hours, minutes, input);

            } else if (input.contains(TIME_PM)) {
            	LOGGER.info(input + " not matched timePattern and contains PM! time:" + time);
            	
                hours = input.substring(0, input.indexOf(TIME_PM)).trim();	//pm strings
                LOGGER.info("hours: " + hours);
                
                if (hours.length() >= 4){ 
                	minutes = hours.substring(2, 4);
                	hours = hours.substring(0, 2);
                } else if (hours.length() == 3) {
                	minutes = hours.substring(1, 3);		
                	hours = hours.substring(0, 1);
                } else {
                	throw new InvalidTimeException();
                }

                LOGGER.info("hours: " + hours);
            	LOGGER.info("minutes: " + minutes);
                
                checkIfInvalidTimeString(hours, minutes, input);
            }
            
            if (input.contains(TIME_AM) && hours.equals(TIME_TWELVE)) {
	        	LOGGER.info(input + " contains 12. Time:" + time);
	            hours = TIME_ZERO;
	            //minutes = TIME_ZERO;
	        }
            
	        time = convertToSeconds(hours, minutes);
	        LOGGER.info("Not matched timePattern. time: " + time);
	        
	        if (input.contains(TIME_PM) && !hours.equals(TIME_TWELVE)) {
	            time = addPM(time);
	            LOGGER.info(input + " has pm but not 12pm. Time:" + time);
	        }
	        
	        return time;
	    }
	 }
	
	/**
	 * check whether timeString is valid:
	 * hour and minute can only be non-negative and cannot exceed their range
	 * @param hours
	 * @param minutes
	 * @param input
	 * @throws InvalidTimeException
	 */
	private void checkIfInvalidTimeString(String hours, String minutes, String input) throws InvalidTimeException {
		LOGGER.info("check whether " + input + " is invalid");
		LOGGER.info("hours: " + hours);
    	LOGGER.info("minutes: " + minutes);
		
    	if (!isDouble(hours) || !isDouble(minutes)){
    		LOGGER.severe("NOT NUMBER!");
        	throw new InvalidTimeException(input);
    	}
    	
        int h = Integer.parseInt(hours);
        int m = Integer.parseInt(minutes);
		if (h > 12 || m > 60){
			LOGGER.severe("h > 12 || m > 60");
			LOGGER.severe("h: " + h);
			LOGGER.severe("m: " + m);
        	throw new InvalidTimeException(input);
        } else if (h <= 0 || m < 0){
			LOGGER.severe("h <= 0 || m < 0");
        	LOGGER.severe("h: " + h);
			LOGGER.severe("m: " + m);
        	throw new InvalidTimeException(input);
        }
	}

	private boolean timePatternMatch(String input) {
		return time12Matches(input) || time24Matches(input);
	}
	
	private boolean time12Matches(String input){
		Pattern time12 = Pattern.compile("^(1[012]|[1-9])([;:.][0-5][0-9])?(a|p|am|pm)?$");
	    Matcher time12M = time12.matcher(input);
	    boolean time12Match = time12M.matches();
	    
		return time12Match;
	}
	
	private boolean time24Matches(String input){
	    Pattern time24 = Pattern.compile("^(([01]?[0-9]|2[0-3])[;:.]?([0-5][0-9])?)$");
	    Matcher time24M = time24.matcher(input);
	    boolean time24Match = time24M.matches();
	    
	    return time24Match;
	}

	private long addPM(long time) {
		time += 12 * 60 * 60 * 1000;
		return time;
	}
	
	private long checkMornAftEvenWords(String input) {
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
        } else if (containsMidnightWords(input)){
        	hours = TIME_ZERO;
        	minutes = TIME_ZERO;
        } else {
        	return time;
        }
        
        //System.err.println(hours + " " + minutes);
        
    	time = convertToSeconds(hours, minutes);
		return time;
	}

	private boolean containsNightWords(String input) {
		return input.toLowerCase().equals("night") || input.toLowerCase().equals("ngt");
	}

	private boolean containsEveWords(String input) {
		return input.toLowerCase().equals("evening") || input.toLowerCase().equals("eve");
	}

	private boolean containsAftWords(String input) {
		return input.toLowerCase().equals("afternoon") || input.toLowerCase().equals("aft") ||
        		input.toLowerCase().equals("noon");
	}

	private boolean containsMornWords(String input) {
		return input.toLowerCase().equals("morning") || input.toLowerCase().equals("morn");
	}

	
	private boolean containsMidnightWords(String input) {
		return input.toLowerCase().equals("midnight") || input.toLowerCase().equals("midngt");
	}
	
	private long convertToSeconds(String hours, String minutes){
		return (Long.parseLong(hours) * 60 + Long.parseLong(minutes)) * 60 * 1000;
	}
	
	private String convertMillisecondsToTime(long milliseconds){
		int minutes = (int) ((milliseconds / (1000*60)) % 60);
		int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
		
		LOGGER.info("milliseconds : " + milliseconds);
		LOGGER.info("minutes : " + minutes);
		LOGGER.info("hours : " + hours);
		
		String hourString = "" + hours;
		if (hourString.length() == 1){
			hourString = TIME_ZERO + hourString;
		}
		
		String minuteString = EMPTY + minutes;
		if (minuteString.length() == 1){
			minuteString = TIME_ZERO + minuteString;
		}
		String timeString = hourString + COLON + minuteString;
		
		LOGGER.info("hourString : " + hourString);
		LOGGER.info("minuteString : " + minuteString);
		LOGGER.info("timeString : " + timeString);
		
		return timeString;
	}
	
	private boolean isInvalidTime(String timeString){
		if (timeString.trim().equals("-1:-1")){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args){
		String input = "12pm";
		
		//System.out.println(checkMornAftEvenWords(input));
		TimeParser tp = TimeParser.getInstance();
		String time = null;
		try {
			time = tp.parseTime(input);
		} catch (InvalidTimeException | TimeErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String timeString = convertMillisecondsToTime(time);
		System.out.println(input + " " + time);
	}
	
}
