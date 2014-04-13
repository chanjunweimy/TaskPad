//@author A0105788U

package com.taskpad.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import com.taskpad.execute.CommandFactoryBackend;
import com.taskpad.execute.ExecutorTestDriver;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

/**
 * This class is for unit testing of Executor component.
 * It is testing by command types.
 *
 */
public class TestExecutor {	
	
	@Test
	public void testAddAndList() {
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

		// test list undone
		Task task = list.get(0);
		task.setDone();
		result = ExecutorTestDriver.getUndoneTasksFromBackend(list);
		assertEquals(result.size(), 1);
		
		// test list done
		result = ExecutorTestDriver.getFinishedTasksFromBackend(list);
		assertEquals(result.size(), 1);
	}
	
	@Test
	public void testAddLargeNumberOfTasks() {
		TaskList list = new TaskList();
		for (int i = 0; i < 100; i++) {
			HashMap<String, String> parameters = new HashMap<String, String>();
			parameters.put("DESC", "do homework 1");
			ExecutorTestDriver.addTask(parameters, list);			
		}
		LinkedList<Integer> result = ExecutorTestDriver.getAllTasksFromBackend(list);
		assertEquals(result.size(), 100);
	}
	
	@Test
	public void testListAll() {
		TaskList list = new TaskList();
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 1");		
		ExecutorTestDriver.addTask(parameters, list);
		
		parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 2");		
		ExecutorTestDriver.addTask(parameters, list);
		
		parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 3");		
		ExecutorTestDriver.addTask(parameters, list);
		
		LinkedList<Integer> result = ExecutorTestDriver.getAllTasksFromBackend(list);
		assertEquals(result.size(), 3);		
	}
	
	@Test
	public void testListUndone() {
		TaskList list = new TaskList();
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 1");	
		ExecutorTestDriver.addTask(parameters, list);	
		
		Task task = list.get(0);
		task.setDone();
		LinkedList<Integer> result = ExecutorTestDriver.getUndoneTasksFromBackend(list);
		assertEquals(result.size(), 0);
	}
	
	@Test
	public void testListDone() {
		TaskList list = new TaskList();
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "do homework 1");	
		ExecutorTestDriver.addTask(parameters, list);	
		
		Task task = list.get(0);
		task.setDone();
		LinkedList<Integer> result = ExecutorTestDriver.getFinishedTasksFromBackend(list);
		
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
	public void testAddinfo() {
		TaskList list = new TaskList();
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "cs2103 tutorial 4");		
		ExecutorTestDriver.addTask(parameters, list);
		
		ExecutorTestDriver.addInfo("next Wed", list, 0);
		
		Task task = list.get(0);
		
		assertEquals(task.getDetails(), "next Wed");
	}
	
	@Test
	public void testSearchByKeyword() {
		TaskList list = new TaskList();
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "cs2103 tutorial 4");		
		ExecutorTestDriver.addTask(parameters, list);
		
		parameters = new HashMap<String, String>();
		parameters.put("DESC", "cs2103 tutorial 5");		
		ExecutorTestDriver.addTask(parameters, list);
		
		parameters = new HashMap<String, String>();
		parameters.put("DESC", "cs2101 oral presentation 2");
		ExecutorTestDriver.addTask(parameters, list);
		
		String[] keywords = {"cs2103", "tutorial"};
		String[] times = {};
		LinkedList<Integer> result = ExecutorTestDriver.search(list, keywords, times);
		
		assertTrue(result.contains(0));
		assertTrue(result.contains(1));
	}
	
	@Test
	public void testSearchByTime() {
		TaskList list = new TaskList();
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DESC", "cs2103 tutorial 4");
		parameters.put("DEADLINE", "02/08/2014");
		ExecutorTestDriver.addTask(parameters, list);
		
		parameters = new HashMap<String, String>();
		parameters.put("DESC", "cs2103 tutorial 5");		
		ExecutorTestDriver.addTask(parameters, list);
		
		parameters = new HashMap<String, String>();
		parameters.put("DESC", "cs2101 oral presentation 2");
		parameters.put("START DATE", "02/08/2014");
		ExecutorTestDriver.addTask(parameters, list);
		
		String[] keywords = {"03/08/2014", "03/08/2014"};
		String[] times = {"02/08/2014", "03/08/2014"};
		LinkedList<Integer> result = ExecutorTestDriver.search(list, keywords, times);
		
		assertTrue(result.contains(0));
		assertTrue(result.contains(2));		
	}
	
}
