package com.taskpad.ui;

//@author A0112084U

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

/**
 * 
 * ===================DEPRECATED========================== 
 *  *
 * @category
 * MouseDragActioner is the actual class to do the moving
 * with the help of MousePressedDetector.
 * 
 * @deprecated
 * DEPRECATED: replaced by ComponentMover.java
 *
 */

public class MouseMover implements MouseMotionListener{
	private static Point _mouseDownPoint = MousePressedDetector.getMouseDownPoint();
	private static Point _currPoint = null;
	private static Point _movePoint = null;
	private JFrame _movingFrame = null;
	
	/**
	 * @deprecated
	 *  DEPRECATED: private constructor shows that it cannot be used anymore.
	 * @param ListenFrame
	 */
	private MouseMover(JFrame ListenFrame){
		setMovingFrame(ListenFrame);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		initializedPoints(e);
		move();
	}

	private void initializedPoints(MouseEvent e) {
		setCurrPoint(e.getLocationOnScreen());
		setMouseDownPoint(MousePressedDetector.getMouseDownPoint());
		setMovePoint(new Point(_currPoint.x - _mouseDownPoint.x, _currPoint.y - _mouseDownPoint.y));
	}

	private void move() {
		_movingFrame.setLocation(_movePoint);
	}

	private void setMouseDownPoint(Point mouseDownPoint) {
		_mouseDownPoint = mouseDownPoint;
	}

	private static void setCurrPoint(Point _currPoint) {
		MouseMover._currPoint = _currPoint;
	}
	
	private static void setMovePoint(Point _movePoint) {
		MouseMover._movePoint = _movePoint;
	}

	private void setMovingFrame(JFrame _movingFrame) {
		this._movingFrame = _movingFrame;
	}

	//unimplemented
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
