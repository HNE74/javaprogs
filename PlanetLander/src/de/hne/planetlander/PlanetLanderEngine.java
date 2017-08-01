package de.hne.planetlander;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import de.hne.gameframework.GameEngine;

/**
 * The planet lander engine provides the environment for the 
 * lander simulation.
 * @author Heiko Nolte
 * @since December 2008
 */
public class PlanetLanderEngine extends GameEngine implements KeyListener {
	
	// Additional game state contants
	public final static int GAME_STATE_LANDED = 3;
	public final static int GAME_STATE_READY = 4;
	public final static int GAME_STATE_COLLISION = 5;
	public final static int GAME_STATE_TOOFAR = 6;
	public final static int GAME_STATE_ESCAPED = 7;
	public final static int GAME_STATE_START = 8;

	// Constants to verify correct landing
	private final double maxYVel = 1;
	private final double maxLeftAngle = 172;
	private final double maxRightAngle = 188;
		
	private double gravity = 0.016; // Added to vertical velocity 	
	private Shape bgShape = null; // Background image
	private List validBases = null; // List of landing bases shapes
	private int ships = -1; // Number of ships
	private int score = 0; // Score
	private int highscore = 0; // Highscore
	private final int maxBonus = 1001; // Maximum bonus
	private int bonus = 0; // Bonus to gain when landed

	
	private boolean escaping = false; // Flag for escape mode
	private int messageCnt = -1; // Counter for message display duration
	private int messageNdx = 0; // Message index 
	
	/**
	 * Create the chase and evade engine.
	 * @param panel
	 * @param width
	 * @param height
	 */
	public PlanetLanderEngine(JPanel panel, int width, int height)
	{
		InitEngine(panel, "res/", width, height, true, null, false, true);
		setShowFps(false);
		soundCache.loadResource("res/thrust.wav");
		gameState = GAME_STATE_START;
	}
	
	/**
	 * Init the chase and evade world.
	 */
	protected void initWorld() {

		// Create actor list and leave it empty for now
		actors = new ArrayList();

		// Create lander
		Lander lander = new Lander(this);
		lander.getPos().x = Math.random() * 400;
		lander.getPos().y = 20;
		lander.getVel().x = 1;
		player = lander;
		score = 0;
		bonus = maxBonus;
		ships = 3;
		
		// Generate background image
		validBases = new ArrayList();
		generateBackground();
	}
	
	/**
	 * Continue after landing
	 */
	public void continueAfterLanding()
	{
		bonus = maxBonus;
		
		int bases = validBases.size();
		if(bases == 0)
		{
			escaping = true;
		}	
		
		gameState = GAME_STATE_RUNNING;
	}
	
	/**
	 * Continue after escape.
	 */
	public void continueAfterEscape()
	{
		// Init lander
		Lander lander = new Lander(this);
		lander.getPos().x = Math.random() * 400;
		lander.getPos().y = 20;
		lander.getVel().x = 1;
		player = lander;

		// Generate new landscape
		generateBackground();

		// Adjust Bonus
		bonus = maxBonus;
		
		// Game state to running
		gameState = GAME_STATE_RUNNING;
		escaping = false;
	}
	
	/**
	 * Continue game after lander has crashed 
	 */
	public void continueAfterCrash()
	{
		// Create lander
		Lander lander = new Lander(this);
		lander.getPos().x = Math.random() * 400;
		lander.getPos().y = 20;
		lander.getVel().x = 1;	
		player = lander;
		ships--;
		
		// Adjust Bonus
		bonus = maxBonus;
		
		if(ships == 0) // Game over if no ships left
		{
			gameState = GAME_STATE_OVER;
			paintWorld();
		}
		else // Continue if ships still available
		{
			gameState = GAME_STATE_RUNNING;
		}
	}
	
