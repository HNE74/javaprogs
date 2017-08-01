package de.hne.chaseevade;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import de.hne.gameframework.Actor;
import de.hne.gameframework.Stage;

/**
 * The predator chases the prey.
 * @author Heiko Nolte	
 * @since December 2008
 */
public class Obstacle extends Actor {

	MVector pos = new MVector();
	MVector vel = new MVector();
	double radius = 10;

	/**
	 * Creates the predator.
	 * @param stage
	 */
	public Obstacle(Stage stage) {
		super(stage);
	}
	
	/**
	 * Overwrites the actor's act method since it uses an image
	 * to visualize the actor. Here the predator is a Java 2D construction
	 */
	public void act() {	
		// do nothing
	}
	
	/**
	 * Draws the predator using the Java 2D API.
	 */
	public void paint(Graphics2D g) {
		Ellipse2D.Double obst = 
			new Ellipse2D.Double(pos.x - radius, pos.y - radius,
					             2 * radius, 2 * radius);
		g.setColor(Color.YELLOW);
		g.draw(obst);
	}

	public MVector getPos() {
		return pos;
	}

	public void setPos(MVector pos) {
		this.pos = pos;
	}

	public MVector getVel() {
		return vel;
	}

	public void setVel(MVector vel) {
		this.vel = vel;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
}
