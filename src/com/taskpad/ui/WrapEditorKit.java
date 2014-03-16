package com.taskpad.ui;

/**
 * 
 * @author Jun
 * This class is created to fix
 * the line wrap problem in JTextPane Java 1.7
 * Hope it works!
 * 
 */

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

public class WrapEditorKit extends StyledEditorKit {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2439803723035686677L;

	ViewFactory defaultFactory=new WrapColumnFactory();
    public ViewFactory getViewFactory() {
        return defaultFactory;
    }

}