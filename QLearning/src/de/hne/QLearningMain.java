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
	
	int[][] rMatrixArr = new int[][]{
		  { -1, -1, -1, -1,  0,  -1 },
		  { -1, -1, -1,  0, -1, 100 },
		  { -1, -1, -1,  0, -1,  -1 },
		  { -1,  0,  0, -1,  0,  -1 },
		  {  0, -1, -1,  0, -1, 100 },
		  { -1,  0, -1, -1,  0, 100 }		  
		};
	
	IntMatrix rMatrix = null;
		
	int[][] qMatrixArr = new int[][]{
		  {  0,  0,  0,  0,  0,  0 },
		  {  0,  0,  0,  0,  0,  0 },
		  {  0,  0,  0,  0,  0,  0 },
		  {  0,  0,  0,  0,  0,  0 },
		  {  0,  0,  0,  0,  0,  0 },
		  {  0,  0,  0,  0,  0,  0 }	  
		};
	
	IntMatrix qMatrix = null;
	
	int goalState = 5;
	int episodes = 1000;
	float gamma = 0.8f;
	
	public static void main(String args[]) {
		QLearningMain qLearn = new QLearningMain();
		qLearn.initLearning();
		qLearn.doQLearning();		
	}
	
	private void initLearning() {
		rMatrix = new IntMatrix(rMatrixArr);
		qMatrix = new IntMatrix(qMatrixArr);
	}
	
	private void doQLearning() {
		for(int i=0; i<episodes; i++) {
			doEpisode();	
		}
				
		qMatrix.normalizeMatrix();
		System.out.println("qMatrix:");
		qMatrix.printMatrix();
		System.out.println("=========================");
		System.out.println("Paths:");
		qMatrix.printAllPaths(5);

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
	
	private List<Pair<Integer, Integer>> fetchActions(int state, IntMatrix matrix) {
		List<Pair<Integer, Integer>> actionList = new ArrayList<>();
		for(int x=0; x<matrix.getColumn(state).length; x++) {
			if(matrix.getColumn(state)[x] > -1) {
				actionList.add(new Pair<Integer, Integer>(x, matrix.getColumn(state)[x]));
			}
		}
		
		return actionList;
	}	
	
	private void calcQState(int state, int newState) {
		int maxReward = -1;
		int currentReward = rMatrix.getValue(state, newState);
		List<Pair<Integer, Integer>> actionList = fetchActions(newState, qMatrix);
		for(Pair<Integer, Integer> action : actionList) {
			int reward = action.getElement1();
			if(maxReward < reward) {
				maxReward = reward;
			}
		}
		
		int qValue = currentReward + (int) (((float) maxReward) * gamma);
		qMatrix.setValue(state, newState, qValue);	
	}
	

	

	

}
