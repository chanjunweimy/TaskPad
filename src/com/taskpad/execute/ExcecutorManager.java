package com.taskpad.execute;

import java.util.LinkedList;
import java.util.Map;

import com.taskpad.data.DataManager;
import com.taskpad.input.Input;

public class ExcecutorManager {
	private static final String FEEDBACK_ADD = null;
	
	private LinkedList<Task> listOfTasks;

	public ExcecutorManager(){
		
	}
	
	public void receiveFromInput(Input input){
		String commandType = input.getCommand();
		Map<String, String> parameters = input.getParameters();
	}

	private void getListOfTasks(String file) {
		listOfTasks = DataManager.retrieve(file);
	}
	
	private void add(String description, String deadline,
			String startTime, String endTime, String details) {
		Task taskToAdd = new Task(description, deadline, startTime, endTime, details);
		listOfTasks.add(taskToAdd);
		passFeedbackToGui(FEEDBACK_ADD);
	}

	private void passFeedbackToGui() {
		// TODO Auto-generated method stub
		
	}
}
