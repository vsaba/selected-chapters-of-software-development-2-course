package oprpp2.hw02.messages;

import java.net.DatagramPacket;

/**
 * A representation of a message that can be exchanged between a client and the
 * server
 * 
 * @author Vito Sabalic
 *
 */
public interface Message {

	public static byte HELLO = 1;

	public static byte ACK = 2;

	public static byte BYE = 3;

	public static byte OUTMSG = 4;

	public static byte INMSG = 5;

	/**
	 * Creates a {@link DatagramPacket} from the message
	 * 
	 * @return returns the created {@link DatagramPacket}
	 */
	DatagramPacket getDatagramFromMessage();

	/**
	 * Gets the number of the message
	 * 
	 * @return the number of the message
	 */
	long getNumber();
}
