package de.hne.dungeongenerator;

import de.hne.dungeongenerator.data.Dungeon;
import de.hne.dungeongenerator.renderer.DungeonConsoleRenderer;
import de.hne.dungeongenerator.renderer.IDungeonRenderer;
import de.hne.dungeongenerator.strategy.DungeonRoomCreationStrategy;
import de.hne.dungeongenerator.strategy.DungeonSolidCreationStrategy;
import de.hne.dungeongenerator.strategy.IDungeonCreationStrategy;

public class DungeonGenerator {
	
	public enum DungeonType {
		SOLID, ROOM;
	}
	
	
	/**
	 * Generates a dungeon of a certain type.
	 * @param type
	 * @param width
	 * @param height
	 * @return Dungeon
	 */
	public static Dungeon generateDungeon(DungeonType type, int dungeonWidth, int dungeonHeight) {
		IDungeonCreationStrategy creationStrategy = null;
		
		if(type == null) {
			type = DungeonType.SOLID;
		}
		
		switch (type) {
			case ROOM:
				creationStrategy = new DungeonRoomCreationStrategy();
				break;
			case SOLID:
				creationStrategy = new DungeonSolidCreationStrategy();
				break;
			default:
				creationStrategy = new DungeonSolidCreationStrategy();				
				break;
		}
		
		Dungeon dungeon = null;
		if(creationStrategy != null) {
			dungeon = creationStrategy.create(dungeonWidth, dungeonHeight);
		}
		
		return dungeon;
	}

	/**
	 * Prints the provided dungeon to the console.
	 * @param dungeon
	 */
	public static void printToConsole(Dungeon dungeon) {
		IDungeonRenderer renderer = new DungeonConsoleRenderer();
		renderer.render(dungeon);
	}
	
}
