package com.taskpad.tests;

/**
 * This class is to test the command words
 * @author Lynnette
 */

import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.input.CommandQueue;
import com.taskpad.input.CommandTypes;
import com.taskpad.input.CommandTypes.CommandType;

public class TestCommands {
	
	/**
	 * Testing Invalid commands
	 */
	
	@Test
	public void testCommandInvalid(){
		testFindValueCommand(CommandType.INVALID, "");
		testFindValueCommand(CommandType.INVALID, " ");
		testFindValueCommand(CommandType.INVALID, "hello");
		testFindValueCommand(CommandType.INVALID, "HELLO");
		testFindValueCommand(CommandType.INVALID, "\n");
	}
	
	/**
	 * Testing add commands
	 */
	@Test
	public void testCommandAdd(){
		testFindValueCommand(CommandType.ADD, "add");
		testFindValueCommand(CommandType.ADD, "ADD");
		testFindValueCommand(CommandType.ADD, "aDd");
		testFindValueCommand(CommandType.ADD, "aDD");
		
		testFindValueCommand(CommandType.ADD, "new");
		testFindValueCommand(CommandType.ADD, "NEW");
		testFindValueCommand(CommandType.ADD, "neW");
		testFindValueCommand(CommandType.ADD, "NeW");

		testFindValueCommand(CommandType.ADD, "create");
		testFindValueCommand(CommandType.ADD, "CREATE");
		testFindValueCommand(CommandType.ADD, "cReaTe");
		testFindValueCommand(CommandType.ADD, "cREATe");

		testFindValueCommand(CommandType.ADD, "insert");
		testFindValueCommand(CommandType.ADD, "INSERT");
		testFindValueCommand(CommandType.ADD, "iNseRt");
		testFindValueCommand(CommandType.ADD, "InSErT");
	}
	
	/**
	 * Testing add info command
	 */
	
	@Test
	public void testCommandAddInfo(){
		testFindValueCommand(CommandType.ADD_INFO, "ADDINFO");
		testFindValueCommand(CommandType.ADD_INFO, "addinfo");
		testFindValueCommand(CommandType.ADD_INFO, "addINFO");

		testFindValueCommand(CommandType.ADD_INFO, "INFO");
		testFindValueCommand(CommandType.ADD_INFO, "info");
		testFindValueCommand(CommandType.ADD_INFO, "INfo");

		testFindValueCommand(CommandType.ADD_INFO, "INFORMATION");
		testFindValueCommand(CommandType.ADD_INFO, "INFORMATIN");
		testFindValueCommand(CommandType.ADD_INFO, "INFORMATN");

		testFindValueCommand(CommandType.ADD_INFO, "ADDDESC");
		
		testFindValueCommand(CommandType.ADD_INFO, "CREATEDESC");
		
		testFindValueCommand(CommandType.ADD_INFO, "CREATEINFO");

	}
	
	/**
	 * Test add reminder
	 */
	@Test
	public void testCommandAddRem(){
		testFindValueCommand(CommandType.ADD_REM, "ADDR");
		
		testFindValueCommand(CommandType.ADD_REM, "REMIND");

		testFindValueCommand(CommandType.ADD_REM, "REMINDER");

		testFindValueCommand(CommandType.ADD_REM, "REMAINDER");
		testFindValueCommand(CommandType.ADD_REM, "ADDREM");

	}
	
	/**
	 * Test alarm
	 */
	@Test
	public void testCommandAlarm(){
		testFindValueCommand(CommandType.ALARM, "ALARM");
		
		testFindValueCommand(CommandType.ALARM, "ADDALARM");
		
		testFindValueCommand(CommandType.ALARM, "SETALARM");

		testFindValueCommand(CommandType.ALARM, "CREATEALARM");
		
		testFindValueCommand(CommandType.ALARM, "RING");
		
		testFindValueCommand(CommandType.ALARM, "SETTIMER");
	}
	
	/**
	 * Test stop alarm
	 */
	@Test
	public void testCommandStop(){
		testFindValueCommand(CommandType.STOP, "STOP");
		testFindValueCommand(CommandType.STOP, "STOPP");
		testFindValueCommand(CommandType.STOP, "STO");
	}
	
	/**
	 * Test clear data
	 */
	@Test
	public void testCommandClear(){
		testFindValueCommand(CommandType.CLEAR_ALL, "CLEAR");
		testFindValueCommand(CommandType.CLEAR_ALL, "CLR");
		testFindValueCommand(CommandType.CLEAR_ALL, "CLC");
		testFindValueCommand(CommandType.CLEAR_ALL, "CLEAN");
		testFindValueCommand(CommandType.CLEAR_ALL, "CLEARALL");
	}
	
