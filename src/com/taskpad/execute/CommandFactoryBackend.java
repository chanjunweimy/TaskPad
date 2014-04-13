//@author A0105788U

package com.taskpad.execute;

import java.util.LinkedList;
import java.util.logging.Logger;

import com.taskpad.storage.CommandRecord;
import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.NoPreviousCommandException;
import com.taskpad.storage.NoPreviousFileException;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

/**
 * CommandFactoryBackend
 * 
 * This class is to access the database to support command execution.
 * 
 */
public class CommandFactoryBackend {
	private static final String LOGGING_STORE_BACK_TO_DB = "Storing task '%s' back to data file...";
	
	private static Logger logger = Logger.getLogger("TaskPad");
	
	protected static Task addTask(String description, String deadline,
			String startDate, String startTime, String endDate, String endTime,
			String venue, TaskList listOfTasks) {
		assert(description != null);
		
		Task taskToAdd = new Task(description, deadline, startDate,
				startTime, endDate, endTime, venue);
		listOfTasks.add(taskToAdd);

		logger.info(String.format(LOGGING_STORE_BACK_TO_DB, description));
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		
		return taskToAdd;
	}
	
	protected static LinkedList<Integer> getUndoneTasks(TaskList listOfTasks) {
		LinkedList<Integer> tasks = new LinkedList<Integer>();
		for (int index = 0; index < listOfTasks.size(); index++) {
			Task task = listOfTasks.get(index);
			if (task.getDone() == 1) {
				continue;
			}
			tasks.add(index);
		}
		return tasks;
	}
	
	protected static LinkedList<Integer> getFinishedTasks(TaskList listOfTasks) {
		LinkedList<Integer> tasks = new LinkedList<Integer>();
		for (int index = 0; index < listOfTasks.size(); index++) {
			Task task = listOfTasks.get(index);
			if (task.getDone() == 0) {
				continue;
			}
			tasks.add(index);
		}
		return tasks;
	}

	protected static LinkedList<Integer> getAllTasks(TaskList listOfTasks) {
		LinkedList<Integer> tasks = new LinkedList<Integer>();
		for (int index = 0; index < listOfTasks.size(); index++) {
			tasks.add(index);
		}
		return tasks;
	}

	/**
	 * updateDataForUndo
	 * 
	 * This is to update data files for undo operation
	 * 
	 * @return the name of previous stored data file
	 * @throws NoPreviousFileException
	 */
	protected static String updateDataForUndo() throws NoPreviousFileException {
		TaskList currentListOfTasks = DataManager.retrieve(DataFileStack.FILE);
		
		// recover previously stored data file in undo stack
		String previousFile = DataFileStack.popForUndo();
		TaskList previousListOfTasks = DataManager.retrieve(previousFile);
		
		DataManager.storeBack(previousListOfTasks, DataFileStack.FILE);
		
		// later can redo back
		DataManager.storeBack(currentListOfTasks, previousFile);
		DataFileStack.pushForRedo(previousFile);
		
		return previousFile;
	}
	
	protected static String updateCommandRecordForUndo(String previousFile)
			throws NoPreviousCommandException {
		
		String command = CommandRecord.popForUndo();
		CommandRecord.pushForRedo(command);
		
		return command;
	}
	
	protected static String updateDataForRedo() throws NoPreviousFileException {
		TaskList currentListOfTasks = DataManager.retrieve(DataFileStack.FILE);
		
		// recover previously stored data file in redo stack
		String previousFile = DataFileStack.popForRedo();
		TaskList previousListOfTasks = DataManager.retrieve(previousFile);
		
		DataManager.storeBack(previousListOfTasks, DataFileStack.FILE);
		
		// later can undo again
		DataManager.storeBack(currentListOfTasks, previousFile);
		DataFileStack.pushForUndo(previousFile);
		
		return previousFile;
	}
	
	protected static String updateCommandRecordForRedo(String previousFile)
			throws NoPreviousCommandException {
		String command = CommandRecord.popForRedo();
		CommandRecord.pushForUndo(command);
		
		return command;
	}

	protected static LinkedList<Integer> getSearchResult(TaskList listOfTasks,
			String[] keywords, String[] times) {
		LinkedList<Integer> results = new LinkedList<Integer>();
		
		for(int index = 0; index < listOfTasks.size(); index++) {
			Task task = listOfTasks.get(index);
			
			String description = task.getDescription();
			String details = task.getDetails();	
			if(details == null) {
				details = "";
			}
			
			boolean isCandidate = false;
			
			isCandidate = containsKeywords(keywords, description, details);
			isCandidate = isCandidate || containsTimes(times, task);
			
			if(isCandidate) {
				results.add(index);
			}
		}
		return results;
	}

	/**
	 * containsKeywords
	 * 
	 * This is to check whether task contains ALL the keywords.
	 * 
	 * @param keywords
	 * @param description
	 * @param details
	 * @return a boolean value
	 */
	private static boolean containsKeywords(String[] keywords,
			String description, String details) {
		boolean isCandidate = true;
		for(String keyword: keywords) {
			if(!description.contains(keyword) && !details.contains(keyword)) {
				isCandidate = false;
			}
		}
		return isCandidate;
	}

