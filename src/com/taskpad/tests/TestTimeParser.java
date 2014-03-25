package com.taskpad.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidDateException;
import com.taskpad.dateandtime.InvalidTimeException;
import com.taskpad.dateandtime.NullTimeUnitException;
import com.taskpad.dateandtime.NullTimeValueException;
import com.taskpad.dateandtime.TimeErrorException;

public class TestTimeParser {
	
	private DateAndTimeManager _timeParser = DateAndTimeManager.getInstance();
	
	private static final String MESSAGE_INVALID = "Not a valid time";

	/**
	 * Test AM strings 
	 */

	@Test
	public void AMtest1() {
		testTimeCommand("08:00", "8am");
	}

	@Test
	public void AMtest2(){
		testTimeCommand("08:00", "8 am");
	}
	
	@Test
	public void AMtest3(){
		testTimeCommand("08:00", "0800");
	}
	
	@Test
	public void AMtest4(){
		testTimeCommand("08:15", "8.15 am");
	}
	
	@Test
	public void AMtest5(){
		testTimeCommand("08:00", "8.00am");
	}
	
	
	/**
	 * Test PM strings 
	 */
	@Test
	public void PMtest1(){
		testTimeCommand("20:00", "8pm");
	}
	
	@Test
	public void PMtest2(){
		testTimeCommand("20:00", "8 pm");
	}
	
	@Test
	public void PMtest3(){
		testTimeCommand("20:00", "2000");
	}
	
	@Test
	public void PMtest4(){
		testTimeCommand("20:15", "20.15");
	}
	
	@Test
	public void PMtest5(){
		testTimeCommand("20:15", "20;15");
	}
	
	/** Junwei can help me see this case? */
	@Test
	public void PMtest6(){
		testTimeCommand("20:15", "815 pm");
	}
	
	/**
	 * Test word strings 
	 */
	
	@Test
	public void wordTest1(){
		testTimeCommand("00:00", "morning");
	}
	
	@Test
	public void wordTest2(){
		testTimeCommand("00:00", "morn");
	}
	
	@Test
	public void wordTest3(){
		testTimeCommand("12:00", "aft");
	}
	
	@Test
	public void wordTest4(){
		testTimeCommand("12:00", "afternoon");
	}
	
	@Test
	public void wordTest5(){
		testTimeCommand("12:00", "noon");
	}
	
	@Test
	public void wordTest6(){
		testTimeCommand("17:00", "evening");
	}
	
	@Test
	public void wordTest7(){
		testTimeCommand("17:00", "eve");
	}
	
	@Test
	public void wordTest8(){
		testTimeCommand("19:00", "night");
	}
	
	@Test
	public void wordTest9(){
		testTimeCommand("19:00", "ngt");
	}
	
	@Test
	public void wordTest10(){
		testTimeCommand("00:00", "MORN");
	}
	
	@Test
	public void wordTest11(){
		testTimeCommand("00:00", "MORNING");
	}
	
	@Test
	public void wordTest12(){
		testTimeCommand("12:00", "AFT");
	}
	
	@Test
	public void wordTest13(){
		testTimeCommand("12:00", "NOON");
	}
	
	@Test
	public void wordTest14(){
		testTimeCommand("19:00", "NiGHt");
	}
	
	/**
	 * Test unsupported cases
	 * @param expected
	 * @param input
	 */
	
	@Test
	public void invalid1(){
		testInvalidTimeCommand(MESSAGE_INVALID, "");
	}
	
	private void testTimeCommand (String expected, String input){
		try {
			assertEquals(expected, DateAndTimeManager.getInstance().parseTimeInput(input));
		} catch (TimeErrorException | InvalidTimeException e) {
			fail();
		}
	}
	
	private void testInvalidTimeCommand(String input, String expected){
		try {
			_timeParser.parseTimeInput(input);
		} catch (TimeErrorException | InvalidTimeException e) {
			assertEquals(e.getMessage(), MESSAGE_INVALID);
			e.printStackTrace();
		}


	}

}
