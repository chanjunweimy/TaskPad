package com.taskpad.execute;

import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

import com.taskpad.data.CommandRecord;
import com.taskpad.data.DataFileStack;
import com.taskpad.data.DataManager;
import com.taskpad.data.NoPreviousCommandException;
import com.taskpad.data.NoPreviousFileException;
import com.taskpad.data.Task;
import com.taskpad.input.Input;
import com.taskpad.ui.GuiManager;

public class ExecutorManager {
	private static Logger logger = Logger.getLogger("InfoLogging");
	
	public static void receiveFromInput(Input input, String command) {
		String commandType = input.getCommand();
		logger.info("Executor: "+command);
		Map<String, String> parameters = input.getParameters();
		
		switch (commandType) {
		case "ADD":
			/*
			add(parameters.get("DESC"), parameters.get("DAY"),
					parameters.get("MONTH"), parameters.get("YEAR"),
					parameters.get("START"), parameters.get("END"),
					parameters.get("VENUE"));
			*/
			CommandFactory.add(parameters.get("DESC"), parameters.get("DEADLINE"),
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
			CommandFactory.edit(parameters.get("TASKID"), parameters.get("DESC"));
			CommandRecord.pushForUndo(command);
			break;
		case "SEARCH":
			CommandFactory.search(parameters.get("KEYWORD"));
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
		switch(option) {
		case "ALL":
			logger.info("Listing all tasks...");
			CommandFactory.listAll();
			break;
		case "DONE":
			logger.info("Listing finished tasks...");
			CommandFactory.listDone();
			break;
		case "UNDONE":
			logger.info("Listing undone tasks...");
			CommandFactory.listUndone();
			break;
		}	
	}

}
