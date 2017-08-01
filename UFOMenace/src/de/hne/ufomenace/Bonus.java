package de.hne.ufomenace;

import de.hne.gameframework.Actor;
import de.hne.gameframework.GameEngine;
import de.hne.gameframework.Stage;

/**
 * This class implements an alien bee to be destroyed
 * @author Heiko Nolte
 * @since August 2008
 */
public class Bonus extends Actor {

	protected int ymov = 4; // Move increment on y axis
	
    protected boolean collidedWithStarship = false;

	/**
	 * Create the alien as actor.
	 * @param stage
	 */
	public Bonus(Stage stage)
	{
		super(stage);
		super.frameSpeed = 10;
		
		// Load alien images
		String names[] = { "bonus1.gif", "bonus2.gif" }; 
		LoadImages(names);
	}
	
	/**
	 * Bonus movement
	 */
	public void act()
	{
		super.act();
		y += ymov;
		
		if(y >= ((GameEngine) stage).getStageHeight())
		{
			this.markedForRemoval = true;
		}
	}
	
	public int getYmov() {
		return ymov;
	}

	public void setYmov(int ymov) {
		this.ymov = ymov;
	}
	
	/**
	 * Handle collision
	 */
	public void collision(Actor actor)
	{
		if(actor instanceof Starship)
		{
			collidedWithStarship = true;
			markedForRemoval = true;
		}
	}
	
	/**
	 * Called in order to increase score
	 */
	public void beingRemoved()
	{
		if(collidedWithStarship)
		{
			((UFOMenaceEngine) stage).increaseScore(250);
			((GameEngine) stage).getSoundCache().playSound("bonus.wav");
		}
	}
}
