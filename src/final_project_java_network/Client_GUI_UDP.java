package final_project_java_network;

import java.awt.EventQueue;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Client_GUI_UDP extends JFrame {

//	private DatagramSocket client;
	private JPanel contentPane;
	private JTextField textField;
	private InetAddress host;
	private int port;
	private JTextArea textArea;

	public Client_GUI_UDP(InetAddress host, int port) {
		this.host = host;
		this.port = port;
	}

	private void execute() throws IOException {
		DatagramSocket client = new DatagramSocket();
		ReadClientUDP read = new ReadClientUDP(client);
		read.start();
		WriteClientUDP write = new WriteClientUDP(client, textArea, host, port);
		write.start();

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client_GUI_UDP frame = new Client_GUI_UDP();
					frame.setVisible(true);
					Client_GUI_UDP client = new Client_GUI_UDP(InetAddress.getLocalHost(), 15797);
					client.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Client_GUI_UDP() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Enter name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(20, 11, 95, 14);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(119, 10, 166, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(295, 9, 118, 23);

		contentPane.add(btnNewButton);

		textArea = new JTextArea();
		textArea.setBounds(10, 37, 414, 167);
		contentPane.add(textArea);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(10, 215, 320, 67);
		contentPane.add(textArea_1);

		JButton btnNewButton_1 = new JButton("SEND");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(335, 215, 89, 67);
		contentPane.add(btnNewButton_1);
	}

//	@Override
//	public void run() {
//		try {
//			while (true) {
//				String sms = recieveData(client);
//				System.out.println(sms);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//
//	private String recieveData(DatagramSocket client) throws IOException {
//		byte[] temp = new byte[1024];
//		DatagramPacket recieve_Packet = new DatagramPacket(temp, temp.length);
//		client.receive(recieve_Packet);
//		return new String(recieve_Packet.getData()).trim();
//	}

}

class ReadClientUDP extends Thread {
	private DatagramSocket client;

	public ReadClientUDP(DatagramSocket client) {
		super();
		this.client = client;
	}

	@Override
	public void run() {
		try {
			while (true) {
				String sms = recieveData(client);
				System.out.println(sms);
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

class WriteClientUDP extends Thread {
	private DatagramSocket client;
	private InetAddress host;
	JTextArea messageChatbox;
	private int port;

	public WriteClientUDP(DatagramSocket client,JTextArea messageChatbox, InetAddress host, int port) {
		super();
		this.client = client;
		this.host = host;
		this.port = port;
	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				String sms = sc.nextLine();
				DatagramPacket DP = createPacket(sms);
				client.send(DP);
				messageChatbox.setText(sms);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private DatagramPacket createPacket(String value) {
		byte[] arrData = value.getBytes();
		return new DatagramPacket(arrData, arrData.length, host, port);

	}
}
