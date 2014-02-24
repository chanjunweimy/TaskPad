package com.taskpad.ui;

import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.KeyStroke;

public class MinimizeKey {
	public MinimizeKey(InputFrame inputFrame, OutputFrame outputFrame){
		enableMinimize(inputFrame, outputFrame);
	}

	private void enableMinimize(InputFrame inputFrame, OutputFrame outputFrame){
		String description = "MINIMIZE";
		
		KeyStroke minimizeKeyStroke = defineMinimizeKey();
	    Action minimizeAction = new MinimizeAction(inputFrame, outputFrame);
	    
	    addKey(inputFrame, outputFrame, description, minimizeKeyStroke,
				minimizeAction);  
	}

	private void addKey(InputFrame inputFrame, OutputFrame outputFrame,
			String description, KeyStroke minimizeKeyStroke,
			Action minimizeAction) {
		inputFrame.setKeyAction(minimizeKeyStroke, minimizeAction, description);
	    outputFrame.setKeyAction(minimizeKeyStroke, minimizeAction, description);
	}

	private KeyStroke defineMinimizeKey() {
		KeyStroke minimizeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		return minimizeKeyStroke;
	}
}
