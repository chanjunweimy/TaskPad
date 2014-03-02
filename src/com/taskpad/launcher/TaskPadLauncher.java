package com.taskpad.launcher;

import com.taskpad.input.InputManager;
import com.taskpad.ui.GuiManager;

public class TaskPadLauncher implements Runnable {
	private final String MESSAGE_START_REMINDER = "HELLO! Reminder: ";
	private final String MESSAGE_WELCOME = "Welcome to Taskpad! Type a command or type \"help\"";

	public TaskPadLauncher(){
	}
	
	
	@Override
	public void run() {
		setUpInputProcessor();
		setUpGui();
	}

	private void setUpGui() {
		GuiManager.initialGuiManager();
		GuiManager.callOutput(MESSAGE_WELCOME);
		GuiManager.remindUser(MESSAGE_START_REMINDER);
	}
	
	private void setUpInputProcessor() {
		InputManager.startInputProcessor();
	}
}
