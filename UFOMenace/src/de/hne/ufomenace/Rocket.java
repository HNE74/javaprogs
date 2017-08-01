package de.hne.ufomenace;

import de.hne.gameframework.Actor;
import de.hne.gameframework.Stage;

/**
 * This class implements the rocket being fired on UFOs.
 * @author Heiko Nolte
 * @since August 2008
 */
public class Rocket extends Actor {

	/**
	 * Create the Rocket as actor.
	 * @param stage
	 */
	public Rocket(Stage stage)
	{
		super(stage);
		super.frameSpeed = 10;
		
		// Load UFO images
		String names[] = { "rocket.gif" }; 
		LoadImages(names);
	}
	
	/**
	 * Rocket movement
	 */
	public void act()
	{
		super.act();
		
		// Move rocket or delete it if to of 
		// screen is reached
		if(this.y > 0)
		{
			this.y -= 8;
		}
		else
		{
			this.markedForRemoval = true;
		}
	}
	
	/**
	 * Rocket is removed and counter decreased
	 */
	public void beingRemoved()
	{
		int rockets = ((UFOMenaceEngine) this.stage).getRocketsFired();
		rockets--;
		((UFOMenaceEngine) this.stage).setRocketsFired(rockets);
	}
	
	/**
	 * Handle collision
	 */
	public void collision(Actor actor)
	{
		if(actor instanceof Alien 
		|| actor instanceof UFO
		|| actor instanceof Bee)
		{	
			this.markedForRemoval = true;
		}
	}
}
