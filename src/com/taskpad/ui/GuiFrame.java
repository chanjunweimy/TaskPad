//@author A0112084U

package com.taskpad.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * For implementing HotKeys for the GuiFrame
 */

public abstract class GuiFrame extends JFrame implements NativeKeyListener, WindowListener, KeyListener{
	
	private final static Logger LOGGER = Logger.getLogger("TaskPad");
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 1179398807003068461L;
	  
	protected final double COMPUTER_WIDTH = 
			Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	protected final double COMPUTER_HEIGHT = 
			Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	protected final Color ROOTPANE_BORDER_COLOR = 
			//new Color(120, 48, 160);//light purple?
			//new Color(41,36,33);	//Black
			//Color.gray;
			new Color(41,36,33);
	private final int ROOTPANE_BORDER_THICKNESS = 2;
	private LineBorder BORDER_ROOTPANE = new LineBorder(ROOTPANE_BORDER_COLOR, ROOTPANE_BORDER_THICKNESS);
	private ComponentResizer _resizer = new ComponentResizer();
	  
	protected boolean _isHiding = false;
	
	protected GuiFrame(){
		setupLogger();
		initalizeGuiFrame();
	}

	/**
	 * 
	 */
	private void setupLogger() {
		LOGGER.setLevel(Level.INFO);
				
		LOGGER.info("Setting up GuiFrame");
	}

	private void initalizeGuiFrame() {
		//to disable the titlebar
		setUndecorated(true);
		
		setupBorder();
		
		setUpResizer();
				                  
		addWindowListener(this);
		
		showWindow(true);	
		
		setAlwaysOnTop(true);
			   		
		//to clear the memory
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * all swing object should be invoke later
	 */
	private void setupBorder() {
		Runnable runBorderSetup = new Runnable(){
			@Override
			public void run(){
				LOGGER.info(this.toString());
				
				getRootPane().setBorder(BORDER_ROOTPANE);
			}
		};
		SwingUtilities.invokeLater(runBorderSetup);
	}      

	private void setUpResizer() {
		_resizer.registerComponent(this);

		
		Runnable runResizerSetup = new Runnable(){
			@Override
			public void run(){
				LOGGER.info("ROOTPANE_BORDER_THICKNESS: " + ROOTPANE_BORDER_THICKNESS);
				
				_resizer.setDragInsets(ROOTPANE_BORDER_THICKNESS * 2);  
			}
		};
		SwingUtilities.invokeLater(runResizerSetup);
		
		
		LOGGER.info("Have set up resizer");
	}

	protected void showUp(final GuiFrame visibleFrame){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				setSize(visibleFrame.getSize());
				setLocation(visibleFrame.getLocation());
				setVisible(true);
				_isHiding = false;			
			}
		});
		
		LOGGER.info("showing GuiFrame");
	}
	     
	protected void close(){ 
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				dispose();		
			}
		});
		
		LOGGER.info("CLOSE!");
	}
	
	protected void hideWindow(){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				setVisible  (false);
				_isHiding = true;		
			}
		});
		
		
		LOGGER.info("hide!");
 	}
	
	protected void showWindow(final boolean isVisible){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				setVisible(isVisible);	
			}
		});
		
		LOGGER.info("changed visibility: " + isVisible);
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		//Initialize native hook.
        try {
        		LOGGER.info("Initializing native hook");
        		
            	GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
        		LOGGER.severe("There was a problem registering the native hook.\n");
        		LOGGER.severe(ex.getMessage());
                ex.printStackTrace();

                System.exit(1);
        }     

        GlobalScreen.getInstance().addNativeKeyListener(this);
        //requestFocusInWindow(); should not request focus here
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
	
	/**
	 * override by inputFrame
	 */
	protected void requestFocusOnInputBox() {
	}
	

	private void cancelAlarms() {
    	LOGGER.info("Canceling Alarm...");
		
    	Runnable runCancel = new Runnable(){
			@Override
			public void run(){
				GuiManager.cancelAlarms();
			}
		};
		SwingUtilities.invokeLater(runCancel);
	}

	private void switchOffAlarm() {
		try {
    		LOGGER.info("Switching off Alarm...");
			
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
		LOGGER.info("Program HALT");
		
		//Clean up every listener
        GlobalScreen.unregisterNativeHook();
        _resizer.deregisterComponent(this);
	}
	
	private void exitProgram() {
		Runnable runExit = new Runnable(){
			@Override
			public void run(){
				LOGGER.info(this.toString());
				LOGGER.severe("EXIT NOW!!!");
				
				System.runFinalization();
			    System.exit(0);
			}
		};
		SwingUtilities.invokeLater(runExit);
	}
	
	private Runnable getVisibilityChanges() {
		Runnable changeVisibility = new Runnable(){
			@Override
			public void run(){
				LOGGER.info(this.toString());
				LOGGER.info("hiding? " + _isHiding);
				
				if (_isHiding){
					return;
				}
				
				boolean isShown = isVisible() == true;
				boolean isHided = isVisible() == false;
				if (isShown){
				  	hide();
				} else if (isHided){
					show();
				}
			}
 
			private void show() {
				LOGGER.info("SHOW!");
				
				showWindow(true);
				setState(Frame.NORMAL);
				
				requestFocusOnInputBox();
			}

			private void hide() {
				LOGGER.info("HIDE!");

				showWindow(false);
			}
		};
		return changeVisibility;
	}

	private Runnable getStateChanges() {
		Runnable changeState = new Runnable(){
			@Override
			public void run(){
				LOGGER.info(this.toString());
				
				boolean isMinimized = getExtendedState() == Frame.ICONIFIED;
				boolean isRestored = getExtendedState() == Frame.NORMAL;
				if (isMinimized){
					restore();
				} else if (isRestored){
					minimize();
				}
			}

			private void minimize() {
				LOGGER.info("minimize!");
				setState(Frame.ICONIFIED);
			}

			private void restore() {
				LOGGER.info("restore!");
				setState(Frame.NORMAL);
			}
		};
		return changeState;
	}
	
	//abstract protected int getInitialWidth();
	//abstract protected int getInitialHeight();
	
	
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

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {		
	}

	@Override
	public void keyReleased(KeyEvent e) {		
	}
}
