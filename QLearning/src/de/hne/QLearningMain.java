package de.hne;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Q-Learning algorithm goes as follows:
 * 1. Set the gamma parameter, and environment rewards in matrix R.
 * 2. Initialize matrix Q to zero.
 * 3. For each episode:
 * Select a random initial state.
 * Do While the goal state hasn't been reached.
 *  -Select one among all possible actions for the current state.
 *  -Using this possible action, consider going to the next state.
 *  -Get maximum Q value for this next state based on all possible actions.
 *  -Compute: Q(state, action) = R(state, action) + Gamma * Max[Q(next state, all actions)]
 *  -Set the next state as the current state.
 * End Do
 * End For
 * http://mnemstudio.org/path-finding-q-learning-tutorial.htm
 * @author 057530
 */
public class QLearningMain {
	
	int[][] rMatrix = new int[][]{
		  { -1, -1, -1, -1,  0,  -1 },
		  { -1, -1, -1,  0, -1, 100 },
		  { -1, -1, -1,  0, -1,  -1 },
		  { -1,  0,  0, -1,  0,  -1 },
		  {  0, -1, -1,  0, -1, 100 },
		  { -1,  0, -1, -1,  0, 100 }		  
		};
		
	int[][] qMatrix = new int[][]{
		  {  0,  0,  0,  0,  0,  0 },
		  {  0,  0,  0,  0,  0,  0 },
		  {  0,  0,  0,  0,  0,  0 },
		  {  0,  0,  0,  0,  0,  0 },
		  {  0,  0,  0,  0,  0,  0 },
		  {  0,  0,  0,  0,  0,  0 }	  
		};	
	
	int goalState = 5;
	int episodes = 1000;
	float gamma = 0.8f;
	
	public static void main(String args[]) {
		QLearningMain qLearn = new QLearningMain();
		qLearn.doQLearning();		
	}
	
	private void doQLearning() {
		for(int i=0; i<episodes; i++) {
			doEpisode();	
		}
				
		normalizeMatrix(qMatrix);
		System.out.println("qMatrix:");
		printMatrix(qMatrix);
		System.out.println("=========================");
		System.out.println("Paths:");
		for(int i=0; i<rMatrix.length; i++) {
			if(i != goalState) {
				printPath(i);
			}
			System.out.println();
		}
	}
	
	private void doEpisode() {		
		Random rand = new Random();		
		int state = rand.nextInt(6);				

		do {	
			// Select one among all possible actions for the current state and
			// using this possible action, consider going to the next state.			
			List<Pair<Integer, Integer>> actions = fetchActions(state, rMatrix);			
			int newState = actions.get(rand.nextInt(actions.size())).getElement0();	
			
			// Adjust Q state matrix
			calcQState(state, newState);
			state = newState;
		}	
		while(state < goalState); //  Do While the goal state hasn't been reached	
	}
	
	private List<Pair<Integer, Integer>> fetchActions(int state, int[][] matrix) {
		List<Pair<Integer, Integer>> actionList = new ArrayList<>();
		for(int x=0; x<matrix[state].length; x++) {
			if(matrix[state][x] > -1) {
				actionList.add(new Pair<Integer, Integer>(x, matrix[state][x]));
			}
		}
		
		return actionList;
	}	
	
	private void calcQState(int state, int newState) {
		int maxReward = -1;
		int currentReward = rMatrix[state][newState];
		List<Pair<Integer, Integer>> actionList = fetchActions(newState, qMatrix);
		for(Pair<Integer, Integer> action : actionList) {
			int reward = action.getElement1();
			if(maxReward < reward) {
				maxReward = reward;
			}
		}
		
		int qValue = currentReward + (int) (((float) maxReward) * gamma);
		qMatrix[state][newState] = qValue;	
	}
	
	private void normalizeMatrix(int[][] matrix) {
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
	
	private List<Integer> fetchPath(int state) {
		int newState = state;
		List<Integer> result = new ArrayList<>();
		result.add(state);
		
		if(state == goalState) {
			return result;
		}
		
		do
		{
			int max = -1;			
			for(int i=0; i<qMatrix.length; i++) {
				if(max < qMatrix[state][i]) {
					max = qMatrix[state][i];
					newState = i;
				}
			}
			
			result.add(newState);	
			state = newState;
		} 
		while(state != goalState);
		
		return result;
	}
	
	private void printMatrix(int[][] matrix) {
		for (int y=0; y<matrix.length; y++)	{
			StringBuilder line = new StringBuilder();
			for(int x=0; x<matrix[0].length; x++) {
				line.append(String.format("%03d", matrix[y][x]));
				if(x<matrix[0].length-1) {
					line.append(", ");
				}
			}
			System.out.println(line.toString());
		}
	}
	
	private void printPath(int startState) {
		List<Integer> path = fetchPath(startState);
		for(Integer node : path) {
			System.out.print(node + " - ");
		}
	}
}
