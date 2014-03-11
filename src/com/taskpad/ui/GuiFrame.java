package com.taskpad.ui;

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.taskpad.alarm.AlarmManager;

public abstract class GuiFrame extends JFrame implements NativeKeyListener, WindowListener{

	private static final long serialVersionUID = 1L;
	
	protected final double COMPUTER_WIDTH = 
			Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	protected final double COMPUTER_HEIGHT = 
			Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	protected GuiFrame(){
		initalizeGuiFrame();
	}

	private void initalizeGuiFrame() {
		//to disable the titlebar
		setUndecorated(true);
		
		addWindowListener(this);
		
		showWindow(true);
		
		//to clear the memory
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		 endProgram();
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
		if (isAltEndKey) {
			minimizeOrRestore();
		} else if (isShiftSpaceKey){
			hideOrShow();
		} else if (isEscapeKey){
			endProgram();
		} else if (isAltAKey){
			switchOffAlarm();
		} else if (isAltCKey){
			cancelAlarms();
		}
	}

	private void cancelAlarms() {
		try {
			AlarmManager.cancelAlarms();
		} catch (Exception e) {
			//do nothing
		}
	}

	private void switchOffAlarm() {
		try {
			AlarmManager.turnOffAlarm();
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

	private void endProgram() {
		//Clean up the native hook.
        GlobalScreen.unregisterNativeHook();
        System.runFinalization();
        System.exit(0);
	}
	
	private Runnable getVisibilityChanges() {
		Runnable changeVisibility = new Runnable(){
			public void run(){
				boolean isShown = isVisible() == true;
				boolean isHided = isVisible() == false;
				if (isShown){
					hide();
				} else if (isHided){
					show();
				}
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
