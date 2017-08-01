package de.hne.ufomenace;

import de.hne.gameframework.Actor;
import de.hne.gameframework.GameEngine;
import de.hne.gameframework.Stage;

/**
 * This class implements an exploding UFO.
 * @author Heiko Nolte
 * @since August 2008
 */
public class UFOKill extends Actor {
	
	/**
	 * Create the UFO as actor.
	 * @param stage
	 */
	public UFOKill(Stage stage)
	{
		super(stage);
		super.frameSpeed = 25;
		
		// Load UFO images
		String names[] = { "ufo_kill1.gif", "ufo_kill2.gif", "ufo_kill3.gif", "ufo_kill4.gif", "ufo_kill5.gif" }; 
		LoadImages(names);
		
		// Play UFO kill sound
		((GameEngine) stage).getSoundCache().playSound("explosion.wav");
	}
	
	/**
	 * UFO movement
	 */
	public void act()
	{
		super.act();
		
        if(currentFrame == 4)
		{
			this.markedForRemoval = true;
		}
        
	}	
}
