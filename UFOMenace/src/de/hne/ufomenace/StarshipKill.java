package de.hne.ufomenace;

import de.hne.gameframework.Actor;
import de.hne.gameframework.GameEngine;
import de.hne.gameframework.Stage;

/**
 * This class implements the an exploding starship.
 * @author Heiko Nolte
 * @since August 2008
 */
public class StarshipKill extends Actor {
	
	/**
	 * Create the UFO as actor.
	 * @param stage
	 */
	public StarshipKill(Stage stage)
	{
		super(stage);
		super.frameSpeed = 30;
		
		// Load UFO images
		String names[] = { "ship_kill1.gif", "ship_kill2.gif", "ship_kill3.gif", "ship_kill4.gif", "ship_kill5.gif" }; 
		LoadImages(names);
		
		// Play UFO kill sound
		((GameEngine) stage).getSoundCache().playSound("explosion.wav");
	}
	
	/**
	 * Starship
	 */
	public void act()
	{
		super.act();
		
        if(currentFrame == 4)
		{
			this.markedForRemoval = true;
			
			if(((UFOMenaceEngine) this.stage).getShipCnt() > 1)
			{
				// If ships are left deploy a new one
				((UFOMenaceEngine) this.stage).deployNewStarship();
			}
			else
			{
				// No ships left, game over
				((UFOMenaceEngine) this.stage).setShipCnt(0);
				((UFOMenaceEngine) this.stage).gameOver();	
			}
		}
	}	
}
