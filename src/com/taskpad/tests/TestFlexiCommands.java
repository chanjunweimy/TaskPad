package com.taskpad.tests;

/**
 * This class is to test the commands not as the first word
 * @author Lynnette
 */

import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.input.CommandQueue;
import com.taskpad.input.CommandTypes;
import com.taskpad.input.CommandTypes.CommandType;

public class TestFlexiCommands {

	/**
	 * Test add in between words
	 */
	@Test
	public void testAdd() {
		testFindValueCommand(CommandType.ADD, "do CS2101 homework add ");
		testFindValueCommand(CommandType.ADD, "  add  ");
		testFindValueCommand(CommandType.ADD, "do add CS2101 homework");
		
		testFindValueCommand(CommandType.ADD, "do new CS2101 homework");
		testFindValueCommand(CommandType.ADD, "do create CS2101 homework");
		testFindValueCommand(CommandType.ADD, "do insert CS2101 homework");
	}
	
	
	private void testFindValueCommand (CommandTypes.CommandType expected, String input){
		new CommandTypes();
		assertEquals(expected, CommandQueue.findFlexi(input));
	}

}
