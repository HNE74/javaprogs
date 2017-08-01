package de.hne.planetlander;

import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Utility methods for chase and evade
 * 
 * @author Heiko Nolte
 * @since December 2008
 */
public class Utils {

	public static int FetchCenterX(String txt, int width ,Graphics g)
	{
	      FontMetrics fm = g.getFontMetrics(g.getFont());
	      int x = (int)(width - fm.stringWidth(txt)) / 2;
	      
	      return x;
	}
	
	public static void main(String args[]) {

	}

}
