package com.taskpad.input;

public class Addrem extends Command{
	
	private static final String COMMAND_ADD_REM = "ADDREM";
	private static final int NUMBER_ARGUMENTS = 2;

	public Addrem(String input) {
		super(input);
		setCOMMAND(COMMAND_ADD_REM);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
	}

	@Override
	protected boolean commandSpecificRun() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void initialiseParametersToNull() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void putInputParameters() {
		// TODO Auto-generated method stub
		
	}
	
}
