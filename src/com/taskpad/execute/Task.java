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
	private int done;	// 0 for undone, 1 for done
	
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

	public Task(String description, String deadlineDay, String deadlineMonth,
			String deadlineYear, String startTime, String endTime, String venue,
			String details, int done) {
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
		return description;
	}

	public String getDeadlineDay() {
		return deadlineDay;
	}
	
	public String getDeadlineMonth() {
		return deadlineMonth;
	}

	public String getDeadlineYear() {
		return deadlineYear;
	}

	public String getStartTime() {
		return startTime;
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



	
	
}
