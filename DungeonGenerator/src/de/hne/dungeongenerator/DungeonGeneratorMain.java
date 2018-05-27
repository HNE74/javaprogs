package de.hne.dungeongenerator;

import de.hne.dungeongenerator.DungeonGenerator.DungeonType;
import de.hne.dungeongenerator.data.Dungeon;

public class DungeonGeneratorMain {
	public static void main(String args[]) {
		Dungeon dungeon = DungeonGenerator.generateDungeon(DungeonType.SOLID, 20, 10);
		DungeonGenerator.printToConsole(dungeon);
	}
}
