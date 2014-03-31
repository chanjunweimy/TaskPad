package com.taskpad.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextFieldListener implements ActionListener
{  
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{  
		String inputString = InputFrame.getText();
		GuiManager.passInput(inputString);
		GuiFrame.addHistory(inputString);
		InputFrame.reset();
	}
}