	/**
	 * containsTimes
	 * 
	 * This is to check whether task contains ANY ONE OF the searched time
	 * 
	 * @param timesOrDates
	 * @param task
	 * @return
	 */
	private static boolean containsTimes(String[] timesOrDates, Task task) {
		String deadline = task.getDeadline();
		String startDate = task.getStartDate();
		String startTime = task.getStartTime();
		String endDate = task.getEndDate();
		String endTime = task.getEndTime();
		
		for(String timeOrDate: timesOrDates) {
			if(isValidTimeOrDates(timeOrDate)) {
				continue;
			}

			if(deadlineContainsTimeOrDate(deadline, timeOrDate)) {
				return true;
			}
			
			if(StartOrEndContainsTimeOrDate(startDate, startTime, endDate,
					endTime, timeOrDate)) {
				return true;
			}		
		}
		
		return false;
	}

	private static boolean isValidTimeOrDates(String timeOrDate) {
		return timeOrDate.trim().equals("");
	}
	
	private static boolean deadlineContainsTimeOrDate(String deadline,
			String timeOrDate) {
		return deadline != null && deadline.contains(timeOrDate);
	}
	
	private static boolean StartOrEndContainsTimeOrDate(String startDate,
			String startTime, String endDate, String endTime, String timeOrDate) {
		
		String time = "";
		String date = "";
		if(timeOrDate.contains(" ")) {
			// time + date
			String[] timeAndDate = timeOrDate.split(" ");
			time = timeAndDate[0];
			date = timeAndDate[1];
			
			if(startDate != null && startDate.equals(date) 
					&& startTime != null && startTime.equals(time)) {
				return true;
			}
			if(endDate != null && endDate.equals(date) 
					&& endTime != null && endTime.equals(time)) {
				return true;
			}
			
		} else {
			// only date
			date = timeOrDate;
			if(startDate != null && startDate.equals(date)) {
				return true;
			}
			if(endDate != null && endDate.equals(date)) {
				return true;
			}
		}
		
		return false;
	}
	
	protected static Task editTask(String taskIdString,
			String description, String deadline,
			String startTime, String startDate, String endTime,
			String endDate, TaskList listOfTasks) {
		
		Task task = getTaskById(listOfTasks, taskIdString);
		
		if (description != null && !description.equals("")) {
			logger.info(String.format("Edit task description: %s ...",
					description));
			task.setDescription(description);
		}
		if (deadline != null) {
			logger.info(String.format("Edit task deadline: %s ...", 
					deadline));
			task.setDeadline(deadline);
		}
		if (startTime != null) {
			logger.info(String.format("Edit task start time: %s ...", 
					startTime));
			task.setStartTime(startTime);
		}
		if (startDate != null) {
			logger.info(String.format("Edit task start date: %s ...", 
					startDate));
			task.setStartDate(startDate);
		}
		if (endTime != null) {
			logger.info(String.format("Edit task end time: %s ...", 
					endTime));
			task.setEndTime(endTime);
		}
		if (endDate != null) {
			logger.info(String.format("Edit task end date: %s ...", 
					endDate));
			task.setEndDate(endDate);
		}
		
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		return task;
	}
	
	protected static Task markTaskAsDone(String taskIdString, TaskList listOfTasks) {
		Task task = getTaskById(listOfTasks, taskIdString);
		task.setDone();
		
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		return task;
	}
	
	protected static Task getTaskById(TaskList listOfTasks, String taskIdString) {
		int taskId = Integer.parseInt(taskIdString);
		int index = taskId - 1;
		Task task = listOfTasks.get(index);
		return task;
	}
	
	protected static void clearTasks() {
		TaskList listOfTasks;
		listOfTasks = new TaskList();
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
	}
	
	/**
	 * archiveForUndo
	 * 
	 * This is to archive the current database before making changes.
	 * 
	 * @return
	 */
	protected static TaskList archiveForUndo() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		// request a file for archive purpose
		String fileRecord = DataFileStack.requestDataFile();
		DataManager.storeBack(listOfTasks, fileRecord);

		DataFileStack.pushForUndo(fileRecord);

		return listOfTasks;
	}
	
	protected static Task addInfoToTask(String info, TaskList listOfTasks,
			int index) {
		Task task = listOfTasks.get(index);
		
		if(task.getDetails() == null) {
			task.setDetails(info);
		} else {
			// append to details
			String details = task.getDetails();
			details += ("\n" + info);
			task.setDetails(details);
		}
		
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		return task;
	}
	
	protected static Task deleteTask(TaskList listOfTasks, int indexOfTask) {
		Task taskDeleted = listOfTasks.get(indexOfTask);
		listOfTasks.remove(indexOfTask);
		
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		return taskDeleted;
	}

	public static void setReminder(int indexOfTask, String date, TaskList listOfTasks) {
		Task task = listOfTasks.get(indexOfTask);
		task.setReminderDate(date);
		
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
	}

	/* not used by v0.5
	public static LinkedList<Integer> sortByDeadline(TaskList listOfTasks) {
		LinkedList<Task> tasks = listOfTasks.getList();
		
		HashMap<Task, Integer> dictionaryForTaskIndex = new HashMap<Task, Integer>();
		for(int i = 0; i < tasks.size(); i++) {
			dictionaryForTaskIndex.put(tasks.get(i), i);
		}
		
		Collections.sort(tasks, ACCENDING_ORDER);
		
		LinkedList<Integer> result = new LinkedList<Integer>();
		for(int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			int index = dictionaryForTaskIndex.get(task);
			result.add(index);
		}
		
		return result;
	}
	*/
}
