//@author A0119646X

package com.taskpad.tests;

/**
 * This unit test class tests the Time Parser
 * ensure 100% coverage
 */

import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidTimeException;
import com.taskpad.dateandtime.TimeErrorException;

public class TestTimeParser {
	
	private DateAndTimeManager _timeParser = DateAndTimeManager.getInstance();
	
	private static final String MESSAGE_INVALID = "Error: Invalid time entered";
	private static final String MESSAGE_TIME = "Error: Invalid Time supplied: ";

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
		testTimeCommand("00:00", "12AM");
	}
	
	@Test
	public void AMtest4(){
		testTimeCommand("08:15", "8.15 am");
	}
	
	@Test
	public void AMtest5(){
		testTimeCommand("08:00", "8.00am");
	}
	
	@Test
	public void AMtest6(){
		testTimeCommand("08:15", "815am");
	}
	
	@Test
	public void AMtest7(){
		testTimeCommand("08:15", "815 am");
	}
	
	/*test if statement in convertMillisecondsToTime*/
	@Test
	public void AMtest8(){
		testTimeCommand("01:01", "1.01 A");
	}
	
	@Test
	public void AMtest9(){
		testTimeCommand("00:15", "1215am");
	}
	
	@Test
	public void AMtest10(){
		testTimeCommand("00:15", "12:15a");
	}
	
	@Test
	public void AMtest11(){
		testTimeCommand("11:00", "11am");
	}
	
	@Test
	public void AMtest12(){
		testTimeCommand("10:00", "10am");
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
	
	/*boundary case: case that failed in OP2*/
	@Test
	public void PMtest3(){
		testTimeCommand("12:00", "12pm");
	}
	
	@Test
	public void PMtest4(){
		testTimeCommand("12:00", "12 Pm ");
	}
	
	@Test
	public void PMtest5(){
		testTimeCommand("20:15", "20;15");
	}
	
	@Test
	public void PMtest6(){
		testTimeCommand("20:15", "815 pm");
	}
	
	@Test
	public void PMtest7(){
		testTimeCommand("12:15", "1215 pm");
	}
	
	@Test
	public void PMtest8(){
		testTimeCommand("23:15", "1115 p");
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
	
	@Test
	public void wordTest15(){
		testTimeCommand("00:00", "MidNiGHt");
	}
	
	@Test
	public void standardTest1(){
		testTimeCommand("19:00", "19:00");
	}
	
	@Test
	public void standardTest2(){
		testTimeCommand("00:00", "00:00");
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
	
	@Test
	public void invalid2(){
		testInvalidTimeCommand(MESSAGE_INVALID, "0800");
	}
	
	@Test
	public void invalid3(){
		testInvalidTimeCommand(MESSAGE_INVALID, "2000");
	}
	
	@Test
	public void invalid4(){
		testInvalidTimeCommand(MESSAGE_INVALID, "20.15");
	}
	
	public void invalid5(){
		testInvalidTimeCommand(MESSAGE_INVALID, null);
	}

	@Test
	public void invalid6(){
		testInvalidTimeCommand(MESSAGE_INVALID, "0 Am");
	}
	
	/*below test case are to test checkIfInvalidTimeString*/
	@Test
	public void invalid7(){
		testInvalidTimeCommand(MESSAGE_INVALID + ": -100am", "-100 Am");
	}
	
	@Test
	public void invalid8(){
		testInvalidTimeCommand(MESSAGE_INVALID + ": -10am", "-10 Am");
	}
	
	@Test
	public void invalid9(){
		testInvalidTimeCommand(MESSAGE_INVALID + ": 0-1am", "0-1 Am");
	}
	
	@Test
	public void invalid10(){
		testInvalidTimeCommand(MESSAGE_INVALID + ": 1400am", "1400 Am");
	}
	
	@Test
	public void invalid11(){
		testInvalidTimeCommand(MESSAGE_INVALID + ": 1199am", "1199 Am");
	}
	
	@Test
	public void invalid12(){
		testInvalidTimeCommand(MESSAGE_INVALID + ": 0000pm", "0000 pm");
	}
	
	@Test
	public void invalid13(){
		testInvalidTimeCommand(MESSAGE_INVALID + ": 000pm", "000 pm");
	}
	/*above test case are to test checkIfInvalidTimeString*/
	
	@Test
	public void invalid14(){
		testInvalidTimeCommand(MESSAGE_INVALID, "00.15");
	}
	
	@Test
	public void invalid15(){
		testInvalidTimeCommand(MESSAGE_INVALID, "00 Am");
	}
	
	@Test
	public void invalid16(){
		testInvalidTimeCommand(MESSAGE_INVALID, "00 p");
	}
	
	@Test
	public void invalid17(){
		testInvalidTimeCommand(MESSAGE_TIME + "24:00", "24:00");
	}
	
	private void testTimeCommand (String expected, String input){
		try {
			assertEquals(expected, DateAndTimeManager.getInstance().parseTimeInput(input));
		} catch (TimeErrorException | InvalidTimeException e) {
			fail();
		}
	}
	
	private void testInvalidTimeCommand(String expected, String input){
		try {
			_timeParser.parseTimeInput(input);
			fail();
		} catch (TimeErrorException | InvalidTimeException e) {
			assertEquals(e.getMessage(), expected);
			e.printStackTrace();
		}
	}

}
