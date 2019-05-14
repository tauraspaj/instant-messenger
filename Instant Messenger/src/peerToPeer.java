import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.crypto.Cipher;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class peerToPeer extends JFrame implements Runnable{
	
	private JTextField userText;
	private JTextArea chatWindow;
	private String username, address; 
	
	private DatagramSocket socket;
	private int port;
	private InetAddress ip;
	private Thread run, listen;
	private Client client;
	
	static Cipher cipher;
	final String secretKey = "CUSTOM-SECRET-KEY";
	
	private boolean running = false;
	
	private JFrame frame;
	
	public peerToPeer(String username, String address, int port) {
		super("Peer to peer chat");
		client = new Client(username, address, port);
		 
		buildWindow();
		
		console("Attempting connection to " + address + ":" + port + ", as user: " + username);
		boolean connect = client.openConnection(address);
		if (!connect) {
			System.err.println("Connection failed!");
			console("Connection failed!");
		} 
		String connection = "/c/" + username + "/e/";
		client.send(connection.getBytes());
		String history = "/l/";
		client.send(history.getBytes());
		running = true;
		userText.setEditable(true);
		run = new Thread(this, "Running");
		run.start();
		
	}
	
	private void buildWindow() {
		userText = new JTextField();
		userText.setEditable(false);
		add(userText, BorderLayout.SOUTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		userText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					send(userText.getText(), true);
				}
			}
		});
		chatWindow.setEditable(false);
		setSize(450, 300);
		setLocation(600, 300);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void run() {
		listen();
	}

	private void send(String message, boolean text) {
		if (message.equals("")) return;
		if (text) {
			message = client.getName() + ": " + message;
			
			try {			     
			    message = AESEncryptor.encrypt(message, secretKey) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			message = "/m/" + message + "/e/";
			userText.setText("");
		}
		client.send(message.getBytes());
	}

	public void listen() {
		listen = new Thread("Listen") {
			public void run() {
				while (running) {
					String message = client.receive();
					if (message.startsWith("/c/")) {
						client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
						console("Successfully connected to server! ID: " + client.getID());
					} else if (message.startsWith("/m/")) {
						String text = message.substring(3);
						text = text.split("/e/")[0];
					    text = AESEncryptor.decrypt(text, secretKey);
						console(text);
					} else if (message.startsWith("/l/")) {
						String text = message.substring(3);
						text = text.split("/e/")[0];
						console(text);
					}
				}
			}
		};
		listen.start();
	}

	public void console(String message) {
		chatWindow.append(message + "\n\r");
	}

}
