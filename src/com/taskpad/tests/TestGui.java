//@author A0112084U

package com.taskpad.tests;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import com.taskpad.ui.GuiManager;

/**
 * ATTEMPT to test GUI!!! 
 *
 */
public class TestGui {
	private final ByteArrayOutputStream _outContent = new ByteArrayOutputStream();

	@Test
	public void test() {
		setUpStream();
		
		GuiManager.setDebug(true);
		GuiManager.initialGuiManager();

		///*
		boolean hasReminder = GuiManager.isTableActive();

		assertTrue(GuiManager.getInputFrameVisibility());
		if (hasReminder) {
			assertTrue(GuiManager.getTableVisibility());
			assertFalse(GuiManager.getOutputFrameVisibility());
		} else {
			assertFalse(GuiManager.getTableVisibility());
			assertTrue(GuiManager.getOutputFrameVisibility());
		}
		//*/

		try {
			Robot bot = new Robot();

			bot.setAutoDelay(40);
			bot.setAutoWaitForIdle(true);
 
			//bot.delay(3000);
			
			//leftClick(bot);
			
			bot.delay(3000);

			type(bot, "add abc\n");
			
			bot.delay(3000); 
			
			String expected =
					"Task Successfully Added!\r\n\r\n\r\n\r\nTask ID:		3"
					+ "\r\n\r\n\r\nDescription:	"
					+ "abc \r\n\r\n\r\nStatus:		Not done.\r\n\r\n\r\n";
			assertEquals(expected + "\r\n", _outContent.toString());
			
			cleanUpStreams();
		} catch (AWTException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//assertFalse(GuiManager.getInputFrameVisibility());
		//assertFalse(GuiManager.getOutputFrameVisibility());
		//assertFalse(GuiManager.getTableVisibility());

		
		
	}
	
	private void setUpStream(){
		System.setOut(new PrintStream(_outContent));
	}
	
	private void cleanUpStreams(){
		_outContent.reset();
	}

	@SuppressWarnings("unused")
	private void leftClick(Robot robot) {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(200);
	}

	@SuppressWarnings("unused")
	private void type(Robot robot, int i) {
		robot.delay(40);
		robot.keyPress(i);
		robot.keyRelease(i);
	}

	private void type(Robot robot, String s) {
		byte[] bytes = s.getBytes();
		for (byte b : bytes) {
			int code = b;
			// keycode only handles [A-Z] (which is ASCII decimal [65-90])
			if (code > 96 && code < 123)
				code = code - 32;
			robot.delay(40);
			robot.keyPress(code);
			robot.keyRelease(code);
		}
	}
}
