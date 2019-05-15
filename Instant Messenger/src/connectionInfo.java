import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class connectionInfo extends JFrame {

	private JPanel contentPane;
	private JTextField txtName, txtAddress, txtPort;
	private JLabel lblIpAddress, lblPort, lblAddressDesc, lblPortDesc;
	
	private String username;

	public connectionInfo(String username) {
		super("Chat room information");
		this.username = username;
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300, 380);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);

		txtName = new JTextField();
		txtName.setBounds(67, 50, 165, 28);
		contentPane.add(txtName);
		txtName.setEditable(false);
		txtName.setText(username);
		txtName.setColumns(10);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(127, 34, 45, 16);
		contentPane.add(lblName);

		txtAddress = new JTextField();
		txtAddress.setBounds(67, 116, 165, 28);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);

		lblIpAddress = new JLabel("IP Address:");
		lblIpAddress.setBounds(111, 96, 77, 16);
		contentPane.add(lblIpAddress);

		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(67, 191, 165, 28);
		contentPane.add(txtPort);

		lblPort = new JLabel("Port:");
		lblPort.setBounds(133, 171, 34, 16);
		contentPane.add(lblPort);

		lblAddressDesc = new JLabel("(eg. 192.168.0.2)");
		lblAddressDesc.setBounds(94, 142, 112, 16);
		contentPane.add(lblAddressDesc);

		lblPortDesc = new JLabel("(eg. 8192)");
		lblPortDesc.setBounds(116, 218, 68, 16);
		contentPane.add(lblPortDesc);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String address = txtAddress.getText();
				int port = Integer.parseInt(txtPort.getText());
				peerToPeer peerChat = new peerToPeer(username, address, port);
				setVisible(false);
				dispose();
			}
		});
		btnLogin.setBounds(91, 311, 117, 29);
		contentPane.add(btnLogin);
	}
}