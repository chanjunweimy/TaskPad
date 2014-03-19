package com.taskpad.input;

public class Redo extends Command{
	
	private final static String COMMAND_REDO = "REDO";
	private static String PARAMETER_REDO = "";

	public Redo(String input, String fullInput) {
		super(input, fullInput);
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
		putOneParameter(PARAMETER_REDO, "");
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_REDO, "");		
	}

}
