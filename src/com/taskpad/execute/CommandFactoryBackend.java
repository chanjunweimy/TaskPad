package com.taskpad.execute;

//@author A0105788U

import java.util.LinkedList;

import com.taskpad.storage.CommandRecord;
import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.NoPreviousCommandException;
import com.taskpad.storage.NoPreviousFileException;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

public class CommandFactoryBackend {
	/**
	 * ACCENDING_ORDER: a comparator used to sort Task by their dates.
	 * 
	 */
	/*
	protected static final Comparator<Task> ACCENDING_ORDER = new Comparator<Task>() {
		/**
		 * compare: compare two tasks' Date
		 * 
		 * @param e1
		 *            : task1
		 * @param e2
		 *            : task2
		 * @return int
		 */
	/*	@Override
		public int compare(Task e1, Task e2) {
			SimpleDateFormat dateConverter = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm");
			Date d1, d2;
			try {
				d1 = dateConverter.parse(e1.getDeadline() + e1.getEndTime());
				d2 = dateConverter.parse(e2.getDeadline() + e2.getEndTime());
			} catch (ParseException e) {
				System.err.println(e.getMessage());
				return 0;
			}
			return d1.compareTo(d2);
		}
	};
	*/
	
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
		TaskList previousListOfTasks = DataManager.retrieve(previousFile);
		
		DataManager.storeBack(previousListOfTasks, DataFileStack.FILE);
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
		TaskList previousListOfTasks = DataManager.retrieve(previousFile);
		
		DataManager.storeBack(previousListOfTasks, DataFileStack.FILE);
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
			isCandidate = containsTimes(times, task);
			
			if(isCandidate) {
				results.add(index);
			}
		}
		return results;
	}

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

	private static boolean containsTimes(String[] timesOrDates, Task task) {
		String deadline = task.getDeadline();
		String startDate = task.getStartDate();
		String startTime = task.getStartTime();
		String endDate = task.getEndDate();
		String endTime = task.getEndTime();
		
		for(String timeOrDate: timesOrDates) {
			// check deadline
			if(deadline != null && deadline.contains(timeOrDate)) {
				return true;
			}
			
			// check start/end time
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
			
		}
		
		return false;
	}
	
	protected static Task editTask(String taskIdString,
			String description, String deadline, TaskList listOfTasks) {
		Task task = getTaskById(listOfTasks, taskIdString);
		// String taskHistory = OutputToGui.generateTitleForOneTask(taskIdString, task.getDescription());
		
		if(description != null && !description.equals("")) {
			task.setDescription(description);
		}
		if(deadline != null && !deadline.equals("")) {
			task.setDeadline(deadline);
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

	/*
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
