package de.hne.ufomenace;

import de.hne.gameframework.Actor;
import de.hne.gameframework.GameEngine;
import de.hne.gameframework.Stage;

/**
 * This class implements a droid to be destroyed.
 * @author Heiko Nolte
 * @since August 2008
 */
public class Droid extends Alien {

	protected int xmov = 5; // Move increment on x axis
	protected int ymov = 3; // Move increment on y axis
	
	/**
	 * Create the alien as actor.
	 * @param stage
	 */
	public Droid(Stage stage)
	{
		super(stage);
		super.frameSpeed = 20;
		
		// Load alien images
		String names[] = { "droid1.gif" }; 
		LoadImages(names);
	}
	
	/**
	 * Initializes the droid.
	 */
	public void initDroid()
	{	
		// Vertical speed
		this.ymov = 5;
		
		// Position
		int wideness = (((GameEngine) this.stage).getStageWidth() - this.width); 
		this.x = Utils.RandomNumber(wideness);
		this.y = Utils.RandomNumber(100);
		
		// Make noise
		((GameEngine) stage).getSoundCache().playSound("droid.wav");
	}
	
	/**
	 * alien movement
	 */
	public void act()
	{	
		// Change frame
		frameChangeCounter++;
		if (frameChangeCounter % frameSpeed == 0) {
			frameChangeCounter = 0;
			currentFrame = (currentFrame + 1) % spriteNames.length;
		}	
		
		// Horizontal movement
		x += xmov;
		if(((GameEngine) stage).getPlayer() != null)
		{
			if(x < ((GameEngine) stage).getPlayer().getX())
			{
				xmov = 5;
			}
			else if(x > ((GameEngine) stage).getPlayer().getX())
			{
				xmov = -5;
			}
		}
		
		// Vertical movement
		y += ymov;
		if(y >= ((GameEngine) stage).getStageHeight())
		{
			initAlien();
		}
	}
	
	/**
	 * Handle collision
	 */
	public void collision(Actor actor)
	{
		if(actor instanceof Rocket)
		{
			// Mark this alien for removal from screen
			this.markedForRemoval = true;
			
			// Let the alien explode
			DroidKill kill = new DroidKill(stage);
			kill.setX(this.x);
			kill.setY(this.y);
			
			// Add explosion
			stage.addActor(kill);
			
			// Increase score
			((UFOMenaceEngine) stage).increaseScore(75);
		}
	}
}
