//@author A0112084U

package com.taskpad.tests;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.SwingUtilities;

import org.junit.Test;

import com.taskpad.launcher.TaskPadMain;
import com.taskpad.ui.GuiManager;

/**
 * ATTEMPT to test GUI!!!
 * 
 */
public class TestGui {
	private final ByteArrayOutputStream _outContent = new ByteArrayOutputStream();

	private String _expected;

	@Test
	public void test() {
		setUpStream();

		GuiManager.setDebug(true);
		GuiManager.setGui(true);
		TaskPadMain.runProgram();

		_expected = "Welcome to Taskpad! Type a command or type \"help\"\n\n"
				+ "Today\'s Tasks \n\n"
				+ "Showing your tasks and reminders...\n\n"
				+ "Nothing to show.\n\n\n";

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// /*
				boolean hasReminder = GuiManager.isTableActive();

				assertTrue(GuiManager.getInputFrameVisibility());
				if (hasReminder) {
					assertTrue(GuiManager.getTableVisibility());
					assertFalse(GuiManager.getOutputFrameVisibility());
				} else {
					assertFalse(GuiManager.getTableVisibility());
					assertTrue(GuiManager.getOutputFrameVisibility());
				}
				// */

			}

		});

		// cleanUpStreams();

		try {
			Robot bot = new Robot();

			bot.setAutoDelay(40);
			bot.setAutoWaitForIdle(true);

			bot.delay(3000);

			type(bot, "clear\n");

			bot.delay(1000);
			_expected += "All tasks have been deleted. You can use undo to get them back.";

			assertEquals(_expected, _outContent.toString().trim());
			cleanUpStreams();

			// leftClick(bot);

			bot.delay(3000);

			type(bot, "add abc\n");

			bot.delay(3000);

			_expected = "Task Successfully Added!\n\n\nTask ID:		1"
					+ "\n\nDescription:	" + "abc \n\nStatus:		Not done.";
			assertEquals(_expected, _outContent.toString().trim());

			cleanUpStreams();
			bot.delay(3000);

		} catch (AWTException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// assertFalse(GuiManager.getInputFrameVisibility());
		// assertFalse(GuiManager.getOutputFrameVisibility());
		// assertFalse(GuiManager.getTableVisibility());

	}

	private void setUpStream() {
		System.setOut(new PrintStream(_outContent));
	}

	private void cleanUpStreams() {
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
