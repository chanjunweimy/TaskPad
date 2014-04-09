//@author A0112084U

package com.taskpad.launcher;



import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import com.taskpad.execute.ExecutorManager;
import com.taskpad.ui.GuiManager;

public class TaskPadLauncher implements Runnable {
	private final String MESSAGE_WELCOME = "Welcome to Taskpad! Type a command or type \"help\"";
	private final static Logger LOGGER = Logger.getLogger("TaskPad");


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
		GuiManager.callOutput(MESSAGE_WELCOME);
		GuiManager.startRemindingUser();
		LOGGER.info("SwingUtilities.isEventDispatchThread: " + SwingUtilities.isEventDispatchThread());
	}
	
}
