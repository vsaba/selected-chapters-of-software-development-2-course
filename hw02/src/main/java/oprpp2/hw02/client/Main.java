package oprpp2.hw02.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import oprpp2.hw02.messages.AckMessage;
import oprpp2.hw02.messages.ByeMessage;
import oprpp2.hw02.messages.HelloMessage;
import oprpp2.hw02.messages.InMessage;
import oprpp2.hw02.messages.Message;
import oprpp2.hw02.messages.MessageUtil;
import oprpp2.hw02.messages.OutMessage;

/**
 * A class which models a client in a chat application
 * 
 * @author Vito Sabalic
 *
 */
public class Main {

	private static InetAddress adress;
	private static int port;
	private static DatagramSocket socket;
	private static long UID;
	private static long messageNumber = 0;
	private static ChatGui gui;
	private static BlockingQueue<Message> recievedMessages = new LinkedBlockingQueue<>();
	private static long lastInMessageNumber;

	/**
	 * The main function of this class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 3) {
			System.out.println("Exactly 3 arguments are required");
			System.exit(0);
		}

		try {
			adress = InetAddress.getByName(args[0]);
		} catch (UnknownHostException exc) {
			System.out.println("The host cannot be resolved!");
			System.exit(0);
		}

		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			System.out.println("Unable to open a socket!");
			return;
		}

		gui = new ChatGui(args[2]);
		port = Integer.parseInt(args[1]);
		gui.addWindowListener(windowAdapter);
		gui.addListener(keyAdapter);

		SwingUtilities.invokeLater(() -> {
			gui.setVisible(true);
		});

		sendMessage(new HelloMessage(messageNumber, args[2], new Random().nextLong()));
		initializeSocketListener();
	}

	/**
	 * Sends a hello message and wait for its appropriate {@link AckMessage}
	 * 
	 * @param message The HelloMessage
	 */
	private static void sendMessage(Message message) {

		DatagramPacket packet = message.getDatagramFromMessage();
		packet.setSocketAddress(new InetSocketAddress(adress, port));
		int counter = 0;
		while (counter < 10) {
			try {
				socket.send(packet);
			} catch (IOException exc) {
				exc.printStackTrace();
				return;
			}

			try {
				socket.setSoTimeout(5000);
			} catch (SocketException exc) {
				exc.printStackTrace();
				return;
			}

			byte[] buf = new byte[4000];

			DatagramPacket packet2 = new DatagramPacket(buf, buf.length);

			try {
				socket.receive(packet2);
			} catch (SocketTimeoutException exc) {
				counter++;
				continue;
			} catch (IOException exc) {
				exc.printStackTrace();
				return;
			}

			if (packet2.getData()[0] != Message.ACK) {
				counter++;
				continue;
			}

			AckMessage ackMessage = (AckMessage) MessageUtil.getMessageFromDatagram(packet2);
			if (Long.compare(ackMessage.getNumber(), Main.messageNumber) == 0) {
				Main.UID = ackMessage.getUID();
				Main.messageNumber++;
				break;
			}
		}

		return;
	}

	/**
	 * Initializes the listener on the opened socket, waits for either
	 * {@link InMessage} or {@link ByeMessage}
	 */
	private static void initializeSocketListener() {
		Thread t = new Thread(() -> {
			while (true) {
				byte[] buf = new byte[4000];

				DatagramPacket packet = new DatagramPacket(buf, buf.length);

				try {
					socket.setSoTimeout(0);
				} catch (SocketException exc) {
					break;
				}

				try {
					socket.receive(packet);
				} catch (SocketException exc) {

				} catch (SocketTimeoutException exc) {
					continue;
				} catch (IOException exc) {
					exc.printStackTrace();
					System.exit(0);
				}

				if (packet.getData()[0] == Message.INMSG) {

					InMessage message = (InMessage) MessageUtil.getMessageFromDatagram(packet);

					DatagramPacket ackPacket = new AckMessage(message.getNumber(), UID).getDatagramFromMessage();
					ackPacket.setSocketAddress(new InetSocketAddress(adress, port));

					try {
						socket.send(ackPacket);
					} catch (IOException exc) {
						exc.printStackTrace();
					}

					if (Long.compare(lastInMessageNumber, message.getNumber()) != 0) {
						gui.addText("[" + packet.getSocketAddress().toString() + "]" + "Poruka od korisnika: "
								+ message.getName() + "\n" + message.getText() + "\n\n");
						lastInMessageNumber = message.getNumber();
					}

				} else if (packet.getData()[0] == Message.ACK) {
					recievedMessages.add(MessageUtil.getMessageFromDatagram(packet));
				}
			}
		});

		t.setDaemon(true);
		t.start();
	}

	/**
	 * The window adapter ensures all threads are closed and a {@link ByeMessage} is
	 * sent
	 */
	private static WindowAdapter windowAdapter = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			ByeMessage message = new ByeMessage(messageNumber, UID);

			DatagramPacket packet = message.getDatagramFromMessage();
			packet.setSocketAddress(new InetSocketAddress(adress, port));
			for (int i = 0; i < 10; i++) {
				try {
					socket.send(packet);
				} catch (IOException exc) {
					exc.printStackTrace();
				}

				if (waitAndCheckAckMessage(message)) {
					break;
				}
			}

			socket.close();
			gui.dispose();
			System.exit(0);
		}
	};

	/**
	 * The key adapter handles the sending of the message
	 */
	private static KeyAdapter keyAdapter = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				JTextField field = (JTextField) e.getSource();

				String fieldText = field.getText();
				field.setText("");

				new Thread(() -> {

					OutMessage outMessage = new OutMessage(messageNumber, UID, fieldText);

					DatagramPacket packet = outMessage.getDatagramFromMessage();
					packet.setSocketAddress(new InetSocketAddress(adress, port));
					for (int i = 0; i < 10; i++) {
						try {
							socket.send(packet);
						} catch (IOException exc) {
							exc.printStackTrace();
						}

						if (waitAndCheckAckMessage(outMessage)) {
							break;
						}
					}
				}).start();
			}
		}
	};

	/**
	 * Waits for the {@link AckMessage} to the sent message to become available,
	 * checks if it is correct. Sends true if it is, false otherwise
	 * 
	 * @param message The sent message
	 * @return Returns true if the {@link AckMessage} is correct, false otherwise
	 */
	private static boolean waitAndCheckAckMessage(Message message) {
		Message receivedMessage = null;
		try {
			receivedMessage = recievedMessages.poll(5L, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		if (receivedMessage == null) {
			return false;
		}

		if (receivedMessage.getNumber() == message.getNumber()) {
			Main.messageNumber++;
			return true;
		}

		return false;
	}

}
