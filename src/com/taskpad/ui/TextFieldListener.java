package com.taskpad.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextFieldListener implements ActionListener
{  
	public void actionPerformed(ActionEvent evt)
	{  
		String inputString = InputFrame.getText();
		GuiManager.passInput(inputString);
		InputFrame.reset();
	}
}
