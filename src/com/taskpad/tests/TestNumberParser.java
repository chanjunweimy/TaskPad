package com.taskpad.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;


public class TestNumberParser {

	//private NumberParser _parseNumWord = new NumberParser();
	private String _description = "";

	@Test
	public void test1() {
		_description = "base case";
		assertEquals(_description, DateAndTimeManager.parseNumber("one"),"1");
	}
	
	@Test
	public void test2() {
		_description = "case insensitive";
		assertEquals(_description, DateAndTimeManager.parseNumber("ONe"),"1");
	}
	
	@Test
	public void test3() {
		_description = "case insensitive and can accept words like twenty";
		assertEquals(_description, DateAndTimeManager.parseNumber("Twenty one"),"21");
	}
	
	@Test
	public void test4() {
		_description = "can accept some broken English";
		assertEquals(_description, DateAndTimeManager.parseNumber("One ONe"),"11");
	}

	@Test
	public void test5() {
		_description = "can accept some broken English";
		assertEquals(_description, DateAndTimeManager.parseNumber("One ONe ONE"),"111");
	}
	
	@Test
	public void test6() {
		_description = "can accept many spaces";
		assertEquals(_description, DateAndTimeManager.parseNumber("One    ONE"),"11");
	}
	
	@Test
	public void test7() {
		_description = "can handle error: spaces only";
		assertEquals(_description, DateAndTimeManager.parseNumber("  "),null);
	}
	
	@Test
	public void test8() {
		_description = "can handle error: empty string";
		assertEquals(_description, DateAndTimeManager.parseNumber(""),null);
	}
	
	@Test
	public void test9() {
		_description = "can handle error: no such number";
		assertEquals(_description, DateAndTimeManager.parseNumber("gfg"),null);
	}
	
	@Test
	public void test10() {
		_description = "only support English, can't support \"one\" in chinese";
		assertEquals(_description, DateAndTimeManager.parseNumber("一"),null);
	}
	
	@Test
	public void test11() {
		_description = "only support English, can't support \"one\" in malay";
		assertEquals(_description, DateAndTimeManager.parseNumber("satu"),null);
	}
}
