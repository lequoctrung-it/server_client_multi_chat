package final_project_java_network;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.CardLayout;

import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;
import java.awt.Color;

public class Server_GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private static JTextArea msg_area;
	
	//Chat fields
	private static int port = 3001;
	public static ArrayList<Socket> listSocket = new ArrayList<>();
	static DataOutputStream dataOutputStream;
	static DataInputStream dataInputStream;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server_GUI frame = new Server_GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//Core function
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Serve listening....");
			while (true) {
				Socket socket = serverSocket.accept();
				listSocket.add(socket);
				System.out.println("Connected to " + socket);
				new ReadServer(socket, msg_area).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public Server_GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Server");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 12, 426, 15);
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(12, 203, 320, 48);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.setBounds(344, 203, 94, 48);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DataOutputStream dataOutputStream = null;
				String sms = ""; //data from GUI
				sms = "Server: " + textField_1.getText().trim();
				new WriteServer(msg_area, sms).start();;
				textField_1.setText("");
			}
		});
		contentPane.add(btnNewButton);
		
		msg_area = new JTextArea();
		msg_area.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		msg_area.setEditable(false);
		msg_area.setBounds(44, 1, 370, 149);
		contentPane.add(msg_area);
		JScrollPane scrollPane = new JScrollPane(msg_area);

		scrollPane.setBounds(12, 38, 426, 152);
		contentPane.add(scrollPane);
	}
}

class ReadServer extends Thread {
	private Socket server;
	private JTextArea messageChatbox;
	
	public ReadServer(Socket server, JTextArea messageChatbox) {
		this.server = server;
		this.messageChatbox = messageChatbox;  
	}
	
	@Override
	public void run() {
		DataInputStream dataInputStream = null;
		try {
			dataInputStream = new DataInputStream(server.getInputStream());
			while (true) {
				String sms = dataInputStream.readUTF();
				for (Socket item : Server_GUI.listSocket) {
					if (item.getPort() != server.getPort()) {
						DataOutputStream dataOutputStream = new DataOutputStream(item.getOutputStream());
						dataOutputStream.writeUTF(sms);
					}
				}
				System.out.println("Server read: " + sms);
				messageChatbox.setText(messageChatbox.getText().trim() + "\n" + sms);
			}
			
		} catch (IOException e) {
			try {
				dataInputStream.close();
				server.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}

class WriteServer extends Thread {
	private JTextArea messageChatbox;
	String messageSend;
	
	public WriteServer(JTextArea messageChatbox, String messageSend) {
		this.messageChatbox = messageChatbox;
		this.messageSend = messageSend;
	}

	@Override
	public void run() {
		DataOutputStream dataOutputStream = null;
//		while (true) {
			try {
				for (Socket item : Server_GUI.listSocket) {
					dataOutputStream = new DataOutputStream(item.getOutputStream());
					dataOutputStream.writeUTF(messageSend);
					dataOutputStream.flush();
				}
				System.out.println("Server write: " + messageSend);
				messageChatbox.setText(messageChatbox.getText().trim() + "\n" + messageSend);
			} catch (IOException e) {
				e.printStackTrace();
			}
//		}
	}
}

