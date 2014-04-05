package com.taskpad.tests;

//@author A0105788U

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import com.taskpad.execute.ExecutorTestDriver;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

public class TestExecutor {	
	
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
	
}
