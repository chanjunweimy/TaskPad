package com.taskpad.execute;

import java.util.LinkedList;
import java.util.Map;

import com.taskpad.data.DataManager;
import com.taskpad.input.Input;

public class ExcecutorManager {
	private static final String FEEDBACK_ADD = "Added:\n";
	
	private LinkedList<Task> listOfTasks;

	public ExcecutorManager(){
		
	}
	
	public void receiveFromInput(Input input){
		String commandType = input.getCommand();
		Map<String, String> parameters = input.getParameters();
		
		switch (commandType) {
		case "ADD":
			add(parameters.get("DESC"), parameters.get("DAY"),
					parameters.get("MONTH"), parameters.get("YEAR"),
					parameters.get("START"), parameters.get("END"),
					parameters.get("VENUE"));
		}
	}

	private void getListOfTasks(String file) {
		listOfTasks = DataManager.retrieve(file);
	}
	
	private void add(String description, String deadlineDay,
			String deadlineMonth, String deadlineYear, String startTime,
			String endTime, String venue) {
		Task taskToAdd = new Task(description, deadlineDay, deadlineMonth, deadlineYear,
				startTime, endTime, venue);
		listOfTasks.add(taskToAdd);

		int index = listOfTasks.size() - 1;
		passFeedbackToGui(generateFeedbackForAdd(taskToAdd, index));
	}

	private String generateFeedbackForAdd(Task taskToAdd, int index) {
		return index+". "+taskToAdd.getDescription();
	}

	private void passFeedbackToGui(String feedback) {
		// TODO Auto-generated method stub
		
	}
}
