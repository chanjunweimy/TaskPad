package com.taskpad.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public abstract class GuiFrame extends JFrame implements NativeKeyListener, WindowListener{

	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 1179398807003068461L;
	
	  
	protected final double COMPUTER_WIDTH = 
			Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	protected final double COMPUTER_HEIGHT = 
			Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	protected final Color ROOTPANE_BORDER_COLOR = 
			new Color(112, 48, 160);//light purple?
	private final int ROOTPANE_BORDER_THICKNESS = 2;
	private LineBorder BORDER_ROOTPANE = new LineBorder(ROOTPANE_BORDER_COLOR, ROOTPANE_BORDER_THICKNESS);
	private ComponentResizer _resizer = new ComponentResizer();
	
	protected GuiFrame(){
		initalizeGuiFrame();
	}

	private void initalizeGuiFrame() {
		//to disable the titlebar
		setUndecorated(true);
		
		getRootPane().setBorder(BORDER_ROOTPANE);
		
		_resizer.registerComponent(this);
		_resizer.setDragInsets(ROOTPANE_BORDER_THICKNESS * 2);
		
		addWindowListener(this);
		
		showWindow(true);
				
		focusInputBox();		
		
		//to clear the memory
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void focusInputBox() {
		//to make it focus to input box
		minimizeOrRestore();
		minimizeOrRestore();
	}
	
	protected void close(){
		dispose();
	}
	
	protected void showWindow(boolean isVisible){
		setVisible(isVisible);
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		//Initialize native hook.
        try {
                GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
                System.err.println("There was a problem registering the native hook.");
                System.err.println(ex.getMessage());
                ex.printStackTrace();

                System.exit(1);
        }

        GlobalScreen.getInstance().addNativeKeyListener(this);
        requestFocusInWindow();
	}
	
	@Override
	public void windowClosed(WindowEvent arg0) {
		//end Program to disable every listeners! Exit Program to really exit it.
		endProgram();
	    exitProgram();
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		
		boolean isEscapeKey = arg0.getKeyCode() == NativeKeyEvent.VK_ESCAPE;
		boolean isShiftSpaceKey = arg0.getKeyCode() == NativeKeyEvent.VK_SPACE 
				&& NativeInputEvent.getModifiersText(arg0.getModifiers()).
				equals("Shift");
		boolean isAltEndKey = arg0.getKeyCode() == NativeKeyEvent.VK_END 
				&& NativeInputEvent.getModifiersText(arg0.getModifiers()).
				equals("Alt");
		boolean isAltAKey = arg0.getKeyCode() == NativeKeyEvent.VK_A 
				&& NativeInputEvent.getModifiersText(arg0.getModifiers()).
				equals("Alt");
		boolean isAltCKey = arg0.getKeyCode() == NativeKeyEvent.VK_C
				&& NativeInputEvent.getModifiersText(arg0.getModifiers()).
				equals("Alt");
		
		/**
		 * @author Jun
		 * we will disable some keys when TaskPad is in
		 * hiding mode
		 */
		isEscapeKey = isEscapeKey && isVisible();
		isAltEndKey = isAltEndKey && isVisible();
		isAltCKey = isAltCKey && isVisible();
		
		if (isAltEndKey) {
			minimizeOrRestore();
		} else if (isShiftSpaceKey){
			hideOrShow();
		} else if (isEscapeKey){
			endProgram();
			exitProgram();
		} else if (isAltAKey){
			switchOffAlarm();
		} else if (isAltCKey){
			cancelAlarms();
		}
	}

	private void cancelAlarms() {
		try {
			GuiManager.cancelAlarms();
		} catch (Exception e) {
			//do nothing
		}
	}

	private void switchOffAlarm() {
		try {
			GuiManager.turnOffAlarm();
		} catch (Exception e) {
			//System.err.println(e.getMessage());
			//do nothing
		}
	}

	private void hideOrShow() {
		Runnable changeVisibility = getVisibilityChanges();
		SwingUtilities.invokeLater(changeVisibility);
	}

	private void minimizeOrRestore() {
		Runnable changeState = getStateChanges();
		SwingUtilities.invokeLater(changeState);
	}

	protected void endProgram() {
		//Clean up every listener
        GlobalScreen.unregisterNativeHook();
        _resizer.deregisterComponent(this);
	}
	
	private void exitProgram() {
		System.runFinalization();
	    System.exit(0);
	}
	
	private Runnable getVisibilityChanges() {
		Runnable changeVisibility = new Runnable(){
			@Override
			public void run(){
				boolean isShown = isVisible() == true;
				boolean isHided = isVisible() == false;
				if (isShown){
				  	hide();
				} else if (isHided){
					show();
				}
				focusInputBox();
			}
 
			private void show() {
				showWindow(true);
				setState(Frame.NORMAL);
			}

			private void hide() {
				showWindow(false);
			}
		};
		return changeVisibility;
	}

	private Runnable getStateChanges() {
		Runnable changeState = new Runnable(){
			@Override
			public void run(){
				boolean isMinimized = getExtendedState() == Frame.ICONIFIED;
				boolean isRestored = getExtendedState() == Frame.NORMAL;
				if (isMinimized){
					restore();
				} else if (isRestored){
					minimize();
				}
			}

			private void minimize() {
				setState(Frame.ICONIFIED);
			}

			private void restore() {
				setState(Frame.NORMAL);
			}
		};
		return changeState;
	}
	
	abstract protected int getInitialWidth();
	abstract protected int getInitialHeight();
	
	
	//won't implement
	@Override
	public void windowActivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		
	}
}
