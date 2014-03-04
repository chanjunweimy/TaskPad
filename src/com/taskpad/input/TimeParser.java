/**
 * This class is for FlexiCommands for time
 */


package com.taskpad.input;

public class TimeParser {

	/* This method takes in a time and parses it
	 * 
	 */
	public String parseTime(String input){
		String time = "";
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
		} else if (decodeTime(input)){
			return true;
		}
		
		return false;
	}
	
	private boolean decodeTime(String input){
		return true;
	}
	
	private String stripTimeDelimiters(String input){
		String time = input.replaceAll(":", "");
		time = time.replaceAll(" ", "");
		time = time.replaceAll(".", "");
		
		return time;
	}
	
}
