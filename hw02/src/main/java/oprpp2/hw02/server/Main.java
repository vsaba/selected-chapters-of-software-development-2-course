package oprpp2.hw02.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import oprpp2.hw02.messages.AckMessage;
import oprpp2.hw02.messages.ByeMessage;
import oprpp2.hw02.messages.HelloMessage;
import oprpp2.hw02.messages.InMessage;
import oprpp2.hw02.messages.Message;
import oprpp2.hw02.messages.MessageUtil;
import oprpp2.hw02.messages.OutMessage;

/**
 * An implementation of a server used in a chat application
 * 
 * @author Vito Sabalic
 *
 */
public class Main {

	public static DatagramSocket socket = null;
	private static List<User> users;
	private static long UIDs;
	private static long lastOutMessageNumber;
	private static long lastOutMessageUID;

	/**
	 * The main method of this class, initializes all the necessary variables and
	 * threads. Waits for incoming messages and processes them
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Exactly 1 aragument is expected");
			System.exit(0);
		}

		users = new ArrayList<>();
		UIDs = new Random().nextLong();
		lastOutMessageNumber = 0;
		lastOutMessageUID = 0;

		try {
			socket = new DatagramSocket(Integer.parseInt(args[0]));
		} catch (SocketException e) {
			e.printStackTrace();
		}

		while (true) {
			byte[] buf = new byte[4000];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);

			try {
				socket.setSoTimeout(0);
			} catch (SocketException e) {
				e.printStackTrace();
			}

			try {
				socket.receive(packet);
			} catch (SocketTimeoutException exc) {
				continue;
			} catch (IOException exc) {
				exc.printStackTrace();
			}

			if (packet.getData()[0] == Message.HELLO) {

				HelloMessage msg = (HelloMessage) MessageUtil.getMessageFromDatagram(packet);

				DatagramPacket ackPacket = null;
				User user = null;

				if (!checkIfUserExists(msg)) {

					user = new User(msg.getName(), UIDs, msg.getUID(), packet.getPort(), packet.getAddress(), 1);
					users.add(user);
					ackPacket = new AckMessage(msg.getNumber(), UIDs++).getDatagramFromMessage();
					new Thread(user).start();

				} else {
					for (User pomUser : users) {
						if (Long.compare(pomUser.getUniqueKey(), msg.getUID()) == 0) {
							user = pomUser;
							break;
						}
					}

					ackPacket = new AckMessage(msg.getNumber(), user.getUID()).getDatagramFromMessage();
				}

				ackPacket.setSocketAddress(new InetSocketAddress(packet.getAddress(), packet.getPort()));

				try {
					socket.send(ackPacket);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else if (packet.getData()[0] == Message.ACK) {

				AckMessage msg = (AckMessage) MessageUtil.getMessageFromDatagram(packet);

				User user = Main.getUserFromUID(msg.getUID());

				users.get(users.indexOf(user)).addToReceivedQueue(msg);

			} else if (packet.getData()[0] == Message.BYE) {

				ByeMessage msg = (ByeMessage) MessageUtil.getMessageFromDatagram(packet);

				User userToBeDeleted = Main.getUserFromUID(msg.getUID());

				System.out.println("Deleting: " + userToBeDeleted.getName());

				userToBeDeleted.close();

				users.remove(userToBeDeleted);

				DatagramPacket ackPacket = new AckMessage(msg.getNumber(), msg.getUID()).getDatagramFromMessage();

				ackPacket.setSocketAddress(new InetSocketAddress(packet.getAddress(), packet.getPort()));

				try {
					socket.send(ackPacket);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else if (packet.getData()[0] == Message.OUTMSG) {

				OutMessage msg = (OutMessage) MessageUtil.getMessageFromDatagram(packet);

				DatagramPacket ackPacket = new AckMessage(msg.getNumber(), UIDs).getDatagramFromMessage();
				ackPacket.setSocketAddress(new InetSocketAddress(packet.getAddress(), packet.getPort()));

				try {
					socket.send(ackPacket);
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (Long.compare(msg.getNumber(), lastOutMessageNumber) == 0
						&& Long.compare(msg.getUID(), lastOutMessageUID) == 0) {
					continue;
				}

				User sendingUser = Main.getUserFromUID(msg.getUID());

				for (User user : users) {
					user.addToToBeSentQueue(
							new InMessage(user.getMessageNumber(), sendingUser.getName(), msg.getMessageText()));
				}

				lastOutMessageNumber = msg.getNumber();
				lastOutMessageUID = msg.getUID();

			} else {
				System.out.println("Garbage. Ignoring...");
			}
		}

	}

	/**
	 * A helper method which checks whether a user already exists in the database
	 * based on the provided message
	 * 
	 * @param message The provided message
	 * @return True if the user exists, false otherwise
	 */
	private static boolean checkIfUserExists(HelloMessage message) {

		boolean exists = false;

		for (User user : users) {
			if (user.getUniqueKey() == message.getUID()) {
				exists = true;
				break;
			}
		}

		return exists;
	}

	/**
	 * Returns the {@link User} based on the UID of the user
	 * 
	 * @param UID The provided UID which is checked in the database
	 * @return Returns the {@link User} if it exists, otherwise <code>null</code>
	 */
	public static User getUserFromUID(long UID) {

		User user = null;

		for (User pomUser : users) {
			if (Long.compare(UID, pomUser.getUID()) == 0) {
				user = pomUser;
				break;
			}
		}

		return user;
	}

}
