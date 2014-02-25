package com.taskpad.ui;

import javax.swing.JScrollBar;

public class BarScroller implements Runnable {
		
		private boolean _forward;
		private JScrollBar _anyScrollBar;
	
		public BarScroller(){
		}
		
		public BarScroller(boolean forward, JScrollBar anyScrollBar){
			initialBarScroller(forward, anyScrollBar);
		}

		private void initialBarScroller(boolean forward, JScrollBar anyScrollBar) {
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
