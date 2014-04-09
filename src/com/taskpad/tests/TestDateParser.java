//@author A0119646X

package com.taskpad.tests;


import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidDateException;

/**
 * tested all possible paths in DateParser
 * but not tested all the formats yet
 *
 */
public class TestDateParser {
	private static final String DATE_TEST = "01/04/2014 00:01";
	private static final String DATE_INVALID = "Error: Invalid Date Entered";
	private DateAndTimeManager _dateParser = DateAndTimeManager.getInstance();
	
	/*We do equivalence partitioning*/
	/*We test dates with years*/
	@Test
	public void test1() {
		testDateCommand("11/11/14", "11/11/2014");
	}

	@Test
	public void test2() {
		testDateCommand("11-11-18", "11/11/2018");
	}
	
	@Test
	public void test3() {
		testDateCommand("11.11.2015", "11/11/2015");
	}
		
	
	@Test 
	public void test4(){
		testDateCommand("Oct 18 15", "18/10/2015");
	}
	
	@Test
	public void test5(){
		testDateCommand("October 18 14", "18/10/2014");
	}
	
	@Test
	public void test6(){
		testDateCommand("Oct 18,16", "18/10/2016");
	}
	
	@Test
	public void test7(){
		testDateCommand("October 18,14", "18/10/2014");
	}
	
	@Test
	public void test8(){
		testDateCommand("Oct 18 , 14", "18/10/2014");
	}
	
	@Test
	public void test9(){
		testDateCommand("October 18 , 15", "18/10/2015");
	}
	
	@Test
	public void test10(){
		testDateCommand("Oct 18, 14", "18/10/2014");
	}
	
	@Test
	public void test11(){
		testDateCommand("October 18, 14", "18/10/2014");
	}
	
	@Test
	public void test12(){
		testDateCommand("18 Oct 16", "18/10/2016");
	}
	
	@Test
	public void test13(){
		testDateCommand("18 October 15", "18/10/2015");
	}
	
	@Test
	public void test14(){
		testDateCommand("18 Oct, 14", "18/10/2014");
	}
	
	@Test
	public void test15(){
		testDateCommand("18 October, 14", "18/10/2014");
	}
	
	@Test
	public void test16(){
		testDateCommand("18-Oct-15", "18/10/2015");
	}
	
	@Test
	public void test17(){
		testDateCommand("18-October-15", "18/10/2015");
	}
	
	@Test
	public void test18(){
		testDateCommand("18 Oct,15", "18/10/2015");
	}
	
	@Test
	public void test19(){
		testDateCommand("18 October,14", "18/10/2014");
	}
	
	@Test
	public void test20(){
		testDateCommand("18 10 , 14", "18/10/2014");
	}
	
	@Test
	public void test21(){
		testDateCommand("18 10, 16", "18/10/2016");
	}
	
	@Test
	public void test22(){
		testDateCommand("18/10/2015", "18/10/2015");
	}
	
	@Test
	public void test23(){
		testDateCommand("18-10-2015", "18/10/2015");
	}
	
	@Test
	public void test24(){
		testDateCommand("18.10.2015", "18/10/2015");
	}
	
	@Test
	public void test25(){
		testDateCommand("18Oct15", "18/10/2015");
	}
	
	@Test
	public void test26(){
		testDateCommand("1 Jun 15", "01/06/2015");
	}
	
	@Test 
	public void test27(){
		testDateCommand("1.6.15", "01/06/2015");
	}
	
	@Test
	public void test28(){
		testDateCommand("1.6.2015", "01/06/2015");
	}
	
	/*We test dates without year*/
	@Test
	public void test29(){
		testDateCommand("1/6", "01/06/2014");
	}
	
	@Test
	public void test30(){
		testDateCommand("1-6", "01/06/2014");
	}
	
	@Test
	public void test31(){
		testDateCommand("June 1", "01/06/2014");
	}
	
	@Test
	public void test32(){
		testDateCommand("1 Jun", "01/06/2014");
	}
	
	@Test
	public void test33(){
		testDateCommand("1Jun", "01/06/2014");
	}
	
	@Test
	public void test34(){
		testDateCommand("Jun1", "01/06/2014");
	}
	
	@Test
	public void test35(){
		testDateCommand("1/06", "01/06/2014");
	}
	
	@Test
	public void test36(){
		testDateCommand("1-06", "01/06/2014");
	}
	
	@Test
	public void test37(){
		testDateCommand("01/6", "01/06/2014");
	}
	
	@Test
	public void test38(){
		testDateCommand("01-6", "01/06/2014");
	}
	
	@Test
	public void test39(){
		testDateCommand("June 01", "01/06/2014");
	}
	
	@Test
	public void test40(){
		testDateCommand("01 Jun", "01/06/2014");
	}
	
	@Test
	public void test41(){
		testDateCommand("01Jun", "01/06/2014");
	}
	
	@Test
	public void test42(){
		testDateCommand("Jun01", "01/06/2014");
	}
	
	@Test
	public void test43(){
		testDateCommand("01/06", "01/06/2014");
	}
	
	@Test
	public void test44(){
		testDateCommand("01-06", "01/06/2014");
	}
	
	@Test
	public void test45(){
		testDateCommand("01-01", "01/01/2014");
	}
	
	@Test
	public void test46(){
		testDateCommand("20/03/14", "20/03/2014");
	}
	
