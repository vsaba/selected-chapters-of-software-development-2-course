package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * A class which is an adapter class for a a stack like abstraction for each key
 * it receives
 * 
 * @author Vito Sabalic
 *
 */
public class ObjectMultistack {

	private Map<String, MultistackEntry> stackMap;

	/**
	 * A simple constructor, initializes all variables
	 */
	public ObjectMultistack() {
		this.stackMap = new HashMap<>();
	}

	/**
	 * Pushes to the "stack" the provided {@link ValueWrapper} which is mapped to
	 * the keyName parameter
	 * 
	 * @param keyName      The key
	 * @param valueWrapper The value to be pushed
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {

		MultistackEntry newEntry = new MultistackEntry(valueWrapper);
		if (stackMap.containsKey(keyName)) {
			newEntry.next = stackMap.get(keyName);
		}

		stackMap.put(keyName, newEntry);

		return;

	}

	/**
	 * Removes the value from the "stack" assigned to the provided key
	 * 
	 * @param keyName The provided key
	 * @return Returns the removed value
	 */
	public ValueWrapper pop(String keyName) {

		if (!stackMap.containsKey(keyName)) {
			throw new IllegalArgumentException("There is no value associated with the provided key");
		}

		MultistackEntry oldEntry = stackMap.get(keyName);

		stackMap.put(keyName, oldEntry.next);

		return oldEntry.value;
	}

	/**
	 * Returns, but doesn't remove the head value from the head of the "stack"
	 * assigned to the provided key
	 * 
	 * @param keyName The provided key
	 * @return Returns the value on top of the stack
	 */
	public ValueWrapper peek(String keyName) {
		if (!stackMap.containsKey(keyName)) {
			throw new IllegalArgumentException("There is no value associated with the provided key");
		}

		return stackMap.get(keyName).value;
	}

	/**
	 * Checks whether the stack assigned to the provided key is empty
	 * 
	 * @param keyName The provided key
	 * @return True if it is empty, false otherwise
	 */
	public boolean isEmpty(String keyName) {

		if (!stackMap.containsKey(keyName)) {
			throw new IllegalArgumentException("There is no value associated with the provided key");
		}

		return stackMap.get(keyName) == null;
	}

	/**
	 * A class which represents an entry in a "stack"
	 * 
	 * @author Vito Sabalic
	 *
	 */
	private static class MultistackEntry {
		private ValueWrapper value;
		private MultistackEntry next;

		/**
		 * A simple constructor, initalizes all values
		 * 
		 * @param value
		 */
		public MultistackEntry(ValueWrapper value) {
			this.value = value;
			this.next = null;
		}
	}

}
