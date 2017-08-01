package de.hne.gameframework;

import java.awt.event.KeyEvent;

/**
 * The player is a user controlled actor.
 * 
 * @author Heiko Nolte / based on tutorial classes by Alexander Hristov
 * @since August 2008
 */
public class Player extends Actor {

	// Movement rate
	protected int rateX = 1;
	protected int rateY = 1;

	// Position change while acting
	protected int vx = 0;
	protected int vy = 0;

	// Indicate movement direction
	private boolean up, down, left, right;

	/**
	 * Create player
	 * @param stage
	 */
	public Player(Stage stage) {
		super(stage);
	}

	/**
	 * Perform movement.
	 */
	public void act() {
		super.act();
		
		x += vx;
		y += vy;
		
		if (x < 0)
			x = 0;
		if (x >= ((GameEngine) stage).getStageWidth() - this.width)
			x = ((GameEngine) stage).getStageWidth() - getWidth()-vx;
		if (y < 0)
			y = 0;
		if (y >= ((GameEngine) stage).getStageHeight() - this.height)
			y = ((GameEngine) stage).getStageHeight() - this.height-vy;

	}

	/**
	 * Updates movement speed
	 */
	protected void updateSpeed() {
		vx = 0;
		vy = 0;
		if (down)
			vy = rateY;
		if (up)
			vy = -rateY;
		if (left)
			vx = -rateX;
		if (right)
			vx = rateX;
	}

	/**
	 * Stops movement on key release event
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		}
		updateSpeed();
	}

	/**
	 * Initiates movement on key press event
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		}
		updateSpeed();
	}
	
	/**
	 * Accessors
	 */

	public int getVx() {
		return vx;
	}

	public void setVx(int i) {
		vx = i;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int i) {
		vy = i;
	}	
	
	public int getRateX() {
		return rateX;
	}

	public void setRateX(int rateX) {
		this.rateX = rateX;
	}

	public int getRateY() {
		return rateY;
	}

	public void setRateY(int rateY) {
		this.rateY = rateY;
	}
}
