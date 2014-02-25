package com.taskpad.ui;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OutputFrame extends GuiFrame{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private final double COMPUTER_WIDTH = 
			Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final double COMPUTER_HEIGHT = 
			Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private final Color OUTPUTBOX_BORDER_COLOR = 
			new Color(112, 48, 160);
	private final Color OUTPUTBOX_BACKGROUND_COLOR = 
			new Color(242, 242, 242);
	
	protected final static int OUTPUTFRAME_WIDTH = 300;
	protected final static int OUTPUTFRAME_HEIGHT = 150;
	protected static JTextArea output = new JTextArea(5, 15);
	
	public OutputFrame(){
		super();
		initialOutputBox();
		initialOutputFrame();
	}

	private void initialOutputFrame() {		
		//make JFrame Disappear
		setUndecorated(true);
		
		setSize(OUTPUTFRAME_WIDTH,OUTPUTFRAME_HEIGHT);
		setLocation((int)(COMPUTER_WIDTH/2),
					(int)(COMPUTER_HEIGHT/2 - OUTPUTFRAME_HEIGHT));
		
		//add JTextArea to JScrollPane will provide a scrollbar for it.
		JScrollPane scrollSpace = new JScrollPane(output);
		
		this.getContentPane().add(scrollSpace);
		
		setVisible(true);
	}

	private void initialOutputBox() {
		// Don't let the user change the output.
		output.setEditable(false);
		output.setBackground(OUTPUTBOX_BACKGROUND_COLOR);
		output.setBorder(BorderFactory.createLineBorder(OUTPUTBOX_BORDER_COLOR));
	}
	
	protected void clearOutputBox() {
		output.setText("");
	}
	
	protected void addLine(String line) {
		output.append(line);
	}
	

}