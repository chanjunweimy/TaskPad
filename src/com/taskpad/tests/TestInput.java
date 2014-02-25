package com.taskpad.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.input.Command;
import com.taskpad.input.Command.CommandType;


public class TestInput {
	//Command command = new Command();
	
	//testing Add.java
	@Test
	public void testAdd() {
		
	}
	
	//testing Command.java
	@Test
	public void testCommand() {
		new Command();
		
		//invalid
		testFindValueCommand("Find null", CommandType.INVALID, "");
		testFindValueCommand("Find abv", CommandType.INVALID, "");
		
		//valid commands
		testFindValueCommand("Find add", CommandType.ADD, "add");
		testFindValueCommand("Find NEw", CommandType.ADD, "NEw");
		testFindValueCommand("Find Create", CommandType.ADD, "Create");
		testFindValueCommand("Find with white spaces", CommandType.ADD, "add ");
		
		testFindValueCommand("Find delete", CommandType.DELETE, "delete");
		testFindValueCommand("Find with enter", CommandType.DELETE, "   del\n");
		testFindValueCommand("Find with front whites", CommandType.DELETE, "   del");
		
		testFindValueCommand("Find add info", CommandType.ADD_INFO, "ADDINFO");
		
		testFindValueCommand("Find done", CommandType.DONE, "done");
		
		testFindValueCommand("Find clr", CommandType.CLEAR_ALL, "clr");
		
		testFindValueCommand("Find edit", CommandType.EDIT, "edit");
		
		testFindValueCommand("Find undo", CommandType.UNDO, "undo");
		
		testFindValueCommand("Find find", CommandType.SEARCH, "find");
		
		testFindValueCommand("Find ls", CommandType.LIST, "ls");
		
		testFindValueCommand("Find hlp", CommandType.HELP, "HELP");
		
		testFindValueCommand("Find quit", CommandType.EXIT, "quIT");
	}
	
	private void testFindValueCommand (String description, Command.CommandType expected, String input){
		assertEquals(description, expected, Command.find(input));
	}

}
