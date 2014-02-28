/**
 * This class is going to take over
 * the original output frame because it is more flexible 
 * in font color and type.
 */

package com.taskpad.ui;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
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

	private JTextPane _outputBox = new JTextPane();

	private JScrollPane sBar;

	public FlexiFontOutputFrame()
	{
		super(true);
		initializeFlexiOutputFrame();
	}

	private void initializeFlexiOutputFrame() {
		setUpFrame();       
		initializeOutputBox();
		setUpScrollBar();
		this.getContentPane().add(_scrollBox);
	}

	@Override
	protected void initializeOutputBox() {
		_outputBox = new JTextPane();          

		// Don't let the user change the output.
		_outputBox.setEditable(false);

		//JTextPane turn on wrapping by default

		_outputBox.setBackground(OUTPUTBOX_BACKGROUND_COLOR);

		Border line = BorderFactory.createLineBorder(OUTPUTBOX_BORDER_COLOR);
		_outputBox.setBorder(line);


		Insets margin = new Insets(MARGIN_TOP, MARGIN_LEFT, MARGIN_BOTTOM, MARGIN_RIGHT);
		_outputBox.setMargin(margin);

		
		/* Testing
		appendToPane(_outputBox, "My Name is Too Good.\n", Color.RED);
		appendToPane(_outputBox, "I wish I could be ONE of THE BEST on ", Color.BLUE);
		appendToPane(_outputBox, "Stack", Color.DARK_GRAY);
		appendToPane(_outputBox, "Over", Color.MAGENTA);
		appendToPane(_outputBox, "flow", Color.ORANGE);
		*/
	}

	@Override
	protected void setUpScrollBar() {
		//JScrollPane provides scroll bar, so I add outputbox inside it.
		_scrollBox = new JScrollPane(_outputBox);
		disableHorizontalScrollBar();
	}
	
	//needs to override this because 
	//we declare _output as JTextPane, not JTextArea
	//so better write one more time to avoid confusion
	@Override
	protected void clearOutputBox() {
		_outputBox.setText("");
	}
	
	@Override
	protected void addLine(String line) {
		appendToPane(line, DEFAULT_COLOR_NORMAL);
	}

	private void appendToPane(String msg, Color c){
		appendToPane(_outputBox, msg, c);		
	}
	
	private void appendToPane(JTextPane tp, String msg, Color c){
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}
}
