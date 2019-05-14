import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class loginSystem {

	private JFrame frame;
	private JLabel lblLogUsername, lblLogPassword, lblRegUsername, lblRegPassword, lblRegEmail, lblRegFullName;
	private JTextField txtLogUsername, txtRegUsername, txtRegEmail, txtRegFullName;
	private JPasswordField txtLogPassword, txtRegPassword;
	private JButton btnMenuLogin, btnMenuRegister, btnMenuExit, btnLogLogin, btnLogClear, btnLogBack, btnRegRegister, btnRegClear, btnRegBack;
	private JPanel panelMenu, panelLogin, panelRegister;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginSystem window = new loginSystem();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public loginSystem() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		/*
		 *  Initialising panels
		 */
		
		// Menu panel
		JPanel panelMenu = new JPanel();
		frame.getContentPane().add(panelMenu, "name_192629480929964");
		
		// Login panel
		JPanel panelLogin = new JPanel();
		frame.getContentPane().add(panelLogin, "name_192632424816088");
		panelLogin.setLayout(null);
		
		// Register panel
		JPanel panelRegister = new JPanel();
		frame.getContentPane().add(panelRegister, "name_192635726826368");
		panelRegister.setLayout(null);
		
		
		/**
		 * Menu panel
		 */
		
		// Login button
		JButton btnMenuLogin = new JButton("Login");
		btnMenuLogin.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnMenuLogin.setBounds((450/2)-60, 5, 120, 60);
		btnMenuLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelLogin.setVisible(true);
				panelMenu.setVisible(false);
			}
		});
		panelMenu.setLayout(null);
		panelMenu.add(btnMenuLogin);
		
		// Register button
		JButton btnMenuRegister = new JButton("Register");
		btnMenuRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelRegister.setVisible(true);
				panelMenu.setVisible(false);
			}
		});
		btnMenuRegister.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnMenuRegister.setBounds((450/2)-60, 70, 120, 60);
		panelMenu.add(btnMenuRegister);
		
		// Exit button
		JButton btnMenuExit = new JButton("Exit");
		btnMenuExit.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnMenuExit.setBounds((450/2)-60, 135, 120, 60);
		btnMenuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panelMenu.add(btnMenuExit);
		
		
		/**
		 *  Login panel
		 */
		
		// Username label
		JLabel lblLogUsername = new JLabel("Username");
		lblLogUsername.setBounds((450/2)-85, 30, 70, 30);
		panelLogin.add(lblLogUsername);
		
		// Username input
		txtLogUsername = new JTextField();
		txtLogUsername.setBounds((450/2), 30, 120, 30);
		panelLogin.add(txtLogUsername);
		
		// Password label
		JLabel lblLogPassword = new JLabel("Password");
		lblLogPassword.setBounds((450/2)-85, 70, 70, 30);
		panelLogin.add(lblLogPassword);
		
		// Password input
		txtLogPassword = new JPasswordField();
		txtLogPassword.setBounds((450/2), 70, 120, 30);
		panelLogin.add(txtLogPassword);
		
		// Login button
		JButton btnLogLogin = new JButton("Login");
		btnLogLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtLogUsername.getText();
				String password = String.valueOf(txtLogPassword.getPassword());
				passwordHasher hasher = new passwordHasher();
				String hashedPassword = hasher.generateHash(password);
				
				if (username.equals("") || password.equals("")) {
					JOptionPane.showMessageDialog(frame, "Please fill out all the fields");
				} else {
					PreparedStatement ps;
					String query = "SELECT `username`, `password_hash` FROM `users` WHERE `username` = '" + username + "'";
					
					try {
						Connection conn = connectDb.getConnection();	
						ps = conn.prepareStatement(query);
						ResultSet r1= ps.executeQuery();
						String checkUser;
						String checkPassword;
						
						if (r1.next() == false) {
							JOptionPane.showMessageDialog(frame, "This user does not exist");
						} else {
							checkUser = r1.getString("username");
							checkPassword = r1.getString("password_hash");
							if (checkUser.equals(username) && checkPassword.equals(hashedPassword)) {
								 JOptionPane.showMessageDialog(frame, "You have successfully logged in!");
								 mainApplication mainApp = new mainApplication(username);
								 frame.dispose();
								 mainApp.setVisible(true);
							} else {
								JOptionPane.showMessageDialog(frame, "Incorrect");
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnLogLogin.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnLogLogin.setBounds(18, 188, 120, 60);
		panelLogin.add(btnLogLogin);
		
		// Clear button
		JButton btnLogClear = new JButton("Clear");
		btnLogClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtLogUsername.setText("");
				txtLogPassword.setText("");
			}
		});
		btnLogClear.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnLogClear.setBounds(156, 188, 120, 60);
		panelLogin.add(btnLogClear);
		
		// Back button
		JButton btnLogBack = new JButton("Back");
		btnLogBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelLogin.setVisible(false);
				panelMenu.setVisible(true);
			}
		});
		btnLogBack.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnLogBack.setBounds(296, 188, 120, 60);
		panelLogin.add(btnLogBack);
		
		
		/**
		 *  Register panel
		 */
		
		// Username label
		JLabel lblRegUsername = new JLabel("Username");
		lblRegUsername.setBounds((450/2)-85, 30, 70, 30);
		panelRegister.add(lblRegUsername);
				
		// Username field
		txtRegUsername = new JTextField();
		txtRegUsername.setBounds((450/2), 30, 120, 30);
		panelRegister.add(txtRegUsername);
		
		// Email label
		JLabel lblRegEmail = new JLabel("Email");
		lblRegEmail.setBounds((450/2)-85, 70, 70, 30);
		panelRegister.add(lblRegEmail);
				
		// Email input
		txtRegEmail = new JTextField();
		txtRegEmail.setBounds((450/2), 70, 120, 30);
		panelRegister.add(txtRegEmail);
				
		// Full name label
		JLabel lblRegFullName = new JLabel("Full name");
		lblRegFullName.setBounds((450/2)-85, 110, 70, 30);
		panelRegister.add(lblRegFullName);
				
		// Full name input
		txtRegFullName = new JTextField();
		txtRegFullName.setBounds((450/2), 110, 120, 30);
		panelRegister.add(txtRegFullName);
		
		// Password label
		JLabel lblRegPassword = new JLabel("Password");
		lblRegPassword.setBounds((450/2)-85, 150, 126, 30);
		panelRegister.add(lblRegPassword);
				
		// Password input
		txtRegPassword = new JPasswordField();
		txtRegPassword.setBounds((450/2), 150, 120, 30);
		panelRegister.add(txtRegPassword);
		
		// Login button
		JButton btnRegRegister = new JButton("Register");
		btnRegRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtRegUsername.getText();
				String fullName = txtRegFullName.getText();
				String email = txtRegEmail.getText();
				String password = String.valueOf(txtRegPassword.getPassword());
				passwordHasher hasher = new passwordHasher();
				String hashedPassword = hasher.generateHash(password);
				
				if (username.equals("") || fullName.equals("") || email.equals("") || password.equals("")) {
					JOptionPane.showMessageDialog(frame, "Please fill out all the fields");
				} else if (!email.contains("@")) { 
					JOptionPane.showMessageDialog(frame, "Please enter a valid email");
				} else if (password.length() < 8) { 
					JOptionPane.showMessageDialog(frame, "Your password must be at least 8 characters long");
				} else {
					PreparedStatement ps;
					String query = "INSERT INTO `users`(`username`, `full_name`, `email`, `password_hash`) "
							+ "VALUES ('" + username + "','" + fullName + "','" + email + "','" + hashedPassword + "')";
					
					try {
						Connection conn = connectDb.getConnection();	
						ps = conn.prepareStatement(query);
						
						
						if (ps.executeUpdate() > 0) {
							JOptionPane.showMessageDialog(frame, "You have been registered");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					} finally {
						System.out.println("User registered");
					}
				}
			}
		});
		btnRegRegister.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnRegRegister.setBounds(18, 188, 120, 60);
		panelRegister.add(btnRegRegister);
		
		// Clear button
		JButton btnRegClear = new JButton("Clear");
		btnRegClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtRegUsername.setText("");
				txtRegEmail.setText("");
				txtRegFullName.setText("");
				txtRegPassword.setText("");
			}
		});
		btnRegClear.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnRegClear.setBounds(156, 188, 120, 60);
		panelRegister.add(btnRegClear);
		
		// Back button
		JButton btnRegBack = new JButton("Back");
		btnRegBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelRegister.setVisible(false);
				panelMenu.setVisible(true);
			}
		});
		btnRegBack.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnRegBack.setBounds(296, 188, 120, 60);
		panelRegister.add(btnRegBack);
	}
}
