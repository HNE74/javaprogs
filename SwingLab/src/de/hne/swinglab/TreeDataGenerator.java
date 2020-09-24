package de.hne.swinglab;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class TreeDataGenerator {
	
	Random rnd = new Random();
	private int maxLevel = 4;
	private int maxChildren = 5;
	private int maxThreads = 5;
	
	public DefaultMutableTreeNode init() {
		return new DefaultMutableTreeNode("Root");
	}
		
	public void addNodes(TreeModel model) {
		ExecutorService executor = Executors.newFixedThreadPool(20);
		for(int i=0; i<maxThreads; i++) {
			executor.submit(() -> {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) model.getRoot();
				generateChildren(model, node, 0);
			});
		}

		for(int i=0; i<maxThreads; i++) {
			executor.submit(() -> {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) model.getRoot();
				removeChildren(model, node, 0);
			});
		}

	}
	
	private void removeChildren(TreeModel model, DefaultMutableTreeNode node, int level) {
		int val = rnd.nextInt(2);
		if((val == 1 || level == maxLevel) && node.getChildCount() > 0) {	
			int rm = rnd.nextInt(node.getChildCount());
			node.remove(rm);
			((DefaultTreeModel) model).nodeStructureChanged(node);
			System.out.println("Removed node " + level + "_" + rm);
			try {
				Thread.sleep(rnd.nextInt(20000) + 1000);
			} catch (InterruptedException e) {
				// pass
			}			
		}
		else if(level < maxLevel) {
			removeChildren(model, node, level+1);
		}
	}
	
	private void generateChildren(TreeModel model, DefaultMutableTreeNode node, int level) {
		int numberOfChildren = rnd.nextInt(maxChildren);

		for(int i=0; i<numberOfChildren; i++) {
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(level + "_" + i);	
			node.add(child);
			((DefaultTreeModel) model).nodeStructureChanged(node);
			System.out.println("Added node " + level + "_" + i);			
			try {
				Thread.sleep(rnd.nextInt(10000) + 1000);
			} catch (InterruptedException e) {
				// pass
			}				


			
			if(level < maxLevel) {
				generateChildren(model, child, level+1);
			}
		}			
	}	
}