	/**
	 * Test clear screen
	 */
	@Test
	public void testCommandClearScr(){
		testFindValueCommand(CommandType.CLEAR_SCREEN, "CLCSR");
		testFindValueCommand(CommandType.CLEAR_SCREEN, "CLCSCR");
		testFindValueCommand(CommandType.CLEAR_SCREEN, "SCREEN");
		testFindValueCommand(CommandType.CLEAR_SCREEN, "CLEARSCR");
		testFindValueCommand(CommandType.CLEAR_SCREEN, "CLEARSCREEN");
		testFindValueCommand(CommandType.CLEAR_SCREEN, "CLEARSC");
	}
	
	/**
	 * Test Delete
	 */
	@Test
	public void testCommandDelete(){
		testFindValueCommand(CommandType.DELETE, "DELETE");
		testFindValueCommand(CommandType.DELETE, "DEL");
		testFindValueCommand(CommandType.DELETE, "REMOVE");
		testFindValueCommand(CommandType.DELETE, "REM");
	}
	
	/**
	 * Test edit 
	 */
	@Test
	public void testCommandEdit(){
		testFindValueCommand(CommandType.EDIT, "EDIT");
		testFindValueCommand(CommandType.EDIT, "CHANGE");
		testFindValueCommand(CommandType.EDIT, "ED");
	}
	
	/**
	 * Test search
	 */
	@Test
	public void testCommandSearch(){
		testFindValueCommand(CommandType.SEARCH, "SEARCH");
		testFindValueCommand(CommandType.SEARCH, "FIND");
	}
	
	/**
	 * Test list 
	 */
	@Test
	public void testCommandList(){
		testFindValueCommand(CommandType.LIST, "LIST");
		testFindValueCommand(CommandType.LIST, "LS");
		testFindValueCommand(CommandType.LIST, "LST");
		testFindValueCommand(CommandType.LIST, "SHOW");
		testFindValueCommand(CommandType.LIST, "DISPLAY");
	}
	
	/**
	 * Test redo
	 */
	@Test
	public void testCommandRedo(){
		testFindValueCommand(CommandType.REDO, "REDO");
		testFindValueCommand(CommandType.REDO, "RDO");
		testFindValueCommand(CommandType.REDO, "RE");
	}
	
	/**
	 * Test done
	 */
	@Test
	public void testCommandDone(){
		testFindValueCommand(CommandType.DONE, "DONE");
		testFindValueCommand(CommandType.DONE, "FINISH");
		testFindValueCommand(CommandType.DONE, "FINISHED");
		testFindValueCommand(CommandType.DONE, "COMPLETE");
		testFindValueCommand(CommandType.DONE, "COMPLETED");
	}
	
	/**
	 * Test undo
	 */
	@Test
	public void testCommandUndo(){
		testFindValueCommand(CommandType.UNDO, "UNDO");
		testFindValueCommand(CommandType.UNDO, "UN");
		testFindValueCommand(CommandType.UNDO, "UDO");
	}
	
	/**
	 * Test exit
	 */
	@Test
	public void testCommandExit(){
		testFindValueCommand(CommandType.EXIT, "EXIT");
		testFindValueCommand(CommandType.EXIT, "QUIT");
		testFindValueCommand(CommandType.EXIT, "CLOSE");
		testFindValueCommand(CommandType.EXIT, "END");
		testFindValueCommand(CommandType.EXIT, "SHUTDOWN");
	}
	
	/**
	 * Test help
	 */
	@Test
	public void testCommandHelp(){
		testFindValueCommand(CommandType.HELP, "HELP");
		testFindValueCommand(CommandType.HELP, "HLP");
		testFindValueCommand(CommandType.HELP, "MAN");
	}
	
	/** 
	 * Testing Any order of Commands
	 */
	@Test
	public void testCommand() {		
		//invalid
		testFindValueCommand(CommandType.INVALID, "");
		testFindValueCommand(CommandType.INVALID, " ");
		
		//valid commands
		testFindValueCommand(CommandType.ADD, "add");
		testFindValueCommand(CommandType.ADD, "NEw");
		testFindValueCommand(CommandType.ADD, "Create");
		testFindValueCommand(CommandType.ADD, "add ");
		
		testFindValueCommand(CommandType.DELETE, "delete");
		testFindValueCommand(CommandType.DELETE, "   del\n");
		testFindValueCommand(CommandType.DELETE, "   del");
		
		testFindValueCommand(CommandType.ADD_INFO, "ADDINFO");
		
		testFindValueCommand(CommandType.DONE, "done");
		
		testFindValueCommand(CommandType.CLEAR_ALL, "clr");
		
		testFindValueCommand(CommandType.EDIT, "edit");
		
		testFindValueCommand(CommandType.UNDO, "undo");
		
		testFindValueCommand(CommandType.SEARCH, "find");
		
		testFindValueCommand(CommandType.LIST, "ls");
		
		testFindValueCommand(CommandType.HELP, "HELP");
		
		testFindValueCommand(CommandType.EXIT, "quIT");
	}
	
	private void testFindValueCommand (CommandTypes.CommandType expected, String input){
		//new CommandTypes();
		CommandTypes.getInstance();
		assertEquals(expected, CommandQueue.find(input));
	}

}
