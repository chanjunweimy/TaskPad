//@author A0119646X

package com.taskpad.input;



import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidQuotesException;

public class Edit extends Command{
	
	private static final String COMMAND_EDIT = "EDIT";
	private static final int NUMBER_ARGUMENTS = 2;
	private static final String STRING_EMPTY = "";
	
	private static String PARAMETER_TASK_ID = "TASKID";
	private static String PARAMETER_DESC = "DESC";
	private static String PARAMETER_DEADLINE = "DEADLINE";
	private static String PARAMETER_START_DATE = "START DATE";
	private static String PARAMETER_START_TIME = "START TIME";
	private static String PARAMETER_END_DATE = "END DATE";
	private static String PARAMETER_END_TIME = "END TIME";
	
	private String _taskID;
	private String _desc;
	private String _deadline;
	private String _startDate;
	private String _startTime;
	private String _endDate;
	private String _endTime;
	
	private static final String STRING_SPACE = " ";

	public Edit(String input, String fullInput) {
		super(input, fullInput);
	}
	
	@Override
	protected void initialiseOthers(){
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		setCOMMAND(COMMAND_EDIT);
		
		_taskID = STRING_EMPTY;
		_desc = STRING_EMPTY;
		_deadline = STRING_EMPTY;
		_startDate = STRING_EMPTY;
		_startTime = STRING_EMPTY;
		_endDate = STRING_EMPTY;
		_endTime = STRING_EMPTY;
	}

	@Override
	protected boolean commandSpecificRun() {
		//String[] splitParams = input.split(" ");
		
		//_taskID = splitParams[0].trim();
		
		try {
			_taskID = findTaskID(fullInput);
		} catch (TaskIDException e) {
			InputManager.outputToGui(e.getMessage());
		}
		
		String tag = findTag(fullInput);
		
		switch(tag){
			case "DESC":
				fullInput = removeWordDesc(fullInput);
				_desc = removeTaskID(fullInput, _taskID);
				break;
			case "DEADLINE":
				fullInput = removeWordDeadline(fullInput);
				_deadline = findDeadline(fullInput);
				break;
			case "START":
				fullInput = removeWordStart(fullInput);
				String startResult = findDateOrTime(fullInput);
				inputStartTimeDate(startResult);
				break;
			case "END":
				fullInput = removeWordEnd(fullInput);
				String endResult = findDateOrTime(fullInput);
				inputEndTimeDate(endResult);
				break;
			default:
				_desc = removeTaskID(fullInput, _taskID);
				break;
		}
		
		/*
		if (isDesc()){
			fullInput = removeWordDesc(fullInput);
			_desc = removeTaskID(fullInput, _taskID);
		} else if (isDeadline()){
			fullInput = removeWordDeadline(fullInput);
			_deadline = findDeadline(fullInput);
		} else {
			//no tag, just assume description
			_desc = removeTaskID(fullInput, _taskID);
		}
		*/
		
		//System.out.println("Desc: " + _desc + "deadline: " + _deadline);
		
		putInputParameters();
		return true;
	}

	private String findTag(String fullInput){
		String tag = STRING_EMPTY;
		
		if (containsDesc(fullInput)){
			tag = "DESC";
		} else if (containsDeadline(fullInput)){
			tag = "DEADLINE";
		} else if (containsStart(fullInput)){
			tag = "START";
		} else if (containsEnd(fullInput)){
			tag = "END";
		}
		
		return tag;		
	}
	
	private String findDeadline(String fullInput) {
		String formatInput = null;
		try {
			formatInput = DateAndTimeManager.getInstance().formatDateAndTimeInString(fullInput);
		} catch (InvalidQuotesException e) {
			InputManager.outputToGui(e.getMessage());
		}
		
		String[] splitResult = formatInput.split(STRING_SPACE);
		return splitResult[4]+ " " + splitResult[5];
	}

	@Override
	protected boolean checkIfIncorrectArguments() throws InvalidParameterException, TaskIDException{
		String inputString[] = input.split(STRING_SPACE);
		
		if (isNotNumberArgs(inputString)){
			System.out.println("Throw");
			throw new InvalidParameterException();
		}
			
		return false;
	}
	
