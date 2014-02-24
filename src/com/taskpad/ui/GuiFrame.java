package com.taskpad.ui;

import java.awt.Frame;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class GuiFrame extends JFrame{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	public GuiFrame(){
	}
	
	protected void minimizeFrame(){
		setState(Frame.ICONIFIED);
	}

	protected void setKeyAction(KeyStroke anyKeyStroke, Action anyAction, String anyDescription) {
		this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(anyKeyStroke, anyDescription);
	    this.getRootPane().getActionMap().put(anyDescription, anyAction);
	}
}
