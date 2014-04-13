//@author A0112084U

package com.taskpad.ui;



import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *  reference: http://tips4java.wordpress.com/2009/09/13/resizing-components/
 *  
 *  The ComponentResizer allows you to resize a component by dragging a border
 *  of the component.
 *  
 *  MouseAdapter is better because it is more OO-oriented.
 */
public class ComponentResizer extends MouseAdapter{
	private final static Dimension MINIMUM_SIZE = new Dimension(10, 10);
	private final static Dimension MAXIMUM_SIZE =
		new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);

	private static Map<Integer, Integer> _cursors = new HashMap<Integer, Integer>(); {
		_cursors.put(1, Cursor.N_RESIZE_CURSOR);
		_cursors.put(2, Cursor.W_RESIZE_CURSOR);
		_cursors.put(4, Cursor.S_RESIZE_CURSOR);
		_cursors.put(8, Cursor.E_RESIZE_CURSOR);
		_cursors.put(3, Cursor.NW_RESIZE_CURSOR);
		_cursors.put(9, Cursor.NE_RESIZE_CURSOR);
		_cursors.put(6, Cursor.SW_RESIZE_CURSOR);
		_cursors.put(12, Cursor.SE_RESIZE_CURSOR);
	}

	private Insets _dragInsets;
	private Dimension _snapSize;

	private int _direction;
	protected static final int NORTH = 1;
	protected static final int WEST = 2;
	protected static final int SOUTH = 4;
	protected static final int EAST = 8;

	private Cursor sourceCursor;
	private boolean _isResizing;
	private Rectangle _bounds;
	private Point _pressed;
	private boolean _autoscrolls;

	private Dimension minimumSize = MINIMUM_SIZE;
	private Dimension maximumSize = MAXIMUM_SIZE;

	/**
	 *  Convenience contructor. All borders are resizable in increments of
	 *  a single pixel. Components must be registered separately.
	 */
	protected ComponentResizer() {
		this(new Insets(5, 5, 5, 5), new Dimension(1, 1));
	}

	/**
	 *  Convenience contructor. All borders are resizable in increments of
	 *  a single pixel. Components can be registered when the class is created
	 *  or they can be registered separately afterwards.
	 *
	 *  @param components components to be automatically registered
	 */
	protected ComponentResizer(Component... components){
		this(new Insets(5, 5, 5, 5), new Dimension(1, 1), components);
	}

	/**
	 *  Create a ComponentResizer.
	 *
	 *  @param _dragInsets Insets specifying which borders are eligible to be
	 *                    resized.
	 *  @param _snapSize Specify the dimension to which the border will snap to
	 *                  when being dragged. Snapping occurs at the halfway mark.
	 *  @param components components to be automatically registered
	 */
	private ComponentResizer(Insets _dragInsets, Dimension _snapSize, Component... components){
		setDragInsets( _dragInsets );
		setSnapSize( _snapSize );
		registerComponent( components );
	}

	/**
	 *  Set the drag _dragInsets. The insets specify an area where mouseDragged
	 *  events are recognized from the edge of the border inwards. A value of
	 *  0 for any size will imply that the border is not resizable. Otherwise
	 *  the appropriate drag cursor will appear when the mouse is inside the
	 *  resizable border area.
	 *
	 *  @param  _dragInsets Insets to control which borders are resizeable.
	 */
	protected void setDragInsets(Insets _dragInsets){
		validateMinimumAndInsets(minimumSize, _dragInsets);

		this._dragInsets = _dragInsets;
	}
	
	/**
	 * Set the _dragInsets by using the insetSize
	 * create an inset that has same top, left, right, bottom
	 * @param insetSize: number that determines the inset that controls which borders are resizeable.
	 */
	protected void setDragInsets(int insetSize){
		Insets _dragInsets = new Insets(insetSize, insetSize, insetSize, insetSize);
		validateMinimumAndInsets(minimumSize, _dragInsets);

		this._dragInsets = _dragInsets;
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

	/**
	 *  Control how many pixels a border must be dragged before the size of
	 *  the component is changed. The border will snap to the size once
	 *  dragging has passed the halfway mark.
	 *
	 *  @param _snapSize Dimension object allows you to separately spcify a
	 *                  horizontal and vertical snap size.
	 */
	private void setSnapSize(Dimension _snapSize){
		this._snapSize = _snapSize;
	}

	/**
	 *  When the components minimum size is less than the drag insets then
	 *	we can't determine which border should be resized so we need to
	 *  prevent this from happening.
	 */
	private void validateMinimumAndInsets(Dimension minimum, Insets drag){
		int minimumWidth = drag.left + drag.right;
		int minimumHeight = drag.top + drag.bottom;

		if (minimum.width  < minimumWidth
		||  minimum.height < minimumHeight){
			String message = "Minimum size cannot be less than drag insets";
			throw new IllegalArgumentException( message );
		}
	}

	/**
	 */
	@Override
	public void mouseMoved(final MouseEvent e){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				Component source = e.getComponent();
				Point location = e.getPoint();
				_direction = 0;

				if (location.x < _dragInsets.left){
					_direction += WEST;
				}

				if (location.x > source.getWidth() - _dragInsets.right - 1){
					_direction += EAST;
				}

				if (location.y < _dragInsets.top){
					_direction += NORTH;
				}

				if (location.y > source.getHeight() - _dragInsets.bottom - 1){
					_direction += SOUTH;
				}

				//  Mouse is no longer over a resizable border

				if (_direction == 0){
					source.setCursor( sourceCursor );
				}
				else { // use the appropriate resizable cursor
					int cursorType = _cursors.get( _direction );
					Cursor cursor = Cursor.getPredefinedCursor( cursorType );
					source.setCursor( cursor );
				}
			}
			
		});
		
	}

	@Override
	public void mouseEntered(final MouseEvent e){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if (!_isResizing){
					Component source = e.getComponent();
					sourceCursor = source.getCursor();
				}
			}
			
		});
		
	}

	@Override
	public void mouseExited(final MouseEvent e){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if (!_isResizing){
					Component source = e.getComponent();
					source.setCursor( sourceCursor );
				}
			}
			
		});
		
	}

	@Override
	public void mousePressed(final MouseEvent e){
		
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
//				The mouseMoved event continually updates this variable

				if (_direction == 0){
					return;
				}

				Component source;
				
				//  Setup for _isResizing. All future dragging calculations are done based
				//  on the original _bounds of the component and mouse pressed location.
				source = setupForResizing(e);

				//  Making sure autoscrolls is false will allow for smoother resizing
				//  of components
				disableAutoScrolls(source);
			}
			
		});
		
	}

	private Component setupForResizing(MouseEvent e){
		Component source;
		_isResizing = true;

		source = e.getComponent();
		_pressed = e.getPoint();
		SwingUtilities.convertPointToScreen(_pressed, source);
		_bounds = source.getBounds();
		return source;
	}

	private void disableAutoScrolls(Component source) {
		if (source instanceof JComponent){
			JComponent jc = (JComponent)source;
			_autoscrolls = jc.getAutoscrolls();
			jc.setAutoscrolls( false );
		}
	}

	/**
	 *  Restore the original state of the Component
	 */
	@Override
	public void mouseReleased(final MouseEvent e){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				_isResizing = false;

				Component source = e.getComponent();
				source.setCursor( sourceCursor );

				restoreAutoScroll(source);
			}
			
		});
		
	}

	private void restoreAutoScroll(Component source) {
		if (source instanceof JComponent){
			((JComponent)source).setAutoscrolls(_autoscrolls);
		}
	}

	/**
	 *  Resize the component ensuring location and size is within the _bounds
	 *  of the parent container and that the size is within the minimum and
	 *  maximum constraints.
	 *
	 *  All calculations are done using the _bounds of the component when the
	 *  _isResizing started.
	 */
	@Override
	public void mouseDragged(final MouseEvent e){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if (!_isResizing){
					return;
				}

				Component source = e.getComponent();
				Point dragged = e.getPoint();
				SwingUtilities.convertPointToScreen(dragged, source);

				changeBounds(source, _direction, _bounds, _pressed, dragged);
			}
			
		});
		
	}

	protected void changeBounds(Component source, int _direction, Rectangle _bounds, Point _pressed, Point current){
		//  Start with original locaton and size

		int x = _bounds.x;
		int y = _bounds.y;
		int width = _bounds.width;
		int height = _bounds.height;

		//  Resizing the West or North border affects the size and location

		if (WEST == (_direction & WEST)){
			int drag = getDragDistance(_pressed.x, current.x, _snapSize.width);
			int maximum = Math.min(width + x, maximumSize.width);
			drag = getDragBounded(drag, _snapSize.width, width, minimumSize.width, maximum);

			x -= drag;
			width += drag;
		}

		if (NORTH == (_direction & NORTH)){
			int drag = getDragDistance(_pressed.y, current.y, _snapSize.height);
			int maximum = Math.min(height + y, maximumSize.height);
			drag = getDragBounded(drag, _snapSize.height, height, minimumSize.height, maximum);

			y -= drag;
			height += drag;
		}

		//  Resizing the East or South border only affects the size

		if (EAST == (_direction & EAST)){
			int drag = getDragDistance(current.x, _pressed.x, _snapSize.width);
			Dimension boundingSize = getBoundingSize( source );
			int maximum = Math.min(boundingSize.width - x, maximumSize.width);
			drag = getDragBounded(drag, _snapSize.width, width, minimumSize.width, maximum);
			width += drag;
		}

		if (SOUTH == (_direction & SOUTH)){
			int drag = getDragDistance(current.y, _pressed.y, _snapSize.height);
			Dimension boundingSize = getBoundingSize( source );
			int maximum = Math.min(boundingSize.height - y, maximumSize.height);
			drag = getDragBounded(drag, _snapSize.height, height, minimumSize.height, maximum);
			height += drag;
		}

		source.setBounds(x, y, width, height);
		source.validate();
	}

	/*
	 *  Determine how far the mouse has moved from where dragging started
	 */
	private int getDragDistance(int larger, int smaller, int _snapSize){
		int halfway = _snapSize / 2;
		int drag = larger - smaller;
		drag += (drag < 0) ? -halfway : halfway;
		drag = (drag / _snapSize) * _snapSize;

		return drag;
	}

	/*
	 *  Adjust the drag value to be within the minimum and maximum range.
	 */
	private int getDragBounded(int drag, int _snapSize, int dimension, int minimum, int maximum){
		while (dimension + drag < minimum){
			drag += _snapSize;
		}
		while (dimension + drag > maximum){
			drag -= _snapSize;
		}

		return drag;
	}

	/*
	 *  Keep the size of the component within the bounds of its parent.
	 */
	private Dimension getBoundingSize(Component source){
		if (source instanceof Window){
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Rectangle bounds = env.getMaximumWindowBounds();
			return new Dimension(bounds.width, bounds.height);
		}
		else {
			return source.getParent().getSize();
		}
	}
}