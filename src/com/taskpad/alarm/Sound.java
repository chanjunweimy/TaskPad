package com.taskpad.alarm;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/**
 * 
 * @author Jun
 * This is a helper class
 * to play the sound
 *
 */

public class Sound {
	private AudioClip _song; // Sound player
	private URL _songPath; // Sound path
	
	protected Sound(String filename) throws Exception{
		assert (filename != null);
		setUpSong(filename);
	}

	private void setUpSong(String filename) throws Exception{
		_songPath = ClassLoader.getSystemResource(filename); // Get the Sound URL
		_song = Applet.newAudioClip(_songPath); // Load the Sound
	}
	
	protected void playSound(){
		_song.loop(); // Play
	}
	
	protected void stopSound(){
		_song.stop(); // Stop		
	}
	
	protected void playSoundOnce(){
		_song.play(); // Play only once
	}
}
