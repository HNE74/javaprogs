package de.hne.gameframework;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * The engine controls the game, forwards input events and displays the stage
 * with its actors.
 * 
 * @author Heiko Nolte / based on tutorial classes by Alexander Hristov
 * @since August 2008
 */
public abstract class GameEngine extends Canvas implements Stage {

	// Game states
	public final static int GAME_STATE_RUNNING = 1;
	public final static int GAME_STATE_OVER = 2;
	
	// Flag indicating if game is over
	protected int gameState = GAME_STATE_RUNNING;	
	
	protected BufferStrategy strategy; // Double buffering strategy
	
	protected Graphics2D g = null; // To paint things

	private long usedTime; // to calculated fps

	// Contains images and sounds
	protected ImageCache imageCache;
	protected SoundCache soundCache;

	// The actors and the player
	protected ArrayList actors;
	protected Player player;
	
	// Resources path
	protected String resPath = null;

	// Stage dimensions
	protected int stageWidth;
	protected int stageHeight;
	
	// Flags for collision checks
	protected boolean checkPlayerActors;
	protected boolean checkActorActors;

	// Flag if fps are to be shown
	protected boolean showFps = false;
	
	// The panel to be used to show the game
	protected JPanel panel;
	
	// Background image
	protected BufferedImage backgroundImage = null;

	/**
	 * Create and initialize game engine.
	 * 
	 * @param panel
	 * @param resPath
	 * @param stageWidth
	 * @param stageHeight
	 * @param disableMouseCursor
	 * @param backgroundImageName
	 * @param checkPlayerActors
	 * @param checkActorActors
	 */
	public void InitEngine(JPanel panel, String resPath, int stageWidth,
			          int stageHeight, boolean disableMouseCursor,
			          String backgroundImageName, boolean checkPlayerActors,
			          boolean checkActorActors) {
		
		// Set passed values as members
		this.panel = panel;
		this.resPath = resPath;
		this.stageWidth = stageWidth;
		this.stageHeight = stageHeight;
		this.checkPlayerActors = checkPlayerActors;
		this.checkActorActors = checkActorActors;

		// Initialize image and sound cache
		this.imageCache = new ImageCache(this.resPath);
		this.soundCache = new SoundCache(this.resPath);

		// Retrieve background image
		if(backgroundImageName != null)
		{
			this.backgroundImage = 
				this.imageCache.fetchImage(backgroundImageName);
		}
		
		// Set panel dimensions
		setBounds(0, 0, this.stageWidth, this.stageHeight);
		this.panel.setPreferredSize(new Dimension(this.stageWidth, this.stageHeight));
		this.panel.setLayout(null);
		this.panel.add(this);

		// Set double buffering strategy
		if(this.strategy == null) 
		{	
			createBufferStrategy(2);
			this.strategy = getBufferStrategy();
		}
		setIgnoreRepaint(true);
		
		// Init graphics context
		boolean done = false;
			while(!done) {
			try {
				this.g = (Graphics2D) strategy.getDrawGraphics();
				done = true;
			}
			catch(IllegalStateException e) {
				done = false;
			}
		}
		
		g.clearRect(0,0, this.stageWidth, this.stageHeight);

		// Mouse cursor might be disabled when passing over
		// the game engine's canvas
		if (disableMouseCursor) {
			BufferedImage cursor = imageCache.createCompatible(10, 10,
					Transparency.BITMASK);
			Toolkit t = Toolkit.getDefaultToolkit();
			Cursor c = t.createCustomCursor(cursor, new Point(5, 5), "null");
			setCursor(c);
		}
	}

	/**
	 * If called, the game is over.
	 */
	public void gameOver() {
		this.gameState = GAME_STATE_OVER;
	}
	
	/**
	 * Initializes the games world. Might be used to distribute the actors on
	 * the screen and to add a background image.
	 */
	protected abstract void initWorld();

	/**
	 * Adds an actor to the stage.
	 * 
	 * @param actor
	 */
	public void addActor(Actor actor) {
		this.actors.add(actor);
	}

	/**
	 * Updates the game world. I.e. player and actors are acting, actors marked
	 * for removal are removed from actors list.
	 */
	protected void updateWorld() {
		int i = 0;

		
		if(player != null && !player.markedForRemoval) 
		{
			player.act();
		}
		else
		{
			player = null;
		}
		
		// Iterate over actors
		while (i < actors.size()) {
			Actor m = (Actor) actors.get(i);
			if (m.isMarkedForRemoval()) {
				actors.remove(i);
				m.beingRemoved();
			} else {
				m.act();
				i++;
			}
		}
	}

