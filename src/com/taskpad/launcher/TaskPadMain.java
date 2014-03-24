/**
 * Author: Chan Jun Wei, Lynnette Ng Hui Xian, Wang Taining
 * Product: TaskPad
 * Team: W13-3j
 */

package com.taskpad.launcher;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.SwingUtilities;

public class TaskPadMain{
	
	private static String _taskpad = "TaskPad";
	
	//Lynnette: try using Logger.GLOBAL_LOGGER_NAME instead of our hard coded name. :) 
	private static Logger _logger = Logger.getLogger(_taskpad);
	private static FileHandler _fh;
	
	private TaskPadMain(){
	}
	
	public static void main(String[] args){
		setUpLogging();
		runProgram();
	}

	private static void setUpLogging() {
		//Set up logging to file 
		//String todayDateAndTime = DateAndTimeManager.getInstance().getTodayDate() + "_" +
		//		DateAndTimeManager.getInstance().getTodayTime();
		//TASKPAD= TASKPAD + "_" + todayDateAndTime;
		//String pattern = String.format("TaskPad_" + todayDateAndTime + ".log");

		_taskpad = _taskpad + "_" + getCurrentDate();
		//Date today = new Date();
		//_taskpad = String.format(_taskpad + "_%1$te-%1$tm-%1$tY", today);
		
		/** 
		 * NoteToLynnette: 
		 * It's because file cannot be created with 
		 * the sign "/".
		 * This piece of code should be refactored.
		 * Suggest using LogManager to do it. 
		 * I think this can be done in a new package: common.
		 * and create a new class call MyLogger or something like that,
		 * according to http://www.vogella.com/tutorials/Logging/article.html
		 * Is this ok? :) 
		 */
		
		try{
			createFileHandler();
			createAndSetFormatter();
			initializeLogger();
		} catch (SecurityException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		/*
		//Redirect System.out
		File file = new File(pattern);
	    PrintStream outPS = null;
		try {
			outPS = new PrintStream(
		         new BufferedOutputStream(
		            new FileOutputStream(file, true)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  // append is true
		System.setErr(outPS);    // redirect System.err
		System.setOut(outPS);
		*/
	}

	private static void initializeLogger() {
		_logger.addHandler(_fh);
	}

	private static void createAndSetFormatter() {
		SimpleFormatter simpleFormatter = new SimpleFormatter();
		_fh.setFormatter(simpleFormatter);
	}

	private static void createFileHandler() throws IOException {
		_fh = new FileHandler(_taskpad + ".log");
	}

	private static void runProgram() {		
		Runnable runTaskPad = new TaskPadLauncher();
		SwingUtilities.invokeLater(runTaskPad);
	}
	
	private static String getCurrentDate(){
		SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
		Date today = new Date();
		return formater.format(today);
	}
}
