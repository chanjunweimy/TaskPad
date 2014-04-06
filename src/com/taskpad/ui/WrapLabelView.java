//@author A0112084U

package com.taskpad.ui;



/**
 * 
 * 
 * This class is created to fix
 * the line wrap problem in JTextPane Java 1.7
 * Hope it works!
 * 
 * WrapLabelView: wrap the view to the text.
 * 
 * The logic is following when a row is bigger 
 * than available width the row must be broken. 
 * The first part (row) should be less than available width. 
 * 
 * The rest again is measured and can be broken once more 
 * if it's bigger than available width.
 * 
 */

import javax.swing.text.Element;
import javax.swing.text.LabelView;
import javax.swing.text.View;

public class WrapLabelView extends LabelView {
	/**
	 * to make sure it cannot be used by other package
	 * @param elem
	 */
    protected WrapLabelView(Element elem) {
        super(elem);
    }

    
    /**
	 * Size of content is defined by minimumSpan
	 * (can't be less than minimal possible). 
	 * So when it's in a scroll scroll measures width and asks for minimal span.
	 * Then width of JScrollPane's content = width of viewport 
	 * (or if min width is bigger =min width).
	 */
    
    @Override
    public float getMinimumSpan(int axis) {
        switch (axis) {
            case View.X_AXIS:
                return 0;
            case View.Y_AXIS:
                return super.getMinimumSpan(axis);
            default:
                throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }

}