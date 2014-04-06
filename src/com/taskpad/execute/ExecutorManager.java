package com.taskpad.execute;

//@author A0105788U

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import com.taskpad.storage.CommandRecord;
import com.taskpad.ui.GuiManager;
import com.taskpad.input.Input;

public class ExecutorManager {
	private static final String MESSAGE_SHOWING_REMINDER = "Showing tasks due today & overdue tasks...";
	
	private static Logger logger = Logger.getLogger("TaskPad");
	
	public static void showReminder() {
		OutputToGui.output(MESSAGE_SHOWING_REMINDER);
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
					Reminder.showReminderForToday();
					Reminder.showReminderForOverdue();
			  }
		}, 2*1000);
		
	}
	public static void receiveFromInput(Input input, String command) {
		String commandType = input.getCommand();
		logger.info("Executor: "+ command);
		Map<String, String> parameters = input.getParameters();
		
		switch (commandType) {
		case "ADD":
			CommandFactory.add(parameters.get("DESC"), parameters.get("DEADLINE DATE"),
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
			CommandFactory.addInfo(parameters.get("TASKID"), parameters.get("INFO"));
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
			CommandFactory.edit(parameters.get("TASKID"), parameters.get("DESC"),
					parameters.get("DEADLINE"));
			CommandRecord.pushForUndo(command);
			break;
		case "SEARCH":
			logger.info("Search keyword: " + parameters.get("KEYWORD"));
			CommandFactory.search(parameters.get("KEYWORD"), parameters.get("TIME"));
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

}
