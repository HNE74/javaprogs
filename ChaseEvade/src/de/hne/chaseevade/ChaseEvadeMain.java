package de.hne.chaseevade;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.hne.gameframework.GameEngine;

/**
 * Main class for chase and evade tests.
 * @author Heiko Nolte
 * @since December 2008
 */
public class ChaseEvadeMain {

	public static void main(String args[])
	{
		ChaseEvadeMain myself = new ChaseEvadeMain();
		myself.doChaseEvade();
	}
	
	
	private void doChaseEvade()
	{
		// Create frame in which action happens
		JFrame frame = new JFrame("Chase and Evade");
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
		ChaseEvadeEngine engine = 
			new ChaseEvadeEngine(panel, width, height);
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
