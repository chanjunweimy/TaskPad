//@author A0119646X

package com.taskpad.tests;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;

import javax.swing.SwingUtilities;

import org.junit.Test;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.launcher.TaskPadMain;
import com.taskpad.ui.GuiManager;

/**
 *
 * This JUnit Test case tests the whole system using GUI testing.
 *
 */
public class TestGui {
	private final ByteArrayOutputStream _outContent = new ByteArrayOutputStream();

	private String _expected;
	
	private DateAndTimeManager _datm = DateAndTimeManager.getInstance();

	@Test
	public void test() {
		setUpStream();

		setUpGuiManager();
		setUpDateTimeDebug("12/04/2014 09:00");
		runTaskPad();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				boolean hasReminder = GuiManager.isTableActive();

				assertTrue(GuiManager.getInputFrameVisibility());
				if (hasReminder) {
					assertTrue(GuiManager.getTableVisibility());
					assertFalse(GuiManager.getOutputFrameVisibility());
				} else {
					assertFalse(GuiManager.getTableVisibility());
					assertTrue(GuiManager.getOutputFrameVisibility());
				}

			}

		});


		try {
			Robot bot = new Robot();

			autoWait(bot);

			/* To reset Eclipse's data */
			clear(bot);
			
			/* Standard commands */
			addFloatingTask(bot);
			addDeadlineTaskStandard(bot);
			addStartEndTaskStandard(bot);
			
			addInfoTask(bot);
			
			markTaskDone(bot);
			
			/* Flexi commands */
			addFloatingTask2(bot);
			addDeadlineTaskFlexi(bot);
			addStartEndTaskFlexi(bot);
			
			editDesc(bot);
			editDesc2(bot);
			editDeadline(bot);
			editStart(bot);
			editEnd(bot);
			
			//Cannot add rem, if not when run, the thing changes
			//addrem(bot);
			
			/* Alternative words */
			delTask(bot);
			remTask(bot);
			
			newTask(bot);
			
			searchTask(bot);
			findTask(bot);
			
			listTask(bot);
			markTaskDone2(bot);
			listDone(bot);
			
			/* Non strict ordering */		
			findTask(bot);
			addTaskNonStrict(bot);
			markDoneNonStrict(bot);
			
			/* set alarm to ring */
			alarmOneSec(bot);
			alarmRings(bot);
			alarmStop(bot);
			
			bot.delay(3000);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

	private void alarmStop(Robot bot) {
		type(bot, "stop\n");
		bot.delay(1000);
		_expected = "Stopping Alarm";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void alarmRings(Robot bot) {
		bot.delay(1000);
		_expected = "ALARM!! stopping demo";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void alarmOneSec(Robot bot) {
		type(bot, "alarm stopping demo 1s\n");
		bot.delay(1000);
		_expected = "Creating alarm... alarm stopping demo 1s";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void markDoneNonStrict(Robot bot) {
		type(bot, "6 done\n");
		bot.delay(1000);
		_expected = "Task ID:		6"
				+ "\n\nDescription:	cs2106 lab \n\n" 
				+ "Deadline:		23:59 13/04/2014\n\n"
				+ "Status:		Done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void addTaskNonStrict(Robot bot) {
		type(bot, "cs2106 lab due tomorrow add\n");
		bot.delay(1000);
		_expected = "Task Successfully Added!\n\n\nTask ID:		6"
				+ "\n\nDescription:	cs2106 lab \n\n" 
				+ "Deadline:		23:59 13/04/2014\n\n"
				+ "Status:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void listDone(Robot bot) {
		type(bot, "list done\n");
		bot.delay(2000);
		_expected = "Listing finished tasks...";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}
	
	private void markTaskDone2(Robot bot) {
		type(bot, "done 1\n");
		bot.delay(1000);
		_expected = "Task ID:		1"
				+ "\n\nDescription:	do developer guide\n\n" 
				+ "Deadline:		23:59 25/04/2014\n\n"
				+"Status:		Done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void listTask(Robot bot) {
		type(bot, "list\n");
		bot.delay(2000);
		_expected = "Listing all tasks...";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void findTask(Robot bot) {
		type(bot, "find meeting \n");
		bot.delay(1000);
		_expected = "No task is found.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void searchTask(Robot bot) {
		type(bot, "search tutorial \n");
		bot.delay(1000);
		_expected = "";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void newTask(Robot bot) {
		type(bot, "new ST2334 tutorial 11\n");
		bot.delay(1000);
		_expected = "Task Successfully Added!\n\n\nTask ID:		5"
				+ "\n\nDescription:	st2334 tutorial 11 \n\n" 
				+ "Status:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void remTask(Robot bot) {
		type(bot, "rem 2\n");
		bot.delay(1000);
		_expected = "'team meeting' deleted.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void delTask(Robot bot) {
		type(bot, "del 1\n");
		bot.delay(1000);
		_expected = "'do video presentation ' deleted.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	@SuppressWarnings("unused")
	private void addrem(Robot bot) {
		type(bot, "addrem 6 2pm\n");
		bot.delay(1000);
		_expected = "Reminder added!  6: 12/04/2014 14:00";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void editEnd(Robot bot) {
		type(bot, "edit 6 end 5pm\n");
		bot.delay(1000);
		_expected = "TASK 6 EDITED: \n\n"
				+ "Task ID:		6\n\n"
				+ "Description:	movie date \n\n" 
				+ "Start:		14:00 12/04/2014\n\n" 
				+ "End:		17:00 12/04/2014\n\n"
				+"Status:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void editStart(Robot bot) {
		type(bot, "edit 6 start 2pm\n");
		bot.delay(1000);
		_expected = "TASK 6 EDITED: \n\n"
				+ "Task ID:		6\n\n"
				+ "Description:	movie date \n\n" 
				+ "Start:		14:00 12/04/2014\n\n" 
				+ "End:		15:00 12/04/2014\n\n"
				+"Status:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void editDesc2(Robot bot) {
		type(bot, "edit 4 desc dinner with mum, dad and sis\n");
		bot.delay(1000);
		_expected = "TASK 4 EDITED: \n\n"
				+ "Task ID:		4\n\n"
				+ "Description:	dinner with mum , dad and sis\n\n" 
				+"Status:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void editDeadline(Robot bot) {
		type(bot, "edit 5 deadline tomorrow\n");
		bot.delay(1000);
		_expected = "TASK 5 EDITED: \n\n"
				+ "Task ID:		5\n\n"
				+ "Description:	do cheat sheet \n\n"
				+ "Deadline:		23:59 13/04/2014\n\n"
				+ "Status:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void editDesc(Robot bot) {
		type(bot, "edit 4 dinner with mum and dad\n");
		bot.delay(1000);
		_expected = "TASK 4 EDITED: \n\n"
				+ "Task ID:		4\n\n"
				+ "Description:	dinner with mum and dad\n\n" 
				+"Status:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}
	
	private void addStartEndTaskFlexi(Robot bot) {
		type(bot, "add movie date from 1pm to 3pm\n");
		bot.delay(1000);
		_expected = "Task Successfully Added!\n\n\nTask ID:		6"
				+ "\n\nDescription:	movie date \n\n" 
				+ "Start:		13:00 12/04/2014\n\n"
				+ "End:		15:00 12/04/2014\n\n"
				+ "Status:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void addDeadlineTaskFlexi(Robot bot) {
		type(bot, "add do cheat sheet by next next thursday\n");
		bot.delay(1000);
		_expected = "Task Successfully Added!\n\n\nTask ID:		5"
				+ "\n\nDescription:	do cheat sheet \n\n" 
				+ "Deadline:		23:59 24/04/2014\n\n"
				+ "Status:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);		
	}

	private void addFloatingTask2(Robot bot) {
		type(bot, "add dinner with mum\n");
		bot.delay(1000);
		_expected = "Task Successfully Added!\n\n\nTask ID:		4"
				+ "\n\nDescription:	dinner with mum \n\n" 
				+"Status:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);		
	}

	private void markTaskDone(Robot bot) {
		type(bot, "done 1\n");
		bot.delay(1000);
		_expected = "Task ID:		1"
				+ "\n\nDescription:	do video presentation \n\n" 
				+"Status:		Done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}
	
	private void addInfoTask(Robot bot) {
		type(bot, "addinfo 3 venue Meeting Room 20\n");
		bot.delay(1000);
		_expected = "Task ID:		3"
				+ "\n\nDescription:	" + "team meeting\n\nStart:		15:00 26/04/2014\n\n" 
				+ "End:		16:00 26/04/2014\n\n" 
				+ "Details:		venue meeting room 20\n\n"
				+"Status:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void addStartEndTaskStandard(Robot bot) {
		type(bot, "add team meeting -s 26/04/2014 3pm -e 26/04/2014 4pm\n");
		bot.delay(1000);
		_expected = "Task Successfully Added!\n\n\nTask ID:		3"
				+ "\n\nDescription:	" + "team meeting\n\nStart:		15:00 26/04/2014\n\n" 
				+ "End:		16:00 26/04/2014\n\n" 
				+"Status:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);		
	}

	private void autoWait(Robot bot) {
		bot.setAutoDelay(40);
		bot.setAutoWaitForIdle(true);
		bot.delay(2000);
	}

	private void addDeadlineTaskStandard(Robot bot) {
		type(bot, "add do developer guide -d 25/04/2014\n");
		bot.delay(1000);
		_expected = "Task Successfully Added!\n\n\nTask ID:		2"
				+ "\n\nDescription:	" + "do developer guide\n\nDeadline:		23:59 25/04/2014\n\nStatus:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(2000);
	}

	private void addFloatingTask(Robot bot) {
		type(bot, "add do video presentation\n");
		bot.delay(1000);
		_expected = "Task Successfully Added!\n\n\nTask ID:		1"
				+ "\n\nDescription:	" + "do video presentation \n\nStatus:		Not done.";
		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(3000);
	}

	private void clear(Robot bot) {
		type(bot, "clear\n");
		bot.delay(1000);
		_expected += "All tasks have been deleted. You can use undo to get them back.";

		assertEquals(_expected, _outContent.toString().trim());
		cleanUpStreams();
		bot.delay(3000);
	}

	private void runTaskPad() {
		TaskPadMain.runProgram();

		_expected = "Welcome to Taskpad! Type a command or type \"help\"\n\n"
				+ "Today\'s Tasks \n\n"
				+ "Showing your tasks and reminders...\n\n"
				+ "Nothing to show.\n\n\n";
	}

	private void setUpGuiManager() {
		GuiManager.setDebug(true);
		GuiManager.setGui(true);
	}
	
	private void setUpDateTimeDebug(String dateString){
		try {
			_datm.setDebug(dateString);
		} catch (ParseException e) {
			//wrong date causes failed
			fail();
		}
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
