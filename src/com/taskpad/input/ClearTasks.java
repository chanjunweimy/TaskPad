package com.taskpad.input;

public class ClearTasks extends Command{
	
	private static final String COMMAND_CLEAR = "CLEAR";
	private static final int NUMBER_ARGUMENTS = 0;

	private static final String PARAMETER_NULL = "NULL";
	
	public ClearTasks(String input, String fullInput) {
		super(input, fullInput);
		setCOMMAND(COMMAND_CLEAR);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
	}
	
	@Override
	public void run(){
		initialiseParametersToNull();
		putInputParameters();
		createInputObject();
		passObjectToExecutor();
	}

	@Override
	protected boolean commandSpecificRun() {
		return false;
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_NULL, "");
	}

	@Override
	protected void putInputParameters() {		
		putOneParameter(PARAMETER_NULL, "");
	}

}
