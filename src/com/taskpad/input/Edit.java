package com.taskpad.input;

public class Edit extends Command{
	
	private static final String COMMAND_EDIT = "EDIT";
	private static final int NUMBER_ARGUMENTS = 2;
	
	private static String PARAMETER_TASK_ID = "TASKID";
	private static String PARAMETER_DESC = "DESC";
	private static String PARAMETER_DEADLINE = "DEADLINE";
	
	private static String _taskID;
	private static String _desc;
	private static String _deadline;

	public Edit(String input, String fullInput) {
		super(input, fullInput);
	}
	
	@Override
	protected void initialiseOthers(){
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		setCOMMAND(COMMAND_EDIT);
		
		_taskID = "";
		_desc = "";
		_deadline = "";
	}

	@Override
	protected boolean commandSpecificRun() {
		String[] splitParams = input.split(" ");
		_taskID = splitParams[0].trim();
		
		if (isDesc()){
			fullInput = removeWordDesc(fullInput);
			_desc = removeTaskID(fullInput, _taskID);
		} else if (isDeadline()){
			fullInput = removeWordDeadline(fullInput);
			_deadline = removeTaskID(fullInput, _taskID);
		} else {
			//no tag, just assume description
			_desc = removeTaskID(fullInput, _taskID);
		}
		
		//System.out.println("Desc: " + _desc + "deadline: " + _deadline);
		
		putInputParameters();
		return true;
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_TASK_ID, "");
		putOneParameter(PARAMETER_DESC, "");
		putOneParameter(PARAMETER_DEADLINE, "");
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_TASK_ID, _taskID);
		putOneParameter(PARAMETER_DESC, _desc);		
		putOneParameter(PARAMETER_DEADLINE, _deadline);
	}
	
	/**
	 * Lynnette, got bug here.
	 * inputString.length should less that getNUMBER_ARGUMENTS
	 * Dont understand :(
	 * JunWei
	 */
	@Override
	protected boolean isNotNumberArgs(String[] inputString){
		if (inputString.length >= getNUMBER_ARGUMENTS()){
			return false;
		}
		return true;
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
	
	private boolean isDesc(){
		String inputCopy = input.toUpperCase();
		if (inputCopy.contains("DESC") || inputCopy.contains("DESCRIPTION")){
			return true;
		} 
		return false;
	}

	private String removeWordDesc(String inputCopy) {
		String newString = "";
		int count = 0;	//Just replace only one occurrence of description
		
		String[] inputSplit = inputCopy.split(" ");
		for (int i=0; i<inputSplit.length; i++){
			System.out.println(inputSplit[i]);
			if (isNotDescWord(inputSplit[i].trim())){
				newString += inputSplit[i] + " ";
			} else if (count > 0) {
				newString += inputSplit[i] + " ";
			} else {
				count ++;
			}
		}
		return newString;
	}

	private boolean isNotDescWord(String string) {
		return !(string.toUpperCase().equals("DESC") || 
				string.toUpperCase().equals("DESCRIPTION"));
	}
	
	private boolean isDeadline(){
		String inputCopy = input.toUpperCase();
		if (inputCopy.contains("DEADLINE") || inputCopy.contains("DEAD") || inputCopy.contains("DATE")){
			return true;
		} 
		return false;
	}
	
	private String removeWordDeadline(String inputCopy) {
		String newString = "";
		int count = 0;
		String[] inputSplit = inputCopy.split(" ");
		for (int i=0; i<inputSplit.length; i++){
			if (!isDeadlineWord(inputSplit[i].trim())){
				newString += inputSplit[i] + " ";
			} else if (count > 0 ){
				newString += inputSplit[i] + " ";
			} else {
				count++;
			}
		}
		return newString;
	}
	
	private boolean isDeadlineWord(String string) {
		return string.toUpperCase().equals("DEADLINE") || 
				string.toUpperCase().equals("DATE") || 
				string.toUpperCase().equals("DEAD");
	}

}
