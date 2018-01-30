package de.hne;

import java.util.ArrayList;
import java.util.List;

public class IntMatrix {

	int[][] matrix = null;
	
	/**
	 * Creates the matrix.
	 * @param matrix
	 */
	public IntMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	/**
	 * Prints the matrix.
	 */
	public void printMatrix() {
		for (int y=0; y<this.matrix.length; y++)	{
			StringBuilder line = new StringBuilder();
			for(int x=0; x<this.matrix[0].length; x++) {
				line.append(String.format("%03d", this.matrix[y][x]));
				if(x<this.matrix[0].length-1) {
					line.append(", ");
				}
			}
			System.out.println(line.toString());
		}
	}
	
	/**
	 * Returns the path from startState to goalState as list of states.
	 * @param startState
	 * @param goalState
	 * @return
	 */
	public List<Integer> fetchPath(int startState, int goalState) {
		int newState = startState;
		int state = startState;
		List<Integer> result = new ArrayList<>();
		result.add(state);
		
		if(state == goalState) {
			return result;
		}
		
		do
		{
			int max = -1;			
			for(int i=0; i<matrix.length; i++) {
				if(max < matrix[state][i]) {
					max = matrix[state][i];
					newState = i;
				}
			}
			
			result.add(newState);	
			state = newState;
		} 
		while(state != goalState);
		
		return result;
	}	
	
	/**
	 * Prints the path from startState to goalState.
	 * @param startState
	 * @param goalState
	 */
	public void printPath(int startState, int goalState) {
		List<Integer> path = fetchPath(startState, goalState);
		for(Integer node : path) {
			System.out.print(node + " - ");
		}
	}
	
	public void printAllPaths(int goalState) {
		for(int i=0; i<matrix.length; i++) {
			if(i != goalState) {
				printPath(i, goalState);
			}
			System.out.println();
		}		
	}
	
	/**
	 * Normalizes the matrix.
	 * @param matrix
	 */
	public void normalizeMatrix() {
		float max = -1f;
		for (int y=0; y<matrix.length; y++)	{		
			for(int x=0; x<matrix[0].length; x++) {
				if(max<matrix[y][x]) {
					max = matrix[y][x];
				}
			}	
		}
		
		for (int y=0; y<matrix.length; y++)	{		
			for(int x=0; x<matrix[0].length; x++) {
				matrix[y][x] = (int) (100 / max * matrix[y][x]);
			}	
		}		
	}
	
	/**
	 * Returns a matrix column.
	 * @param c
	 * @return
	 */
	public int[] getColumn(int c) {
		return this.matrix[c];
	}
	
	/**
	 * Returns the value of the matrix on x and y.
	 * @param y
	 * @param x
	 * @return
	 */
	public int getValue(int y, int x) {
		return this.matrix[y][x];
	}
	
	/**
	 * Sets value on position y, x of matrix.
	 * @param y
	 * @param x
	 * @param value
	 * @return
	 */
	public void setValue(int y, int x, int value) {
		this.matrix[y][x] = value;
	}
}
