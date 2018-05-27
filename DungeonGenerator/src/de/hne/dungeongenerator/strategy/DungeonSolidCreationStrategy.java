package de.hne.dungeongenerator.strategy;

import de.hne.dungeongenerator.data.Dungeon;

public class DungeonSolidCreationStrategy implements IDungeonCreationStrategy {

	@Override
	public Dungeon create(int width, int height) {
		Dungeon dungeon = new Dungeon(width, height);
		
		return dungeon;
	}

}
