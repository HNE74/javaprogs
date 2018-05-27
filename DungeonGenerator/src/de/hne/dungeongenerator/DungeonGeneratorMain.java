package de.hne.dungeongenerator;

import de.hne.dungeongenerator.DungeonGenerator.DungeonType;
import de.hne.dungeongenerator.data.Dungeon;

public class DungeonGeneratorMain {
	public static void main(String args[]) {
		for(int i=0; i<1000; i++) {
			Dungeon dungeon = DungeonGenerator.generateDungeon(DungeonType.SQUARED_ROOM, 40, 20);
			DungeonGenerator.printToConsole(dungeon);
			System.out.println();
		}
	}
}
