package com.taskpad.input;

public class Undo extends Command{

	private final static String COMMAND_UNDO = "UNDO";
	
	private static String PARAMETER_UNDO = "";
	
	public Undo(String input) {
		super(input);
		setCOMMAND(COMMAND_UNDO);
	}

	@Override
	public void run(){
		clearInputParameters();
		initialiseParametersToNull();
		putInputParameters();
		createInputObject();
		passObjectToExecutor();
	}
	
	@Override
	protected boolean commandSpecificRun() {
		return true;
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_UNDO, "");
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_UNDO, "");
	}

}
