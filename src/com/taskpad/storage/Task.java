package com.taskpad.storage;

public class Task {
	private String description;
	private String deadline;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String venue; 
	private String details;
	private int done;	// 0 for undone, 1 for done
	
	public Task(String description, String deadline, String startDate, String startTime,
			String endDate, String endTime, String venue) {
		this.description = description;
		this.deadline = deadline;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.venue = venue;
		this.done = 0;
	}

	public Task(String description, String deadline, String startDate, String startTime,
			String endDate, String endTime, String venue,
			String details, int done) {
		this.description = description;
		this.deadline = deadline;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.details = details;
		this.venue = venue;
		this.done = done;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getDeadline() {
		return deadline;
	}

	public String getStartDate() {
		return startDate;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public String getEndDate() {
		return endDate;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public String getVenue() {
		return venue;
	}

	public String getDetails() {
		return details;
	}

	public int getDone() {
		return done;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setDone() {
		this.done = 1;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
}
