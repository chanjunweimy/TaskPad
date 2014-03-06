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

}
