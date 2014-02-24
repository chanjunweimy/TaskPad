package com.taskpad.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class MinimizeAction extends AbstractAction {
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	private InputFrame _inputFrame;
	private OutputFrame _outputFrame;
	
	public MinimizeAction(InputFrame inputFrame, OutputFrame outputFrame){
		initializeAction(inputFrame, outputFrame);
	}

	private void initializeAction(InputFrame inputFrame, OutputFrame outputFrame) {
		setInputFrame(inputFrame);
		setOuputFrame(outputFrame);
	}

	private void setOuputFrame(OutputFrame outputFrame) {
		_outputFrame = outputFrame;
	}

	private void setInputFrame(InputFrame inputFrame) {
		_inputFrame = inputFrame;
	}
	
	public void actionPerformed(ActionEvent e) {
        _inputFrame.minimizeFrame();
        _outputFrame.minimizeFrame();
     }
}
