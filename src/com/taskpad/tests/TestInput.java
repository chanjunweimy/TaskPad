package com.taskpad.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import com.taskpad.input.CommandQueue;
import com.taskpad.input.CommandTypes;
import com.taskpad.input.CommandTypes.CommandType;
import com.taskpad.input.InputManager;


public class TestInput {

	private final ByteArrayOutputStream _outContent = new ByteArrayOutputStream();
	
	//testing Command.java
	@Test
	public void testCommand() {
		new CommandTypes();
		
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
	
	@Test
	public void testAdd(){
		new CommandTypes();
		
		setUpStream();
		
		testFindValueCommand("Add1", CommandType.ADD, "Add");
		
		testInputString("CATEGORY \r\nSTART TIME \r\nEND TIME \r\nDEADLINE 23/03/2014\r\nVENUE "
				+ "\r\nSTART DATE \r\nDESC homework to complete\r\nEND DATE " , "add homework to complete -d 23/03/2014");
		
		//testFindValueCommand("Add2", CommandType.ADD, "hello add");
		//testFindValueCommand("Add3", CommandType.ADD, "add homework to complete -d 23/03/2014");
		//testFindValueCommand("Add4", CommandType.ADD, "add -d 23/03/2014 \"complete homework\"");
	}
	
	private void testFindValueCommand (String description, CommandTypes.CommandType expected, String input){
		assertEquals(description, expected, CommandQueue.find(input));
	}
	
	private void testInputString(String expected, String input){
		InputManager.setDebug(true);
		//assertEquals(description, expected, InputManager.receiveFromGui(input));
		InputManager.receiveFromGui(input);
		assertEquals(expected + "\r\n", _outContent.toString());
		cleanUpStreams();
	}
	
	private void setUpStream(){
		System.setOut(new PrintStream(_outContent));
	}
	
	private void cleanUpStreams(){
		_outContent.reset();
	}

}
