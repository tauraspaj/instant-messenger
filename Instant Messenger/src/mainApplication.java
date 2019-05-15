import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class mainApplication extends JFrame {

	private JPanel contentPane;
	private String username;
	private JButton btnAppP2P, btnAppExit;

	/**
	 * Launch the application.
	 */
	/*
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
	}*/

	/**
	 * Create the frame.
	 */
	public mainApplication(String username) {
		this.username = username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

				
		// Connection information button
		JButton btnAppP2P = new JButton("Peer-to-peer");
		btnAppP2P.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnAppP2P.setBounds((450/2)-120, 70, 240, 60);
		btnAppP2P.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectionInfo connInfo = new connectionInfo(username);
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
