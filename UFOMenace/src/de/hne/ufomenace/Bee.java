package de.hne.ufomenace;

import de.hne.gameframework.Actor;
import de.hne.gameframework.GameEngine;
import de.hne.gameframework.Stage;

/**
 * This class implements an alien bee to be destroyed
 * @author Heiko Nolte
 * @since August 2008
 */
public class Bee extends Actor {

	protected int xmov = 5; // Move increment on x axis
	protected int ymov = 3; // Move increment on y axis
	
	protected int changeCourseProb = 0;
	protected int ymax = 0;
	
	/**
	 * Create the alien as actor.
	 * @param stage
	 */
	public Bee(Stage stage)
	{
		super(stage);
		super.frameSpeed = 10;
		
		// Load alien images
		String names[] = { "bee1.gif", "bee2.gif" }; 
		LoadImages(names);
		
		changeCourseProb = Utils.RandomNumber(50) + 50;
		this.ymax = Utils.RandomNumber(200) + 200;
	}
	
	/**
	 * Initializes the bee.
	 */
	public void initBee()
	{
		// Horizontal speed
		this.xmov = Utils.RandomNumber(6);
		int change = Utils.RandomNumber(1);
		if(change == 0) this.xmov = -this.xmov;
		
		// Vertical speed
		if(((UFOMenaceEngine) stage).getDiff() < 6) 
			this.ymov = Utils.RandomNumber(((UFOMenaceEngine) stage).getDiff()) + 1;
		else
			this.ymov = Utils.RandomNumber(5) + 1;
		
		// Position
		int wideness = (((GameEngine) this.stage).getStageWidth() - this.width); 
		this.x = Utils.RandomNumber(wideness);
		this.y = Utils.RandomNumber(100);
		
		// Make noise
		((GameEngine) stage).getSoundCache().playSound("alien.wav");
	}
	
	/**
	 * alien movement
	 */
	public void act()
	{
		super.act();
		
		// Horizontal movement
		x += xmov;
		if(x >= ((GameEngine) stage).getStageWidth() - this.width ||
		   x <= 0)
		{
			xmov=-xmov;			
		}
		else
		{
			if(Utils.RandomNumber(changeCourseProb) == 0)
			{
				xmov=-xmov;
			}
		}
		
		// Vertical movement
		y += ymov;
		if(y >= 550)
		{
			ymov = -ymov;
			xmov = Utils.RandomNumber(6);
			int change = Utils.RandomNumber(1);
			if(change == 0) this.xmov = -this.xmov;			
		}

		if(y <= ymax && ymov < 0)
		{
			ymov = -ymov;
			ymax = Utils.RandomNumber(200) + 200;
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
			AlienKill kill = new AlienKill(stage);
			kill.setX(this.x);
			kill.setY(this.y);
			
			// Add explosion
			stage.addActor(kill);
			
			// Increase score
			((UFOMenaceEngine) stage).increaseScore(50);
		}
	}
	
	/**
	 * Called when alien is really removed. If no aliens
	 * are left UFO will start to attack player.
	 */
	public void beingRemoved()
	{
		// Check if UFO should change to attack mode
		UFO ufo = ((UFOMenaceEngine) stage).checkAliensDestroyedUFOLeft();
		if(ufo != null)
		{
			ufo.setAttackMode(true);
		}
		
        // Add bonus item 
        if(Utils.RandomNumber(8) == 0)
        {
        	Bonus bonus = new Bonus(stage);
        	bonus.setX(this.x);
        	bonus.setY(this.y);
        	stage.addActor(bonus);
        }		
	}

	public int getXmov() {
		return xmov;
	}

	public void setXmov(int xmov) {
		this.xmov = xmov;
	}

	public int getYmov() {
		return ymov;
	}

	public void setYmov(int ymov) {
		this.ymov = ymov;
	}
	
}
