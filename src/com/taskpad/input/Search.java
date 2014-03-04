package com.taskpad.input;

public class Search extends Command{
	
	private static final String COMMAND_SEARCH = "SEARCH";
	private static final int NUMBER_ARGUMENTS = 1;
	
	private static String PARAMETER_KEYWORD = "KEY";
	private static String _keyword = "";

	public Search(String input) {
		super(input);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		setCOMMAND(COMMAND_SEARCH);
	}

	@Override
	protected boolean commandSpecificRun() {
		_keyword = input;
		return true;
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_KEYWORD, "");
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_KEYWORD, _keyword);
	}
	
	@Override
	protected boolean checkIfIncorrectArguments(){
		return false;
	}

}
