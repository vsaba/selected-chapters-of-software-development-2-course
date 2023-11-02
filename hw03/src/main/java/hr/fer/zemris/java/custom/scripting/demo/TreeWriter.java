package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * A demo class for the {@link INodeVisitor} interface
 * @author Vito Sabalic
 *
 */
public class TreeWriter {
	
	private static class WriterVisitor implements INodeVisitor{

		@Override
		public void visitTextNode(TextNode node) {
			System.out.printf(node.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String s = "{$FOR ";
			//System.out.printf("{$FOR ");
			if (node.getStepExpression() != null) {
				s = s + node.getVariable().asText() + node.getStartExpression().asText() + node.getStepExpression().asText()
						+ node.getEndExpression().asText() + "$}";
			} else {
				s = s + node.getVariable().asText() + node.getStartExpression().asText() + node.getEndExpression().asText() + "$}";
			}
			
			System.out.printf(s);
			if (node.numberOfChildren() != 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
					//s = s.concat(node.getChild(i).toString());
				}
			}

			//s += "{$END$}";
			
			System.out.printf("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.printf(node.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			int numberOfChildren = node.numberOfChildren();
			for(int i = 0; i < numberOfChildren; i++) {
				node.getChild(i).accept(this);
			}
			
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		
		if(args.length != 1) {
			System.out.println("Exactly one argument is required");
		}
		
		String docBody = Files.readString(Paths.get(args[0]));
		
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}

}
