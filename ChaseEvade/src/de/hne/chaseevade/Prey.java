package de.hne.chaseevade;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

import de.hne.gameframework.GameEngine;
import de.hne.gameframework.Player;
import de.hne.gameframework.Stage;

/**
 * The prey is chased by the predator. Here it acts
 * @author Heiko Nolte	
 * @since December 2008
 */
public class Prey extends Player {
	double speed = 0.0;
	double angle = 0.0;

	MVector vel = new MVector();
	MVector pos = new MVector();
	 
	int fcnt = 0;
	
	/**
	 * Creates the prey.
	 * @param stage
	 */
	public Prey(Stage stage) {
		super(stage);
		pos.x = 600;
		pos.y = 120;
	}
	
	/**
	 * Handle key pressed event
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			// Increase speed
			speed = speed + 0.5;
			if(speed > 3) speed = 3;
			adjustMovement();
			break;
		case KeyEvent.VK_RIGHT:
			// Turn left
			angle = angle + 2.0;
			if(angle >= 360) angle = angle - 360;
			adjustMovement();
			break;
		case KeyEvent.VK_LEFT:
			// Turn right
			angle = angle - 2.0;
			if(angle < 0) angle = angle + 360;
			adjustMovement();
			break;
		case KeyEvent.VK_DOWN:
			// Decrease speed
			speed = speed - 0.5;
			if(speed < 0.0) speed = 0;
			adjustMovement();
			break;
		}
	}
	
	/**
	 * Recalculate prey movement
	 */
	protected void adjustMovement()
	{
		vel.x = MVector.FetchComponent(speed, angle+90);
	    vel.y = MVector.FetchComponent(speed, angle);
	}
	
	/**
	 * Overwrites the actor's act method since it uses an image
	 * to visualize the actor. Here the prey is a Java 2D construction
	 */
	public void act() {	
		
		pos.x += vel.x;
		pos.y += vel.y;
		x = (int) pos.x;
		y = (int) pos.y;
		
		fcnt++;
		
		// Prey exceeds top or bottom of screen
		if (x < 0)
		{
			x = ((GameEngine) stage).getStageWidth();
			pos.x = x;
		}
		else if (x >= ((GameEngine) stage).getStageWidth())
		{
			x = 0;
			pos.x = x;
		}
		
		// Prey exceeds left or right of screen
		if (y < 100)
		{
			y = ((GameEngine) stage).getStageHeight();
			pos.y = y;
		}
		else if (y >= ((GameEngine) stage).getStageHeight())
		{
			y = 100;
			pos.y = y;
		}

	}
	
	/**
	 * Draws the prey using the Java 2D API.
	 */
	public void paint(Graphics2D g) {
		// Draw prey shape
		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0);
		gp.lineTo(10, 40);
		gp.lineTo(20, 0);
		gp.lineTo(0, 0);
		
		// Translate prey shape to its position
		// and rotate it
		AffineTransform id = new AffineTransform();
		id.rotate(Math.toRadians(angle), x+10, y+20);
		id.translate(x, y);
		Shape tShip = id.createTransformedShape(gp);
		g.setColor(Color.WHITE);
		g.draw(tShip);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getFcnt() {
		return fcnt;
	}

	public void setFcnt(int fcnt) {
		this.fcnt = fcnt;
	}

	public MVector getVel() {
		return vel;
	}

	public void setVel(MVector vel) {
		this.vel = vel;
	}

	public MVector getPos() {
		return pos;
	}

	public void setPos(MVector pos) {
		this.pos = pos;
	}

}
