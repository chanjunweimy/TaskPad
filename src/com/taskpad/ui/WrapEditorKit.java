package com.taskpad.ui;

/**
 * 
 * @author Jun
 * This class is created to fix
 * the line wrap problem in JTextPane Java 1.7
 * Hope it works!
 * 
 * WrapEditorKit:
 * This is the set of things needed by a text component 
 * to be a reasonably functioning editor for some type of text document. 
 * This implementation provides a default implementation which treats 
 * text as styled text and provides a minimal set of actions for editing styled text, 
 * and we add the viewFactory that we manually create to here,
 * in order to connect it to JTextPane
 * 
 */

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

public class WrapEditorKit extends StyledEditorKit {
    /**
	 * generated
	 */
	private static final long serialVersionUID = -2439803723035686677L;

	ViewFactory defaultFactory = new WrapColumnFactory();
	
	@Override
    public ViewFactory getViewFactory() {
        return defaultFactory;
    }

}