package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node which stores the elements from the {@link Element} class
 * @author Vito Sabalic
 *
 */
public abstract class Node {
	
	
	/**
	 * An internal collection which stores the child nodes of the current node
	 */
	List<Node> col;
	
	/**
	 * A simple constructor
	 */
	public Node() {
		col = new ArrayList<>();
	}

	/**
	 * Adds a child node to the {@link ArrayIndexedCollection} collection
	 * @param child the node to be added
	 */
	public void addChildNode(Node child) {
		col.add(child);
	}
	
	/**
	 * Returns the current number of children of this node stored in the collection
	 * @return the current number of children of this node stored in the collection
	 */
	public int numberOfChildren() {
		return col.size();
	}
	
	/**
	 * Fetches a child currently situated at the provided index
	 * @param index the index from which the child is retrieved
	 * @return returns the obtained child node
	 */
	public Node getChild(int index) {
		return (Node)col.get(index);
	}
	
	/**
	 * A method which accepts a {@link INodeVisitor}
	 * @param visitor The provided visitor
	 */
	public abstract void accept(INodeVisitor visitor);
}
