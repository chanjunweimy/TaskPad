//@author A0112084U

package com.taskpad.tests;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;

/**
 * 
 * 
 * TestHolidayDatesParser is to test whether HolidayDatesParser works fine!
 */
public class TestHolidayDatesParser {
	private DateAndTimeManager _datm = DateAndTimeManager.getInstance();
	
	/* We test all cases at least once to ensure full path coverage */
	@Test
	public void testValid1() {
		testValidHoliday("CHRISTMas", "25/12/2014", "09/04/2014 00:00");
	}
	
	@Test
	public void testValid2() {
		testValidHoliday("NATIONAL DAY", "09/08/2014", "09/04/2014 00:00");
	}
	
	@Test
	public void testValid3() {
		testValidHoliday("April FOOLS", "01/04/2015", "09/04/2014 00:00");
	}
	
	@Test
	public void testValid4() {
		testValidHoliday("April FOOL", "01/04/2015", "09/04/2014 00:00");
	}
	
	@Test
	public void testValid5() {
		testValidHoliday("APRIL FOOLS DAY", "01/04/2015", "09/04/2014 00:00");
	}
	
	@Test
	public void testValid6() {
		testValidHoliday("APRIL FOOL DAY", "01/04/2015", "09/04/2014 00:00");
	}
	
	@Test
	public void testValid7() {
		testValidHoliday("INDEPDENCE DAY", "04/07/2014", "09/04/2014 00:00");
	}
	
	@Test
	public void testValid8() {
		testValidHoliday("LABOUR DAY", "01/05/2014", "09/04/2014 00:00");
	}
	
	@Test
	public void testValid9() {
		testValidHoliday("LABOR DAY", "01/05/2014", "09/04/2014 00:00");
	}
	
	@Test
	public void testValid10() {
		testValidHoliday("NEW YEAR", "01/01/2015", "09/04/2014 00:00");
	}
	
	@Test
	public void testValid11() {
		testValidHoliday("NEW YEAR Day", "01/01/2015", "09/04/2014 00:00");
	}
	
	@Test
	public void testInValid1() {
		testValidHoliday("JustForFun", null, "09/04/2014 00:00");
	}
	
	@Test
	public void testInValid2() {
		testValidHoliday(" ", null, "09/04/2014 00:00");
	}
	
	@Test
	public void testInValid3() {
		testValidHoliday("", null, "09/04/2014 00:00");
	}
	
	@Test
	public void testInValid4() {
		testValidHoliday(null, null, "09/04/2014 00:00");
	}

	/**
	 * 
	 */
	private void setupDebug(String date) {
		try {
			_datm.setDebug(date);
		} catch (ParseException e1) {
			fail();
		}
	}

	private void testValidHoliday(String input, String expected, String date) {
		setupDebug(date);
		assertEquals(DateAndTimeManager.getInstance().parseHolidayString(input), expected);
	}
}
