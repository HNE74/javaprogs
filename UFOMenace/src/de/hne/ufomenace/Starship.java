package de.hne.ufomenace;

import java.awt.event.KeyEvent;

import de.hne.gameframework.Actor;
import de.hne.gameframework.GameEngine;
import de.hne.gameframework.Player;
import de.hne.gameframework.Stage;

/**
 * This class implements the starship which is the player.
 * @author Heiko Nolte
 * @since August 2008
 */
public class Starship extends Player {
	
	// Starship state constants
	public final static int SHIP_STATE_DEFENDING = 1;
	public final static int SHIP_STATE_PROCEEDING = 2;	

	// Ship state
	private int shipState = SHIP_STATE_DEFENDING;
	
	/**
	 * Create the UFO as actor.
	 * @param stage
	 */
	public Starship(Stage stage)
	{
		super(stage);
		super.frameSpeed = 20;
		
		// Load UFO images
		String names[] = { "ship1.gif", "ship2.gif" }; 
		LoadImages(names);
		
		// Allow only horizontal movement
		this.rateY = 5;
		this.rateX = 5;
	}
	
	/**
	 * Restrict vertical movement
	 */
	public void act()
	{
		if(shipState == SHIP_STATE_DEFENDING)
		{
			if(this.y < 450) this.y = 450;
			else if(this.y > 500) this.y = 500;
		}
		else if(shipState == SHIP_STATE_PROCEEDING)
		{
			if(this.y < 0)
			{
				shipState = SHIP_STATE_DEFENDING;
			}
			this.vy=-3;
		}
		super.act();
	}
	
	/**
	 * Handle collision
	 */
	public void collision(Actor actor)
	{
		// Shield active -> No collisions
		if(((UFOMenaceEngine) stage).isShieldActive())
		  return;
		
		// Check collision with enemy
		if(actor instanceof Alien 
		   || actor instanceof Bomb
		   || actor instanceof UFO
		   || actor instanceof Bee)
		{
			// Remove Starship
			this.markedForRemoval = true;
			
			// Let the starship explode
			StarshipKill kill = new StarshipKill(stage);
			kill.setX(this.x);
			kill.setY(this.y);
			
			// Add explosion
			stage.addActor(kill);
		}
	}
	
	/**
	 * Initiates movement on key press event
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		
		// Check if space is pressed in order to fire rocket
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_S)
		{
			if(((UFOMenaceEngine) this.stage).getRocketsFired() <= 2)
			{
				// set flag
				int rockets = ((UFOMenaceEngine) this.stage).getRocketsFired();
				rockets++;
				((UFOMenaceEngine) this.stage).setRocketsFired(rockets);
				
				// Initialize rocket
				Rocket rocket = new Rocket(stage);
				rocket.setX(this.x + 12);
				rocket.setY(this.y - 10);
				
				// Add rocket to stage
				stage.addActor(rocket);
				
				// Make rocket noise
				((GameEngine) stage).getSoundCache().playSound("rocket.wav");
			}
		}
		else if(code == KeyEvent.VK_A)
		{
			if(!((UFOMenaceEngine) this.stage).isShieldActive())
			{
				int shields = ((UFOMenaceEngine) this.stage).getShieldCnt();
				if(shields > 0)
				{
					((UFOMenaceEngine) this.stage).setShieldCnt(shields-1);
					((UFOMenaceEngine) this.stage).setShieldActive(true);
					stage.addActor(new Shield(stage));
					((GameEngine) stage).getSoundCache().playSound("shield.wav");
				}
			}
		}
	}

	public int getShipState() {
		return shipState;
	}

	public void setShipState(int shipState) {
		this.shipState = shipState;
	}
	
}
