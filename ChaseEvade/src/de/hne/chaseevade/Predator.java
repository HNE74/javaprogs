package de.hne.chaseevade;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.Iterator;
import java.util.List;

import de.hne.gameframework.Actor;
import de.hne.gameframework.GameEngine;
import de.hne.gameframework.Stage;

/**
 * The predator chases the prey.
 * @author Heiko Nolte	
 * @since December 2008
 */
public class Predator extends Actor {
	double speed = 1.0;
	double angle = 180.0;

	MVector pos = new MVector();
	MVector vel = new MVector();
    MVector icpt = new MVector();	

	int fcnt = 0;
	double ind = 0;
	
	/**
	 * Creates the predator.
	 * @param stage
	 */
	public Predator(Stage stage) {
		super(stage);
		pos.x = 50;
		pos.y = 500;
	}
	
	/**
	 * Recalculate predator movement using Lenard Jones potential
	 * function.
	 */
	protected void adjustMovementPotential()
	{
		// Calculate and normalize distance between prey and predator
		Prey prey = (Prey) ((ChaseEvadeEngine) stage).getPlayer();
		MVector diff = prey.pos.substractVector(pos);
		double dist = Math.sqrt(diff.x * diff.x + diff.y * diff.y);
		MVector nDist = new MVector();
		nDist.x = (diff.x / dist);
		nDist.y = (diff.y / dist);
		
		// Calculate force using Lendard Jones potential function
		// and apply force to predator movement vector
		double force = Utils.CalcLJPotentialForce(dist, 12000, 2000, 2, 3);
		vel.x = - force * nDist.x;
		if(vel.x > 3) vel.x = 3;
		if(vel.x < -3) vel.x = -3;
		vel.y = - force * nDist.y;
		if(vel.y > 3) vel.y = 3;
		if(vel.y < -3) vel.y = -3;
		speed = Math.abs(vel.x) + Math.abs(vel.y);
		
		// Consider obstacles
		addRepulsiveForce();
		
		// Set predator angle according to velocity direction
		if(prey.pos.y < pos.y)
		{
			angle = Math.toDegrees(Math.asin(vel.normalize().x)) + 180;
		}
		else
		{
			angle = Math.toDegrees(Math.asin(-vel.normalize().x));			
		}
	}
	
	/**
	 * Adds repulsive force to velocity vector in order to
	 * avoid obstacles
	 */
	protected void addRepulsiveForce()
	{
		List obsts = ((ChaseEvadeEngine) stage).obstacles;
		for(Iterator it = obsts.iterator(); it.hasNext(); )
		{
			// Calculate and normalize distance between prey and obstacle
			Obstacle obst = (Obstacle) it.next();
			MVector diff = obst.pos.substractVector(pos);
			double dist = Math.sqrt(diff.x * diff.x + diff.y * diff.y);
			MVector nDist = new MVector();
			nDist.x = (diff.x / dist);
			nDist.y = (diff.y / dist);
			double force = Utils.CalcLJPotentialForce(dist, 0, 5000, 1, 2.5);
			
			// Add repulsive obstacle force to predator velocity vector
			vel.x += - force * nDist.x;
			if(vel.x > 3) vel.x = 3;
			if(vel.x < -3) vel.x = -3;
			vel.y += - force * nDist.y;
			if(vel.y > 3) vel.y = 3;
			if(vel.y < -3) vel.y = -3;
		}
	}
	
	/*
	 * Recalculate predator movement in order to intercept
	 * the prey by assessing its direction.
	 */
	protected void adjustMovementIntercept()
	{
		// Calculate intercept point
		Prey prey = (Prey) ((ChaseEvadeEngine) stage).getPlayer();
		double velMag = prey.vel.fetchMagnitude(vel);		
		double distMag = prey.pos.fetchMagnitude(pos); 
		double tc = distMag / velMag;
		icpt.x = prey.pos.x + prey.vel.x * tc;		
		icpt.y = prey.pos.y + prey.vel.y * tc;

		// Check on which side of sighting line prey is
		MVector diff = icpt.substractVector(pos);
		MVector local = new MVector();
		local.x = MVector.FetchLocalXComponent(diff, angle);
		local.y = MVector.FetchLocalYComponent(diff, angle);
		local = local.normalize();
        ind = local.x;
		
        // Adjust direction angle of predator
		double chg = 0.0;
		if(local.x > 0.0) chg = -1.0;
		if(local.x < 0.0) chg = 1.0;
		angle += chg;
		
		if(angle < 0) angle += 360;
		if(angle > 360) angle -= 360;
		
		vel.x = MVector.FetchComponent(speed, angle+90);
	    vel.y = MVector.FetchComponent(speed, angle);
	    
	    addRepulsiveForce();
	}
	
	
	/**
	 * Recalculate predator movement by checking if prey
	 * is on left or right side of predator's sight line.
	 */
	protected void adjustMovement()
	{
		// Calculate prey vector local to predator
		Prey prey = (Prey) ((GameEngine)stage).getPlayer();
		MVector diff = prey.pos.substractVector(pos);
		
		MVector local = new MVector();
		local.x = MVector.FetchLocalXComponent(diff, angle);
		local.y = MVector.FetchLocalYComponent(diff, angle);
		local = local.normalize();
		ind = local.x;
		
		// Decide turn
		double chg = 0.0;
		if(local.x > 0.0) chg = -1.0;
		if(local.x < 0.0) chg = 1.0;
		angle += chg;
		
		if(angle < 0) angle += 360;
		if(angle > 360) angle -= 360;
		
		// Change speed components according to turn angle
		vel.x = MVector.FetchComponent(speed, angle+90);
	    vel.y = MVector.FetchComponent(speed, angle);
	    
	    addRepulsiveForce();
	}
	
	/**
	 * Overwrites the actor's act method since it uses an image
	 * to visualize the actor. Here the predator is a Java 2D construction
	 */
	public void act() {	
		
		adjustMovementIntercept();
		
		// Calculate new position
		pos.x += vel.x;
		pos.y += vel.y;
		x = (int) pos.x;
		y = (int) pos.y;
		
		fcnt++; // Increase frame counter
		
		// Handle predator exceeding screen
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
	 * Draws the predator using the Java 2D API.
	 */
	public void paint(Graphics2D g) {
		// Draw predator shape
		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0);
		gp.lineTo(10, 40);
		gp.lineTo(20, 0);
		gp.lineTo(0, 0);
		
		// Translate predator shape to its position
		// and rotate it
		AffineTransform id = new AffineTransform();
		id.rotate(Math.toRadians(angle), x+10, y+20);
		id.translate(x, y);
		Shape tShip = id.createTransformedShape(gp);
		g.setColor(Color.RED);
		g.draw(tShip);
		
		// Draw interception point
		if(icpt.x >= 0 && icpt.y >= 0)
		{
			g.drawLine((int)icpt.x,(int) icpt.y, (int)icpt.x, (int)icpt.y);
		}
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

	public double getInd() {
		return ind;
	}

	public void setInd(double ind) {
		this.ind = ind;
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
}
