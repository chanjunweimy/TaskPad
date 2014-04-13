//@author A0112084U

package com.taskpad.ui;



import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 
 * ===================DEPRECATED==========================
 * 
 * @category
 * MousePressedDetector is created to detect whether 
 * the mouse has clicked on the boxes or not.
 * 
 * It is a helper class to move JFrame......
 * 
 * @deprecated
 * DEPRECATED: replaced by ComponentMover.java
 *
 */

public class MousePressedDetector implements MouseListener {
	private static Point _mouseDownPoint = null;
	
	/** 
	 * @deprecated
	 * DEPRECATED: private constructor shows that it cannot be used anymore.
	 * To make sure it cannot be used by other package
	 */
	private MousePressedDetector(){
	}

	@Override
	public void mousePressed(MouseEvent e) {
		setMouseDownPoint(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		setMouseDownPoint(null);
	}
	
	protected static Point getMouseDownPoint() {
		return _mouseDownPoint;
	}
	

	private void setMouseDownPoint(Point _mouseDownPoint) {
		MousePressedDetector._mouseDownPoint = _mouseDownPoint;
	}

	
	//unimplemented methods at below:
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