	/**
	 * Paint the game world.
	 */
	protected void paintWorld() {
		
		if(gameState == GAME_STATE_START)
		{
			if(backgroundImage == null)
			{
				generateBackground();
			}
			g.drawImage(backgroundImage, 0, 0, this.stageWidth, this.stageHeight, this);
			
			int ybase = 100;
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.setColor(Color.PINK);
			String txt = "Planet Lander";	
			int xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase);		

			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.setColor(Color.WHITE);
			txt = "by Heiko Nolte / January 2009";	
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase+25);	
			
			ybase += 50;
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.setColor(Color.LIGHT_GRAY);
			txt = "Use left and right arrow to steer ship.";	
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase);	
			
			ybase += 20;
			txt = "Arrow up to activate thruster.";
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase);				

			ybase += 20;
			txt = "Arrow down to lower gear.";
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase);				

			ybase += 20;
			txt = "Shift to pull gear up.";
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase);			

			ybase = getHeight() - 50;
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.setColor(Color.YELLOW);
			txt = "Highscore: " + highscore;	
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase);	
			
			strategy.show();
		}
		else
		{
			super.paintWorld();
		}
	}
	
	/**
	 * Generates landscape as background image.
	 */
	protected void generateBackground()
	{
		backgroundImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D bg = (Graphics2D) backgroundImage.getGraphics();
		
		// Determine landing base positions
		Map baseMap = new HashMap();
		int base1 = (int) Math.abs((Math.random() * 195) / 5) * 5 + 30;
		baseMap.put("" + base1, "" + base1);
		int base2 = (int) Math.abs((Math.random() * 200) / 5) * 5 + 250;
		baseMap.put("" + base2, "" + base2);
		int base3 = (int) Math.abs((Math.random() * 200) / 5) * 5 + 520;
		baseMap.put("" + base3, "" + base3);
		
		// Init pen to draw landscape
		int xStart = 0;
		int yStart = 400;
		int xEnd = 0;
		int yEnd = 400;
		GeneralPath gp = new GeneralPath();
		gp.moveTo(xStart, yStart);
				
		// Draw landscape and bases to land on
		int xDelta = 5;
		int xBase = 30;
		int yDelta = 20;
		String isBase = null;
		while(xEnd <= getWidth())
		{
			// If current x coordinate matches base coordinate
			// isBase will not be null
			isBase = (String) baseMap.get(xStart + "");
			
			if(isBase != null) 
			{
				// Draw base
				xEnd = xStart + xBase;
				yEnd = yStart;
				
				Rectangle2D.Double base = new Rectangle2D.Double(xStart, yStart-5, xEnd-xStart, 5);
				bg.setColor(Color.GREEN);
				bg.fill(base);
				bg.setColor(Color.ORANGE);
				
				// Add base shape to list of valid bases
				validBases.add(base);
			}
			else // Draw hill 
			{
				xEnd = xStart + xDelta;
				
				double yDir = Math.random();
				if(yDir <= 0.33)
				{
					yEnd += yDelta;
				}
				else if(yDir <= 0.66)
				{
					yEnd -= yDelta;
				}
				else
				{
					yEnd = yStart;
				}
				
				if(yEnd < 200) yEnd = 200;
				if(yEnd >= getHeight()-160) yEnd = getHeight() - yDelta-160;
			}
			
			gp.lineTo(xEnd, yEnd);
			xStart = xEnd;
			yStart = yEnd;
		}
		
		// Make sure landscape is a closed shape, otherwise
		// collision detection wouldn't work correctly
		gp.lineTo(getWidth(), getHeight()-100);
		gp.lineTo(0, getHeight()-100);
		gp.lineTo(0, 400);

		// Draw landscape
		AffineTransform id = new AffineTransform();
		bgShape = id.createTransformedShape(gp);
		bg.setColor(Color.ORANGE);
		bg.fill(bgShape);
	}
	
	/**
	 * Checks collisions of actors and players if respective
	 * flags are set to true.
	 * @param playerActors
	 * @param actorActors
	 */
	protected void checkCollisions(boolean playerActors,
			                       boolean actorActors) {
		
		super.checkCollisions(playerActors, actorActors);

		// Check lander collision with hills
		Shape lShape = ((Lander) player).getTShip();		
		if(bgShape != null && lShape != null)
		{
			Area bg = new Area(bgShape);
			Area ld = new Area(lShape);
			bg.intersect(ld);
			
			if(!bg.isEmpty() && gameState != GAME_STATE_COLLISION)
			{
				gameState = GAME_STATE_COLLISION;
				soundCache.stopLoopSound("thrust.wav");
				soundCache.playSound("explosion.wav");
			}
		}
		
		// Check lander collision with base
		if(lShape != null)
		{
			int toRemove = -1;
			int cnt = 0;
			for(Iterator it = validBases.iterator(); it.hasNext(); )
			{
				// Check if lander is colliding with base
				Shape bShape = (Shape) it.next();
				Area bs = new Area(bShape);
				Area ld = new Area(lShape);
				bs.intersect(ld);
				
				if(!bs.isEmpty() &&
				   gameState != GAME_STATE_LANDED) // Collision since intersect not empty
				{							
					boolean landed = false; // Flag if landing was successful
					
					if(((Lander) player).getAngle() >= maxLeftAngle &&
					   ((Lander) player).getAngle() <= maxRightAngle &&
					   ((Lander) player).isGearDown()) // Angle ok, gear down?
					{
						// Set lander angle
						((Lander) player).setAngle(180);
						
						// Base correctly hit?
						Rectangle2D basePos = bShape.getBounds2D();
						if(player.getX() >= basePos.getX()-3 &&
						   player.getX()+player.getWidth() <= basePos.getX()+basePos.getWidth()+3)
						{
							// Vertical speed not too high?
							if(((Lander) player).getVel().y <= maxYVel)
							{
								// Landing is successful
								landed = true;
								
								// Add bonus to score
								score += bonus; 
									
								// Mark base to be removed from list of valid bases
								toRemove = cnt;
								
								// Change base color
								Graphics2D bg = (Graphics2D) backgroundImage.getGraphics();
								bg.setColor(Color.RED);
								bg.fill(bShape);
								
								// Adjust game state, score and fuel
								gameState = GAME_STATE_LANDED;
						        messageNdx = (int) (Math.random() * 10);
								
								// Disable thruster
								((Lander) player).setThrustOn(false);
								soundCache.stopLoopSound("thrust.wav");
								soundCache.playSound("landed.wav");
							}
						}
					}
					
					// Collision with rocks
					if(!landed && gameState != GAME_STATE_COLLISION)
					{
						gameState = GAME_STATE_COLLISION;
						soundCache.stopLoopSound("thrust.wav");
						soundCache.playSound("explosion.wav");
						paintFeatures();
					}
						
					break;
				}
				
				cnt++; // Increment counter
			}
			
			// Remove base from list of valid bases
			if(toRemove > -1)
			{
				validBases.remove(toRemove);
			}
		}
	}	

	/**
	 * Print information about starship.
	 */
	protected void paintFeatures() {
		
		// Adjust bonus
		if(gameState == GAME_STATE_RUNNING)
		{
			bonus--;
			if(bonus < 0) bonus = 0;
		}
		
		// Output score and ships left
		g.setColor(Color.WHITE);		
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Score: " + score, 5, 15);
		g.drawString("Ships: " + ships, 730, 15);
	
		// Output bonus
		String txt = null; int xpos;
		if(!escaping && gameState != GAME_STATE_OVER)
		{
			txt = "Bonus: " + bonus;
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, 15);
		}
		
		// Prepare feature paint
		int ybase = getHeight()-100;
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.setColor(Color.YELLOW);
		
		// Fuel bar
		int barWidth = 720;
		int barHeight = 16;
		g.drawRect(60, ybase+10, barWidth, barHeight);
		double fuelWidth = barWidth / ((Lander) player).getMaxFuel() * ((Lander) player).getFuel();  
		g.fillRect(60, ybase+10, (int) fuelWidth, barHeight);
		g.drawString("Fuel:", 20, ybase+22);
		
		// Vertical speed bar
		g.setColor(Color.GREEN);
		g.drawRect(60, ybase+29, barWidth, barHeight);
		double speedWidth = barWidth / ((Lander) player).getMaxYVel() * ((Lander) player).getVel().y;  
		if(((Lander) player).getVel().y > maxYVel)
		{
			g.setColor(Color.RED);
		}
		g.fillRect(60, ybase+29, (int) speedWidth, barHeight);
		g.drawString("Fall:", 20, ybase+42);
		
		if(gameState == GAME_STATE_LANDED) // Landed
		{
			String txtEls[] = { "Touchdown!!!", "Well done!", "The eagle has landed.",
					         "One small step...", "Nice to meet you.", "Just rocket science.",
					         "Reached parking position.", "Hello again!", "Live long and prosper.",
					         "What planet is this?"
			               };
			txt = txtEls[messageNdx];
			
			ybase = 100;
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.setColor(Color.GREEN);
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase);
			txt = "Bonus:" + bonus;
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase+25);
		}
		else if(gameState == GAME_STATE_ESCAPED) // Escaped
		{
			ybase = 100;
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.setColor(Color.GREEN);
			txt = "Congratulations, proceed to next drop zone!";	
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase);
			int fuelBonus = (int) (((Lander) player).getFuel() * 10);
			txt = "Remaining fuel bonus: " + fuelBonus;	
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase+25);
			if(messageCnt == -1) score += fuelBonus;
			messageCnt++;
			if(messageCnt == 250) 
			{
				messageCnt = -1;
				continueAfterEscape();
			}
		}
		else if(gameState == GAME_STATE_TOOFAR) // Bounds exceeded
		{
			ybase = 100;
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.setColor(Color.CYAN);
			txt = "Too far!";	
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase);
			if(messageCnt == -1) soundCache.stopLoopSound("thrust.wav");
			messageCnt++;
			if(messageCnt == 250) 
			{
				messageCnt = -1;
				continueAfterCrash();
			}
		}
		else if(gameState == GAME_STATE_OVER) // Game over
		{
			ybase = 100;
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.setColor(Color.CYAN);
			txt = "Game over";	
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase);
		    txt = "Final score: " + score;	
			xpos = Utils.FetchCenterX(txt, getWidth(), g);
			g.drawString(txt, xpos, ybase + 25);	
		}				
	}
	
	/**
	 * Handle key pressed event.
	 */
	public void keyPressed(KeyEvent e) {
		synchronized(this)
		{
			if(gameState == GAME_STATE_RUNNING ||
			   gameState == GAME_STATE_LANDED)
			{
				if(player != null) player.keyPressed(e);
			}
			else if(gameState == GAME_STATE_OVER)
			{
				gameState = GAME_STATE_START;
				if(highscore < score) highscore = score;
			}
			else if(gameState == GAME_STATE_START)
			{
				gameState = GAME_STATE_RUNNING;
				initWorld();
			}
		}		
	}

	/**
	 * Handle key released event.
	 */
	public void keyReleased(KeyEvent e) {
		synchronized(this)
		{
			if(gameState == GAME_STATE_RUNNING)
			{
				if(player != null) player.keyReleased(e);
			}
		}	
	}

	/**
	 * Handle key typed event.
	 */
	public void keyTyped(KeyEvent arg0) {
		// pass
	}

	public double getGravity() {
		return gravity;
	}

	public List getValidBases() {
		return validBases;
	}

	public boolean isEscaping() {
		return escaping;
	}

	public void setEscaping(boolean escaping) {
		this.escaping = escaping;
	}

}
