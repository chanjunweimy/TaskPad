//@author A0112084U

package com.taskpad.ui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;

public class InputFrame extends GuiFrame{
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 6500266679828724479L;

	private static final Color INPUTBOX_BACKGROUND_COLOR = 
			//new Color(219, 219, 219); //this is grey color
			new Color(255,248,220);		//Cornsilk
	
	//inputTextBox
	private static JTextField _input = new JTextField(15);
	
	private static final int INPUTFRAME_WIDTH = 550;
	private static final int INPUTFRAME_HEIGHT = 33;
	private static final int HISTORY_LIMIT = 3000;
	
	private TextFieldListener _seeText = new TextFieldListener(this);
	private ComponentMover _moveInputBox = new ComponentMover(this);
	
	//protected static ArrayList<String> _inputHistory = new ArrayList<String>();
	private LinkedList<String> _historyInput = new LinkedList<String>();
	private int _historyCount = 0;
	
	private static final String EMPTY = "";
	
	protected InputFrame(){
		super();
		initializeInputFrame();
	}

	private void initializeInputFrame() {		
		setUpFrame();
		
		initializeInputBox();
		this.getContentPane().add(_input);
	}

	private void setUpFrame() {
		setSize(INPUTFRAME_WIDTH, INPUTFRAME_HEIGHT);
		setLocation((int)(COMPUTER_WIDTH / 2 - INPUTFRAME_WIDTH / 2),
					(int)(COMPUTER_HEIGHT / 2 + OutputFrame.getInitialHeight() / 2 - INPUTFRAME_HEIGHT / 2));
		setVisible(true);
		_isHiding = false;
	}

	private void initializeInputBox() {
		//ready to receive key
		_input.addKeyListener(this);
		
		//ready to receive input
		_input.addActionListener(_seeText);
		
		//ready to move
		_moveInputBox.registerComponent(_input);
		//_input.addMouseListener(_mousePress);      DEPRECATED
		//_input.addMouseMotionListener(_mouseMove); DEPRECATED
		
		_input.setBackground(INPUTBOX_BACKGROUND_COLOR);
		
		_input.setFocusable(true);
		_input.setRequestFocusEnabled(true);
		
		requestFocusOnInputBox();
	}
	
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		requestFocusOnInputBox();
	}       

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		super.nativeKeyPressed(arg0);
		
		boolean isCtrlI = arg0.getKeyCode() == NativeKeyEvent.VK_I
	            && NativeInputEvent.getModifiersText(arg0.getModifiers()).equals(
	                    "Ctrl");
		
		
		if (isCtrlI){
			requestFocusOnInputBox();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		boolean isUpKey = arg0.getKeyCode() == KeyEvent.VK_UP;
		boolean isDownKey = arg0.getKeyCode() == KeyEvent.VK_DOWN;
		
		if (isUpKey){
			showPastCommands();
		} else if (isDownKey){
			showNextCommands();
		}
	}
	
	protected void addHistory(String inputString){
		int size = _historyInput.size();
		if (size >= HISTORY_LIMIT){
			_historyInput.removeLast();
		}
		_historyInput.addFirst(inputString);
		
		initializeHistory();
	}
	
	private void initializeHistory(){
		_historyCount = 0;
	}
	
	private void showPastCommands(){ 
		int size = _historyInput.size();
		if (isBoundary()){
			return;
		}
		
		String outputString = _historyInput.get(_historyCount);
		_input.setText(outputString);
		
		boolean isNotMaximum = _historyCount < size - 1;
		if(isNotMaximum){
			_historyCount++;
		}
	}

	/**
	 * @param size
	 * @return
	 */
	private boolean isBoundary() {
		int size = _historyInput.size();
		return _historyCount >= size && _historyCount < 0 || size == 0;
	}
	
	private void showNextCommands(){
		if (isBoundary()){
			return;
		}
		

		String outputString = EMPTY;
		
		boolean isNotMinimum = _historyCount > 0;
		if (isNotMinimum){
			_historyCount--;
			outputString = _historyInput.get(_historyCount);
		} 
		_input.setText(outputString);	
	}
	
	@Override
	protected void requestFocusOnInputBox() {
		Runnable inputBoxFocus = new Runnable(){
			@Override
			public void run(){
				_input.grabFocus();  
				_input.requestFocusInWindow();
			}
		};
		SwingUtilities.invokeLater(inputBoxFocus);
	}
	
	protected String getText(){
		return _input.getText();
	}
	
	protected void reset(){
		_input.setText(InputFrame.EMPTY);
	}
	
	protected JTextField getInputBox(){
		return _input;
	}
	
	//@Override
	protected static int getInitialWidth(){
		return INPUTFRAME_WIDTH;
	}
	
	//@Override
	protected static int getInitialHeight(){
		return INPUTFRAME_HEIGHT;
	}
	
	@Override
	protected void endProgram(){
		super.endProgram();
		
		//clear every listener before closing
		_input.removeActionListener(_seeText);
		
		_moveInputBox.deregisterComponent(_input);
		//_input.removeMouseListener(_mousePress);      DEPRECATED
		//_input.removeMouseMotionListener(_mouseMove); DEPRECATED
	}

	protected void passInput(String inputString) {
		GuiManager.passInput(inputString);
	}
	
	/**
	 * @deprecated
	 * @param line
	 */
	protected void setLine(String line) {
		_input.setText(line);
	}
}
