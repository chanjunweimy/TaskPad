package com.taskpad.tests;

import static org.junit.Assert.*;

/** 
 * This unit test is to parse a time string through the TimeParser class
 * @author Lynnette
 */

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.TimeErrorException;

public class TestTimeParser {

	/**
	 * Test AM strings 
	 */

	@Test
	public void AMtest() {
		testTimeCommand("08:00", "8am");
		testTimeCommand("08:00", "8 am");
		testTimeCommand("08:00", "0800");
		testTimeCommand("08:15", "8.15 am");
		testTimeCommand("08:00", "8.00am");
	}
	
	/**
	 * Test PM strings 
	 */
	@Test
	public void PMtest(){
		testTimeCommand("20:00", "8pm");
		testTimeCommand("20:00", "8 pm");
		testTimeCommand("20:00", "2000");
		testTimeCommand("20:15", "20.15");
		testTimeCommand("20:15", "20;15");
	}
	
	private void testTimeCommand (String expected, String input){
		try {
			assertEquals(expected, DateAndTimeManager.getInstance().parseTimeInput(input));
		} catch (TimeErrorException e) {
			e.printStackTrace();
		}
	}

}
