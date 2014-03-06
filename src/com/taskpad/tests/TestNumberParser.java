package com.taskpad.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.timeanddate.NumberParser;

public class TestNumberParser {

	private NumberParser _parseNumWord = new NumberParser();
	private String _description = "";

	@Test
	public void test1() {
		_description = "base case";
		assertEquals(_description, _parseNumWord.parseTheNumbers("one"),"1");
	}
	
	@Test
	public void test2() {
		_description = "case insensitive";
		assertEquals(_description, _parseNumWord.parseTheNumbers("ONe"),"1");
	}
	
	@Test
	public void test3() {
		_description = "case insensitive and can accept words like twenty";
		assertEquals(_description, _parseNumWord.parseTheNumbers("Twenty one"),"21");
	}
	
	@Test
	public void test4() {
		_description = "can accept some broken English";
		assertEquals(_description, _parseNumWord.parseTheNumbers("One ONe"),"11");
	}

	@Test
	public void test5() {
		_description = "can accept some broken English";
		assertEquals(_description, _parseNumWord.parseTheNumbers("One ONe ONE"),"111");
	}
	
	@Test
	public void test6() {
		_description = "can accept many spaces";
		assertEquals(_description, _parseNumWord.parseTheNumbers("One    ONE"),"11");
	}
	
	@Test
	public void test7() {
		_description = "can handle error: spaces only";
		assertEquals(_description, _parseNumWord.parseTheNumbers("  "),null);
	}
	
	@Test
	public void test8() {
		_description = "can handle error: empty string";
		assertEquals(_description, _parseNumWord.parseTheNumbers(""),null);
	}
	
	@Test
	public void test9() {
		_description = "can handle error: no such number";
		assertEquals(_description, _parseNumWord.parseTheNumbers("gfg"),null);
	}
	
	@Test
	public void test10() {
		_description = "only support English, can't support \"one\" in chinese";
		assertEquals(_description, _parseNumWord.parseTheNumbers("一"),null);
	}
	
	@Test
	public void test11() {
		_description = "only support English, can't support \"one\" in malay";
		assertEquals(_description, _parseNumWord.parseTheNumbers("satu"),null);
	}
}
