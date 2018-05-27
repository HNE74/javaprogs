package de.hne.dungeongenerator.strategy;

import de.hne.dungeongenerator.data.Dungeon;

/**
 * Definition of strategy to create the dungeon.
 * @author hnema
 */
public interface IDungeonCreationStrategy {
	
	/**
	 * Creates a 2D dungeon with the provided width and size.
	 * @param width
	 * @param height
	 * @return Dungeon
	 */
	Dungeon create(int width, int height);

}
