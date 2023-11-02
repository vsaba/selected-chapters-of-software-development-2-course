package hr.fer.zemris.java.custom.scripting.exec;

/**
 * A wrapper class used for storing any value
 * 
 * @author Vito Sabalic
 *
 */
public class ValueWrapper {

	private Object value;

	/**
	 * A simple constructor, initializes all values
	 * 
	 * @param value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * A getter for the stored value
	 * 
	 * @return The stored value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value to the provided value
	 * 
	 * @param value The provided value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Adds to the current value the provided value. Checks whether the provided
	 * value and the current value can really be added
	 * 
	 * @param inValue The provided value
	 */
	public void add(Object inValue) {
		checkValidity(inValue);
		inValue = assignValues(inValue);

		if (value instanceof Double || inValue instanceof Double) {
			value = castToDouble(value);
			inValue = castToDouble(inValue);
			value = (double) value + (double) inValue;
		} else if (value instanceof Integer && inValue instanceof Integer) {
			value = (Integer) value + (Integer) inValue;
		} else {
			throw new IllegalArgumentException("An unknown argument has been given");
		}
	}

	/**
	 * Subtracts from the current value the provided value. Checks whether the
	 * provided value can be subtracted from the current value
	 * 
	 * @param inValue The provided value
	 */
	public void subtract(Object decValue) {
		checkValidity(decValue);
		decValue = assignValues(decValue);

		if (value instanceof Double || decValue instanceof Double) {
			value = castToDouble(value);
			decValue = castToDouble(decValue);
			value = (double) value - (double) decValue;
		} else if (value instanceof Integer && decValue instanceof Integer) {
			value = (Integer) value - (Integer) decValue;
		} else {
			throw new IllegalArgumentException("An unknown argument has been given");
		}
	}

	/**
	 * Multiplies the current value and the provided value. Checks whether the
	 * provided value and the current value can really be multiplied
	 * 
	 * @param inValue The provided value
	 */
	public void multiply(Object mulValue) {
		checkValidity(mulValue);
		mulValue = assignValues(mulValue);

		if (value instanceof Double || mulValue instanceof Double) {
			value = castToDouble(value);
			mulValue = castToDouble(mulValue);
			value = (double) value * (double) mulValue;
		} else if (value instanceof Integer && mulValue instanceof Integer) {
			value = (Integer) value * (Integer) mulValue;
		} else {
			throw new IllegalArgumentException("An unknown argument has been given");
		}
	}

	/**
	 * Divides the current value and the provided value. Checks whether the provided
	 * value and the current value can really be divided
	 * 
	 * @param inValue The provided value
	 */
	public void divide(Object divValue) {
		checkValidity(divValue);
		divValue = assignValues(divValue);
		if (value instanceof Double || divValue instanceof Double) {
			value = castToDouble(value);
			divValue = castToDouble(divValue);
			value = (double) value / (double) divValue;
		} else if (value instanceof Integer && divValue instanceof Integer) {
			value = (Integer) value / (Integer) divValue;
		} else {
			throw new IllegalArgumentException("An unknown argument has been given");
		}
	}

	/**
	 * Compares the current value to the provided value. Checks whether the provided
	 * value and the current value can be compared.
	 * 
	 * @param withValue The provided value
	 * @return The comparison, 0 if equal, <0 if current value is smaller, >0 if
	 *         current value is larger
	 */
	public int numCompare(Object withValue) {
		checkValidity(withValue);
		withValue = assignValues(withValue);
		if (value instanceof Double || withValue instanceof Double) {
			value = castToDouble(value);
			withValue = castToDouble(withValue);
			return Double.compare((Double) value, (Double) withValue);
		} else if (value instanceof Integer && withValue instanceof Integer) {
			return Integer.compare((Integer) value, (Integer) withValue);
		} else {
			throw new IllegalArgumentException("An unknown argument has been given");
		}
	}

	/**
	 * Checks whether the provided argument is instance of double, if true casts to
	 * double. Otherwise, casts the value to integer.
	 * 
	 * @param doubleValue The value to be cast
	 * @return Returns the cast value
	 */
	private Object castToDouble(Object doubleValue) {
		if (doubleValue instanceof Double) {
			return doubleValue;
		}

		return Integer.valueOf((int) doubleValue).doubleValue();
	}

	/**
	 * Parses the provided string. If provided string has double properties (. or
	 * E), returns a double instance. Otherwise, returns an integer instance.
	 * 
	 * @param s
	 * @return
	 */
	private Object parseString(String s) {

		if (s.contains(".") || s.contains("E")) {
			return Double.parseDouble(s);
		}

		return Integer.parseInt(s);
	}

	/**
	 * Assigns a value to the provided inValue
	 * 
	 * @param inValue The provided value
	 * @return Returns a instance of either integer or double
	 */
	private Object assignValues(Object inValue) {
		if (value == null) {
			value = Integer.valueOf(0);
		}

		if (inValue == null) {
			return Integer.valueOf(0);
		}

		if (value instanceof String) {
			value = parseString((String) value);
		}
		if (inValue instanceof String) {
			return parseString((String) inValue);
		}

		return inValue;
	}

	/**
	 * Checks whether the provided value and the current value can truly be
	 * compared/added/subtracted from/multiplied. This means that they must either
	 * be an instance of Integer, Double, String or null. If they are String or
	 * null, must be assigned to either integer or double
	 * 
	 * @param inValue
	 */
	private void checkValidity(Object inValue) {

		if (inValue instanceof Integer || value instanceof Integer || inValue instanceof String
				|| value instanceof String || inValue instanceof Double || value instanceof Double || inValue == null
				|| value == null) {
			return;
		}

		throw new IllegalArgumentException("If an arithmetic method is called, the provided argument,"
				+ " or the currently stored value must be an instance of either String," + " Integer, Double or null");
	}

}
