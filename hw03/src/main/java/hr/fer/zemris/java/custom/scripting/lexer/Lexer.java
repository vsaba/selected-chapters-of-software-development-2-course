package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * A simple implementation of a lexer which creates tokens from the provided
 * data input
 * 
 * @author Vito Sabalic
 *
 */
public class Lexer {

	/**
	 * the data to be tokenized
	 */
	private char[] data;

	/**
	 * the token to be returned
	 */
	private Token token;

	/**
	 * the current state of the lexer
	 */
	private LexerState state;

	/**
	 * the current index at which the data set is located
	 */
	private int currentIndex;

	/**
	 * represents if the lexer is currently in a TAGSTATE state
	 */
	private boolean isTagState = false;

	/**
	 * A constructor which receives the data set which is to be tokenized and
	 * assigns it to the lexer
	 * 
	 * @param documentBody the value to be tokenized
	 */
	public Lexer(String documentBody) {

		// documentBody = documentBody.replaceAll(" ", "");
		this.data = documentBody.trim().toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.NORMALSTATE;
	}

	/**
	 * Removes blank spaces, \n, \r and \t from the data set
	 */
	public void removeBlanks() {
		for (int i = currentIndex; i < data.length; i++) {
			if (checkIfSpace(i)) {
				currentIndex++;
				continue;
			}

			break;
		}
	}

	/**
	 * Checks whether the char at the current index is a blank space, \n, \r or \t
	 * 
	 * @param index the index at which the char is set
	 * @return true if it is the aforementioned char, otherwise returns false
	 */
	public boolean checkIfSpace(int index) {
		if(data[index] == '\r' || data[index] == '\n' || data[index] == '\t' || data[index] == ' ') {
			return true;
		}

		return false;
	}

	/**
	 * sets the working state of this lexer based on the values from the
	 * {@link LexerState} enumeration
	 */
	private void setState() {
		if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
			state = LexerState.TAGSTATE;
			this.isTagState = true;
			return;
		}

