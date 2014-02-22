package com.taskpad.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.inputproc.Command;
import com.taskpad.inputproc.Command.CommandType;


public class TestInput {
	Command command = new Command();
	
	@Test
	public void testFind() {
		testFindValueCommand("Find null", CommandType.INVALID, "");
		testFindValueCommand("Find add", CommandType.ADD, "add");
		testFindValueCommand("Find with white spaces", CommandType.ADD, "add ");
		testFindValueCommand("Find with front whites", CommandType.DELETE, "   del");
	}
	
	private void testFindValueCommand (String description, Command.CommandType expected, String input){
		assertEquals(description, expected, Command.find(input));
	}

}
