//@author A0105788U

package com.taskpad.execute;

import java.util.HashMap;
import java.util.LinkedList;

import com.taskpad.storage.NoPreviousFileException;
import com.taskpad.storage.TaskList;

public class ExecutorTestDriver {
	/* 
	 * Methods in CommandFactoryBackend are not public
	 * Offer public access for testing purpose
	 */
	
	public static void addTask(HashMap<String, String> parameters, TaskList list) {
		CommandFactoryBackend.addTask(parameters.get("DESC"), parameters.get("DEADLINE"),
				parameters.get("START DATE"), parameters.get("START TIME"),
				parameters.get("END DATE"), parameters.get("END TIME"),
				parameters.get("VENUE"), list);
	}

	public static LinkedList<Integer> getAllTasksFromBackend(TaskList list) {
		return CommandFactoryBackend.getAllTasks(list);
	}
	
	public static LinkedList<Integer> getUndoneTasksFromBackend(TaskList list) {
		return CommandFactoryBackend.getUndoneTasks(list);
	}

	public static LinkedList<Integer> getFinishedTasksFromBackend(TaskList list) {
		return CommandFactoryBackend.getFinishedTasks(list);
	}

	public static void deleteTask(TaskList list, int index) {
		CommandFactoryBackend.deleteTask(list, index);
		
	}

	public static void clearTasks() {
		CommandFactoryBackend.clearTasks();		
	}

	public static void archiveForUndo() {
		CommandFactoryBackend.archiveForUndo();		
	}

	public static void updateDataForUndo() throws NoPreviousFileException {
		CommandFactoryBackend.updateDataForUndo();
	}

	public static void addInfo(String info, TaskList list, int index) {
		CommandFactoryBackend.addInfoToTask(info, list, index);
	}

	public static LinkedList<Integer> search(TaskList list, String[] keywords, String[] times) {
		return CommandFactoryBackend.getSearchResult(list, keywords, times);
	}
	
}
