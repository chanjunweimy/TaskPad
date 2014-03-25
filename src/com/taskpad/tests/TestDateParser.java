package com.taskpad.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidDateException;

public class TestDateParser {
	private DateAndTimeManager _dateParser = DateAndTimeManager.getInstance();
	
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
		testDateCommand("18 10 15", "18/10/2015");
	}
	
	@Test 
	public void test5(){
		testDateCommand("Oct 18 15", "18/10/2015");
	}
	
	@Test
	public void test6(){
		testDateCommand("October 18 14", "18/10/2014");
	}
	
	@Test
	public void test7(){
		testDateCommand("Oct 18,16", "18/10/2016");
	}
	
	@Test
	public void test8(){
		testDateCommand("October 18,14", "18/10/2014");
	}
	
	@Test
	public void test9(){
		testDateCommand("Oct 18 , 14", "18/10/2014");
	}
	
	@Test
	public void test10(){
		testDateCommand("October 18 , 15", "18/10/2015");
	}
	
	@Test
	public void test11(){
		testDateCommand("Oct 18, 14", "18/10/2014");
	}
	
	@Test
	public void test12(){
		testDateCommand("October 18, 14", "18/10/2014");
	}
	
	@Test
	public void test13(){
		testDateCommand("18 Oct 16", "18/10/2016");
	}
	
	@Test
	public void test14(){
		testDateCommand("18 October 15", "18/10/2015");
	}
	
	@Test
	public void test15(){
		testDateCommand("18 Oct, 14", "18/10/2014");
	}
	
	@Test
	public void test16(){
		testDateCommand("18 October, 14", "18/10/2014");
	}
	
	@Test
	public void test17(){
		testDateCommand("18-Oct-15", "18/10/2015");
	}
	
	@Test
	public void test18(){
		testDateCommand("18-October-15", "18/10/2015");
	}
	
	@Test
	public void test19(){
		testDateCommand("18 Oct,15", "18/10/2015");
	}
	
	@Test
	public void test20(){
		testDateCommand("18 October,14", "18/10/2014");
	}
	
	@Test
	public void test21(){
		testDateCommand("18 10 , 14", "18/10/2014");
	}
	
	@Test
	public void test22(){
		testDateCommand("18 10, 16", "18/10/2016");
	}
	
	@Test
	public void test23(){
		testDateCommand("18/10/2015", "18/10/2015");
	}
	
	@Test
	public void test24(){
		testDateCommand("18-10-2015", "18/10/2015");
	}
	
	@Test
	public void test25(){
		testDateCommand("18.10.2015", "18/10/2015");
	}
	
	@Test
	public void test26(){
		testDateCommand("18Oct15", "18/10/2015");
	}
	
	@Test
	public void test27(){
		testDateCommand("1 6 15", "01/06/2015");
	}
	
	@Test
	public void test28(){
		testDateCommand("1 Jun 15", "01/06/2015");
	}
	
	@Test 
	public void test29(){
		testDateCommand("1.6.15", "01/06/2015");
	}
	
	@Test
	public void test30(){
		testDateCommand("1.6.2015", "01/06/2015");
	}
	
	@Test
	public void test31(){
		testDateCommand("1/6", "01/06/2014");
	}
	
	@Test
	public void test32(){
		testDateCommand("1-6", "01/06/2014");
	}
	
	@Test
	public void test33(){
		testDateCommand("1.6", "01/06/2014");
	}
	
	@Test
	public void test34(){
		testDateCommand("1 6", "01/06/2014");
	}
	
	@Test
	public void test35(){
		testDateCommand("June 1", "01/06/2014");
	}
	
	@Test
	public void test36(){
		testDateCommand("1 Jun", "01/06/2014");
	}
	
	@Test
	public void test37(){
		testDateCommand("1Jun", "01/06/2014");
	}
	
	@Test
	public void test38(){
		testDateCommand("Jun1", "01/06/2014");
	}
	
	@Test
	public void test39(){
		testDateCommand("01 12", "01/12/2014");
	}
	
	@Test
	public void test40(){
		testDateCommand("1/06", "01/06/2014");
	}
	
	@Test
	public void test41(){
		testDateCommand("1-06", "01/06/2014");
	}
	
	@Test
	public void test42(){
		testDateCommand("1.06", "01/06/2014");
	}
	
	@Test
	public void test43(){
		testDateCommand("1 06", "01/06/2014");
	}
	
	@Test
	public void test44(){
		testDateCommand("01/6", "01/06/2014");
	}
	
	@Test
	public void test45(){
		testDateCommand("01-6", "01/06/2014");
	}
	
	@Test
	public void test46(){
		testDateCommand("01.6", "01/06/2014");
	}
	
	@Test
	public void test47(){
		testDateCommand("01 6", "01/06/2014");
	}
	
	@Test
	public void test48(){
		testDateCommand("June 01", "01/06/2014");
	}
	
	@Test
	public void test49(){
		testDateCommand("01 Jun", "01/06/2014");
	}
	
	@Test
	public void test50(){
		testDateCommand("01Jun", "01/06/2014");
	}
	
	@Test
	public void test51(){
		testDateCommand("Jun01", "01/06/2014");
	}
	
	@Test
	public void test52(){
		testDateCommand("01/06", "01/06/2014");
	}
	
	@Test
	public void test53(){
		testDateCommand("01-06", "01/06/2014");
	}
	
	@Test
	public void test54(){
		testDateCommand("01.06", "01/06/2014");
	}
	
	@Test
	public void test55(){
		testDateCommand("01 06", "01/06/2014");
	}
	
	@Test
	public void invalid1(){
		testInvalidDateCommand("1993.1.6", "Not a valid date");
	}
	
	@Test
	public void invalid2(){
		testInvalidDateCommand("1993 1 6", "Not a valid date");
	}
	
	
	/**
	 * we no longer support this 2 special cases
	 */
	@Test
	public void invalid3(){
		testInvalidDateCommand("30/02/15", "Not a valid date");
	}
	
	@Test
	public void invalid4() {
		testInvalidDateCommand("000000", "Not a valid date");
	}
	
	@Test
	public void invalid5() {
		testInvalidDateCommand("", "Not a valid date");
	}
	
	@Test
	public void invalid6() {
		testInvalidDateCommand(" ", "Not a valid date");
	}
	
	@Test
	public void invalid7(){
		testInvalidDateCommand(",", "Not a valid date");
	}
	
	@Test
	public void invalid8(){
		testInvalidDateCommand("Wed", "Not a valid date");
	}
	
	@Test
	public void invalid9(){
		testInvalidDateCommand("Today", "Not a valid date");
	}
	
	@Test
	public void invalid10(){
		testInvalidDateCommand("Oct1815", "Not a valid date");
	}
	
	@Test
	public void invalid11(){
		testInvalidDateCommand("18102015", "Not a valid date");
	}
	
	@Test
	public void invalid12(){
		testInvalidDateCommand("15/18/10", "Not a valid date");
	}
	
	@Test
	public void invalid13(){
		testInvalidDateCommand("20/03/14", "Not a valid date");
	}
	
	@Test
	public void invalid14(){
		testInvalidDateCommand("40-18-10", "Not a valid date");
	}
	
	@Test
	public void invalid15(){
		testInvalidDateCommand("40.18.10", "Not a valid date");
	}
	
	@Test
	public void invalid16(){
		testInvalidDateCommand("40 18 10", "Not a valid date");
	}
	
	@Test
	public void invalid27(){
		testInvalidDateCommand("40-18-Oct", "Not a valid date");
	}
	
	@Test
	public void invalid28(){
		testInvalidDateCommand("16/10/Oct", "Not a valid date");
	}
	
	@Test
	public void invalid29(){
		testInvalidDateCommand("15/10/oct", "Not a valid date");
	}
	
	@Test
	public void invalid30(){
		testInvalidDateCommand("35.18.10", "Not a valid date");
	}
	
	@Test
	public void invalid31(){
		testInvalidDateCommand("49 18 10", "Not a valid date");
	}
	
	@Test
	public void invalid32(){
		testInvalidDateCommand(null, "Not a valid date");
	}
	
	
	private void testDateCommand(String input, String expected){
		try {
			assertEquals(_dateParser.parseDate(input), expected);
		} catch (InvalidDateException e) {
			fail();
		}
	}
	
	private void testInvalidDateCommand(String input, String expected){
		try{
			_dateParser.parseDate(input);
		} catch (InvalidDateException e){
			assertEquals(e.getMessage(), expected);
		}
	}
}
