package oprpp2.hw02.messages;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * A message which a server sends to its clients
 * 
 * @author Vito Sabalic
 *
 */
public class InMessage implements Message {

	private long number;
	private String name;
	private String text;

	/**
	 * The constructor for this message
	 * 
	 * @param number
	 * @param name
	 * @param text
	 */
	public InMessage(long number, String name, String text) {

		this.number = number;
		this.name = name;
		this.text = text;
	}

	@Override
	public long getNumber() {
		return number;
	}

	/**
	 * A getter for the name of the owner of the text in this message
	 * 
	 * @return The name of the owner of the text in this message
	 */
	public String getName() {
		return name;
	}

	/**
	 * A getter for the text of this message
	 * 
	 * @return The text from this message
	 */
	public String getText() {
		return text;
	}

	@Override
	public DatagramPacket getDatagramFromMessage() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(Message.INMSG);
			dos.writeLong(this.number);
			dos.writeUTF(name);
			dos.writeUTF(text);
			dos.close();
		} catch (IOException e) {
			System.out.println("Error");
			return null;
		}
		byte[] buf = bos.toByteArray();
		return new DatagramPacket(buf, buf.length);
	}

}
