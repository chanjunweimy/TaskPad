//@author A0112084U

package com.taskpad.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;



/**
 * 
 * ===============DEPRECATED================
 * 
 * This class is deprecated as this class can only 
 * show text font with only one color.
 * 
 * But we need different font color for reminder...
 * JTextArea doesn't support this functionality 
 * so we have to implement a new class......
 * 
 * @category
 * Change to become a parent class
 * 
 * @see
 * FlexiFontOutputFrame
 * 
 */

public abstract class OutputFrame extends GuiFrame{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	//protected final Color OUTPUTBOX_BORDER_COLOR = 
	//		new Color(112, 48, 160);//light purple?
	protected final Color OUTPUTBOX_BORDER_COLOR = Color.white;
	protected final Color OUTPUTBOX_BACKGROUND_COLOR = 
			//new Color(242, 242, 242);//light grey I think
			new Color(240,248,255);		//Baby blue
	
	private final static int OUTPUTFRAME_WIDTH = 500;
	private final static int OUTPUTFRAME_HEIGHT = 350;
	
	private final Color DEFAULT_FONT_COLOR = Color.black;
	
	//outputTextBox
	private JTextArea _output = new JTextArea(5, 15);
	
	//children should have scroll bar too
	protected JScrollPane _scrollBox  = new JScrollPane();
	
	protected OutputFrame(){
		super();
		initializeOutputFrame();
	}
	
	protected OutputFrame(boolean inherit){
		super();
	}

	protected void setUpFrame() {
		setSize(OUTPUTFRAME_WIDTH, OUTPUTFRAME_HEIGHT);
		setLocation((int)(COMPUTER_WIDTH / 2 - OUTPUTFRAME_WIDTH / 2),
					(int)(COMPUTER_HEIGHT / 2 - OUTPUTFRAME_HEIGHT / 2 - InputFrame.getInitialHeight() / 2));
	}

	private void initializeOutputFrame() {				
		setUpFrame();
		
		initializeOutputBox();
		
		setUpScrollBar();

		this.getContentPane().add(_scrollBox);
	}

	protected void setUpScrollBar() {
		//JScrollPane provides scroll bar, so I add outputbox inside it.
		_scrollBox = new JScrollPane(_output);
		disableHorizontalScrollBar();
	}

	protected void disableHorizontalScrollBar() {
		_scrollBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}


	protected void initializeOutputBox() {
		// Don't let the user change the output.
		_output.setEditable(false);
		
		// Fix the maximum length of the line
		_output.setLineWrap(true);
		
		_output.setBackground(OUTPUTBOX_BACKGROUND_COLOR);
		
		Border line = BorderFactory.createLineBorder(OUTPUTBOX_BORDER_COLOR);
		_output.setBorder(line);
		
		initializeFont();
	}

	private void initializeFont() {
		Font font = new Font("Verdana", Font.BOLD, 12);
		_output.setFont(font);
		_output.setForeground(DEFAULT_FONT_COLOR);
	}
	
	protected void clearOutputBox() {
		_output.setText("");
	}
	
	protected void addLine(String line) {
		_output.append(line);
	}
	
	protected void addReminder(String line) {
		_output.append(line);
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
		Runnable downScroller = new BarScroller(false, _scrollBox.getVerticalScrollBar());
		SwingUtilities.invokeLater(downScroller);
	}

	private void scrollUp() {
		Runnable upScroller = new BarScroller(true, _scrollBox.getVerticalScrollBar());
		SwingUtilities.invokeLater(upScroller);
	}
	
	protected static int showWidth(){
		return OUTPUTFRAME_WIDTH;
	}
	
	abstract protected void addSelfDefinedLine(String line, Color c, boolean isBold);
	
	@Override
	protected void endProgram(){
		super.endProgram();
	}

	//@Override
	protected static int getInitialWidth(){
		return OUTPUTFRAME_WIDTH;
	}
	
	//@Override
	protected static int getInitialHeight(){
		return OUTPUTFRAME_HEIGHT;
	}
}