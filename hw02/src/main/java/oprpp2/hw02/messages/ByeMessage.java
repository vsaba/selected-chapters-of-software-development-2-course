package oprpp2.hw02.messages;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * A message which indicates that a user wants to disconnect from the server
 * 
 * @author Vito Sabalic
 *
 */
public class ByeMessage implements Message {

	private long number;
	private long UID;

	/**
	 * The constructor for this message
	 * 
	 * @param number
	 * @param UID
	 */
	public ByeMessage(long number, long UID) {
		this.number = number;
		this.UID = UID;
	}

	@Override
	public long getNumber() {
		return number;
	}

	/**
	 * A getter for the UID of the sender of this message
	 * 
	 * @return The UID of the sender of this message
	 */
	public long getUID() {
		return UID;
	}

	@Override
	public DatagramPacket getDatagramFromMessage() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(Message.BYE);
			dos.writeLong(this.number);
			dos.writeLong(this.UID);
			dos.close();
		} catch (IOException e) {
			System.out.println("Error");
			return null;
		}
		byte[] buf = bos.toByteArray();
		return new DatagramPacket(buf, buf.length);
	}

}
