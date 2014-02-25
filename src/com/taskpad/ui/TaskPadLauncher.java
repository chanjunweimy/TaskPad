package com.taskpad.ui;

import com.taskpad.input.InputMain;

public class TaskPadLauncher implements Runnable {
	private static InputFrame _inputFrame;
	private static OutputFrame _outputFrame;
	
	@Override
	public void run() {
		setUpInputProcessor();
		setUpGui();
	}

	private void setUpGui() {
		initialFrames();
		GuiManager.initialGuiManager(_inputFrame, _outputFrame);
	}

	private void initialFrames() {
		_inputFrame = new InputFrame();
		_outputFrame = new OutputFrame();
	}

	private void setUpInputProcessor() {
		new InputMain();
	}

}
