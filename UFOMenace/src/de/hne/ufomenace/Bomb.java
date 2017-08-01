package de.hne.ufomenace;

import de.hne.gameframework.Actor;
import de.hne.gameframework.GameEngine;
import de.hne.gameframework.Stage;

/**
 * Bombs are dropped by the bad UFO.
 * @author Heiko Nolte
 * @since August 2008
 */
public class Bomb extends Actor {

	/**
	 * Create the Rocket as actor.
	 * @param stage
	 */
	public Bomb(Stage stage)
	{
		super(stage);
		super.frameSpeed = 10;
		
		// Load UFO images
		String names[] = { "bomb1.gif", "bomb2.gif" }; 
		LoadImages(names);
	}
	
	/**
	 * Initializes the bomb
	 */
	public void initBomb(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Rocket movement
	 */
	public void act()
	{
		super.act();
		
		// Move and delete it if end of screen is reached
		y += 5;
		if(this.y >= ((GameEngine) stage).getStageHeight())
		{
			this.markedForRemoval = true;
		}
	}
	
	/**
	 * Handle collision
	 */
	public void collision(Actor actor)
	{
		if(actor instanceof Starship)
		{	
			this.markedForRemoval = true;
		}
	}
}
