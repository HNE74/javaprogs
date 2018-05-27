package de.hne.dungeongenerator.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Data structure for a dungeon.
 * @author hnema
 */
public class Dungeon {
	
	public enum Cell {
		EMPTY, WALL
	}
	
	private int width;
	private int height;
	
	private final List<List<Cell>> cells;
	
	/**
	 * Konstruktor.
	 * @param width
	 * @param height
	 */
	public Dungeon(int width, int height) {
		this.width = width;
		this.height = height;		
		this.cells = init();
	}
	
	private List<List<Cell>> init() {
		List<List<Cell>> result = new ArrayList<>();
		for(int i=0; i<height; i++) {
			List<Cell> row = fetchSolidRow(this.width);
			result.add(row);
		}		
		
		return result;
	}
	
	private List<Cell> fetchSolidRow(int width) {
		List<Cell> row = new ArrayList<Cell>();
		for(int i=0; i<width; i++) {
			row.add(Cell.WALL);
		}
		
		return row;
	}
	
	/**
	 * Sets the state of a cell in the dungeon.
	 * @param row
	 * @param column
	 * @param cell
	 */
	public void setCell(int row, int column, Cell cell) {
		if(row < 0 || row > this.height) {
			throw new IllegalArgumentException("Row must be between 0 and " + height + ", value passed:" + row);
		}

		if(column < 0 || column > this.width) {
			throw new IllegalArgumentException("Column must be between 0 and " + width + ", value passed:" + column);
		}
		
		List<Cell> rowCells = cells.get(row);
		if(rowCells != null) {
			rowCells.set(column, cell);
		}
	}
	
	/**
	 * Gets the state of a cell in the dungeon.
	 * @param row
	 * @param column
	 * @return Cell
	 */
	public Cell getCell(int row, int column) {
		if(row < 0 || row > this.height) {
			throw new IllegalArgumentException("Row must be between 0 and " + height + ", value passed:" + row);
		}

		if(column < 0 || column > this.width) {
			throw new IllegalArgumentException("Column must be between 0 and " + width + ", value passed:" + column);
		}
		
		List<Cell> rowCells = cells.get(row);
		if(rowCells != null) {
			return rowCells.get(column);
		}
		
		return null;
	}	

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<List<Cell>> getCells() {
		return cells;
	}	
}
