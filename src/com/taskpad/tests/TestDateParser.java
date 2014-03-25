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
	public void test23(){
		testDateCommand("93/18/10", "18/10/1993");
	}
	
	@Test
	public void test24(){
		testDateCommand("93-18-10", "18/10/1993");
	}
	
	@Test
	public void test25(){
		testDateCommand("93.18.10", "18/10/1993");
	}
	
	@Test
	public void test26(){
		testDateCommand("93 18 10", "18/10/1993");
	}
	
	@Test
	public void test27(){
		testDateCommand("93-18-Oct", "18/10/1993");
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
	
	@Test
	public void test32(){
		testDateCommand("18/10/1993", "18/10/1993");
	}
	
	@Test
	public void test33(){
		testDateCommand("18-10-1993", "18/10/1993");
	}
	
	@Test
	public void test34(){
		testDateCommand("18.10.1993", "18/10/1993");
	}
	
	@Test
	public void test35(){
		testDateCommand("18101993", "18/10/1993");
	}
	
	@Test
	public void test36(){
		testDateCommand("18Oct93", "18/10/1993");
	}
	
	@Test
	public void test37(){
		testDateCommand("Oct1893", "18/10/1993");
	}
	
	@Test
	public void test38(){
		testDateCommand("1 6 93", "01/06/1993");
	}
	
	@Test
	public void test39(){
		testDateCommand("1 Jun 93", "01/06/1993");
	}
	
	@Test 
	public void test40(){
		testDateCommand("1.6.93", "01/06/1993");
	}
	
	@Test
	public void test41(){
		testDateCommand("1.6.1993", "01/06/1993");
	}
	
	@Test
	public void test42(){
		testDateCommand("1993.1.6", "01/06/1993");
	}
	
	@Test
	public void test43(){
		testDateCommand("1993 1 6", "01/06/1993");
	}
	
	@Test
	public void special1(){
		testDateCommand("30/02/93", "02/03/1993");
	}
	
	@Test
	public void special2() {
		testDateCommand("000000", "30/11/1999");
	}
	
	@Test
	public void invalid1() {
		testInvalidDateCommand("", "Not a valid date");
	}
	
	@Test
	public void invalid2() {
		testInvalidDateCommand(" ", "Not a valid date");
	}
	
	@Test
	public void invalid3(){
		testInvalidDateCommand(",", "Not a valid date");
	}
	
	@Test
	public void invalid4(){
		testInvalidDateCommand("Wed", "Not a valid date");
	}
	
	@Test
	public void invalid5(){
		testInvalidDateCommand("Today", "Not a valid date");
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
			assertEquals(e.getMessage(), "Not a valid date");
		}
	}
}
