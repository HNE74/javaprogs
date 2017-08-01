/**
 * An actor on the stage. This class knows the actors position,
 * an uses the sprite cache to load and manage its visual
 * presentation.
 * @author Heiko Nolte / based on tutorial classes by Alexander Hristov
 * @since August 2008
 */
package de.hne.gameframework;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Actor {
	protected Stage stage; // Reference to stage
	protected int x, y; // Global coordinates on stage
	protected int width, height; // Size of actor

	protected String[] spriteNames; // Image names to be loaded
	protected int currentFrame; // Current frame
	protected int frameSpeed; // When frame changes
	protected int frameChangeCounter; // Counter to determine frame change
	protected ImageCache imageCache; // To load images

	protected boolean markedForRemoval; // Flag to remove from stage

	/**
	 * Creates an actor on the stage.
	 * @param stage
	 */
	public Actor(Stage stage) {
		this.stage = stage;
		this.imageCache = stage.getImageCache();
		this.currentFrame = 0;
		this.frameSpeed = 1;
		this.frameChangeCounter = 0;
	}

	/**
	 * Marks the actor for removal from the stage.
	 */
	public void remove() {
		markedForRemoval = true;
	}
	
	/**
	 * Called when actor was actually removed from game world.
	 * Its no longer in the actor list.
	 */
	public void beingRemoved()
	{
		// pass
	}
	
	/**
	 * Returns true if the actor is marked for removal.
	 * @return boolean
	 */
	public boolean isMarkedForRemoval() {
		return markedForRemoval;
	}

	/**
	 * Prints the actor on the provided graphics context.
	 * @param g
	 */
	public void paint(Graphics2D g) {
		g.drawImage(imageCache.fetchImage(spriteNames[currentFrame]), x, y,
				stage);
	}

	/**
	 * Loads the images used for the visual representation
	 * of the actor. Name and image path are passed in the
	 * names array. 
	 * @param names
	 */
	public void LoadImages(String[] names) {
		
		spriteNames = names;
		height = 0;
		width = 0;
		
		// Load images using sprite cache class
		for (int i = 0; i < names.length; i++) {
			BufferedImage image = imageCache.fetchImage(spriteNames[i]);
			height = Math.max(height, image.getHeight());
			width = Math.max(width, image.getWidth());
		}
	}
	
	/**
	 * Lets the actor act. I.e change its image according to the
	 * frame counter.
	 */
	public void act() {
		frameChangeCounter++;
		if (frameChangeCounter % frameSpeed == 0) {
			frameChangeCounter = 0;
			currentFrame = (currentFrame + 1) % spriteNames.length;
		}
	}
	
	/**
	 * By default an actor might do nothing on a collision.
	 * To be overwritten in subclass
	 * @param a
	 */
	public void collision(Actor a) {
		// pass
	}
	
	/**
	 * Accessors. 
	 */
	
	public int getX() {
		return x;
	}

	public void setX(int i) {
		x = i;
	}

	public int getY() {
		return y;
	}

	public void setY(int i) {
		y = i;
	}

	public int getFrameSpeed() {
		return frameSpeed;
	}

	public void setFrameSpeed(int i) {
		frameSpeed = i;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int i) {
		height = i;
	}

	public void setWidth(int i) {
		width = i;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
