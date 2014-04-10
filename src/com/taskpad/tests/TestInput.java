//@author A0119646X

package com.taskpad.tests;



/**
 * This class is to test the input object passed to executor
 */

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import com.taskpad.input.InputManager;

public class TestInput {

	private final ByteArrayOutputStream _outContent = new ByteArrayOutputStream();
	
	
	/**
	 * ToDo: alarm not fully being able to test with output streams
	 */
	
	public void testClear(){
		setUpStream();
		testInputString("Output to GUI: \r\nConfirm clear data? (Y/N)", "Clear");
		testInputString("NULL ", "Y");
	}
	
	@Test
	public void testAddInfo(){
		setUpStream();
		testInputString("TASKID 1\r\nINFO venue: meeting room", "addinfo 1 venue: meeting room");
	}
	
	@Test
	public void testAddRem(){
		setUpStream();
		testInputString("TIME 16:00\r\nDATE 23/04/2015\r\nTASKID 1", "addrem 1 23/04/2015 16:00");
	}
	
	@Test
	public void testAlarm(){
		setUpStream();
		testInputString("Output to GUI: Creating alarm... alarm collect laundry 30 min", "alarm collect laundry 30 min");
	}
	
	public void testStopAlarm(){
		setUpStream();
		testInputString("", "STOP");
	}
	
	public void testClearScr(){
		setUpStream();
		testInputString("Output to GUI:\r\nConfirm clear screen? (Y/N)", "screen");
		testInputString("Clear GUI Screen", "Y");
	}
	
	@Test
	public void testDelete(){
		setUpStream();
		testInputString("TASKID 1", "Del 1");
	}
	
	@Test
	public void testEdit(){
		setUpStream();
		testInputString("END TIME \r\n"
				+ "START TIME \r\n"
				+ "DEADLINE \r\n"
				+ "START DATE \r\n"
				+ "DESC new\r\n"
				+ "TASKID 1\r\n"
				+ "END DATE "
				, "Edit 1 new description");
		
		testInputString("END TIME \r\n"
				+ "START TIME \r\n"
				+ "DEADLINE \r\n"
				+ "START DATE \r\n"
				+ "DESC new\r\n"
				+ "TASKID 1\r\n"
				+ "END DATE "
				, "Edit one new description");
		
		testInputString("END TIME \r\n"
				+ "START TIME \r\n"
				+ "DEADLINE \r\n"
				+ "START DATE \r\n"
				+ "DESC new , a\r\n"
				+ "TASKID 11\r\n"
				+ "END DATE "
				, "Edit one one new description, a, dead 10/04/2014");
	}
	
	@Test
	public void testSearch(){
		setUpStream();
		testInputString("KEYWORD dragon potions", "search dragon potions");
	}
	
	@Test
	public void testList(){
		setUpStream();
		testInputString("KEY ALL", "list all");
		testInputString("KEY DONE", "list done");
		testInputString("KEY UNDONE", "list undone");
		testInputString("KEY 31/03/2016", "list 31/03/2016");
	}
	
	@Test
	public void testDone(){
		setUpStream();
		testInputString("TASKID 1", "done 1");
	}
	
	@Test
	public void testAdd1(){
		setUpStream();
		testInputString("START TIME 11:00\r\n"
				+ "END TIME 11:00\r\n"
				+ "DEADLINE 11/11/2015\r\n"
				+ "START DATE 11/11/2015\r\n"
				+ "DESC \" aaa\" 1\r\n"
				+ "END DATE 11/11/2015", 
				"add 1 -s 11am, 11/11/15 -d 11/11/15 -e 11am, 11/11/15 \" aaa\"");
	}
	
	
	private void testInputString(String expected, String input){
		InputManager.setDebug(true);
		//assertEquals(description, expected, InputManager.receiveFromGui(input));
		InputManager.receiveFromGui(input);
		assertEquals(expected + "\r\n", _outContent.toString());
		cleanUpStreams();
	}
	
	private void setUpStream(){
		System.setOut(new PrintStream(_outContent));
	}
	
	private void cleanUpStreams(){
		_outContent.reset();
	}

}
