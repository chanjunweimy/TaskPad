package com.taskpad.execute;

import java.util.LinkedList;
import java.util.Map;

import com.taskpad.data.CommandRecord;
import com.taskpad.data.DataFileStack;
import com.taskpad.data.DataManager;
import com.taskpad.data.NoPreviousCommandException;
import com.taskpad.data.NoPreviousFileException;
import com.taskpad.input.Input;
import com.taskpad.ui.GuiManager;

public class ExecutorManager {
	public static void receiveFromInput(Input input, String command) {
		String commandType = input.getCommand();
		Map<String, String> parameters = input.getParameters();
		
		switch (commandType) {
		case "ADD":
			/*
			add(parameters.get("DESC"), parameters.get("DAY"),
					parameters.get("MONTH"), parameters.get("YEAR"),
					parameters.get("START"), parameters.get("END"),
					parameters.get("VENUE"));
			*/
			add(parameters.get("DESC"), parameters.get("DEADLINE"),
					parameters.get("START DATE"), parameters.get("START TIME"),
					parameters.get("END DATE"), parameters.get("END TIME"),
					parameters.get("VENUE"));
			
			CommandRecord.pushForUndo(command);
			break;
		case "DELETE":
			delete(parameters.get("TASKID"));
			CommandRecord.pushForUndo(command);
			break;
		case "ADDINFO":
			addInfo(parameters.get("TASKID"), parameters.get("INFO"));
			CommandRecord.pushForUndo(command);
			break;
		case "CLEAR":
			clear();
			CommandRecord.pushForUndo(command);
			break;
		case "DONE":
			markAsDone(parameters.get("TASKID"));
			CommandRecord.pushForUndo(command);
			break;
		case "EDIT":
			edit(parameters.get("TASKID"), parameters.get("DESC"));
			CommandRecord.pushForUndo(command);
			break;
		case "SEARCH":
			search(parameters.get("KEYWORD"));
			break;
		case "UNDO":
			undo();
			break;
		case "REDO":
			redo();
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
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		LinkedList<Integer> tasks = new LinkedList<Integer>();
		int index = 0;
		for (Task task : listOfTasks) {
			if (task.getDone() == 1) {
				index++;
				continue;
			}
			index++;
			tasks.add(index);
		}
		
		if (tasks.size() == 0) {
			GuiManager.callOutput("No undone task found.");
		} else {
			String text = generateTextForTasks(tasks, listOfTasks);
			GuiManager.callOutput(text);
		}
		
	}

	private static void listDone() {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		LinkedList<Integer> tasks = new LinkedList<Integer>();
		int index = 0;
		for (Task task : listOfTasks) {
			if (task.getDone() == 0) {
				index++;
				continue;
			}
			tasks.add(index);
			index++;
		}
		
		if (tasks.size() == 0) {
			GuiManager.callOutput("No finished task found.");
		} else {
			String text = generateTextForTasks(tasks, listOfTasks);
			GuiManager.callOutput(text);
		}
	}

	private static void listAll() {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		LinkedList<Integer> tasks = new LinkedList<Integer>();
		int index = 0;
		for (@SuppressWarnings("unused") Task task : listOfTasks) {
			tasks.add(index);
			index++;
		}
		
		if (tasks.size() == 0) {
			GuiManager.callOutput("No task found.");
		} else {
			String text = generateTextForTasks(tasks, listOfTasks);
			// debug
			// System.out.println(text);
			GuiManager.callOutput(text);
		}
	}

	private static void undo() {
		/*
		if (!DataFileStack.isValidPrevious()) {
			GuiManager.callOutput("You don't have things to undo, or you just performed an undo operation.");
			return;
		}

		DataFileStack.setPreviousIsValid(false);
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFileStack.FILE_PREV);
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		GuiManager.callOutput("Undo of '" + CommandRecord.getPreviousCommand() + "' completed.");
		*/
		try {
			String previousFile = DataFileStack.popForUndo();
			LinkedList<Task> listOfTasks = DataManager.retrieve(previousFile);
			DataManager.storeBack(listOfTasks, DataFileStack.FILE);
			
			String command = CommandRecord.popForUndo();
			DataFileStack.pushForRedo(previousFile);
			CommandRecord.pushForRedo(command);
			
			GuiManager.callOutput("Undo of '" + command + "' completed.");
		} catch (NoPreviousFileException e) {
			GuiManager.callOutput("You don't have things to undo.");
		} catch (NoPreviousCommandException e) {
			// should never come to this
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void redo() {
		try {
			String previousFile = DataFileStack.popForRedo();
			LinkedList<Task> listOfTasks = DataManager.retrieve(previousFile);
			DataManager.storeBack(listOfTasks, DataFileStack.FILE);
			
			String command = CommandRecord.popForRedo();
			DataFileStack.pushForUndo(previousFile);
			CommandRecord.pushForUndo(command);
			
			GuiManager.callOutput("Redo of '" + command + "' completed.");
		} catch (NoPreviousFileException e) {
			GuiManager.callOutput("You don't have things to redo.");
		} catch (NoPreviousCommandException e) {
			// should never come to this
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void search(String keywordsString) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		
		String[] keywords = keywordsString.split(" ");
		LinkedList<Integer> results = new LinkedList<Integer>();
		
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
				results.add(index);
			}
				
			index++;
		}
		
		// pass feedback to GUI
		String feedback = generateTextForTasks(results, listOfTasks);
		GuiManager.callOutput(feedback);
	}

	private static String generateTextForTasks(LinkedList<Integer> candidates, LinkedList<Task> listOfTasks) {
		String text = "";
		for(int next: candidates) {
			int taskId = next + 1;
			text += generateTextForOneTask(taskId, listOfTasks.get(next));
			text += "\n";
		}
		return text;
	}

	private static void edit(String taskIdString, String description) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		String fileRecord = DataFileStack.requestDataFile();
		DataManager.storeBack(listOfTasks, fileRecord);
		DataFileStack.pushForUndo(fileRecord);
		
		Task task = getTaskById(listOfTasks, taskIdString);
		String taskHistory = generateTitleForOneTask(taskIdString, task.getDescription());
		
		task.setDescription(description);
		
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		// pass feedback to gui
		GuiManager.callOutput("'" + taskHistory + "' changed to '" 
				+ generateTitleForOneTask(taskIdString, description) + "'");
	}

	private static String generateTitleForOneTask(String taskIdString,
			String description) {
		return taskIdString + ". " + description;
	}

	private static void markAsDone(String taskIdString) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		String fileRecord = DataFileStack.requestDataFile();
		DataManager.storeBack(listOfTasks, fileRecord);
		DataFileStack.pushForUndo(fileRecord);
		
		Task task = getTaskById(listOfTasks, taskIdString);
		task.setDone();
		
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		// passFeedbackToGui to be implemented
	}

