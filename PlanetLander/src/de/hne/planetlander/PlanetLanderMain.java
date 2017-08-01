package de.hne.planetlander;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.hne.gameframework.GameEngine;

/**
 * Main class for planet lander simulation.
 * @author Heiko Nolte
 * @since December 2008
 */
public class PlanetLanderMain {

	public static void main(String args[])
	{
		PlanetLanderMain myself = new PlanetLanderMain();
		myself.doPlanetLander();
	}
	
	
	private void doPlanetLander()
	{
		// Create frame in which action happens
		JFrame frame = new JFrame("Planet Lander");
		int width = 800; 
		int height = 600;
		frame.setBounds(0, 0, width, height);
		frame.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setResizable(false);
		frame.setVisible(true);
		
		// Create game engine
		JPanel panel = (JPanel)frame.getContentPane();
		panel.setBackground(Color.BLACK);
		PlanetLanderEngine engine = 
			new PlanetLanderEngine(panel, width, height);
		frame.addKeyListener(engine);
		
		// On we go...
		while (true)
		{
			if(engine.getGameState() != GameEngine.GAME_STATE_OVER) engine.game();
			
			try
			{
				Thread.sleep(20);
			}
			catch(Exception ex) {}
		}
	}

}
