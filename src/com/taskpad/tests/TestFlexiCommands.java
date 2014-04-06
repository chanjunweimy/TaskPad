//@author A0119646X

package com.taskpad.tests;



/**
 * This class is to test the commands not as the first word
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
		testFindValueCommand(CommandType.ADD, "do CS2101 homework new");
		testFindValueCommand(CommandType.ADD, "do create CS2101 homework");
		testFindValueCommand(CommandType.ADD, "do insert CS2101 homework");
	}
	
	/**
	 * Testing add info in between words
	 */
	
	@Test
	public void testCommandAddInfo(){
		testFindValueCommand(CommandType.ADD_INFO, "venue meeting room addinfo");
		testFindValueCommand(CommandType.ADD_INFO, "venue addinfo meeting room");

		testFindValueCommand(CommandType.ADD_INFO, "venue meeting room info");
		testFindValueCommand(CommandType.ADD_INFO, "venue info meeting room");
		testFindValueCommand(CommandType.ADD_INFO, "venue meeting room information");
		testFindValueCommand(CommandType.ADD_INFO, "venue information meeting room");
		testFindValueCommand(CommandType.ADD_INFO, "venue meeting room informatin");
		testFindValueCommand(CommandType.ADD_INFO, "venue informatin meeting room");
		testFindValueCommand(CommandType.ADD_INFO, "venue meeting room informatn");
		testFindValueCommand(CommandType.ADD_INFO, "venue informatn meeting room");
		
		testFindValueCommand(CommandType.ADD_INFO, "venue meeting room adddesc");
		testFindValueCommand(CommandType.ADD_INFO, "venue adddesc meeting room");
		
		testFindValueCommand(CommandType.ADD_INFO, "venue meeting room createdesc");
		testFindValueCommand(CommandType.ADD_INFO, "venue createdesc meeting room");
		
		testFindValueCommand(CommandType.ADD_INFO, "venue meeting room createinfo");
		testFindValueCommand(CommandType.ADD_INFO, "venue createinfo meeting room");
	}
	
	/**
	 * Test add reminder
	 */
	@Test
	public void testCommandAddRem(){
		testFindValueCommand(CommandType.ADD_REM, "today wash laundry ADDR");
		testFindValueCommand(CommandType.ADD_REM, "today addr wash laundry");
		
		testFindValueCommand(CommandType.ADD_REM, "today wash laundry remind");
		testFindValueCommand(CommandType.ADD_REM, "today remind wash laundry");

		testFindValueCommand(CommandType.ADD_REM, "today reminder wash laundry");
		testFindValueCommand(CommandType.ADD_REM, "today wash laundry reminder");

		testFindValueCommand(CommandType.ADD_REM, "today wash laundry remainder");
		testFindValueCommand(CommandType.ADD_REM, "today remainder wash laundry");
		
		testFindValueCommand(CommandType.ADD_REM, "today addrem wash laundry");
		testFindValueCommand(CommandType.ADD_REM, "today wash laundry addrem");
	}
	
	/**
	 * Test alarm
	 */
	@Test
	public void testCommandAlarm(){
		testFindValueCommand(CommandType.ALARM, "wash dishes ALARM");
		testFindValueCommand(CommandType.ALARM, "wash ALARM dishes");
		
		testFindValueCommand(CommandType.ALARM, "wash dishes ring");
		testFindValueCommand(CommandType.ALARM, "wash ring dishes");
		
		testFindValueCommand(CommandType.ALARM, "wash dishes ADDALARM");
		testFindValueCommand(CommandType.ALARM, "wash addALARM dishes");
		
		testFindValueCommand(CommandType.ALARM, "wash dishes setALARM");
		testFindValueCommand(CommandType.ALARM, "wash SETALARM dishes");
		
		testFindValueCommand(CommandType.ALARM, "wash dishes createALARM");
		testFindValueCommand(CommandType.ALARM, "wash CREATEALARM dishes");
		
		testFindValueCommand(CommandType.ALARM, "wash dishes setTImer");
		testFindValueCommand(CommandType.ALARM, "wash setTIMER dishes");
	}
	
	/**
	 * Test stop alarm
	 */
	@Test
	public void testCommandStop(){
		testFindValueCommand(CommandType.STOP, "STOP it now!!!");
		testFindValueCommand(CommandType.STOP, "now STOP");
		testFindValueCommand(CommandType.STOP, "hurry STOP la.");
		
		testFindValueCommand(CommandType.STOP, "now STOPP");
		testFindValueCommand(CommandType.STOP, "hurry STOPP la.");
		
		testFindValueCommand(CommandType.STOP, "now STO");
		testFindValueCommand(CommandType.STOP, "hurry STO la.");
	}
	
	/**
	 * Test clear data
	 */
	@Test
	public void testCommandClear(){
		testFindValueCommand(CommandType.CLEAR_ALL, "all data CLEAR");
		testFindValueCommand(CommandType.CLEAR_ALL, "just CLR");
		testFindValueCommand(CommandType.CLEAR_ALL, "all CLC data");
		testFindValueCommand(CommandType.CLEAR_ALL, "i want it CLEAN");
		testFindValueCommand(CommandType.CLEAR_ALL, "hurry and CLEARALL");
	}
	
	/**
	 * Test clear screen
	 */
	@Test
	public void testCommandClearScr(){
		testFindValueCommand(CommandType.CLEAR_SCREEN, "the output frame CLCSR");
		testFindValueCommand(CommandType.CLEAR_SCREEN, "output CLCSCR now");
		testFindValueCommand(CommandType.CLEAR_SCREEN, "i want an empty SCREEN");
		testFindValueCommand(CommandType.CLEAR_SCREEN, "output CLEARSCR now");
		testFindValueCommand(CommandType.CLEAR_SCREEN, "hurry and CLEARSCREEN");
		testFindValueCommand(CommandType.CLEAR_SCREEN, "please refresh CLEARSC");
	}
	
	/**
	 * Test Delete
	 */
	@Test
	public void testCommandDelete(){
		testFindValueCommand(CommandType.DELETE, "task 1 DELETE");
		testFindValueCommand(CommandType.DELETE, "task DEL now");
		testFindValueCommand(CommandType.DELETE, "task 1 REMOVE");
		testFindValueCommand(CommandType.DELETE, "task REM now");
	}
	
	/**
	 * Test edit 
	 */
	@Test
	public void testCommandEdit(){
		testFindValueCommand(CommandType.EDIT, "description must EDIT");
		testFindValueCommand(CommandType.EDIT, "i CHANGE me");
		testFindValueCommand(CommandType.EDIT, "description to ED");
	}
	
	/**
	 * Test search
	 */
	@Test
	public void testCommandSearch(){
		testFindValueCommand(CommandType.SEARCH, "homework SEARCH");
		testFindValueCommand(CommandType.SEARCH, "homework FIND");
		testFindValueCommand(CommandType.SEARCH, "i cant FIND my homework");
	}
	
	/**
	 * Test list 
	 */
	@Test
	public void testCommandList(){
		testFindValueCommand(CommandType.LIST, "all LIST");
		testFindValueCommand(CommandType.LIST, "-d LS");
		testFindValueCommand(CommandType.LIST, "all LST");
		testFindValueCommand(CommandType.LIST, "undone SHOW");
		testFindValueCommand(CommandType.LIST, "-al DISPLAY");
	}
	
	/**
	 * Test done
	 */
	@Test
	public void testCommandDone(){
		testFindValueCommand(CommandType.DONE, "1 DONE");
		testFindValueCommand(CommandType.DONE, "2 FINISH");
		testFindValueCommand(CommandType.DONE, "3 FINISHED");
		testFindValueCommand(CommandType.DONE, "4 COMPLETE");
		testFindValueCommand(CommandType.DONE, "5 COMPLETED");
	}
	
	private void testFindValueCommand (CommandTypes.CommandType expected, String input){
		//new CommandTypes();
		CommandTypes.getInstance();
		assertEquals(expected, CommandQueue.findFlexi(input));
	}

}
