package com.taskpad.launcher;

import com.taskpad.ui.FlexiFontOutputFrame;
import com.taskpad.ui.GuiManager;
import com.taskpad.ui.InputFrame;
import com.taskpad.ui.OutputFrame;

public class TaskPadLauncher implements Runnable {
	private static InputFrame _inputFrame;
	private static OutputFrame _outputFrame;
	
	@Override
	public void run() {
		setUpGui();
	}

	private void setUpGui() {
		initialFrames();
		GuiManager.initialGuiManager(_inputFrame, _outputFrame);
		GuiManager.callOutput("Welcome to Taskpad! Type a command or type \"help\"");
		GuiManager.remindUser("HELLO! Reminder: ");
	}

	private void initialFrames() {
		_inputFrame = new InputFrame();
		//_outputFrame = new OutputFrame(); //DEPRECATED!!!
		_outputFrame = new FlexiFontOutputFrame();
	}

}
