package com.taskpad.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MousePressedDetector implements MouseListener {
	private static Point _mouseDownPoint = null;
	
	/**
	 * To make sure it cannot be used by other package
	 */
	protected MousePressedDetector(){
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.err.println("debug!!!fff");
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
