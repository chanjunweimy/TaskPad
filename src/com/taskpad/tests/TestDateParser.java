package com.taskpad.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidDateException;

public class TestDateParser {
	private DateAndTimeManager _dateParser = DateAndTimeManager.getInstance();
	
	@Test
	public void test1() {
		testDateCommand("11/11/94", "11/11/1994");
	}

	@Test
	public void test2() {
		testDateCommand("11-11-08", "11/11/2008");
	}
	
	@Test
	public void test3() {
		testDateCommand("11.11.2000", "11/11/2000");
	}
	
	@Test
	public void test4(){
		testDateCommand("18 10 93", "18/10/1993");
	}
	
	@Test 
	public void test5(){
		testDateCommand("Oct 18 93", "18/10/1993");
	}
	
	@Test
	public void test6(){
		testDateCommand("October 18 93", "18/10/1993");
	}
	
	@Test
	public void test7(){
		testDateCommand("Oct 18,93", "18/10/1993");
	}
	
	@Test
	public void test8(){
		testDateCommand("October 18,93", "18/10/1993");
	}
	
	@Test
	public void test9(){
		testDateCommand("Oct 18 , 93", "18/10/1993");
	}
	
	@Test
	public void test10(){
		testDateCommand("October 18 , 93", "18/10/1993");
	}
	
	@Test
	public void test11(){
		testDateCommand("Oct 18, 93", "18/10/1993");
	}
	
	@Test
	public void test12(){
		testDateCommand("October 18, 93", "18/10/1993");
	}
	
	@Test
	public void test13(){
		testDateCommand("18 Oct 93", "18/10/1993");
	}
	
	@Test
	public void test14(){
		testDateCommand("18 October 93", "18/10/1993");
	}
	
	@Test
	public void test15(){
		testDateCommand("18 Oct, 93", "18/10/1993");
	}
	
	@Test
	public void test16(){
		testDateCommand("18 October, 93", "18/10/1993");
	}
	
	@Test
	public void test17(){
		testDateCommand("18-Oct-93", "18/10/1993");
	}
	
	@Test
	public void test18(){
		testDateCommand("18-October-93", "18/10/1993");
	}
	
	@Test
	public void test19(){
		testDateCommand("18 Oct,93", "18/10/1993");
	}
	
	@Test
	public void test20(){
		testDateCommand("18 October,93", "18/10/1993");
	}
	
	@Test
	public void test21(){
		testDateCommand("18 10 , 93", "18/10/1993");
	}
	
	@Test
	public void test22(){
		testDateCommand("18 10, 93", "18/10/1993");
	}
	
	@Test
	public void test32(){
		testDateCommand("18/10/2015", "18/10/2015");
	}
	
	@Test
	public void test33(){
		testDateCommand("18-10-2015", "18/10/2015");
	}
	
	@Test
	public void test34(){
		testDateCommand("18.10.2015", "18/10/2015");
	}
	
	@Test
	public void test36(){
		testDateCommand("18Oct15", "18/10/2015");
	}
	
	@Test
	public void test38(){
		testDateCommand("1 6 15", "01/06/2015");
	}
	
	@Test
	public void test39(){
		testDateCommand("1 Jun 15", "01/06/2015");
	}
	
	@Test 
	public void test40(){
		testDateCommand("1.6.15", "01/06/2015");
	}
	
	@Test
	public void test41(){
		testDateCommand("1.6.2015", "01/06/2015");
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
	public void test28(){
		testDateCommand("93/10/Oct", "10/10/1993");
	}
	
	@Test
	public void test29(){
		testDateCommand("93/10/oct", "10/10/1993");
	}
	
	@Test
	public void test30(){
		testDateCommand("93.18.10", "18/10/1993");
	}
	
	@Test
	public void test31(){
		testDateCommand("93 18 10", "18/10/1993");
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
