package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A class which represents the tokens situated inside a For - tag. The class
 * has 4 different possible elements of the {@link Element} class. It is
 * obligated to contain at least one {@link ElementVariable}
 * 
 * @author Vito Sabalic
 *
 */
public class ForLoopNode extends Node {

	/**
	 * The obligatory {@link ElementVariable} value
	 */
	private ElementVariable variable;

	/**
	 * The first expression inside the for - tag
	 */
	private Element startExpression;

	/**
	 * The last expression inside the for - tag
	 */
	private Element endExpression;

	/**
	 * The middle expression inside the for - tag
	 */
	private Element stepExpression;

	/**
	 * A simple constructor which assigns all four provided values to the each
	 * current value respectively
	 * 
	 * @param variable the {@link ElementVariable} to be assigned
	 * @param start    the first expression to be assigned
	 * @param end      the last expression to be assigned
	 * @param step     the middle expression to be assigned
	 */
	public ForLoopNode(ElementVariable variable, Element start, Element end, Element step) {
		this.variable = variable;
		this.startExpression = start;
		this.endExpression = end;
		this.stepExpression = step;
	}

	/**
	 * A simple constructor which assigns three provided values to each curretn
	 * value respectively
	 * 
	 * @param variable the {@link ElementVariable} to be assigned
	 * @param start    the first expression to be assigned
	 * @param end      the last expression to be assigned
	 */
	public ForLoopNode(ElementVariable variable, Element start, Element end) {
		this.variable = variable;
		this.startExpression = start;
		this.endExpression = end;
		this.stepExpression = null;
	}

	/**
	 * A basic default constructor
	 */
	public ForLoopNode() {

	}

	/**
	 * Sets the {@link ElementVariable} value
	 * 
	 * @param variable the value to be assigned
	 */
	public void setVariable(ElementVariable variable) {
		this.variable = variable;
	}

	/**
	 * Sets the first expression of a for-tag
	 * 
	 * @param startExpression the value to be assigned
	 */
	public void setStartExpression(Element startExpression) {
		this.startExpression = startExpression;
	}

	/**
	 * Sets the last expression of a for-tag
	 * 
	 * @param endExpression the value to be assigned
	 */
	public void setEndExpression(Element endExpression) {
		this.endExpression = endExpression;
	}

	/**
	 * Sets the middle expression of a for-tag
	 * 
	 * @param stepExpression the value to be assigned
	 */
	public void setStepExpression(Element stepExpression) {
		this.stepExpression = stepExpression;
	}

	/**
	 * Returns the {@link ElementVariable} value
	 * 
	 * @return the {@link ElementVariable} value
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Returns the first expression of a for-tag
	 * 
	 * @return the first expression of a for-tag
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns the last expression of a for-tag
	 * 
	 * @return the last expression of a for-tag
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns the middle expression of a for-tag
	 * 
	 * @return the middle expression of a for-tag
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	/**
	 * Returns the string value of the implemented values. Takes into account the
	 * Child nodes of the current class
	 */
	@Override
	public String toString() {
		String s = "{$FOR ";
		if (stepExpression != null) {
			s = s + getVariable().asText() + getStartExpression().asText() + getStepExpression().asText()
					+ getEndExpression().asText() + "$}";
		} else {
			s = s + getVariable().asText() + getStartExpression().asText() + getEndExpression().asText() + "$}";
		}
		if (numberOfChildren() != 0) {
			for (int i = 0; i < numberOfChildren(); i++) {
				s = s.concat(getChild(i).toString());
			}
		}

		return s + "\n{$END$}";
	}

	@Override
	public void accept(INodeVisitor visitor) {
		
		visitor.visitForLoopNode(this);
	}

}
