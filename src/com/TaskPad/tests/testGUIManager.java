package com.TaskPad.tests;

import java.util.Timer;
import java.util.TimerTask;

import com.TaskPad.inputproc.Input;
import com.TaskPad.ui.GUIManager;
import com.TaskPad.ui.InputFrame;
import com.TaskPad.ui.OutputFrame;

public class testGUIManager {
	Timer timer;
	public static void main(String[] args){
		new Input();
		new InputFrame();
		new OutputFrame();
		
		GUIManager.callOutput("a");

		new Reminder(5);
		
	}
}


class Reminder {
    Timer timer;

    public Reminder(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000, seconds*1000);
        
	}

    static class RemindTask extends TimerTask {
    	private int t = 0;
        public void run() {
        	GUIManager.callOutput("b");
        	t++;
        	if(t == 3){
            	GUIManager.callExit();
            }
        }
    }
}

/**
 * test if the input shown in displayBox. (true)
 * test if everything is closed. (true)
 */
