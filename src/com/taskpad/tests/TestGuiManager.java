package com.taskpad.tests;

import java.util.Timer;
import java.util.TimerTask;

import com.taskpad.ui.GuiManager;

public class TestGuiManager {
	public static void main(String[] args){
		GuiManager.initialGuiManager();
		
		GuiManager.callOutput("a");

		new Reminder(5);
		
	}
}


class Reminder {
    Timer timer;

    public Reminder(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), 0, seconds*1000);
        
	}

    static class RemindTask extends TimerTask {
    	private int t = 0;
        public void run() {
        	GuiManager.callOutput("b");
        	t++;
        	if(t == 3){
            	GuiManager.callExit();
            }
        }
    }
}

/**
 * test if the input shown in displayBox. (true)
 * test if everything is closed. (true)
 */
