package de.hne.ufomenace;

import de.hne.gameframework.Actor;
import de.hne.gameframework.Stage;

/**
 * This class implements the shield protecting the player.
 * @author Heiko Nolte
 * @since August 2008
 */
public class Shield extends Actor {
	
	long created; // Time when shield was created
	
	/**
	 * Create the UFO as actor.
	 * @param stage
	 */
	public Shield(Stage stage)
	{
		super(stage);
		super.frameSpeed = 30;
		
		// Load UFO images
		String names[] = { "shield.gif" }; 
		LoadImages(names);
		
		// Creation time
		created = System.currentTimeMillis();
	}
	
	/**
	 * Attach shield to starship.
	 */
	public void act()
	{
		super.act();
		
		this.x = stage.getPlayer().getX() - 3;
		this.y = stage.getPlayer().getY() - 3;
		
		// Remove shield after 3 seconds
		long diff = System.currentTimeMillis() - created;
		if(diff >= 3000) this.markedForRemoval = true;
	}	
	
	/**
	 * Called when actor was actually removed from game world.
	 * Its no longer in the actor list.
	 */
	public void beingRemoved()
	{
		((UFOMenaceEngine) stage).setShieldActive(false);
	}
	
}
