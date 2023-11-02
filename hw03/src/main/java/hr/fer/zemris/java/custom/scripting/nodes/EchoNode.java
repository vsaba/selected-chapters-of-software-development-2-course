package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node which stores the elements from the {@link Element} class
 * @author Vito Sabalic
 *
 */
public class EchoNode extends Node{
	
	/**
	 * The array which stores the elements
	 */
	private Element[] elements;
	
	/**
	 * A simple constructor which assigns the provided elements array to the current elements array
	 * @param elements the array to be assigned
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Returns the element array
	 * @return the element array
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public String toString() {
		
		String s = "{$=";
		for(int i = 0; i < elements.length; i++) {
			s = s.concat(elements[i].asText());
		}
		
		return s + "$}";
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
	
}
