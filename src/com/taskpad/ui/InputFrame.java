package com.taskpad.ui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;



public class InputFrame extends GuiFrame{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;  
	
	private final Color INPUTBOX_BACKGROUND_COLOR = 
			new Color(219, 219, 219);
	
	private final double COMPUTER_WIDTH = 
			Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	
	private final double COMPUTER_HEIGHT = 
			Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	//inputTextBox
	protected static JTextField input = new JTextField(15);
	
	protected final static int INPUTFRAME_WIDTH = 350;
	protected final static int INPUTFRAME_HEIGHT = 30;
	
	public InputFrame(){
		super();
		initialInputBox();
		initialInputFrame();
	}

	private void initialInputFrame() {
		setSize(INPUTFRAME_WIDTH,INPUTFRAME_HEIGHT);
		
		int leftShift = INPUTFRAME_WIDTH - OutputFrame.OUTPUTFRAME_WIDTH;
		leftShift /= 2;
		
		setLocation((int)(COMPUTER_WIDTH/2 - leftShift),
					(int)(COMPUTER_HEIGHT/2));
		
		this.getContentPane().add(input);
		
		input.requestFocus();        // start with focus on this field
	}

	private void initialInputBox() {
		inputboxReadyForEvent();
		
		input.setBackground(INPUTBOX_BACKGROUND_COLOR);//grey color
	}

	private void inputboxReadyForEvent() {
		TextFieldListener tfListener = new TextFieldListener();
		input.addActionListener(tfListener);
	}
	
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		input.requestFocus();
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		super.nativeKeyPressed(arg0);
		
		boolean isCtrlI = arg0.getKeyCode() == NativeKeyEvent.VK_I
	            && NativeInputEvent.getModifiersText(arg0.getModifiers()).equals(
	                    "Ctrl");
		
		if (isCtrlI){
			Runnable inputBoxFocus = requestFocusOnInputBox();
            SwingUtilities.invokeLater(inputBoxFocus);
		} 
	}
	
	private Runnable requestFocusOnInputBox() {
		Runnable inputBoxFocus = new Runnable(){
			public void run(){
				input.requestFocus();
			}
		};
		return inputBoxFocus;
	}
}
