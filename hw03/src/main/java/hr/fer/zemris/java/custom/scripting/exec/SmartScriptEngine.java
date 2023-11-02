package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.lexer.LexerToken;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A class which interprets and compiles SmartScript code. Writes all the
 * interpretations to an instance of {@link RequestContext}
 * 
 * @author Vito Sabalic
 *
 */
public class SmartScriptEngine {

	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack objectMultistack;

	/**
	 * The node visitor
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.out.println("Unable to write to document");
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {

			ValueWrapper start = new ValueWrapper(node.getStartExpression().asText());

			objectMultistack.push(node.getVariable().asText(), start);
			Object increment = node.getEndExpression().asText();
			Object condition = node.getStepExpression().asText();

			while (start.numCompare(condition) <= 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(visitor);
				}

				start = objectMultistack.pop(node.getVariable().asText());
				start.add(increment);
				objectMultistack.push(node.getVariable().asText(), start);
			}

			objectMultistack.pop(node.getVariable().asText());
		}

		@Override
		public void visitEchoNode(EchoNode node) {

			Stack<Object> stack = new Stack<>();

			for (Element element : node.getElements()) {

				if (element.getToken() == LexerToken.DOUBLE || element.getToken() == LexerToken.INT
						|| element.getToken() == LexerToken.STRING) {
					stack.push(element.asText());
				} else if (element.getToken() == LexerToken.VARIABLE) {
					stack.push(objectMultistack.peek(element.asText()).getValue());
				} else if (element.getToken() == LexerToken.OPERATOR) {
					stack.push(performArithmeticOperation(stack.pop(), stack.pop(), element.asText()).toString());
				} else if (element.getToken() == LexerToken.FUNCTION) {
					switch (element.asText()) {
					case "sin":
						stack.push(Math.sin(Double.parseDouble((String) stack.pop())));
						break;
					case "decfmt":
						DecimalFormat format = new DecimalFormat((String) stack.pop());
						stack.push(format.format(stack.pop()));
						break;
					case "dup":
						Object obj = stack.pop();
						stack.push(obj);
						stack.push(obj);
						break;
					case "swap":
						Object a = stack.pop();
						Object b = stack.pop();

						stack.push(a);
						stack.push(b);
						break;
					case "setMimeType":
						requestContext.setMimeType((String) stack.pop());
						break;
					case "paramGet":
						String defValue = (String) stack.pop();
						String name = (String) stack.pop();
						String value = requestContext.getParameter(name);

						stack.push(value == null ? defValue : value);
						break;
					case "pparamGet":
						String defValuePersistant = (String) stack.pop();
						String namePersistant = (String) stack.pop();
						String valuePersistant = requestContext.getPersistentParameter(namePersistant);

						stack.push(valuePersistant == null ? defValuePersistant : valuePersistant);
						break;
					case "pparamSet":
						String pname = (String) stack.pop();
						String pvalue = (String) stack.pop();
						requestContext.setPersistentParameter(pname, pvalue);
						break;
					case "pparamDel":
						requestContext.removePersistentParameter((String) stack.pop());
						break;
					case "tparamGet":
						String defValueTemp = (String) stack.pop();
						String nameTemp = (String) stack.pop();
						String valueTemp = requestContext.getTemporaryParameter(nameTemp);

						stack.push(valueTemp == null ? defValueTemp : valueTemp);
						break;
					case "tparamSet":
						String tname = (String) stack.pop();
						String tvalue = (String) stack.pop();
						requestContext.setTemporaryParameter(tname, tvalue);
						break;
					case "tparamDel":
						requestContext.removeTemporaryParameter((String) stack.pop());
						break;
					default:
						throw new IllegalArgumentException("Unknown function" + element.asText());
					}
				} else {
					throw new IllegalArgumentException("Unknown token!");
				}

			}

			if (!stack.isEmpty()) {
				List<Object> objects = new ArrayList<>();

				while (!stack.isEmpty()) {
					objects.add(stack.pop());
				}

				Collections.reverse(objects);

				for (Object object : objects) {
					try {
						requestContext.write(object.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(visitor);
			}
		}

		/**
		 * Performs arithmetic operations between two Objects, uses the
		 * {@link ValueWrapper} class as a helper
		 * 
		 * @param obj1      The first object
		 * @param obj2      The second object
		 * @param operation The operation to be executed
		 * @return Returns the result of the operation
		 */
		private Object performArithmeticOperation(Object obj1, Object obj2, String operation) {
			ValueWrapper value = new ValueWrapper(obj1);

			switch (operation) {
			case "+":
				value.add(obj2);
				break;
			case "-":
				value.divide(obj2);
				break;
			case "*":
				value.multiply(obj2);
				break;
			case "/":
				value.divide(obj2);
				break;
			default:
				throw new IllegalArgumentException("Unknown operation provided: " + operation);
			}

			return value.getValue();
		}
	};

	/**
	 * A simple constructor, assigns and initializes all values
	 * 
	 * @param documentNode   The provided document node
	 * @param requestContext The provided request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {

		this.documentNode = documentNode;
		this.requestContext = requestContext;
		this.objectMultistack = new ObjectMultistack();
	}

	/**
	 * Executes the parsed smart script code
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
