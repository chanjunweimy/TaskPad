//@author A0105788U

package com.taskpad.tests;


import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import com.taskpad.data.DataFileStack;
import com.taskpad.data.DataManager;
import com.taskpad.execute.ExecutorTestDriver;
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
	
	@Test
	public void testUndo() throws NoPreviousFileException {
		TaskList list = new TaskList();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 1");
		
		ExecutorTestDriver.addTask(parameters, list);
		ExecutorTestDriver.archiveForUndo();
		ExecutorTestDriver.addTask(parameters, list);
		ExecutorTestDriver.archiveForUndo();
		
		ExecutorTestDriver.updateDataForUndo();
		
		list = DataManager.retrieve(DataFileStack.FILE);
		assertEquals(list.size(), 1);
	}
	
}
