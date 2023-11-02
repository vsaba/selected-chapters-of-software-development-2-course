package oprpp2.hw02.messages;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * A message which a client sends to the server when it wishes to connect
 * 
 * @author Vito Sabalic
 *
 */
public class HelloMessage implements Message {

	private long number;
	private String name;
	private long UID;

	/**
	 * The constructor for this class
	 * 
	 * @param number
	 * @param name
	 * @param UID
	 */
	public HelloMessage(long number, String name, long UID) {
		this.number = number;
		this.name = name;
		this.UID = UID;
	}

	@Override
	public long getNumber() {
		return number;
	}

	/**
	 * A getter for the name of the sender of this message
	 * 
	 * @return The name of the sender
	 */
	public String getName() {
		return name;
	}

	/**
	 * A getter for the unique code of the sender of this message
	 * 
	 * @return The code of the sender of this message
	 */
	public long getUID() {
		return UID;
	}

	@Override
	public DatagramPacket getDatagramFromMessage() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(Message.HELLO);
			dos.writeLong(this.number);
			dos.writeUTF(this.name);
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
