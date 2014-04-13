//@author A0105788U

package com.taskpad.execute;

import java.util.LinkedList;
import java.util.logging.Logger;

import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.NoPreviousCommandException;
import com.taskpad.storage.NoPreviousFileException;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

/**
 * CommandFactory
 * 
 * This class is to execute commands of different command type,
 * and output feedback to GUI.
 * 
 */
public class CommandFactory {
	private static final String FEEDBACK_NO_TASK_FOUND = "No task found.";
	private static final String FEEDBACK_NO_UNDONE_TASK = "No undone task found.";
	private static final String FEEDBACK_NO_FINISHED_TASK = "No finished task found.";
	private static final String FEEDBACK_CLEAR = "All tasks have been deleted. You can use undo to get them back.";
	private static final String FEEDBACK_CANNOT_UNDO = "You don't have things to undo.";
	
	private static final String LOGGING_ADDING_TASK = "adding task: %s";
	private static final String LOGGING_UNDO_FAILED = "Undo failed";
	private static final String LOGGING_REDO_FAILED = "Redo failed";
	
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
			// Notes: can output in color text or in table view
			// OutputToGui.output(STRING_NEWLINE);
			// OutputToGui.outputColorTextForTasks(tasks, listOfTasks);
			OutputToGui.outputTable(tasks, listOfTasks);
		}
		
	}

	protected static void listDone() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		LinkedList<Integer> tasks = CommandFactoryBackend.getFinishedTasks(listOfTasks);
		
		if (tasks.size() == 0) {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output(FEEDBACK_NO_FINISHED_TASK);
		} else {
			// Notes: can output in color text or in table view
			// OutputToGui.output(STRING_NEWLINE);
			// OutputToGui.outputColorTextForTasks(tasks, listOfTasks);
			OutputToGui.outputTable(tasks, listOfTasks);
		}
	}

	protected static void listAll() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		LinkedList<Integer> tasks = CommandFactoryBackend.getAllTasks(listOfTasks);
		
		if (tasks.size() == 0) {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output(FEEDBACK_NO_TASK_FOUND);
		} else {
			// Notes: can output in color text or in table view
			// OutputToGui.output(STRING_NEWLINE);
			// OutputToGui.outputColorTextForTasks(tasks, listOfTasks);
			OutputToGui.outputTable(tasks, listOfTasks);
		}
	}

	/*	not used by v0.5
	protected static void listByDeadline() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		
		LinkedList<Integer> tasks = CommandFactoryBackend.sortByDeadline(listOfTasks);
		
		if (tasks.size() == 0) {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output(FEEDBACK_NO_TASK_WITH_DEADLINE);
		} else {
			// OutputToGui.output(STRING_NEWLINE);
			// OutputToGui.outputColorTextForTasks(tasks, listOfTasks);
			OutputToGui.outputTable(tasks, listOfTasks);
		}		
	}
	*/
	
	protected static void undo() {
		try {
			String previousFile = CommandFactoryBackend.updateDataForUndo();		
			String command = CommandFactoryBackend.updateCommandRecordForUndo(previousFile);
			
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output("Undo of '" + command + "' completed.");
			
		} catch (NoPreviousFileException e) {
			logger.info(LOGGING_UNDO_FAILED);
			
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output(FEEDBACK_CANNOT_UNDO);
			
		} catch (NoPreviousCommandException e) {
			// should never come to this
			// will catch NoPreviesFileException first
		}
	}
	
	protected static void redo() {
		try {
			String previousFile = CommandFactoryBackend.updateDataForRedo();
			String command = CommandFactoryBackend.updateCommandRecordForRedo(previousFile);
			
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output("Redo of '" + command + "' completed.");
			
		} catch (NoPreviousFileException e) {
			logger.info(LOGGING_REDO_FAILED);
			
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output("You don't have things to redo.");
			
		} catch (NoPreviousCommandException e) {
			// should never come to this
		}
	}

	/**
	 * search
	 * 
	 * Search can support search by keywords and search by time
	 * 
	 * @param keywordsString
	 * @param timeString
	 */
	protected static void search(String keywordsString, String timeString) {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		String[] keywords = keywordsString.split(" ");
		String[] times = timeString.split("&"); // purposely defined spliter

		LinkedList<Integer> results = CommandFactoryBackend.getSearchResult(
				listOfTasks, keywords, times);

		// pass feedback to GUI in color text
		// OutputToGui.output(STRING_NEWLINE);
		// OutputToGui.output("Number of tasks found: " + results.size() +
		// "\n\n");
		// OutputToGui.outputColorTextForTasks(results, listOfTasks);

		if (results.size() == 0) {
			OutputToGui.output(STRING_NEWLINE);
			OutputToGui.output(FEEDBACK_NO_TASK_FOUND);
		} else {
			OutputToGui.outputTable(results, listOfTasks);
		}

	}

	protected static void edit(String taskIdString, String description,
			String deadline, String startTime, String startDate,
			String endTime, String endDate, String info) {

		TaskList listOfTasks = CommandFactoryBackend.archiveForUndo();

		Task task = CommandFactoryBackend.editTask(taskIdString, description,
				deadline, startTime, startDate, endTime, endDate, info,
				listOfTasks);

		// pass feedback to gui
		OutputToGui.output(STRING_NEWLINE + "TASK " + taskIdString
				+ " EDITED: " + STRING_NEWLINE);
		int taskid = Integer.parseInt(taskIdString);
		OutputToGui.outputColorTextForOneTask(taskid, task);
	}

	protected static void markAsDone(String taskIdString) {
		TaskList listOfTasks = CommandFactoryBackend.archiveForUndo();

		Task task = CommandFactoryBackend.markTaskAsDone(taskIdString,
				listOfTasks);

		OutputToGui.output(STRING_NEWLINE);
		OutputToGui.outputColorTextForOneTask(Integer.parseInt(taskIdString),
				task);
	}

	/**
	 * clear
	 * 
	 * This is to delete all tasks.
	 * 
	 */
	protected static void clear() {
		CommandFactoryBackend.archiveForUndo();
		CommandFactoryBackend.clearTasks();

		// pass feedback to gui
		OutputToGui.output(STRING_NEWLINE);
		OutputToGui.output(FEEDBACK_CLEAR);
	}

	/**
	 * addInfo
	 * 
	 * This is to add some additional information into 'details' column of a
	 * task
	 * 
	 * @param taskIdString
	 * @param info
	 */
	protected static void addInfo(String taskIdString, String info) {
		TaskList listOfTasks = CommandFactoryBackend.archiveForUndo();

		int index = getIndexById(taskIdString);
		assert(index < listOfTasks.size());
		
		Task task = CommandFactoryBackend.addInfoToTask(info, listOfTasks,
				index);

		OutputToGui.output(STRING_NEWLINE);
		OutputToGui.outputColorTextForOneTask(index + 1, task);
	}
	
	protected static void delete(String taskIdString) {
		TaskList listOfTasks = CommandFactoryBackend.archiveForUndo();

		int indexOfTask = getIndexById(taskIdString);
		assert (indexOfTask < listOfTasks.size());

		Task taskDeleted = CommandFactoryBackend.deleteTask(listOfTasks,
				indexOfTask);

		OutputToGui.output(STRING_NEWLINE);

		OutputToGui.output(OutputToGui.generateFeedbackForDelete(taskDeleted));
	}

	/**
	 * addReminder
	 * 
	 * This method is to attach a reminder to some task. Tasks attached with
	 * reminder will be shown for showrem command.
	 * 
	 * @param taskIdString
	 * @param date
	 */
	protected static void addReminder(String taskIdString, String date) {
		TaskList listOfTasks = CommandFactoryBackend.archiveForUndo();

		int indexOfTask = getIndexById(taskIdString);
		assert (indexOfTask < listOfTasks.size());

		CommandFactoryBackend.setReminder(indexOfTask, date, listOfTasks);
	}
	
	protected static int getIndexById(String taskIdString) {
		return Integer.parseInt(taskIdString) - 1;
	}

}
