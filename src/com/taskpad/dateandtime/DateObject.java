package com.taskpad.dateandtime;

/**
 * Date object to contain parsed date and the input date format
 *
 */

//@author A0119646X

public class DateObject {
	
	private String parsedDate;
	private String inputDate;
	
	public DateObject(String parsedDate, String inputDate){
		this.parsedDate = parsedDate;
		this.inputDate = inputDate;
	}

	public String getParsedDate() {
		return parsedDate;
	}

	public String getInputDate() {
		return inputDate;
	}
}
