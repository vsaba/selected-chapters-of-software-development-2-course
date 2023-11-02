package oprpp2.hw02.server;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import oprpp2.hw02.messages.InMessage;
import oprpp2.hw02.messages.Message;

/**
 * A representation of a user in the chat application
 * 
 * @author Vito Sabalic
 *
 */
public class User implements Runnable {

	private String name;
	private long UID;
	private BlockingQueue<Message> receivedMessages;
	private BlockingQueue<Message> toBeSentMessages;
	private long uniqueKey;
	private int port;
	private InetAddress ipAddress;
	private long messageNumber;
	private boolean close;

	/**
	 * The constructor of this class which assigns and initializes all necessary
	 * variables
	 * 
	 * @param name
	 * @param UID
	 * @param uniqueKey
	 * @param port
	 * @param ipAddress
	 * @param messageNumber
	 */
	public User(String name, long UID, long uniqueKey, int port, InetAddress ipAddress, long messageNumber) {
		this.name = name;
		this.UID = UID;
		this.uniqueKey = uniqueKey;
		this.port = port;
		this.ipAddress = ipAddress;
		this.receivedMessages = new LinkedBlockingQueue<>();
		this.toBeSentMessages = new LinkedBlockingQueue<>();
		this.messageNumber = messageNumber;
		this.close = false;
	}

	/**
	 * Adds the provided message to the queue of the received messages
	 * 
	 * @param message The provided message
	 */
	public void addToReceivedQueue(Message message) {
		receivedMessages.add(message);
	}

	/**
	 * Adds the provided message to the queue of the messages which need to be sent
	 * 
	 * @param message The provided message
	 */
	public void addToToBeSentQueue(Message message) {
		toBeSentMessages.add(message);
	}

	/**
	 * A getter for the name of the user
	 * 
	 * @return The name of the user
	 */
	public String getName() {
		return name;
	}

	/**
	 * A getter for the UID of the user
	 * 
	 * @return The UID of the user
	 */
	public long getUID() {
		return UID;
	}

	/**
	 * A getter for the unique key of the user
	 * 
	 * @return The unique key of the user
	 */
	public long getUniqueKey() {
		return uniqueKey;
	}

	/**
	 * A getter for the port from which the user is communicating
	 * 
	 * @return The assigned port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * A getter for the Ip address from which the user is communicating
	 * 
	 * @return The assigned Ip address
	 */
	public InetAddress getIpAddress() {
		return ipAddress;
	}

	/**
	 * A getter for the current number of sent messages
	 * 
	 * @return The current number of sent messages
	 */
	public long getMessageNumber() {
		return messageNumber;
	}

	/**
	 * Interrupts the job and closes the thread
	 */
	public void close() {
		this.close = true;
	}

	@Override
	public void run() {
		while (true) {

			if (close) {
				break;
			}

			InMessage newMessage = null;
			try {
				newMessage = (InMessage) toBeSentMessages.take();
			} catch (InterruptedException e) {
				continue;
			}

			for (int counter = 0; counter < 10; counter++) {

				DatagramPacket packet = newMessage.getDatagramFromMessage();

				packet.setSocketAddress(new InetSocketAddress(ipAddress, port));

				try {
					Main.socket.send(packet);
				} catch (IOException exc) {
					exc.printStackTrace();
				}

				Message receivedMessage = null;

				try {
					receivedMessage = receivedMessages.poll(5L, TimeUnit.SECONDS);
				} catch (InterruptedException exc) {
					exc.printStackTrace();
				}

				if (receivedMessage == null) {
					continue;
				}

				if (messageNumber == receivedMessage.getNumber()) {
					messageNumber++;
					break;
				}
			}

		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (uniqueKey ^ (uniqueKey >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (uniqueKey != other.uniqueKey)
			return false;
		return true;
	}

}
