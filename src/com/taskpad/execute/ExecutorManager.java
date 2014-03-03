package com.taskpad.execute;

import java.util.LinkedList;
import java.util.Map;

import com.taskpad.data.CommandRecord;
import com.taskpad.data.DataFile;
import com.taskpad.data.DataManager;
import com.taskpad.input.Input;

public class ExecutorManager {
	private static final String FEEDBACK_ADD = "Added:";
	
	// private LinkedList<Task> listOfTasks;

	
	public static void receiveFromInput(Input input, String command) {
		String commandType = input.getCommand();
		Map<String, String> parameters = input.getParameters();
		
		switch (commandType) {
		case "ADD":
			add(parameters.get("DESC"), parameters.get("DAY"),
					parameters.get("MONTH"), parameters.get("YEAR"),
					parameters.get("START"), parameters.get("END"),
					parameters.get("VENUE"));
			
			CommandRecord.setPreviousCommand(command);
			break;
		case "DELETE":
			delete(parameters.get("TASKID"));
			CommandRecord.setPreviousCommand(command);
			break;
		case "ADDINFO":
			addInfo(parameters.get("TASKID"), parameters.get("INFO"));
			CommandRecord.setPreviousCommand(command);
			break;
		case "CLEAR":
			clear();
			CommandRecord.setPreviousCommand(command);
			break;
		case "DONE":
			markAsDone(parameters.get("TASKID"));
			CommandRecord.setPreviousCommand(command);
			break;
		case "EDIT":
			edit(parameters.get("TASKID"), parameters.get("DESC"));
			CommandRecord.setPreviousCommand(command);
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

	private static void list(String option) {
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

	private static void listUndone() {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);

		LinkedList<String[]> tasks = new LinkedList<String[]>();
		int index = 0;
		for (Task task : listOfTasks) {
			if (task.getDone() == 1) {
				continue;
			}
			String[] taskToDisplay = new String[2];
			String taskIdString = Integer.toString(index + 1);
			String description = task.getDescription();
			taskToDisplay[0] = taskIdString;
			taskToDisplay[1] = description;
			tasks.add(taskToDisplay);
		}
		
		if (tasks.size() == 0) {
			passFeedbackToGui("No undone task found.");
		} else {
			String text = generateTextForTasks(tasks);
			passFeedbackToGui(text);
		}
		
	}

	private static void listDone() {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);

		LinkedList<String[]> tasks = new LinkedList<String[]>();
		int index = 0;
		for (Task task : listOfTasks) {
			if (task.getDone() == 0) {
				continue;
			}
			String[] taskToDisplay = new String[2];
			String taskIdString = Integer.toString(index + 1);
			String description = task.getDescription();
			taskToDisplay[0] = taskIdString;
			taskToDisplay[1] = description;
			tasks.add(taskToDisplay);
		}
		
		if (tasks.size() == 0) {
			passFeedbackToGui("No finished task found.");
		} else {
			String text = generateTextForTasks(tasks);
			passFeedbackToGui(text);
		}
	}

	private static void listAll() {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);

		LinkedList<String[]> tasks = new LinkedList<String[]>();
		int index = 0;
		for (Task task : listOfTasks) {
			String[] taskToDisplay = new String[2];
			String taskIdString = Integer.toString(index + 1);
			String description = task.getDescription();
			taskToDisplay[0] = taskIdString;
			taskToDisplay[1] = description;
			tasks.add(taskToDisplay);
		}
		
		if (tasks.size() == 0) {
			passFeedbackToGui("No finished task found.");
		} else {
			String text = generateTextForTasks(tasks);
			passFeedbackToGui(text);
		}
	}

