package com.taskpad.tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.DatePassedException;
import com.taskpad.dateandtime.InvalidDayException;
import com.taskpad.dateandtime.InvalidTimeException;
import com.taskpad.dateandtime.TimeErrorException;

public class TestSpecialWordParser {

	private DateAndTimeManager _specialWordParser = DateAndTimeManager.getInstance();
	
	private static final String MESSAGE_INVALID = "Error: Invalid time entered";
	
	@Test
	public void test1() {
		testWordCommand("", "MON");
	}
	
	private void testWordCommand (String expected, String input){
		try {
			assertEquals(expected, _specialWordParser.parseDayToDate(input));
		} catch (InvalidDayException | DatePassedException e) {
			fail();
		}
	}
	
	private void testInvalidWordCommand(String input, String expected){
		try {
			_specialWordParser.parseDayToDate(input);
		} catch (InvalidDayException | DatePassedException e) {
			assertEquals(e.getMessage(), MESSAGE_INVALID);
		}
	}

}
