package com.taskpad.execute;

import java.util.LinkedList;
import java.util.Map;

import com.taskpad.data.DataFile;
import com.taskpad.data.DataManager;
import com.taskpad.input.Input;

public class ExcecutorManager {
	private static final String FEEDBACK_ADD = "Added:";
	
	// private LinkedList<Task> listOfTasks;

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
			break;
		case "DONE":
			markAsDone(parameters.get("TASKID"));
			break;
		case "EDIT":
			edit(parameters.get("TASKID"), parameters.get("DESC"));
			break;
		case "SEARCH":
			search(parameters.get("KEYWORD"));
			break;
		case "UNDO":
			undo();
			break;
		case "LIST":
			list(parameters.get("KEY"));
			break;
		}
	}

	private void list(String option) {
		switch(option) {
		case "ALL":
			listAll();
			break;
		case "DONE":
			listDone();
			break;
		case "UNDONE":
			listUndone();
			break;
		}
		
	}

	private void listAll() {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);

		String text = "";
		for (Task task : listOfTasks) {
			
		}
	}

	private void undo() {
		// TODO Auto-generated method stub
		
	}

	private void search(String keywordsString) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);
		
		String[] keywords = keywordsString.split(" ");
		LinkedList<String[]> results = new LinkedList<String[]>();
		
		int index = 0;
		for(Task task: listOfTasks) {
			String description = task.getDescription();
			
			boolean isCandidate = true;
			for(String keyword: keywords) {
				if(!description.contains(keyword)) {
					isCandidate = false;
				}
			}
			
			if(isCandidate) {
				String taskIdString = Integer.toString(index + 1);
				String[] result = {taskIdString, task.getDescription()};
				results.add(result);
			}
				
			index++;
		}
		
		// pass feedback to GUI
		String feedback = generateTextForTasks(results);
		passFeedbackToGui(feedback);
	}

	private String generateTextForTasks(LinkedList<String[]> tasks) {
		String text = "";
		for(String[] task: tasks) {
			text += generateTextForOneTask(task[0], task[1]);
			text += "\n";
		}
		return text;
	}

	private void edit(String taskIdString, String description) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);
		
		Task task = getTaskById(listOfTasks, taskIdString);
		task.setDescription(description);
		
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		// pass feedback to gui
	}

	private void markAsDone(String taskIdString) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);
		
		Task task = getTaskById(listOfTasks, taskIdString);
		task.setDone();
	}

	private Task getTaskById(LinkedList<Task> listOfTasks, String taskIdString) {
		int taskId = Integer.parseInt(taskIdString);
		int index = taskId - 1;
		Task task = listOfTasks.get(index);
		return task;
	}

	private void clear() {
		LinkedList<Task> listOfTasks = new LinkedList<Task>();
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		// pass feedback to gui
	}

	private void addInfo(String taskId, String info) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);
		
		int index = Integer.parseInt(taskId) - 1;
		Task task = listOfTasks.get(index);
		String details = task.getDetails();
		details += info;
		task.setDetails(details);
		
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		passFeedbackToGui(getInfoOfTask(taskId));
	}

	private String getInfoOfTask(String taskId) {
		return null;
	}

	private void delete(String index) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);
		
		int indexOfTask = Integer.parseInt(index);
		Task taskDeleted = listOfTasks.get(indexOfTask);
		listOfTasks.remove(indexOfTask);
		
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		passFeedbackToGui(generateFeedbackForDelete(taskDeleted));
	}

	private String generateFeedbackForDelete(Task taskDeleted) {
		return "'" + taskDeleted.getDescription() + "' " + "deleted."; 
	}
	
	private void add(String description, String deadlineDay,
			String deadlineMonth, String deadlineYear, String startTime,
			String endTime, String venue) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);
		
		Task taskToAdd = new Task(description, deadlineDay, deadlineMonth, deadlineYear,
				startTime, endTime, venue);
		listOfTasks.add(taskToAdd);

		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		int taskId = listOfTasks.size();
		String taskIdString = Integer.toString(taskId);
		passFeedbackToGui(generateFeedbackForAdd(taskIdString, taskToAdd.getDescription()));
	}

	private String generateFeedbackForAdd(String taskIdString, String description) {
		String firstLine = FEEDBACK_ADD;
		String secondLine = generateTextForOneTask(taskIdString, description);
		return firstLine + "\n" + secondLine;
	}

	private String generateTextForOneTask(String taskIdString, String description) {
		return taskIdString +". "+ description;
	}

	private void passFeedbackToGui(String feedback) {
		// TODO Auto-generated method stub
		
	}
}
