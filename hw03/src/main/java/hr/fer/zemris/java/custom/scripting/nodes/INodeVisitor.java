package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * An interface used for modeling a node visitor
 * @author Vito Sabalic
 *
 */
public interface INodeVisitor {
	
	/**
	 * Method which is called when a text node is visited
	 * @param node The text node
	 */
	void visitTextNode(TextNode node);
	
	/**
	 * Method which is called when a for loop node is visited
	 * @param node The for loop node
	 */
	void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Method which is called when a echo node is visited
	 * @param node The echo node
	 */
	void visitEchoNode(EchoNode node);
	
	/**
	 * Method which is called when a document node is visited
	 * @param node The document node
	 */
	void visitDocumentNode(DocumentNode node);

}
