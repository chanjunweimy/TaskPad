package com.taskpad.launcher;

import com.taskpad.ui.GuiManager;

public class TaskPadLauncher implements Runnable {

	@Override
	public void run() {
		setUpGui();
	}

	private void setUpGui() {
		GuiManager.initialGuiManager();
		GuiManager.callOutput("Welcome to Taskpad! Type a command or type \"help\"");
		GuiManager.remindUser("HELLO! Reminder: ");
	}
}
