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

	@Test(expected = NoPreviousCommandException.class)
	public void testCommandRecord() throws NoPreviousCommandException {
		CommandRecord.pushForUndo("add do homework 1");
		CommandRecord.pushForUndo("delete 1");
		try {
			assert(CommandRecord.popForUndo().equals("delete 1"));
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
	
	@Test
	public void TestDataFileStack() {
		DataFileStack.pushForUndo(".data0.xml");
		try {
			DataFileStack.popForUndo();
		} catch (NoPreviousFileException e) {
			fail("Fail to pop from DataFileStack undoStack");
		}
		DataFileStack.pushForRedo(".data0.xml");
		assert(DataFileStack.requestDataFile().equals(".data1.xml"));
		DataFileStack.pushForUndo(".data1.xml");
		assert(DataFileStack.requestDataFile().equals(".data2.xml"));
	}

	@Test(expected = SAXParseException.class)
	public void TestDataManager() {
		/* 
		 * Only testing 0, 1, 2 tasks here,
		 * since 2 or more are unlikely to be dealt with differently
		 */
		TaskList list = new TaskList();
		DataManager.storeBack(list, DataFileStack.FILE);
		assert(DataManager.retrieveNumberOfTasks() == 0);
		
		list.add(new Task("some task", null, null, null, null, null, null));
		DataManager.storeBack(list, DataFileStack.FILE);
		assert(DataManager.retrieveNumberOfTasks() == 1);
		
		list.add(new Task("some task", null, null, null, null, null, null));
		DataManager.storeBack(list, DataFileStack.FILE);
		assert(DataManager.retrieveNumberOfTasks() == 2);
		
		TaskList currentList = DataManager.retrieve(DataFileStack.FILE);
		Task task = currentList.get(0);
		assert(task.getDescription().equals("some task"));
		/* 
		 * There is usually no need to test that all other attributes are null/empty
		 * They are unlikely to be dealt with differently 
		 * Only testing deadline and venue here
		 */
		assert(task.getDeadline() == null);
		assert(task.getVenue() == null);
		
		/* partition: existing file */
		TaskList testList = DataManager.retrieve(DataFileStack.FILE);
		assert(testList.size() > 0);
		
		/* partition: non-existing file */
		testList = DataManager.retrieve("test.xml");
		assert(testList.size() == 0);
		
		/* partition: empty string as file name */
		testList = DataManager.retrieve("");	// should throw SAXParseException
		
	}
}
