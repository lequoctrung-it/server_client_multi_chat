package final_project_java_network;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Client_GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField messageField;
	private static JTextArea chatboxArea;

	//Chat field
	private static int port = 3001;
	static DataInputStream dataInputStream;
	static DataOutputStream dataOutputStream;
	static Socket client;
	private JTextField nameField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client_GUI frame = new Client_GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//Core function
		try {
			client = new Socket(InetAddress.getLocalHost(), port);
			new ReadClient(client, chatboxArea).start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Create the frame.
	 */
	public Client_GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nameLabel = new JLabel("Enter name: *");
		nameLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(12, 12, 141, 15);
		contentPane.add(nameLabel);
		
		messageField = new JTextField();
		messageField.setBounds(12, 203, 320, 48);
		contentPane.add(messageField);
		messageField.setColumns(10);
		
		JButton sendBtn = new JButton("Send");
		sendBtn.setBounds(344, 203, 94, 48);
		sendBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String sms = null;
				String name = null;
				if (nameField.getText().isEmpty()) {
					name = "Anonymous";
				} else {
					name = nameField.getText().trim();
				}
				sms = name + ": " + messageField.getText().trim();
				new WriteClient(client, chatboxArea, sms).start();;
				messageField.setText("");
				
			}
		});
		contentPane.add(sendBtn);
		
		chatboxArea = new JTextArea();
		chatboxArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		chatboxArea.setEditable(false);
		chatboxArea.setBounds(44, 1, 370, 149);
		contentPane.add(chatboxArea);
		JScrollPane scrollPane = new JScrollPane(chatboxArea);

		scrollPane.setBounds(12, 38, 426, 152);
		contentPane.add(scrollPane);
		
		nameField = new JTextField();
		nameField.setBounds(152, 11, 286, 19);
		contentPane.add(nameField);
		nameField.setColumns(10);
	}
}

class ReadClient extends Thread {
	private Socket client;
	private JTextArea messageChatbox;

	public ReadClient(Socket client, JTextArea messageChatbox) {
		this.client = client;
		this.messageChatbox = messageChatbox;
	}
	
	@Override
	public void run() {
		DataInputStream dataInputStream = null;
		try {
			dataInputStream = new DataInputStream(client.getInputStream());
			String sms;
			while (true) {
				sms = dataInputStream.readUTF();
				System.out.println("Client read: " + sms);
				messageChatbox.setText(messageChatbox.getText().trim() + "\n" + sms); 
			}
		} catch (Exception e) {
			try {
				dataInputStream.close();
				client.close();
			} catch (IOException ex) {
				System.out.println("Server disconnected!");
			}
		}
	}
}

class WriteClient extends Thread {
	private Socket client;
	private JTextArea messageChatbox;
	String messageSend;

	public WriteClient(Socket client, JTextArea messageChatbox, String messageSend) {
		this.client = client;
		this.messageChatbox = messageChatbox;
		this.messageSend = messageSend;
	}
	
	@Override
	public void run() {
		DataOutputStream dataOutputStream = null;
		try {
			System.out.println("TEST CLIENT WRITE " + client.getOutputStream());
			dataOutputStream = new DataOutputStream(client.getOutputStream());
			
//			while (true) {
				dataOutputStream.writeUTF(messageSend);
				dataOutputStream.flush();
				System.out.println("Client write: " + messageSend);
//			}
			messageChatbox.setText(messageChatbox.getText().trim() + "\n" + messageSend);
		} catch (Exception e) {
			try {
				dataOutputStream.close();
				client.close();
			} catch (IOException ex) {
				System.out.println("Server disconnected!");
			}
		}
	}
}

