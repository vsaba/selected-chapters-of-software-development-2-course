package oprpp2.hw02.client;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A GUI for the chat application modeled by the class {@link Main}
 * 
 * @author Vito Sabalic
 *
 */
public class ChatGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField textField;
	private JTextArea textArea;

	/**
	 * A constructor for the GUI
	 * 
	 * @param name
	 */
	public ChatGui(String name) {
		super(name);
		setSize(500, 500);
		setLocationRelativeTo(null);

		initGUI();
	}

	/**
	 * Initializes all the necessary components for the GUI
	 */
	private void initGUI() {
		textField = new JTextField();
		textArea = new JTextArea();
		textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		textArea.setEditable(false);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(textField, BorderLayout.PAGE_START);
		this.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);

	}

	/**
	 * Adds a key listener to the {@link JTextField} component
	 * 
	 * @param l The listener
	 */
	public void addListener(KeyListener l) {
		textField.addKeyListener(l);
	}

	/**
	 * Removes the listener from the {@link JTextField} component
	 * 
	 * @param l
	 */
	public void removeListener(KeyListener l) {
		textField.removeKeyListener(l);
	}

	/**
	 * Adds text to the {@link JTextArea} component
	 * 
	 * @param text
	 */
	public void addText(String text) {
		textArea.append(text);
	}

}