		state = LexerState.NORMALSTATE;
	}

	/**
	 * Generates the next token based on the current data set at which the lexer is
	 * positioned.
	 * 
	 * @return returns the next assigned token
	 */
	public Token getNextToken() {

		if (token != null && token.getType() == LexerToken.EOF) {
			throw new LexerException("No tokens available.");
		}

		if (currentIndex >= data.length) {
			token = new Token(LexerToken.EOF, null);
			return token;
		}

		if (!isTagState) {
			setState();
		}

		if (state == LexerState.TAGSTATE) {
			tagStateToken();
		} else {
			normalStateToken();
		}

		return getCurrentToken();
	}

	/**
	 * finds a token while the lexer is in a TAGSTATE state
	 * 
	 * @throw throws a {@link LexerException} if an error occurred
	 */
	public void tagStateToken() {

		if (token != null && token.getType() == LexerToken.EOF) {
			throw new LexerException("No tokens available.");
		}

		removeBlanks();

		if (currentIndex >= data.length) {
			token = new Token(LexerToken.EOF, null);
			return;
		}

		if (data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
			currentIndex += 2;
			isTagState = false;
			token = new Token(LexerToken.SYMBOL, "$}");
			return;
		}

		else if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
			currentIndex += 2;
			token = new Token(LexerToken.SYMBOL, "{$");
			return;
		}

		else if (data[currentIndex] == '=') {
			token = new Token(LexerToken.EMPTYTAG, "=");
			currentIndex++;
			removeBlanks();
		} else if (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])) {
			extractNumber(true);
		} else if (data[currentIndex] == '+' || data[currentIndex] == '-' || data[currentIndex] == '*'
				|| data[currentIndex] == '/' || data[currentIndex] == '^') {
			token = new Token(LexerToken.OPERATOR, String.valueOf(data[currentIndex]));
			currentIndex++;
		} else if (data[currentIndex] == '"') {
			extractString();
		} else if (Character.isLetter(data[currentIndex])) {
			extractVariable();
		} else if (Character.isDigit(data[currentIndex])) {
			extractNumber(false);
		} else if (data[currentIndex] == '@') {
			extractFunction();
		} else if (data[currentIndex] == '\\') {
			throw new LexerException();
		} else {
			token = new Token(LexerToken.SYMBOL, String.valueOf(data[currentIndex]));
			currentIndex++;
		}

		return;
	}

	/**
	 * Extracts a number from the data set
	 * 
	 * @param isNegative true if the number is negative, false otherwise
	 * @throws throws a {@link LexerException} if an error occurred
	 */
	private void extractNumber(boolean isNegative) {
		boolean isDouble = false;

		if (currentIndex >= data.length) {
			throw new LexerException();
		}

		if (isNegative) {
			currentIndex++;
		}

		int startIndex = currentIndex;

		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			currentIndex++;
		}

		if (currentIndex < data.length && data[currentIndex] == '.') {
			currentIndex++;
			isDouble = true;

			while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				currentIndex++;
			}
		}

		int endIndex = currentIndex;

		String s = new String(data, startIndex, endIndex - startIndex);

		if (endIndex == startIndex || (endIndex == startIndex + 1 && data[startIndex] == '.')) {
			throw new LexerException("Invalid decimal number");
		}

		if (isDouble) {
			double d = Double.parseDouble(s);
			if (isNegative) {
				d = -1 * d;
			}
			token = new Token(LexerToken.DOUBLE, d);
		} else {
			int i = Integer.parseInt(s);
			if (isNegative) {
				i = -1 * i;
			}
			token = new Token(LexerToken.INT, i);
		}

		removeBlanks();

		return;
	}

	/**
	 * Extracts a variable from the data set
	 * 
	 * @throws throws a {@link LexerException} if an error occurred
	 */
	private void extractVariable() {

		if (!(Character.isLetter(data[currentIndex]))) {
			throw new LexerException();
		}

		int startIndex = currentIndex;
		currentIndex++;

		while (currentIndex < data.length
				&& (Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_')) {
			currentIndex++;
		}

		int endIndex = currentIndex;

		String s = new String(data, startIndex, endIndex - startIndex);
		removeBlanks();

		if (s.toUpperCase().equals("FOR")) {
			token = new Token(LexerToken.FORTAG, s);
			removeBlanks();
			return;
		} else if (s.toUpperCase().equals("END")) {
			token = new Token(LexerToken.ENDTAG, s);
			removeBlanks();
			return;
		}

		token = new Token(LexerToken.VARIABLE, s);

		removeBlanks();
		return;
	}

	/**
	 * Extracts a function from the data set
	 * 
	 * @throws throws a {@link LexerException} if an error occurred
	 */
	private void extractFunction() {
		if (data[currentIndex] != '@') {
			throw new LexerException();
		}

		currentIndex++;

		extractVariable();
		Token tmp = token;

		token = new Token(LexerToken.FUNCTION, tmp.getValue());

		removeBlanks();
		return;
	}

	/**
	 * Extracts a string from the data set
	 * 
	 * @throws throws a {@link LexerException} if an error occurred
	 */
	private void extractString() {
		if (data[currentIndex] != '"') {
			throw new LexerException();
		}

		String s = new String();
		currentIndex++;

		while (data[currentIndex] != '"' && currentIndex < data.length) {
			if (data[currentIndex] == '\\') {
				if (data[currentIndex + 1] == '\\') {
					s = s.concat(String.valueOf(data[currentIndex]));
					currentIndex += 2;
					continue;
				} else if (data[currentIndex + 1] == '"') {
					s = s.concat(String.valueOf(data[currentIndex + 1]));
					currentIndex += 2;
					continue;
				} else if (data[currentIndex + 1] == 'n') {
					s = s.concat(String.valueOf('\n'));
					currentIndex += 2;
					continue;
				} else if (data[currentIndex + 1] == 'r') {
					s = s.concat(String.valueOf('\r'));
					currentIndex += 2;
					continue;
				} else if (data[currentIndex + 1] == 't') {
					s = s.concat(String.valueOf('\t'));
					currentIndex += 2;
					continue;
				} else {
					throw new LexerException();
				}
			}

			s = s.concat(String.valueOf(data[currentIndex]));
			currentIndex++;
		}

		currentIndex++;
		removeBlanks();

		token = new Token(LexerToken.STRING, s);
	}

	/**
	 * Extracts a word from the data set
	 * 
	 * @throws throws a {@link LexerException} if an error occurred
	 */
	private void extractWord() {
		String s = new String();

		while (currentIndex < data.length) {
			if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
				break;
			}
			if (data[currentIndex] == '\\') {
				if (data[currentIndex + 1] == '\\') {
					s = s.concat(String.valueOf(data[currentIndex]));
					currentIndex += 2;
					continue;
				} else if (data[currentIndex + 1] == '{') {
					s = s.concat(String.valueOf(data[currentIndex + 1]));
					currentIndex += 2;
					continue;
				} else {
					throw new LexerException();
				}
			}

			s = s.concat(String.valueOf(data[currentIndex]));
			currentIndex++;
		}

		token = new Token(LexerToken.WORD, s);

	}

	/**
	 * finds a token while the lexer is in a NORMALSTATE state
	 * 
	 * @throw throws a {@link LexerException} if an error occurred
	 */
	public void normalStateToken() {
		if (token != null && token.getType() == LexerToken.EOF) {
			throw new LexerException("No tokens available.");
		}

		if (currentIndex >= data.length) {
			token = new Token(LexerToken.EOF, null);
			return;
		}

		extractWord();
		return;
	}

	/**
	 * Returns the currently assigned token to the lexer
	 * 
	 * @return returns the currently assigned token to the lexer
	 */
	public Token getCurrentToken() {
		return token;
	}

}
