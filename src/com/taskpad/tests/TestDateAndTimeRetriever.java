//@author A0112084U

package com.taskpad.tests;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;

public class TestDateAndTimeRetriever {

	private DateAndTimeManager _datm = DateAndTimeManager.getInstance();


	@Test
	public void testCompareDateAndTimeExecutorValidBigger1() {
		testCompareDateAndTimeExecutor(1, "10/12/2014", "18/10/2014 00:00");
	}
	
	@Test
	public void testCompareDateAndTimeExecutorValidBigger2() {
		testCompareDateAndTimeExecutor(1, "10/12/2014 11:00", "18/10/2014 00:00");
	}
	
	@Test
	public void testCompareDateAndTimeExecutorValidBigger3() {
		testCompareDateAndTimeExecutor(1, "19/10/2014 00:01", "18/10/2014 00:00");
	}
	
	@Test
	public void testCompareDateAndTimeExecutorValidBigger4() {
		testCompareDateAndTimeExecutor(1, "00:01 19/10/2014", "18/10/2014 00:00");
	}
	
	@Test
	public void testCompareDateAndTimeExecutorValidBigger5() {
		testCompareDateAndTimeExecutor(1, "00:01 18/10/2014", "00:01 18/8/2014", "18/10/2014 00:00");
	}
	
	/**
	 * this is compared with 18/10/2014 23:59
	 */
	@Test
	public void testCompareDateAndTimeExecutorValidSmaller2() {
		testCompareDateAndTimeExecutor(-1, "00:01 18/10/2014", "18/10/2014 00:00");
	}
	
	@Test
	public void testCompareDateAndTimeExecutorValidSmaller1() {
		testCompareDateAndTimeExecutor(-1, "00:01 18/8/2014",  "00:01 18/10/2014", "18/10/2014 00:00");
	}
	
	@Test
	public void testCompareDateAndTimeExecutorValidSame1() {
		testCompareDateAndTimeExecutor(0, "00:01 18/10/2014",  "00:01 18/10/2014", "18/10/2014 00:00");
	}
	
	@Test
	public void testCompareDateAndTimeExecutorInvalid1() {
		testCompareDateAndTimeExecutor(-2, null, null, "18/10/2014 00:00");
	}
	
	@Test
	public void testCompareDateAndTimeExecutorInvalid2() {
		testCompareDateAndTimeExecutor(-2, null, "18/10/2014 00:00");
	}
	
	@Test
	public void testCompareDateAndTimeExecutorInvalid3() {
		testCompareDateAndTimeExecutor(-2, "00:01 18/10/2014", null, "18/10/2014 00:00");
	}
	
	@Test
	public void testCompareDateAndTimeExecutorInvalid4() {
		testCompareDateAndTimeExecutor(-2, "00:01 18/10/2014", "AA", "18/10/2014 00:00");
	}
	
	@Test
	public void testCompareDateAndTimeExecutorInvalid5() {
		testCompareDateAndTimeExecutor(-2, "BB", "00:01 18/10/2014", "18/10/2014 00:00");
	}
	
	private void testCompareDateAndTimeExecutor(int expected, String input1, String dateString){
		setupDebugEnvironment(dateString);

		assertEquals(expected, _datm.compareDateAndTime(input1));

	}
	
	private void testCompareDateAndTimeExecutor(int expected, String input1, String input2, String dateString){
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
