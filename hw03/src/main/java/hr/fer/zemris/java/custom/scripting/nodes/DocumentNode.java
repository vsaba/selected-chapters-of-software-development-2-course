package hr.fer.zemris.java.custom.scripting.nodes;


/**
 * A node which represents the base of the token tree
 * @author Vito Sabalic
 *
 */
public class DocumentNode extends Node{
	
	
	/**
	 * A simple constructor
	 */
	public DocumentNode() {
		super();
	}
	
	@Override
	public String toString() {
		String s = new String();
		int numberOfChildren = numberOfChildren();
		for(int i = 0; i < numberOfChildren; i++) {
			s = s.concat(getChild(i).toString());
		}
		return s;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		DocumentNode node = (DocumentNode) obj;
		
		boolean toReturn = this.toString().equals(node.toString());
		
		return toReturn;
		
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
		
	}
	
}
