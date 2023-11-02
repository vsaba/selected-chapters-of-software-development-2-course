package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node which stores text saved as a string
 * @author Vito Sabalic
 *
 */
public class TextNode extends Node{
	
	/**
	 * The text value to be saved
	 */
	private String text;
	
	
	/**
	 * A simple constructor which assigns the provided text value to the current text value
	 * @param text
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Retrieves the currently stored text value
	 * @return the currently stored text value
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		if(text.contains(String.valueOf('\\'))) {
			int index = text.indexOf('\\');
			text = text.substring(0, index) + '\\' + text.substring(index, text.length());
		}
		
		if(text.contains(String.valueOf('{'))) {
			int index = text.indexOf('{');
			text = text.substring(0, index) + '\\' + text.substring(index, text.length());
		}
		return getText();
	}

	@Override
	public void accept(INodeVisitor visitor) {

		visitor.visitTextNode(this);
	}

}
