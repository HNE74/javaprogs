package de.hne.dungeongenerator.renderer;

import de.hne.dungeongenerator.data.Dungeon;
import de.hne.dungeongenerator.data.Dungeon.Cell;

/**
 * Renders the passed dungeon on the console.
 * @author hnema
 */
public class DungeonConsoleRenderer implements IDungeonRenderer {
	
	private static final String CELL_WALL = "#";
	private static final String CELL_EMPTY = " ";
	
	@Override
	public void render(Dungeon dungeon) {
		int width = dungeon.getWidth();
		int height = dungeon.getHeight();
		for(int r = 0; r<height; r++) {
			for(int c=0; c<width; c++) {
				Cell cell = dungeon.getCell(r, c);
				switch (cell) {
				case WALL:
					System.out.print(CELL_WALL);
					break;
				case EMPTY:
					System.out.print(CELL_EMPTY);
					break;
				default:
					break;				
				}
			}
			System.out.print("\n");
		}
	}

}
