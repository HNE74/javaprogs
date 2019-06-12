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
	
	private Room mainRoom = null;
	
	private int horShiftRand = 5;
	private int verShiftRand = 5;
	
	private int horSmallRoomShiftRand = 5;
	private int verSmallRoomShiftRand = 5;
	
	private int smallRoomMaxCnt = 10;
	
	private Dungeon dungeon = null;
	

	@Override
	public Dungeon create(int width, int height) {
		dungeon = new Dungeon(width, height);
		createMainRoom();
		
		for(int i=0; i<this.smallRoomMaxCnt; i++) {
			createSmallRoom();			
		}
		
		return dungeon;
	}
	
	private void createMainRoom() {
		int centerRow = dungeon.getHeight() / 2;
		int centerCol = dungeon.getWidth() / 2;
	
		int horShift = random.nextInt(horShiftRand);
		int verShift = random.nextInt(verShiftRand);
		
		int roomWidth = horShift + this.dungeon.getWidth() / 2;
		int roomHeight = verShift + this.dungeon.getHeight() / 2;
		
		int rowStart = centerRow + verShift / 2 - roomHeight / 2;
		int colStart = centerCol + horShift / 2 - roomWidth / 2;
		
		this.mainRoom = new Room(rowStart, colStart, roomWidth, roomHeight);
		drawRoom(this.mainRoom);
	}
	
	private void drawRoom(Room room) {
		for(int r=room.getRow(); r<room.getHeight(); r++) {
			for(int c=room.getCol(); c<room.getWidth(); c++) {
				this.dungeon.setCell(r, c, Cell.EMPTY);
			}
		}		
	}
	
	private void createSmallRoom() {
		int smallRoomWidth = random.nextInt(horSmallRoomShiftRand) + 3;
		int smallRoomHeight = random.nextInt(verSmallRoomShiftRand) + 3;
		
		Room smallRoom = new Room(1, 1, smallRoomWidth, smallRoomHeight);
		
		for(int r=1; r<this.dungeon.getHeight() - smallRoomHeight; r++) {
			for(int c=1; c<this.dungeon.getWidth() - smallRoomWidth; c++) {
				smallRoom.setRow(r);
				smallRoom.setCol(c);
				if(smallRoomFit(smallRoom)) {
					drawRoom(smallRoom);
					return;
				}
			}			
		}
	}
	
	private boolean smallRoomFit(Room room) {
		// check top row
		for(int i=0; i<room.getWidth(); i++) {
			if(dungeon.getCell(room.getRow(), room.getCol() + i) == Cell.EMPTY) {
				return false;
			}
		}
		
		// check bottom row
		for(int i=0; i<room.getWidth(); i++) {
			if(dungeon.getCell(room.getRow() + room.getHeight(), room.getCol() + i) == Cell.EMPTY) {
				return false;
			}
		}

		// check left column
		for(int i=0; i<room.getHeight(); i++) {
			if(dungeon.getCell(room.getRow() + i, room.getCol()) == Cell.EMPTY) {
				return false;
			}
		}

		// check right column
		for(int i=0; i<room.getHeight(); i++) {
			if(dungeon.getCell(room.getRow() + i, room.getCol() + room.getWidth()) == Cell.EMPTY) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Room descriptor. 
	 * @author hnema
	 */
	private class Room {
		
		int row;
		int col;
		final int width;
		final int height;
		
		public Room(int row, int col, int width, int height) {
			this.row = row;
			this.col = col;
			this.width = width;
			this.height = height;
		}
		
		public int getColCenter() {
			return this.col + this.height / 2;
		}
		
		public int getRowCenter() {
			return this.row + this.width / 2;
		}
		
		public void setRow(int row) {
			this.row = row;
		}
		
		public int getRow() {
			return row;
		}
		
		public void setCol(int col) {
			this.col = col;
		}

		public int getCol() {
			return col;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}		
	}
}
