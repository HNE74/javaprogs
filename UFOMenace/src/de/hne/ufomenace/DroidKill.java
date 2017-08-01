package de.hne.ufomenace;

import de.hne.gameframework.Actor;
import de.hne.gameframework.GameEngine;
import de.hne.gameframework.Stage;

/**
 * This class implements a droid being destroyed
 * @author Heiko Nolte
 * @since August 2008
 */
public class DroidKill extends Actor {
	
	/**
	 * Create the UFO as actor.
	 * @param stage
	 */
	public DroidKill(Stage stage)
	{
		super(stage);
		super.frameSpeed = 20;
		
		// Load UFO images
		String names[] = { "droid_kill1.gif", "droid_kill2.gif", "droid_kill3.gif", "droid_kill4.gif" }; 
		LoadImages(names);
		
		// Play UFO kill sound
		((GameEngine) stage).getSoundCache().playSound("alienkill.wav");
	}
	
	/**
	 * UFO movement
	 */
	public void act()
	{
		super.act();
		
        if(currentFrame == 3)
		{
			this.markedForRemoval = true;
	        ((UFOMenaceEngine) stage).setDroidActive(false);
		}
	}	
}
