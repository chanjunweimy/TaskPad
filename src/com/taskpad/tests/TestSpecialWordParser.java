package com.taskpad.tests;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.DatePassedException;
import com.taskpad.dateandtime.InvalidDayException;

public class TestSpecialWordParser {

	private static final String MESSAGE_INVALID_TIME = "Error: Invalid time entered";	
	private static final String MESSAGE_INVALID_DAY = "Error: Invalid Day";

	private DateAndTimeManager _specialWordParser = DateAndTimeManager.getInstance();
	
	//day parser parts
	@Test
	public void validTest1() {
		testWordCommand("24/03/2014", "MON", "23/03/2014 00:01");
	}
	
	@Test
	public void validTest2() {
		testWordCommand("07/04/2014", "NXT NXT MON", "23/03/2014 00:01");
	}
	
	@Test
	public void validTest3() {
		testWordCommand("07/04/2014", "NXT NXT NXT PREV MON", "23/03/2014 00:01");
	}
	
	@Test
	public void validTest4() {
		testWordCommand("30/03/2014", "NXT WK", "23/03/2014 00:01");
	}
	
	@Test
	public void validTest5() {
		testWordCommand("23/03/2014", "THIS WK", "23/03/2014 00:01");
	}
	
	@Test
	public void validTest6() {
		testWordCommand("24/03/2014", "THIS MON", "23/03/2014 00:01");
	}
	
	@Test
	public void validTest7() {
		testWordCommand("24/03/2014", "TMR", "23/03/2014 00:01");
	}
	
	@Test
	public void validTest8() {
		testWordCommand("26/03/2014", "TMR TMR TMR", "23/03/2014 00:01");
	}
	
	@Test
	public void validTest9() {
		testWordCommand("25/03/2014", "tmr tmr TOMORRO TOM TOMORROW YTD YEST YESTERDAY", "23/03/2014 00:01");
	}
	
	@Test
	public void invalidTest1() {
		testInvalidWordCommand(MESSAGE_INVALID_TIME, "MAN", "23/3/2014 00:01");
	}
	
	@Test
	public void invalidTest2() {
		testInvalidWordCommand(MESSAGE_INVALID_DAY, "WEEK", "23/03/2014 00:01");
	}
	
	private void testWordCommand (String expected, String input, String dateString){
		setupDebugEnvironment(dateString);
		
		try {
			assertEquals(expected, _specialWordParser.parseDayToDate(input));
		} catch (InvalidDayException | DatePassedException e) {
			fail();
		}
	}
	
	private void testInvalidWordCommand(String expected, String input, String dateString){
		setupDebugEnvironment(dateString);
		
		try {
			_specialWordParser.parseDayToDate(input);
		} catch (InvalidDayException | DatePassedException e) {
			assertEquals(e.getMessage(), expected);
		}
	}
	
	private void setupDebugEnvironment(String dateString){
		try {
			_specialWordParser.setDebug(dateString);
		} catch (ParseException e) {
			//wrong date causes failed
			fail();
		}
	}

}
