package com.taskpad.execute;

import java.util.HashMap;

public class ExecutorTestDriver {
	/* 
	 * Methods in CommandFactory are not public
	 * Offer public access for testing purpose
	 */
	public static void clearTasks() {
		CommandFactoryStub.clear();
	}
	
	public static void addTask(HashMap<String, String> parameters) {
		CommandFactoryStub.add(parameters.get("DESC"), parameters.get("DEADLINE"),
				parameters.get("START DATE"), parameters.get("START TIME"),
				parameters.get("END DATE"), parameters.get("END TIME"),
				parameters.get("VENUE"));
	}
}
