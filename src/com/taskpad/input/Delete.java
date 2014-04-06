/**
 * This class creates a Delete object
 * 
 * Current syntax for delete: del <taskID>
 * 
 * Returns Input object
 * 
 */

//@author A0119646X


package com.taskpad.input;

public class Delete extends Command{
	
	private static String COMMAND_DELETE = "DELETE";
	private static String PARAMETER_TASK_ID = "TASKID";
	private static String PARAMETER_KEYWORD = "KEYWORD";
	private static int NUMBER_ARGUMENTS = 1;		//Number of arguments for delete
	
	private String _taskID;
	private String _keyword;

	public Delete(String input, String fullInput) {
		super(input, fullInput);	
	}
	
	@Override
	protected void initialiseOthers(){
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		setCOMMAND(COMMAND_DELETE);	
		
		_taskID = "";
		_keyword = "";
	}
	
	@Override
	protected boolean commandSpecificRun() {
		if (isTaskID(input)){
			_taskID = input.trim();
		} else{
			_keyword = input.trim();
		}
		
		return true;
	}
	
	private boolean isTaskID(String input){
		try{
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
	}

	@Override
	protected void initialiseParametersToNull() {
		inputParameters.put(PARAMETER_TASK_ID, "");		
		inputParameters.put(PARAMETER_KEYWORD, "");
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_TASK_ID, _taskID);
		putOneParameter(PARAMETER_KEYWORD, _keyword);
	}
	
	protected boolean checkIfIncorrectArguments() throws TaskIDException, InvalidParameterException{
		String inputString[] = input.split(" ");
		
		if (isNotNumberArgs(inputString)){
			throw new InvalidParameterException();
		}
		
		return false;
	}
	
}
