package com.TaskPad.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextFieldListener implements ActionListener
{  
	public void actionPerformed(ActionEvent evt)
	{  
		String inputString = InputFrame.input.getText();
		GUIManager.passInput(inputString);
		InputFrame.input.setText("");
	}
}
