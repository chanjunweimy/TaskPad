package com.taskpad.alarm;

import javax.swing.JApplet;


public class AlarmManager extends JApplet{

	
	/**
	 * random generated
	 */
	private static final long serialVersionUID = 4348001564533802036L;
	private static final Exception NullPointerException = null;
	private final String SONG_DEFAULT = "katy_perry-the_one_that_got_away.mid";
	private static Sound _alarm = null;
	private final int ALARM_DURATION = 60;
	
	public AlarmManager() throws Exception{
		initializeSong();
	}
	
	public AlarmManager(String otherSong) throws Exception{
		initializeSong(otherSong);
	}
	
	private void initializeSong() throws Exception{
		_alarm = setUpSong();
	}
	
	private void initializeSong(String otherSong) throws Exception{
		_alarm = setUpSong(otherSong);
	}

	protected void playSong(){
		assert(_alarm != null);
		_alarm.playSound();
	}
	
	private Sound setUpSong(String otherSong) throws Exception {
		Sound testSong = new Sound(otherSong);
		return testSong;
	}
	
	private Sound setUpSong() throws Exception {
		Sound testSong = new Sound(SONG_DEFAULT);
		return testSong;
	}
	
	public static void turnOffAlarm() throws Exception{
		if (_alarm == null){
			throw NullPointerException;
		}
		
		_alarm.stopSound();
	}
	
	public void runAlarm(){
		playSong();
		new TimerObject(ALARM_DURATION);
	}
	
	/* test alarm
	public static void main(String[] args){
		AlarmManager alarm = null;
		try {
			alarm = new AlarmManager();
			alarm.runAlarm();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			System.err.println("cannot start song");
			
		}
	}
	*/
	
}
