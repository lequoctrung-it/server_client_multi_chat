package udp_chat;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class Server_GUI extends JFrame {

	private JPanel contentPane;
	private static InetAddress clientIP;
	private static int clientPort;
	public static Map<DatagramPacket, Integer> listSK= new HashMap<DatagramPacket, Integer>();

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

		try {
//			listSK 
			DatagramSocket server = new DatagramSocket(15797);

			WriteServer write = new WriteServer(server);
			write.start();
			

			System.out.println("Server is listening...");
			byte[] temp = new byte[1024];
			DatagramPacket recieve_Packet = new DatagramPacket(temp, temp.length);

			while (true) {
				server.receive(recieve_Packet);
				
				clientIP = recieve_Packet.getAddress();
				clientPort = recieve_Packet.getPort();
				
				String sms = new String(recieve_Packet.getData()).trim();
				
				try {
//					for (DatagramPacket item : listSK.keySet()) {
//						if (item.getAddress().equals(recieve_Packet.getAddress()) && item.getPort() == recieve_Packet.getPort()) {
//							listSK.replace(item, 0);
//							break;
//						}
//					}
					listSK.put(recieve_Packet, 0);
//					try {
//						for (DatagramPacket item : listSK.keySet()) {
//							if (!(item.getAddress().equals(clientIP) && item.getPort() == clientPort)) {
//								byte[] temp2 = new byte[1024];
//								temp2 = sms.getBytes();
//								DatagramPacket send_result_Packet = new DatagramPacket(temp2, temp2.length, clientIP, clientPort);
//								server.send(send_result_Packet);
//							}
//						}
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
//				for (DatagramPacket item : listSK.keySet()) {
//					if (!(item.getAddress().equals(clientIP) && item.getPort() == clientPort)) {
//						byte[] temp2 = new byte[1024];
//						temp2 = sms.getBytes();
//						DatagramPacket send_result_Packet = new DatagramPacket(temp2, temp2.length, clientIP, clientPort);
//						server.send(send_result_Packet);
//					}
//				}
				System.out.println(sms);
				System.out.println(listSK);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

//	private String recieveData(DatagramSocket server) throws IOException {
//		byte[] temp = new byte[1024];
//		DatagramPacket recieve_Packet = new DatagramPacket(temp, temp.length);
//		server.receive(recieve_Packet);
//		clientIP = recieve_Packet.getAddress();
//		clientPort = recieve_Packet.getPort();
//		checkDuplicate(recieve_Packet);
//		return new String(recieve_Packet.getData()).trim();
//	}

//	private void checkDuplicate(DatagramPacket packet) {
//		for (DatagramPacket item : listSK.keySet()) {
//			if (item.getAddress().equals(packet.getAddress()) && item.getPort() == packet.getPort()) {
//				listSK.replace(item, 0);
//				return;
//			}
//		}
//		listSK.put(packet, 0);
//	}

//	private void sendData(String value, DatagramSocket server, InetAddress clientIP, int clientPort)
//			throws IOException {
//		byte[] temp = new byte[1024];
//		temp = value.getBytes();
//		DatagramPacket send_result_Packet = new DatagramPacket(temp, temp.length, clientIP, clientPort);
//		server.send(send_result_Packet);
//	}

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

		JTextArea chatArea = new JTextArea();
		chatArea.setBounds(10, 11, 414, 181);
		contentPane.add(chatArea);

		JButton btnNewButton = new JButton("SEND");
		btnNewButton.setBounds(335, 203, 89, 47);
		contentPane.add(btnNewButton);

		JTextArea smsArea = new JTextArea();
		smsArea.setBounds(10, 203, 316, 47);
		contentPane.add(smsArea);

		JScrollPane scrollPane = new JScrollPane(chatArea);
		scrollPane.setBounds(10, 11, 414, 181);
		contentPane.add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane(smsArea);
		scrollPane_1.setBounds(10, 203, 315, 47);
		contentPane.add(scrollPane_1);
	}

}

class WriteServer extends Thread {
	private DatagramSocket server;

	public WriteServer(DatagramSocket server) {
		this.server = server;
	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			String sms = sc.nextLine();
			try {
				for (DatagramPacket item : Server_GUI.listSK.keySet()) {
					sendData("Server: " + sms, server, item.getAddress(), item.getPort());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private void sendData(String value, DatagramSocket server, InetAddress clientIP, int clientPort)
			throws IOException {
		byte[] temp = new byte[1024];
		temp = value.getBytes();
		DatagramPacket send_result_Packet = new DatagramPacket(temp, temp.length, clientIP, clientPort);
		server.send(send_result_Packet);
	}
}
