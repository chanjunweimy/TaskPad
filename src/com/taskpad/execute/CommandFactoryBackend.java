package com.taskpad.execute;

import java.util.LinkedList;

import com.taskpad.storage.CommandRecord;
import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.NoPreviousCommandException;
import com.taskpad.storage.NoPreviousFileException;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

public class CommandFactoryBackend {
	protected static Task addTask(String description, String deadline,
			String startDate, String startTime, String endDate, String endTime,
			String venue, TaskList listOfTasks) {
		Task taskToAdd = new Task(description, deadline, startDate,
				startTime, endDate, endTime, venue);
		listOfTasks.add(taskToAdd);

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

	protected static String updateDataForUndo() throws NoPreviousFileException {
		TaskList currentListOfTasks = DataManager.retrieve(DataFileStack.FILE);
		String previousFile = DataFileStack.popForUndo();
		TaskList listOfTasks = DataManager.retrieve(previousFile);
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
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
		String previousFile = DataFileStack.popForRedo();
		TaskList listOfTasks = DataManager.retrieve(previousFile);
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
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
			String[] keywords) {
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
		return results;
	}
	
	protected static String editTaskDescription(String taskIdString,
			String description, TaskList listOfTasks) {
		Task task = getTaskById(listOfTasks, taskIdString);
		String taskHistory = OutputToGui.generateTitleForOneTask(taskIdString, task.getDescription());
		
		task.setDescription(description);
		
		DataManager.storeBack(listOfTasks, DataFileStack.FILE);
		return taskHistory;
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
	
	protected static TaskList archiveForUndo() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
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
	
}
