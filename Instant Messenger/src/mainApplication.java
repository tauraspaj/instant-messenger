import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class mainApplication extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainApplication frame = new mainApplication();
					frame.setVisible(true);
				} catch (Exception e) {	
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainApplication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Login button
		JButton btnAppGroupChat = new JButton("Group chat");
		btnAppGroupChat.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnAppGroupChat.setBounds((450/2)-120, 5, 240, 60);
		btnAppGroupChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				groupChat groupChat = new groupChat();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnAppGroupChat);
				
		// Register button
		JButton btnAppP2P = new JButton("Peer-to-peer");
		btnAppP2P.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnAppP2P.setBounds((450/2)-120, 70, 240, 60);
		btnAppP2P.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//peerToPeer peerToPeer = new peerToPeer();
				connectionInfo connInfo = new connectionInfo();
			}
		});
		contentPane.add(btnAppP2P);
				
		// Exit button
		JButton btnAppExit = new JButton("Exit");
		btnAppExit.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnAppExit.setBounds((450/2)-60, 135, 120, 60);
		btnAppExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		contentPane.add(btnAppExit);
	}

}
