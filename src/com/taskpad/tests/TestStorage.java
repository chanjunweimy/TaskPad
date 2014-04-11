//@author A0105788U

package com.taskpad.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.xml.sax.SAXParseException;

import com.taskpad.storage.CommandRecord;
import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.NoPreviousCommandException;
import com.taskpad.storage.NoPreviousFileException;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

public class TestStorage {

	/**
	 * This is to test whether the record of previously
	 * entered commands is successfully stored
	 * for the sake of undo and redo
	 * 
	 * @throws NoPreviousCommandException
	 * 
	 */
	@Test(expected = NoPreviousCommandException.class)
	public void testCommandRecord() throws NoPreviousCommandException {
		CommandRecord.pushForUndo("add do homework 1");
		CommandRecord.pushForUndo("delete 1");
		try {
			assertTrue(CommandRecord.popForUndo().equals("delete 1"));
		} catch (NoPreviousCommandException e) {
			fail("Fail to pop from CommandRecord undoStack");
		}
		
		CommandRecord.pushForRedo("delete 1");
		try {
			CommandRecord.popForRedo();
		} catch (NoPreviousCommandException e) {
			fail("Fail to pop from CommandRecord redoStack");
		}
		
		/* should throw NoPreviousCommandException */
		CommandRecord.popForRedo();
	}
	
	/**
	 * This is to test whether the data file stack
	 * is successfully stored for the sake of undo and redo
	 * 
	 */
	@Test
	public void TestDataFileStack() {
		DataFileStack.pushForUndo(".data0.xml");
		try {
			DataFileStack.popForUndo();
		} catch (NoPreviousFileException e) {
			fail("Fail to pop from DataFileStack undoStack");
		}
		DataFileStack.pushForRedo(".data0.xml");
		assertTrue(DataFileStack.requestDataFile().equals(".data1.xml"));
		DataFileStack.pushForUndo(".data1.xml");
		assertTrue(DataFileStack.requestDataFile().equals(".data2.xml"));
	}

	/**
	 * This is to test whether DataManager can successfully
	 * store tasks into or retrieve tasks from database 
	 */
	@Test
	public void TestDataManagerStoreAndRetrieve() {
		/* 
		 * Only testing 0, 1, 2 tasks here,
		 * since 2 or more are unlikely to be dealt with differently
		 */
		TaskList list = new TaskList();
		DataManager.storeBack(list, DataFileStack.FILE);
		assertTrue(DataManager.retrieveNumberOfTasks() == 0);
		
		list.add(new Task("some task", null, null, null, null, null, null));
		DataManager.storeBack(list, DataFileStack.FILE);
		assertTrue(DataManager.retrieveNumberOfTasks() == 1);
		
		list.add(new Task("some task", null, null, null, null, null, null));
		DataManager.storeBack(list, DataFileStack.FILE);
		assertTrue(DataManager.retrieveNumberOfTasks() == 2);
		
		TaskList currentList = DataManager.retrieve(DataFileStack.FILE);
		Task task = currentList.get(0);
		assertTrue(task.getDescription().equals("some task"));
		/* 
		 * There is usually no need to test that all other attributes are null/empty
		 * They are unlikely to be dealt with differently 
		 * Only testing deadline and venue here
		 */
		assertTrue(task.getDeadline() == null);
		assertTrue(task.getVenue() == null);
		
		/* partition: existing file */
		TaskList testList = DataManager.retrieve(DataFileStack.FILE);
		assertTrue(testList.size() > 0);
		
		/* partition: non-existing file */
		testList = DataManager.retrieve("test.xml");
		assertTrue(testList.size() == 0);
		
		/* partition: empty string as file name */
		// testList = DataManager.retrieve("");

	}
	
	@Test
	public void testStoreAndRetriveFloatingTasks() {
		TaskList list = new TaskList();
		list.add(new Task("task 1", null, null, null, null, null, null));
		list.add(new Task("task 2", null, null, null, null, null, null));
		
		DataManager.storeBack(list, DataFileStack.FILE);
		TaskList listRetrieved = DataManager.retrieve(DataFileStack.FILE);
		
		assertEquals(listRetrieved.size(), 2);
	}
	
	@Test
	public void testStoreAndRetriveTasksWithDeadline() {
		TaskList list = new TaskList();
		list.add(new Task("task 1", "02/07/2014", null, null, null, null, null));
		list.add(new Task("task 2", "02/08/2014", null, null, null, null, null));
		
		DataManager.storeBack(list, DataFileStack.FILE);
		TaskList listRetrieved = DataManager.retrieve(DataFileStack.FILE);
		
		assertEquals(listRetrieved.size(), 2);
	}	
	
	@Test
	public void testStoreAndRetriveTasksWithFixedTime() {	//to be finished
		TaskList list = new TaskList();
		list.add(new Task("task 1", null, "02/07/2014", "16:00", "03/07/2014", "18:00", null));
		list.add(new Task("task 2", null, "02/08/2014", "18:00", "04/07/2014", "20:00", null));
		
		DataManager.storeBack(list, DataFileStack.FILE);
		TaskList listRetrieved = DataManager.retrieve(DataFileStack.FILE);
		
		assertEquals(listRetrieved.size(), 2);
	}	
	
}
