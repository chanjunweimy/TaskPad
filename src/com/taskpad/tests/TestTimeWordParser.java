package com.taskpad.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.NullTimeUnitException;
import com.taskpad.dateandtime.NullTimeValueException;

/**
 * 
 *
 * @category
 * TestTimeWordParser: a Junit test case written to test TimeWordParser
 */
public class TestTimeWordParser {

	DateAndTimeManager parser = DateAndTimeManager.getInstance();
	
	@Test
	public void test1() {
		try {
			assertEquals(parser.convertToSecond("1s"), "1");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test2() {
		try {
			parser.convertToSecond("");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			assertTrue(e.getMessage().equals("Error: Please enter a time value"));
		}
	}
	
	@Test
	public void test3() {
		try {
			parser.convertToSecond("s");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			assertTrue(e.getMessage().equals("Error: Please enter a time value"));
		}
	}
	
	@Test
	public void test4() {
		try {
			assertEquals(parser.convertToSecond("1 s"), "1");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void test5() {
		try {
			parser.convertToSecond(null);
		} catch (NullTimeUnitException | NullTimeValueException e) {
			assertTrue(e.getMessage().equals("Error: Please enter a time unit"));
		}
	}
	
	@Test
	public void test6() {
		try {
			parser.convertToSecond("a");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			assertTrue(e.getMessage().equals("Error: Please enter a time unit"));
		}
	}
	
	@Test
	public void test7() {
		try {
			parser.convertToSecond("1");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			assertTrue(e.getMessage().equals("Error: Please enter a time unit"));
		}
	}
	
	@Test
	public void test8() {
		try {
			parser.convertToSecond("1                m");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			assertTrue(e.getMessage().equals("Error: Please enter a time unit"));
		}
	}

	@Test
	public void test9() {
		try {
			assertEquals(parser.convertToSecond("1 s 1 m"), "61");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			fail();
		}
	}
	
	@Test
	public void test10() {
		try {
			parser.convertToSecond("1                m 2");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			assertTrue(e.getMessage().equals("Error: Please enter a time unit"));
		}
	}
	
	@Test
	public void test11() {
		try {
			parser.convertToSecond("1  month");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			assertTrue(e.getMessage().equals("Error: Please enter a time unit"));
		}
	}
	
	@Test
	public void test12() {
		try {
			parser.convertToSecond("one one ones");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			assertTrue(e.getMessage().equals("Error: Please enter a time unit"));
		}
	}
	
	@Test
	public void test13() {
		try {
			assertEquals(parser.convertToSecond("one one one s"), "111");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			fail();
		}
	}
	
	@Test
	public void test14() {
		try {
			parser.convertToSecond("1 1 1 s");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			assertTrue(e.getMessage().equals("Error: Please enter a time value"));
		}
	}
	
	@Test
	public void test15() {
		try {
			assertEquals(parser.convertToSecond("one one s 1 m"), "71");
		} catch (NullTimeUnitException | NullTimeValueException e) {
			fail();
		}
	}
}
