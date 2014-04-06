//@author A0112084U

package com.taskpad.launcher;



import com.taskpad.execute.ExecutorManager;
import com.taskpad.ui.GuiManager;

public class TaskPadLauncher implements Runnable {
	private final String MESSAGE_WELCOME = "Welcome to Taskpad! Type a command or type \"help\"";

	//TaskPadLauncher is meant to use in launcher package only
	protected TaskPadLauncher(){
	}
	
	
	@Override
	public void run() {
		//initialStorage(); DEPRECATED
		setUpGui();
		ExecutorManager.showReminder();
	}

	/* DEPRECATED
	private void initialStorage() {
		DataManager.initializeXml();
	}
	 */


	private void setUpGui() {
		GuiManager.initialGuiManager();
		GuiManager.callOutput(MESSAGE_WELCOME);
		GuiManager.startRemindingUser();
	}
	
}
