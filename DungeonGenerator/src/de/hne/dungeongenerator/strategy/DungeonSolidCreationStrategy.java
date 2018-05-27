package de.hne.dungeongenerator.strategy;

import de.hne.dungeongenerator.data.Dungeon;

/**
 * Creates a dungeon consisting only of solid walls.
 * @author hnema
 */
public class DungeonSolidCreationStrategy implements IDungeonCreationStrategy {

	@Override
	public Dungeon create(int width, int height) {
		Dungeon dungeon = new Dungeon(width, height);
		
		return dungeon;
	}

}
