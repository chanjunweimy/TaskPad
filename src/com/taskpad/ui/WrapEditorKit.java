//@author A0112084U

package com.taskpad.ui;



import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

/**
 * 
 * 
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
 * 
 */
public class WrapEditorKit extends StyledEditorKit {
    /**
	 * generated
	 */
	private static final long serialVersionUID = -2439803723035686677L;

	private ViewFactory _defaultFactory = new WrapColumnFactory();
	
	/**
	 * To make sure it cannot be used by other package
	 */
	protected WrapEditorKit(){
	}
	
	@Override
    public ViewFactory getViewFactory() {
        return _defaultFactory;
    }

}