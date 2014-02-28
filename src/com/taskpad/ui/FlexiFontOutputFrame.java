/**
 * This class is going to take over
 * the original output frame because it is more flexible 
 * in font color and type.
 */

package com.taskpad.ui;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


public class FlexiFontOutputFrame extends OutputFrame {
	
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private final int DEFAULT_ALIGNMENT = StyleConstants.ALIGN_JUSTIFIED;
	private final String DEFAULT_FONT_TYPE = "Lucida Console";
	private final Color DEFAULT_COLOR_NORMAL = Color.BLACK;
	private final Color DEFAULT_COLOR_REMINDER = Color.RED;
	
	private final int MARGIN_TOP = 5;
	private final int MARGIN_LEFT = 5;
	private final int MARGIN_BOTTOM = 5;
	private final int MARGIN_RIGHT = 5;
	
	private JTextPane _output = new JTextPane();
	
	public FlexiFontOutputFrame(){
		super(true);
		initializeFfOutputFrame();
	}
	
	private void initializeFfOutputFrame() {	
		setUpFrame();
		initializeOutputBox();
		setUpScrollBar();
		
		//this.getContentPane().add(_output);
		this.getContentPane().add(_scrollBox);
		appendToPane("My Name is Too Good.\n", Color.RED);
	}
	
	@Override
	protected void initializeOutputBox() {
		_output = new JTextPane();
		
		// Don't let the user change the output.
		_output.setEditable(false);
		
		//JTextPane turn on wrapping by default
		
		_output.setBackground(OUTPUTBOX_BACKGROUND_COLOR);
		
		Border line = BorderFactory.createLineBorder(OUTPUTBOX_BORDER_COLOR);
		_output.setBorder(line);
		
		
		Insets margin = new Insets(MARGIN_TOP, MARGIN_LEFT, MARGIN_BOTTOM, MARGIN_RIGHT);
		_output.setMargin(margin);
		
	}
	
	//needs to override this because 
	//we declare _output as JTextPane, not JTextArea
	//so better write one more time to avoid confusion
	@Override
	protected void clearOutputBox() {
		_output.setText("");
	}
	
	@Override
	protected void addLine(String line) {
		appendToPane(line, DEFAULT_COLOR_NORMAL);
	}
	
	@Override
	protected void addReminder(String line) {
		appendToPane(line, DEFAULT_COLOR_REMINDER);
	}

	@Override
	protected void setUpScrollBar() {
		//JScrollPane provides scroll bar, so I add outputbox inside it.
		_scrollBox = new JScrollPane(_output);
		disableHorizontalScrollBar();
	}
	
	private void appendToPane(String msg, Color anyColor)
    {		
		//declare Style context to build the attribute set
        StyleContext sc = StyleContext.getDefaultStyleContext();
        
        AttributeSet aset = setUpAttributeSet(anyColor, sc);

        showMessage(msg, aset);
    }

	private void showMessage(String msg, AttributeSet aset) {
		System.err.println(msg);
		int len = _output.getDocument().getLength();
        _output.setCaretPosition(len);
        _output.setCharacterAttributes(aset, false);
        _output.replaceSelection(msg);
	}

	private AttributeSet setUpAttributeSet(Color anyColor, StyleContext sc) {
		AttributeSet aset = SimpleAttributeSet.EMPTY;
		aset = setFontColor(anyColor, sc, aset);
        aset = setFontType(sc, aset);
        aset = setFontAlignment(sc, aset);
		return aset;
	}

	private AttributeSet setFontAlignment(StyleContext sc, AttributeSet aset) {
		aset = sc.addAttribute(aset, StyleConstants.Alignment, DEFAULT_ALIGNMENT);
		return aset;
	}

	private AttributeSet setFontType(StyleContext sc, AttributeSet aset) {
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, DEFAULT_FONT_TYPE);
		return aset;
	}

	private AttributeSet setFontColor(Color anyColor, StyleContext sc, AttributeSet aset) {
		aset = sc.addAttribute(aset, StyleConstants.Foreground, anyColor);
		return aset;
	}
	
	public static void main(String... args){
        SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    new FlexiFontOutputFrame();
                }
            });
    }

}
