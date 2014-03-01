package com.taskpad.execute;

public class Task {
	private String description;
	//private String deadline;
	private String deadlineDay;
	private String deadlineMonth;
	private String deadlineYear;
	private String startTime;
	private String endTime;
	private String venue; 
	private String details;
	private int done;
	
	public Task(String description, String deadlineDay, String deadlineMonth,
			String deadlineYear, String startTime, String endTime, String venue) {
		this.description = description;
		this.deadlineDay = deadlineDay;
		this.deadlineMonth = deadlineMonth;
		this.deadlineYear = deadlineYear;
		this.startTime = startTime;
		this.endTime = endTime;
		this.venue = venue;
		this.done = 0;
	}
	
	public Task(String description, String deadline, String startTime, String endTime, String venue, String details, int done) {
		this.description = description;
		this.deadlineDay = deadlineDay;
		this.deadlineMonth = deadlineMonth;
		this.deadlineYear = deadlineYear;
		this.startTime = startTime;
		this.endTime = endTime;
		this.details = details;
		this.venue = venue;
		this.done = done;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDeadlineDay() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStartTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEndTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDone() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	
	
}
