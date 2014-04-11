//@author A0105788U

package com.taskpad.tests;



import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import com.taskpad.execute.ExecutorTestDriver;
import com.taskpad.input.InputManager;
import com.taskpad.storage.CommandRecord;
import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.NoPreviousFileException;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

public class TestExecutorStorage {

	@Test
	public void testAddFloatingTask() {
		TaskList list = new TaskList();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 1");
		
		ExecutorTestDriver.addTask(parameters, list);
		assertEquals(DataManager.retrieveNumberOfTasks(), 1);
		
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		assertEquals(listOfTasks.size(), 1);
		
		Task task = listOfTasks.get(0);
		assertEquals(task.getDescription(), "do homework 1");
		
		/* 
		 * There is usually no need to test that all other attributes are null/empty
		 * They are unlikely to be dealt with differently 
		 * Only testing deadline and venue here
		 */
		assertTrue(task.getDeadline() == null || task.getDeadline().equals(""));
		assertTrue(task.getVenue() == null || task.getVenue().equals(""));
	}

	@Test
	public void testAddTaskWithDeadline() {
		TaskList list = new TaskList();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 1");
		parameters.put("DEADLINE", "02/03/2014");
		
		ExecutorTestDriver.addTask(parameters, list);
		assertEquals(DataManager.retrieveNumberOfTasks(), 1);
		
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		assertEquals(listOfTasks.size(), 1);
		
		Task task = listOfTasks.get(0);
		assertEquals(task.getDescription(), "do homework 1");
		assertEquals(task.getDeadline(), "02/03/2014");
		
		/* 
		 * There is usually no need to test that all other attributes are null/empty
		 * They are unlikely to be dealt with differently 
		 * Only testing deadline and venue here
		 */
		assertTrue(task.getStartTime() == null || task.getStartTime().equals(""));
		assertTrue(task.getVenue() == null || task.getVenue().equals(""));		
	}
	
	@Test
	public void testAddTimedTasks() {
		TaskList list = new TaskList();
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "CCA meeting");
		parameters.put("START TIME", "16:00");
		parameters.put("START DATE", "02/07/2018");
		parameters.put("END TIME", "18:00");
		parameters.put("END DATE", "02/07/2018");
		
		ExecutorTestDriver.addTask(parameters, list);
		
		assertEquals(DataManager.retrieveNumberOfTasks(), 1);
		
		Task task = list.get(0);
		assertEquals(task.getStartTime(), "16:00");
		assertEquals(task.getStartDate(), "02/07/2018");
		assertEquals(task.getEndTime(), "18:00");
		assertEquals(task.getEndDate(), "02/07/2018");
	}
	
	@Test
	public void testAddLargeNumberOfTasks() {
		TaskList list = new TaskList();
		
		// floating tasks
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 1");
		
		for(int i = 0; i < 100; i++) {
			ExecutorTestDriver.addTask(parameters, list);
		}
		
		// tasks with deadline
		parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 1");
		parameters.put("DEADLINE", "02/03/2014");
		
		for(int i = 0; i < 100; i++) {
			ExecutorTestDriver.addTask(parameters, list);
		}
		
		// tasks with start and end time (timed tasks)
		parameters = new HashMap<String, String>();
		parameters.put("DESC", "CCA meeting");
		parameters.put("START TIME", "16:00");
		parameters.put("START DATE", "02/07/2018");
		parameters.put("END TIME", "18:00");
		parameters.put("END DATE", "02/07/2018");
		
		for(int i = 0; i < 100; i++) {
			ExecutorTestDriver.addTask(parameters, list);
		}
		
		assertEquals(DataManager.retrieveNumberOfTasks(), 300);
	}
	
	@Test
	public void testList() {
		/*
		 * only test 0, 1, 2 here
		 */
		TaskList list = new TaskList();
		LinkedList<Integer> result = ExecutorTestDriver.getAllTasksFromBackend(list);
		assertEquals(result.size(), 0);
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 1");
		
		ExecutorTestDriver.addTask(parameters, list);
		result = ExecutorTestDriver.getAllTasksFromBackend(list);
		assertEquals(result.size(), 1);
		
		ExecutorTestDriver.addTask(parameters, list);
		result = ExecutorTestDriver.getAllTasksFromBackend(list);
		assertEquals(result.size(), 2);

		Task task = list.get(0);
		task.setDone();
		result = ExecutorTestDriver.getUndoneTasksFromBackend(list);
		assertEquals(result.size(), 1);
		
		result = ExecutorTestDriver.getFinishedTasksFromBackend(list);
		assertEquals(result.size(), 1);
	}
	
	@Test
	public void testDelete() {
		TaskList list = new TaskList();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 1");
		
		ExecutorTestDriver.addTask(parameters, list);
		ExecutorTestDriver.addTask(parameters, list);
		
		ExecutorTestDriver.deleteTask(list, 0);
		assertEquals(list.size(), 1);
	}
	
	@Test
	public void testClear() {
		TaskList list = new TaskList();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 1");
		
		ExecutorTestDriver.addTask(parameters, list);
		ExecutorTestDriver.addTask(parameters, list);
		
		DataManager.storeBack(list, DataFileStack.FILE);
		
		ExecutorTestDriver.clearTasks();
		list = DataManager.retrieve(DataFileStack.FILE);
		assertEquals(list.size(), 0);
	}
	

}
