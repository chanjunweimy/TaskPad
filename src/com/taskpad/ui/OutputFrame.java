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
		
		this.getContentPane().add(_scrollSpace);
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
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		super.nativeKeyPressed(arg0);
		
		boolean isUpArrow = arg0.getKeyCode() == NativeKeyEvent.VK_UP;
		boolean isDownArrow = arg0.getKeyCode() == NativeKeyEvent.VK_DOWN;
		boolean isLeftArrow = arg0.getKeyCode() == NativeKeyEvent.VK_LEFT;
		boolean isRightArrow = arg0.getKeyCode() == NativeKeyEvent.VK_RIGHT;
		boolean isCtrlO = arg0.getKeyCode() == NativeKeyEvent.VK_O
	            && NativeInputEvent.getModifiersText(arg0.getModifiers()).equals(
	                    "Ctrl");
		
		if (isUpArrow){
			Runnable upScroller = new BarScroller(true, _scrollSpace.getVerticalScrollBar());
			SwingUtilities.invokeLater(upScroller);
			
		} else if (isDownArrow){
			Runnable downScroller = new BarScroller(false, _scrollSpace.getVerticalScrollBar());
			SwingUtilities.invokeLater(downScroller);
			
		} else if (isLeftArrow){
			Runnable leftScroller = new BarScroller(true, _scrollSpace.getHorizontalScrollBar());
			SwingUtilities.invokeLater(leftScroller);
			
		} else if (isRightArrow){
			Runnable rightScroller = new BarScroller(false, _scrollSpace.getHorizontalScrollBar());
			SwingUtilities.invokeLater(rightScroller);
			
		} else if (isCtrlO){
			Runnable outputBoxFocus = requestFocusOnOutputBox();
            SwingUtilities.invokeLater(outputBoxFocus);
		}
	}


	private Runnable requestFocusOnOutputBox() {
		Runnable outputBoxFocus = new Runnable(){
			public void run(){
				output.requestFocus();
			}
		};
		return outputBoxFocus;
	}

}