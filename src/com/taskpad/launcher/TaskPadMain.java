/**
 * Author: Chan Jun Wei, Lynnette Ng Hui Xian, Wang Taining
 * Product: TaskPad
 * Team: W13-3j
 */

package com.taskpad.launcher;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.SwingUtilities;

import com.taskpad.dateandtime.DateAndTimeManager;

public class TaskPadMain{
	
	private static Logger logger = Logger.getLogger("TaskPad");
	private static FileHandler fh;

	private TaskPadMain(){
	}
	
	public static void main(String[] args){
		setUpLogging();
		runProgram();
	}

	private static void setUpLogging() {
		//Set up logging to file 
		/*
		String todayDateAndTime = DateAndTimeManager.getInstance().getTodayDate() + "_" +
				DateAndTimeManager.getInstance().getTodayTime();
		String pattern = String.format("TaskPad_" + todayDateAndTime + ".log");
		*/
		/** Note to Jun Wei
		 * If I pass pattern (see above^^) into addHandlertoLogger, it gives an error. 
		 * Also I tried passing pattern to the FileOutputStream below...
		 * To not get error, I must do new FileHandler("test.log"); i.e. put the full filename inside
		 * or new FileOutputStream("test.log"); which is very strange, because it's not suppose to be that way?
		 */
		
		try{
			createFileHandler();
			addHandlerToLogger();
			createAndSetFormatter();
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

	private static void addHandlerToLogger() {
		logger.addHandler(fh);
	}

	private static void createAndSetFormatter() {
		SimpleFormatter simpleFormatter = new SimpleFormatter();
		fh.setFormatter(simpleFormatter);
	}

	private static void createFileHandler() throws IOException {
		fh = new FileHandler("TaskPad.%u.%g.log");
	}

	private static void runProgram() {		
		Runnable runTaskPad = new TaskPadLauncher();
		SwingUtilities.invokeLater(runTaskPad);
	}
}
