package com.taskpad.ui;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public abstract class GuiFrame extends JFrame implements NativeKeyListener, WindowListener{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	public GuiFrame(){
		addWindowListener(this);
	}
	
	protected void close(){
		dispose();
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
		 //Clean up the native hook.
        GlobalScreen.unregisterNativeHook();
        System.runFinalization();
        System.exit(0);
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		if (arg0.getKeyCode() == NativeKeyEvent.VK_ESCAPE) {
			Runnable changeState = getStateChanges();
            SwingUtilities.invokeLater(changeState);
            requestFocusInWindow();
		}
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
