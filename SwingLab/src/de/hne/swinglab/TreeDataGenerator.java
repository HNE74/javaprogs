package de.hne.swinglab;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class TreeDataGenerator {
	
	public DefaultMutableTreeNode init() {
		return new DefaultMutableTreeNode("Root");
	}
		
	public void addNodes(TreeModel model, int cnt, int depth) {		
        DefaultMutableTreeNode vegetableNode = new DefaultMutableTreeNode("Vegetables");
        vegetableNode.add(new DefaultMutableTreeNode("Capsicum"));
        vegetableNode.add(new DefaultMutableTreeNode("Carrot"));
        vegetableNode.add(new DefaultMutableTreeNode("Tomato"));
        vegetableNode.add(new DefaultMutableTreeNode("Potato"));
         
        DefaultMutableTreeNode fruitNode = new DefaultMutableTreeNode("Fruits");
        fruitNode.add(new DefaultMutableTreeNode("Banana"));
        fruitNode.add(new DefaultMutableTreeNode("Mango"));
        fruitNode.add(new DefaultMutableTreeNode("Apple"));
        fruitNode.add(new DefaultMutableTreeNode("Grapes"));
        fruitNode.add(new DefaultMutableTreeNode("Orange"));
        
        //add the child nodes to the root node
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) model.getRoot();
        node.add(vegetableNode);
        node.add(fruitNode);
        
        ((DefaultTreeModel) model).reload();

	}
}
