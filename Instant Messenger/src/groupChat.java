import java.awt.*;
import javax.swing.*;

public class groupChat extends JFrame{
	
	private JTextField userText;
	private JTextArea chatWindow;
	
	public groupChat() {
		super("Group chat");
		
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
