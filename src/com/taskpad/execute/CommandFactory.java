package com.taskpad.execute;

import java.util.LinkedList;
import java.util.logging.Logger;

import com.taskpad.data.CommandRecord;
import com.taskpad.data.DataFileStack;
import com.taskpad.data.DataManager;
import com.taskpad.data.NoPreviousCommandException;
import com.taskpad.data.NoPreviousFileException;
import com.taskpad.data.Task;
import com.taskpad.data.TaskList;
import com.taskpad.ui.GuiManager;

public class CommandFactory {
	private static Logger logger = Logger.getLogger("InfoLogging");
	
	protected static void listUndone() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		LinkedList<Integer> tasks = new LinkedList<Integer>();
		for (int index = 0; index < listOfTasks.size(); index++) {
			Task task = listOfTasks.get(index);
			if (task.getDone() == 1) {
				continue;
			}
			tasks.add(index);
		}
		
		if (tasks.size() == 0) {
			GuiManager.callOutput("No undone task found.");
		} else {
			String text = generateTextForTasks(tasks, listOfTasks);
			GuiManager.callOutput(text);
		}
		
	}

	protected static void listDone() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		LinkedList<Integer> tasks = new LinkedList<Integer>();
		for (int index = 0; index < listOfTasks.size(); index++) {
			Task task = listOfTasks.get(index);
			if (task.getDone() == 0) {
				continue;
			}
			tasks.add(index);
		}
		
		if (tasks.size() == 0) {
			GuiManager.callOutput("No finished task found.");
		} else {
			String text = generateTextForTasks(tasks, listOfTasks);
			GuiManager.callOutput(text);
		}
	}

	protected static void listAll() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		LinkedList<Integer> tasks = new LinkedList<Integer>();
		for (int index = 0; index < listOfTasks.size(); index++) {
			tasks.add(index);
		}
		
		if (tasks.size() == 0) {
			GuiManager.callOutput("No task found.");
		} else {
			String text = generateTextForTasks(tasks, listOfTasks);
			GuiManager.callOutput(text);
		}
	}

	protected static void undo() {
		/*
		if (!DataFileStack.isValidPrevious()) {
			GuiManager.callOutput("You don't have things to undo, or you just performed an undo operation.");
			return;
		}

		DataFileStack.setPreviousIsValid(false);
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE_PREV);
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		GuiManager.callOutput("Undo of '" + CommandRecord.getPreviousCommand() + "' completed.");
		*/
		try {
			String previousFile = DataFileStack.popForUndo();
			TaskList listOfTasks = DataManager.retrieve(previousFile);
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
	
	protected static void redo() {
		try {
			String previousFile = DataFileStack.popForRedo();
			TaskList listOfTasks = DataManager.retrieve(previousFile);
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

	protected static void search(String keywordsString) {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		
		String[] keywords = keywordsString.split(" ");
		LinkedList<Integer> results = new LinkedList<Integer>();
		
		for(int index = 0; index < listOfTasks.size(); index++) {
			Task task = listOfTasks.get(index);
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
		}
		
		// pass feedback to GUI
		String feedback = generateTextForTasks(results, listOfTasks);
		GuiManager.callOutput(feedback);
	}

	protected static String generateTextForTasks(LinkedList<Integer> candidates, TaskList listOfTasks) {
		String text = "";
		for(int next: candidates) {
			int taskId = next + 1;
			text += generateTextForOneTask(taskId, listOfTasks.get(next));
			text += "\n";
		}
		return text;
	}

	protected static void edit(String taskIdString, String description) {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
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

	protected static String generateTitleForOneTask(String taskIdString,
			String description) {
		return taskIdString + ". " + description;
	}

	protected static void markAsDone(String taskIdString) {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		String fileRecord = DataFileStack.requestDataFile();
		DataManager.storeBack(listOfTasks, fileRecord);
		DataFileStack.pushForUndo(fileRecord);
		
		Task task = getTaskById(listOfTasks, taskIdString);
		task.setDone();
		
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		// passFeedbackToGui to be implemented
	}

	protected static Task getTaskById(TaskList listOfTasks, String taskIdString) {
		int taskId = Integer.parseInt(taskIdString);
		int index = taskId - 1;
		Task task = listOfTasks.get(index);
		return task;
	}

	protected static void clear() {
		// TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		// DataManager.storeBack(listOfTasks, DataFileStack.FILE_PREV);
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		String fileRecord = DataFileStack.requestDataFile();
		DataManager.storeBack(listOfTasks, fileRecord);
		DataFileStack.pushForUndo(fileRecord);
		
		listOfTasks = new TaskList();
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		// pass feedback to gui
		GuiManager.callOutput("All tasks have been deleted. You can use undo to get them back.");
	}

	protected static void addInfo(String taskIdString, String info) {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
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

	protected static int getIndexById(String taskIdString) {
		return Integer.parseInt(taskIdString) - 1;
	}

	protected static void delete(String index) {		
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		String fileRecord = DataFileStack.requestDataFile();
		DataManager.storeBack(listOfTasks, fileRecord);
		DataFileStack.pushForUndo(fileRecord);
		
		int indexOfTask = Integer.parseInt(index);
		
		/**
		 * indexOfTask should minus 1,
		 * as the TaskID = LinkedListIndex + 1
		 */
		indexOfTask--;
		
		assert(indexOfTask < listOfTasks.size());
		
		Task taskDeleted = listOfTasks.get(indexOfTask);
		listOfTasks.remove(indexOfTask);
		
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		GuiManager.callOutput(generateFeedbackForDelete(taskDeleted));
	}

	protected static String generateFeedbackForDelete(Task taskDeleted) {
		return "'" + taskDeleted.getDescription() + "' " + "deleted."; 
	}
	
	protected static void add(String description, String deadline, String startDate,
			String startTime, String endDate,
			String endTime, String venue) {
		logger.info("adding task: " + description);
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		String fileRecord = DataFileStack.requestDataFile();
		DataManager.storeBack(listOfTasks, fileRecord);
		DataFileStack.pushForUndo(fileRecord);
		
		Task taskToAdd = new Task(description, deadline, startDate,
				startTime, endDate, endTime, venue);
		listOfTasks.add(taskToAdd);

		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		int taskId = listOfTasks.size();
		GuiManager.callOutput(generateFeedbackForAdd(taskId, taskToAdd));
	}

	protected static String generateFeedbackForAdd(int taskId, Task taskAdded) {
		return generateTextForOneTask(taskId, taskAdded);
	}

	protected static String generateTextForOneTask(int taskId, Task task) {
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