	private static void undo() {
		if (!DataFile.isValidPrevious()) {
			passFeedbackToGui("You don't have things to undo, or you just performed an undo operation.");
			return;
		}

		DataFile.setPreviousIsValid(false);
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE_PREV);
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		passFeedbackToGui("Undo of '" + CommandRecord.getPreviousCommand() + "' completed.");
	}

	private static void search(String keywordsString) {
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

	private static String generateTextForTasks(LinkedList<String[]> tasks) {
		String text = "";
		for(String[] task: tasks) {
			text += generateTextForOneTask(task[0], task[1]);
			text += "\n";
		}
		return text;
	}

	private static void edit(String taskIdString, String description) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);
		DataManager.storeBack(listOfTasks, DataFile.FILE_PREV);
		
		Task task = getTaskById(listOfTasks, taskIdString);
		String taskHistory = generateTextForOneTask(taskIdString, task.getDescription());
		
		task.setDescription(description);
		
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		// pass feedback to gui
		passFeedbackToGui("'" + taskHistory + "' changed to '" 
				+ generateTextForOneTask(taskIdString, description) + "'");
	}

	private static void markAsDone(String taskIdString) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);
		DataManager.storeBack(listOfTasks, DataFile.FILE_PREV);
		
		Task task = getTaskById(listOfTasks, taskIdString);
		task.setDone();
		
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		// passFeedbackToGui to be implemented
	}

	private static Task getTaskById(LinkedList<Task> listOfTasks, String taskIdString) {
		int taskId = Integer.parseInt(taskIdString);
		int index = taskId - 1;
		Task task = listOfTasks.get(index);
		return task;
	}

	private static void clear() {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);
		DataManager.storeBack(listOfTasks, DataFile.FILE_PREV);
		
		listOfTasks = new LinkedList<Task>();
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		// pass feedback to gui
	}

	private static void addInfo(String taskIdString, String info) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);
		DataManager.storeBack(listOfTasks, DataFile.FILE_PREV);
		
		int index = getIndexById(taskIdString);
		Task task = listOfTasks.get(index);
		String details = task.getDetails();
		details += info;
		task.setDetails(details);
		
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		passFeedbackToGui(getInfoOfTask(index, listOfTasks));
	}

	private static String getInfoOfTask(int index, LinkedList<Task> listOfTasks) {
		Task task = listOfTasks.get(index);
		// to be implemented
		return null;
	}

	private static int getIndexById(String taskIdString) {
		return Integer.parseInt(taskIdString) - 1;
	}

	private static void delete(String index) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);
		DataManager.storeBack(listOfTasks, DataFile.FILE_PREV);
		
		int indexOfTask = Integer.parseInt(index);
		Task taskDeleted = listOfTasks.get(indexOfTask);
		listOfTasks.remove(indexOfTask);
		
		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		passFeedbackToGui(generateFeedbackForDelete(taskDeleted));
	}

	private static String generateFeedbackForDelete(Task taskDeleted) {
		return "'" + taskDeleted.getDescription() + "' " + "deleted."; 
	}
	
	private static void add(String description, String deadlineDay,
			String deadlineMonth, String deadlineYear, String startTime,
			String endTime, String venue) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFile.FILE);
		DataManager.storeBack(listOfTasks, DataFile.FILE_PREV);
		
		Task taskToAdd = new Task(description, deadlineDay, deadlineMonth, deadlineYear,
				startTime, endTime, venue);
		listOfTasks.add(taskToAdd);

		DataManager.storeBack(listOfTasks, DataFile.FILE);
		
		int taskId = listOfTasks.size();
		String taskIdString = Integer.toString(taskId);
		passFeedbackToGui(generateFeedbackForAdd(taskIdString, taskToAdd.getDescription()));
	}

	private static String generateFeedbackForAdd(String taskIdString, String description) {
		String firstLine = FEEDBACK_ADD;
		String secondLine = generateTextForOneTask(taskIdString, description);
		return firstLine + "\n" + secondLine;
	}

	private static String generateTextForOneTask(String taskIdString, String description) {
		return taskIdString +". "+ description;
	}

	private static void passFeedbackToGui(String feedback) {
		// TODO Auto-generated method stub
		
	}
}
