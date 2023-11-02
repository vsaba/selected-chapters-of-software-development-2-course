package hr.fer.zemris.java.tecaj_13.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class which either creates a sha-1 hash
 * @author Vito Sabalic
 *
 */
public class Crypto {
	
	/**
	 * Hashes the provided password
	 * @param parameter The provided password
	 * @return Returns the password SHA1 hash
	 */
	public static String hashPassword(String parameter) {

		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		return byteToHex(digest.digest(parameter.getBytes()));
	}

	/**
	 * Transforms the provided byte array to a hex string
	 * @param bytearray The provided byte array
	 * @return returns the calculated hex string
	 */
	private static String byteToHex(byte[] bytearray) {

		char[] hexDigits = "0123456789abcdef".toCharArray();

		char[] hex = new char[bytearray.length * 2];
		int index = 0;

		for (byte b : bytearray) {
			hex[index++] = hexDigits[(b >> 4) & 0xF];
			hex[index++] = hexDigits[b & 0xF];
		}

		return String.valueOf(hex);

	}

}
