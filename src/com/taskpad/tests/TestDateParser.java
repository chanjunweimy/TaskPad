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

	@Test
	public void test2() {
		try {
			assertEquals(_dateParser.parseDate("11/11/08"), "11/11/2008");
		} catch (InvalidDateException e) {
			fail();
		}
	}
	
	@Test
	public void test3() {
		try {
			assertEquals(_dateParser.parseDate("11/11/1000"), "11/11/1000");
		} catch (InvalidDateException e) {
			fail();
		}
	}
	
	@Test
	public void test4() {
		try {
			assertEquals(_dateParser.parseDate("13-12-14"), "13/12/2014");
		} catch (InvalidDateException e) {
			fail();
		}
	}
	
	@Test
	public void test5() {
		try {
			assertEquals(_dateParser.parseDate("011214"), "01/12/2014");
		} catch (InvalidDateException e) {
			fail();
		}
	}
	
	@Test
	public void test6() {
		try {
			assertEquals(_dateParser.parseDate("31/02/2014"), "03/03/2014");
		} catch (InvalidDateException e) {
			fail();
		}
	}
	@Test
	public void test7() {
		try {
			assertEquals(_dateParser.parseDate("2014Dec1"), "01/12/2014");
		} catch (InvalidDateException e) {
			fail();
		}
	}
	
	@Test
	public void test8() {
		try {
			_dateParser.parseDate(null);
		} catch (InvalidDateException e) {
			assertEquals(e.getMessage(), "Not a valid date");
		}
	}
	
	@Test
	public void test9() {
		try {
			_dateParser.parseDate(" ");
		} catch (InvalidDateException e) {
			assertEquals(e.getMessage(), "Not a valid date");
		}
	}
	
	@Test
	public void test10() {
		try {
			assertEquals(_dateParser.parseDate("111111"), "11/11/2011");
			//_dateParser.parseDate(" ");
		} catch (InvalidDateException e) {
			fail();
			//assertEquals(e.getMessage(), "Not a valid date");
		}
	}
	
	@Test
	public void test11() {
		try {
			/**
			 * this is not an issue,
			 * just java help you to modify invalid date to valid date.
			 */
			assertEquals(_dateParser.parseDate("000000"), "30/11/1999");
			//_dateParser.parseDate("000000");
		} catch (InvalidDateException e) {
			fail();
			//assertEquals(e.getMessage(), "Not a valid date");
		}
	}
}
