package com.taskpad.execute;

import java.util.LinkedList;
import java.util.Map;

import com.taskpad.data.DataFile;
import com.taskpad.data.DataManager;
import com.taskpad.input.Input;

public class ExcecutorManager {
	private static final String FEEDBACK_ADD = "Added:";
	
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
			break;
		case "DELETE":
			delete(parameters.get("TASKID"));
			break;
		case "ADDINFO":
			addInfo(parameters.get("TASKID"), parameters.get("INFO"));
			break;
		case "CLEAR":
			clear();
		case "DONE":
			markAsDone(parameters.get("TASKID"));
		}
	}

	private void clear() {
		listOfTasks = new LinkedList<Task>();
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		// pass feedback to gui
	}

	private void addInfo(String taskId, String info) {
		int index = Integer.parseInt(taskId) - 1;
		Task task = listOfTasks.get(index);
		String details = task.getDetails();
		details += info;
		task.setDetails(details);
		
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		passFeedbackToGui(getInfoOfTask(taskId));
	}

	private String getInfoOfTask(String taskId) {
		
	}

	private void delete(String index) {
		int indexOfTask = Integer.parseInt(index);
		Task taskDeleted = listOfTasks.get(indexOfTask);
		listOfTasks.remove(indexOfTask);
		
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		passFeedbackToGui(generateFeedbackForDelete(taskDeleted));
	}

	private String generateFeedbackForDelete(Task taskDeleted) {
		return "'" + taskDeleted.getDescription() + "' " + "deleted."; 
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

		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		int index = listOfTasks.size() - 1;
		passFeedbackToGui(generateFeedbackForAdd(taskToAdd, index));
	}

	private String generateFeedbackForAdd(Task taskToAdd, int index) {
		String firstLine = FEEDBACK_ADD;
		String secondLine = index+". "+taskToAdd.getDescription();
		return firstLine + "\n" + secondLine;
	}

	private void passFeedbackToGui(String feedback) {
		// TODO Auto-generated method stub
		
	}
}
