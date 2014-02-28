package com.TaskPad.execute;

public class Task {
	private String description;
	private String deadline;
	private String startTime;
	private String endTime; 
	private String details;
	private int done;
	
	public Task(String description, String deadline, String startTime, String endTime, String details) {
		this.description = description;
		this.deadline = deadline;
		this.startTime = startTime;
		this.endTime = endTime;
		this.details = details;
		this.done = 0;
	}
	
	
}
