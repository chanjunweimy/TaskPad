package com.taskpad.input;

public class Edit extends Command{
	
	private static final String COMMAND_EDIT = "EDIT";
	private static final int NUMBER_ARGUMENTS = 2;
	
	private static String PARAMETER_TASK_ID = "TASKID";
	private static String PARAMETER_INFO = "DESC";
	
	private static String _taskID = "";
	private static String _info = "";

	public Edit(String input, String fullInput) {
		super(input, fullInput);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		setCOMMAND(COMMAND_EDIT);
	}

	@Override
	protected boolean commandSpecificRun() {
		String[] splitParams = input.split(" ");
		_taskID = splitParams[0].trim();
		_info = removeTaskID(fullInput, _taskID);
		putInputParameters();
		return true;
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
	
	/**
	 * Lynnette, got bug here.
	 * inputString.length should less that getNUMBER_ARGUMENTS
	 * JunWei
	 */
	@Override
	protected boolean isNotNumberArgs(String[] inputString){
		if (inputString.length > getNUMBER_ARGUMENTS()){
			return true;
		}
		return false;
	}
	
	private String removeTaskID(String input, String taskID){
		input = input.replaceFirst("(?i)"+COMMAND_EDIT, "").trim();
		return input.replaceFirst(taskID, "").trim();
	}
	
	@SuppressWarnings("unused")
	private String removeFirstWord(String input){
		return input.replace(getFirstWord(input), "").trim();
	}
	
	private static String getFirstWord(String input) {
		return input.trim().split("\\s+")[0];
	}

}
