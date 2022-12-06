package udp_chat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class Client_GUI extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField smsField;
	private JButton btnNewButton_1;
	private DatagramSocket client;
	private JTextArea chatArea;
	private JScrollPane scrollPane;

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

		JButton btnNewButton = new JButton("CONNECT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
//					System.out.println(InetAddress.getByName(hostName)+ "-" + portNo);

					client = new DatagramSocket();
					System.out.println(client);
					if (nameField.getText().isEmpty()) {
						client.send(createPacket("Anonymous" + " joined"));
					} else {
						client.send(createPacket(nameField.getText() + " joined"));
					}

					ReadClient read = new ReadClient(client, chatArea);
					read.start();

//					WriteClient write = new WriteClient(client, InetAddress.getLocalHost(), 15797, nameField.getText(),smsField);
//					write.start();

				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNewButton.setBounds(337, 7, 87, 20);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_2 = new JLabel("NAME");
		lblNewLabel_2.setBounds(10, 10, 46, 14);
		contentPane.add(lblNewLabel_2);

		nameField = new JTextField();
		nameField.setBounds(57, 7, 270, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);

		smsField = new JTextField();
		smsField.setBounds(10, 182, 317, 68);
		contentPane.add(smsField);
		smsField.setColumns(10);

		btnNewButton_1 = new JButton("SEND");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					WriteClient write = new WriteClient(client, nameField.getText(),InetAddress.getLocalHost(), 15797 , smsField, chatArea);
					write.start();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		btnNewButton_1.setBounds(337, 205, 89, 23);
		contentPane.add(btnNewButton_1);

		chatArea = new JTextArea();
		chatArea.setBounds(10, 35, 317, 139);
		contentPane.add(chatArea);

		scrollPane = new JScrollPane(chatArea);
		scrollPane.setBounds(10, 35, 317, 139);
		contentPane.add(scrollPane);

	}

	private DatagramPacket createPacket(String value) throws UnknownHostException {
		byte[] arrData = value.getBytes();
		return new DatagramPacket(arrData, arrData.length, InetAddress.getLocalHost(), 15797);
	}
}

class ReadClient extends Thread {
	private DatagramSocket client;
	private JTextArea chatArea;

	public ReadClient(DatagramSocket client, JTextArea chatArea) {
		this.client = client;
		this.chatArea = chatArea;
	}

	@Override
	public void run() {
		try {
			while (true) {
				String sms = recieveData(client);
				chatArea.setText(chatArea.getText().trim() + "\n" + sms);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private String recieveData(DatagramSocket client) throws IOException {
		byte[] temp = new byte[1024];
		DatagramPacket recieve_Packet = new DatagramPacket(temp, temp.length);
		client.receive(recieve_Packet);
		return new String(recieve_Packet.getData()).trim();

	}
}

class WriteClient extends Thread {
	private DatagramSocket client;
	private String name;
	private JTextField smsField;
	private JTextArea chatArea;
	private InetAddress host;
	private int port;

	public WriteClient(DatagramSocket client, String name, InetAddress host, int port, JTextField smsField,
			JTextArea chatArea) {
		this.client = client;
		this.name = name;
		this.host = host;
		this.port = port;
		this.smsField = smsField;
		this.chatArea = chatArea;
	}

	@Override
	public void run() {
		try {
			String sms = smsField.getText().trim();
			DatagramPacket DP = createPacket(name + " : " + sms);
			client.send(DP);
			chatArea.setText(chatArea.getText().trim() + "\n" + sms);
			smsField.setText("");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private DatagramPacket createPacket(String value) throws UnknownHostException {
		byte[] arrData = value.getBytes();
		return new DatagramPacket(arrData, arrData.length, InetAddress.getLocalHost(), 15797);
	}
}