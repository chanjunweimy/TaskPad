package com.taskpad.tests;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;

public class TestDateAndTimeRetriever {

	private DateAndTimeManager _datm = DateAndTimeManager.getInstance();


	@Test
	public void testValidBigger1() {
		testInputCommand(1, "10/12/2014", "18/10/2014 00:00");
	}
	
	@Test
	public void testValidBigger2() {
		testInputCommand(1, "10/12/2014 11:00", "18/10/2014 00:00");
	}
	
	@Test
	public void testValidBigger3() {
		testInputCommand(1, "19/10/2014 00:01", "18/10/2014 00:00");
	}
	
	@Test
	public void testValidBigger4() {
		testInputCommand(1, "00:01 19/10/2014", "18/10/2014 00:00");
	}
	
	@Test
	public void testValidBigger5() {
		testInputCommand(1, "00:01 18/10/2014", "00:01 18/8/2014", "18/10/2014 00:00");
	}
	
	/**
	 * this is compared with 18/10/2014 23:59
	 */
	@Test
	public void testValidSmaller2() {
		testInputCommand(-1, "00:01 18/10/2014", "18/10/2014 00:00");
	}
	
	@Test
	public void testValidSmaller1() {
		testInputCommand(-1, "00:01 18/8/2014",  "00:01 18/10/2014", "18/10/2014 00:00");
	}
	
	@Test
	public void testValidSame1() {
		testInputCommand(0, "00:01 18/10/2014",  "00:01 18/10/2014", "18/10/2014 00:00");
	}
	
	@Test
	public void testInvalid1() {
		testInputCommand(-2, null, null, "18/10/2014 00:00");
	}
	
	@Test
	public void testInvalid2() {
		testInputCommand(-2, null, "18/10/2014 00:00");
	}
	
	@Test
	public void testInvalid3() {
		testInputCommand(-2, "00:01 18/10/2014", null, "18/10/2014 00:00");
	}
	
	@Test
	public void testInvalid4() {
		testInputCommand(-2, "00:01 18/10/2014", "AA", "18/10/2014 00:00");
	}
	
	@Test
	public void testInvalid5() {
		testInputCommand(-2, "BB", "00:01 18/10/2014", "18/10/2014 00:00");
	}
	
	private void testInputCommand(int expected, String input1, String dateString){
		setupDebugEnvironment(dateString);

		assertEquals(expected, _datm.compareDateAndTime(input1));

	}
	
	private void testInputCommand(int expected, String input1, String input2, String dateString){
		setupDebugEnvironment(dateString);

		assertEquals(expected, _datm.compareDateAndTime(input1, input2));

	}
	
	private void setupDebugEnvironment(String dateString){
		try {
			_datm.setDebug(dateString);
		} catch (ParseException e) {
			//wrong date causes failed
			fail();
		}
	}

}
