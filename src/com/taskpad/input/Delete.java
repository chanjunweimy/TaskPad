/**
 * This class creates a Delete object
 * 
 * Current syntax for delete: del <taskID>
 * 
 * Returns Input object
 * 
 */

package com.taskpad.input;

import java.util.HashMap;
import java.util.Map;

public class Delete extends Command{
	
	private static String COMMAND_DELETE = "DELETE";
	private static String PARAMETER_TASK_ID = "TASKID";
	private static int NUMBER_ARGUMENTS = 1;		//Number of arguments for delete

	public Delete(String input, String fullInput) {
		super(input, fullInput);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		setCOMMAND(COMMAND_DELETE);
	}

	@Override
	protected boolean commandSpecificRun() {
		putInputParameters();
		return true;
	}

	@Override
	protected void initialiseParametersToNull() {
		inputParameters.put(PARAMETER_TASK_ID, "");		
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_TASK_ID, input.trim());
	}
	
}
