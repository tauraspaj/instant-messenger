import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class peerToPeer extends JFrame {
	
	private JTextField userText;
	private JTextArea chatWindow;
	
	private JFrame frame;
	
	public peerToPeer() {
		super("Peer to peer chat");
		
		userText = new JTextField();
		userText.setEditable(false);
		//Add action listener
		add(userText, BorderLayout.SOUTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		chatWindow.setEditable(false);
		setSize(450, 300);
		setLocation(600, 300);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}

}
