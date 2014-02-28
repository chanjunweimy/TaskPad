package com.taskpad.ui;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;

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
	
	protected final static int OUTPUTFRAME_WIDTH = 350;
	protected final static int OUTPUTFRAME_HEIGHT = 150;
	
	//outputTextBox
	protected static JTextArea output = new JTextArea(5, 15);
	
	private JScrollPane _scrollSpace;
	
	public OutputFrame(){
		super();
		initialOutputBox();
		initialOutputFrame();
	}

	private void initialOutputFrame() {				
		setSize(OUTPUTFRAME_WIDTH,OUTPUTFRAME_HEIGHT);
		setLocation((int)(COMPUTER_WIDTH/2),
					(int)(COMPUTER_HEIGHT/2 - OUTPUTFRAME_HEIGHT));
		
		//add JTextArea to JScrollPane will provide a scrollbar for it.
		_scrollSpace = new JScrollPane(output);
		
		//disable horizontal scrollbar
		_scrollSpace.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.getContentPane().add(_scrollSpace);
	}


	private void initialOutputBox() {
		// Don't let the user change the output.
		output.setEditable(false);
		
		// Fix the maximum length of the line
		output.setLineWrap(true);
		
		output.setBackground(OUTPUTBOX_BACKGROUND_COLOR);
		output.setBorder(BorderFactory.createLineBorder(OUTPUTBOX_BORDER_COLOR));
	}
	
	protected void clearOutputBox() {
		output.setText("");
	}
	
	protected void addLine(String line) {
		output.append(line);
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		super.nativeKeyPressed(arg0);
		
		boolean isCtrlW= arg0.getKeyCode() == NativeKeyEvent.VK_W
				&& NativeInputEvent.getModifiersText(arg0.getModifiers()).equals(
	                    "Ctrl");
		boolean isCtrlS = arg0.getKeyCode() == NativeKeyEvent.VK_S				
				&& NativeInputEvent.getModifiersText(arg0.getModifiers()).equals(
	                    "Ctrl");

		
		if (isCtrlW){
			scrollUp();
			
		} else if (isCtrlS){
			scrollDown();
		}
	}

	private void scrollDown() {
		Runnable downScroller = new BarScroller(false, _scrollSpace.getVerticalScrollBar());
		SwingUtilities.invokeLater(downScroller);
	}

	private void scrollUp() {
		Runnable upScroller = new BarScroller(true, _scrollSpace.getVerticalScrollBar());
		SwingUtilities.invokeLater(upScroller);
	}
}