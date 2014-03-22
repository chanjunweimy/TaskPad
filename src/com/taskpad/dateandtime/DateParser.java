/**
 * This class is for FlexiCommands for date
 */

package com.taskpad.dateandtime;

/**
 * 
 * DateParser: a singleton class that specializes in parsing date
 *
 */
public class DateParser {
	private DateParser _dateParser = new DateParser();
	
	/**
	 * it's a singleton
	 */
	private DateParser(){
	}
	
	/**
	 * the method that get the instance in DateParser.
	 */
	protected DateParser getInstance(){
		return _dateParser;
	}

	/**
	 * takes in a date and parses it
	 * @param input String
	 * @return
	 */
	protected String parseDate(String input){
		String date = "";
		return date;
	}
	
	/**
	 * This method takes in a date and check if its a valid date - if cannot decode, should throw error to GUI
	 * @param input String
	 * @return boolean
	 */
	protected boolean checkIfValidDate(String input){
		return true;
	}
	
}
