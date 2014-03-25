package com.taskpad.tests;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.taskpad.data.DataFileStack;
import com.taskpad.data.DataManager;
import com.taskpad.execute.ExecutorTestDriver;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

public class TestExecutor {
	@Before
	public void setUp() {
		ExecutorTestDriver.clearTasks();
	}
	
	@Test
	public void testAddFloatingTask() {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 1");
		
		ExecutorTestDriver.addTask(parameters);
		assert(DataManager.retrieveNumberOfTasks() == 1);
		
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		assert(listOfTasks.size() == 1);
		
		Task task = listOfTasks.get(0);
		assert(task.getDescription().equals("do homework 1"));
		
		/* There is usually no need to test that all other attributes are null/empty
		 * They are unlikely to be dealt with differently 
		 * Only testing deadline and venue here
		 */
		assert(task.getDeadline() == null || task.getDeadline().equals(""));
		assert(task.getVenue() == null || task.getVenue().equals(""));
	}

	@Test
	public void testAddTaskWithDeadline() {
	
	}
}
