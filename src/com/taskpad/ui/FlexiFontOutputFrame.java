/**
 * This class is going to take over
 * the original output frame because it is more flexible 
 * in font color and type.
 */

package com.taskpad.ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


public class FlexiFontOutputFrame extends OutputFrame {

	private static final long serialVersionUID = 1L;

	private final int DEFAULT_ALIGNMENT = StyleConstants.ALIGN_JUSTIFIED;
	private final String DEFAULT_FONT_TYPE = "Lucida Console";
	private final int DEFAULT_FONT_SIZE = 10;
	private final Color DEFAULT_COLOR_NORMAL = Color.BLACK;
	private final Color DEFAULT_COLOR_REMINDER = Color.RED;

	private final int MARGIN_TOP = 5;
	private final int MARGIN_LEFT = 5;
	private final int MARGIN_BOTTOM = 5;
	private final int MARGIN_RIGHT = 5;
	
	private final String ERROR_BAD_LOCATION_EXCEPTION = "BadLocationException occurs";
	
	private JTextPane _outputBox = new JTextPane();
	
	private MousePressedDetector _pressDetect = new MousePressedDetector();
	private MouseMover _moveMouse = new MouseMover(this);
	

	protected FlexiFontOutputFrame()
	{
		super(true);
		initializeFlexiOutputFrame();
	}

	private void initializeFlexiOutputFrame() {
		setUpFrame();       
		initializeOutputBox();
		setUpScrollBar();
		getContentPane().add(_scrollBox);
	}

	@Override
	protected void initializeOutputBox() {
		// Don't let the user change the output.
		_outputBox.setEditable(false);

		_outputBox.setBackground(OUTPUTBOX_BACKGROUND_COLOR);

		// Fix the maximum length of the line
		setUpBorderAndMargin();
		
		//manually create an EditorKit that supports wrap
		//to make JTextPane supports wrap.
		//and set it to be JTextPane's editorKit
		_outputBox.setEditorKit(new WrapEditorKit());		
		
		//to make it movable
		_outputBox.addMouseListener(_pressDetect);
		_outputBox.addMouseMotionListener(_moveMouse);
		
		/* Testing
		appendToPane(_outputBox, "My Name is Too Good.\n", Color.RED);
		appendToPane(_outputBox, "I wish I could be ONE of THE BEST on ", Color.BLUE);
		appendToPane(_outputBox, "Stack", Color.DARK_GRAY);
		appendToPane(_outputBox, "Over", Color.MAGENTA);
		appendToPane(_outputBox, "flow", Color.ORANGE);
		*/
	}

	private void setUpBorderAndMargin() {
		Border border = BorderFactory.createLineBorder(OUTPUTBOX_BORDER_COLOR);
		Border margin =  BorderFactory.createEmptyBorder(MARGIN_TOP, MARGIN_LEFT, MARGIN_BOTTOM, MARGIN_RIGHT);
		CompoundBorder marginBorder = BorderFactory.createCompoundBorder(border, margin);
		_outputBox.setBorder(marginBorder);
	}

	@Override
	protected void setUpScrollBar() {
		//JScrollPane provides scroll bar, so I add outputbox inside it.
		_scrollBox = new JScrollPane(_outputBox);
		disableHorizontalScrollBar();
	}
	
	@Override
	protected void clearOutputBox() {
		_outputBox.setText("");
	}

	@Override
	protected void addLine(String line) {
		append(line, DEFAULT_COLOR_NORMAL);
	}
	
	@Override
	protected void addReminder(String line) {
		boolean isBold = true;
		append(line, DEFAULT_COLOR_REMINDER, isBold);
	}
	
	@Override
	protected void addSelfDefinedLine(String line, Color c, boolean isBold) {
		append(line, c, isBold);
	}
	
	/*
	 * Method to replace appendToPane()
	 * this method is easier to write
	 * and can handle setEditable(false)
	 * without changing its status anymore!
	 */
	private void append(String msg, Color c){
		boolean isBold = false;
		append(msg, c, isBold);
	}
	
	private void append(String msg, Color c, boolean isBold){
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = setUpAttributeSet(c, sc, isBold);
		StyledDocument doc = _outputBox.getStyledDocument();
		int len = doc.getLength();
		printMessage(msg, aset, doc, len);
	}

	private void printMessage(String msg, AttributeSet aset,
			StyledDocument doc, int len) {
		try {
			doc.insertString(len, msg, aset);
		} catch (BadLocationException e) {
			e.printStackTrace();
			System.err.println(ERROR_BAD_LOCATION_EXCEPTION);
		}
	}
	
	
	
	/*
	 * appendToPane() finally works
	 * but it is obviously not the best way
	 * so I implement another method
	 */
	/*
	private void appendToPane(String msg, Color c){  //DEPRECATED
		//let us to modify the output
		_outputBox.setEditable(true);
		
		appendToPane(_outputBox, msg, c);
	
		//prevent user from modifying the output
		_outputBox.setEditable(false);
	}
	
	private void appendToPane(JTextPane tp, String msg, Color c){ //DEPRECATED
		StyleContext sc = StyleContext.getDefaultStyleContext();
		
		AttributeSet aset = setUpAttributeSet(c, sc);

		showMessage(tp, msg, aset);
	}

	private void showMessage(JTextPane tp, String msg, AttributeSet aset) { //DEPRECATED
		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}*/

	private AttributeSet setUpAttributeSet(Color c, StyleContext sc, boolean isBold) {
		AttributeSet aset = SimpleAttributeSet.EMPTY;
		aset = setFontColor(c, sc, aset);
		aset = setFontType(sc, aset);
		aset = setAlignment(sc, aset);
		aset = setFontSize(sc, aset);
		aset = setBold(sc, isBold, aset);
		return aset;
	}

	private AttributeSet setBold(StyleContext sc, boolean isBold,
			AttributeSet aset) {
		return sc.addAttribute(aset, StyleConstants.Bold, isBold);
	}

	private AttributeSet setFontSize(StyleContext sc, AttributeSet aset) {
		return sc.addAttribute(aset, StyleConstants.FontSize, DEFAULT_FONT_SIZE);
	}

	private AttributeSet setAlignment(StyleContext sc, AttributeSet aset) {
		return sc.addAttribute(aset, StyleConstants.Alignment, DEFAULT_ALIGNMENT);
	}

	private AttributeSet setFontType(StyleContext sc, AttributeSet aset) {
		return sc.addAttribute(aset, StyleConstants.FontFamily, DEFAULT_FONT_TYPE);
	}

	private AttributeSet setFontColor(Color c, StyleContext sc, AttributeSet aset) {
		return sc.addAttribute(aset, StyleConstants.Foreground, c);
	}

	@Override
	protected void endProgram() {
		super.endProgram();
		
		//clear every listener before closing
		_outputBox.removeMouseListener(_pressDetect);
		_outputBox.removeMouseMotionListener(_moveMouse);
	}
	
	
}
