package com.taskpad.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidDateException;

public class TestDateParser {
	private DateAndTimeManager _dateParser = DateAndTimeManager.getInstance();
	
	@Test
	public void test1() {
		try {
			assertEquals(_dateParser.parseDate("11/11/94"), "11/11/1994");
		} catch (InvalidDateException e) {
			fail();
		}
	}

}
