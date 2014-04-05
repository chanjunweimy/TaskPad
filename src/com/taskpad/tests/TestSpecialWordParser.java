package com.taskpad.tests;

//@author A0112084U

/**
 * For testing special word and time word parser
 * @author Jun & Lynnette
 */

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.DatePassedException;
import com.taskpad.dateandtime.InvalidDayException;
import com.taskpad.dateandtime.NullTimeUnitException;
import com.taskpad.dateandtime.NullTimeValueException;

public class TestSpecialWordParser {

	//private static final String MESSAGE_INVALID_TIME = "Error: Invalid time entered";	
	private static final String MESSAGE_INVALID_DAY = "Error: Invalid Day";

	private DateAndTimeManager _specialWordParser = DateAndTimeManager.getInstance();
	
	//day parser part
	@Test
	public void validTestDay1() {
		testWordCommand("24/03/2014", "MOND", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestDay2() {
		testWordCommand("07/04/2014", "NXT NXT MONDay", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestDay3() {
		testWordCommand("07/04/2014", "NXT NXT NXT PREV MONda", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestDay4() {
		testWordCommand("30/03/2014", "NXT WK", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestDay5() {
		testWordCommand("23/03/2014", "THIS WK", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestDay6() {
		testWordCommand("24/03/2014", "THIS MON", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestDay7() {
		testWordCommand("24/03/2014", "TMR", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestDay8() {
		testWordCommand("26/03/2014", "TMR TMR TMR", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestDay9() {
		testWordCommand("25/03/2014", "tmr tmr TOMORRO TOM TOMORROW YTD YEST YESTERDAY", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestDay10(){
		testWordCommand("08/04/2014", "next Next Prev Next Tues", "23/03/2014 00:01");
	}
	
	/**
	 * Time Word Parser
	 */
	
	//For hours
	@Test
	public void validTestTimeWord1(){
		testTimeWordCommand("23/03/2014 01:01", "1h", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestTimeWord2(){
		testTimeWordCommand("23/03/2014 01:01", "next hour", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestTimeWord3(){
		testTimeWordCommand("23/03/2014 00:01", "next previous hour", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestTimeWord4(){
		testTimeWordCommand("23/03/2014 01:01", "next 1 hour", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestTimeWord5(){
		testTimeWordCommand("23/03/2014 02:01", "next next 1 hour", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestTimeWord6(){
		testTimeWordCommand("23/03/2014 04:01", "next next 2 hours", "23/03/2014 00:01");
	}
	
	//For minutes
	@Test
	public void validTestTimeWord7(){
		testTimeWordCommand("23/03/2014 00:01", "next previous min", "23/03/2014 00:01");
	}
	
	@Test
	public void validTestTimeWord8(){
		testTimeWordCommand("23/03/2014 01:01", "next next 30 min", "23/03/2014 00:01");
	}
	
	@Test
	public void invalidTest1() {
		testInvalidWordCommand(MESSAGE_INVALID_DAY, "MAN", "23/3/2014 00:01");
	}
	
	@Test
	public void invalidTest2() {
		testInvalidWordCommand(MESSAGE_INVALID_DAY, "WEEK", "23/03/2014 00:01");
	}
	
	//We do not support Yesterday
	@Test
	public void invalidTest3(){
		testInvalidWordCommand(MESSAGE_INVALID_DAY, "Yesterday", "23/03/2014 00:01");
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
			System.err.println(_specialWordParser.parseDayToDate(input));
			fail();
		} catch (InvalidDayException | DatePassedException e) {
			assertEquals(e.getMessage(), expected);
		}
	}
	
	private void testTimeWordCommand(String expected, String input, String dateString){
		setupDebugEnvironment(dateString);
		try {
			assertEquals(expected,_specialWordParser.parseTimeWord(input));
		} catch (NullTimeUnitException | NullTimeValueException e) {
			fail();
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
