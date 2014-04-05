package com.taskpad.ui;

//@author A0112084U

import javax.swing.JScrollBar;

public class BarScroller implements Runnable {
		
		private boolean _forward;
		private JScrollBar _anyScrollBar;
	
		protected BarScroller(){
		}
		
		protected BarScroller(boolean forward, JScrollBar anyScrollBar){
			initializeBarScroller(forward, anyScrollBar);
		}

		private void initializeBarScroller(boolean forward, JScrollBar anyScrollBar) {
			_forward = forward;
			_anyScrollBar = anyScrollBar;
		}
	
		public void run(){
			if (_anyScrollBar.isEnabled()){
				int current = scroll();
				_anyScrollBar.setValue(current);
			}
		}

		private int scroll() {
			int increment = _anyScrollBar.getBlockIncrement();
			int current = _anyScrollBar.getValue();
			if (_forward){//down, right
				current -= increment;
			} else if (!_forward){//up, left
				current += increment;
			}
			return current;
		}
}
