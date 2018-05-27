package de.hne.dungeongenerator.strategy;

import java.util.Random;

import de.hne.dungeongenerator.data.Dungeon;
import de.hne.dungeongenerator.data.Dungeon.Cell;

/**
 * Creates a dungeon with squared rooms.
 * @author hnema
 */
public class DungeonSquaredRoomCreationStrategy implements IDungeonCreationStrategy {
	
	private Random random = new Random();	
	
	@Override
	public Dungeon create(int width, int height) {
		Dungeon dungeon = new Dungeon(width, height);
		createMainRoom(dungeon);
		
		return dungeon;
	}
	
	private void createMainRoom(Dungeon dungeon) {
		int centerRow = dungeon.getHeight() / 2;
		int centerCol = dungeon.getWidth() / 2;
	
		int horShift = random.nextInt(10);
		int verShift = random.nextInt(10);
		
		int roomWidth = horShift + dungeon.getWidth() / 2;
		int roomHeight = verShift + dungeon.getHeight() / 2;
		
		int rowStart = centerRow + verShift / 2 - roomHeight / 2;
		int colStart = centerCol + horShift / 2 - roomWidth / 2;
		
		for(int r=rowStart; r<roomHeight; r++) {
			for(int c=colStart; c<roomWidth; c++) {
				dungeon.setCell(r, c, Cell.EMPTY);
			}
		}

	}
}
