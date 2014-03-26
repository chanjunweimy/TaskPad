package com.taskpad.dateandtime;

public class TimeObject {

	private String parsedTime;
	private String inputTime;
	
	public TimeObject(String parsedTime, String inputTime){
		this.parsedTime = parsedTime;
		this.inputTime = inputTime;
	}

	public String getParsedTime() {
		return parsedTime;
	}

	public String getInputTime() {
		return inputTime;
	}
	
}
