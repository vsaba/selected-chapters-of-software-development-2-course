package oprpp2.hw02.messages;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * A helper class for message interpretation
 * 
 * @author Vito Sabalic
 *
 */
public class MessageUtil {

	/**
	 * Creates the appropriate message from the provided {@link DatagramPacket}
	 * 
	 * @param packet the provided packet
	 * @return Returns the newly created message
	 */
	public static Message getMessageFromDatagram(DatagramPacket packet) {

		ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
		DataInputStream dis = new DataInputStream(bis);

		Message msg = null;

		try {
			byte code = dis.readByte();
			if (code == Message.HELLO) {

				msg = new HelloMessage(dis.readLong(), dis.readUTF(), dis.readLong());

			} else if (code == Message.ACK) {
				msg = new AckMessage(dis.readLong(), dis.readLong());

			} else if (code == Message.BYE) {

				msg = new ByeMessage(dis.readLong(), dis.readLong());

			} else if (code == Message.OUTMSG) {

				msg = new OutMessage(dis.readLong(), dis.readLong(), dis.readUTF());

			} else if (code == Message.INMSG) {

				msg = new InMessage(dis.readLong(), dis.readUTF(), dis.readUTF());

			} else {
				System.out.println("Garbage message. Ignoring...");
			}

			dis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return msg;
	}

}
