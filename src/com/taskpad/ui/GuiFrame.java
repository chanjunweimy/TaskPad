package com.taskpad.ui;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public abstract class GuiFrame extends JFrame implements NativeKeyListener, WindowListener{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	public GuiFrame(){
		//to disable the titlebar
		setUndecorated(true);
		
		addWindowListener(this);
		
		//to make hotkey works
		showWindow(true);
		showWindow(false);
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
		boolean isAltHomeKey = arg0.getKeyCode() == NativeKeyEvent.VK_HOME 
				&& NativeInputEvent.getModifiersText(arg0.getModifiers()).
				equals("Alt");
		boolean isAltEndKey = arg0.getKeyCode() == NativeKeyEvent.VK_END 
				&& NativeInputEvent.getModifiersText(arg0.getModifiers()).
				equals("Alt");
		if (isAltEndKey) {
			minimizeOrRestore();
		} else if (isAltHomeKey){
			hideOrShow();
		} else if (isEscapeKey){
			endProgram();
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
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