	/**
	 * Checks collisions of actors and players if respective
	 * flags are set to true.
	 * @param playerActors
	 * @param actorActors
	 */
	protected void checkCollisions(boolean playerActors,
			                       boolean actorActors) {
		
		// No collision check necessary
		if(!playerActors && !actorActors)
		{
			return;
		}
		
		// Fetch player bounds
		Rectangle playerBounds = null;
		if(player != null) playerBounds = player.getBounds();
		
		// Iterate over actors and check their collision
		for (int i = 0; i < actors.size(); i++) {
			
			// Get actor
			Actor a1 = (Actor) actors.get(i);
			Rectangle r1 = a1.getBounds();

			// Check if actor has collided with the player 
			if(playerActors && player != null)
			{
				if (r1.intersects(playerBounds)) {
					player.collision(a1);
					a1.collision(player);
				}				
			}
			
			// Check if actor has collided with other actor
			if(actorActors)
			{	
				for (int j = i + 1; j < actors.size(); j++) {
					Actor a2 = (Actor) actors.get(j);
					Rectangle r2 = a2.getBounds();
					if (r1.intersects(r2)) {
						a1.collision(a2);
						a2.collision(a1);
					}
				}
			}
		}
	}

	/**
	 * Paint the current fps rate.
	 * @param g
	 */
	protected void paintFps() {
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.setColor(Color.white);
		if (usedTime > 0)
			g.drawString(String.valueOf(1000 / usedTime) + " fps",
					 this.stageWidth-50, this.stageHeight-50);
		else
			g.drawString("--- fps", this.stageWidth-50, this.stageHeight-50);
	}

	/**
	 * Paint the game world.
	 */
	protected void paintWorld() {
		
		// Draw background
		if(backgroundImage != null)
		{
			g.drawImage(backgroundImage, 0, 0, this.stageWidth, this.stageHeight, this);			
		}
		else
		{
			g.clearRect(0, 0, this.stageWidth, this.stageHeight);
		}
		// Draw actors
		for (int i = 0; i < actors.size(); i++) {
			Actor m = (Actor) actors.get(i);
			m.paint(g);
		}
		
		// Draw player
		if(player != null) player.paint(g);

		// Show fps rate
		if(showFps) paintFps();
		
		// Paint other features
		paintFeatures();
			
		// Update visible world
		strategy.show();
	}
	
	/**
	 * To be implemented to paint context specific features.
	 * @param g
	 */
	protected abstract void paintFeatures();

	/**
	 * Engine carries out the game.
	 */
	public void game() {
				
		usedTime = 1000;
		
		// Init the game world
		initWorld();
		
		// Loop as long as canvas is visible or games is over
		while (isVisible() && gameState != GAME_STATE_OVER) 
		{
			// To calculate fps
			long startTime = System.currentTimeMillis();

			// Update the games world, I.e. recalculate actors and player
			// and remove actors if flag set
			updateWorld();

			// Checks if player and / or actors have collided
			checkCollisions(this.checkActorActors, this.checkActorActors);	
			
			// Repaint the game world
			paintWorld();
			
			// Makes sure the game speed is appropriate
			usedTime = System.currentTimeMillis() - startTime;
			do {
				Thread.yield();
			} while (System.currentTimeMillis() - startTime < 17);
		}
	}
	
	/**
	 * Accessors 
	 */

	public String getResPath() {
		return resPath;
	}

	public void setResPath(String resPath) {
		this.resPath = resPath;
	}

	public int getStageWidth() {
		return stageWidth;
	}

	public void setStageWidth(int stageWidth) {
		this.stageWidth = stageWidth;
	}

	public int getStageHeight() {
		return stageHeight;
	}

	public void setStageHeight(int stageHeight) {
		this.stageHeight = stageHeight;
	}

	public boolean isShowFps() {
		return showFps;
	}

	public void setShowFps(boolean showFps) {
		this.showFps = showFps;
	}

	public ImageCache getImageCache() {
		return imageCache;
	}

	public void setImageCache(ImageCache imageCache) {
		this.imageCache = imageCache;
	}

	public SoundCache getSoundCache() {
		return soundCache;
	}

	public void setSoundCache(SoundCache soundCache) {
		this.soundCache = soundCache;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public List getActors()
	{
		return this.actors;
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
}
