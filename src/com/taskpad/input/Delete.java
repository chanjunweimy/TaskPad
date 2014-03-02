/* 
 * This class creates a Delete object
 * 
 * Current syntax for delete: del <taskID>
 * 
 * @postconditions: passes Input object to executor to delete
 * 
 */

package com.taskpad.input;

import java.util.HashMap;
import java.util.Map;

public class Delete extends Command{
	
	private static String PARAMETER_TASK_ID = "TASKID";

	public Delete(String input) {
		super(input);
		setNUMBER_ARGUMENTS(1);
		setCOMMAND("DELETE");
	}

	@Override
	protected Input commandSpecificRun() {
		inputObject = createInputObject();
		return inputObject;
	}

	@Override
	protected void initialiseParametersToNull() {
		inputParameters.put(PARAMETER_TASK_ID, "");		
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_TASK_ID, input);
	}

	
}
