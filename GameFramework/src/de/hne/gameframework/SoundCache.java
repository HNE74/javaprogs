package de.hne.gameframework;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/**
 * Cache for sounds.
 * 
 * @author Heiko Nolte / based on tutorial classes by Alexander Hristov
 * @since August 2008
 */
public class SoundCache extends ResourceCache {

	// Thread used to play the looped sound
	protected LoopSoundThread loopSoundThread = null;
	public boolean stopped = false;
	
	/**
	 * Creates the sound cache. Sounds can be found on the provided path.
	 * 
	 * @param path
	 */
	public SoundCache(String path) {
		super(path);
	}

	/**
	 * Loads a sound from the provided url.
	 */
	protected Object loadResource(URL url) {
		return Applet.newAudioClip(url);
	}

	/**
	 * Fetches the audio clip to be played.
	 * 
	 * @param name
	 * @return AudioClip
	 */
	public AudioClip fetchSound(String name) {
		return (AudioClip) getResource(name);
	}

	/**
	 * Plays the provided sound in a separate thread.
	 * 
	 * @param name
	 */
	public void playSound(final String name) {
		new Thread(new Runnable() {
			public void run() {
				fetchSound(name).play();
			}
		}).start();
	}
	
	/**
	 * Plays the provided sound in a loop.
	 * @param name
	 */
	public void loopSound(final String name) {
		loopSoundThread = new LoopSoundThread(name);
		stopped = false;
		loopSoundThread.start();
	}
	
	/**
	 * Stops the currently looped sound.
	 * @param name
	 */
	public void stopLoopSound(final String name) {
		if(!stopped && loopSoundThread != null) loopSoundThread.stopLoop();
	}
	
	/**
	 * Inner class to loop sound.
	 * @author Heiko Nolte
	 *
	 */
	protected class LoopSoundThread extends Thread
	{
		private String name = null;
		
		public LoopSoundThread(String name)
		{
			this.name = name;
		}
		
		public void run()
		{
			fetchSound(this.name).loop();
		}
		
		public void stopLoop()
		{
			fetchSound(this.name).stop();
		}
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

}
