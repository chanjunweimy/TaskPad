package com.taskpad.input;

/**
 * CommandTypes has been changed to a Singleton class
 */

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CommandTypes {
	
	protected static Logger logger = Logger.getLogger("TaskPad");
	
	public enum CommandType{
		ADD, ADD_INFO, ADD_REM, ADD_PRI, ALARM, CLEAR_ALL, CLEAR_SCREEN, 
		DELETE, DONE, EDIT, EXIT, HELP, INVALID, LIST, REDO, SEARCH, SHOW_REM, STOP, UNDO
	};
	
	protected static Map<CommandType, String[]> commandVariations = new HashMap<CommandType, String[]>();
	private static final CommandTypes _commandTypes = new CommandTypes();
	
	private CommandTypes(){
		createHashMap();
	}
	
	public static CommandTypes getInstance(){
		return _commandTypes;
	}

	private static void createHashMap(){
		putAddVariations();
		putAddInfoVariations();
		putAddRemVariations();
		putAddPriVariations();
		putAlarmVariations();
		putClearVariations();
		putClearScreenVariations();
		putDeleteVariations();
		putDoneVariations();
		putEditVariations();
		putExitVariations();
		putHelpVariations();
		putListVariations();
		putRedoVariations();
		putSearchVariations();
		putShowRemVariations();
		putStopVariations();
		putUndoVariations();
	}
	
	/* Helper methods for creating the hashmap */
	
	private static void putAddVariations(){
		String[] addVariations = {"ADD", "NEW", "CREATE", "INSERT"};
		commandVariations.put(CommandType.ADD, addVariations);
	}
	
	private static void putAddInfoVariations(){
		String[] addInfoVariations = {"ADDINFO", "INFO", "INFORMATION", "CREATEDESC", "ADDDESC", "CREATEINFO", "INFORMATIN", "INFORMATN"};
		commandVariations.put(CommandType.ADD_INFO, addInfoVariations);
	}
	
	private static void putAddRemVariations(){
		String[] addRemVariations = {"ADDR", "REMINDER", "REMIND", "REMAINDER", "ADDREM"};
		commandVariations.put(CommandType.ADD_REM, addRemVariations);
	}
	
	private static void putAddPriVariations(){
		String[] addPriVariations = {"ADDPRI", "ADDPRIORITY", "PRI", "PRIORITY"};
		commandVariations.put(CommandType.ADD_PRI, addPriVariations);
	}
	
	private static void putAlarmVariations() {
		String[] exitVariations = {"ALARM", "ADDALARM", "SETALARM", "SETTIMER", "RING", "CREATEALARM"};
		commandVariations.put(CommandType.ALARM, exitVariations);
	}
	
	private static void putDeleteVariations(){
		String[] deleteVariations = {"DELETE", "DEL", "REMOVE", "REM"};
		commandVariations.put(CommandType.DELETE, deleteVariations);
	}
	
	private static void putDoneVariations(){
		String[] doneVariations = {"DONE", "FINISHED", "COMPLETED", "FINISH", "COMPLETE"};
		commandVariations.put(CommandType.DONE, doneVariations);
	}
	
	private static void putClearVariations(){
		String[] clearVariations = {"CLEAR", "CLR", "CLEAN", "CLC", "CLEARALL"};
		commandVariations.put(CommandType.CLEAR_ALL, clearVariations);
	}
	
	private static void putClearScreenVariations(){
		String[] clearScreenVariations = {"CLEARSCR", "CLEARSCREEN", "CLEARSC", "CLCSR", "CLCSCR", "SCREEN", "SCR"};
		commandVariations.put(CommandType.CLEAR_SCREEN, clearScreenVariations);
	}
	
	private static void putEditVariations(){
		String[] editVariations = {"EDIT", "CHANGE", "ED"};
		commandVariations.put(CommandType.EDIT, editVariations);
	}
	
	private static void putUndoVariations(){
		String[] undoVariations = {"UNDO", "UN", "UDO"};
		commandVariations.put(CommandType.UNDO, undoVariations);
	}
	
	private static void putSearchVariations(){
		String[] searchVariations = {"SEARCH", "FIND"};
		commandVariations.put(CommandType.SEARCH, searchVariations);
	}
	
	private static void putShowRemVariations(){
		String[] showRemVariations = {"SHOWREM", "SHOWREMINDER", "SHOWREMAINDER"};
		commandVariations.put(CommandType.SHOW_REM, showRemVariations);
	}
	
	private static void putStopVariations(){
		String[] stopVariations = {"STOP", "STOPP", "STO"};
		commandVariations.put(CommandType.STOP, stopVariations);
	}
	
	private static void putListVariations(){
		String[] listVariations = {"LIST", "LS", "SHOW", "DISPLAY", "LST"};
		commandVariations.put(CommandType.LIST, listVariations);
	}
	
	private static void putRedoVariations(){
		String[] redoVariations = {"REDO", "RDO", "RE"};
		commandVariations.put(CommandType.REDO, redoVariations);
	}
	
	public static void putHelpVariations(){
		String[] helpVariations = {"HELP", "HLP", "MAN"};
		commandVariations.put(CommandType.HELP, helpVariations);
	}
	
	private static void putExitVariations(){
		String[] exitVariations = {"EXIT", "QUIT", "END", "CLOSE", "SHUTDOWN"};
		commandVariations.put(CommandType.EXIT, exitVariations);
	}

}
