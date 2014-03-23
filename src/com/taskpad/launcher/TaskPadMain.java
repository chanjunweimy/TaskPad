/**
 * Author: Chan Jun Wei, Lynnette Ng Hui Xian, Wang Taining
 * Product: TaskPad
 * Team: W13-3j
 */

package com.taskpad.launcher;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.SwingUtilities;

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
		/* deprecated
		String todayDateAndTime = DateAndTimeManager.getInstance().getTodayDate() + "_" +
				DateAndTimeManager.getInstance().getTodayTime();
		String pattern = String.format("TaskPad_" + todayDateAndTime + ".log");
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
		
		/* Deprecated
		//Redirect System.out
	    PrintStream outPS = null;
		try {
			outPS = new PrintStream(
		         new BufferedOutputStream(
		            new FileOutputStream("TaskPad.%u.%g.log", true)));
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
