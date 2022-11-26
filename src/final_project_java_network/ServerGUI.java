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
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;
import java.awt.Color;

public class ServerGUI extends JFrame {

	private JPanel contentPane;
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
					ServerGUI frame = new ServerGUI();
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
	public ServerGUI() {
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
				String sms = "";
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
			String sms = null;
			dataInputStream = new DataInputStream(server.getInputStream());
			while (true) {
				sms = dataInputStream.readUTF();
				boolean isFunctions = sms.contains("@");
				if (isFunctions) {
					String[] smsSplited = sms.split("@");
					sms = ExecuteNumbers(smsSplited);
				}
				
				for (Socket item : ServerGUI.listSocket) {
					if (item.getPort() != server.getPort() || isFunctions) {
						DataOutputStream dataOutputStream = new DataOutputStream(item.getOutputStream());
						dataOutputStream.writeUTF(sms);
					}
				}
				messageChatbox.setText(messageChatbox.getText().trim() + "\n" + sms);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String ExecuteNumbers(String[] sms) {
		String res = "";
		int num1 = Integer.parseInt(sms[0]);
		int num2 = Integer.parseInt(sms[1]);
		Action action = Action.valueOf(sms[2]);
		
		switch (action) {
		case ADD:
			res = "Sum of 2 numbers is: " + String.valueOf(num1 + num2);
			break;
		case MINUS:
			res = "Minus of 2 numbers is: " + String.valueOf(num1 - num2);
			break;
		case MULTIPLY:
			res = "Multiply of 2 numbers is: " + String.valueOf(num1 * num2);
			break;
		case DIVIDE:
			res = "Divide of 2 numbers is: " + String.valueOf(num1 / num2);
			break;
		case RECTANGLE_AREA:
			res = "Area of rectangle is: " + String.valueOf(num1 * num2);
			break;
		case RECTANGLE_PERIMETER:
			res = "Perimeter of rectangle is: " + String.valueOf((num1 + num2)*2);
			break;
		case SQUARE_AREA:
			res = "Minus of 2 numbers is: " + String.valueOf(num1 * num2);
			break;
		case SQUARE_PERIMETER:
			res = "Minus of 2 numbers is: " + String.valueOf((num1 + num2)*2);
			break;
		default:
			return "";
		}
		return res;
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
			try {
				for (Socket item : ServerGUI.listSocket) {
					dataOutputStream = new DataOutputStream(item.getOutputStream());
					dataOutputStream.writeUTF(messageSend);
					dataOutputStream.flush();
				}
				messageChatbox.setText(messageChatbox.getText().trim() + "\n" + messageSend);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}

