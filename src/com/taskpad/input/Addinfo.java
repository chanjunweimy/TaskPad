//@author A0119646X

package com.taskpad.input;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidQuotesException;


public class Addinfo extends Command{
	
	private static final String COMMAND_ADD_INFO = "ADDINFO";
	private static final int NUMBER_ARGUMENTS = 2;
	
	private static String PARAMETER_INFO = "INFO";
	private static String PARAMETER_TASK_ID = "TASKID";
	
	private static String _info = "";
	private static String _taskID = "";
	private String _editInput;
	
	private static final String STRING_SPACE = " ";


	public Addinfo(String input, String fullInput) {
		super(input, fullInput);
	}

	@Override
	protected void initialiseOthers(){
		setCOMMAND(COMMAND_ADD_INFO);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
	}
	
	@Override
	protected boolean commandSpecificRun() {
		//String splitInput[] = input.split(" ");
		//_taskID = splitInput[0];
		LOGGER.info("fullInput is " + fullInput);
		_editInput = fullInput;  
		if(hasNoTaskIDInPosition(fullInput)){
			try {
				_taskID = findTaskID(fullInput);
			} catch (TaskIDException | InvalidQuotesException e) {
				InputManager.outputToGui(e.getMessage());
				LOGGER.severe(e.getMessage());
				return false;
			}
		}
		fullInput = _editInput;
		
		_info = getInfo(input, _taskID);
		return true;
	}
	
	@Override
	protected boolean checkIfIncorrectArguments() throws InvalidParameterException, TaskIDException{
		String inputString[] = input.split(STRING_SPACE);
		
		if (isNotNumberArgs(inputString)){
			LOGGER.severe("Throw");
			LOGGER.severe("inputString is " + inputString);
			throw new InvalidParameterException();
		}
			
		return false;
	}
	
	/**
	 * Takes in input string and finds the first integer as taskID
	 * @param input
	 * @return taskID
	 * @throws TaskIDException 
	 * @throws InvalidQuotesException 
	 */
	private String findTaskID(String input) throws TaskIDException, InvalidQuotesException{
		String numberInput = DateAndTimeManager.getInstance().parseNumberString(input);

		LOGGER.info("finding TaskID. Converted to numberInput");
		LOGGER.info("numberInput is " + numberInput);
		
		input = numberInput;
		fullInput = numberInput;
		
		LOGGER.info("input is " + input);
		LOGGER.info("fullInput is " + fullInput);
		
		int taskID = -1;
		String[] splitInput = input.split(STRING_SPACE);
		
		for (int i = 0; i < splitInput.length; i++){
			if (taskID == -1){
				String token = splitInput[i];
				if (!isNotValidTaskID(token)){
					taskID = Integer.parseInt(token);
					break;
				}
			}
		}
				
		LOGGER.info("taskID is " + taskID);
		
		if (taskID == -1){
			LOGGER.severe("TASK ID is invalid!");
			throw new TaskIDException();
		}
		
		return "" + taskID;
	}
	
	/**
	 * Check if second word entered is an integer (likely to be taskID)
	 * @param fullInput
	 * @return true if is integer and sets it as taskID
	 */
	private boolean hasNoTaskIDInPosition(String fullInput) {
		String splitInput[] = fullInput.split(STRING_SPACE);
		
		if (isNotValidTaskID(splitInput[1])){
			InputManager.outputToGui(MESSAGE_WARNING_TASKID);
			return true;
		} else {
			int taskID = Integer.parseInt(splitInput[1]);
			_taskID = "" + taskID;
			return false;
		}
		/*
		try{
			int taskID = Integer.parseInt(splitInput[1]);
			_taskID = "" + taskID;
			return false;
		} catch (NumberFormatException e){
			InputManager.outputToGui(MESSAGE_WARNING_TASKID);
			return true;
		}
		*/
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_TASK_ID, "");
		putOneParameter(PARAMETER_INFO, "");
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_TASK_ID, _taskID);
		putOneParameter(PARAMETER_INFO, _info);
	}
	
	@Override
	protected boolean isNotNumberArgs(String[] inputString){
		if (inputString.length < getNUMBER_ARGUMENTS()){
			return true;
		}
		return false;
	}
	
	private String getInfo(String input, String taskID){
		//input = removeFirstWord(input);
		return input.replaceFirst(taskID, "").trim();
	}
	
	/**
	 * @deprecated
	 * @param input
	 * @return
	 */
	@SuppressWarnings("unused")
	private String removeFirstWord(String input){
		return input.replace(getFirstWord(input), "").trim();
	}
	
	/**
	 * @deprecated
	 * @param input
	 * @return
	 */
	private static String getFirstWord(String input) {
		return input.trim().split("\\s+")[0];
	}

}
