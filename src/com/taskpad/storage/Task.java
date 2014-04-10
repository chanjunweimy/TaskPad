//@author A0105788U

package com.taskpad.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;


public class Task{
	private String description;
	private String deadline;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String venue; 
	private String details;
	private int done;	// 0 for undone, 1 for done
	private String reminderDate;
	
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
		this.reminderDate = "";
	}

	public Task(String description, String deadline, String startDate, String startTime,
			String endDate, String endTime, String venue,
			String details, int done, String reminderDate) {
		this.description = description;
		this.deadline = deadline;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.details = details;
		this.venue = venue;
		this.done = done;
		this.reminderDate = reminderDate;
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
	
	public String getReminderDate() {
		return reminderDate;
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
	
	public void setReminderDate(String date) {
		this.reminderDate = date;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
