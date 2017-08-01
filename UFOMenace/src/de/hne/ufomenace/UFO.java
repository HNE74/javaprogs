package de.hne.ufomenace;

import de.hne.gameframework.Actor;
import de.hne.gameframework.GameEngine;
import de.hne.gameframework.Stage;

/**
 * This class implements an alien to be destroyed
 * @author Heiko Nolte
 * @since August 2008
 */
public class UFO extends Actor {

	private int xmov = 3; // Move increment on x axis
	private int ymov = 1; // Move increment on y axis
	
	private int dropProb = 55; // Bomb drop probability;
	
	private boolean attackMode = false;
	
	/**
	 * Create the UFO as actor.
	 * @param stage
	 */
	public UFO(Stage stage)
	{
		super(stage);
		super.frameSpeed = 10;
		
		// Load UFO images
		String names[] = { "ufo1.gif", "ufo2.gif" }; 
		LoadImages(names);
	}
	
	/**
	 * Initializes the UFO and set x move direction.
	 * @param xmov
	 */
	public void initUFO()
	{	
		// Position
		int wideness = (((GameEngine) this.stage).getStageWidth() - this.width); 
		this.x = Utils.RandomNumber(wideness);
		this.y = Utils.RandomNumber(50) + 20;
	}
	
	/**
	 * UFO movement
	 */
	public void act()
	{
		super.act();
		
		if(!attackMode) // UFO rests on top of screen
		{	
			// Horizontal movement
			if(x >= ((GameEngine) stage).getStageWidth() - this.width ||
			   x <= 0)
			{
				xmov=-xmov;			
			}
			x += xmov;
			
			// Vertical movement
			if(y <= 20 || y >= 100)
			{
				ymov = -ymov;
			}
			y += ymov;
			
			// UFO drops bomb
			if(Utils.RandomNumber(dropProb) == 1)
			{
				Bomb bomb = new Bomb(stage);
				bomb.initBomb(this.x+28, this.y+10);
				stage.addActor(bomb);
				((GameEngine) stage).getSoundCache().playSound("ufobomb.wav");
			}
		}
		else // UFO attacks user
		{
			// Horizontal movement
			x += xmov*2;
			if(x >= ((GameEngine) stage).getStageWidth() - this.width ||
			   x <= 0)
			{
				xmov=-xmov;			
			}
			
			// Vertical movement
			y += ymov;
			if(y >= ((GameEngine) stage).getStageHeight())
			{
				initUFO();
			}
			else
			{
				// UFO drops bomb
				if(Utils.RandomNumber(dropProb) == 1)
				{
					Bomb bomb = new Bomb(stage);
					bomb.initBomb(this.x+28, this.y+10);
					stage.addActor(bomb);
					((GameEngine) stage).getSoundCache().playSound("ufobomb.wav");
				}
			}
		}
	}
	
	/**
	 * Handle collision
	 */
	public void collision(Actor actor)
	{
		if(actor instanceof Rocket)
		{
			// Mark this UFO for removal from screen
			this.markedForRemoval = true;
			
			// Let the UFO explode
			UFOKill kill = new UFOKill(stage);
			kill.setX(this.x);
			kill.setY(this.y);
			
			// Add explosion
			stage.addActor(kill);
			
			// Increase score
			((UFOMenaceEngine) stage).increaseScore(100);
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

	public int getDropProb() {
		return dropProb;
	}

	public void setDropProb(int dropProb) {
		this.dropProb = dropProb;
	}

	public boolean isAttackMode() {
		return attackMode;
	}

	public void setAttackMode(boolean attackMode) {
		this.attackMode = attackMode;
		ymov = 3;
	}
	
}
