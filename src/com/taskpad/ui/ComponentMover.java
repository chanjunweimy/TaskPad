package com.taskpad.ui;

//@author A0112084U

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class ComponentMover extends MouseAdapter{
	private Point _mouseDownPoint = null;
	private Point _currPoint = null;
	private Point _movePoint = null;
	private JFrame _movingFrame = null;
	
	/**
	 * To make sure it cannot be used by other package
	 */
	protected ComponentMover(JFrame ListenFrame){
		setMovingFrame(ListenFrame);
	}

	/**
	 *  Remove listeners from the specified component
	 *
	 *  @param component  the component the listeners are removed from
	 */
	protected void deregisterComponent(Component... components){
		for (Component component : components){
			component.removeMouseListener(this);
			component.removeMouseMotionListener(this);
		}
	}

	/**
	 *  Add the required listeners to the specified component
	 *
	 *  @param component  the component the listeners are added to
	 */
	protected void registerComponent(Component... components){
		for (Component component : components){
			component.addMouseListener(this);
			component.addMouseMotionListener(this);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		setMouseDownPoint(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		setMouseDownPoint(null);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		initializedPoints(e);
		move();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	private void initializedPoints(MouseEvent e) {
		setCurrPoint(e.getLocationOnScreen());
		setMovePoint(new Point(_currPoint.x - _mouseDownPoint.x, _currPoint.y - _mouseDownPoint.y));
	}

	private void move() {
		_movingFrame.setLocation(_movePoint);
	}

	private void setMouseDownPoint(Point mouseDownPoint) {
		_mouseDownPoint = mouseDownPoint;
	}

	private void setCurrPoint(Point _currPoint) {
		this._currPoint = _currPoint;
	}
	
	private void setMovePoint(Point _movePoint) {
		this._movePoint = _movePoint;
	}

	private void setMovingFrame(JFrame _movingFrame) {
		this._movingFrame = _movingFrame;
	}
}
