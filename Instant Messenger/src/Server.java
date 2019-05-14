import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Server implements Runnable {

	private List<ServerClient> clients = new ArrayList<ServerClient>();

	private DatagramSocket socket;
	private int port;
	private boolean running = false;
	private Thread run, send, receive;
	
	final String secretKey = "CUSTOM-SECRET-KEY";
	
	public Server(int port) {
		this.port = port;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		run = new Thread(this, "Server");
		run.start();
	}

	public void run() {
		running = true;
		System.out.println("Server started on port " + port);
		receive();
		Scanner scanner = new Scanner(System.in);
		while (running) {
			String text = scanner.nextLine();
			if (!text.startsWith("/")) {
				sendToAll("/m/Server: " + text + "/e/");
				continue;
			}
			text = text.substring(1);
			if (text.equals("clients")) {
				System.out.println("Clients:");
				System.out.println("========");
				for (int i = 0; i < clients.size(); i++) {
					ServerClient c = clients.get(i);
					System.out.println(c.name + "(" + c.getID() + "): " + c.address.toString() + ":" + c.port);
				}
				System.out.println("========");
			} else if (text.equals("help")) {
				printHelp();
			} else if (text.equals("quit")) {
				quit();
			} else {
				System.out.println("Unknown command.");
				printHelp();
			}
		}
		scanner.close();
	}

	private void printHelp() {
		System.out.println("Here is a list of all available commands:");
		System.out.println("=========================================");
		System.out.println("/clients - shows all connected clients.");
		System.out.println("/help - shows this help message.");
		System.out.println("/quit - shuts down the server.");
	}

	private void receive() {
		receive = new Thread("Receive") {
			public void run() {
				while (running) {
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						socket.receive(packet);
					} catch (SocketException e) {
					} catch (IOException e) {
						e.printStackTrace();
					}
					process(packet);
				}
			}
		};
		receive.start();
	}

	private void sendToAll(String message) {
		if (message.startsWith("/m/")) {
			String text = message.substring(3);
			text = text.split("/e/")[0];
		    text = AESEncryptor.encrypt(text, secretKey);
			System.out.println(message);
			message = "/m/" + text + "/e/";
		}
		for (int i = 0; i < clients.size(); i++) {
			ServerClient client = clients.get(i);
			send(message.getBytes(), client.address, client.port);
		}
	}

	private void send(final byte[] data, final InetAddress address, final int port) {
		send = new Thread("Send") {
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
	}

	private void send(String message, InetAddress address, int port) {
		message += "/e/";
		send(message.getBytes(), address, port);
	}

	private void addMessageToDB(String message) {
		String name = message.split(":")[0];
		message = message.split(":")[1];
		PreparedStatement ps;
		String query = "INSERT INTO `message_history`(`port`, `user`, `message`) "
				+ "VALUES ('" + this.port + "','" + name + "','" + message + "')";
		
		try {
			Connection conn = connectDb.getConnection();	
			ps = conn.prepareStatement(query);
			
			
			if (ps.executeUpdate() > 0) {
				//
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void loadHistory(InetAddress address, int port) {
		String message, dbUser, dbText;
		PreparedStatement ps;
		String query = "SELECT `user`, `message` FROM `message_history` WHERE `port` = '" + this.port + "'";

		try {
			Connection conn = connectDb.getConnection();	
			ps = conn.prepareStatement(query);
			ResultSet rs= ps.executeQuery();
			
			while (rs.next()) {
				dbUser = rs.getString("user");
				dbText = rs.getString("message");
				
				message = "/l/" + dbUser + ":" + dbText;
				System.out.println(message);
				send(message, address, port);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void process(DatagramPacket packet) {
		String string = new String(packet.getData());
		if (string.startsWith("/c/")) {
			int id = new Random().nextInt();
			String name = string.split("/c/|/e/")[1];
			System.out.println(name + "(" + id + ") connected!");
			clients.add(new ServerClient(name, packet.getAddress(), packet.getPort(), id));
			String ID = "/c/" + id;
			send(ID, packet.getAddress(), packet.getPort());
		} else if (string.startsWith("/m/")) {
			
			String text = string.substring(3);
			text = text.split("/e/")[0];
			
		    text = AESEncryptor.decrypt(text, secretKey);
		    addMessageToDB(text);

			sendToAll("/m/" + text + "/e/");
		} else if (string.startsWith("/l/")) {
			loadHistory(packet.getAddress(), packet.getPort());
		} else {
			System.out.println(string);
		}
	}

	private void quit() {
		for (int i = 0; i < clients.size(); i++) {
			disconnect(clients.get(i).getID(), true);
		}
		running = false;
		socket.close();
	}

	private void disconnect(int id, boolean status) {
		ServerClient c = null;
		boolean existed = false;
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getID() == id) {
				c = clients.get(i);
				clients.remove(i);
				existed = true;
				break;
			}
		}
		if (!existed) return;
		String message = "";
		if (status) {
			message = "Client " + c.name + " (" + c.getID() + ") @ " + c.address.toString() + ":" + c.port + " disconnected.";
		} else {
			message = "Client " + c.name + " (" + c.getID() + ") @ " + c.address.toString() + ":" + c.port + " timed out.";
		}
		System.out.println(message);
	}

}