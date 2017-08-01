package de.hne.planetlander;

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
 * The ship to be landed on the planet.
 * @author Heiko Nolte	
 * @since December 2008
 */
public class Lander extends Player {
	
	private double thrust = 0; // Thrust as scalar value
	private double angle = 180.0; // Steering angle of ship
	private final double maxFuel = 220; // Maximum fuel
	private double fuel = maxFuel; // Fuel left
	private double maxYVel = 2.0; // Maximum fall velocity
	private int explColorCnt = 0; // Counter for explosion color
	private int totalExplCnt = 0; // Total explosion counter
	
	private MVector vel = new MVector(); // Velocity vector
	private MVector pos = new MVector(); // Position vector
	 
	private boolean thrustOn = false; // Flag is thruster is on
	private boolean gearDown = false; // Flag if gear is down
	
	Shape tShip = null; // Ship shape
	
	// Key state flags
	boolean upPressed = false;
	boolean downPressed = false;
	boolean leftPressed = false;
	boolean rightPressed = false;
	boolean shiftPressed = false;
	
	/**
	 * Creates the prey.
	 * @param stage
	 */
	public Lander(Stage stage) {
		super(stage);
		pos.x = 600;
		pos.y = 120;
		width = 20;
		height = 40;
	}
	
	/**
	 * Handle key pressed event
	 */
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {	
		case KeyEvent.VK_UP:
			upPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			leftPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = true;
			break;
		case KeyEvent.VK_SHIFT:
			shiftPressed = true;
			break;
		}
	}
	
	/**
	 * Handle key released event.
	 */
	public void keyReleased(KeyEvent e) {
		
		switch (e.getKeyCode()) {	
		case KeyEvent.VK_UP:
			upPressed = false;
			break;
		case KeyEvent.VK_LEFT:
			leftPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = false;
			break;
		case KeyEvent.VK_SHIFT:
			shiftPressed = false;
			break;			
		}
	}
	
	/**
	 * Control lander according to key pressed flags.
	 */
	private void controlLander()
	{
		if(((PlanetLanderEngine) stage).getGameState() == 
			   PlanetLanderEngine.GAME_STATE_LANDED) 
		{
			// Lander landed an ready to start
			if(upPressed) 
			{	
				// Reset velocity and increase thrust
				// to take off value
				thrust = 0.3;
				vel.x = 0;
				vel.y = 0;
				
				// Flag thrust on
				if(!thrustOn)
				{
					((GameEngine) stage).getSoundCache().loopSound("thrust.wav");
					thrustOn = true;
				}
				
				// Set game state to running again
                ((PlanetLanderEngine) stage).continueAfterLanding();
			}
		}
		else
		{	
			// Apply thrust if arrow up key pressed
			if(upPressed)
			{
				if(((PlanetLanderEngine) stage).getGameState() == 
					   PlanetLanderEngine.GAME_STATE_RUNNING) 
				{								
					// No thrust if fuel is empty
					if(fuel > 0) 
					{
						// Increase speed
						thrust += 0.0075;
						if(thrust > 0.1) thrust = 0.1;
						
						// Decrease fuel 
						if(gearDown)
							fuel -= 0.5;
						else
							fuel -= 0.25;
						if(fuel < 0) fuel = 0;
						
						// Flag thrust on
						if(!thrustOn)
						{
							((GameEngine) stage).getSoundCache().loopSound("thrust.wav");
							thrustOn = true;
						}
					}
				}	
			}
			
			// Turn right if arrow right pressed
			if(rightPressed)
			{
				angle = angle + 1.0;
				if(angle >= 360) angle = angle - 360;
			}
		
			// Turn left if arrow right pressed
			if(leftPressed)
			{
				angle = angle - 1.0;
				if(angle < 0) angle = angle + 360;
			}
			
			// Lower gear
			if(downPressed)
			{
				gearDown = true;
			}
			
			// Gear up
			if(shiftPressed)
			{
				gearDown = false;
				downPressed = false;
			}
		}

		// If thrust is on turn it off if arrow up key
		// is not pressed
		if(!upPressed)
		{	
			if(thrustOn)
			{
				((GameEngine) stage).getSoundCache().stopLoopSound("thrust.wav");
				thrustOn = false;
			}
		}
	}
		
	/**
	 * Overwrites the actor's act method since it uses an image
	 * to visualize the actor. Here the prey is a Java 2D construction
	 */
	public void act() {	
		
		// Evaluate key flags and control lander accordingly
		controlLander();
		
		if(((PlanetLanderEngine) stage).getGameState() == 
		   PlanetLanderEngine.GAME_STATE_RUNNING)
		{
			pos.x += vel.x;
			pos.y += vel.y;
			x = (int) pos.x;
			y = (int) pos.y;
					
			// Lander exceeds top or bottom of screen
			if (x < -20)
			{
				((PlanetLanderEngine) stage).setGameState(PlanetLanderEngine.GAME_STATE_TOOFAR);

			}
			else if (x >= ((GameEngine) stage).getStageWidth())
			{
				((PlanetLanderEngine) stage).setGameState(PlanetLanderEngine.GAME_STATE_TOOFAR);
			}
			
			// Lander exceeds left or right of screen
			if (y < -40)
			{
				if(((PlanetLanderEngine) stage).isEscaping() ==
				   true)
				{
					((PlanetLanderEngine) stage).setGameState(PlanetLanderEngine.GAME_STATE_ESCAPED);
				} 
				else
				{
					((PlanetLanderEngine) stage).setGameState(PlanetLanderEngine.GAME_STATE_TOOFAR);
				}
			}
			else if (y >= ((GameEngine) stage).getStageHeight())
			{
				y = 0;
				pos.y = y;
			}
			
			// Consider planet gravity
			vel.y += ((PlanetLanderEngine) stage).getGravity();
			
			// Consider thrust
			if(thrustOn)
			{
				// Adjust velocity according to lander steering angle
				vel.x += MVector.FetchComponent(thrust, angle+90);
			    vel.y += MVector.FetchComponent(thrust, angle);
			}
			else
			{
			    // Set  
				thrust = 0.0;
			}
			
			// Limit lander fall velocity
			if(vel.y > maxYVel) vel.y = maxYVel;
		}
	}
		
	/**
	 * Draws the prey using the Java 2D API.
	 */
	public void paint(Graphics2D g) {
		
		if(((PlanetLanderEngine) stage).getGameState() == 
			   PlanetLanderEngine.GAME_STATE_OVER)
		{
			return;
		}
		
		// Draw lander shape
		GeneralPath gp = new GeneralPath();
		if(gearDown)
		{
			gp.moveTo(0, 4);
			gp.lineTo(10, 40);
			gp.lineTo(20, 4);
			gp.lineTo(20, 0);
			gp.lineTo(16, 0);
			gp.lineTo(16, 4);
			gp.lineTo(4, 4);
			gp.lineTo(4, 0);
			gp.lineTo(0, 0);
			gp.lineTo(0, 4);
		}
		else
		{
			gp.moveTo(0, 3);
			gp.lineTo(10, 40);
			gp.lineTo(20, 3);
			gp.lineTo(0, 3);			
		}
		
		// Translate prey shape to its position
		// and rotate it
		AffineTransform id = new AffineTransform();
		id.rotate(Math.toRadians(angle), x+10, y+20);
		id.translate(x, y);
		tShip = id.createTransformedShape(gp);
		
		// Show flickering ship on collision of lander with rocks
		if(((PlanetLanderEngine) stage).getGameState() == 
			   PlanetLanderEngine.GAME_STATE_COLLISION)
		{
			if(totalExplCnt > 200)
			{
				g.setColor(g.getBackground());
				((PlanetLanderEngine) stage).continueAfterCrash();
			}
			else
			{
				if(explColorCnt < 10)
				{
					g.setColor(Color.WHITE);				
				}
				else
				{
					g.setColor(Color.YELLOW);								
				}
				explColorCnt++;
				totalExplCnt++;
				if(explColorCnt > 20) explColorCnt = 0;
			}
		}
		else // Normal lander color
		{
			g.setColor(Color.LIGHT_GRAY);			
		}
		
		// Remove lander from screen when game is over
		if(((PlanetLanderEngine) stage).getGameState() == 
			   PlanetLanderEngine.GAME_STATE_OVER)
		{
			g.setColor(Color.BLACK);
		}
		
		g.fill(tShip);
		
		// Paint thrust exhaust
		if(thrustOn)
		{
			// Draw fume
			gp = new GeneralPath();
			gp.moveTo(4, 4);
			gp.lineTo(16, 4);
			gp.lineTo(16, 0);
			gp.lineTo(4, 0);
			gp.lineTo(4, 4);
			
			// Translate prey shape to its position
			// and rotate it
			id = new AffineTransform();
			id.rotate(Math.toRadians(angle), x+10, y+20);
			id.translate(x, y);
			Shape fume = id.createTransformedShape(gp);
					
			// Remove fume from screen when game is over
			if(((PlanetLanderEngine) stage).getGameState() == 
				   PlanetLanderEngine.GAME_STATE_OVER)
			{
				g.setColor(Color.BLACK);
			}
			else
			{
				g.setColor(Color.YELLOW);
			}
			
			// Fill fume
			g.fill(fume);
		}
	}

	public double getSpeed() {
		return thrust;
	}

	public void setSpeed(double speed) {
		this.thrust = speed;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
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

	public Shape getTShip() {
		return tShip;
	}

	public void setTShip(Shape ship) {
		tShip = ship;
	}

	public boolean isThrustOn() {
		return thrustOn;
	}

	public void setThrustOn(boolean thrustOn) {
		this.thrustOn = thrustOn;
	}

	public double getFuel() {
		return fuel;
	}

	public void setFuel(double fuel) {
		this.fuel = fuel;
	}

	public double getMaxFuel() {
		return maxFuel;
	}

	public double getMaxYVel() {
		return maxYVel;
	}

	public boolean isGearDown() {
		return gearDown;
	}
}
