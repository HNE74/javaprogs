package de.hne.chaseevade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import de.hne.gameframework.Actor;
import de.hne.gameframework.GameEngine;

/**
 * The chase and evade engine provides the environment
 * to perform chase and evade algorithm tests.
 * @author Heiko Nolte
 * @since December 2008
 */
public class ChaseEvadeEngine extends GameEngine implements KeyListener {

	boolean doTrace = false;
	Predator predator = null;
	List obstacles = new ArrayList();
	
	/**
	 * Create the chase and evade engine.
	 * @param panel
	 * @param width
	 * @param height
	 */
	public ChaseEvadeEngine(JPanel panel, int width, int height)
	{
		InitEngine(panel, "res/", width, height, true, null, false, true);
		setShowFps(false);
	}
	
	/**
	 * Init the chase and evade world.
	 */
	protected void initWorld() {

		// Create actor list
		actors = new ArrayList();
		
		// Create predator
		predator = new Predator(this);
		actors.add(predator);

		// Create obstacles
		Obstacle obst = new Obstacle(this);
		obst.pos.x = 380;
		obst.pos.y = 280;
		obstacles.add(obst);
		actors.add(obst);
		
		obst = new Obstacle(this);
		obst.pos.x = 180;
		obst.pos.y = 180;
		obstacles.add(obst);
		actors.add(obst);
				
		obst = new Obstacle(this);
		obst.pos.x = 480;
		obst.pos.y = 380;
		obstacles.add(obst);
		actors.add(obst);

		obst = new Obstacle(this);
		obst.pos.x = 250;
		obst.pos.y = 200;
		obstacles.add(obst);
		actors.add(obst);

		
		// Create prey
		Prey ship = new Prey(this);
		ship.setX(300);
		ship.setY(500);
		player = ship;		
	}

	/**
	 * Print information about starship.
	 */
	protected void paintFeatures() {
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.setColor(Color.white);
		g.drawString("Angle: " + ((Prey) player).getAngle(), 10, 15);
		g.drawString("Speed: " + ((Prey) player).getSpeed(), 10, 30);
		g.drawString("X Speed: " + "" + ((Prey) player).vel.x, 10, 45);
		g.drawString("Y Speed: " + "" + ((Prey) player).vel.y, 10, 60);

		g.setColor(Color.RED);
		g.drawString("Angle: " + predator.getAngle(), 300, 15);
		g.drawString("Speed: " + predator.getSpeed(), 300, 30);
		g.drawString("X Speed: " + "" + predator.vel.x, 300, 45);
		g.drawString("Y Speed: " + "" + predator.vel.y, 300, 60);		
		g.drawString("Indicator: " + predator.getInd(), 300, 75);
		
		g.setColor(Color.white);	
		g.drawLine(0, 99, getWidth(), 99);
	}

	/**
	 * Overwrite the paint world method of the game engine in order to
	 * let engine print the ships path
	 */
	protected void paintWorld() {
		
		if(!doTrace) // No trace of movements
		{
			super.paintWorld();
			return;
		}
			
		// Draw background
		if(backgroundImage != null)
		{
			g.drawImage(backgroundImage, 0, 0, this.stageWidth, this.stageHeight, this);			
		}
		else
		{
			g.clearRect(0, 0, this.stageWidth, 100);
		}
		// Draw actors
		for (int i = 0; i < actors.size(); i++) {
			Actor m = (Actor) actors.get(i);
			if(m instanceof Predator && ((Predator) m).getFcnt() == 9)
			{
			  m.paint(g);
			  ((Predator) m).setFcnt(0);
			}
			else if(m instanceof Obstacle)
			{
				m.paint(g);
			}
		}
		
		// Draw player
		if(player != null && ((Prey) player).getFcnt() == 9) 
		{
			player.paint(g);
			((Prey) player).setFcnt(0);
		}

		// Show fps rate
		if(showFps) paintFps();
		
		// Paint other features
		paintFeatures();
			
		// Update visible world
		strategy.show();
	}
	
	
	/**
	 * Handle key pressed event.
	 */
	public void keyPressed(KeyEvent e) {
		synchronized(this)
		{
			if(this.gameState == GAME_STATE_RUNNING)
			{
				if(player != null) player.keyPressed(e);
			}
		}
		
	}

	/**
	 * Handle key released event.
	 */
	public void keyReleased(KeyEvent arg0) {
		// pass
	}

	/**
	 * Handle key typed event.
	 */
	public void keyTyped(KeyEvent arg0) {
		// pass
	}

}
