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
	
	int width = rMatrix[0].length;	
	int height = rMatrix.length;
	
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
			
			System.out.println("\nEpisode: " + i);
			System.out.println("====================");			
			printMatrix(qMatrix);	
			
		}
	}
	
	private void doEpisode() {		
		Random rand = new Random();		
		int state = rand.nextInt(6);				

		do {	
			// Select one among all possible actions for the current state and
			// using this possible action, consider going to the next state.			
			List<Pair<Integer, Integer>> actions = fetchRActions(state);			
			int newState = actions.get(rand.nextInt(actions.size())).getElement0();	
			calcQState(state, newState);
			state = newState;
		}	
		while(state < 5); //  Do While the goal state hasn't been reached	
	}
	
	private List<Pair<Integer, Integer>> fetchRActions(int state) {
		List<Pair<Integer, Integer>> actionList = new ArrayList<>();
		for(int x=0; x<rMatrix[state].length; x++) {
			if(rMatrix[state][x] > -1) {
				actionList.add(new Pair<Integer, Integer>(x, rMatrix[state][x]));
			}
		}
		
		return actionList;
	}
	
	private List<Pair<Integer, Integer>> fetchQActions(int state) {
		List<Pair<Integer, Integer>> actionList = new ArrayList<>();
		for(int x=0; x<qMatrix[state].length; x++) {
			if(qMatrix[state][x] > -1) {
				actionList.add(new Pair<Integer, Integer>(x, qMatrix[state][x]));
			}
		}
		
		return actionList;
	}	
	
	private void calcQState(int state, int newState) {
		int maxReward = -1;
		int currentReward = rMatrix[state][newState];
		List<Pair<Integer, Integer>> actionList = fetchQActions(newState);
		for(Pair<Integer, Integer> action : actionList) {
			int reward = action.getElement1();
			if(maxReward < reward) {
				maxReward = reward;
			}
		}
		
		int qValue = currentReward + (int) (((float) maxReward) * gamma);
		qMatrix[state][newState] = qValue;	
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
	
	//Q(1, 5) = R(1, 5) + 0.8 * Max[Q(5, 1), Q(5, 4), Q(5, 5)] = 100 + 0.8 * 0 = 100

}
