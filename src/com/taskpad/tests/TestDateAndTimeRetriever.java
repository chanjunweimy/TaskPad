//@author A0112084U

package com.taskpad.tests;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidQuotesException;

public class TestDateAndTimeRetriever {

	private DateAndTimeManager _datm = DateAndTimeManager.getInstance();

	/*below is to test checkDateAndTimeWithStart*/
	@Test
	public void testValidCheckDateAndTimeWithStart1() {
		testCheckDateAndTimeWithStart(null, "18/10/1993 00:00","18/08/1994 00:00");
	}
	
	private void testCheckDateAndTimeWithStart(String expected, String startEarliest, String dateLatest){
		_datm.checkDateAndTimeWithStart(startEarliest, dateLatest);
	}
	/*above is to test checkDateAndTimeWithStart*/
	
	/*below is to test parseNumberString*/
	@Test
	public void testValidNumberString1() {
		testParseNumberString("I want to have 1 apple", "I want to have one apple", true);
		testParseNumberString("I want to have 1 apple", "I want to have one apple", false);
	}
	
	@Test
	public void testValidNumberString2() {
		testParseNumberString("I want to have 1 , thanks", "I want to have one,thanks", true);
		testParseNumberString("I want to have 1 , thanks", "I want to have one, thanks", false);
	}
	
	@Test
	public void testValidNumberString3() {
		testParseNumberString("1 ppl named two want to have 1 .", "One ppl named \"two\" want to have 1.", true);
		testParseNumberString("1 ppl named two want to have 1 .", "One ppl named \"two\" want to have 1.", false);
	}
	
	@Test
	public void testValidNumberString4() {
		testParseNumberString("11/11/15 11 / 11 / 15 , 11:11 12 : 00 ,", "11/11/15 11/11/15, 11:11 12:00,", true);
		testParseNumberString("11 / 11 / 15 11 : 11", "11/11/15 11:11", false);
	}
	
	@Test
	public void testInvalidNumberString1() {
		testInvalidParseNumberString("Error: Cannot have odd numbers of quotes", 
				"One ppl named \"two\" want\" to have 1.", true);
		testInvalidParseNumberString("Error: Cannot have odd numbers of quotes", 
				"One ppl named \"two\" want \" to have 1.", false);
	}
	
	private void testParseNumberString(String expected, String input, boolean isDateAndTimePreserved){
		try {
			assertEquals(expected, _datm.parseNumberString(input, isDateAndTimePreserved));
		} catch (InvalidQuotesException e) {
			fail();
		}
	}
	
	private void testInvalidParseNumberString(String expected, String input, boolean isDateAndTimePreserved){
		try {
			_datm.parseNumberString(input, isDateAndTimePreserved);
			fail();
		} catch (InvalidQuotesException e) {
			assertEquals(expected, e.getMessage());
		}
	}
	/*above is to test parseNumberString*/
	
	/*below is to test DateAndTime*/
	@Test
	public void testDateAndTime1() {
		testDateAndTimeObject("11/04/2014", "08:13", "friday", "11/04/2014 08:13");
	}
	
	private void testDateAndTimeObject(String date, String time, String day, String dateString){
		//String dateString = "11/04/2014 08:13";
		setupDebugEnvironment(dateString);

		assertEquals(date, _datm.getTodayDate());
		assertEquals(time, _datm.getTodayTime());
		assertEquals(day.toUpperCase(), _datm.getTodayDay().toUpperCase());
		assertEquals(dateString, _datm.getTodayDateAndTime());
	}
	/*above is to test DateAndTime*/
	
	/*below is testCompareDateAndTimeExecutor*/
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
	/*above is testCompareDateAndTimeExecutor*/
	
	private void setupDebugEnvironment(String dateString){
		try {
			_datm.setDebug(dateString);
		} catch (ParseException e) {
			//wrong date causes failed
			fail();
		}
	}

}
