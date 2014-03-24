package com.taskpad.input;

public class Search extends Command{
	
	private static final String COMMAND_SEARCH = "SEARCH";
	private static final int NUMBER_ARGUMENTS = 1;
	
	private static String PARAMETER_KEYWORD = "KEYWORD";
	private static String _keyword;

	public Search(String input, String fullInput) {
		super(input, fullInput);
	}
	
	@Override
	protected void initialiseOthers(){
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		setCOMMAND(COMMAND_SEARCH);
	}

	@Override
	public void run(){
		try {
			if (checkIfEmptyString()){
				return; 
			}
		} catch (EmptyStringException e) {

		}
		
		initialiseParametersToNull();
		commandSpecificRun();
		putInputParameters();
		createInputObject();
		passObjectToExecutor();
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