	@Test
	public void test47(){
		testDateCommand("1 January", "01/01/2014");
	}
	
	
	
	
	/*We test invalid dates*/
	
	/*We only support date-month-year*/
	@Test
	public void invalid1(){
		testInvalidDateCommand("1993.1.6", DATE_INVALID);
	}
	
	@Test
	public void invalid2(){
		testInvalidDateCommand("1993 1 6", DATE_INVALID);
	}
	
	/*Boundary case: February have no date "30"*/
	@Test
	public void invalid3(){
		testInvalidDateCommand("30/02/15", DATE_INVALID);
	}
	
	/*We cannot support 6 digits stick together*/
	@Test
	public void invalid4() {
		testInvalidDateCommand("000000", DATE_INVALID);
	}
	
	/*And also we can't parse anything if user doesn't key in anything*/
	@Test
	public void invalid5() {
		testInvalidDateCommand("", DATE_INVALID);
	}
	
	@Test
	public void invalid6() {
		testInvalidDateCommand(" ", DATE_INVALID);
	}
	
	@Test
	public void invalid7(){
		testInvalidDateCommand(",", DATE_INVALID);
	}
	
	/*We can't parse "day" in dateparser*/
	@Test
	public void invalid8(){
		testInvalidDateCommand("Wed", DATE_INVALID);
	}
	
	@Test
	public void invalid9(){
		testInvalidDateCommand("Today", DATE_INVALID);
	}
	
	@Test
	public void invalid10(){
		testInvalidDateCommand("Oct1815", DATE_INVALID);
	}
	
	/*We cannot support 6 digits stick together*/
	@Test
	public void invalid11(){
		testInvalidDateCommand("18102015", DATE_INVALID);
	}
	
	@Test
	public void invalid12(){
		testInvalidDateCommand("15/18/10", DATE_INVALID);
	}
	
	@Test
	public void invalid14(){
		testInvalidDateCommand("40-18-10", DATE_INVALID);
	}
	
	@Test
	public void invalid15(){
		testInvalidDateCommand("40.18.10", DATE_INVALID);
	}
	
	@Test
	public void invalid16(){
		testInvalidDateCommand("40 18 10", DATE_INVALID);
	}
	
	@Test
	public void invalid17(){
		testInvalidDateCommand("40-18-Oct", DATE_INVALID);
	}
	
	@Test
	public void invalid18(){
		testInvalidDateCommand("16/10/Oct", DATE_INVALID);
	}
	
	@Test
	public void invalid19(){
		testInvalidDateCommand("15/10/oct", DATE_INVALID);
	}
	
	@Test
	public void invalid20(){
		testInvalidDateCommand("35.18.10", DATE_INVALID);
	}
	
	@Test
	public void invalid21(){
		testInvalidDateCommand("49 18 10", DATE_INVALID);
	}

	/*boundary case: when it is null*/
	@Test
	public void invalid22(){
		testInvalidDateCommand(null, DATE_INVALID);
	}
	
	@Test
	public void invalid23(){
		testInvalidDateCommand("100 October,14", DATE_INVALID);
	}
	
	@Test
	public void invalid24(){
		testInvalidDateCommand("1 1", DATE_INVALID);
	}
	
	@Test
	public void invalid25(){
		testInvalidDateCommand("18 10 15", DATE_INVALID);
	}
	
	@Test
	public void invalid26(){
		testInvalidDateCommand("01.06", DATE_INVALID);
	}
	
	@Test
	public void invalid27(){
		testInvalidDateCommand("01 06", DATE_INVALID);
	}
	
	/*test those exceptions*/
	@Test
	public void invalid28(){
		testInvalidDateCommand("01 02 2015", DATE_INVALID);
	}
	
	@Test
	public void invalid29(){
		testInvalidDateCommand("01.6", DATE_INVALID);
	}
	
	@Test
	public void invalid30(){
		testInvalidDateCommand("01 6", DATE_INVALID);
	}
	
	@Test
	public void invalid31(){
		testInvalidDateCommand("1.06", DATE_INVALID);
	}
	
	@Test
	public void invalid32(){
		testInvalidDateCommand("1 06", DATE_INVALID);
	}
	
	@Test
	public void invalid33(){
		testInvalidDateCommand("01 12", DATE_INVALID);
	}
	
	@Test
	public void invalid34(){
		testInvalidDateCommand("1.6", DATE_INVALID);
	}
	
	@Test
	public void invalid35(){
		testInvalidDateCommand("1 6", DATE_INVALID);
	}
	
	@Test
	public void invalid36(){
		testInvalidDateCommand("1 6 15", DATE_INVALID);
	}
	
	private void testDateCommand(String input, String expected){
		setupDebug();
		try {
			assertEquals(_dateParser.parseDate(input), expected);
		} catch (InvalidDateException e) {
			fail();
		}
	}

	/**
	 * 
	 */
	private void setupDebug() {
		try {
			_dateParser.setDebug(DATE_TEST);
		} catch (ParseException e1) {
			fail();
		}
	}
	
	private void testInvalidDateCommand(String input, String expected){
		setupDebug();
		try{
			_dateParser.parseDate(input);
			fail();
		} catch (InvalidDateException e){
			assertEquals(e.getMessage(), expected);
		}
	}
}
