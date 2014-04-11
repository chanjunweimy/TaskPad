//@author A0112084U

package com.taskpad.ui;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextFieldListener implements ActionListener
{  
	private InputFrame _textFrame;
	protected TextFieldListener(InputFrame textFrame){
		setTextFrame(textFrame);
	}

	/**
	 * @param textFrame
	 */
	private void setTextFrame(InputFrame textFrame) {
		_textFrame = textFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt)
	{  
		String inputString = _textFrame.getText();
		_textFrame.passInput(inputString);
		_textFrame.addHistory(inputString);
		_textFrame.reset();
	}
}