	private static Task getTaskById(LinkedList<Task> listOfTasks, String taskIdString) {
		int taskId = Integer.parseInt(taskIdString);
		int index = taskId - 1;
		Task task = listOfTasks.get(index);
		return task;
	}

	private static void clear() {
		// LinkedList<Task> listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		// DataManager.storeBack(listOfTasks, DataFileStack.FILE_PREV);
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		String fileRecord = DataFileStack.requestDataFile();
		DataManager.storeBack(listOfTasks, fileRecord);
		DataFileStack.pushForUndo(fileRecord);
		
		listOfTasks = new LinkedList<Task>();
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		// pass feedback to gui
		GuiManager.callOutput("All tasks have been deleted. You can use undo to get them back.");
	}

	private static void addInfo(String taskIdString, String info) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		String fileRecord = DataFileStack.requestDataFile();
		DataManager.storeBack(listOfTasks, fileRecord);
		DataFileStack.pushForUndo(fileRecord);
		
		int index = getIndexById(taskIdString);
		Task task = listOfTasks.get(index);
		if(task.getDetails() == null) {
			task.setDetails(info);
		} else {
			String details = task.getDetails();
			details += ("\n" + info);
			task.setDetails(details);
		}
		
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		GuiManager.callOutput(generateTextForOneTask(index + 1, task));
	}

	private static int getIndexById(String taskIdString) {
		return Integer.parseInt(taskIdString) - 1;
	}

	private static void delete(String index) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		String fileRecord = DataFileStack.requestDataFile();
		DataManager.storeBack(listOfTasks, fileRecord);
		DataFileStack.pushForUndo(fileRecord);
		
		int indexOfTask = Integer.parseInt(index);
		
		/**
		 * indexOfTask should minus 1,
		 * as the TaskID = LinkedListIndex + 1
		 */
		indexOfTask--;
		
		Task taskDeleted = listOfTasks.get(indexOfTask);
		listOfTasks.remove(indexOfTask);
		
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		GuiManager.callOutput(generateFeedbackForDelete(taskDeleted));
	}

	private static String generateFeedbackForDelete(Task taskDeleted) {
		return "'" + taskDeleted.getDescription() + "' " + "deleted."; 
	}
	
	private static void add(String description, String deadline, String startDate,
			String startTime, String endDate,
			String endTime, String venue) {
		LinkedList<Task> listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		String fileRecord = DataFileStack.requestDataFile();
		DataManager.storeBack(listOfTasks, fileRecord);
		DataFileStack.pushForUndo(fileRecord);
		
		Task taskToAdd = new Task(description, deadline, startDate,
				startTime, endDate, endTime, venue);
		listOfTasks.add(taskToAdd);

		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		int taskId = listOfTasks.size();
		GuiManager.callOutput(generateFeedbackForAdd(taskId, taskToAdd));
		// System.out.println(generateFeedbackForAdd(taskIdString, taskToAdd.getDescription()));
	}

	private static String generateFeedbackForAdd(int taskId, Task taskAdded) {
		return generateTextForOneTask(taskId, taskAdded);
	}

	private static String generateTextForOneTask(int taskId, Task task) {
		String text = "";
		
		text += "Task ID: " + taskId + "\n";
		text += "Description: " + task.getDescription() + "\n";
		
		if (task.getDeadline() != null) {
			text += "Deadline: " + task.getDeadline() + "\n";
		}
		
		String start = "";
		if (task.getStartTime() != null) {
			start += task.getStartTime();
		}
		if (task.getStartDate() != null) {
			start += (" " + task.getStartDate());
		}
		if (!start.equals("")) {
			text += "Start: " + start + "\n";
		}
		
		String end = "";
		if (task.getEndTime() != null) {
			end += task.getEndTime();
		}
		if (task.getEndDate() != null) {
			end += (" " + task.getEndDate());
		}
		if (!end.equals("")) {
			text += "end: " + end + "\n";
		}
		
		if (task.getVenue() != null) {
			text += "Venue: " + task.getVenue() + "\n";
		}
		
		if (task.getDetails() != null) {
			text += "Details: " + task.getDetails() + "\n";
		}
		
		if (task.getDone() == 0) {
			text += "Not done yet.";
		} else {
			text += "Task has been done.";
		}
		
		return text;
	}

}
