//@author A0119646X

package com.taskpad.tests;



/**
 * This class is to test the input object passed to executor
 */

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.input.Input;
import com.taskpad.input.InputManager;

public class TestInput {

	private static final String DEBUG_DATE = "10/04/2014 00:00";
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
		testInputString("TASKID 1\r\nINFO venue : meeting room", "addinfo 1 venue: meeting room");
	}
	
	@Test
	public void testAddRem(){
		setUpStream();
		testInputString("Output to GUI: Reminder added!  1: 23/04/2015 16:00\r\n"
				+ "TIME 16:00\r\nDATE 23/04/2015\r\nTASKID 1", "addrem 1 23/04/2015 16:00");
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
		testInputString("KEYWORD \r\nTASKID 1", "Del 1");
		testInputString("KEYWORD hello\r\nTASKID ", "Del hello");
	}
	
	@Test
	public void testEdit(){
		setUpStream();
		testInputString("DESC new\r\nTASKID 1"
				, "Edit 1 new description");
		
		testInputString("Output to GUI: Warning: TaskID is not in standard position\r\n"
				+ "DESC new\r\n"
				+ "TASKID 1"
				, "Edit one new description");
		
		testInputString("Output to GUI: Warning: TaskID is not in standard position\r\n"
				+ "Output to GUI: Error: Invalid TaskID",
				"Edit one one new description, a, dead 10/04/2014");
		
		testInputString("TASKID 1"
				, "Edit 1 desc");
		
		testInputString("END TIME 23:59\r\n"
				+ "START TIME 00:00\r\n"
				+ "DEADLINE 23:59 13/04/2014\r\n"
				+ "START DATE 10/04/2014\r\n"
				+ "DESC a\r\n"
				+ "TASKID 1\r\n"
				+ "END DATE 14/04/2014"
				, "Edit 1 desc a, end Monday, start today, dead Sunday");

		testInputString("Output to GUI: Warning: TaskID is not in standard position\r\n"
				+ "Output to GUI: BY a Sunday is not a valid date\r\n"
				+ "END TIME 23:59\r\n"
				+ "START TIME 00:00\r\n"
				+ "START DATE 10/04/2014\r\n"
				+ "DESC . a\r\n"
				+ "TASKID 1\r\n"
				+ "END DATE 14/04/2014"
				, "Edit one. desc a, end Monday, start today, dead a Sunday");
		
		testInputString("Output to GUI: Error: Empty Input"
				, "Edit");
		
		testInputString("Output to GUI: Error: Invalid Number of Parameters. Type Help if you need! :) "
				, "Edit aa");
		
		testInputString("Output to GUI: Warning: TaskID is not in standard position\r\n"
				+ "Output to GUI: Error: Invalid TaskID"
				, "Edit aa nn");
		
		testInputString("DESC nn\r\n" 
				+ "TASKID 1"
				, "Edit 1 nn");
		
		testInputString("START TIME 19:00\r\n"
				+ "START DATE 03/08/2014\r\n"
				+ "TASKID 1"
				, "Edit 1 start 19:00 03/08/2014");
		
		testInputString("START TIME 00:00\r\n"
				+ "START DATE 11/04/2014\r\n"
				+ "TASKID 1"
				, "Edit 1 start 1 day");
		
		testInputString("START TIME 16:00\r\n"
				+ "START DATE 11/04/2014\r\n"
				+ "TASKID 1"
				, "Edit 1 start 4pm tmr");
		
  		testInputString("Output to GUI: TO a Monday is not a valid date\r\n"
  				+ "Output to GUI: BY a that day is not a valid date\r\n"
  				+ "Output to GUI: WARNING: has 3 start date and time\r\n"
			+ "Output to GUI: WARNING: has 3 end date and time\r\n"
			+ "Output to GUI: WARNING: has 3 deadline\r\n"
			+ "END TIME \r\n"
			+ "START TIME 00:00\r\n"
			+ "DEADLINE 23:59 14/04/2014\r\n"
			+ "START DATE 10/04/2014\r\n"
			+ "DESC a\r\n"
			+ "TASKID 1\r\n"
			+ "END DATE "
			, "Edit 1 desc a, end Monday, start today, start ,end ,dead Sunday, dead Monday"
			+ ", end a Monday, dead a that day, start a today");
	}
	
	@Test
	public void testSearch(){
		setUpStream();
		testInputString("TIME \r\nKEYWORD dragon potions", "search dragon potions");
		testInputString("TIME 15/04/2014\r\nKEYWORD 15/04/2014", "search 15/04/2014");
	}
	
	@Test
	public void testList(){
		setUpStream();
		testInputString("DEADLINE \r\nKEY ALL", "list all");
		testInputString("DEADLINE \r\nKEY DONE", "list done");
		testInputString("DEADLINE \r\nKEY UNDONE", "list undone");
		testInputString("DEADLINE 31/03/2016\r\nKEY ", "list 31/03/2016");
	}
	
	@Test
	public void testDone(){
		setUpStream();
		testInputString("TASKID 1", "done 1");
	}
	
	@Test
	public void testAdd1(){
		setUpStream();
		testInputString("START TIME 08:00\r\nEND TIME 11:00\r\n"
				+ "START DATE 11/11/2015\r\nDESC  aaa 1\r\nEND DATE 11/11/2015\r\n"
				+ "DEADLINE DATE 23:59 11/11/2015", 
				"add 1 -s 8am, 11/11/15 -d 11/11/15 -e 11am, 11/11/15 \" aaa\"");
	}
	
	
	private void testInputString(String expected, String input){
		setupDebugDate(DEBUG_DATE);
		ArrayList<Input> debugStor = setupDebugStorage();
		InputManager.setDebug(true, debugStor);
		//assertEquals(description, expected, InputManager.receiveFromGui(input));
		InputManager.receiveFromGui(input);
		assertEquals(expected + "\r\n", _outContent.toString());
		cleanUpStreams();
	}

	/**
	 * @return
	 */
	private ArrayList<Input> setupDebugStorage() {
		ArrayList<Input> debugStor = new ArrayList<Input>();
		Map<String, String> param = new HashMap<String, String>();
		param.put("DESC", "AAA");
		Input newInput = new Input("ADD", param);
		debugStor.add(newInput);
		return debugStor;
	}

	/**
	 * @throws ParseException
	 */
	private void setupDebugDate(String dateString) {
		try {
			DateAndTimeManager.getInstance().setDebug(dateString);
		} catch (ParseException e) {
			fail();
		}
	}
	
	private void setUpStream(){
		System.setOut(new PrintStream(_outContent));
	}
	
	private void cleanUpStreams(){
		_outContent.reset();
	}

}
