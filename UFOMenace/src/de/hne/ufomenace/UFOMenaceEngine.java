package de.hne.ufomenace;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.hne.gameframework.Actor;
import de.hne.gameframework.GameEngine;

/** 
 * UFO menace game engine.
 * @author Heiko Nolte
 * @since August 2008
 */
public class UFOMenaceEngine extends GameEngine implements KeyListener {
	
	// Additional game state
	public final int GAME_STATE_START = 3;
	public final int GAME_STATE_PROCEEDING = 4;
	
	// Shield boolean
	private boolean shieldActive = false;
	
	// Droid boolean
	private boolean droidActive = false;
	
	// Number of rockets fired
	private int rocketsFired = 0;	
		
	// Number of aliens
	private final int ALIEN_BEGIN = 3;
	private final int ALIEN_MAX = 10;
	private int alienCnt = ALIEN_BEGIN;
	
	// Number of bees
	private final int BEE_BEGIN = 0;
	private final int BEE_MAX = 10;
	private int beeCnt = BEE_BEGIN;	

	// Number of starships
	private int shipCnt = 3;
	
	// Number of shields
	private int shieldCnt = 3;
	
	// Score
	private int score = 0;
	
	// Level
	private int level = 0;
	
	// Difficulty
	private int diff = 0;
	
	// Planets
	private String planetImg[] = 
	{
		"pluto.gif", "neptun.gif", "uranus.gif", "saturn.gif",
		"jupiter.gif", "mars.gif", "venus.gif", "merkur.gif",
		"moon.gif", "erde.gif"
	};
	
	// Planet names
	private String planetNames[] = 
	{
		"Pluto", "Neptun", "Uranus", "Saturn",
		"Jupiter", "Mars", "Venus", "Mercury",
		"Moon", "Earth"
	};
	
	/**
	 * Create the UFO Menace engine as instance of GameEngine
	 * @param panel
	 */
	public UFOMenaceEngine(JPanel panel, int width, int height)
	{
		InitEngine(panel, "res/", width, height, true, null, false, true);
		setShowFps(true);
		this.gameState = GAME_STATE_START;
	}
	
