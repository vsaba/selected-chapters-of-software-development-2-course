package oprpp2.hw02.messages;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * A message which is sent as an acknowledgment of receiving a message
 * 
 * @author Vito Sabalic
 *
 */
public class AckMessage implements Message {

	private long number;
	private long UID;

	/**
	 * The constructor for this class
	 * 
	 * @param number
	 * @param UID
	 */
	public AckMessage(long number, long UID) {

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
			dos.writeByte(Message.ACK);
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
