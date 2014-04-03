package com.taskpad.tests;


import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidDayException;

public class TestDayParser {
	private static final String DAY_INVALID = "Error: Invalid Day";

	/*We test all cases at least once to ensure full path coverage*/
	@Test
	public void test1() {
		testValidDay("Monday", 1);
	}
	
	@Test
	public void test2() {
		testValidDay("Mon", 1);
	}
	
	@Test
	public void test3() {
		testValidDay("TueSday", 2);
	}
	
	@Test
	public void test4() {
		testValidDay("TUES", 2);
	}
	
	@Test
	public void test5() {
		testValidDay("WedNesday", 3);
	}
	
	@Test
	public void test6() {
		testValidDay("WED", 3);
	}
	
	@Test
	public void test7() {
		testValidDay("Thurs", 4);
	}
	
	@Test
	public void test8() {
		testValidDay("Thursday", 4);
	}
	
	@Test
	public void test9() {
		testValidDay("fri", 5);
	}
	
	@Test
	public void test10() {
		testValidDay("FriDay", 5);
	}
	
	@Test
	public void test11() {
		testValidDay("Saturday", 6);
	}
	
	@Test
	public void test12() {
		testValidDay("SAT", 6);
	}
	
	@Test
	public void test13() {
		testValidDay("Sunday", 0);
	}
	
	@Test
	public void test14() {
		testValidDay("Sun", 0);
	}
	
	/*boundary case: when it is null*/
	@Test
	public void test15() {
		testInvalidDay(null);
	}
	
	private void testValidDay(String input, int expected){
		try {
			assertEquals(DateAndTimeManager.getInstance().parseDayToInt(input), expected);
		} catch (InvalidDayException e) {
			fail();
		}
	}
	
	private void testInvalidDay(String input){
		try {
			DateAndTimeManager.getInstance().parseDayToInt(input);
			fail();
		} catch (InvalidDayException e) {
			assertEquals(e.getMessage(), DAY_INVALID);
		}
	}
}
