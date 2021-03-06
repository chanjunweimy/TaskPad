//@author A0105788U

package com.taskpad.execute;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import com.taskpad.storage.CommandRecord;
import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;
import com.taskpad.ui.GuiManager;
import com.taskpad.input.Input;

/**
 * ExecutorManager
 * 
 * This is the facade class of Executor component.
 * It accepts an Input object from InputProcessing component,
 * and call CommandFactory to execute the command.
 *
 */
public class ExecutorManager {
	private static final int WAITING_TIME_IN_MILISECONDS = 1 * 1000;

	private static final String MESSAGE_SHOWING_REMINDER = "Showing your tasks and reminders...\n";

	private static Logger logger = Logger.getLogger("TaskPad");
	
	public static void receiveFromInput(Input input, String command) {
		logger.info("Executor: " + command);

		String commandType = input.getCommand();
		Map<String, String> parameters = input.getParameters();

		switch (commandType) {
		case "ADD":
			CommandFactory.add(parameters.get("DESC"),
					parameters.get("DEADLINE DATE"),
					parameters.get("START DATE"), parameters.get("START TIME"),
					parameters.get("END DATE"), parameters.get("END TIME"),
					parameters.get("VENUE"));
			CommandRecord.pushForUndo(command);
			break;
		case "DELETE":
			CommandFactory.delete(parameters.get("TASKID"));
			CommandRecord.pushForUndo(command);
			break;
		case "ADDINFO":
			CommandFactory.addInfo(parameters.get("TASKID"),
					parameters.get("INFO"));
			CommandRecord.pushForUndo(command);
			break;
		case "CLEAR":
			CommandFactory.clear();
			CommandRecord.pushForUndo(command);
			break;
		case "DONE":
			CommandFactory.markAsDone(parameters.get("TASKID"));
			CommandRecord.pushForUndo(command);
			break;
		case "EDIT":
			CommandFactory.edit(parameters.get("TASKID"),
					parameters.get("DESC"), parameters.get("DEADLINE"),
					parameters.get("START TIME"), parameters.get("START DATE"),
					parameters.get("END TIME"), parameters.get("END DATE"),
					parameters.get("INFO"));
			CommandRecord.pushForUndo(command);
			break;
		case "SEARCH":
			logger.info("Search keyword: " + parameters.get("KEYWORD"));
			CommandFactory.search(parameters.get("KEYWORD"),
					parameters.get("TIME"));
			break;
		case "UNDO":
			CommandFactory.undo();
			break;
		case "REDO":
			CommandFactory.redo();
			break;
		case "LIST":
			list(parameters.get("KEY"));
			break;
		case "ADDREM":
			CommandFactory.addReminder(parameters.get("TASKID"),
					parameters.get("DATE"));
			CommandRecord.pushForUndo(command);
			break;
		}
	}

	
	private static void list(String option) {
		GuiManager.clearOutput();
		switch(option) {
		case "ALL":
			logger.info("Listing all tasks...");
			
			GuiManager.callOutput("Listing all tasks...");
			CommandFactory.listAll();
			break;
		case "DONE":
			logger.info("Listing finished tasks...");
			
			GuiManager.callOutput("Listing finished tasks...");
			CommandFactory.listDone();
			break;
		case "UNDONE":
			logger.info("Listing undone tasks...");
			
			GuiManager.callOutput("Listing undone tasks...");
			CommandFactory.listUndone();
			break;
		}	
	}
	
	/**
	 * showReminder
	 * 
	 * This is to schedule a TimerTask to show tasks due today, overdue tasks,
	 * and tasks attached with reminder (using addrem command)
	 * 
	 */
	public static void showReminder() {
		OutputToGui.output(MESSAGE_SHOWING_REMINDER);

		// wait for some time (1s) before showing tasks
		Timer timer = new Timer();

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Reminder.showTasksForReminder();
			}
		}, WAITING_TIME_IN_MILISECONDS);

	}
	
	/**
	 * getStartDateAndTimeForTask
	 * 
	 * This is to get the start date and time for a certain task.
	 * InputProcessing needs these information to decide whether an editing
	 * request is valid (e.g. start time must be earlier than end time etc)
	 * 
	 * @param taskId
	 * @return String of start date plus start time
	 * @throws InvalidTaskIdException
	 */
	public static String getStartDateAndTimeForTask(int taskId)
			throws InvalidTaskIdException {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);

		if (taskId > listOfTasks.size()) {
			throw new InvalidTaskIdException();
		}

		int index = taskId - 1;
		Task task = listOfTasks.get(index);
		return task.getStartDate() + " " + task.getStartTime();
	}

	public static String getEndDateAndTimeForTask(int taskId) throws InvalidTaskIdException {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		
		if (taskId > listOfTasks.size()) {
			throw new InvalidTaskIdException();
		}
		
		int index = taskId - 1;
		Task task = listOfTasks.get(index);
		return task.getEndDate() + " " + task.getEndTime();
	}

	public static String getDeadlineForTask(int taskId) throws InvalidTaskIdException {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		
		if (taskId > listOfTasks.size()) {
			throw new InvalidTaskIdException();
		}
		
		int index = taskId - 1;
		Task task = listOfTasks.get(index);
		return task.getDeadline();
	}
	
	//@author A0119646X
	/**
	 * For integration/ system tests
	 * Creates an artificial database 
	 * @param ArrayList<Input>
	 */
	public void setDebug(){
		CommandFactory.clear();
		createDatabase();
	}
	
	/**
	 * Takes in an arrayList of Input objects and arrayList of full Input 
	 * Add them to database
	 */
	private static void createDatabase(){
		ArrayList<Input> inputObjects = new ArrayList<Input>();
		ArrayList<String> fullInputList = new ArrayList<String>();
		
		/**
		 * Create your inputObjects here 
		 */
		
		addObjectsToDatabase(inputObjects, fullInputList);
	}


	private static void addObjectsToDatabase(ArrayList<Input> inputObjects, 
			ArrayList<String> fullInputList) {
		for (int i=0; i<inputObjects.size(); i++){
			receiveFromInput(inputObjects.get(i), fullInputList.get(i));
		}
	}
	
}
