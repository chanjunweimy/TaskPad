package com.taskpad.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class MouseDragActioner implements MouseMotionListener{
	private static Point _mouseDownPoint = MousePressedDetector.getMouseDownPoint();
	private static Point _currPoint = null;
	private static Point _movePoint = null;
	private JFrame _movingFrame = null;
	
	protected MouseDragActioner(JFrame ListenFrame){
		setMovingFrame(ListenFrame);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		System.err.println("debug!!!");
		setCurrPoint(e.getLocationOnScreen());
		setMovePoint(new Point(_currPoint.x - _mouseDownPoint.x, _currPoint.y - _mouseDownPoint.y));
		_movingFrame.setLocation(_movePoint);
	}

	//unimplemented
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	private static void setCurrPoint(Point _currPoint) {
		MouseDragActioner._currPoint = _currPoint;
	}
	
	private static void setMovePoint(Point _movePoint) {
		MouseDragActioner._movePoint = _movePoint;
	}

	private void setMovingFrame(JFrame _movingFrame) {
		this._movingFrame = _movingFrame;
	}

}
