package oprpp2.hw02.messages;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * An implementation of a message containing text which a client sends to the
 * server
 * 
 * @author Vito Sabalic
 *
 */
public class OutMessage implements Message {

	private long number;
	private long UID;
	private String messageText;

	/**
	 * The constructor for this class
	 * 
	 * @param number
	 * @param UID
	 * @param messageText
	 */
	public OutMessage(long number, long UID, String messageText) {

		this.number = number;
		this.UID = UID;
		this.messageText = messageText;

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

	/**
	 * A getter for the text from this message
	 * 
	 * @return The text from this message
	 */
	public String getMessageText() {
		return messageText;
	}

	@Override
	public DatagramPacket getDatagramFromMessage() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(Message.OUTMSG);
			dos.writeLong(this.number);
			dos.writeLong(this.UID);
			dos.writeUTF(messageText);
			dos.close();
		} catch (IOException e) {
			System.out.println("Error");
			return null;
		}
		byte[] buf = bos.toByteArray();
		return new DatagramPacket(buf, buf.length);
	}

}
