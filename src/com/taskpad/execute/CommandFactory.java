package com.taskpad.execute;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;

import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.NoPreviousCommandException;
import com.taskpad.storage.NoPreviousFileException;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

public class CommandFactory {
	private static final String FEEDBACK_NO_TASK_WITH_DEADLINE = "No task with deadline in the database.";
	private static final String FEEDBACK_NO_TASK = "No task found.";
	private static final String FEEDBACK_NO_FINISHED_TASK = "No finished task found.";
	private static final String LOGGING_ADDING_TASK = "adding task: %s";
	private static final String FEEDBACK_NO_UNDONE_TASK = "No undone task found.";
	private static final String FEEDBACK_CLEAR = "All tasks have been deleted. You can use undo to get them back.";
	private static final String FEEDBACK_CANNOT_UNDO = "You don't have things to undo.";
	private static final String STRING_NEWLINE = "\n";
	private static Logger logger = Logger.getLogger("TaskPad");
	
	protected static void add(String description, String deadline, String startDate,
			String startTime, String endDate,
			String endTime, String venue) {
		logger.info(String.format(LOGGING_ADDING_TASK, description));
		
		TaskList listOfTasks = CommandFactoryBackend.archiveForUndo();
		
		Task taskAdded = CommandFactoryBackend.addTask(description, deadline, startDate, startTime,
				endDate, endTime, venue, listOfTasks);
		
		int taskId = listOfTasks.size();
		OutputToGui.output(STRING_NEWLINE);
		OutputToGui.generateFeedbackForAdd(taskId, taskAdded);
	}
	
	protected static void listUndone() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		
		LinkedList<Integer> tasks = CommandFactoryBackend.getUndoneTasks(listOfTasks);
		
		if (tasks.size() == 0) {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output(FEEDBACK_NO_UNDONE_TASK);
		} else {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.outputColorTextForTasks(tasks, listOfTasks);
		}
		
	}

	protected static void listDone() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		LinkedList<Integer> tasks = CommandFactoryBackend.getFinishedTasks(listOfTasks);
		
		if (tasks.size() == 0) {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output(FEEDBACK_NO_FINISHED_TASK);
		} else {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.outputColorTextForTasks(tasks, listOfTasks);
		}
	}

	protected static void listAll() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		LinkedList<Integer> tasks = CommandFactoryBackend.getAllTasks(listOfTasks);
		
		if (tasks.size() == 0) {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output(FEEDBACK_NO_TASK);
		} else {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.outputColorTextForTasks(tasks, listOfTasks);
		}
	}

	protected static void listByDeadline() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		
		LinkedList<Integer> tasks = CommandFactoryBackend.sortByDeadline(listOfTasks);
		
		if (tasks.size() == 0) {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output(FEEDBACK_NO_TASK_WITH_DEADLINE);
		} else {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.outputColorTextForTasks(tasks, listOfTasks);
		}		
	}
	
	protected static void undo() {
		try {
			String previousFile = CommandFactoryBackend.updateDataForUndo();		
			String command = CommandFactoryBackend.updateCommandRecordForUndo(previousFile);
			
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output("Undo of '" + command + "' completed.");
		} catch (NoPreviousFileException e) {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output(FEEDBACK_CANNOT_UNDO);
		} catch (NoPreviousCommandException e) {
			// should never come to this
		}
	}
	
	protected static void redo() {
		try {
			String previousFile = CommandFactoryBackend.updateDataForRedo();
			String command = CommandFactoryBackend.updateCommandRecordForRedo(previousFile);
			
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output("Redo of '" + command + "' completed.");
		} catch (NoPreviousFileException e) {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output("You don't have things to redo.");
		} catch (NoPreviousCommandException e) {
			// should never come to this
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected static void search(String keywordsString) {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		
		String[] keywords = keywordsString.split(" ");
		LinkedList<Integer> results = CommandFactoryBackend.getSearchResult(listOfTasks, keywords);
		
		// pass feedback to GUI
		OutputToGui.output(STRING_NEWLINE);
		OutputToGui.output("Number of tasks found: " + results.size() + "\n\n");
		OutputToGui.outputColorTextForTasks(results, listOfTasks);
	}

	protected static void edit(String taskIdString, String description, String deadline) {
		TaskList listOfTasks = CommandFactoryBackend.archiveForUndo();
		
		Task task = CommandFactoryBackend.editTask(taskIdString, description, deadline,
				listOfTasks);
		
		// pass feedback to gui
		OutputToGui.output(STRING_NEWLINE);
		/*
		if(description != null && !description.equals("")) {
			OutputToGui.output("'" + taskHistory + "' changed to '" 
				+ OutputToGui.generateTitleForOneTask(taskIdString, description) + "'");
		}
		*/
		int taskid = Integer.parseInt(taskIdString);
		OutputToGui.outputColorTextForOneTask(taskid, task);
	}

	protected static void markAsDone(String taskIdString) {
		TaskList listOfTasks = CommandFactoryBackend.archiveForUndo();

		Task task = CommandFactoryBackend.markTaskAsDone(taskIdString, listOfTasks);

		OutputToGui.output(STRING_NEWLINE);
		OutputToGui.outputColorTextForOneTask(
				Integer.parseInt(taskIdString), task);
	}

	protected static void clear() {
		CommandFactoryBackend.archiveForUndo();	
		CommandFactoryBackend.clearTasks();
		
		// pass feedback to gui
		OutputToGui.output(STRING_NEWLINE);
		OutputToGui.output(FEEDBACK_CLEAR);
	}

	protected static void addInfo(String taskIdString, String info) {
		TaskList listOfTasks = CommandFactoryBackend.archiveForUndo();
		
		int index = getIndexById(taskIdString);
		Task task = CommandFactoryBackend.addInfoToTask(info, listOfTasks, index);
		
		OutputToGui.output(STRING_NEWLINE);
		OutputToGui.outputColorTextForOneTask(index + 1, task);
	}

	protected static int getIndexById(String taskIdString) {
		return Integer.parseInt(taskIdString) - 1;
	}
	
	protected static void delete(String taskIdString) {		
		TaskList listOfTasks = CommandFactoryBackend.archiveForUndo();
		
		int indexOfTask = Integer.parseInt(taskIdString) - 1;	
		assert(indexOfTask < listOfTasks.size());
		
		Task taskDeleted = CommandFactoryBackend.deleteTask(listOfTasks, indexOfTask);
		
		OutputToGui.output(STRING_NEWLINE);

		OutputToGui.output(OutputToGui.generateFeedbackForDelete(taskDeleted));
	}


}