	/**
	 * Generates games background image with stars and
	 * planets
	 */
	protected void generateBackgroundImage()
	{
		Color colors[] = { Color.WHITE, Color.YELLOW, Color.GREEN, Color.CYAN };
		BufferedImage background = 
			new BufferedImage(this.stageWidth, this.getStageHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D gc = background.createGraphics();
		
		// Generate stars
		for(int i=0; i<200; i++)
		{
			double x = new Double(Utils.RandomNumber(this.stageWidth)).doubleValue();
			double y = new Double(Utils.RandomNumber(this.stageHeight)).doubleValue();
			int thick = Utils.RandomNumber(5);
			
			Line2D.Double point = new Line2D.Double(x,y,x,y);
			BasicStroke stroke = new BasicStroke(thick, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
	        gc.setStroke(stroke);
			gc.setColor(colors[Utils.RandomNumber(4)]);
			gc.draw(point);
		}
		
		// Draw planet
		BufferedImage planet = this.imageCache.fetchImage(planetImg[level]);
		gc.drawImage(planet, 500, 300, this);
		
		this.backgroundImage = background;
	}
	
	/**
	 * Paints the start screen
	 */
	protected void paintStartscreen()
	{
		g.setFont(new Font("Arial", Font.BOLD, 60));
		g.setColor(Color.ORANGE);
		g.drawString("UFO Menace", 220, 170);
		
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.setColor(Color.WHITE);
		g.drawString("by Heiko Nolte / August 2008", 290, 200);
		
		g.setColor(Color.YELLOW);
		g.drawImage(imageCache.fetchImage("alien1.gif"), 294, 240, this);
		g.drawString("Alien ... Score: 25", 360, 258);
		g.drawImage(imageCache.fetchImage("bee1.gif"), 294, 270, this);
		g.drawString("Bee ... Score: 50", 360, 288);
		g.drawImage(imageCache.fetchImage("droid1.gif"), 290, 300, this);
		g.drawString("Droid ... Score: 75", 360, 318);
		g.drawImage(imageCache.fetchImage("ufo1.gif"), 282, 332, this);
		g.drawString("UFO ... Score: 100", 360, 348);
		g.drawImage(imageCache.fetchImage("bonus1.gif"), 294, 364, this);
		g.drawString("Bonus ... Score: 250", 360, 378);
		
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.setColor(Color.WHITE);
		g.drawString("Arrow keys to control ship. [s] to fire. [a] to active shield.", 200, 440);	
		g.drawString("Bonus ship every 5000 points.", 300, 460);	
		
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.setColor(Color.CYAN);
		g.drawString("Press space to start!", 315, 500);	
		
		boolean initStrategy = true;
		while(initStrategy)
		{
			try
			{
				strategy.show();
				initStrategy = false;
			}
			catch(RuntimeException ex)
			{
				// pass
			}
		}
	}
	
	/**
	 * Paint UFO Menace features
	 * @param g
	 */
	protected void paintFeatures()
	{
		paintScore();
		if(this.gameState == GAME_STATE_OVER) 
		{
			paintGameOver();
		}
		else if(gameState == GAME_STATE_PROCEEDING)
		{
			paintProceeding();
		}
	}

	/**
	 * Paints proceed message after level completed.
	 */
	protected void paintProceeding() {
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.setColor(Color.ORANGE);
		if(!planetNames[level].equals("Earth"))
		{
			g.drawString("Level completed, proceeding...", 200, 240);			
		}
		else
		{
			g.drawString("Congratulations, you saved mankind!", 200, 240);	
		}
	}
	
	/**
	 * Paint game over message.
	 * @param g
	 */
	protected void paintGameOver() {
		paintScore();
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.setColor(Color.ORANGE);
		g.drawString("G A M E  O V E R !", 210, 240);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		g.setColor(Color.GREEN);
		g.drawString("Final score: " + score, 320, 290);
		getSoundCache().playSound("bwep.wav");
	}	
	
	/**
	 * Paint the current score.
	 * @param g
	 */
	protected void paintScore() {
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.setColor(Color.white);
		g.drawString("Score: " + score, 10, 20);
		g.drawString("Ships: " + shipCnt, 10, 40);
		g.drawString("Shields: " + shieldCnt, 10, 60);
		g.drawString("Level: " + planetNames[level], 10, 80);
	}
	
	
	/**
	 * Init the gameworld.
	 */
	public void initWorld()
	{	
		// Create actors list
		actors = new ArrayList();

		// Create an initialize UFO
		UFO ufo = new UFO(this);
		int prob = ufo.getDropProb();
		if(prob > 30)
		{
			prob -= 3;
			ufo.setDropProb(prob);
		}
		ufo.initUFO();
		actors.add(ufo);
		
		// Create and initialize aliens
		for(int i=0; i<alienCnt; i++)
		{
			Alien alien = new Alien(this);
			alien.initAlien();

			actors.add(alien);
		}
		
		// Create and initialize bees
		for(int i=0; i<beeCnt; i++)
		{
			Bee bee = new Bee(this);
			bee.initBee();

			actors.add(bee);
		}

		// Create player starship
		Starship ship = new Starship(this);
		ship.setX(300);
		ship.setY(500);
		player = ship;
		
		// Init shield
		this.setShieldActive(true);
		this.addActor(new Shield(this));

		// Generate background image
		generateBackgroundImage();
	}
	
	/**
	 * Deploys a new starship.
	 */
	public void deployNewStarship()
	{
		shipCnt--; // Decrease number of starships
		shieldCnt = 3; // Reset number of shields
		
		// Create new player starship
		Starship ship = new Starship(this);
		ship.setX(300);
		ship.setY(500);
		player = ship;
		
		// Init shield
		this.setShieldActive(true);
		this.addActor(new Shield(this));
	}
	
	/**
     * Updates the game world. Reinitializes game if all
     * UFOs have been destroyed and increases the number of
     * enemies.
	 */
	protected void updateWorld() {
				
		super.updateWorld();
	
		// Restart with one more enemy when player has
		// reached to of the screen
		if(gameState == GAME_STATE_PROCEEDING)
		{
			if(player.getY() <= 0)
			{
				gameState = GAME_STATE_RUNNING;
				if(alienCnt < ALIEN_MAX && diff % 2 != 0) alienCnt++;
				if(beeCnt < BEE_MAX && diff % 2 == 0) beeCnt++;
				level++;
				diff++;
				if(level > 9) level = 0;
				initWorld();
			}
		}
		else if(gameState == GAME_STATE_RUNNING)
		{
			// Proceed to next level when there are no
			// actors except player on the screen
			if(actors.size() == 0)
			{
				gameState = GAME_STATE_PROCEEDING;
				((Starship) player).setShipState(Starship.SHIP_STATE_PROCEEDING);
				if(planetNames[level].equals("Earth"))
				{
					getSoundCache().playSound("win.wav");
				}
			}	
			else
			{
				// Add droid to game
				int prob = 3000 - (diff * 200);
				if (prob < 500) prob = 1000;
				int droidN = Utils.RandomNumber(prob);
				if(droidN >= 1 && droidN <= 5 && !droidActive)
				{
					Droid droid = new Droid(this);
					droid.initDroid();
					actors.add(droid);
					droidActive = true;
				}
			}
		}
	}

	/**
	 * Key listener to be forwarded to actor
	 * @param KeyEvent
	 */
	public void keyPressed(KeyEvent e) {
		
		synchronized(this)
		{
		if(this.gameState == GAME_STATE_RUNNING)
		{
			if(player != null) player.keyPressed(e);
		}
		else if(gameState == GAME_STATE_START)
		{
			// Start game
			gameState = GAME_STATE_RUNNING;
			this.score = 0;
			this.shipCnt = 3;
			this.level = 0;
			this.diff = 0;
			InitEngine(this.panel, "res/", this.stageWidth, this.stageHeight, true, null, false, true);	
		}
		else
		{
			// Check if space is pressed in order to restart
			int code = e.getKeyCode();
			if(code == KeyEvent.VK_SPACE)
			{
				this.gameState = GAME_STATE_START;
				InitEngine(this.panel, "res/", this.stageWidth, this.stageHeight, true, null, false, true);	
			}
		}
		}
	}
	
	/**
	 * Overwritten in order to fork to start screen.
	 */
	public void game()
	{	
		if(gameState == GAME_STATE_START) 
		{
			paintStartscreen();			
		}
		else 
		{
			super.game();			
		}

	}
	
	/**
	 * If called, the game is over.
	 */
	public void gameOver() {
		super.gameOver();
		alienCnt = ALIEN_BEGIN;
		beeCnt = BEE_BEGIN;
	}
	
	
	public void increaseScore(int inc)
	{
		// Increase score
		int oldSDiv = score / 5000; // For bonus ship determination
		score += inc;
		
		// Bonus ship determination
		int newSDiv = score / 5000;
		if(newSDiv > oldSDiv) 
		{
			shipCnt++;
			getSoundCache().playSound("freeship.wav");
		}		
	}
	
	/**
	 * Returns UFO if all aliens were destroyed and UFO is left.
	 */
	public UFO checkAliensDestroyedUFOLeft()
	{
		UFO ufo = null;
		for(java.util.Iterator it = actors.iterator(); it.hasNext(); )
		{
			Actor actor = (Actor) it.next();
			
			if(actor instanceof Alien)
			{
				return null;
			}
			else if(actor instanceof UFO)
			{
				ufo = (UFO) actor;
			}
		}
		
		return ufo;
	}
	
	/**
	 * Key listener to be forwarded to actor
	 * @param KeyEvent
	 */
	public void keyReleased(KeyEvent e) {
		if(player != null) player.keyReleased(e);
	}
    
	/**
	 * Key listener
	 */
	public void keyTyped(KeyEvent arg0) {
		// pass
	}

	/**
	 * Accessors
	 */

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getShipCnt() {
		return shipCnt;
	}

	public void setShipCnt(int shipCnt) {
		this.shipCnt = shipCnt;
	}

	public int getRocketsFired() {
		return rocketsFired;
	}

	public void setRocketsFired(int rocketsFired) {
		this.rocketsFired = rocketsFired;
	}

	public int getDiff() {
		return diff;
	}

	public void setDiff(int diff) {
		this.diff = diff;
	}

	public boolean isShieldActive() {
		return shieldActive;
	}

	public void setShieldActive(boolean shieldActive) {
		this.shieldActive = shieldActive;
	}

	public int getShieldCnt() {
		return shieldCnt;
	}

	public void setShieldCnt(int shieldCnt) {
		this.shieldCnt = shieldCnt;
	}

	public boolean isDroidActive() {
		return droidActive;
	}

	public void setDroidActive(boolean droidActive) {
		this.droidActive = droidActive;
	}
}
