//@author A0119646X

package com.taskpad.launcher;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/** This class is to set up global logging to a file
 */
public class LogManager {

	private static String _taskpad = "TaskPad";
	
	//Lynnette: try using Logger.GLOBAL_LOGGER_NAME instead of our hard coded name. :) 
	private static Logger _logger = Logger.getLogger(_taskpad);
	private static FileHandler _fh;
	
	private static LogManager _logManager = new LogManager();
	
	private LogManager(){
		
	}
	
	protected static LogManager getInstance(){
		return _logManager;
	}
	
	protected void setUpGlobalLogger(){
		//Set up logging to file 

		createFileName();
		
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
	
	private static void createFileName(){
		_taskpad = _taskpad + "_" + getCurrentDate();
		_taskpad.replaceAll("/", "");
		_taskpad.replaceAll(":", "");
	}
	
	private static String getCurrentDate(){
		SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
		Date today = new Date();
		return formater.format(today);
	}
}