	/**
	 * Takes in input string and finds the first integer as taskID
	 * @param input
	 * @return
	 * @throws TaskIDException 
	 */
	private String findTaskID(String input) throws TaskIDException{
		String numberInput = DateAndTimeManager.getInstance().parseNumberString(input);

		if (numberInput != null){
			input = numberInput;
			fullInput = numberInput;
		}
		
		int taskID = -1;
		String[] splitInput = input.split(STRING_SPACE);
		
		for (int i=0; i<splitInput.length; i++){
			if (taskID == -1){
				try{
					taskID = Integer.parseInt(splitInput[i]);
				} catch (NumberFormatException e){
					//do nothing
				}
			}
		}
				
		if (taskID == -1){
			throw new TaskIDException();
		}
		
		
		return ""+taskID;
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_TASK_ID, STRING_EMPTY);
		putOneParameter(PARAMETER_DESC, STRING_EMPTY);
		putOneParameter(PARAMETER_DEADLINE, STRING_EMPTY);
		putOneParameter(PARAMETER_START_TIME, STRING_EMPTY);
		putOneParameter(PARAMETER_START_DATE, STRING_EMPTY);
		putOneParameter(PARAMETER_END_TIME, STRING_EMPTY);
		putOneParameter(PARAMETER_END_DATE, STRING_EMPTY);
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_TASK_ID, _taskID);
		putOneParameter(PARAMETER_DESC, _desc);		
		putOneParameter(PARAMETER_DEADLINE, _deadline);
		putOneParameter(PARAMETER_START_TIME, _startTime);
		putOneParameter(PARAMETER_START_DATE, _startDate);
		putOneParameter(PARAMETER_END_TIME, _endTime);
		putOneParameter(PARAMETER_END_DATE, _endDate);
	}
	
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
	
	private boolean containsDesc(String input){
		String inputCopy = input.toUpperCase();
		if (inputCopy.contains(" DESC ") || inputCopy.contains(" DESCRIPTION ")){
			return true;
		} 
		return false;
	}
	
	private boolean containsStart(String input){
		String inputCopy = input.toUpperCase();
		if (inputCopy.contains(" START ")){
			return true;
		} 
		return false;
	}
	
	private boolean containsEnd(String input){
		String inputCopy = input.toUpperCase();
		if (inputCopy.contains(" END ")){
			return true;
		} 
		return false;
	}

	private String removeWordDesc(String inputCopy) {
		String newString = "";
		int count = 0;	//Just replace only one occurrence of description
		
		String[] inputSplit = inputCopy.split(" ");
		for (int i=0; i<inputSplit.length; i++){
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
	
	private boolean containsDeadline(String input){
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

	private String removeWordEnd(String inputCopy) {
		String newString = "";
		int count = 0;	//Just replace only one occurrence of description
		
		String[] inputSplit = inputCopy.split(" ");
		for (int i=0; i<inputSplit.length; i++){
			if (isNotEndWord(inputSplit[i].trim())){
				newString += inputSplit[i] + " ";
			} else if (count > 0) {
				newString += inputSplit[i] + " ";
			} else {
				count ++;
			}
		}
		return newString;
	}
	
	private boolean isNotEndWord(String string) {
		return !string.toUpperCase().equals("END");
	}

	private String findDateOrTime(String fullInput) {
		String formatInput = null;
		try {
			formatInput = DateAndTimeManager.getInstance().formatDateAndTimeInString(fullInput);
		} catch (InvalidQuotesException e) {
			InputManager.outputToGui(e.getMessage());
		}
		
		return formatInput;
	}
	
	private void inputStartTimeDate(String result){
		String[] splitResult = result.split(STRING_SPACE);
		_startDate = splitResult[4];
		_startTime = splitResult[5];
	}
	
	private void inputEndTimeDate(String result){
		String[] splitResult = result.split(STRING_SPACE);
		_endDate = splitResult[4];
		_endTime = splitResult[5];
	}

	private String removeWordStart(String inputCopy) {
		String newString = "";
		int count = 0;	//Just replace only one occurrence of description
		
		String[] inputSplit = inputCopy.split(" ");
		for (int i=0; i<inputSplit.length; i++){
			if (isNotStartWord(inputSplit[i].trim())){
				newString += inputSplit[i] + " ";
			} else if (count > 0) {
				newString += inputSplit[i] + " ";
			} else {
				count ++;
			}
		}
		return newString;
	}
	
	private boolean isNotStartWord(String string) {
		return !string.toUpperCase().equals("START");
	}


}
