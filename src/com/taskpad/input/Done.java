package com.taskpad.input;

public class Done extends Command{
	
	private static final String COMMAND_DONE = "DONE";
	private static final int NUMBER_ARGUMENTS = 1;
	
	private static String PARAMETER_TASK_ID = "TASKID";
	
	private static String _taskID = "";

	public Done(String input, String fullInput) {
		super(input, fullInput);
		setCOMMAND(COMMAND_DONE);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
	}

	@Override
	protected boolean commandSpecificRun() {
		if (isNotValidTaskID(input)){
			return false;
		}
		_taskID = input.trim();
		return true;
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_TASK_ID, "");
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_TASK_ID, _taskID);		
	}

}
