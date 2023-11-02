package hr.fer.zemris.java.custom.scripting.parser;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerToken;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * A class which implements a simple parser for tokens which a lexer produces
 * @author Vito Sabalic
 *
 */
public class SmartScriptParser {

	//write single constructor which accepts a string that contains the document body
	//create an instance of the lexer and delegate the body to it
	
	
	/**
	 * The lexer which produces the tokens
	 */
	private Lexer lexer;
	
	
	/**
	 * The base document node
	 */
	Node doc;
	
	/**
	 *A stack used for tree implementation 
	 */
	Stack<Node> stack;
	
	/**
	 * A constructor which receives the document body as text which needs to be parsed.
	 * Immediately starts the parsing process
	 * @param documentBody text which needs to be parsed
	 * @throws throws {@link SmartScriptParserException} if an exception occurs
	 */
	public SmartScriptParser(String documentBody) {
		this.lexer = new Lexer(documentBody);
		this.doc = new DocumentNode();
		stack = new Stack<>();
		
		stack.push(doc);
		
		
		try{
			
			startParse();
		}
		catch(RuntimeException e) {
			throw new SmartScriptParserException();
		}
		
	}
	
	/**
	 * Parses the document text
	 * @throws throws {@link SmartScriptParserException} if an exception occurs
	 */
	public void startParse() {
		
		Token t = lexer.getNextToken();
		
		while(t.getType() != LexerToken.EOF) {
			
			if(t.getType() == LexerToken.WORD) {
				Node toAddNode = new TextNode((String)t.getValue());
				Node stackNode = (Node)stack.peek();
				stackNode.addChildNode(toAddNode);
			}
			else if(t.getType() == LexerToken.FORTAG) {
				Node toAddNode = forTagParser();
				Node stackNode = (Node)stack.peek();
				stackNode.addChildNode(toAddNode);
				stack.push(toAddNode);
			}
			else if(t.getType() == LexerToken.EMPTYTAG) {
				Node toAddNode = emptyTagParser();
				Node stackNode = (Node)stack.peek();
				stackNode.addChildNode(toAddNode);
			}
			else if(t.getType() == LexerToken.ENDTAG) {
				stack.pop();
				if(stack.size() == 0) {
					throw new SmartScriptParserException();
				}
			}
			
			t = lexer.getNextToken();
		}
		
	}
	
	
	
	/**
	 * Creates a new object of the class {@link Element} based on the currently assigned token value
	 * @param t the token whose value is assigned to an element
	 * @return returns the created element
	 */
	public Element createNewElement(Token t) {
		
		Element el = null;
		
		if(t.getType() == LexerToken.VARIABLE) {
			el = new ElementVariable((String)t.getValue());
		}
		else if(t.getType() == LexerToken.STRING) {
			el = new ElementString((String)t.getValue());
		}
		else if(t.getType() == LexerToken.OPERATOR) {
			el =  new ElementOperator((String)t.getValue());
		}
		else if(t.getType() == LexerToken.FUNCTION) {
			el = new ElementFunction((String)t.getValue());
		}
		else if(t.getType() == LexerToken.INT) {
			el = new ElementConstantInteger((int)t.getValue());
		}
		else if(t.getType() == LexerToken.DOUBLE) {
			el = new ElementConstantDouble((double)t.getValue());
		}
		
		return el;
	}
	
	
	
	/**
	 * Retrieves the tokens from the for-tag and parses them
	 * @return returns a {@link ForLoopNode} which has all the parsed tokens
	 * @throws throws {@link SmartScriptParserException} if more than 4 elements are to be added to the loop node
	 */
	public ForLoopNode forTagParser() {
		Token t = lexer.getNextToken();
		int counter = 0;
		ForLoopNode node = new ForLoopNode();
		
		for(; t.getValue() != "$}"; t = lexer.getNextToken(), counter++){
			
			switch(counter) {
			
			case 0:
				if(t.getType() != LexerToken.VARIABLE) {
					throw new SmartScriptParserException();
				}
				node.setVariable(new ElementVariable((String)t.getValue()));
				break;
			
			case 1:
				node.setStartExpression(createNewElement(t));
				break;
			
			case 2:
				node.setStepExpression(createNewElement(t));
				break;
				
			case 3:
				node.setEndExpression(createNewElement(t));
				break;
				
			default:
				throw new SmartScriptParserException();
			}
		}
		
		return node;
	}
	
	/**
	 * Retrieves the tokens from the for-tag and parses them
	 * @return returns a {@link EchoNode} which has all the parsed tokens
	 */
	public EchoNode emptyTagParser() {
		
		Token t = lexer.getNextToken();
		
		List<Element> col = new ArrayList<>();
		for(; t.getValue() != "$}"; t = lexer.getNextToken()){
			col.add(createNewElement(t));
		}
		
		Element[] elems = new Element[col.size()];
		for(int i = 0; i < elems.length; i++) {
			elems[i] = (Element)col.get(i);
		}
		
		return new EchoNode(elems);
	}
	
	
	/**
	 * Returns the parsed document text from the base {@link Node}
	 * @return the parsed document text from the base {@link Node}
	 */
	public DocumentNode getDocumentNode() {
		return (DocumentNode) this.doc;
	}

}
