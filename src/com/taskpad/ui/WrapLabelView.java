package com.taskpad.ui;

/**
 * 
 * @author Jun
 * This class is created to fix
 * the line wrap problem in JTextPane Java 1.7
 * Hope it works!
 * 
 */

import javax.swing.text.Element;
import javax.swing.text.LabelView;
import javax.swing.text.View;

public class WrapLabelView extends LabelView {
    public WrapLabelView(Element elem) {
        super(elem);
    }

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