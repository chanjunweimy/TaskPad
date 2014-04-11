//@author A0112084U

package com.taskpad.tests;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidQuotesException;

public class TestDateAndTimeRetriever {

	private DateAndTimeManager _datm = DateAndTimeManager.getInstance();

	/*below is testConvertDateAndTimeString*/
	@Test
	public void testValidConvertDateAndTimeString1() {
		testConvertDateAndTimeString("find Lynnette on 14/04/2014","find Lynnette on Monday", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString2() {
		testConvertDateAndTimeString("next ASH 14/04/2014 I want to catch Pokemon 18/04/2014 !",
				"next ASH nxt Monday I want to catch Pokemon nxt Fri !", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString3() {
		testConvertDateAndTimeString("21/04/2014 I want to catch Pokemon !",
				"next nxt NXT prev Monday I want to catch Pokemon!", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString4() {
		testConvertDateAndTimeString("01/11/2014 , I watch movie in 01/12/2014",
				"1 / 11 / 2014 , I watch movie in 1 December", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString5() {
		testConvertDateAndTimeString("aaa at 11/03/2014 by 03/04/2014 23:00",
				"aaa at 11/3 by 3/4 11pm", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString6() {
		testConvertDateAndTimeString("do cs2010 assignment by 25/04/2014",
				"do cs2010 assignment by nxt nxt Wk", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString7() {
		testConvertDateAndTimeString("use calculator 570 11:11",
				"use calculator 570 11:11", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString8() {
		testConvertDateAndTimeString("use calculator 570 11/11/2015",
				"use calculator 570 11Nov 15", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString9() {
		testConvertDateAndTimeString("aaa 12/04/2014 23:59",
				"aaa tmr 11.59pm", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString10() {
		testConvertDateAndTimeString("Having CS2010 test with One May on : 17/04/2014 .",
				"Having CS2010 test with \"One May\" on :17April.", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString11() {
		testConvertDateAndTimeString("We tried to fool Ms Lee on 01/04/2015 .",
				"We tried to fool Ms Lee on April    FOOL.", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString12() {
		testConvertDateAndTimeString("We tried to fool Ms Lee on 01/04/2015 .",
				"We tried to fool Ms Lee on April FOOL day.", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString13() {
		testConvertDateAndTimeString("This 25/12/2014 , I want to stay with my family !",
				"This christmas, I want to stay with my family!", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString14() {
		testConvertDateAndTimeString("We tried to fool Christmas on 01/04/2015 .",
				"We tried to fool \"Christmas\" on April FOOL day.", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString15() {
		testConvertDateAndTimeString("We tried to fool Christmas 11/04/2014 11/04/2014 00:00 .",
				"We tried to fool \"Christmas\" tdy nw.", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString16() {
		testConvertDateAndTimeString("We tried to fool Christmas nxt 11/04/2014 11/04/2014 00:00 , 22/04/2014 .",
				"We tried to fool \"Christmas\" nxt tdy nw, nxt  nxt prev nxt Tues.", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString17() {
		testConvertDateAndTimeString("We tried to fool Christmas 11/04/2014 .",
				"We tried to fool \"Christmas\" tdy tdy tdy.", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString18() {
		testConvertDateAndTimeString("We tried to fool Christmas 11/04/2014 00:00 .",
				"We tried to fool \"Christmas\" nw nw nw.", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString19() {
		testConvertDateAndTimeString("We tried to fool Christmas nxt nxt 11/04/2014 00:00 15/04/2014 .",
				"We tried to fool \"Christmas\" nxt nxt nw Tues.", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString20() {
		testConvertDateAndTimeString("We tried to fool Christmas 22/04/2014 11/04/2014 00:00 .",
				"We tried to fool \"Christmas\" nxt nxt Tues nw.", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString21() {
		testConvertDateAndTimeString("We tried to fool Christmas 22/04/2014 nw .",
				"We tried to fool \"Christmas\" nxt nxt Tues \"nw\".", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString22() {
		testConvertDateAndTimeString("We tried to fool tdy 13/04/2014 13:00 .",
				"We tried to fool \"tdy\" tmr tmr 1pm.", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString23() {
		testConvertDateAndTimeString("We tried to fool tdy 12/04/2014 13:00 .",
				"We tried to fool \"tdy\" tmr tmr ytd 1pm.", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString24() {
		testConvertDateAndTimeString("We tried to fool tmr 12/04/2014",
				"We tried to fool \"tmr\" tmr tmr ytd", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString25() {
		testConvertDateAndTimeString("We tried to fool tmr 12/04/2014 13:00",
				"We tried to fool \"tmr\" tmr 1pm", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString26() {
		testConvertDateAndTimeString("We tried to fool tmr 12/04/2014 13:00",
				"We tried to fool \"tmr\" tmr 1.00pm", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString27() {
		testConvertDateAndTimeString("We tried to fool tmr 12/04/2014 13:00",
				"We tried to fool \"tmr\" tmr 13:00", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString28() {
		testConvertDateAndTimeString("We tried to fool 1pm 12/04/2014 13:00",
				"We tried to fool \"1pm\" tmr 1:00 pm", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString29() {
		testConvertDateAndTimeString("We tried to fool hour",
				"We tried to fool hour", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString30() {
		testConvertDateAndTimeString("We tried to fool 11/04/2014 01:00",
				"We tried to fool 1 hour", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString31() {
		testConvertDateAndTimeString("We tried to fool 11/04/2014 01:00",
				"We tried to fool next next prev hour", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString32() {
		testConvertDateAndTimeString("We tried to fool 11/04/2014 04:00",
				"We tried to fool next next 2 hour", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString33() {
		testConvertDateAndTimeString("We tried to fool 11/04/2014 01:00",
				"We tried to fool 1h", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidConvertDateAndTimeString34() {
		testConvertDateAndTimeString("We tried to fool 11/04/2014 01:00",
				"We tried to fool 1hour", "11/04/2014 00:00");
	}
	
	@Test
	public void testInvalidConvertDateAndTimeString1() {
		testExceptionConvertDateAndTimeString("Error: Cannot have odd numbers of quotes", 
				"One ppl named \"two\" want \" to have 1.", "11/04/2014 00:00");
	}
	
	private void testConvertDateAndTimeString(String expected, String input, String dateString){
		setupDebugEnvironment(dateString);
		try {
			assertEquals(expected, _datm.convertDateAndTimeString(input));
		} catch (InvalidQuotesException e) {
			fail();
		}
	}
	
	private void testExceptionConvertDateAndTimeString(String expected, String input, String dateString){
		setupDebugEnvironment(dateString);
		try {
			 _datm.convertDateAndTimeString(input);
			fail();
		} catch (InvalidQuotesException e) {
			assertEquals(expected, e.getMessage());
		}
	}
	/*above is testConvertDateAndTimeString*/
	
	/*below is to test checkDateAndTimeWithStart*/
	@Test
	public void testValidCheckDateAndTimeWithStart1() {
		testCheckDateAndTimeWithStart(null, "18/10/1993 00:00", "18/08/1994 00:00", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidCheckDateAndTimeWithStart2() {
		testCheckDateAndTimeWithStart(null, "18/10/1993 15:00", null, "11/04/2014 00:00");
	}
	
	@Test
	public void testValidCheckDateAndTimeWithStart3() {
		testCheckDateAndTimeWithStart(null, null, null, "11/04/2014 00:00");
	}
	
	@Test
	public void testValidCheckDateAndTimeWithStart4() {
		testCheckDateAndTimeWithStart("18/10/2014 00:00", null, "18/10/2014 00:00", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidCheckDateAndTimeWithStart5() {
		testCheckDateAndTimeWithStart(null, null, "18/10/2011 00:00", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidCheckDateAndTimeWithStart6() {
		testCheckDateAndTimeWithStart("18/08/2015 00:00", "18/7/2015 00:00", "18/08/2015 00:00", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidCheckDateAndTimeWithStart7() {
		testCheckDateAndTimeWithStart("", "18/10/1993 15:00", " ", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidCheckDateAndTimeWithStart8() {
		testCheckDateAndTimeWithStart("", "", " ", "11/04/2014 00:00");
	}
	
	@Test
	public void testValidCheckDateAndTimeWithStart9() {
		testCheckDateAndTimeWithStart("18/10/2014 00:00", "", "18/10/2014 00:00", "11/04/2014 00:00");
	}
	
	
	private void testCheckDateAndTimeWithStart(String expected, String startEarliest, String dateLatest, String dateString){
		setupDebugEnvironment(dateString);
		assertEquals(expected, _datm.checkDateAndTimeWithStart(startEarliest, dateLatest));
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
		testParseNumberString("111 aaa 111", "one one one aaa one one one", true);
		testParseNumberString("111 aaa 111", "one one one aaa one one one", false);
	}
	
	@Test
	public void testValidNumberString5() {
		testParseNumberString("11/11/15 11 / 11 / 15 , 11:11 12 : 00 ,", "11/11/15 11/11/15, 11:11 12:00,", true);
		testParseNumberString("11 / 11 / 15 11 : 11", "11/11/15 11:11", false);
	}
	
	@Test
	public void testValidNumberString6() {
		testParseNumberString("111 1 111", "one one one 1 one one one", true);
		testParseNumberString("111 1 111", "one one one 1 one one one", false);
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
