package com.taskpad.tests;

/**
 * This class is to test the input object passed to executor
 * @author Lynnette
 */

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import com.taskpad.input.InputManager;

public class TestInput {

	private final ByteArrayOutputStream _outContent = new ByteArrayOutputStream();
	
	@Test
	public void testAdd(){	
		setUpStream();
		
		testInputString("CATEGORY \r\nSTART TIME \r\nEND TIME \r\nDEADLINE 23/03/2014\r\nVENUE "
				+ "\r\nSTART DATE \r\nDESC homework to complete\r\nEND DATE " , "add homework to complete -d 23/03/2014");
		
		//testFindValueCommand("Add2", CommandType.ADD, "hello add");
		//testFindValueCommand("Add3", CommandType.ADD, "add homework to complete -d 23/03/2014");
		//testFindValueCommand("Add4", CommandType.ADD, "add -d 23/03/2014 \"complete homework\"");
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
