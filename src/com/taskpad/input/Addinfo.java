//@author A0119646X

package com.taskpad.input;


public class Addinfo extends Command{
	
	private static final String COMMAND_ADD_INFO = "ADDINFO";
	private static final int NUMBER_ARGUMENTS = 2;
	
	private static String PARAMETER_INFO = "INFO";
	private static String PARAMETER_TASK_ID = "TASKID";
	
	private static String _info = "";
	private static String _taskID = "";

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
		String splitInput[] = input.split(" ");
		_taskID = splitInput[0];
		_info = getInfo(fullInput, _taskID);
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
	
	@Override
	protected boolean isNotNumberArgs(String[] inputString){
		if (inputString.length < getNUMBER_ARGUMENTS()){
			return true;
		}
		return false;
	}
	
	private String getInfo(String input, String taskID){
		input = removeFirstWord(input);
		return input.replaceFirst(taskID, "").trim();
	}
	
	private String removeFirstWord(String input){
		return input.replace(getFirstWord(input), "").trim();
	}
	
	private static String getFirstWord(String input) {
		return input.trim().split("\\s+")[0];
	}

}
